import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

public class MazeSaver {
	
	private PrintWriter wr;
	
	public void save(String filename, HashSet<MazeGeneration.Direction>[][] maze) throws IOException { //saves maze with notation similar to FEN from chess
		wr=new PrintWriter(filename);
		for (int i=0; i<maze.length; i++) { //only writes the first wall/no wall with 1/0; next writes the number of that boolean then each number after alternates
			StringBuilder walls=new StringBuilder(), floors=new StringBuilder();
			int w=0, f=0; 
			boolean wall=!maze[i][0].contains(MazeGeneration.Direction.RIGHT), floor=!maze[i][0].contains(MazeGeneration.Direction.DOWN);
			walls.append((wall?1:0));
			floors.append((floor?1:0));
			for (int j=0; j<maze[i].length; j++) {
				if (wall!=maze[i][j].contains(MazeGeneration.Direction.RIGHT)) 
					w++;
				else {
					walls.append(w>9?"["+w+"]":w);
					wall=!maze[i][j].contains(MazeGeneration.Direction.RIGHT);
					w=1;
				}
				if (i==maze.length-1) continue;
				if (floor!=maze[i][j].contains(MazeGeneration.Direction.DOWN)) 
					f++;
				else {
					floors.append(f>9?"["+f+"]":f);
					floor=!maze[i][j].contains(MazeGeneration.Direction.DOWN);
					f=1;
				}
			}
			if (w!=1) walls.append(w-1);
			floors.append(f);
			wr.write(walls.toString());
			if (i!=maze.length-1) { 
				wr.write('/');
				wr.write(floors.toString());
				wr.write('/');
			}
			
		}
		wr.close();
	}
}
