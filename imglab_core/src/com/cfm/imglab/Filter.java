package com.cfm.imglab;

public interface Filter {
	
	public String getName();
	
	public ValueSet getParameters();
	
	public ValueSet getOutput();
	
	public void runFilter(ValueSet input, ValueSet output);
	
	public String toString();
}
