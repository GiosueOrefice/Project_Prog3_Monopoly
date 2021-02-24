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
	private int numBuyers=0;
	public ConcreteMediator() { 
		buyers = new ArrayList<>(); 

	} 


	@Override
	public void addBuyer(AbstractPlayer buyer) { 
		buyers.add(buyer); 
		numBuyers += 1;
	} 

	@Override
	public void eseguiAsta() { 	
		int index,price=0,number;
		ArrayList<AbstractPlayer> playersAsta = new ArrayList<AbstractPlayer>();
		for(index=0; index<numBuyers;index++) {
			buyers.get(index).price=0;
			if(buyers.get(index).money>0)
				playersAsta.add(buyers.get(index));
		}

		int numPartecipanti = playersAsta.size();
		index = 0;
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

				if (price>= number) {
					JOptionPane.showMessageDialog (null,
							"IL VALORE DELLA PUNTATA DEVE ESSERE MAGGIORE DI " + price,
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
				price = number;


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
