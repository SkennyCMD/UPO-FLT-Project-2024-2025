package visitor;

import ast.*;
import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;

public class TypeCheckinVisitor implements IVisitor{
	
	private TypeDescriptor resType; // mantiene il risultato della visita effettuata
	private int row;
	private String msg;
	
	public TypeCheckinVisitor() {
		SymbolTable.init();
		row = 0;
		msg = "";
	}

	@Override
	public void visit(NodeId node) {
		if(SymbolTable.lookup(node.getName()) == null) {
			resType = new TypeDescriptor(TypeTD.ERROR, node.getName() + " is not declared!", row);
			msg += node.getName() + " is not declared!";
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

	@Override
	public void visit(NodeProgram node) {
		for(NodeDecSt n: node.getDecSts()) {
			n.accept(this);
			row++;
		}
		
	}

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

	@Override
	public void visit(NodeDeref node) {
		node.getId().accept(this);
	}

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

	@Override
	public void visit(NodeDecl node) {
		TypeDescriptor  td = null;
		Attributes aus_attr = new Attributes(node.getType(), node.getId().getName()); // usato solo per il controllo
		
		if(SymbolTable.enter(node.getId().getName(), aus_attr)) {
			LangType nodeType = node.getType();
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
        }else if(!exp.compatible(id)) {
        	resType = new TypeDescriptor(TypeTD.ERROR, "(Semantic Error): can't assign a " + exp.getType() + " expression to " + node.getId().getName() + "(" + id.getType() + ")" , row);
        	msg = "(Semantic Error): can't assign a " + exp.getType() + " expression to " + node.getId().getName() + "(" + id.getType() + ")";
        }else {
        	resType = new TypeDescriptor(TypeTD.OK);
        }
	}

	@Override
	public void visit(NodePrint node) {
		node.getId().accept(this);
		
		if(resType != null && resType.getType() != TypeTD.ERROR) {
			resType = new TypeDescriptor(TypeTD.OK);
		}
		
	}
	
	
	
}
