package Model;
/**
*
* @version n.n (24-02-2021)
* @author Giosue' Orefice
*/
public class Dado {
	private int result;

	public Dado() {

	}

	public int lancia() {
		this.result = (int) (Math.random()*6)+1;
		return this.result;
	}

	public int getResult() {
		return this.result;
	}

}
