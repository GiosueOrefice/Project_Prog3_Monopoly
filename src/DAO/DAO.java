package DAO;
import java.util.ArrayList;

import Mediator.Player;
import Model.StateGame;
/**
*
* @version n.n (24-02-2021)
* @author Giosue' Orefice
*/
public interface DAO {
	/**
	Scopo del metodo: inserire nel DB lo stato di una partita
	@param state Stato del gioco da salvare
	*/
	public void insert(StateGame state);
	/**
	Scopo del metodo: aggiornare nel DB lo stato di una partita
	*/
	public default void update(StateGame state) {};
	/**
	Scopo del metodo: eliminare nel DB lo stato di una partita
	@param idGame int
	*/
	public default void delete(int idGAME) {};
	/**
	Scopo del metodo: Trovare tutti i vincitori delle partite
	@return ArrayList<Player> 
	*/
	public ArrayList<Player> findAllWinner();
	
	/**
	Scopo del metodo: trovare una partita salvata
	@param id intero che indica lo stato della partita da cercare
	@return StateGame
	*/
	public StateGame findGame(int id);

}
