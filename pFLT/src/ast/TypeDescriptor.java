package ast;

public class TypeDescriptor {
	/**
	 * tipo del TypeDescriptor	 
	 * @author Matteo Schintu (20051769)
	 */
	private TypeTD type;
	/**
	 * messaggio d'errore del TypeDescriptor	 
	 * @author Matteo Schintu (20051769)
	 */
	private String msg;
	/**
	 * riga associata al tipo 	 
	 * @author Matteo Schintu (20051769)
	 */
	private int row;
	
	/**
	 * Costruttore che inizializza l'oggetto TypeDescriptor con il tipo, il messaggio d'errore e la riga del tipo
	 * @param type : tipo del TypeDescriptor
	 * @param msg : messaggio d'errore
	 * @param row : riga del tipo
	 * @author Matteo Schintu (20051769)
	 */
	public TypeDescriptor(TypeTD type, String msg, int row) {
		this.type = type;
		this.msg = msg;
		this.row = row;
	}
	
	/**
	 * Costruttore che inizializza l'oggetto TypeDescriptor solamente con il tipo
	 * @param type : tipo del TypeDescriptor
	 * @author Matteo Schintu (20051769)
	 */
	public TypeDescriptor(TypeTD type) {
		this.type = type;
	}
	
	/**
	 * Ritorna il messaggio d'errore
	 * @author Matteo Schintu (20051769)
	 */
	public String getMsg() {
		return msg;
	}
	
	/**
	 * Ritorna la riga
	 * @author Matteo Schintu (20051769)
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Ritorna il tipo
	 * @author Matteo Schintu (20051769)
	 */
	public TypeTD getType() {
		return type;
	}
	
	/**
	 * Consente di modificare il messaggio d'errore del TypeDescriptor	 
	 * @param msg : nuovo messaggio da assegnare al TypeDescriptor
	 * @author Matteo Schintu (20051769)
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	/**
	 * Consente di modificare la riga del TypeDescriptor	 
	 * @param row : nuovo riga da assegnare al TypeDescriptor
	 * @author Matteo Schintu (20051769)
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * Consente di modificare il tipo del TypeDescriptor	 
	 * @param type : nuovo tipo da assegnare al TypeDescriptor
	 * @author Matteo Schintu (20051769)
	 */
	public void setType(TypeTD type) {
		this.type = type;
	}
	
	/**
	 * Controlla se il TypeDescriptor passato è compatibile
	 * @param tD : TypeDescriptor di cui verificare la compatibilità
	 * @author Matteo Schintu (20051769)
	 */
	public boolean compatible(TypeDescriptor tD) {
		return !(type == TypeTD.FLOAT && tD.getType() == TypeTD.INT);
	}
}
