package com.cfm.imglab.desktop.ui.composer.graphic;

import java.awt.Color;
import java.awt.Graphics2D;

import cfm.neograph.core.GraphPort;

public class PortView extends PartShape {
	
	private GraphPort port;
	private boolean isInput;
	
	public static final int MIN_WIDTH = 20, MIN_HEIGHT = 12;
	
	public PortView(GraphPort port, boolean isInput){
		super();
		this.port = port;
		this.isInput = isInput;
		bounds.width = MIN_WIDTH;
		bounds.height = MIN_HEIGHT;
	}
	
	@Override
	public boolean couldContain(int x, int y) {
		return contains(x, y);
	}

	public GraphPort getPort() {
		return port;
	}

	public boolean isInput() {
		return isInput;
	}

	@Override
	public void paint(Graphics2D g) {
		if( isInput )
			g.setColor(Color.yellow);
		else
			g.setColor(Color.red);
		
		g.fill(getBounds());
		g.setColor(Color.black);
		g.draw(getBounds());
		
		g.drawString("[" + port.getLabel() + "]", getX(), getY());
	}
	
}
