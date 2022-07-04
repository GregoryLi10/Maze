import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;

public class MazeLoader {

	private FileReader fr;
	
	public HashSet<MazeGeneration.Direction>[][] load(String filename) throws IOException {
		fr=new FileReader(filename);
		ArrayList<Entry<Boolean, ArrayList<Integer>>> list=new ArrayList<Entry<Boolean, ArrayList<Integer>>>(); 
		for (int temp=fr.read(); temp!=-1; temp=fr.read()) { //reads file and stores into a list of first bool and alternating numbers after
			ArrayList<Integer> row=new ArrayList<Integer>();
			for (int i=fr.read(); i!=(int)'/'&&i!=-1; i=fr.read()) 
				row.add(i-48);
			list.add(new AbstractMap.SimpleEntry<Boolean, ArrayList<Integer>>(temp-48==1, row)); //uses map entry to temporarily store info
		}
		System.out.println(list);
		HashSet<MazeGeneration.Direction>[][] maze=new HashSet[list.size()/2+1][list.get(0).getValue().stream().mapToInt(Integer::intValue).sum()+1];
		for (int i=0; i<maze.length; i++) { //fills in maze for drawing 
			boolean wall=list.get(i*2).getKey();
			int indw=1, w=list.get(i*2).getValue().get(0);
			for (int j=0; j<maze[i].length; j++) {
				if (j==maze[i].length-1) {
					maze[i][j]=new HashSet<MazeGeneration.Direction>();
					break;
				}
				if (j>=w) {
					w+=list.get(i*2).getValue().get(indw);
					indw++;
					wall=!wall;
				}
				maze[i][j]=wall?new HashSet<MazeGeneration.Direction>():new HashSet<MazeGeneration.Direction>(Arrays.asList(MazeGeneration.Direction.RIGHT));
			}
			if (i==maze.length-1) break;
			boolean floor=list.get(i*2+1).getKey();
			int indf=1, f=list.get(i*2+1).getValue().get(0);
			for (int j=0; j<maze[i].length; j++) {
				if(j>=f) {
					f+=list.get(i*2+1).getValue().get(indf);
					indf++;
					floor=!floor;
				}
				if (!floor)
					maze[i][j].add(MazeGeneration.Direction.DOWN);
			}
		}
		for (int i=maze.length-1; i>=0; i--) { //fills the rest of up and left directions for each cell
			for (int j=maze[i].length-1; j>=0; j--) {
				if (i!=0&&maze[i-1][j].contains(MazeGeneration.Direction.DOWN)) 
					maze[i][j].add(MazeGeneration.Direction.UP);
				if (j!=0&&maze[i][j-1].contains(MazeGeneration.Direction.RIGHT))
					maze[i][j].add(MazeGeneration.Direction.LEFT);
				
			}
		}
		return maze;
	}
}
