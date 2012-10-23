package com.cfm.imglab.composer;

import com.cfm.imglab.NamedValue;

public class Port {
	protected NamedValue value;
	
	private int index;
	private static int indices = 0;
	
	protected transient Node node;
	
	public Port(Node node) {
		this.node = node;
		indices++;
		index = indices;
	}

	public Port(Node node, NamedValue value) {
		this(node);
		this.value = value;
	}

	public NamedValue getValue() {
		return value;
	}

	public void setValue(NamedValue value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return node.getIndex() + "." + value.toString();
	}

	public Node getNode() {
		return node;
	}
}
