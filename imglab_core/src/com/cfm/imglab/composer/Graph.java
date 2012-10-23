package com.cfm.imglab.composer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.cfm.imglab.composer.instructions.ExecutableNodeInstruction;
import com.cfm.imglab.composer.instructions.RunNodeInstruction;

public class Graph {
	private ArrayList<Node> nodes;
	private ArrayList<Link> links;
	
	public Graph(){
		nodes = new ArrayList<Node>();
		links = new ArrayList<Link>();
	}
	
	public List<OutputPort> getOutputs(){
		ArrayList<OutputPort> outs = new ArrayList<OutputPort>();
		for(Node node : nodes){
			for(OutputPort outPort : node.getOutputs() ){
				Link l = findLinkByOuput(outPort);
				if( l == null ){
					outs.add(outPort);
				}
			}
		}
		return outs;
	}
	
	public Link findLinkByOuput(OutputPort output){
		for(Link link : links )
			if( link.getSource().equals(output) )
				return link;
		
		return null;
	}
	
	public Link findLinkByInput(InputPort input){
		for(Link link : links )
			if( link.getTarget().equals(input) )
				return link;
		
		return null;
	}
	
	public List<InputPort> getInputs(){
		ArrayList<InputPort> ins = new ArrayList<InputPort>();
		
		for(Node node : nodes)
			for(InputPort inPort : node.getInputs() ){
				Link l = findLinkByInput(inPort);
				if( l == null ) ins.add(inPort);
			}
		
		return ins;
	}
	
	public Program compile(){
		Program program = new Program();
		
		List<OutputPort> outputs = getOutputs();
		LinkedList<Node> nodesToVisit = new LinkedList<Node>();
		
		//Add the nodes to visit
		for(OutputPort out : outputs)
			nodesToVisit.add(out.getNode());
		
		while( nodesToVisit.size() > 0){
			Node node = nodesToVisit.removeFirst();
			
			if( node instanceof FilterNode ){
				program.pushInstruction(new RunNodeInstruction((FilterNode)node));
			}
			
			if( node instanceof ExecutableNode ){
				program.pushInstruction(new ExecutableNodeInstruction( (ExecutableNode)node));
			}

			//Go Backwards on the Graph
			for( InputPort nodeIn : node.getInputs() ){
				Link link = findLinkByInput(nodeIn);
				
				if( link == null ){//Not satisfied parameter
					System.out.println("(" + link.toString() + ") NOT SATISFIED PARAMETER");
					return null;
				}
				
				OutputPort out = link.getSource();
				Node n = out.getNode();
				nodesToVisit.addLast(n);
			}
		}
		
		return program;
	}
	
	public void addNode(Node node){
		nodes.add(node);
	}
	
	public void addLink(Link l){
		links.add(l);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		String nl = System.getProperty("line.separator");
		
		str.append("=Nodes=").append(nl);
		for( Node node : nodes)
			str.append(node.toString()).append(nl);
		
		str.append("=Links=").append(nl);
		for( Link l : links )
			str.append(l.toString()).append(nl);
		
		return str.toString();
	}

	public List<Link> getLinks() {
		return links;
	}
	
	
}
