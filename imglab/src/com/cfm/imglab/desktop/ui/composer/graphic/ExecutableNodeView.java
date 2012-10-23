package com.cfm.imglab.desktop.ui.composer.graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.cfm.imglab.composer.ExecutableNode;
import com.cfm.imglab.composer.Port;

public class ExecutableNodeView extends PartShape implements HasInputPorts{
	
	private List<PortView> inputPorts;
	private ExecutableNode executableNode;
	
	private Rectangle extendedBounds = new Rectangle();
	private int extendedRadious = 10;
	
	public ExecutableNodeView(ExecutableNode node){
		super();
		this.executableNode = node;
		bounds.width = MIN_WIDTH;
		bounds.height = MIN_HEIGHT;
		
		inputPorts = new ArrayList<PortView>();
		for(Port p : node.getInputs()){
			PortView pv = new PortView(p);
			inputPorts.add(pv);
		}
	}
	
	@Override
	public List<PortView> getInputPorts() {
		return inputPorts;
	}

	@Override
	public boolean couldContain(int x, int y) {
		extendedBounds.x = bounds.x - extendedRadious;
		extendedBounds.y = bounds.y - extendedRadious;
		extendedBounds.width = bounds.width + 2 * extendedRadious;
		extendedBounds.height = bounds.height + 2 * extendedRadious;
		return extendedBounds.contains(x, y);
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.orange);
		g.fill(bounds);
		
		g.setColor(Color.black);
		g.drawString(executableNode.toString(), bounds.x, bounds.y);
		g.draw(bounds);
		
		for(PortView p : inputPorts){
			p.paint(g);
		}
	}
	
	public ExecutableNode getExecutableNode() {
		return executableNode;
	}
	
	@Override
	public void grow(int direction, int x, int y) {
		super.grow(direction, x, y);
		relocatePorts();
	}

	@Override
	public void setPosition(int x, int y) {
		super.setPosition(x, y);
		relocatePorts();
	}
	
	private void relocatePorts(){
		relocatePorts(inputPorts, bounds.x - PortView.MIN_WIDTH / 2);
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
}
