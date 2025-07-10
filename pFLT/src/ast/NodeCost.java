package ast;

import visitor.IVisitor;

public class NodeCost extends NodeExpr{
	private String value;
	private LangType type;
	
	public NodeCost(String value,LangType type) {
		super();
		
		this.value = value;
		this.type = type;
	}
	
	public LangType getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "NodeCost[type: " + type + ", value: " + value + "]";
	}
	
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
