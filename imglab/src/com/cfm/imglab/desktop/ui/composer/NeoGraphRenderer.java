package com.cfm.imglab.desktop.ui.composer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import com.cfm.imglab.ImageDescriptor;

import cfm.neograph.core.Graph;
import cfm.neograph.core.GraphLink;
import cfm.neograph.core.GraphNode;
import cfm.neograph.core.GraphPort;

public class NeoGraphRenderer {

	public void renderGraphView(Graph view, Graphics2D g) {
		for (GraphNode n : view.getNodes()) {
			if (n.isValueNode())
				renderVariableNode(n, g);
			else
				renderOperationNode(n, g);
		}

		for (GraphLink l : view.getLinks()) {
			renderGraphLink(view, l, g);
		}
	}

	private void renderVariableNode(GraphNode n, Graphics2D g) {
		if (n.getLabel() != null) {
			g.setColor(Color.black);
			g.drawString(n.getLabel(), n.getX(), n.getY());
		}

		g.setColor(Color.green);
		g.fillOval(n.getX(), n.getY(), n.getHeight(), n.getHeight());
		
		g.setColor(Color.black);
		g.drawOval(n.getX(), n.getY(), n.getHeight(), n.getHeight());
		
		// Render Port
		renderPorts(n.getOutPorts(), Color.red, g);
		
		//Render Value
		switch(n.getValue().getType()){
			case Boolean:
			case Number:
			case String:
				break;
			case Image:
				renderImageNode(n, g);
		}
	}

	private void renderImageNode(GraphNode n, Graphics2D g) {
		
		int offset = 10;
		
		if( n.getValue().getValue() != null )
			g.drawImage( ((ImageDescriptor)n.getValue().getValue()).getIcon().getImage(), n.getX() + offset, n.getY() + offset, n.getWidth() - offset * 2, n.getHeight() - offset * 2, null);
	}

	private void renderOperationNode(GraphNode n, Graphics2D g) {
		if (n.getLabel() != null) {
			g.setColor(Color.black);
			g.drawString(n.getLabel(), n.getX(), n.getY());
		}

		g.setColor(Color.green);
		g.fill(n.getBounds());

		g.setColor(Color.black);
		g.draw(n.getBounds());

		// Render Ports
		renderPorts(n.getInPorts(), Color.yellow, g);
		renderPorts(n.getOutPorts(), Color.red, g);
		// renderPorts(n, n.getOutPorts(), n.getX() + n.getWidth() -
		// halfPortWidth, Color.red, g);
	}

	private void renderPorts(List<GraphPort> ports, Color color, Graphics2D g) {
		for (GraphPort p : ports) {

			g.setColor(color);
			g.fill(p.getBounds());

			g.setColor(Color.black);
			g.draw(p.getBounds());
			
			g.drawString(p.getLabel(), p.getX() - 10, p.getY());
		}

	}

	public void renderGraphLink(Graph view, GraphLink link, Graphics2D g) {
		g.setColor(Color.blue);

		GraphPort p1 = view.getPort(link.getSource());
		GraphPort p2 = view.getPort(link.getTarget());

		g.drawLine(p1.getCenterX(), p1.getCenterY(), p2.getCenterX(), p2.getCenterY());
		// g.drawLine(link.x1, link.y1, link.x2, link.y2);
	}

	public void renderTempLink(int x1, int y1, int x2, int y2, boolean possible, Graphics2D g) {
		Color c = Color.blue;
		if (!possible) c = Color.red;

		g.setColor(c);
		g.drawLine(x1, y1, x2, y2);
	}

	public void renderSelected(GraphNode p, Graphics2D g2) {
		g2.setColor(new Color(0.5f, 0.5f, 1.0f, 0.4f));
		g2.fill(p.getBounds());
	}
}
