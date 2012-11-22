package cfm.neograph.core;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Graph {
	private ArrayList<GraphNode> nodes;
	private transient ArrayList<GraphPort> ports;
	private ArrayList<GraphLink> links;
	
	private int lastNodeIndex = 0, lastPortIndex = 0;
	
	public Graph(){
		nodes = new ArrayList<GraphNode>();
		ports = new ArrayList<GraphPort>();
		links = new ArrayList<GraphLink>();
	}
	
	public void addNode(GraphNode node){
		nodes.add(node);
		lastNodeIndex++;
		//int index = nodes.size();
		node.setIndex(lastNodeIndex);
		
		for(GraphPort p : node.getInPorts())
			addPort(p);
		
		for(GraphPort p : node.getOutPorts()){
			addPort(p);
		}
	}
	
	private void addPort(GraphPort p){
		ports.add(p);
		//int index = ports.size();
		lastPortIndex ++;
		p.setIndex(lastPortIndex);
	}
	
	private void addDeserializedPort(GraphPort p){
		ports.add(p);
		
	}
	
	public GraphPort getPort(int index){
		for(GraphPort p : ports)
			if( p.index == index)
				return p; 
		return null;
	}
	
	public GraphNode getNode(int index){
		for(GraphNode n : nodes)
			if( n.getIndex() == index)
				return n; 
		//return nodes.get(index - 1);
		return null;
	}
	
	public GraphNode getNodeOfPort(int index){
		for(GraphNode n : nodes){
			for(GraphPort p : n.getInPorts() )
				if( p.getIndex() == index )
					return n;
			
			for(GraphPort p : n.getOutPorts() )
				if( p.getIndex() == index )
					return n;
		}
		return null;
	}
	
	public void addLink(GraphLink link){
		links.add(link);
	}
	
	public void addLink(GraphPort source, GraphPort target){
		GraphLink l = new GraphLink();
		l.setSource(source.getIndex());
		l.setTarget(target.getIndex());
		links.add(l);
	}
	
	public GraphNode getTarget(GraphLink link){
		return nodes.get(link.getTarget());
	}
	
	public GraphNode getSource(GraphLink link){
		return nodes.get(link.getSource());
	}
	public GraphLink getLinkBySource(GraphPort outPort){
		for(GraphLink link : links){
			if( link.getSource() == outPort.getIndex() )
				return link;
		}
		return null;
	}
	
	public GraphLink getLinkByTarget(GraphPort inPort){
		for(GraphLink link : links){
			if( link.getTarget() == inPort.getIndex() )
				return link;
		}
		return null;
	}
	
	private static Gson getGson(OperationRegister r){
		GsonBuilder b = new GsonBuilder().setPrettyPrinting();
		b.registerTypeHierarchyAdapter(Operation.class, new OperationSerializer());
		b.registerTypeHierarchyAdapter(NodeValue.class, new NodeValueHandler(r));
		
		System.out.println("Registered operation class adapter");
		return b.create();
	}
	
	public static void saveTo(File file, Graph graph, OperationRegister r) throws IOException{
		Gson g = getGson(r);
		String str = g.toJson(graph);
		
		FileWriter fw = new FileWriter(file);
		fw.write(str);
		fw.close();
	}
	private void setLastNodeIndex(int lastNodeIndex){
		this.lastNodeIndex = lastNodeIndex;
	}
	
	private void setLastPortIndex(int lastPortIndex){
		this.lastPortIndex = lastPortIndex;
	}
	
	public static Graph loadFrom(File file, OperationRegister r) throws FileNotFoundException{
		Gson g = getGson(r);
		
		FileReader fr = new FileReader(file);
		Graph graph = g.fromJson(fr, Graph.class);
		
		int lastNodeIndex = -1;
		int lastPortIndex = -1;
		
		for(GraphNode node : graph.getNodes()){
			for(GraphPort p : node.getInPorts()){
				graph.addDeserializedPort(p);
				if( lastPortIndex < p.getIndex() )
					lastPortIndex = p.getIndex();
			}
			
			for(GraphPort p : node.getOutPorts()){
				graph.addDeserializedPort(p);
				if( lastPortIndex < p.getIndex() )
					lastPortIndex = p.getIndex();
			}
			
			if( lastNodeIndex < node.getIndex() )
				lastNodeIndex = node.getIndex();
		}
		
		return graph;
	}

	public List<GraphNode> getNodes() {
		return nodes;
	}
	
	public void linkValueToPort(GraphNode value, GraphPort port){
		GraphPort out = value.getOutPorts().get(0);
		addLink(out, port);
	}
	
	public boolean isInput(GraphPort port){
		for(GraphNode n : nodes){
			for(GraphPort p : n.getInPorts() )
				if( p.getIndex() == port.getIndex() )
					return true;
			
			for(GraphPort p : n.getOutPorts() )
				if( p.getIndex() == port.getIndex() )
					return false;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Graph [************nodes=\n" + nodes + ", *************ports=\n" + ports + ", **************links=\n" + links + "]";
	}

	public GraphNode getNodeAt(int x, int y) {
		for(GraphNode n : nodes)
			if( n.getBounds().contains(x, y))
				return n;
		
		return null;
	}

	public GraphPort getPortAt(int x, int y) {
		for(GraphNode n : nodes){
			for(GraphPort p : n.getInPorts())
				if( p.contains(x, y) )
					return p;
			
			for(GraphPort p : n.getOutPorts())
				if( p.contains(x, y) )
					return p;
		}
		return null;
	}

	public Rectangle getBoundsFor(GraphPort port) {
		return port.getBounds();
	}

	public ArrayList<GraphLink> getLinks() {
		return links;
	}

	public void deleteNode(GraphNode n) {
		nodes.remove(n);
		
		for(GraphPort p : n.getInPorts())
			deletePort(p);
		
		for(GraphPort p : n.getOutPorts())
			deletePort(p);
	}
	
	public void deletePort(GraphPort p){
		ports.remove(p);
		
		for(int i = 0; i < links.size(); i++ ){
			GraphLink l = links.get(i);
			if( l.getSource() == p.getIndex() || l.getTarget() == p.getIndex() ){
				links.remove(l);
				i--;
			}
		}
		
	}
	
	
}
