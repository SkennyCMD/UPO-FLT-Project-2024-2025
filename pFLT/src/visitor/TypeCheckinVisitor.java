package visitor;

import ast.*;
import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;

/*
 * Classe che implementa il visitor per il controllo dei tipi
 * durante la visita dell'albero sintattico.
 * 
 * @author Matteo Schintu (20051769)
 */
public class TypeCheckinVisitor implements IVisitor{
	/*
	 * Attributo che mantiene il risultato della visita effettuata
	 * @author Matteo Schintu (20051769)
	 */
	private TypeDescriptor resType;
	/*
	 * Attributo che mantiene la riga corrente durante la visita
	 * @author Matteo Schintu (20051769)
	 */
	private int row;
	/*
	 * Attributo che mantiene i messaggi di errore o avviso
	 * @author Matteo Schintu (20051769)
	 */
	private String msg;
	
	/*
	 * Costruttore che inizializza la symbolTable e le variabili di stato
	 * @author Matteo Schintu (20051769)
	 */
	public TypeCheckinVisitor() {
		SymbolTable.init();
		row = 0;
		msg = "";
	}
	
	/*
	 * Ritorna il risultato della visita effettuata
	 * @return resType
	 * @author Matteo Schintu (20051769)
	 */
	public TypeDescriptor getResType() {
		return resType;
	}
	
	/*
	 * Ritorna la riga corrente durante la visita
	 * @return row
	 * @author Matteo Schintu (20051769)
	 */
	public String getMsg() {
		return msg;
	}
	
	/*
	 * Imposta il messaggio di errore o avviso
	 * @param msg : messaggio da impostare
	 * @author Matteo Schintu (20051769)
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/* Metodo di visita per il NodeId
	 * @param node : il NodeId da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodeId node) {
		if(SymbolTable.lookup(node.getName()) == null) {
			resType = new TypeDescriptor(TypeTD.ERROR, node.getName() + " is not declared!", row);
			msg += "(Semantic Error): " + node.getName() + " is not declared!";
		}else {
			LangType nodeType = SymbolTable.lookup(node.getName()).getType();
			switch(nodeType){
				case INT:
					resType = new TypeDescriptor(TypeTD.INT);
					break;
					
				case FLOAT:
					resType = new TypeDescriptor(TypeTD.FLOAT);
					break;
			}
		}
		
	}
	
	/* Metodo di visita per il NodeProgram
	 * @param node : il NodeProgram da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodeProgram node) {
		for(NodeDecSt n: node.getDecSts()) {
			n.accept(this);
			row++;
		}
		
	}

	/* Metodo di visita per il NodeCost
	 * @param node : il NodeCost da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodeCost node) {
		LangType nodeType = node.getType();
		switch(nodeType){
			case INT:
				resType = new TypeDescriptor(TypeTD.INT);
				break;
				
			case FLOAT:
				resType = new TypeDescriptor(TypeTD.FLOAT);
				break;
		}
		
	}

	/* Metodo di visita per il NodeDeref
	 * @param node : il NodeDeref da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodeDeref node) {
		node.getId().accept(this);
	}

	/**
	 * Metodo di visita per il NodeBinOp
	 * @param node : il NodeBinOp da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodeBinOp node) {
		 node.getLeft().accept(this);
	        TypeDescriptor left = resType;
	        node.getRight().accept(this);
	        TypeDescriptor right = resType;
	        
	        if(left.getType() == TypeTD.ERROR) {
	        	resType = left;
	        } else if(right.getType() == TypeTD.ERROR) {
	        	resType = right;
	        }else {
	        	if(node.getOp() == LangOper.DIV && left.getType() == TypeTD.FLOAT || right.getType() == TypeTD.FLOAT ){
	        		node.setOp(LangOper.DIV_FLOAT);
	        	}
	        	
	        	if(left.getType() == TypeTD.FLOAT || right.getType() == TypeTD.FLOAT) {
	        		resType = new TypeDescriptor(TypeTD.FLOAT);
	        	}else {
	        		resType = new TypeDescriptor(TypeTD.INT);
	        	}
	        }
	}

	/**
	 * Metodo di visita per il NodeDecl
	 * @param node : il NodeDecl da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodeDecl node) {
		TypeDescriptor  td = null;
		Attributes aus_attr = new Attributes(node.getType(), node.getId().getName()); // usato solo per il controllo
		
		if(SymbolTable.enter(node.getId().getName(), aus_attr)) {// se è presente quella vvoce nella SytmbolTable
			LangType nodeType = node.getType(); //tipo del nodo
			switch(nodeType){
				case INT:
					td = new TypeDescriptor(TypeTD.INT);
					break;
				case FLOAT:
					td = new TypeDescriptor(TypeTD.FLOAT);
					break;
				default:
					td = new TypeDescriptor(TypeTD.ERROR, "(Semantic Error): var " + node.getId().getName() + " has an unknown type", row);
					break;
			}
		} else {
			resType = new TypeDescriptor(TypeTD.ERROR, "(Semantic Error): var " + node.getId().getName() + " is already declared", row);
			msg = "(Semantic Error): var " + node.getId().getName() + " is already declared";
		}
		
		if(node.getInit() == null) {
			resType = new TypeDescriptor(TypeTD.OK);
		}else {
			node.getInit().accept(this);
	        TypeDescriptor aus_td = resType;
	        
	        if(td.getType() == TypeTD.INT && aus_td.getType() == TypeTD.FLOAT ) {
	        	resType = new TypeDescriptor(TypeTD.ERROR, "(Semantic Error): can't assign " + aus_td.getType() + " to " + node.getId().getName() + "("+ td.getType() + ")" , row);
	        	msg = "(Semantic Error): can't assign " + aus_td.getType() + " to " + node.getId().getName() + "("+ td.getType() + ")";
	        }else {
	        	resType = new TypeDescriptor(TypeTD.OK);
	        }
		}
		
	}

	/**
	 * Metodo di visita per il NodeAssign
	 * @param node : il NodeAssign da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodeAssign node) {
		node.getId().accept(this);
        TypeDescriptor id = resType; // identificatore sul quale avviene l'assegnazione
        node.getExpr().accept(this);
        TypeDescriptor exp = resType; // espressione che viene assegnata al identificatore
        
        if(id.getType() == TypeTD.ERROR) {
        	resType = id;
        }else if(exp.getType() == TypeTD.ERROR) {
        	resType = exp;
        }else if(!exp.compatible(id)) {//errore se il tipo dell'espressione non è compatibile con il tipo dell'identificatore
        	resType = new TypeDescriptor(TypeTD.ERROR, "(Semantic Error): can't assign a " + exp.getType() + " expression to " + node.getId().getName() + "(" + id.getType() + ")" , row);
        	msg = "(Semantic Error): can't assign a " + exp.getType() + " expression to " + node.getId().getName() + "(" + id.getType() + ")";
        }else {
        	resType = new TypeDescriptor(TypeTD.OK);
        }
	}

	/*
	 * Metodo di visita per il NodePrint
	 * @param node : il NodePrint da visitare
	 * @author Matteo Schintu (20051769)
	 */
	@Override
	public void visit(NodePrint node) {
		node.getId().accept(this);
		
		if(resType != null && resType.getType() != TypeTD.ERROR) {
			resType = new TypeDescriptor(TypeTD.OK);
		}
		
	}
	
	
	
	
}
