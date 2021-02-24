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
public class Prigione extends Terreno {

	private int prezzOut;

	public Prigione(String name,Color color) {
		super(name,color);
		prezzOut = 125;

	}

	/**
	   @see Strategy.Terreno#esegui 
	   Quando il giocatore si ferma su una casella prigione non può giocare per 2 turni, ma può lo stesso lanciare i dadi
	   Il giocatore può uscire di prigione o pagando una somma o per condono
	   @param player il giocatore che si trova sul terreno Prigione
	   @return void
	 */
	@Override
	public void esegui(Player player) {
		if(player.isPrigione()) {
			player.setTurniPrigione(player.getTurniPrigione() - 1);

			if (player.getTurniPrigione() != 0) 
				Banca.setMessage(player.getTurniPrigione() +  " turno di stop");
			else player.setPrigione(false);
		}
		else {
			player.setPrigione(true);
			player.setTurniPrigione(2);
			Banca.setMessage("SEI FINITO IN PRIGIONE: 2 TURNI DI STOP");
		}

		if(player.isPrigione())
			askOutPrigione(player);

	}

	/**
	Scopo del metodo: Chiedere al giocatore se vuole uscire di prigione pagando una somma di denaro
	@param player giocatore a cui chiedere se vuole uscire di prigione
	@return void
	 */
	public void askOutPrigione(Player player) {

		try {
			Object[] possibleValues2 = { "SI", "NO" };
			Object sel_input2 = JOptionPane.showInputDialog(null,"VUOI USCIRE DI PRIGIONE PAGANDO " + this.prezzOut + "€?", "SEI IN PRIGIONE",
					JOptionPane.INFORMATION_MESSAGE, null, possibleValues2, possibleValues2[0]);

			String input = sel_input2.toString();
			if(input == "SI") 	
				Banca.esciPrigione(player, this);	
		}catch(NullPointerException e) {}
	}

	public int getPrezzOut() {
		return prezzOut;
	}

	public void setPrezzOut(int prezzOut) {
		this.prezzOut = prezzOut;
	}

}
