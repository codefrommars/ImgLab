package cfm.neograph.core;

public class GraphLink {
	private int source, target;
	
	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return "GraphLink [source=" + source + ", target=" + target + "]\n";
	}
}
