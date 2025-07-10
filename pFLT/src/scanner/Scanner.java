package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import exceptions.LexicalException;
import token.*;

/**
 * Legge il programma sorgente e: \n
 * -ritorna i token del programma sorgente\n
 * -identifica possibili errori lessicali\n
 * -elimina informazioni inutili\n
 *	@author Matteo Schintu (20051769)
 */
public class Scanner {
	
	//parametri
	//--------------------------------------------------------------------------------------------------------------------------
	final char EOF = (char) -1; 
	private int riga;
	private PushbackReader buffer;
	private Token nxtToken;

	/**
	 * skpChars: insieme caratteri di skip (include EOF) e inizializzazione
	 * @author Matteo Schintu (20051769)
	 */
	private Set<Character> skpChars;
	/**
	 * letters: insieme lettere
	 * @author Matteo Schintu (20051769)
	 */
	private Set<Character> letters;
	/**
	 * digits: cifre
	 * @author Matteo Schintu (20051769)
	 */
	private Set<Character> digits;

	// operTkType: mapping fra caratteri '+', '-', '*', '/'  e il TokenType corrispondente
	private Map<Character,TokenType> operTkType;
	// delimTkType: mapping fra caratteri '=', ';' e il e il TokenType corrispondente
	private Map<Character,TokenType> delimTkType;
	// keyWordsTkType: mapping fra le stringhe "print", "float", "int" e il TokenType  corrispondente
	private Map<String,TokenType> keyWordsTkType;

	//--------------------------------------------------------------------------------------------------------------------------
	//Costruttore
	/**
	 * Costruttore che inizializza l'oggetto Scanner asseganndogli il file da cui prendere i dati
	 * @param fileName : nome del file da legare allo scanner
	 * @throws FileNotFoundException
	 * @author Matteo Schintu (20051769)
	 */
	public Scanner(String fileName) throws FileNotFoundException {

		this.buffer = new PushbackReader(new FileReader(fileName));
		riga = 1;
		
		skpChars = Set.of(' ', '\n', '\t', '\r', EOF);
		letters = Set.of('q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m');
		digits = Set.of('1','2','3','4','5','6','7','8','9','0');
		
		operTkType = Map.of('+',TokenType.PLUS,'-',TokenType.MINUS,'*',TokenType.TIMES, '/', TokenType.DIVIDE);
		delimTkType = Map.of('=',TokenType.ASSIGN, ';',TokenType.SEMI);
		keyWordsTkType = Map.of("print",TokenType.PRINT, "float", TokenType.TYFLOAT, "int", TokenType.TYINT);
		
	}
	
	//--------------------------------------------------------------------------------------------------------------------------
	/**
	 * nextToken ritorna il prossimo token nel file di input e legge i caratteri del token ritornato 
	 * (avanzando fino al carattere successivo all'ultimo carattere del token)
	 * @author Matteo Schintu (20051769)
	 */
	public Token nextToken() throws LexicalException, IOException  {
		
		if(nxtToken != null) {
			Token aus = nxtToken;
			nxtToken = null;
			return aus;
		}

		while(skpChars.contains(peekChar())) {
			if(peekChar() == '\n') {
				riga++;
			}
			
			readChar();
			
			if(peekChar() == EOF) {
				readChar();
				return getEOFToken();
			}
		}
		
		if(digits.contains(peekChar())) {
			return getNumbToken();
		}
		
		if(letters.contains(peekChar())) {
			return getIdToken();
		}
		
		if(operTkType.containsKey(peekChar())) {
			return getOperatorToken();
		}
		
		if(delimTkType.containsKey(peekChar())) {
			return getDelimitatorToken();
		}
		
		StringBuilder s = new StringBuilder();
		while(!skpChars.contains(peekChar()) && !delimTkType.containsKey(peekChar()) && peekChar() != '\n') {
			char c = readChar();
			s.append(c);
		}

		throw new LexicalException(riga, s.toString());


	}
	
	/**
	 * peekToken ritorna il prossimo token nel file di input, senza rimuoverlo
	 * @author Matteo Schintu (20051769)
	 */
	public Token peekToken() throws LexicalException, IOException {
		if(nxtToken == null) {
			nxtToken = nextToken();
		}
		
		return nxtToken;
	}

	//--------------------------------------------------------------------------------------------------------------------------
	//Funzioni per la lettura del buffer
	
	/**
	 * legge il carattere e lo ritorna, rimuovendolo dal buffer
	 * @author Matteo Schintu (20051769)
	 */
	private char readChar() throws IOException {
		return ((char) this.buffer.read());
	}

	/**
	 * legge il carattere e lo ritorna, senza rimuoverlo dal buffer
	 * @author Matteo Schintu (20051769)
	 */
	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------
	//Funzioni per ottenere i Token
	
	/**
	 * Ritorna il token relativo al tipo ID
	 * @author Matteo Schintu (20051769)
	 */
	private Token getIdToken() throws IOException{
		StringBuilder id = new StringBuilder();
		
		id.append(readChar());
		
		while(letters.contains(peekChar()) || digits.contains(peekChar())) {
			id.append(readChar());
		}
		
		String stringId = id.toString();
		
		if(keyWordsTkType.containsKey(stringId)) {
			return new Token(keyWordsTkType.get(stringId), riga);
		}else {
			return new Token(TokenType.ID, riga, stringId);

		}
		
	}
	/**
	 * Ritorna il token relativo ai tipi numerici (FLOAT e INT)	
	 * @author Matteo Schintu (20051769)
	 */
	private Token getNumbToken() throws  LexicalException, IOException {
		StringBuilder num = new StringBuilder();
		int count = 0;
		num.append(readChar());
		
		while (digits.contains(peekChar())) {
			num.append(readChar());
		}

		if (peekChar() == '.') {
			num.append(readChar());
			while (digits.contains(peekChar())) {
				num.append(readChar());
				count++;
			}
		}

		if (count > 5)
			throw new LexicalException(riga, num.toString());

		String numString = num.toString();
		if (numString.contains(".")) {
			return new Token(TokenType.FLOAT, riga, num.toString());
		}else{
			return new Token(TokenType.INT, riga, num.toString());
		}

	}
	
	/**
	 * Ritorna il token relativo ad un operatore (OP_ASSIGN,PLUS,MINUS,TIMES,DIVIDE)
	 * @author Matteo Schintu (20051769)
	 */
	private Token getOperatorToken() throws IOException, LexicalException {
		char op = readChar();
		
		if(peekChar() == '=' && operTkType.get(op) != null) {
			StringBuilder s = new StringBuilder();
			s.append(op);
			s.append(readChar());
			return new Token(TokenType.OP_ASSIGN, riga, s.toString());
		}else if(operTkType.get(op)  != null) {
			return new Token(operTkType.get(op), riga);
		}else {
			throw new LexicalException(riga, op);
		}
	}
	
	/**
	 * Ritorna il token relativo ai tipi di delimitazione (ASSIGN,SEMI)
	 * @author Matteo Schintu (20051769)
	 */
	private Token getDelimitatorToken() throws IOException {
		return new Token(delimTkType.get(readChar()), riga);
	}
	
	/**
	 * Ritorna il token relativo al tipo EOF
	 * @author Matteo Schintu (20051769)
	 */
	private Token getEOFToken() throws IOException {
		return new Token(TokenType.EOF, riga);
	}
}
