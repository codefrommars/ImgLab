package cfm.neograph.core;

import java.util.Map.Entry;

import cfm.neograph.core.type.RuntimePrimitive;

import com.cfm.imglab.ImageDescriptor;

public class GraphNodeFactory {
	
	
	public GraphNode newImageNode(ImageDescriptor image){

		NodeValue v = new NodeValue();
		v.setType(GraphNodeType.Image);
		v.setValue(image);
		
		GraphNode node = new GraphNode();
		node.setValue(v);
		
		GraphPort p = new GraphPort(v.getType().toString());
		node.addPort(p, false);
		
		return node;
	}
	
	public GraphNode newNumberNode(Double number){
		GraphNode node = new GraphNode();
		
		NodeValue v = new NodeValue();
		v.setType(GraphNodeType.Number);
		v.setValue(number);
		
		node.setValue(v);
		
		GraphPort p = new GraphPort(v.getType().toString());
		node.addPort(p, false);
		
		return node;
	}
	
	public GraphNode newStringNode(String str){
		GraphNode node = new GraphNode();
		
		NodeValue v = new NodeValue();
		v.setType(GraphNodeType.String);
		v.setValue(str);
		
		node.setValue(v);
		
		GraphPort p = new GraphPort(v.getType().toString());
		node.addPort(p, false);
		
		return node;
	}
	
	public GraphNode newBooleanNode(Boolean b){
		GraphNode node = new GraphNode();
		
		NodeValue v = new NodeValue();
		v.setType(GraphNodeType.Boolean);
		v.setValue(b);
		
		node.setValue(v);
		
		GraphPort p = new GraphPort(v.getType().toString());
		node.addPort(p, false);
		
		return node;
	}
	
	public GraphNode newOperationNode(Operation op){
		GraphNode node = new GraphNode();
		
		NodeValue v = new NodeValue();
		v.setType(GraphNodeType.Operation);
		v.setValue(op);
		
		node.setValue(v);
		node.setLabel(op.getName());
		
		for(Entry<String, RuntimePrimitive> e : op.getParameters().entrySet()){
			GraphPort p = new GraphPort(e.getKey());
			node.addPort(p, true);
		}
		
		for(Entry<String, RuntimePrimitive> e : op.getOutput().entrySet()){
			GraphPort p = new GraphPort(e.getKey());
			node.addPort(p, false);
		}
		
		return node;
	}

	public GraphNode newNodeValue(NodeValue v) {
		GraphNode node = new GraphNode();
		node.setValue(v);
		
		GraphPort p = new GraphPort(v.getType().toString());
		node.addPort(p, false);
		
		return node;
	}

	public GraphNode newVariable(GraphNodeType transferData) {
		switch(transferData){
			case Boolean:
				return newBooleanNode(true);
			case Number:
				return newNumberNode(0d);
			case String:
				return newStringNode("");
			case Image:
				return newImageNode(null);
				
		}
		return null;
	}
}
