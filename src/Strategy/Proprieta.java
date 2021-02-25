package Strategy;
import java.awt.Color;
import javax.swing.JOptionPane;

import Mediator.Player;
import Model.Banca;
/**
*
* @version n.n (24-02-2021)
* @author Giosue' Orefice
*/
public class Proprieta extends Terreno {

	private int costoAcquisto;
	private int guadagnoAffitto;
	private int costoCasa;
	private int costoAlbergo;
	private int guadagnoCasa;
	private int guadagnoAlbergo;
	private boolean acquistata;
	private Player player;
	private int numCase;
	private boolean albergo;
	private boolean checkCostruire;

	public Proprieta(String name, int costoAcquisto, int costoAffitto,Color color, int costoCasa, 
			int costoAlbergo, int guadagnoAlbergo, int guadagnoCasa) {
		super(name,color);
		this.setCostoAcquisto(costoAcquisto);
		this.setGuadagnoAffitto(costoAffitto);
		this.setAcquisto(false);
		this.costoAlbergo = costoAlbergo;
		this.costoCasa = costoCasa;
		this.albergo = false;
		this.numCase = 0;
		this.guadagnoAlbergo = guadagnoAlbergo;
		this.guadagnoCasa = guadagnoCasa;
		this.checkCostruire=false;
	}

	/**
	   @see Strategy.Terreno#esegui 
	   Quando il giocatore si ferma su una proprieta' libera, il giocatore
		puo' acquistarla dalla Banca, pagando il prezzo indicato sulla casella e riportato
		anche sul contratto, altrimenti la proprieta viene subito messa all'asta e ceduta
		al miglior offerente.
		
		Se la proprieta gia' è stata acquistata da un altro giocatore allora bisogna pagare 
		l'affitto al propietario 
		
	   @param player il giocatore che si trova su questa proprieta
	   @return 
	*/
	@Override
	public void esegui(Player player) {

		if(acquistata) {
			if (!this.player.equals(player)) 
				Banca.pagaAffitto(player, this);	

			else 
				Banca.setMessage("Sei finito su una tua proprieta");
		}
		else askBuy(player);
	}
	
	/**
	Le rendite o gli affitti aumentano in ragione delle case e degli
	alberghi che vengono eretti sul terreno.
	@param //
	@return int che indica il prezzo da pagare
	*/
	public int prezzoDaPagare()
	{
		if(!albergo)
			return guadagnoAffitto + (numCase*guadagnoCasa);
		else 
			return guadagnoAffitto +  guadagnoAlbergo;
	}
	
	
	/**
	Scopo del metodo: Chiedere al giocatore se vuole acquistare la proprieta, a seconda della risposta
	verranno eseguite operazione diverse
	@param player giocatore a cui chiedere se vuole comprare la proprieta
	@return 
	*/
	public void askBuy(Player player)
	{
		Object[] possibleValues2 = { "SI", "NO" };
		Object sel_input2 = JOptionPane.showInputDialog(null,"Vuoi comprare " + getName() + " per " + 
				costoAcquisto + "€ ?", "COMPRARE",
				JOptionPane.INFORMATION_MESSAGE, null, possibleValues2, possibleValues2[0]);
		try {
			String input = sel_input2.toString();
			if(input.equals("SI"))
				Banca.buyProprieta(player, this);
			
			else
				Banca.asta(this);
		}
		catch(NullPointerException e) {
			Banca.asta(this);
		}
	}



	public int getCostoAcquisto() {
		return costoAcquisto;
	}


	public void setCostoAcquisto(int costoAcquisto) {
		this.costoAcquisto = costoAcquisto;
	}


	public boolean isAcquisto() {
		return acquistata;
	}


	public void setAcquisto(boolean acquisto) {
		this.acquistata = acquisto;
	}


	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getCostoCasa() {
		return costoCasa;
	}

	public void setCostoCasa(int costoCasa) {
		this.costoCasa = costoCasa;
	}

	public int getCostoAlbergo() {
		return costoAlbergo;
	}

	public void setCostoAlbergo(int costoAlbergo) {
		this.costoAlbergo = costoAlbergo;
	}

	public boolean isAcquistata() {
		return acquistata;
	}

	public void setAcquistata(boolean acquistata) {
		this.acquistata = acquistata;
	}

	public int getNumCase() {
		return numCase;
	}

	public void setNumCase(int numCase) {
		this.numCase = numCase;
	}

	public boolean isAlbergo() {
		return albergo;
	}

	public void setAlbergo(boolean albergo) {
		this.albergo = albergo;
	}

	public int getGuadagnoAffitto() {
		return guadagnoAffitto;
	}

	public void setGuadagnoAffitto(int guadagnoAffitto) {
		this.guadagnoAffitto = guadagnoAffitto;
	}

	public int getGuadagnoCasa() {
		return guadagnoCasa;
	}

	public void setGuadagnoCasa(int guadagnoCasa) {
		this.guadagnoCasa = guadagnoCasa;
	}

	public int getGuadagnoALbergo() {
		return guadagnoAlbergo;
	}

	public void setGuadagnoALbergo(int guadagnoALbergo) {
		this.guadagnoAlbergo = guadagnoALbergo;
	}

	public boolean isCheckCostruire() {
		return checkCostruire;
	}

	public void setCheckCostruire(boolean checkCostruire) {
		this.checkCostruire = checkCostruire;
	}

}
