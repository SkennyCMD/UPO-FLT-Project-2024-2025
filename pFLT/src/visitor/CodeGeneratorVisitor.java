package visitor;

import ast.*;
import symbolTable.*;
import symbolTable.SymbolTable.*;

public class CodeGeneratorVisitor implements IVisitor{
	
	private String code;
	private String msg;
	private String generated;
	
	public CodeGeneratorVisitor() {
		Register.init();
		code = "";
		msg = "";
		generated = "";
	}
	
	public String getCode() {
		return code;
	}
	
	public String getGenerated() {
		return generated.strip();
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setGenerated(String generated) {
		this.generated = generated;
	}

	@Override
	public void visit(NodeId node) {
		code = "" + SymbolTable.lookup(node.getName()).getRegister();
	}

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

	@Override
	public void visit(NodeCost node) {
		code = node.getValue();
		
	}

	@Override
	public void visit(NodeDeref node) {
		node.getId().accept(this);
		code = "l" + code;
	}

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

	@Override
	public void visit(NodeAssign node) {
		node.getExpr().accept(this);
		String expr = code;
		
		node.getId().accept(this);
		String id = code;
		code = expr + " s" + id;
		
	}

	@Override
	public void visit(NodePrint node) {
		node.getId().accept(this);
		code = "l" + code + " p P";
	}

}
