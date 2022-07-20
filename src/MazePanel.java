import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MazePanel {
	private JFrame frame;
	private int[] screensize={(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()};
	
	public MazePanel(HashMap<Boolean, HashSet<ArrayList<Integer>>> map, int depth, int width, int height) {
		frame=new JFrame();
		frame.setSize(screensize[0], screensize[1]);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				g.setColor(Color.gray);
				g.fillRect(0, 0, screensize[0], screensize[1]);
				g.setColor(Color.black);
				HashSet<ArrayList<Integer>> temp=map.get(false);
				
				for (ArrayList<Integer> h:temp) {
					g.fillRect(screensize[0]/2-width/2+h.get(0), (screensize[1]-120)/2-height/2+h.get(1), depth, depth/10);
				}
				temp=map.get(true);
				for (ArrayList<Integer> v:temp)  {
					g.fillRect(screensize[0]/2-width/2+v.get(0), (screensize[1]-120)/2-height/2+v.get(1), depth/10, depth);
				}
			}
		};
		canvas.setFocusable(true);
		
		frame.add(canvas);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frame.getContentPane().repaint();
	}
	
	public static void main(String[] args) {
		MazeGeneration m=new MazeGeneration(15, new int[]{0,0});
		int n=50;
		MazePanel mp=new MazePanel(m.DirectionToCoords(n), n, m.width*n, m.height*n);
	}
}
