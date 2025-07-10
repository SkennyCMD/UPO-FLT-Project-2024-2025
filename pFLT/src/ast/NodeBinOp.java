package ast;

import visitor.IVisitor;

/**
 * Classe che rappresenta un espressione formata da due espressioni con un operatore binario (+, -, *, /, /(float))
 * @author Matteo Schintu (20051769)
 */
public class NodeBinOp extends NodeExpr{
	/**
	 * Operatore dell'espressione
	 * @author Matteo Schintu (20051769)
	 */
	private LangOper op;
	/**
	 * Espressione alla sinistra dell'operatore
	 * @author Matteo Schintu (20051769)
	 */
	private NodeExpr left;
	/**
	 * Espressione alla destra dell'operatore
	 * @author Matteo Schintu (20051769)
	 */
	private NodeExpr right;
	
	/**
	 * Costruttore che crea un oggetto di tipo NodeBinOp che crea un espressione binaria tra due espressioni a partire da un operatore e le sue due espressioni
	 * @param op : operatore dell'espressione
	 * @param left : espressione alla sinistra dell'operatore
	 * @param right : espressione alla destra dell'operatore
	 * @author Matteo Schintu (20051769)
	 */
	public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right) {
		super();
		
		this.op = op;
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Imposta una nuova espressione sull'espressione sinistra
	 * @param left : nuova operazione sinistra
	 * @author Matteo Schintu (20051769)
	 */
	public void setLeft(NodeExpr left) {
		this.left = left;
	}
	
	/**
	 * Imposta una nuova espressione sull'espressione destra
	 * @param right : nuova operazione destra
	 * @author Matteo Schintu (20051769)
	 */
	public void setRight(NodeExpr right) {
		this.right = right;
	}
	
	/**
	 * Imposta un nuovo operatore
	 * @param op : nuova operatore
	 * @author Matteo Schintu (20051769)
	 */
	public void setOp(LangOper op) {
		this.op = op;
	}
	
	/**
	 * Ritorna l'espressione sinistra
	 * @author Matteo Schintu (20051769)
	 */
	public NodeExpr getLeft() {
		return left;
	}
	
	/**
	 * Ritorna l'operatore 
	 * @author Matteo Schintu (20051769)
	 */
	public LangOper getOp() {
		return op;
	}
	
	/**
	 * Ritorna l'espressione destra
	 * @author Matteo Schintu (20051769)
	 */
	public NodeExpr getRight() {
		return right;
	}
	
	@Override
	public String toString() {
		return "NodeBinOp[op: " + op + ", left: " + left.toString() + ", rigth: " + right.toString() + "]";
	}
	
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
}
