package cfm.neograph.core;


import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

public class GraphNode {
	private String label;
	private int index;
	private ArrayList<GraphPort> inPorts, outPorts;
	private NodeValue value;
	
	public static final int MIN_WIDTH = 50, MIN_HEIGHT = 50, PORT_SEP = 10, VAR_WIDTH = 50, VAR_HEIGHT = 50;
	protected Rectangle bounds = new Rectangle();
	
	
	protected GraphNode() {
		inPorts = new ArrayList<GraphPort>();
		outPorts = new ArrayList<GraphPort>();
	}
	
	public GraphNode(GraphNode copy){
		this();
		inPorts = copy.inPorts;
		outPorts = copy.outPorts;
		index = copy.index;
		label = copy.label;
		value = copy.value;
	}
	
	public GraphNode(String label) {
		this();
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getIndex() {
		return index;
	}
	public void setIndex(int index){
		this.index = index;
	}
	
	public ArrayList<GraphPort> getInPorts() {
		return inPorts;
	}

	public ArrayList<GraphPort> getOutPorts() {
		return outPorts;
	}

	@Override
	public String toString() {
		return "Node [label=" + label + ", \nindex=" + index + ", \ninPorts=" + inPorts + ", \noutPorts=" + outPorts + "]";
	}
	
	public void addPort(GraphPort p, boolean in){
//		p.setNodeId(index);
		
		if( in )
			inPorts.add(p);
		else
			outPorts.add(p);
		
		resize();
		
		if( in ){
			relocatePorts(inPorts, getX() - GraphPort.portWidth / 2);
		}else{
			relocatePorts(outPorts, getX() + getWidth() - GraphPort.portWidth / 2);
		}
	}
	
	public boolean containsPort(GraphPort p){
		return inPorts.contains(p) || outPorts.contains(p);
	}
	
	public void boom(){
		System.out.println("Booming: " + getLabel());
	}

	public NodeValue getValue() {
		return value;
	}

	public void setValue(NodeValue value) {
		this.value = value;
	}
	
	public GraphPort getInputPortByName(String name){
		for(GraphPort p : inPorts)
			if( name.equalsIgnoreCase(p.getLabel()) )
				return p;
		
		return null;
	}
	
	public GraphPort getOuputPortByName(String name){
		for(GraphPort p : outPorts)
			if( name.equalsIgnoreCase(p.getLabel()) )
				return p;
		
		return null;
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
		relocatePorts(inPorts, getX() - GraphPort.portWidth / 2);
		relocatePorts(outPorts, getX() + getWidth() - GraphPort.portWidth / 2);
	}

	public boolean isValueNode() {
		return value.getType() == GraphNodeType.Boolean || value.getType() == GraphNodeType.Image || value.getType() == GraphNodeType.Number || value.getType() == GraphNodeType.String;
	}

	public void move(int dx, int dy) {
		setPosition(bounds.x + dx, bounds.y + dy);
		
	}

	public Shape getBounds() {
		return bounds;
	}
	
	private void resize(){
		int width = isValueNode() ? VAR_WIDTH : MIN_WIDTH;
		int maxHeight = isValueNode() ? VAR_HEIGHT : MIN_HEIGHT;
		
		int maxPorts = getInPorts().size();
		if( getOutPorts().size() > maxPorts )
			maxPorts = getOutPorts().size();
		
		int height = (GraphPort.portHeight + PORT_SEP) * maxPorts + PORT_SEP;
		
		height = Math.max(height, maxHeight);
		
		bounds = new Rectangle(0, 0, width, height);
		
	}
	
	private void relocatePorts(List<GraphPort> ports, int xOffset){
		int n = ports.size();
		int gap = getHeight() / (n + 1);
		int curr = 1;
		
		for(GraphPort p : ports){
			int x = xOffset;
			int y = getY() + curr * gap - GraphPort.portHeight / 2;
			p.setPosition(x, y);
			curr++;
		}
	}
	
//	public int getCorner(int x, int y){
//		int radiusCorner = 5;
//		
//		int xCorn = 1;
//		int yCorn = 1;
//		
//		if( Math.abs(x - bounds.x) < radiusCorner  )
//			xCorn = 0;
//		
//		if( Math.abs(x - (bounds.x + bounds.width)) < radiusCorner )
//			xCorn = 2;
//		
//		if( Math.abs(y - bounds.y) < radiusCorner  )
//			yCorn = 0;
//		
//		if( Math.abs(y - (bounds.y + bounds.height)) < radiusCorner )
//			yCorn = 2;
//		
//		return 3 * yCorn + xCorn;
//	}
//	
//	public void grow(int direction, int x, int y){
//		switch(direction){
//			case SelectionHelper.HELPOS_CENTER_RIGHT:
//				growRight(x);
//				break;
//			case SelectionHelper.HELPOS_CENTER_LEFT:
//				growLeft(x);
//				break;
//			case SelectionHelper.HELPOS_BOTTOM_MIDDLE:
//				growDown(y);
//				break;
//			case SelectionHelper.HELPOS_TOP_MIDDLE:
//				growUp(y);
//				break;
//			case SelectionHelper.HELPOS_TOP_LEFT:
//				growLeft(x);
//				growUp(y);
//				break;
//			case SelectionHelper.HELPOS_TOP_RIGHT:
//				growRight(x);
//				growUp(y);
//				break;
//			case SelectionHelper.HELPOS_BOTTOM_LEFT:
//				growLeft(x);
//				growDown(y);
//				break;
//			case SelectionHelper.HELPOS_BOTTOM_RIGHT:
//				growRight(x);
//				growDown(y);
//				break;
//		}
//	}
//	
//	private void growDown(int y){
//		int newHeight = y - bounds.y;
//		if( newHeight > MIN_HEIGHT)
//			bounds.height = newHeight;
//	}
//	
//	private void growUp(int y){
//		int newHeight = bounds.y - y + bounds.height;
//		if( newHeight > MIN_HEIGHT){
//			bounds.height = newHeight;
//			bounds.y = y;
//		}
//	}
//	
//	private void growRight(int x){
//		int newWidth = x - bounds.x;
//		if( newWidth > MIN_WIDTH)
//			bounds.width = newWidth;
//	}
//	
//	private void growLeft(int x){
//		int newWidth = bounds.x - x + bounds.width;
//		if( newWidth > MIN_WIDTH){
//			bounds.width = newWidth;
//			bounds.x = x;
//		}
//	}
//	
//	public boolean isValueNode(){
//		return 	getValue().getType() == GraphNodeType.Boolean ||
//				getValue().getType() == GraphNodeType.Image ||
//				getValue().getType() == GraphNodeType.Number ||
//				getValue().getType() == GraphNodeType.String;
//	}
//	
//	public boolean isOperation(){
//		return getValue().getType() == GraphNodeType.Operation;
//	}
}
