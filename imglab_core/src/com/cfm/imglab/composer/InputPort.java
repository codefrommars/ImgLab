package com.cfm.imglab.composer;

import com.cfm.imglab.NamedValue;

public class InputPort extends Port{
	
	public InputPort(Node node, NamedValue value) {
		super(node, value);
	}

	public InputPort(Node node) {
		super(node);
	}

	@Override
	public String toString() {
		return  "o--" + super.toString();
	}

}
