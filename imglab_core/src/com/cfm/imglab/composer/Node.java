package com.cfm.imglab.composer;

import java.util.ArrayList;

public class Node {
	protected ArrayList<InputPort> inputs;
	protected ArrayList<OutputPort> outputs;
	
	private int index;
	private static int indices = 0;
	
	public Node() {
		inputs = new ArrayList<InputPort>();
		outputs = new ArrayList<OutputPort>();
		indices++;
		index = indices;
	}
	
	public ArrayList<InputPort> getInputs() {
		return inputs;
	}
	
	public ArrayList<OutputPort> getOutputs() {
		return outputs;
	}
	
	public int getIndex(){
		return index;
	}
}
