package com.cfm.imglab.composer;

import com.cfm.imglab.NamedValue;

public class OutputPort extends Port {

	public OutputPort(Node node, NamedValue value) {
		super(node, value);
	}

	public OutputPort(Node node) {
		super(node);
	}

	@Override
	public String toString() {
		return super.toString() + "-->";
	}
	
}
