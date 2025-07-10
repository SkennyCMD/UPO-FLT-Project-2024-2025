package ast;

import visitor.IVisitor;
/**
 * Nodo rappresetnante la dichiarazione di una variabile
 * @author Matteo Schintu (20051769)
 */
public class NodeDecl extends NodeDecSt{
	/**
	 * ID della variabile
	 * @author Matteo Schintu (20051769)
	 */
	private NodeId id;
	/**
	 * Tipo della variabile
	 * @author Matteo Schintu (20051769)
	 */
	private LangType type;
	/**
	 * Valore a cui è inizializzata la variabile
	 * @author Matteo Schintu (20051769)
	 */
	private NodeExpr init;
	
	/**
	 * Costruttore che crea l'oggetto di tipo NodeDecl a partire da id, tipo e valore iniziale(dato da un espressione)
	 * @param id : id della variabile
	 * @param type : tipo della variabile
	 * @param init : espressione che inizializza la variabile
	 * @author Matteo Schintu (20051769)
	 */
	public NodeDecl(NodeId id, LangType type, NodeExpr init) {
		super();
		
		this.id = id;
		this.type = type;
		this.init = init;
	}
	
	/**
	 * Ritorna l'id
	 * @author Matteo Schintu (20051769)
	 */
	public NodeId getId() {
		return id;
	}
	
	/**
	 * Ritorna l'espressione con cui la variabile è inizializzata
	 * @author Matteo Schintu (20051769)
	 */
	public NodeExpr getInit() {
		return init;
	}
	
	/**
	 * Ritorna il tipo della varaibile
	 * @author Matteo Schintu (20051769)
	 */
	public LangType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "NodeDecl[id:" + id.toString() + ", type: " + type + ", init: " + init.toString() + "]";
 	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
