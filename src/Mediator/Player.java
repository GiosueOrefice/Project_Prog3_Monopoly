package Mediator;
import java.util.ArrayList;
import Strategy.Proprieta;
import Model.Banca;
/**
*
* @version n.n (24-02-2021)
* @author Giosue' Orefice
*/
public class Player extends AbstractPlayer implements Cloneable
{

	private int location; 
	private boolean prigione;
	private int turniPrigione;
	private boolean costruzione;
	private ArrayList<Proprieta> proprieta;
	private Mediator mediator;
	private String nick;


	public Player(String name)
	{
		super.name = name;
		this.location = 0;
		this.prigione = false;
		nick = name;
		proprieta = new ArrayList<Proprieta>();
	}


	/**
	* Il metodo viene invocato quando si acquista una nuova proprieta
	* e serve per capire se il giocatore può iniziare a costruire su una proprieta
	* 
	* @param property ultima proprieta acquistata
	* @return boolean costruzione se questo valore è true allora il giocatore può iniziare a costruire
	*/
	public boolean checkCostruire(Proprieta property) {
		int cont=0;
		for(Proprieta proprieta: proprieta) {
			if(proprieta.getColor() == property.getColor())
				cont++;
		}
		if (cont==2) {
			this.costruzione=true;
			for(Proprieta proprieta: proprieta) {
				if(proprieta.getColor() == property.getColor())
					proprieta.setCheckCostruire(true);
			}
		}


		return costruzione;
	}

	/**
	* Il metodo viene invocato quando il giocatore deve costruire su una proprieta
	*
	* @param property ultima proprieta acquistata
	*/
	public void costruisci(Proprieta property) {

		if(property.getNumCase()<4) 
			Banca.costruisciCasa(property);
		
		else {
			property.setNumCase(0);
			property.setAlbergo(true);
			Banca.costruisciAlbergo(property);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		Player q = (Player)o;
		return this.name.equals(q.getName());
	}
	
	@Override
	public void bid(int price) { 
		this.price = price; 
	} 

	@Override
	public void cancelTheBid() { 
		this.price = -1; 
	} 
	
	/**
	* Il metodo viene invocato quando si vogliono ottenere le statistiche di un giocatore
	*
	* @return String  Statistiche del giocatore 
	*/
	public String getStatistiche() {
		String p = "Proprieta: ";
		for(Proprieta proprieta: proprieta) {
			p = p + proprieta.getName() + " - ";
		}
		return "SOLDI: " + money + "\n" +  p +
				"\nPOSIZIONE: "; 
	}
	
	public int getMoney()
	{
		return money;
	}

	public int getLocation()
	{
		return location;
	}

	public String getName() {
		return nick;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public boolean isPrigione() {
		return prigione;
	}

	public void setPrigione(boolean prigione) {
		this.prigione = prigione;
	}

	public int getTurniPrigione() {
		return turniPrigione;
	}

	public void setTurniPrigione(int turniPrigione) {
		this.turniPrigione = turniPrigione;
	}

	public boolean isCostruzione() {
		return costruzione;
	}

	public void setCostruzione(boolean costruzione) {
		this.costruzione = costruzione;
	}

	public ArrayList<Proprieta> getProprieta() {
		return proprieta;
	}

	public void setProprieta(Proprieta proprieta) {
		this.proprieta.add(proprieta);
	}

	public Mediator getMediator() {
		return mediator;
	}

	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

}