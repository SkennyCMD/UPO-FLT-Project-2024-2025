package ast;

import visitor.*;

/**
 * Classe che rappresenta il nodo di assegnazione di un valore ad una variabile
 * @author Matteo Schintu (20051769)
 */
public class NodeAssign extends NodeStm{
	/**
	 * id della variabile a cui assegnare il valore
	 * @author Matteo Schintu (20051769)
	 */
	private NodeId id;
	/**
	 * espressione che assegna un valore alla variabile
	 * @author Matteo Schintu (20051769)
	 */
	private NodeExpr expr;
	
	/**
	 * Costruttore che crea un oggetto NodeAssign a partire dall'id della variabile e da un'espressione
	 * @param id : id della varaibile a cui assegnare il valore
	 * @param expr : espressione da assegnare alla variabile
	 * @author Matteo Schintu (20051769)
	 */
	public NodeAssign(NodeId id, NodeExpr expr) {
		super();
		
		this.id = id;
		this.expr = expr;
	}
	
	/**
	 * Ritorna l'espressione da assegnare alla variabile
	 * @author Matteo Schintu (20051769)
	 */
	public NodeExpr getExpr() {
		return expr;
	}
	
	/**
	 * Ritorna l'id della variabile
	 * @author Matteo Schintu (20051769)
	 */
	public NodeId getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "NodeAssign[id: " + id.toString() + ", expr: " + expr.toString() + "]";
	}
	
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
