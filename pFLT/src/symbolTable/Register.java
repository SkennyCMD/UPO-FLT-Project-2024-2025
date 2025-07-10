package symbolTable;

import java.util.ArrayList;

public class Register {
	/**
	 * chars: ArrayList che conterrà i registri disponibili all uso
	 * @author Matteo Schintu (20051769)
	 */
	static ArrayList<Character> chars;
	
	/**
	 * Metodo che inizializza l'ArrayList dei registri, inizializzandoli con nomi da 'a' a 'z'
	 * @author Matteo Schintu (20051769)
	 */
	public static void init() {
		chars = new ArrayList<>();
		
		for(char c = 'a'; c <= 'z'; c++) {
			chars.add(c);
		}
	}
	
	/**
	 * metodo che ritorna il prossimo registro da utilizzare.
	 * Se non ci sono più registri disponibili viene lanciata un'eccezione
	 * @author Matteo Schintu (20051769)
	 */
	public static char newRegister() throws Exception {
		if(!chars.isEmpty()) {
			return chars.remove(0);
		}
		
		throw new Exception("There are no more free registers");
	}
}
