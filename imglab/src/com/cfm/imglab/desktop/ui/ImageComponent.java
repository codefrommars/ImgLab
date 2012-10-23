package com.cfm.imglab.desktop.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class ImageComponent extends JLayeredPane implements MouseWheelListener{
	private JScrollPane scrollPane;
	private ImageCanvas canvas;
	private JPanel toolbar;
	
	public ImageComponent(){
		
		addComponentListener(new ComponentAdapter() {

	         private void resizeLayers() {
//	            final JViewport viewport = scrollPane.getViewport();
	            scrollPane.setBounds(0, 0, getWidth(), getHeight());
//	            blueRectPanel.setBounds(viewport.getBounds());
	            SwingUtilities.invokeLater(new Runnable() {
	               public void run() {
//	                  blueRectPanel.setBounds(viewport.getBounds());
	               }
	            });
	         }

	         @Override
	         public void componentShown(ComponentEvent e) {
	            resizeLayers();
	         }

	         @Override
	         public void componentResized(ComponentEvent e) {
	            resizeLayers();
	         }
	      });
		
		
		canvas = new ImageCanvas();
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(canvas);
		add(scrollPane, 1, 0);
		scrollPane.addMouseWheelListener(this);
		
		setPreferredSize(new Dimension(450, 450));
		
	}
	
	public void setupToolbar(){
		toolbar = new JPanel();
		toolbar.setLayout(new FlowLayout());
		
		JButton zoom = new JButton("Zoom");
		
		add(toolbar, 2, 0);
	}
	
	public void setImage(BufferedImage img){
		canvas.setImage(img);
		scrollPane.revalidate();
		//repaint();
		revalidate();
		repaint();
		//scrollPane.setSize(getSize());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if( (e.getModifiersEx() & e.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK){
			canvas.zoom(e.getUnitsToScroll() < 0);
			scrollPane.revalidate();
		}
	}
	
	
}
