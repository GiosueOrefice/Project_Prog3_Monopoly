package View;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import Strategy.Proprieta;
import Strategy.Terreno;

/**
*
* @version n.n (24-02-2021)
* @author Giosue' Orefice
*/
public class PanelTerreno extends JPanel {
	private Terreno terreno;
	private boolean[] giocatori;
	
	public PanelTerreno(Terreno terreno,int gio) {
		this.terreno = terreno;
		giocatori = new boolean[gio];
		for(int j=0;j<gio;j++)
			giocatori[j]=false;
	}
	
	/**
	Scopo del metodo: Rappresentazione grafica dei vari terreni
	@param g 
	@return void
	*/
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(terreno.getColor());
		// white, gray, lightGray, darkGray
		// red, green, yellow, pink, etc. etc.
		g.fillRect(0,0, 118,30);
		g.setColor(Color.black);
		g.drawRect(0,0, 117,127);
		g.setColor(Color.black);

		g.drawRect(0,0, 117,30);
		g.setColor(Color.black);
		g.drawString(terreno.getName(),4,19);

		for(int i=0; i<giocatori.length;i++) {
			if(i<4 && giocatori[i]==true) {
				g.drawOval((5+(i*20))+i, 70, 20, 20);
				g.setColor(Color.black);
				g.drawString(""+(i+1), (5+(i*20))+i+6, 85);
			}
			else if (giocatori[i]) {
				g.drawOval((5+((i-4)*20))+i-4, 95, 20, 20);
				g.setColor(Color.black);
				g.drawString(""+(i+1), (5+((i-4)*20))+i+4, 110);
			}

		}
		if(terreno instanceof Proprieta) {
			Proprieta p = (Proprieta) terreno;
			if(!p.isAlbergo()) {
				for(int i=0; i<p.getNumCase();i++) {

					g.setColor(Color.RED.darker());
					g.fillRect((5+(i*20))+i+1, 41, 19, 19);
					g.setColor(Color.white);
					g.drawString("C", (5+(i*20))+i+6, 55);
				}
			}
			else {
				g.setColor(Color.GREEN.darker());
				g.fillRect(6, 41, 25, 19);
				g.setColor(Color.white);
				g.drawString("A", 15, 55);
			}
		}
	}

	public Terreno getTerreno() {
		return terreno;
	}
	
	public void setTerreno(Terreno terreno) {
		this.terreno = terreno;
	}
	
	public boolean[] getGiocatori() {
		return giocatori;
	}
	
	public void setGiocatori(int index,boolean giocatore) {
		this.giocatori[index] = giocatore;
	}


}



