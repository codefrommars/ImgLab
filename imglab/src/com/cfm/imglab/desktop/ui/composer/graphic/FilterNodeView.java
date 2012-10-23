package com.cfm.imglab.desktop.ui.composer.graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.cfm.imglab.composer.FilterNode;
import com.cfm.imglab.composer.Port;

public class FilterNodeView extends PartShape implements HasOutputPorts, HasInputPorts{
	private Rectangle extendedBounds = new Rectangle();
	private int extendedRadious = 10;
	private List<PortView> inputPorts, outputPorts;
	
	private FilterNode filter;
		
	public FilterNodeView(FilterNode filter) {
		super();
		this.filter = filter;
		bounds.width = MIN_WIDTH;
		bounds.height = MIN_HEIGHT;
		
		inputPorts = new ArrayList<PortView>();
		outputPorts = new ArrayList<PortView>();
		
		for(Port port : filter.getInputs()){
			PortView pv = new PortView(port);
		//	pv.setInput(true);
			inputPorts.add(pv);
		}

		for(Port port : filter.getOutputs()){
			PortView pv = new PortView(port);
//			pv.setInput(false);
			outputPorts.add(pv);
		}
	}

	public void paint(Graphics2D g) {
		
		g.setColor(Color.green);
		g.fill(bounds);

		g.setColor(Color.black);
		g.drawString(filter.toString(), bounds.x, bounds.y);
		g.draw(bounds);
		
		renderPorts(g, inputPorts, Color.yellow);
		renderPorts(g, outputPorts, Color.red);
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

	@Override
	public boolean couldContain(int x, int y) {
		extendedBounds.x = bounds.x - extendedRadious;
		extendedBounds.y = bounds.y - extendedRadious;
		extendedBounds.width = bounds.width + 2 * extendedRadious;
		extendedBounds.height = bounds.height + 2 * extendedRadious;
		return extendedBounds.contains(x, y);
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
		relocatePorts(outputPorts, bounds.x + bounds.width - PortView.MIN_WIDTH / 2);
	}
	
	public void drag(int x, int y){
		
	}

	

	public FilterNode getFilter() {
		return filter;
	}

	public List<PortView> getInputPorts() {
		return inputPorts;
	}

	public List<PortView> getOutputPorts() {
		return outputPorts;
	}
	
}
