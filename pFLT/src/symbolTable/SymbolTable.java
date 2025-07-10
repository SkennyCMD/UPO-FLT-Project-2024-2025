package symbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import ast.*;

/**
 * Classe che rappresenta la struttura di supporto alla compilazione che contiene informazioni sui nomi utilizzati nel programma
 * @author Matteo Schintu (20051769)
 *
 */
public class SymbolTable {
	
	/**
	 * Classe statica che rappresenta gli attributi che verranno memorizzati nella symbol table
	 * @author Matteo Schintu (20051769)
	 */
	public static class Attributes{
		
		/**
		 * Attributo (della classe) che rappresenta il tipo dell'attributo
		 * @author Matteo Schintu (20051769)
		 */
		private LangType type;
		/**
		 * Attributo (della classe) che rappresenta il nome dell'attributo
		 * @author Matteo Schintu (20051769)
		 */
		private String name;
		/**
		 * Attributo (della classe) che rappresenta il registro associato all'attributo
		 * @author Matteo Schintu (20051769)
		 */
		private char register;
		
		/**
		 * Costruttore che inizializza l'oggetto di tipo Attributes con i valori di tipo e nome
		 * @param tipo : tipo dell'attributo
		 * @param nome : nome dell'attributo
		 * @author Matteo Schintu (20051769)
		 */
		public Attributes(LangType tipo, String nome) {
			this.type = tipo;
			this.name = nome;
		}
		
		/**
		 * Ritorna il nome dell'attributo
		 * @author Matteo Schintu (20051769)
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Ritorna il registro relativo all'attributo
		 * @author Matteo Schintu (20051769)
		 */
		public char getRegister() {
			return register;
		}
		
		/**
		 * Ritorna il tipo dell'attributo
		 * @author Matteo Schintu (20051769)
		 */
		public LangType getType() {
			return type;
		}
		
		/**
		 * Consente di modificare il nome dell'attributo con un nuovo nome
		 * @param nome : nuovo nome da assegnare all'attributo
		 * @author Matteo Schintu (20051769)
		 */
		public void setName(String nome) {
			this.name = nome;
		}
		
		/**
		 * Consente di modificare il tipo dell'attributo con uno nuovo
		 * @param tipo : nuovo tipo da assegnare all'attributo
		 * @author Matteo Schintu (20051769)
		 */
		public void setType(LangType tipo) {
			this.type = tipo;
		}
		
		/**
		 * Consente di modificare il registro dell'attributo con uno nuovo
		 * @param registro : nuovo registro da associare all'attributo
		 * @author Matteo Schintu (20051769)
		 */
		public void setRegister(char registro) {
			this.register = registro;
		}
	}
	
	/**
	 * Mappatura tra nomi degli attributi e oggetti di tipo Attibutes (è la symble table)
	 * @author Matteo Schintu (20051769)
	 */
	private static HashMap<String, Attributes> symbTab;
	
	/**
	 * Inizializza la syombol table
	 * @author Matteo Schintu (20051769)
	 */
	public static void init() {
		symbTab = new HashMap<>();
	}
	
	/**
	 * Aggiunge una nuova vode alla symbol table
	 * @param id : id della nuova voce da aggiungere
	 * @param entry : valore da aggiungere alla nuova voce
	 * @author Matteo Schintu (20051769)
	 */
	public static boolean enter(String id, Attributes entry) {
		if(symbTab.containsKey(id)) {
			return false;
		}else {
			symbTab.put(id, entry);
			return true;
		}
		
	}
	
	/**
	 * Ritorna l'attributo relativo all'id passato
	 * @param id : id dell'oggetto da ritornare
	 * @author Matteo Schintu (20051769)
	 */
	public static Attributes lookup(String id) {
		return symbTab.get(id);
	}
	
	/**
	 * Ritorna una stringa che contiene tutta la tabella
	 * @author Matteo Schintu (20051769)
	 */
	public static String toStr() {
		String ris = "SymbolTable\n";
		
		for(var e: symbTab.entrySet()) {
			ris += "{ID: " + e.getKey() + ", Tipo: " + e.getValue().getType() + "}\n";
		}
		
		return ris;
	}
	
	/**
	 * Ritorna il numero di voci della symbol table
	 * @author Matteo Schintu (20051769)
	 */
	public static int size() {
		return symbTab.size();
	}

}
