package exceptions;

import token.*;

public class SyntacticException extends Exception{
	
	/**
	*	Costruttore per il lancio dell'eccezione, indicando la rig, il tipo aspettato e quello ottenuto
	*	@param row : riga in cui è avvenuto l'errore
	*	@param expected: tipo aspettato
	*	@param actual: tipo che si è ottenuto
	*	@author Matteo Schintu (20051769)
	*/
	public SyntacticException(int row, TokenType expected, TokenType actual) {
		super("Syntax Error at row " + row + ": Expected: " + expected + ", Actual: " + actual);
	}
	
	/**
	*	Costruttore per il lancio dell'eccezione, passandogli un messaggio personalizzato
	*	@param message: messaggio che si vuole visualizzare
	*	@author Matteo Schintu (20051769)
	*/
	public SyntacticException(String message) {
		super(message);
	}

}
