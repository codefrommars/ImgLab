package com.cfm.imglab.composer;

import com.cfm.imglab.NamedValue;

public class ValueNode extends Node{
	protected NamedValue value;

	public ValueNode() {
		super();
		outputs.add(new OutputPort(this));
	}

	public ValueNode(NamedValue value) {
		super();
		this.value = value;
		outputs.add(new OutputPort(this, value));
	}
	
	public OutputPort getOutputPort(){
		return outputs.get(0);
	}
	
	public String getName(){
		return value.getName();
	}

	@Override
	public String toString() {
		return getName();
	}
	
	public NamedValue getValue(){
		return value;
	}
	
}
