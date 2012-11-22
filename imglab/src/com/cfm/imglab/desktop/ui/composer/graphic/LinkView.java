package com.cfm.imglab.desktop.ui.composer.graphic;

import java.awt.Color;
import java.awt.Graphics2D;

import cfm.neograph.core.Graph;
import cfm.neograph.core.GraphLink;
import cfm.neograph.core.GraphPort;


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
	
	public GraphLink getLink(){
		
		GraphPort p1 = source.getPort();
		GraphPort p2 = target.getPort();
		
		if( !source.isInput() ){
			p1 = target.getPort();
			p2 = source.getPort();
		}
		
		GraphLink l = new GraphLink();
		l.setSource(p1.getIndex());
		l.setSource(p2.getIndex());
		
		return l;
	}
	
}
