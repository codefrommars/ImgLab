package cfm.neograph.core;


public class NodeValue {
	private Object value;
	private GraphNodeType type;
	
	public GraphNodeType getType() {
		return type;
	}
	public void setType(GraphNodeType type) {
		this.type = type;
	}
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	public boolean hasValue(){
		return value != null;
	}
	
}