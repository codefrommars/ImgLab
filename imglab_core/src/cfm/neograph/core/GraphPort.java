package cfm.neograph.core;

import java.awt.Rectangle;

import cfm.neograph.core.type.RuntimeType;

public class GraphPort {
	
	
	public static final int portHeight = 10,
			portWidth = 15,
			halfPortWidth = 8;
	
	protected String label;
	protected int index;
	protected RuntimeType type;
	
//	protected int nodeId;
	protected Rectangle bounds = new Rectangle(portWidth, portHeight);
	
	public GraphPort(){
		
	}
	
	public GraphPort(String label){
		this();
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}

	public RuntimeType getType() {
		return type;
	}

	public void setType(RuntimeType type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if( !(obj instanceof GraphPort) )
			return false;
		
		GraphPort p = (GraphPort)obj;
		return p.index == index;
	}

	@Override
	public String toString() {
		return "Port [label=" + label + ", index=" + index + ", type=" + type + "]";
	}

//	public int getNodeId() {
//		return nodeId;
//	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public int getX() {
		return (int)bounds.getX();
	}

	public int getY() {
		return (int)bounds.getY();
	}

	public int getWidth() {
		return (int)bounds.getWidth();
	}

	public int getHeight() {
		return (int)bounds.getHeight();
	}

	public void setPosition(int x, int y) {
		bounds.setLocation(x, y);
	}

//	public void setNodeId(int nodeId) {
//		this.nodeId = nodeId;
//	}

	public boolean contains(int x, int y) {
		return bounds.contains(x, y);
	}

	public int getCenterX() {
		return (int)bounds.getCenterX();
	}

	public int getCenterY() {
		return (int)bounds.getCenterY();
	}
	
}