package cfm.neograph;

import cfm.neograph.core.Graph;
import cfm.neograph.core.type.RuntimePrimitive;

public class ExecutionContext {
	private RuntimePrimitive[] memory;
	private Graph graph;
	
	public ExecutionContext(Graph graph, int memSize) {
		super();
		this.graph = graph;
		memory = new RuntimePrimitive[memSize];
	}

	public void setMemory(int portIndex, RuntimePrimitive p){
		memory[portIndex - 1] = p;
	}
	
	public RuntimePrimitive getMemory(int portIndex){
		return memory[portIndex - 1];
	}
	

	public Graph getGraph() {
		return graph;
	}
	
	public void debug(){
		int index = 0;
		for(RuntimePrimitive p : memory){
			index++;
			if( p == null ){
				System.out.println(index + ": null");
				continue;
			}
			System.out.println(index + ": " +p.toString());
		}
		
	}
	
}
