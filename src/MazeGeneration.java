import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class MazeGeneration {
	
	//enum direction for each cell to hold
	enum Direction{
		UP, DOWN, LEFT, RIGHT
	}
	
	public int width, height; //width height of maze
	private HashSet<Direction>[][] maze; //2d array of direction sets to represent maze 
	private int[] start; //start cell
	private HashSet<List<Integer>> visited, pEndpoints; //visited and potential end points list
	private Stack<int[]> backtrace; //backtrace stack
	private Stack<Direction> backtraceDir; //backtrace direction stack
	
	public void draw() {
//		System.out.println("DRAW: "); //prints values of every cell 
//		for (int i=0; i<height; i++) {
//			for (int j=0; j<width; j++) {
//				System.out.print("["+i+", "+j+"] ");
//				for (Direction dir: maze[i][j])
//					System.out.print(dir+" ");
//				System.out.println();
//			}
//		}
		for (int i=0; i<width; i++)  
			System.out.print(" __"); //prints the top of the maze
		System.out.println();
		for (int i=0; i<height; i++) {
			System.out.print('|'); //prints left wall of the maze
			for (int j=0; j<width; j++) {
				if(!maze[i][j].contains(Direction.DOWN))
					System.out.print("__"); 
				else
					System.out.print("  ");
				if(!maze[i][j].contains(Direction.RIGHT))
					System.out.print('|');
				else
					System.out.print(" ");
			} //draws maze
			System.out.println();
		}
	}

	public void generate() {
		generate(start, false);
		System.out.println(pEndpoints); //prints potential endpoints for the maze
		draw(); 
	}
	
	private void generate(int[] curr, boolean isDeadend) {
		if (Arrays.equals(curr, start)&&visited.size()!=0) { 
			return; //if it backtraces back to start, end generation
		}
		ArrayList<Direction> possibles=possibles(curr); //all possible directions from curr
		if (possibles.size()==0) { //if deadend or backtracing
			if (!isDeadend) pEndpoints.add(List.of(curr[0], curr[1])); //add to potential endpoints if true deadend
			visited.add(List.of(curr[0], curr[1])); //add point to visited
			maze[curr[0]][curr[1]].add(backtraceDir.pop()); //add previous direction
//			System.out.println("["+curr[0]+", "+curr[1]+"]"); //print backtrace steps
			generate(backtrace.pop(), true); //go backwards by popping from stack
			return;
		}
		Direction dir=possibles.get((int)(Math.random()*possibles.size())); //gets random valid direction
		visited.add(List.of(curr[0], curr[1])); //adds to visited set
		backtrace.push(curr); //pushes to backtrace
		maze[curr[0]][curr[1]].add(dir); //adds direction to cell
//		System.out.println("["+curr[0]+", "+curr[1]+"] "+dir); //prints step 
		switch(dir) { //pushes opposite direction for backtrace and moves to next cell
		case UP: 
			backtraceDir.push(Direction.DOWN); 
			generate(new int[] {curr[0]-1, curr[1]}, false);
			break;
		case DOWN:
			backtraceDir.push(Direction.UP);
			generate(new int[] {curr[0]+1, curr[1]}, false);
			break;
		case LEFT:
			backtraceDir.push(Direction.RIGHT);
			generate(new int[] {curr[0], curr[1]-1}, false);
			break;
		case RIGHT:
			backtraceDir.push(Direction.LEFT);
			generate(new int[] {curr[0], curr[1]+1}, false);
			break;
		}
	}
	
	private ArrayList<Direction> possibles(int[] n){ //creates list of possible directions 
		ArrayList<Direction> possibles=new ArrayList<Direction>();
		for (Direction dir: Direction.values())
			switch(dir) {
			case UP:
				if (n[0]==0||visited.contains(List.of(n[0]-1, n[1]))) break;
				possibles.add(dir);
				break;
			case DOWN:
				if (n[0]==height-1||visited.contains(List.of(n[0]+1, n[1]))) break;
				possibles.add(dir);
				break;
			case LEFT:
				if (n[1]==0||visited.contains(List.of(n[0], n[1]-1))) break;
				possibles.add(dir);
				break;
			case RIGHT:
				if (n[1]==width-1||visited.contains(List.of(n[0], n[1]+1))) break;
				possibles.add(dir);
				break;
			} 
		return possibles;
	}
	
	public MazeGeneration(int width, int height) { //constructor initialize all variables and calls generate
		this.width=width;
		this.height=height;
		maze=new HashSet[height][width];
		for (int i=0; i<height; i++) 
			for (int j=0; j<width; j++) 
				maze[i][j]=new HashSet<Direction>();
		start=new int[]{(int) (Math.random()*height), (int) (Math.random()*width)};
		visited=new HashSet<List<Integer>>();
		pEndpoints=new HashSet<List<Integer>>();
		backtrace=new Stack<int[]>();
		backtraceDir=new Stack<Direction>();
		generate(); //generate maze
		MazeSaver saver=new MazeSaver(); //save maze to text file
		try {
			saver.save("maze.txt", maze);
		} catch (IOException e) {
			e.printStackTrace();
		}
		MazeLoader loader=new MazeLoader(); //load maze from text file
		try {
			maze=loader.load("maze.txt");
			draw();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public MazeGeneration(int n) { //constructor for square mazes
		this(n,n);
	}
	
	public static void main(String[] args) { //main method to create maze generator
		new MazeGeneration(45);
	}
}
