package cfm.neograph.core.instructions;

import java.util.HashMap;

import cfm.neograph.ExecutionContext;
import cfm.neograph.core.GraphNode;
import cfm.neograph.core.GraphPort;
import cfm.neograph.core.Operation;
import cfm.neograph.core.ValueSet;
import cfm.neograph.core.type.RuntimePrimitive;

public class ExecuteOperationInstruction implements Instruction{
	private GraphNode node;
	
	public ExecuteOperationInstruction(GraphNode node){
		this.node = node;
	}
	
	public void execute(ExecutionContext ctx) {
		
		//Get input values
		ValueSet input = new ValueSet();
		
		for(GraphPort p : node.getInPorts()){
			//RuntimePrimitive rp = ctx.getMemory()[p.getIndex()];
			RuntimePrimitive rp = ctx.getMemory(p.getIndex());
			input.put(p.getLabel(), rp);
		}
		
		//Create the output HashSet
		ValueSet output = new ValueSet();
		Operation operation = (Operation)node.getValue().getValue();
		
		//Execute
		operation.execute(input, output);
		
		//Set the results to memory.
		for(GraphPort p : node.getOutPorts()){
			RuntimePrimitive out = output.get(p.getLabel());
			ctx.setMemory(p.getIndex(), out);
			//ctx.getMemory()[p.getIndex()] = out;
		}
	}
	
	@Override
	public String toString() {
		Operation operation = (Operation)node.getValue().getValue();
		return "Execute: " + operation.getName() + "\n";
	}
}
