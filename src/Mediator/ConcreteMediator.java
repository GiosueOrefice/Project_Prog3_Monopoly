package Mediator;
import java.util.ArrayList;

import javax.swing.JOptionPane;
/**
 *
 * @version n.n (24-02-2021)
 * @author Giosue' Orefice
 */

public class ConcreteMediator implements Mediator {

	private ArrayList<AbstractPlayer> buyers; 
	public ConcreteMediator() { 
		buyers = new ArrayList<>(); 

	} 


	@Override
	public void addBuyer(AbstractPlayer buyer) { 
		buyers.add(buyer); 
	} 

	@Override
	public void eseguiAsta() { 	
		int index,maxPrice,number;
		ArrayList<AbstractPlayer> playersAsta = new ArrayList<AbstractPlayer>();
		for(AbstractPlayer buyers : buyers) {
			buyers.price=0;
			if(buyers.money>0)
				playersAsta.add(buyers);
		}

		int numPartecipanti = playersAsta.size();
		index = 0;
		maxPrice = 0;
		while (numPartecipanti >1) {
			try {
				String input=JOptionPane.showInputDialog(null," " + playersAsta.get(index).getName() + 
						" quanto offri?", "ASTA",JOptionPane.QUESTION_MESSAGE);

				if(input == null) {
					numPartecipanti--;
					playersAsta.get(index).cancelTheBid();
					playersAsta.remove(index);
					if(index==numPartecipanti)
						index=0;
					continue;
				}
				number = Integer.parseInt(input);

				if (maxPrice>= number) {
					JOptionPane.showMessageDialog (null,
							"IL VALORE DELLA PUNTATA DEVE ESSERE MAGGIORE DI " + maxPrice,
							"Attenzione",
							JOptionPane.ERROR_MESSAGE);
					continue;
				}

				if(number>playersAsta.get(index).money) {
					JOptionPane.showMessageDialog (null,
							"NON HAI SUFFICIENTEMENTE DENARO PER FARE QUESTA PUNTATA",
							"Attenzione",
							JOptionPane.ERROR_MESSAGE);
					continue;
				}
				playersAsta.get(index).bid(number);
				maxPrice = number;


				if(++index >= numPartecipanti)
					index = 0;

			} catch (NumberFormatException e) {}
		}


		if(playersAsta.get(0).price <= 0)
			playersAsta.get(0).price = 1;

		JOptionPane.showMessageDialog (null, " " + playersAsta.get(0).getName() + " vince l'asta  con il prezzo di "
				+  playersAsta.get(0).price + "€",
				"ASTA TERMINATA", JOptionPane.INFORMATION_MESSAGE);

	}

	@Override
	public AbstractPlayer findHighestBidder() { 
		int maxBid = 0; 
		AbstractPlayer winner = null; 
		for (AbstractPlayer b : buyers) { 
			if (b.price > maxBid) { 
				maxBid = b.price; 
				winner = b; 
			} 
		} 
		return winner;
	} 

}
