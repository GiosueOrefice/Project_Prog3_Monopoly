package Model;
import Mediator.Player;
import Monopoly.Utily;
import Strategy.Board;
import View.PanelTerreno;
import Strategy.Proprieta;

/**
 *
 * @version n.n (24-02-2021)
 * @author Giosue' Orefice
 */
public class StateGame {

	private int n;
	private Board board;
	private Player[] players;
	private int numGiocatoriInGara;
	private int currenPlayer;
	private int winner;

	//Costruttore per memorizzare o ripristinare lo stato di una partita 
	public StateGame(int n, int winner,Player [] players, int numGiocatori, int i,Board board) {
		this.n = n;
		this.winner = winner;
		this.board = board;
		this.players = new Player[n];
		for(int j=0;j<n;j++)
			this.players[j] = (Player) players[j].clone();

		setPanel(board);
		setProprieta(board);
		this.currenPlayer=i;
		this.numGiocatoriInGara = numGiocatori;
	}

	//Costruttore per creare lo stato della partita iniziale
	public StateGame(int n) {
		this.n = n;
		this.winner = -1;
		this.board = new Board(n);
		this.players = new Player[n];
		for(int j=0;j<n;j++)
			this.players[j] = new Player("Player"+(j+1));
		setPanel(board);
	
		this.currenPlayer=0;
		this.numGiocatoriInGara=n;

	}

	/**
	Scopo del metodo: Settare la rappresentazione grafica di una partita da uno stato
	@param board 
	@return void
	 */
	private void setPanel(Board board) {
		for(int j=0;j<board.getNumPanel();j++) {
			if(board.getSquarePanel(j) instanceof PanelTerreno) {
				PanelTerreno p = (PanelTerreno) board.getSquarePanel(j);
				for(int k=0;k<n;k++) 
					if(j == Utily.conversioneGrafica(players[k].getLocation())) 
						p.setGiocatori(k, true);

			}

		}
	}

	private void setProprieta(Board board) {
		for(int j=0;j<board.getNumTerreni();j++) {
			if(board.getSquare(j) instanceof Proprieta) {
				Proprieta p = (Proprieta) board.getSquare(j);
				if(p.getPlayer()!=null) {
					Player player = p.getPlayer();
					player.setProprieta(p);
				}
			}
		}
	}

	public int getN() {
		return n;
	}

	public Board getBoard() {
		return board;
	}

	public Player[] getPlayers() {
		return players;
	}


	public int getNumGiocatoriInGara() {
		return numGiocatoriInGara;
	}


	public int getCurrentPlayer() {
		return currenPlayer;
	}

	public int getWinner() {
		return winner;
	}

	public int getCurrenPlayer() {
		return currenPlayer;
	}

	public void setCurrenPlayer(int currenPlayer) {
		this.currenPlayer = currenPlayer;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public void setNumGiocatoriInGara(int numGiocatoriInGara) {
		this.numGiocatoriInGara = numGiocatoriInGara;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}
	
	public void setAll(int n, int winner,Player [] players, int numGiocatori, int i,Board board) {
		this.n = n;
		this.winner = winner;
		this.board = board;
		this.players = players;
		this.currenPlayer=i;
		this.numGiocatoriInGara = numGiocatori;
	}

}
