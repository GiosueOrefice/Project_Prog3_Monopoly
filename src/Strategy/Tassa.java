package Strategy;
import java.awt.Color;

import Mediator.Player;
import Model.Banca;
/**
*
* @version n.n (24-02-2021)
* @author Giosue' Orefice
*/

public class Tassa extends Terreno{
	private int spesaTassa;
	
	public Tassa(String name,int spesaTassa, Color color) {
		super(name,color);
		this.spesaTassa = spesaTassa;
	}

	/**
	   @see Strategy.Terreno#esegui 
	   Quando il giocatore si ferma su un terreno TASSA deve pagare il prezzo della tassa alla banca
		
	   @param player il giocatore che si trova sulla casella tassa
	   @return void
	*/
	@Override
	public void esegui(Player player) {
		Banca.riscuoti(player, spesaTassa);	
	}
	
}
