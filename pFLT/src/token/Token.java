package token;
/**
 * 
 * @author Matteo Schintu
 * 
 * @implNote
 * Token -> descrive insieme di caratteri con stesso significato
 *
 */
public class Token {

	//--------------------------------------------------------------------------------------------------------------------------
	//parametri
	private int row;
	private TokenType type;
	private String val;
	
	//--------------------------------------------------------------------------------------------------------------------------
	//costruttori
	
	/** 
	 * Costruttore per la creazione di un Token composto da tutti i parametri
	 * @param tipo : tipo del token (TokenType)
	 * @param riga : riga alla quale si trova il token
	 * @param val : valore del token
	 * @author Matteo Schintu (20051769)
	 * */
	public Token(TokenType tipo, int riga, String val) {
		this.row = riga;
		this.type = tipo;
		this.val = val;
	}
	
	/** 
	 * Costruttore per la creazione di un Token composto da tutti i parametri
	 * @param tipo : tipo del token (TokenType)
	 * @param riga : riga alla quale si trova il token
	 * @author Matteo Schintu (20051769)
	 */
	public Token(TokenType tipo, int riga) {
		this.type = tipo;
		this.row = riga;
	}
	
	/**
	 * Metodo che ritorna la riga alla quale si trova il token
	 * @return row
	 * @author Matteo Schintu (20051769)
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Metodo che ritorna il tipo del token
	 * @return type
	 * @author Matteo Schintu (20051769)
	 */
	public TokenType getType() {
		return type;
	}
	
	/**
	 * Metodo che ritorna il valore del token
	 * @return val
	 * @author Matteo Schintu (20051769)
	 */
	public String getVal() {
		return val;
	}
    
	public String toString() {
		if(val != null)
			return "<" + type + ",r:" + row + "," + val + ">";
		else
			return "<" + type + ",r:" + row + ">";

	}

     

}
