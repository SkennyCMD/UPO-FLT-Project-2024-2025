package visitor;

import ast.*;
import symbolTable.*;
import symbolTable.SymbolTable.*;

public class CodeGeneratorVisitor implements IVisitor{
	
	/*
	 * Codice da controllare
	 */
	private String code;
	/*
	 * Messaggio di errore
	 */
	private String msg;
	/*
	 * Codice generato
	 */
	private String generated;
	
	/**
	 * Costruttore della classe CodeGeneratorVisitor
	 * Inizializza la tabella dei registri e le variabili di istanza
	 * @author Matteo Schintu (20051769)
	 */
	public CodeGeneratorVisitor() {
		Register.init();
		code = "";
		msg = "";
		generated = "";
	}
	
	/**
	 * Restituisce il codice 
	 * @return codice 
	 * @author Matteo Schintu (20051769)
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Restituisce il codice generato, rimuovendo gli spazi iniziali e finali
	 * @return codice generato senza spazi iniziali e finali
	 * @author Matteo Schintu (20051769)
	 */
	public String getGenerated() {
		return generated.strip();
	}
	
	/**
	 * Restituisce il messaggio di errore
	 * @return messaggio di errore
	 * @author Matteo Schintu (20051769)
	 */
	public String getMsg() {
		return msg;
	}
	
	/**
	 * Imposta il messaggio di errore
	 * @param msg messaggio di errore da impostare
	 * @author Matteo Schintu (20051769)
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	/**
	 * Imposta il codice
	 * @param code codice da impostare
	 * @author Matteo Schintu (20051769)
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Imposta il codice generato
	 * @param generated codice generato da impostare
	 * @author Matteo Schintu (20051769)
	 */
	public void setGenerated(String generated) {
		this.generated = generated;
	}

	/**
	 * Visita un nodo di tipo NodeId
	 * @param node nodo da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodeId node) {
		code = "" + SymbolTable.lookup(node.getName()).getRegister();
	}

	/**
	 * Visita un nodo di tipo NodeProgram
	 * @param node nodo da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodeProgram node) {
		for(NodeDecSt n: node.getDecSts()) {
			if(msg.equals("")) {
				n.accept(this);
				if(!code.equals("")) {
					generated += code + " ";
				}
				code = "";
			}else {
				generated = "";
			}
		}
		
	}

	/**
	 * Visita un nodo di tipo NodeCost
	 * @param node nodo da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodeCost node) {
		code = node.getValue();
		
	}

	/**
	 * Visita un nodo di tipo NodeDeref
	 * @param node nodo da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodeDeref node) {
		node.getId().accept(this);
		code = "l" + code;
	}

	/**
	 * Visita un nodo di tipo NodeBinOp
	 * @param node nodo da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodeBinOp node) {
		node.getLeft().accept(this);
		String left = code;
		
		node.getRight().accept(this);
		String right = code;
		
		LangOper operator = node.getOp();
		
		switch(operator) {
			case PLUS:
				code = left + " " + right + " +";
				break;
			case MINUS:
				code = left + " " + right + " -";
				break;
			case TIMES:
				code = left + " " + right + " *";
				break;
			case DIV:
				code = left + " " + right + " /";
				break;
			case DIV_FLOAT:
				code = left + " " + right + " 5 k / 0 k";
				break;
			
		}
		
	}

	/**
	 * Visita un nodo di tipo NodeUnaryOp
	 * @param node nodo da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodeDecl node) {
		Attributes attr = SymbolTable.lookup(node.getId().getName());
		char r = 0; // qui viene memorizzato il registro
		
		try {
			r = Register.newRegister();
		} catch (Exception e) {
			msg = e.getMessage();
			return;
		}
		
		attr.setRegister(r);
		
		if(node.getInit() != null) {
			node.getInit().accept(this);
			String init = code;
			
			node.getId().accept(this);
			String id = code;
			
			code =  init + " s" + id;
		}
	}

	/*
	 * Visita un nodo di tipo NodeUnaryOp
	 * @param node nodo da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodeAssign node) {
		node.getExpr().accept(this);
		String expr = code;
		
		node.getId().accept(this);
		String id = code;
		code = expr + " s" + id;
		
	}

	/**
	 * Visita un nodo di tipo NodeUnaryOp
	 * @param node nodo da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodePrint node) {
		node.getId().accept(this);
		code = "l" + code + " p P";
	}

}
