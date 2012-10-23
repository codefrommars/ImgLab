package com.cfm.imglab;

import java.awt.image.BufferedImage;

public class ImageUtils {

	public static void getRGBA(BufferedImage img, int x, int y, int[] rgba){
		int px = img.getRGB(x, y);
		
		int a = (px >> 24) & 0x000000FF;
		int r = (px >> 16) & 0x000000FF;
		int g = (px >> 8 ) & 0x000000FF;
		int b = (px >> 0 ) & 0x000000FF;
		
		rgba[0] = r;
		rgba[1] = g;
		rgba[2] = b;
		rgba[3] = a;
	}
	
	public static void setRGBA(BufferedImage img, int x, int y, int[] rgba){
		if(!inBounds(img, x, y)) return;
		int color = (rgba[3] << 24) + (rgba[0] << 16) + (rgba[1] << 8) + (rgba[2] << 0);
		img.setRGB(x, y, color);
	}
	
	
	public static boolean inBounds(BufferedImage img, int x, int y){
		return ( x < img.getWidth() && x >= 0 && y < img.getHeight() && y >= 0 );
	}
	
	public static void saturate(int[] rgba){
		rgba[0] = Math.min(rgba[0], 255);
		rgba[1] = Math.min(rgba[1], 255);
		rgba[2] = Math.min(rgba[2], 255);
		rgba[3] = Math.min(rgba[3], 255);
		
		rgba[0] = Math.max(rgba[0], 0);
		rgba[1] = Math.max(rgba[1], 0);
		rgba[2] = Math.max(rgba[2], 0);
		rgba[3] = Math.max(rgba[3], 0);
	}
}
