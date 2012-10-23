package com.cfm.imglab.desktop.ui.composer.graphic;

import java.awt.Graphics2D;

public class ShapeControl extends PartShape{
	
	private int direction = 0;
	
	public static final int 
		HELPOS_TOP_LEFT = 0, 
		HELPOS_TOP_MIDDLE = 1, 
		HELPOS_TOP_RIGHT = 2, 
		HELPOS_CENTER_LEFT = 3, 
		HELPOS_CENTER_MIDDLE = 4, 
		HELPOS_CENTER_RIGHT = 5, 
		HELPOS_BOTTOM_LEFT = 6, 
		HELPOS_BOTTOM_MIDDLE = 7, 
		HELPOS_BOTTOM_RIGHT = 8; 
	
	public ShapeControl(int direction){
		super();
		this.direction = direction;
	}
	
	@Override
	public boolean couldContain(int x, int y) {
		return contains(x, y);
	}

	public int getDirection() {
		return direction;
	}

	@Override
	public void paint(Graphics2D g) {
		
	}
	
}