package cfm.neograph;

import java.util.ArrayList;
import java.util.Stack;

import cfm.neograph.core.Graph;
import cfm.neograph.core.GraphLink;
import cfm.neograph.core.GraphNode;
import cfm.neograph.core.GraphPort;
import cfm.neograph.core.instructions.BlockInstruction;
import cfm.neograph.core.instructions.Instruction;
import cfm.neograph.core.instructions.InstructionFactory;
import cfm.neograph.core.instructions.MoveMemoryInstruction;

public class Evaluator {

//	private int depth = 0;
//	private ArrayList<Integer> visited = new ArrayList<Integer>();
	//private ExecutionContext context;
	//private RuntimePrimitive[] memory;
	//private ArrayList<Integer> outputs;
	//private ArrayList<>
	//private Graph graph;
	
	public BlockInstruction compile(Graph graph){
		//Validate input
		ArrayList<Integer> inputs = findDisconnectedInputs(graph);
		if( inputs.size() > 0 ){
			//Can't execute
			System.err.println("Can't execute, not complete graph");
			System.err.println("Port: " + inputs.get(0));
			return null;
		}
		
		ArrayList<Integer> outputs = findOutputs(graph);
		Stack<Integer> exeStack = new Stack<Integer>();
		
		//Find the node dependencies, constructing the execution order
		for(Integer outIndex : outputs ){
			GraphNode node = graph.getNode(outIndex);
			exeStack.push(node.getIndex());			
			addDependencies(graph, node, exeStack);
		}
		
		BlockInstruction program = new BlockInstruction();
		InstructionFactory insFac = new InstructionFactory();
		
		//Compile the program
		while(!exeStack.isEmpty()){
			GraphNode node = graph.getNode(exeStack.pop());
			//Node evaluation
			Instruction ins = insFac.toInstruction(node, graph);
			program.addInstruction(ins);
			
			//Move by Link
			for(GraphPort out : node.getOutPorts() ){
				GraphLink link = graph.getLinkBySource(out);
				if( link == null )
					continue;
				
				GraphPort in = graph.getPort(link.getTarget());
				program.addInstruction(new MoveMemoryInstruction(link.getSource(), link.getTarget(), in.getLabel()));
			}
			
		}
		
//		System.out.println(graph);
//		System.out.println(program);
		
		return program;
	}
	
	public void execute(Graph graph){
		BlockInstruction program = compile(graph);
		
		int memorySize = maxPort(graph);
		ExecutionContext context = new ExecutionContext(graph, memorySize);
		program.execute(context);
		
		context.debug();
	}

	private void addDependencies(Graph g, GraphNode node, Stack<Integer> stack){
		ArrayList<Integer> parents = new ArrayList<Integer>();

		for(GraphPort pIn : node.getInPorts()){
			GraphLink l = g.getLinkByTarget(pIn);
			GraphNode parent = g.getNodeOfPort(l.getSource());
			
			if( !parents.contains(parent.getIndex()) )
				parents.add(parent.getIndex());
		}
		
		for(Integer parentIndex : parents ){
			if( !stack.contains(parentIndex) )
				stack.push(parentIndex);
		}
		
		for(Integer parentIndex : parents ){
			addDependencies(g, g.getNode(parentIndex), stack);
		}
	}
	
	private int maxPort(Graph g){
		int total = 0;
		for(GraphNode node : g.getNodes()){
			
			for(GraphPort p : node.getInPorts()){
				if( total < p.getIndex() )
					total = p.getIndex();
			}
			
			for(GraphPort p : node.getOutPorts()){
				if( total < p.getIndex() )
					total = p.getIndex();
			}
			
			//total += node.getInPorts().size();
			//total += node.getOutPorts().size();
		}
		
		return total + 1;
	}
	
	private ArrayList<Integer> findOutputs(Graph g){
		ArrayList<Integer> out = new ArrayList<Integer>();
		
		for(GraphNode node : g.getNodes()){
			
			if( node.getOutPorts().size() == 0 ){
				out.add(node.getIndex());
				continue;
			}
			
			for(GraphPort pOut : node.getOutPorts() ){
				GraphLink l = g.getLinkBySource(pOut);
				//Not connected, this is an output of the system
				if( l == null )
					out.add(node.getIndex());
					//out.add(pOut.getIndex());
			}
		}
		
		return out;
	}
	
	private ArrayList<Integer> findDisconnectedInputs(Graph g){
		ArrayList<Integer> ins = new ArrayList<Integer>();
		
		for(GraphNode node: g.getNodes()){
			for(GraphPort pIn : node.getInPorts()){
				GraphLink l = g.getLinkByTarget(pIn);
				
				if( l == null)
					ins.add(pIn.getIndex());
			}
		}
		
		return ins;
	}
}
