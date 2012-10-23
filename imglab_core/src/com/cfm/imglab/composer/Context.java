package com.cfm.imglab.composer;

import java.util.HashMap;

import com.cfm.imglab.NamedValue;
import com.cfm.imglab.ValueSet;

public class Context {
	private Graph graph;
	private HashMap<String, NamedValue> linkValues;
	
	public Context(Graph graph){
		this.graph = graph;
		linkValues = new HashMap<String, NamedValue>();
	}
	
	private NamedValue getValueAt(Link link){
		if( link == null )
			return null;
		
		return linkValues.get(link.toString());
	}
	
	private void setLinkValue(Link link, NamedValue value){
		if( link == null )
			return;
		
		linkValues.put(link.toString(), value);
	}
	
	public Graph graph(){
		return graph;
	}
	
	public NamedValue getValueForInput(InputPort input){
		return getValueAt( graph.findLinkByInput(input) );
	}
	
	public void setValueForOutput(OutputPort out, NamedValue value){
		Link l = graph.findLinkByOuput(out);
		
		if( l != null ){//Normal Link Value
			value.setName(l.getTarget().getValue().getName());
			setLinkValue(l, value);
		}else{
			linkValues.put(value.getName(), value);
		}
	}

	@Override
	public String toString() {
		return "Context [linkValues=" + linkValues + "]";
	}
	
	public ValueSet runProgram(Program p){
		//Clear the memory
		linkValues.clear();
		
		//Initial known values
		for(Link link : graph.getLinks()){
			NamedValue val = link.getSource().getValue();
			
			if( val == null )
				continue;
			
			if( !val.hasValue() )
				continue;
			val.setName(link.getTarget().getValue().getName());
			linkValues.put(link.toString(), val);
		}
		
		//Run the program
		p.run(this);
		
		//Get the output
		ValueSet values = new ValueSet();
		
		for(OutputPort out : graph.getOutputs())
			values.addValue(linkValues.get(out.getValue().getName()));
		
		return values;
	}
	
	public void executeNode(ExecutableNode node){
		
	}
}
