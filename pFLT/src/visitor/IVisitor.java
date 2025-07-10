package visitor;

import ast.*;

public interface IVisitor {
	/**
	 * Visista al NodeId	 
	 * @param node : il NodeId da visitare
	 * @author Matteo Schintu (20051769)
	 */
	void visit(NodeId node);
	/**
	 * Visista al NodeProgramm	 
	 * @param node : il NodeProgramm da visitare
	 * @author Matteo Schintu (20051769)
	 */
	void visit(NodeProgram node);
	/**
	 * Visista al NodeCost	 
	 * @param node : il NodeCost da visitare
	 * @author Matteo Schintu (20051769)
	 */
	void visit(NodeCost node);
	/**
	 * Visista al NodeDeref	 
	 * @param node : il NodeDeref da visitare
	 * @author Matteo Schintu (20051769)
	 */
	void visit(NodeDeref node);
	/**
	 * Visista al NodeBinOp	 
	 * @param node : il NodeBinOp da visitare
	 * @author Matteo Schintu (20051769)
	 */
	void visit(NodeBinOp node);
	/**
	 * Visista al NodeDecl	 
	 * @param node : il NodeDecl da visitare
	 * @author Matteo Schintu (20051769)
	 */
	void visit(NodeDecl node);
	/**
	 * Visista al NodeAssign	 
	 * @param node : il NodeAssign da visitare
	 * @author Matteo Schintu (20051769)
	 */
	void visit(NodeAssign node);
	/**
	 * Visista al NodePrint	 
	 * @param node : il NodePrint da visitare
	 * @author Matteo Schintu (20051769)
	 */
	void visit(NodePrint node);
}
