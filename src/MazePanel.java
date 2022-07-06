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
	
	public MazePanel(HashMap<Boolean, HashSet<ArrayList<Integer>>> map, int depth) {
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
					System.out.print(h.get(0)+", "+h.get(1)+", ");
					g.fillRect(h.get(0), h.get(1), depth, depth/10);
				}
				temp=map.get(true);
				for (ArrayList<Integer> v:temp)  {
					g.fillRect(v.get(0), v.get(1), depth/10, depth);
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
		new MazePanel(new MazeGeneration(15).DirectionToCoords(50), 50);
	}
}
