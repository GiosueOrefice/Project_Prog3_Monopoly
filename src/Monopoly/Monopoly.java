package Monopoly;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import DAO.DAO;
import DAO.DerbyDB;
import Model.Banca;
import Model.StateGame;
import View.GameGUI;
/**
 *
 * @version n.n (24-02-2021)
 * @author Giosue' Orefice
 */
public class Monopoly{

	Monopoly() {
		String input;
		DAO db = DerbyDB.getInstance();
		GameGUI game = null;
		StateGame state = null;
		Utily utily = new Utily();
		Banca banca;
		try { //Domdandare se vuole iniziare una nuova partita o caricare una precedente
			String[] possibleValues = { "Nuova Partita", "Carica Partita"};
			input = (String) JOptionPane.showInputDialog(null,"Cosa vuoi fare?", "Benvenuto in Monopoly",
					JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);


			if(input.equals("Nuova Partita")) { //Domandare quanti persone giocheranno
				Object[] possibleValues2 = { "2", "3" , "4" , "5", "6" };
				Object sel_input2 = JOptionPane.showInputDialog(null,"Quanti giocatori siete?", "Benvenuto in Monopoly",
						JOptionPane.INFORMATION_MESSAGE, null, possibleValues2, possibleValues2[0]);
				input = sel_input2.toString();
				int n= Integer.parseInt(input);
				state = new StateGame(n);
				banca = new Banca(state.getPlayers());
				Banca.distribuisciDenaro(state.getPlayers());
				
			}

			else { //Carica la partita tramite id
				while(state == null) {
					input =JOptionPane.showInputDialog ("Inserisci l'ID della partita");
					state = db.findGame(Integer.parseInt(input));
					if(state==null)
						JOptionPane.showMessageDialog (null, "ID PARTITA ERRATO","Attenzione",
								JOptionPane.ERROR_MESSAGE);
				}
				banca = new Banca(state.getPlayers());
			}

		}
		catch(NumberFormatException e) {

		}
		catch(NullPointerException e) {
			System.exit(0);
		}

		game = new GameGUI(state);
		game.play();
	}
	
	public static void main(String[] args) {
		Monopoly monopoly = new Monopoly();
	}

}
