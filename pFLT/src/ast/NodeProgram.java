package ast;

import java.util.ArrayList;

import visitor.IVisitor;

/**
 * Classe che rappresenta il nodo Program dell'AST, ovvero il nodo che contiene il programma
 * @author Matteo Schintu (20051769)
 */
public class NodeProgram extends NodeAST{
	
	/**
	 * ArrayList contenente i nodi NodeDecSt che compongono il programma
	 * @author Matteo Schintu (20051769)
	 */
	private ArrayList<NodeDecSt> decSts;
	
	/**
	 * Costruttore che inizializza l'Arraylist come nuovo
	 * @author Matteo Schintu (20051769)
	 */
	public NodeProgram() {
		super();
		decSts = new ArrayList<>();
	}
	
	/**
	 * Costruttore che inizializza l'Arraylist a partire da un'altro ArrayList 
	 * @author Matteo Schintu (20051769)
	 */
	public NodeProgram(ArrayList<NodeDecSt> decSts) {
		super();
		this.decSts = decSts;
	}
	
	/**
	 * Ritorna l'intero ArrayList
	 * @author Matteo Schintu (20051769)
	 */
	public ArrayList<NodeDecSt> getDecSts() {
		return decSts;
	}
	
	/**
	 * Ritorna l'elemento in posizione pos dell'ArrayList
	 * @param pos : posizione da ritornare dell'ArrayList
	 * @author Matteo Schintu (20051769)
	 */
	public NodeDecSt getNodeDecSt(int pos) {
		return decSts.get(pos);
	}
	
	@Override
	public String toString() {
		String ris = "Program: [";
		
		for(NodeAST n: decSts) {
			ris += n.toString() + " ";
		}
		
		ris += "]";
		
		return ris;
	}
	
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
