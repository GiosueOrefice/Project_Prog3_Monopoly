package Monopoly;
/**
*
* @version n.n (24-02-2021)
* @author Giosue' Orefice
*/

public class Utily {
	
	private static int conversione[] = new int[12];
	
	public Utily(){
		conversione[0] = 0;
		conversione[1] = 1;
		conversione[2] = 2;
		conversione[3] = 3;
		conversione[4] = 7;
		conversione[5] = 11;
		conversione[6] = 15;
		conversione[7] = 14;
		conversione[8] = 13;
		conversione[9] = 12;
		conversione[10] = 8;
		conversione[11] = 4;
		
	}
	
	
	
	
	
	
	/**
	Scopo del metodo: convertire l'indice di un terreno  nell'indice della rispettiva rappresentazione grafica
	@param pos indice del terreno
	@return indice della rappresentazione grafica
	*/
	public static int conversioneGrafica(int pos) {
		return conversione[pos];
	}

	/**
	Scopo del metodo: convertire l'indice della  rappresentazione grafica di un terreno nel rispettivo terreno
	@param pos indice della rapresentazione grafica del terreno
	@return indice del terreno
	*/
	public static int conversioneBoard(int posGrafico) {
		int index=-1;
		for(int j=0;j<12;j++) 
			if(posGrafico == conversione[j])
				index=j;
		return index;
	}

}
