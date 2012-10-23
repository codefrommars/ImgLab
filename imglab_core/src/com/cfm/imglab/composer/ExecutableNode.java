package com.cfm.imglab.composer;


public abstract class ExecutableNode extends Node{
	
//	public ExecutableNode(){
//		super();
//		//inputs.add(new InputPort(this));
//	}
	
	public String getName(){
		return "Executable";
	}

	@Override
	public String toString() {
		return getName();
	}
	
	public abstract void execute(Context ctx);
}
