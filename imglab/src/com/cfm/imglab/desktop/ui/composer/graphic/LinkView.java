package com.cfm.imglab.desktop.ui.composer.graphic;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cfm.imglab.composer.InputPort;
import com.cfm.imglab.composer.Link;
import com.cfm.imglab.composer.OutputPort;
import com.cfm.imglab.composer.Port;


public class LinkView {
	
	private Color color;
	private PortView source, target;
	
	public void render(Graphics2D g){
		if( source == target )
			return;
		
		g.setColor(color);
		g.drawLine(source.getX(), source.getY(), target.getX(), target.getY());
	}

	public PortView getSource() {
		return source;
	}

	public void setSource(PortView origin) {
		this.source = origin;
	}

	public PortView getTarget() {
		return target;
	}

	public void setTarget(PortView target) {
		this.target = target;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public Link getLink(){
		
		Port p1 = source.getPort();
		Port p2 = target.getPort();
		
		if( p1 instanceof OutputPort ){
			p1 = target.getPort();
			p2 = source.getPort();
		}
		
		return new Link((OutputPort)p2, (InputPort)p1);
	}
	
}
