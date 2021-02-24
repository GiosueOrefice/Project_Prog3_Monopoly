package Mediator;
public abstract class AbstractPlayer{
	/**
	*
	* @version n.n (24-02-2021)
	* @author Giosue' Orefice
	*/
	
	protected Mediator mediator; 
    protected String name; 
    protected int price; 
    protected int money;
  
    /**
	Scopo del metodo: Effettuare l'offeta
	@param price prezzo del'offerta
	@return void
	*/
    public abstract void bid(int price); 
    
    /**
   	Scopo del metodo: Ritirarsi dall'asta
   	@param 
   	@return void
   	*/
    public abstract void cancelTheBid(); 
    
    public String getName() {
    	return this.name;
    }
}
