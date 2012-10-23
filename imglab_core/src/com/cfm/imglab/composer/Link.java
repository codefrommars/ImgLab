package com.cfm.imglab.composer;

public class Link {
	private OutputPort source;
	private InputPort target;
	
	public Link(OutputPort source, InputPort target) {
		this.source = source;
		this.target = target;
	}

	public OutputPort getSource() {
		return source;
	}
	
	public InputPort getTarget(){
		return target;
	}

	@Override
	public String toString() {
		return source.toString() + target.toString();
	}
}