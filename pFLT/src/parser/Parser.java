package parser;

import java.io.IOException;
import java.util.ArrayList;

import javax.sql.rowset.spi.SyncFactoryException;

import ast.*;
import exceptions.*;
import token.*;
import scanner.*;


public class Parser {
	/**
	 * Scanner utilizzato per leggere i token
	 * @author Matteo Schintu (20051769)
	 */
	private Scanner sc;
	
	/**
	 * Controlla se il token successivo contiene il tipo specificato, se si lo ritorna (facendo avanzare lo scanner), altrimenti lancia una SyntacticException
	 * @param type il tipo di token atteso
	 * @return il token successivo se il tipo corrisponde
	 * @throws SyntacticException se il tipo non corrisponde
	 * @author Matteo Schintu (20051769)
	 */
	private Token checkNextType(TokenType type) throws SyntacticException {
		Token t=null;
		
		try {
			t = sc.peekToken();
		} catch (LexicalException | IOException e) {
			throw new SyntacticException(e.getMessage());
		}
		
		if(type.equals(t.getType())) {
			try {
				return sc.nextToken();
			} catch (LexicalException | IOException e) {
				throw new SyntacticException(e.getMessage());			
			}
		}else {
			throw new SyntacticException(t.getRow(), type, t.getType());
		}
	}
	
	/**
	 * Costruttore della classe Parser
	 * @param sc lo scanner da utilizzare per leggere i token
	 * @author Matteo Schintu (20051769)
	 */
	public Parser(Scanner sc){
		this.sc = sc;
	}
	
	/**
	 * Metodo che avvia il processo di parsing, richiamando il metodo privato parseProgram
	 * @return un oggetto NodeProgram che rappresenta il programma parsato
	 * @throws SyntacticException se si verifica un errore di sintassi durante il parsing
	 * @author Matteo Schintu (20051769)
	 */
	public NodeProgram parse() throws SyntacticException {
		return parseProgram();
	}
	
	/**
	 * Metodo privato che effettua il parsing del programma, partendo dalla produzione Prg -> DSs $
	 * @return un oggetto NodeProgram che rappresenta il programma parsato
	 * @throws SyntacticException se si verifica un errore di sintassi durante il parsing
	 * @author Matteo Schintu (20051769)
	 */
	private NodeProgram parseProgram() throws SyntacticException {
		ArrayList<NodeDecSt> program = null;
		Token t = null;
		
		try {
			t = sc.peekToken();
		} catch (LexicalException | IOException e) {
			e.printStackTrace();
		}
		
		//Prg -> DSs $
		if(t.getType() == TokenType.ID || t.getType() == TokenType.PRINT ||t.getType() == TokenType.EOF ||t.getType() == TokenType.TYINT ||t.getType() == TokenType.TYFLOAT) {
			program = parseDSs();
			//parseDSs();
			checkNextType(TokenType.EOF);
		}else {
			checkNextType(TokenType.SEMI);
			parse();
			throw new SyntacticException("TYINT, TYFLOAT, PRINT, ID or EOF expected, obtained: " + t.getType());
		}
		
		return new NodeProgram(program);
	}
	
	/**
	 * Metodo privato che effettua il parsing partendo dalla produzione DSs -> Dcl DSs | Stm DSs | empty
	 * @return un ArrayList di NodeDecSt che rappresenta le dichiarazioni e gli statement parsati
	 * @throws SyntacticException se si verifica un errore di sintassi durante il parsing
	 * @author Matteo Schintu (20051769)
	 */
	private ArrayList<NodeDecSt> parseDSs() throws SyntacticException{
		ArrayList<NodeDecSt> nodes;
		Token t = null;
		
		try {
			t = sc.peekToken();
		} catch (LexicalException | IOException e) {
			throw new SyntacticException(e.getMessage());
		}
		
		if(t.getType() == TokenType.TYINT || t.getType() == TokenType.TYFLOAT) { //DSs -> Dcl DSs
			NodeDecl decl = parseDcl();
			nodes = parseDSs();
			nodes.add(0,decl);
		}else if(t.getType() == TokenType.PRINT || t.getType() == TokenType.ID) { //DSs -> Stm DSs
			NodeStm stm = parseStm();
			nodes = parseDSs();
			nodes.add(0,stm);
		}else if(t.getType() == TokenType.EOF) { //DSs -> empty
			nodes = new ArrayList<>();
		}else {
			throw new SyntacticException("TYINT, TYFLOAT, PRINT, ID or EOF expected, obtained: " + t.getType());
		}
		
		return nodes;
	}
	
	/**
	 * Metodo privato che effettua il parsing partendo dalla produzione Dcl -> Ty ID DclP
	 * @return un oggetto NodeDecl che rappresenta la dichiarazione parsata
	 * @throws SyntacticException se si verifica un errore di sintassi durante il parsing
	 * @author Matteo Schintu (20051769)
	 */
	private NodeDecl parseDcl() throws SyntacticException {
		Token t = null;
		
		try {
			t = sc.peekToken();
		} catch (LexicalException | IOException e) {
			throw new SyntacticException(e.getMessage());
		}
		
		// Ty -> tyfloat
		if(t.getType() == TokenType.TYFLOAT) {
			//Ty ID DclP 
			checkNextType(TokenType.TYFLOAT);
			LangType type = LangType.FLOAT;
			NodeId id = new NodeId(checkNextType(TokenType.ID).getVal());
			NodeExpr dclp = parseDclP();
			return new NodeDecl(id, type, dclp);
		}
		
		// Ty -> tyint
		if(t.getType() == TokenType.TYINT) {
			checkNextType(TokenType.TYINT);
			LangType type = LangType.INT;
			NodeId id = new NodeId(checkNextType(TokenType.ID).getVal());
			NodeExpr dclp = parseDclP();
			return new NodeDecl(id, type, dclp);
		}
		
		throw new SyntacticException("TYINT or TYFLOAT expected, obtained: " + t.getType());
	}
	
	/**
	 * Metodo privato che effettua il parsing partendo dalla produzione DclP -> = Exp semi | semi
	 * @return un oggetto NodeExpr che rappresenta l'espressione di inizializzazione della dichiarazione, o null se non c'è inizializzazione
	 * @throws SyntacticException se si verifica un errore di sintassi durante il parsing
	 * @author Matteo Schintu (20051769)
	 */
	private NodeExpr parseDclP() throws SyntacticException {
		Token t = null;
		
		try {
			t = sc.peekToken();
		} catch (LexicalException | IOException e) {
			throw new SyntacticException(e.getMessage());
		}
		
		//DclP -> = Exp semi
		if(t.getType() == TokenType.ASSIGN) {
			checkNextType(TokenType.ASSIGN);
			NodeExpr e = parseExp();
			checkNextType(TokenType.SEMI);
			return e;
		}

		//DclP -> semi
		if(t.getType() == TokenType.SEMI) {
			checkNextType(TokenType.SEMI); //per far avanzare lo scanner
			return null;
		}
		
		throw new SyntacticException("SEMI or ASSIGN expected, obtained: " + t.getType());
		
	}
	
	/**
	 * Metodo privato che effettua il parsing partendo dalla produzione Stm -> print id semi | ID Op Exp semi
	 * @return un oggetto NodeStm che rappresenta lo statement parsato
	 * @throws SyntacticException se si verifica un errore di sintassi durante il parsing
	 * @author Matteo Schintu (20051769)
	 */
	private NodeStm parseStm() throws SyntacticException {
		Token t = null;
		
		try {
			t = sc.peekToken(); //il token non � stato rimosso dalla coda, peekToken() non lo rimuove
		} catch (LexicalException | IOException e) {
			e.printStackTrace();
		}
		
		//Stm -> print id semi
		if(t.getType() == TokenType.PRINT) {
			checkNextType(TokenType.PRINT);
			Token idToken = checkNextType(TokenType.ID);
			NodeId id = new NodeId(idToken.getVal());
			checkNextType(TokenType.SEMI);
			return new NodePrint(id);
		}
		
		//Stm -> ID Op Exp semi
		if(t.getType() == TokenType.ID) {
			Token idToken = checkNextType(TokenType.ID);
			NodeId id = new NodeId(idToken.getVal());
			
			LangOper op = parseOp();
			NodeExpr e = parseExp();
			checkNextType(TokenType.SEMI);
			if(op == null) {
				return new NodeAssign(id,e);
			}else {
				NodeExpr left = new NodeDeref(id);
				NodeBinOp n = new NodeBinOp(op, left, e);
				return new NodeAssign(id, n);
			}
		}
		
		throw new SyntacticException("ID or PRINT expected, obtained: " + t.getType());
	}
	
	/**
	 * Metodo privato che effettua il parsing partendo dalla produzione Exp -> Tr ExpP
	 * @return un oggetto NodeExpr che rappresenta l'espressione parsata
	 * @throws SyntacticException se si verifica un errore di sintassi durante il parsing
	 * @author Matteo Schintu (20051769)
	 */
	private NodeExpr parseExp() throws SyntacticException {
		Token t = null;
		
		try {
			t = sc.peekToken(); //il token non � stato rimosso dalla coda, peekToken() non lo rimuove
		} catch (LexicalException | IOException e) {
			e.printStackTrace();
		}
		
		// Exp -> Tr ExpP
		if(t.getType() == TokenType.ID ||t.getType() == TokenType.INT ||t.getType() == TokenType.FLOAT ) {
			NodeExpr exp = parseExpP(parseTr());
			return exp;
		}

		throw new SyntacticException("ID, INT or FLOAT expected, obtained: " + t.getType());
	}
	
	/**
	 * Metodo privato che effettua il parsing partendo dalla produzione ExpP -> + Tr ExpP | - Tr ExpP | ;
	 * @param left l'espressione a sinistra dell'operatore
	 * @return un oggetto NodeExpr che rappresenta l'espressione parsata
	 * @throws SyntacticException se si verifica un errore di sintassi durante il parsing
	 * @author Matteo Schintu (20051769)
	 */
	private NodeExpr parseExpP(NodeExpr left) throws SyntacticException {
		Token t = null;
		
		try {
			t = sc.peekToken(); //il token non � stato rimosso dalla coda, peekToken() non lo rimuove
		} catch (LexicalException | IOException e) {
			e.printStackTrace();
		}
		
		// ExpP -> + Tr ExpP
		if(t.getType() == TokenType.PLUS) {
			checkNextType(TokenType.PLUS);
			NodeExpr exp = parseExpP(parseTr());
			return new NodeBinOp(LangOper.PLUS, left, exp);
		}
		
		// ExpP -> - Tr ExpP
		if(t.getType() == TokenType.MINUS) {
			checkNextType(TokenType.MINUS);
			NodeExpr exp = parseExpP(parseTr());
			return new NodeBinOp(LangOper.MINUS, left, exp);
		}
		
		// ExpP -> ;
		if(t.getType() == TokenType.SEMI) {
			return left;
		}
		
		throw new SyntacticException("PLUS or MINUS expected, obtained: " + t.getType());
	}
	
	private NodeExpr parseTr() throws SyntacticException {
		Token t = null;
		
		try {
			t = sc.peekToken(); //il token non � stato rimosso dalla coda, peekToken() non lo rimuove
		} catch (LexicalException | IOException e) {
			e.printStackTrace();
		}
		
		// Tr -> Val Trp
		if(t.getType() == TokenType.ID || t.getType() == TokenType.INT || t.getType() == TokenType.FLOAT) {
			NodeExpr val = parseVal();
			return parseTrP(val);
		}
		
		throw new SyntacticException("ID, INT or FLOAT expected, obtained: " + t.getType());
	}
	
	/**
	 * Metodo privato che effettua il parsing partendo dalla produzione Trp -> + Val Trp | / Val Trp | empty
	 * @param left l'espressione a sinistra dell'operatore
	 * @return un oggetto NodeExpr che rappresenta l'espressione parsata
	 * @throws SyntacticException se si verifica un errore di sintassi durante il parsing
	 * @author Matteo Schintu (20051769)
	 */
	private NodeExpr parseTrP(NodeExpr left) throws SyntacticException {
		Token t = null;
		
		try {
			t = sc.peekToken(); //il token non � stato rimosso dalla coda, peekToken() non lo rimuove
		} catch (LexicalException | IOException e) {
			e.printStackTrace();
		}
		
		// Trp -> + Val Trp
		if(t.getType() == TokenType.TIMES) {
			checkNextType(TokenType.TIMES);
			NodeExpr nodeLeft = parseVal();
			return new NodeBinOp(LangOper.TIMES, left, parseTrP(nodeLeft));
		}
		
		// Trp -> / Val Trp
		if(t.getType() == TokenType.DIVIDE) {
			checkNextType(TokenType.DIVIDE);
			NodeExpr nodeLeft = parseVal();
			return new NodeBinOp(LangOper.DIV, left, parseTrP(nodeLeft));
		}
		
		// Trp -> empty
		if(t.getType() == TokenType.PLUS || t.getType() == TokenType.MINUS || t.getType() == TokenType.SEMI) {
			return left;
		}
		
		throw new SyntacticException("TIMES, DIVIDE, PLUS, MINUS or SEMI expected, obtained: " + t.getType());
	}
	
	/**
	 * Metodo privato che effettua il parsing partendo dalla produzione Val -> int | float | id
	 * @return un oggetto NodeExpr che rappresenta il valore parsato
	 * @throws SyntacticException se si verifica un errore di sintassi durante il parsing
	 * @author Matteo Schintu (20051769)
	 */
	private NodeExpr parseVal() throws SyntacticException {
		Token t = null;
		
		try {
			t = sc.peekToken(); //il token non � stato rimosso dalla coda, peekToken() non lo rimuove
		} catch (LexicalException | IOException e) {
			e.printStackTrace();
		}
		
		// Val -> int
		if(t.getType() == TokenType.INT) {
			Token aus = checkNextType(t.getType()); 
			return new NodeCost(aus.getVal(), LangType.INT);
		}
		// Val -> float
		if(t.getType() == TokenType.FLOAT) {
			Token aus = checkNextType(t.getType()); 
			return new NodeCost(aus.getVal(), LangType.FLOAT);
		}
		
		// Val -> id
		if(t.getType() == TokenType.ID) {
			Token aus = checkNextType(t.getType()); 
			return new NodeDeref(new NodeId(aus.getVal()));
		}
		
		throw new SyntacticException("INT, FLOAT or ID expected, obtained: " + t.getType());
	}
	
	/**
	 * Metodo privato che effettua il parsing partendo dalla produzione Op -> ASSIGN | OP_ASSIGN
	 * @return un oggetto LangOper che rappresenta l'operatore parsato
	 * @throws SyntacticException se si verifica un errore di sintassi durante il parsing
	 * @author Matteo Schintu (20051769)
	 */
	private LangOper parseOp() throws SyntacticException {
		Token t = null;
		
		try {
			t = sc.peekToken(); //il token non � stato rimosso dalla coda, peekToken() non lo rimuove
		} catch (LexicalException | IOException e) {
			e.printStackTrace();
		}
		
		// Op -> ASSIGN
		if(t.getType() == TokenType.ASSIGN) {
			checkNextType(TokenType.ASSIGN);
			return null;
		}
		
		// Op -> OP_ASSIGN
		if(t.getType() == TokenType.OP_ASSIGN) {
			checkNextType(TokenType.OP_ASSIGN);
			
			String val = t.getVal();
			
			switch(val) {
				case "+=":
					return LangOper.PLUS;
				case "-=":
					return LangOper.MINUS;
				case "*=":
					return LangOper.TIMES;
				case "/=":
					return LangOper.DIV;
				default: 
					throw new SyntacticException("UNIDENTIFIED OPERATOR");
			}
		}
		
		throw new SyntacticException("ASSIGN or OP_ASSIGN expected, obtained: " + t.getType());
	}
	
}
