package View;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
/**
*
* @version n.n (24-02-2021)
* @author Giosue' Orefice
*/
public class PanelDadi extends JPanel {
	private int result;
	public PanelDadi(int resultDado) {
		this.result = resultDado;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.GREEN);
		g.fillRect(0,0, 118,128);
		
		g.setColor(Color.black);
		g.drawRect(54,59, 31,31);

		g.setColor(Color.white);
		g.fillRect(55, 60, 30, 30);
		
		g.setColor(Color.black);

		switch(result) {
		case 1: {
			g.fillOval(68, 73, 4, 4);
			break;}
		case 2: {
			g.fillOval(60, 65, 4, 4);
			g.fillOval(77, 80, 4, 4);
			break;}
		case 3: {
			g.fillOval(60, 65, 4, 4);
			g.fillOval(77, 80, 4, 4);
			g.fillOval(68, 72, 4, 4);
			break;}
		case 4: {
			g.fillOval(60, 65, 4, 4);
			g.fillOval(60, 80, 4, 4);
			g.fillOval(77, 80, 4, 4);
			g.fillOval(77, 65, 4, 4);
			break;}
		case 5: {
			g.fillOval(60, 65, 4, 4);
			g.fillOval(60, 80, 4, 4);
			g.fillOval(77, 80, 4, 4);
			g.fillOval(77, 65, 4, 4);
			g.fillOval(68, 73, 4, 4);
			break;
			
		}
		case 6:{
			g.fillOval(60, 65, 4, 4);
			g.fillOval(60, 80, 4, 4);
			g.fillOval(77, 80, 4, 4);
			g.fillOval(77, 65, 4, 4);
			g.fillOval(60, 73, 4, 4);
			g.fillOval(77, 73, 4, 4);
			break;
		}
		}

	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
}



