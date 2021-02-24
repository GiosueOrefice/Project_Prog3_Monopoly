package Strategy;
import java.awt.Color;

import Mediator.Player;
/**
* Classe astratta per le varie caselle/terreni del gioco
* @version n.n (24-02-2021)
* @author Giosue' Orefice
*/
public abstract class Terreno {

	private String name;
	private Color color;
	
	public Terreno(String name,Color color)
	{
		this.name = name; 
		this.color = color;
	}

	public String getName()
	{
		return name;
	}

	public Color getColor() {
		return color;
	}
	
	/**
	 * Questo metodo viene invocato quando un giocatore dopo aver girato i dadi finisce su un determinato terreno
	 * 
		@param player il giocatore che si trova sul terreno
		@return void
	*/
	public abstract void esegui(Player player);


}
