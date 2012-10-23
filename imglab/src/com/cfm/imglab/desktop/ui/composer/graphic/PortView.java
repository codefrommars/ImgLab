package com.cfm.imglab.desktop.ui.composer.graphic;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cfm.imglab.composer.InputPort;
import com.cfm.imglab.composer.Port;

public class PortView extends PartShape {
	
	private Port port;
	
	public static final int MIN_WIDTH = 20, MIN_HEIGHT = 12;
	
	public PortView(Port port){
		super();
		this.port = port;
		bounds.width = MIN_WIDTH;
		bounds.height = MIN_HEIGHT;
	}
	
	@Override
	public boolean couldContain(int x, int y) {
		return contains(x, y);
	}

	public boolean isInput() {
		return port instanceof InputPort;
	}

	public Port getPort() {
		return port;
	}

	@Override
	public void paint(Graphics2D g) {
		if( isInput() )
			g.setColor(Color.yellow);
		else
			g.setColor(Color.red);
		
		g.fill(getBounds());
		g.setColor(Color.black);
		g.draw(getBounds());
		
		g.drawString("[" + port.getValue().getName() + "]", getX(), getY());
	}
	
}
