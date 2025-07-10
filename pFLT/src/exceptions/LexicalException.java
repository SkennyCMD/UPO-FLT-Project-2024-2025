package exceptions;

public class LexicalException extends Exception {
	
	// Costruttori
	
	/**
	*	Costruttore per il lancio dell'eccezione lessicale generica
	* 	@author Matteo Schintu (20051769)
	*/
	public LexicalException() {
		super();
	}
	
	/**
	*	Costruttore per il lancio dell'eccezione lessicale generica, indicando la riga
	*	@param row : riga in cui è avvenuto l'errore
	*	@author Matteo Schintu (20051769)
	*/
	public LexicalException(int row) {
		super("Lexical Error at " + row + " row: Can't read character");
	}
	/**
	*	Costruttore per il lancio dell'eccezione lessicale, indicando la riga e l'identificatore del token errato
	*	@param row : riga in cui è avvenuto l'errore
	*	@param id : sequenza di caratteri che rappresenta il token errato
	*	@author Matteo Schintu (20051769)
	*/
	public LexicalException(int row, String id) {
		super("Lexical Error at " + row + " row: Invalid sequence '" + id + "' ");
	}
	/**
	*	Costruttore per il lancio dell'eccezione lessicale, indicando la riga e il carattere errato
	*	@param row : riga in cui è avvenuto l'errore
	*	@param c : carattere che ha causato l'errore
	*	@author Matteo Schintu (20051769)
	*/
	public LexicalException(int row, char c) {
		super("Lexical Error at " + row + " row: Invalid character '" + c + "' ");
	}
	

}
