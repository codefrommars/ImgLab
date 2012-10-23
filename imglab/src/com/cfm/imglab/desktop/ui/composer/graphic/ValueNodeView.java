package com.cfm.imglab.desktop.ui.composer.graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.cfm.imglab.composer.Port;
import com.cfm.imglab.composer.ValueNode;

public class ValueNodeView extends PartShape implements HasOutputPorts{
	private Rectangle extendedBounds = new Rectangle();
	private int extendedRadious = 10;
	private ValueNode node;
	
	public static final int MIN_WIDTH = 50, MIN_HEIGHT = 50;
	private List<PortView> outputPorts;
	
	public ValueNodeView(ValueNode node) {
		super();
		outputPorts = new ArrayList<PortView>();
		this.node = node;
		bounds.width = MIN_WIDTH;
		bounds.height = MIN_HEIGHT;
		
		for(Port port : node.getOutputs()){
			PortView pv = new PortView(port);
//			pv.setInput(false);
			outputPorts.add(pv);
		}
	}

	public void paint(Graphics2D g) {
		g.setColor(Color.lightGray);
		g.fill(bounds);

		g.setColor(Color.black);
		//g.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, new float[]{1.0f, 0.0f}, 2.0f  ));
		g.drawString(node.toString(), bounds.x, bounds.y);
		g.draw(bounds);
		
		renderValue(g);
		
		renderPorts(g, outputPorts, Color.red);
	}
	
	private void renderValue(Graphics2D g) {
		if(node.getValue().isImage()){
			renderImageValue(g);
			return;
		}
		
		g.setColor(Color.black);
		
		if( node.getValue().hasValue() )
			g.drawString(String.valueOf(node.getValue().getValue()), bounds.x + 5, bounds.y + 25);
	}

	private void renderImageValue(Graphics2D g) {
		
		if( node.getValue().hasValue() )
			g.drawImage(node.getValue().getAsImage().getIcon().getImage(), bounds.x + 5, bounds.y + 5, (int)bounds.getWidth() - 10, (int)bounds.getHeight() - 10, null);
	}

	private void renderPorts(Graphics2D g, List<PortView> ports, Color c){
		for(PortView p : ports){
			p.paint(g);
		}
	}
	
	private void relocatePorts(List<PortView> ports, int xOffset){
		int n = ports.size();
		int gap = bounds.height / (n + 1);
		
		int curr = 1;
		for(PortView p : ports){
			p.setPosition(xOffset, bounds.y + curr * gap - p.getHeight() / 2 );
			curr++;
		}
	}

	public void grow(int direction, int x, int y){
		switch(direction){
			case SelectionHelper.HELPOS_CENTER_RIGHT:
				growRight(x);
				break;
			case SelectionHelper.HELPOS_CENTER_LEFT:
				growLeft(x);
				break;
			case SelectionHelper.HELPOS_BOTTOM_MIDDLE:
				growDown(y);
				break;
			case SelectionHelper.HELPOS_TOP_MIDDLE:
				growUp(y);
				break;
			case SelectionHelper.HELPOS_TOP_LEFT:
				growLeft(x);
				growUp(y);
				break;
			case SelectionHelper.HELPOS_TOP_RIGHT:
				growRight(x);
				growUp(y);
				break;
			case SelectionHelper.HELPOS_BOTTOM_LEFT:
				growLeft(x);
				growDown(y);
				break;
			case SelectionHelper.HELPOS_BOTTOM_RIGHT:
				growRight(x);
				growDown(y);
				break;
		}
		
		relocatePorts();
	}
	
	private void growDown(int y){
		int newHeight = y - bounds.y;
		if( newHeight > MIN_HEIGHT)
			bounds.height = newHeight;
	}
	
	private void growUp(int y){
		int newHeight = bounds.y - y + bounds.height;
		if( newHeight > MIN_HEIGHT){
			bounds.height = newHeight;
			bounds.y = y;
		}
	}
	
	private void growRight(int x){
		int newWidth = x - bounds.x;
		if( newWidth > MIN_WIDTH)
			bounds.width = newWidth;
	}
	
	private void growLeft(int x){
		int newWidth = bounds.x - x + bounds.width;
		if( newWidth > MIN_WIDTH){
			bounds.width = newWidth;
			bounds.x = x;
		}
	}

	@Override
	public boolean couldContain(int x, int y) {
		extendedBounds.x = bounds.x - extendedRadious;
		extendedBounds.y = bounds.y - extendedRadious;
		extendedBounds.width = bounds.width + 2 * extendedRadious;
		extendedBounds.height = bounds.height + 2 * extendedRadious;
		return extendedBounds.contains(x, y);
	}

	public void move(int dx, int dy) {
		setPosition(bounds.x + dx, bounds.y + dy);
	}

	@Override
	public void setPosition(int x, int y) {
		super.setPosition(x, y);
		relocatePorts();
	}
	
	private void relocatePorts(){
		relocatePorts(outputPorts, bounds.x + bounds.width - PortView.MIN_WIDTH / 2);
	}
	
	public void drag(int x, int y){
		
	}

	public int getCorner(int x, int y){
		int radiusCorner = 5;
		
		int xCorn = 1;
		int yCorn = 1;
		
		if( Math.abs(x - bounds.x) < radiusCorner  )
			xCorn = 0;
		
		if( Math.abs(x - (bounds.x + bounds.width)) < radiusCorner )
			xCorn = 2;
		
		if( Math.abs(y - bounds.y) < radiusCorner  )
			yCorn = 0;
		
		if( Math.abs(y - (bounds.y + bounds.height)) < radiusCorner )
			yCorn = 2;
		
		return 3 * yCorn + xCorn;
	}

	public List<PortView> getOutputPorts() {
		return outputPorts;
	}

	public ValueNode getValueNode() {
		return node;
	}
	
}
