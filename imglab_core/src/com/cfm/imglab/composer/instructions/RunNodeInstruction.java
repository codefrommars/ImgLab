package com.cfm.imglab.composer.instructions;

import com.cfm.imglab.NamedValue;
import com.cfm.imglab.ValueSet;
import com.cfm.imglab.composer.Context;
import com.cfm.imglab.composer.FilterNode;
import com.cfm.imglab.composer.InputPort;
import com.cfm.imglab.composer.OutputPort;

public class RunNodeInstruction implements Instruction {
	
	private FilterNode filterNode;
	
	public RunNodeInstruction(FilterNode filterNode){
		this.filterNode = filterNode;
	}
	
	@Override
	public void execute(Context ctx) {
		
		ValueSet params = new ValueSet();
		
		for( InputPort in : filterNode.getInputs() ){
			NamedValue val = ctx.getValueForInput(in);
			params.addValue(val);
		}
		
		ValueSet output = new ValueSet();
		filterNode.getFilter().runFilter(params, output);
		
		for(OutputPort out : filterNode.getOutputs() ){
			ctx.setValueForOutput(out, output.get(out.getValue().getName()));
		}
	}

	@Override
	public String toString() {
		return "[RUN " + filterNode.toString()+"]";
	}
}
