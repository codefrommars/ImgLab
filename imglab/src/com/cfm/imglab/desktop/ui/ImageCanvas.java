package com.cfm.imglab.desktop.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class ImageCanvas extends JComponent {
	private BufferedImage image;
	private int zoom;
	
	public ImageCanvas() {
		setPreferredSize(new Dimension(400, 400));
		zoom = 1;
	}

	@Override
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		paintImage(g);
	}

	private void paintImage(Graphics2D g) {
		if( image == null )
			return;
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g.drawImage(image, 0, 0, image.getWidth() * zoom , image.getHeight() * zoom, this);
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		setZoom(1);
	}
	
	public void setZoom(int zoom){
		this.zoom = zoom;
		this.zoom = Math.min(zoom, 32);
		this.zoom = Math.max(1, zoom);
		setPreferredSize(new Dimension(image.getWidth() * zoom, image.getHeight() * zoom));
		repaint();
	}

	public void zoom(boolean in) {
		if( in )
			setZoom(zoom * 2);
		else
			setZoom(zoom / 2);
	}

	
	
}
