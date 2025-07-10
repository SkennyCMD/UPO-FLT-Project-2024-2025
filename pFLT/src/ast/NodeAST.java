package ast;

import visitor.*;

/**
 * Classe astratta che rappresenta un qualisasi nodo dell'AST
 * @author Matteo Schintu (20051769)
 */
public abstract class NodeAST {
	
	/**
	 * Metodo Astratto serve ad implementare il pattern visitor per il nodo 
	 * @author Matteo Schintu (20051769)
	 */
	public abstract void accept(IVisitor visitor);

}
