package Strategy;
import java.awt.Color;

import javax.swing.JPanel;

import Mediator.Player;
import Monopoly.Utily;
import View.PanelDadi;
import View.PanelTerreno;
/**
*
* @version n.n (24-02-2021)
* @author Giosue' Orefice
*/

public class Board {
	private int numTerreni;
	private int numPanel;
	private Terreno[] terreno;
	private JPanel[] terrenoG;
	public Board(int n){
		numTerreni = 12;
		numPanel = (int) Math.pow(((numTerreni/4)+1), 2);
		
		terreno = new Terreno[numTerreni];
		terrenoG = new JPanel[numPanel];
		
		terreno[0] = new Via("VIA",200,Color.WHITE);
		terreno[1] = new Proprieta("Milano",100,20,Color.red,50,65,30,100);
		terreno[2] = new Proprieta("Napoli",110,21,Color.red,50,65,30,110);
		terreno[3] = new Tassa("Tassa Patrimoniale",200,Color.LIGHT_GRAY);
		terreno[4] = new Proprieta("Salerno",120,25,Color.yellow,60,75,40,120);
		terreno[5] =  new Proprieta("Avellino",125,27,Color.yellow,60,75,40,130);
		terreno[6] = new  Prigione("Prigione",Color.gray);
		terreno[7] = new  Proprieta("Roma",130,38,Color.CYAN,70,85,50,140);
		terreno[8] = new Proprieta("Rimini",140,48,Color.CYAN,70,85,57,140);
		terreno[9] = new Tassa("Tassa Monopoly",250,Color.LIGHT_GRAY); 
		terreno[10] = new Proprieta("Torino",160,54,Color.MAGENTA,80,65,70,150);
		terreno[11] = new Proprieta("Caserta",160,62,Color.MAGENTA,80,75,70,170);
		
		terrenoG[Utily.conversioneGrafica(0)] = new PanelTerreno(terreno[0],n);
		terrenoG[Utily.conversioneGrafica(1)] = new PanelTerreno(terreno[1],n);
		terrenoG[Utily.conversioneGrafica(2)] = new PanelTerreno(terreno[2],n);
		terrenoG[Utily.conversioneGrafica(3)] = new PanelTerreno(terreno[3],n);
		terrenoG[Utily.conversioneGrafica(4)] = new PanelTerreno(terreno[4],n);
		terrenoG[Utily.conversioneGrafica(5)] = new PanelTerreno(terreno[5],n);
		terrenoG[Utily.conversioneGrafica(6)] = new PanelTerreno(terreno[6],n);
		terrenoG[Utily.conversioneGrafica(7)] = new PanelTerreno(terreno[7],n);
		terrenoG[Utily.conversioneGrafica(8)] = new PanelTerreno(terreno[8],n);
		terrenoG[Utily.conversioneGrafica(9)] = new PanelTerreno(terreno[9],n);
		terrenoG[Utily.conversioneGrafica(10)] = new PanelTerreno(terreno[10],n);
		terrenoG[Utily.conversioneGrafica(11)] = new PanelTerreno(terreno[11],n);
		terrenoG[9] = new PanelDadi(1);
		terrenoG[10] = new PanelDadi(1);
		terrenoG[5] = new JPanel();
		terrenoG[5].setBackground(Color.GREEN);
		terrenoG[6] = new JPanel();
		terrenoG[6].setBackground(Color.GREEN);
		
	}

	/**
	Scopo del metodo: Restituire il terreno di un determinato indice
	@param num indice del terreno
	@return Terreno
	*/
	public Terreno getSquare(int num){
		return terreno[num];
	}
	
	/**
	Scopo del metodo: Restituire il JPanel di un determinato indice
	@param num indice del JPanel
	@return JPanel
	*/
	public JPanel getSquarePanel(int num){
		return terrenoG[num];
	}

	/**
	Scopo del metodo: Eseuire l'azione del terreno su cui è atterrato il giocatore
	@param num indice del terreno
	@param player 
	@return void
	*/
	public void playerLanded(Player player) {
		terreno[player.getLocation()].esegui(player);
	}
	
	/**
	Scopo del metodo: Restituire l'indice di un terreno con un determinato nome
	@param name nome del terreno
	@return int -1 se non è presente nessun terreno con quel nome
	*/
	public int getSquare(String name) {
		for(int i=0;i<12;i++) {
			if(name.equals(terreno[i].getName()))
				return i;
		}
		
		return -1;
	}

	/**
	Scopo del metodo: Caclolare la posizione di un giocatore dopo aver lanciato i dadi
	@param num1 valore del primo dado
	@param num1 valore del secondo dado
	@return int posizione del giocatore aggiornata
	*/
	public int calcolaPosizione(int num1,int num2,Player player) {
		if(player.getLocation()+num1+num2>11)
			terreno[0].esegui(player);
		player.setLocation((player.getLocation()+num1+num2)%numTerreni); 

		return player.getLocation();
	}


	public void setSquarePanel(JPanel terrenoG, int num) {
		this.terrenoG[num] = terrenoG;
	}
	
	public void setSquarePanel(PanelTerreno terrenoG, int num) {
		this.terrenoG[num] = terrenoG;
	}
	
	public void setSquarePanel(PanelDadi terrenoG, int num) {
		this.terrenoG[num] = terrenoG;
	}

	public int getNumTerreni() {
		return numTerreni;
	}

	public void setNumTerreni(int numTerreni) {
		this.numTerreni = numTerreni;
	}

	public int getNumPanel() {
		return numPanel;
	}

	public void setNumPanel(int numPanel) {
		this.numPanel = numPanel;
	}

}