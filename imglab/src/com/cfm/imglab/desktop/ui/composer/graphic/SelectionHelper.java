package com.cfm.imglab.desktop.ui.composer.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SelectionHelper {
	
	private static final Rectangle	selHelper	= new Rectangle(0, 0, 5, 5);
	private static final float[][]	helperPositons = {
			// TopLeft
			{0, -1, 0, -1},
			// TopMiddle
			{0.5f, -0.5f, 0, -1},
			// TopRight
			{1, 0, 0, -1},
			
			// MiddleLeft
			{0, -1, 0.5f, -0.5f},
			// MiddleCenter
			{0.5f, -0.5f, 0.5f, -0.5f}, //Help in analisis.
			// MiddleRight
			{1,  0, 0.5f, -0.5f},
			
			// BottomLeft
			{0, -1, 1, 0},
			// BottomMiddle
			{0.5f, -0.5f, 1, 0},
			// BottomRight
			{1, 0, 1, 0}
	};
	
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
	
	public static final int CURSOR_FOR_POS[] = {
		Cursor.NW_RESIZE_CURSOR,
		Cursor.N_RESIZE_CURSOR,
		Cursor.NE_RESIZE_CURSOR,
		Cursor.W_RESIZE_CURSOR,
		Cursor.MOVE_CURSOR,
		Cursor.E_RESIZE_CURSOR,
		Cursor.SW_RESIZE_CURSOR,
		Cursor.S_RESIZE_CURSOR,
		Cursor.SE_RESIZE_CURSOR
	};
	
	public static int getContaintmentFor(FilterNodeView node, int x, int y){
		for(int i = 0; i < helperPositons.length; i++){
			positionHelper(node.getBounds(), helperPositons[i]);
			if( selHelper.contains(x, y) )
				return i;
		}
		
		return -1;
	}
	
	public static void renderFor(FilterNodeView node, Graphics2D g, Color color){
		g.setColor(color);
		
		for(float[] helperPos : helperPositons){
			positionHelper(node.getBounds(), helperPos);
			g.fill(selHelper);
		}
	}
	
	private static void positionHelper(Rectangle bounds, float[] arr){
		positionHelper(bounds, arr[0], arr[1], arr[2], arr[3]);
	}
	
	private static void positionHelper(Rectangle bounds, float hAlpha, float hBeta, float vAlpha, float vBeta) {
		selHelper.x = (int) (bounds.x + hAlpha * bounds.width + hBeta * selHelper.width);
		selHelper.y = (int) (bounds.y + vAlpha * bounds.height + vBeta * selHelper.height);		
	}
	
	private static final Color selectedColor = new Color(0.0f, 0.0f, 1, 0.05f);
	
	public static void renderSelection(PartShape p, Graphics2D g){
		
		Color selectedColor = new Color(0.0f, 0.0f, 1f, 1f);
		g.setStroke(new BasicStroke(3f));
		g.setColor(selectedColor);
		g.draw(p.getBounds());
	}
}
