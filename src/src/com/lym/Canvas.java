package src.com.lym;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Surface extends JPanel {

	private static final long serialVersionUID = 1L;

	private void doDrawing(Graphics g){
		
		Graphics g2d = (Graphics2D) g;
		ArrayList<Node> nodes = Main.nodes;
		int x, y;
		int size = 200;
		
		/* legend in the top left*/
		
		g.setColor(Color.BLACK);
		g.fillRect(16, 2, 106, 50);
		g.setColor(Color.GRAY);
		g.fillRect(18, 4, 102, 46);
		
		g.setColor(Color.GREEN);
		g2d.drawString("Green: has token", 20, 15);
		g.setColor(Color.CYAN);
		g2d.drawString("Cyan: has frame", 20, 26);
		g.setColor(Color.ORANGE);
		g2d.drawString("Orange: is waiting ", 20, 37);
		g.setColor(Color.WHITE);
		g2d.drawString("White: is idle", 20, 48);
		
		/* set the node position */
		for(int i = 0; i < nodes.size(); i++){
			if(i < nodes.size()/2 ){
				x = (i * size) + size / 2;
				y = size / 2;
			}
			else{
				x = ((nodes.size() - i) * size) - size / 2;
				y = size + (size / 2);
			}
			
			/* colour the nodes based on their status */
			if(nodes.get(i).tokenLock){
				g.setColor(Color.ORANGE);
			}else if(nodes.get(i).hasToken){
				g.setColor(Color.GREEN);
			}else if(nodes.get(i).hasFrame){
				g.setColor(Color.CYAN);
			}else{
				g.setColor(Color.WHITE);
			}
			
			g.fillRect(x, y, size / 2, size / 2);
			
			g.setColor(Color.BLACK);
			g.drawRect(x, y, size / 2, size / 2);
			
			/* draw information about the node */
			g2d.drawString("Node: " + i, x + 5, y + 15);
			g2d.drawString("Port: " + nodes.get(i).port, x + 5, y + 25);
			g2d.drawString(nodes.get(i).status, x + 5, y + 35);

		}
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		doDrawing(g);
	}
	
}

public class Canvas extends JFrame{
	
	private static final long serialVersionUID = 1L;
	public static Surface window;
	
	public Canvas(){
		
		initUI();
	}
	
	private void initUI(){
		setTitle("Token Ring");
		add(window = new Surface());
		setSize(1000, 600);// 900, 750
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
}