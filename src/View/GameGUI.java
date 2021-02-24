package View;
import javax.swing.*;

import DAO.DAO;
import DAO.DerbyDB;
import Mediator.Mediator;
import Mediator.Player;
import Model.Banca;
import Model.Dado;
import Model.StateGame;
import Strategy.Board;
import Strategy.Proprieta;
import Monopoly.Utily;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
*
* @version n.n (24-02-2021)
* @author Giosue' Orefice
*/
public class GameGUI extends JFrame {  

	private int n;
	private Board board; 
	private Player[] players;
	private Dado uno,due;
	private int pos,currentPlayer,numGiocatoriInGara;
	private int winner;
	private JButton dadiButton;
	private JButton salvaButton;
	private JButton oldWinButton;
	private JButton nextButton;
	private JButton costruisciButton;
	private JLabel giocatoreLabel;
	private JTextArea giocatoreSTATS;
	private JPanel panel1;
	private JTextArea bancaSTATS;
	private PanelDadi dadoPanelUno,dadoPanelDue;
	private StateGame state;
	private DAO db;

	
	public GameGUI(StateGame state) {
		super("MONOPOLY");
		this.state = state;
		this.n= state.getN();
		this.currentPlayer = state.getCurrentPlayer();
		
		this.board = state.getBoard();
		this.numGiocatoriInGara =state.getNumGiocatoriInGara();
		this.players = state.getPlayers();
		this.winner = state.getWinner();
		uno = new Dado();
		due = new Dado();
		dadoPanelUno = new PanelDadi(1);
		dadoPanelDue = new PanelDadi(1); 
		
		db = DerbyDB.getInstance();
		
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(4,0));

		for(int j=0;j<16;j++) 
			panel1.add(board.getSquarePanel(j));

	}


	public void play() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame jframe = new JFrame("MONOPOLY");
				jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);

				dadoPanelUno = (PanelDadi) board.getSquarePanel(9);
				dadoPanelDue = (PanelDadi) board.getSquarePanel(10);

				JPanel panel2 = new JPanel();
				giocatoreLabel = new JLabel();
				panel2.add(giocatoreLabel,BorderLayout.NORTH);

				giocatoreSTATS = new JTextArea("STATISTICHE GIOCATORE",5,45);
				panel2.add(giocatoreSTATS,BorderLayout.CENTER);
				bancaSTATS = new JTextArea("OPERAZIONI",6,45);
				panel2.add(bancaSTATS,BorderLayout.CENTER);

				dadiButton = new JButton ("Gira i dadi");
				salvaButton = new JButton ("Salva la partita");
				nextButton = new JButton ("Prossimo Turno");
				costruisciButton = new JButton ("Costruisci");
				oldWinButton = new JButton ("Vincitori");
				panel2.add(dadiButton); 
				panel2.add(nextButton);
				panel2.add(costruisciButton);
				panel2.add(salvaButton);
				panel2.add(oldWinButton);

				giocatoreSTATS.setText("STATISTICHE\n"+ players[currentPlayer].getStatistiche() + 
						board.getSquare(players[currentPlayer].getLocation()).getName());

				giocatoreLabel.setText("E' IL TURNO DI:  " + players[currentPlayer].getName());
				jframe.setLayout(new GridLayout(0,2));
				jframe.add(panel1); 
				jframe.add(panel2);
				nextButton.setEnabled(false);
				costruisciButton.setEnabled(false);
				
				nextButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						do{
							if(++currentPlayer >= n)
								currentPlayer=0;
						}while (players[currentPlayer].getMoney() <= 0 );

						giocatoreLabel.setText("E' IL TURNO DI:  " + players[currentPlayer].getName());
						giocatoreSTATS.setText("STATISTICHE\n"+ players[currentPlayer].getStatistiche() +
								board.getSquare(players[currentPlayer].getLocation()).getName());
						nextButton.setEnabled(false);
						dadiButton.setEnabled(true);
						bancaSTATS.setText("OPERAZIONI");
						if(players[currentPlayer].isCostruzione())
							costruisciButton.setEnabled(true);
						else costruisciButton.setEnabled(false);
					}
				});


				dadiButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PanelTerreno k1 =  (PanelTerreno) board.getSquarePanel(Utily.conversioneGrafica(players[currentPlayer].getLocation()));
						if(!players[currentPlayer].isPrigione()){
							int result1 = uno.lancia();
							dadoPanelUno.setResult(result1); 
							int result2 = due.lancia();
							dadoPanelDue.setResult(result2); 
							pos= board.calcolaPosizione(result1, result2, players[currentPlayer]);

							PanelTerreno k2 =  (PanelTerreno) board.getSquarePanel(Utily.conversioneGrafica(players[currentPlayer].getLocation()));

							k1.setGiocatori(currentPlayer, false);
							k2.setGiocatori(currentPlayer, true);

							dadoPanelUno.repaint();
							dadoPanelDue.repaint();
							k2.repaint();
							k1.repaint();

						}
						else 
							pos=players[currentPlayer].getLocation();

						board.playerLanded(pos,players[currentPlayer]);
						giocatoreSTATS.setText("STATISTICHE\n"+ players[currentPlayer].getStatistiche() + board.getSquare(players[currentPlayer].getLocation()).getName());
						nextButton.setEnabled(true);
						dadiButton.setEnabled(false);
						bancaSTATS.setText("OPERAZIONI\n" +Banca.getMessage());
						if(players[currentPlayer].isCostruzione())
							costruisciButton.setEnabled(true);
						checkLosePlayer();
					}
				});

				costruisciButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String input=JOptionPane.showInputDialog(null,"Digita il nome della proprieta", "Costruzione",
								JOptionPane.QUESTION_MESSAGE); 
						try {
							int posizione = board.getSquare(input);

							if(posizione == -1) {
								JOptionPane.showMessageDialog (null,
										"INSERISCI IL NOME CORRETTO RISPETTANDO MAIUSCOLE E MINUSCOLE",
										"ERRORE",
										JOptionPane.ERROR_MESSAGE);
							}
							else if (board.getSquare(posizione) instanceof Proprieta) {

								Proprieta p = (Proprieta) board.getSquare(posizione);
								if(p.isCheckCostruire() && players[currentPlayer].equals(p.getPlayer())) {

									players[currentPlayer].costruisci(p);
									PanelTerreno k2 =  (PanelTerreno) board.getSquarePanel(Utily.conversioneGrafica(posizione));
									k2.repaint();
									bancaSTATS.setText("OPERAZIONI\n" +Banca.getMessage());
									giocatoreSTATS.setText("STATISTICHE\n"+ players[currentPlayer].getStatistiche() + board.getSquare(players[currentPlayer].getLocation()).getName());
								}
								else {
									JOptionPane.showMessageDialog (null,
											"Non puoi costruire qui",
											"ERRORE",
											JOptionPane.ERROR_MESSAGE);
								}

							}

							else {
								JOptionPane.showMessageDialog (null,
										"Non puoi costruire qui",
										"ERRORE",
										JOptionPane.ERROR_MESSAGE);
							}
						} catch (NullPointerException ex) {

						}
					}
				});
				
				salvaButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						state.setAll(n, winner, players, numGiocatoriInGara, currentPlayer, board);
						db.insert(state);
						JOptionPane.showMessageDialog(null, "SALVATA");
					}
				});
				
				oldWinButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ArrayList<Player> playersWinner;
						String winner ="";
						playersWinner = db.findWinner();
						if(playersWinner.size()>0) {
							for (int j=0;j<playersWinner.size();j++) {
								String nameWinner = playersWinner.get(j).getName();
								if(nameWinner.equals("Player-1"))
									nameWinner= "Partita non conclusa";
								winner+= nameWinner + "\n";
							}

						}
						else winner = "NESSUN VINCITORE NELLE SCORSE PARTITE";
						JOptionPane.showMessageDialog (null,winner,
								"Vincitori", JOptionPane.INFORMATION_MESSAGE);
					}
				});
				jframe.setSize(950,550);
				GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
				jframe.setMaximizedBounds(env.getMaximumWindowBounds());
				jframe.setExtendedState(jframe.getExtendedState() | jframe.MAXIMIZED_BOTH);
				jframe.setVisible(true);
			}
		});
	}


	public  void checkLosePlayer() {
		if(players[currentPlayer].getMoney()<=0) {
			JOptionPane.showMessageDialog (null, ""+players[currentPlayer].getName() +"HA PERSO",
					"GAME OVER", JOptionPane.INFORMATION_MESSAGE,
					null); 
			numGiocatoriInGara-=1;
			if(numGiocatoriInGara == 1)
				checkWinner();
		}

	}

	public  void checkWinner() {
		for(int j=0;j<players.length;j++) {
			if(players[j].getMoney()>0) {
				JOptionPane.showMessageDialog (null, ""+players[j].getName() +"HA VINTO",
						"VITTORIA", JOptionPane.INFORMATION_MESSAGE,
						null); 
				winner = j;
			}
		}
		dadiButton.setEnabled(false); 
		nextButton.setEnabled(false); 
		costruisciButton.setEnabled(false); 
	}

}