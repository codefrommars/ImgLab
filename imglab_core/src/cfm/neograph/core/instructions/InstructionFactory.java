package cfm.neograph.core.instructions;

import cfm.neograph.core.Graph;
import cfm.neograph.core.GraphLink;
import cfm.neograph.core.GraphNode;
import cfm.neograph.core.GraphNodeType;
import cfm.neograph.core.GraphPort;
import cfm.neograph.core.NodeValue;

import com.cfm.imglab.ImageDescriptor;

public class InstructionFactory {
	
	public Instruction toInstruction(GraphNode node, Graph graph){
		NodeValue v = node.getValue();
		
		if( v.getType() == GraphNodeType.Operation ){
			return toOperationInstruction(node, graph);
		}
		
		GraphPort out = node.getOutPorts().get(0);
		GraphLink l = graph.getLinkBySource(out);
		GraphPort in = graph.getPort(l.getTarget());
		
		switch(v.getType()){
			case Image:
				return new SetImageInstruction(in.getLabel(), (ImageDescriptor)v.getValue(), out.getIndex());
				
			case Number:
				return new SetNumberInstruction(in.getLabel(), (Double)v.getValue(), out.getIndex());
				
			case String:
				return new SetStringInstruction(in.getLabel(), (String)v.getValue(), out.getIndex());
			
			case Boolean:
				return new SetBooleanInstruction(in.getLabel(), (Boolean)v.getValue(), out.getIndex());
			
			default:
				break;
		}
		
		return null;
	}
	

	private Instruction toOperationInstruction(GraphNode node, Graph g){
//		BlockInstruction block = new BlockInstruction();
			
		//Execution instructions
		ExecuteOperationInstruction ex = new ExecuteOperationInstruction(node);
		return ex;
//		block.addInstruction(ex);
		
		//Set results instructions
//		for(GraphPort out : node.getOutPorts()){
//			GraphLink l = g.getLinkBySource(out);
//			
//			if( l == null )
//				continue;
//			
//			GraphPort in = g.getPort(l.getTarget());
//			Instruction move = new MoveMemoryInstruction(out.getIndex(), in.getIndex(), in.getLabel());
//			block.addInstruction(move);
//		}
		
//		return block;
	}
}
