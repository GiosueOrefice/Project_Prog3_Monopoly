package Mediator;
/**
*
* @version n.n (24-02-2021)
* @author Giosue' Orefice
*/
public interface Mediator { 
    public void addBuyer(AbstractPlayer buyer); 
    public void eseguiAsta();
    public AbstractPlayer findHighestBidder(); 
} 
