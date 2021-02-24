package Strategy;
import java.awt.Color;

import Mediator.Player;
import Model.Banca;
/**
*
* @version n.n (24-02-2021)
* @author Giosue' Orefice
*/
public class Via extends Terreno {
	int soldiVia;
	public Via(String name, int soldi, Color color) {
		super(name,color);
		this.soldiVia = soldi;
	}
	/**
	   @see (default package).Terreno#esegui 
	   Quando il giocatore si ferma sulla casella VIA la banca versa al giocatore un quantitativo di denaro 
		
	   @param player il giocatore che si trova sul terreno VIA 
	   @return void
	*/
	@Override
	public void esegui(Player player) {
		Banca.versa(player, this.soldiVia);
		
		
	}

}
