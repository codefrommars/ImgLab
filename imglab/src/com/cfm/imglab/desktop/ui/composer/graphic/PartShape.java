package com.cfm.imglab.desktop.ui.composer.graphic;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class PartShape {
	protected Rectangle bounds;
	public static final int MIN_WIDTH = 50, MIN_HEIGHT = 50;
	public PartShape(){
		bounds = new Rectangle();
	}
	
	public int getX(){
		return bounds.x;
	}
	public int getY(){
		return bounds.y;
	}
	public int getWidth(){
		return bounds.width;
	}
	public int getHeight(){
		return bounds.height;
	}
	
	public void move(int dx, int dy) {
		setPosition(bounds.x + dx, bounds.y + dy);
	}
	
	public boolean contains(int x, int y){
		return bounds.contains(x, y);
	}
	
	public void setPosition(int x, int y){
		bounds.x = x;
		bounds.y = y;
	}
	
	public Rectangle getBounds(){
		return bounds;
	}
	
	public int getCorner(int x, int y){
		int radiusCorner = 5;
		
		int xCorn = 1;
		int yCorn = 1;
		
		if( Math.abs(x - bounds.x) < radiusCorner  )
			xCorn = 0;
		
		if( Math.abs(x - (bounds.x + bounds.width)) < radiusCorner )
			xCorn = 2;
		
		if( Math.abs(y - bounds.y) < radiusCorner  )
			yCorn = 0;
		
		if( Math.abs(y - (bounds.y + bounds.height)) < radiusCorner )
			yCorn = 2;
		
		return 3 * yCorn + xCorn;
	}
	
	public void grow(int direction, int x, int y){
		switch(direction){
			case SelectionHelper.HELPOS_CENTER_RIGHT:
				growRight(x);
				break;
			case SelectionHelper.HELPOS_CENTER_LEFT:
				growLeft(x);
				break;
			case SelectionHelper.HELPOS_BOTTOM_MIDDLE:
				growDown(y);
				break;
			case SelectionHelper.HELPOS_TOP_MIDDLE:
				growUp(y);
				break;
			case SelectionHelper.HELPOS_TOP_LEFT:
				growLeft(x);
				growUp(y);
				break;
			case SelectionHelper.HELPOS_TOP_RIGHT:
				growRight(x);
				growUp(y);
				break;
			case SelectionHelper.HELPOS_BOTTOM_LEFT:
				growLeft(x);
				growDown(y);
				break;
			case SelectionHelper.HELPOS_BOTTOM_RIGHT:
				growRight(x);
				growDown(y);
				break;
		}
	}
	
	private void growDown(int y){
		int newHeight = y - bounds.y;
		if( newHeight > MIN_HEIGHT)
			bounds.height = newHeight;
	}
	
	private void growUp(int y){
		int newHeight = bounds.y - y + bounds.height;
		if( newHeight > MIN_HEIGHT){
			bounds.height = newHeight;
			bounds.y = y;
		}
	}
	
	private void growRight(int x){
		int newWidth = x - bounds.x;
		if( newWidth > MIN_WIDTH)
			bounds.width = newWidth;
	}
	
	private void growLeft(int x){
		int newWidth = bounds.x - x + bounds.width;
		if( newWidth > MIN_WIDTH){
			bounds.width = newWidth;
			bounds.x = x;
		}
	}
	
	public abstract boolean couldContain(int x, int y);
	
	public abstract void paint(Graphics2D g);
	
	public void onHover(){
		
	}
}