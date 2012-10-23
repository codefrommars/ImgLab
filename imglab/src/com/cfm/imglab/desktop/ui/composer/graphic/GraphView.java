package com.cfm.imglab.desktop.ui.composer.graphic;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.cfm.imglab.composer.ExecutableNode;
import com.cfm.imglab.composer.FilterNode;
import com.cfm.imglab.composer.Graph;
import com.cfm.imglab.composer.ValueNode;

public class GraphView {
	private List<PartShape> nodes;
	private List<LinkView> links;
	
	public GraphView(){
		nodes = new ArrayList<PartShape>();
		links = new ArrayList<LinkView>();
	}
	
	public List<PartShape> getNodes() {
		return nodes;
	}
	public void setNodes(List<PartShape> nodes) {
		this.nodes = nodes;
	}
	public List<LinkView> getLinks() {
		return links;
	}
	public void setLinks(List<LinkView> links) {
		this.links = links;
	}
	
	public void addNode(PartShape node){
		nodes.add(node);
	}
	
	public void addLink(LinkView link){
		links.add(link);
	}
	
	public PartShape getNodeAt(int x, int y){
		for(PartShape n : nodes){
			if( n.couldContain(x, y) ){
				return n;
			}
		}
		return null;
	}
	
	public void paint(Graphics2D g2){
		for(PartShape n : nodes){
			n.paint(g2);
		}
		
		for(LinkView l : links)
			l.render(g2);
	}
	
	public Graph getGraph(){
		Graph g = new Graph();
		
		for(PartShape p : nodes){
			if( p instanceof FilterNodeView ){
				FilterNodeView fnv = (FilterNodeView)p;
				FilterNode f = fnv.getFilter();
				g.addNode(f);
			}
			
			if( p instanceof ValueNodeView ){
				ValueNodeView vnw = (ValueNodeView)p;
				ValueNode v = vnw.getValueNode();
				g.addNode(v);
			}
			
			if( p instanceof ExecutableNodeView ){
				ExecutableNodeView e = (ExecutableNodeView)p;
				ExecutableNode en = e.getExecutableNode();
				g.addNode(en);
			}
		}
		
		for( LinkView l : links){
			g.addLink(l.getLink());
		}
		
		return g;
	}
}
