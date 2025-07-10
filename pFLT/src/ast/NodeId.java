package ast;

import visitor.IVisitor;

public class NodeId extends NodeAST{
	private String name;
	
	public NodeId(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "NodeID[name:" + name + "]";
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
