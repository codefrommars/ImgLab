package com.cfm.imglab;

import java.awt.image.BufferedImage;

public class ImageUtils {

	public static void getRGBA(BufferedImage img, int x, int y, int[] rgba) {
		int px = img.getRGB(x, y);
		getComponents(px, rgba);
		// int a = (px >> 24) & 0x000000FF;
		// int r = (px >> 16) & 0x000000FF;
		// int g = (px >> 8 ) & 0x000000FF;
		// int b = (px >> 0 ) & 0x000000FF;
		//
		// rgba[0] = r;
		// rgba[1] = g;
		// rgba[2] = b;
		// rgba[3] = a;
	}

	public static void getComponents(int px, int[] rgba) {
		int a = (px >> 24) & 0x000000FF;
		int r = (px >> 16) & 0x000000FF;
		int g = (px >> 8) & 0x000000FF;
		int b = (px >> 0) & 0x000000FF;

		rgba[0] = r;
		rgba[1] = g;
		rgba[2] = b;
		rgba[3] = a;
	}

	public static void setRGBA(BufferedImage img, int x, int y, int[] rgba) {
		if (!inBounds(img, x, y)) return;
		int color = (rgba[3] << 24) + (rgba[0] << 16) + (rgba[1] << 8) + (rgba[2] << 0);
		img.setRGB(x, y, color);
	}

	public static boolean inBounds(BufferedImage img, int x, int y) {
		return (x < img.getWidth() && x >= 0 && y < img.getHeight() && y >= 0);
	}

	// http://www.easyrgb.com/index.php?X=MATH

	public static void rgb2xyz(int[] rgb, float[] xyz) {

		double var_R = (rgb[0] / 255f); // R from 0 to 255
		double var_G = (rgb[1] / 255f); // G from 0 to 255
		double var_B = (rgb[2] / 255f); // B from 0 to 255

		if (var_R > 0.04045)
			var_R = Math.pow((var_R + 0.055) / 1.055, 2.4);
		else
			var_R = var_R / 12.92;
		if (var_G > 0.04045)
			var_G = Math.pow((var_G + 0.055) / 1.055, 2.4);
		else
			var_G = var_G / 12.92;
		if (var_B > 0.04045)
			var_B = Math.pow((var_B + 0.055) / 1.055, 2.4);
		else
			var_B = var_B / 12.92;

		var_R = var_R * 100;
		var_G = var_G * 100;
		var_B = var_B * 100;

		// Observer. = 2°, Illuminant = D65
		double X = var_R * 0.4124 + var_G * 0.3576 + var_B * 0.1805;
		double Y = var_R * 0.2126 + var_G * 0.7152 + var_B * 0.0722;
		double Z = var_R * 0.0193 + var_G * 0.1192 + var_B * 0.9505;

		xyz[0] = (float) X;
		xyz[1] = (float) Y;
		xyz[2] = (float) Z;
	}

	public static void xyz2cieLab(float[] xyz, float[] lab) {
		float ref_X = 95.047f;// Observer= 2°, Illuminant= D65
		float ref_Y = 100.000f;
		float ref_Z = 108.883f;

		double var_X = xyz[0] / ref_X;
		double var_Y = xyz[1] / ref_Y;
		double var_Z = xyz[2] / ref_Z;

		if (var_X > 0.008856)
			var_X = Math.pow(var_X, (1 / 3f));
		else
			var_X = (7.787 * var_X) + (16 / 116);
		if (var_Y > 0.008856)
			var_Y = Math.pow(var_Y, (1 / 3f));
		else
			var_Y = (7.787 * var_Y) + (16 / 116);
		if (var_Z > 0.008856)
			var_Z = Math.pow(var_Z, (1 / 3f));
		else
			var_Z = (7.787 * var_Z) + (16 / 116);

		lab[0] = (float) ((116 * var_Y) - 16);
		lab[1] = (float) (500 * (var_X - var_Y));
		lab[2] = (float) (200 * (var_Y - var_Z));
	}

	public static void cieLab2xyz(float[] lab, float[] xyz) {
		float ref_X = 95.047f;// Observer= 2°, Illuminant= D65
		float ref_Y = 100.000f;
		float ref_Z = 108.883f;

		double var_Y = (lab[0] + 16) / 116;
		double var_X = lab[1] / 500 + var_Y;
		double var_Z = var_Y - lab[2] / 200;

		if (Math.pow(var_Y, 3) > 0.008856)
			var_Y = Math.pow(var_Y, 3);
		else
			var_Y = (var_Y - 16 / 116) / 7.787;
		if (Math.pow(var_X, 3) > 0.008856)
			var_X = Math.pow(var_X, 3);
		else
			var_X = (var_X - 16 / 116) / 7.787;
		if (Math.pow(var_Z, 3) > 0.008856)
			var_Z = Math.pow(var_Z, 3);
		else
			var_Z = (var_Z - 16 / 116) / 7.787;

		xyz[0] = (float) (ref_X * var_X);
		xyz[1] = (float) (ref_Y * var_Y);
		xyz[2] = (float) (ref_Z * var_Z);
	}

	public static void xyz2rgb(float[] xyz, int[] rgb) {
		double var_X = xyz[0] / 100; // X from 0 to 95.047 (Observer = 2°,
										// Illuminant = D65)
		double var_Y = xyz[1] / 100; // Y from 0 to 100.000
		double var_Z = xyz[2] / 100; // Z from 0 to 108.883

		double var_R = var_X * 3.2406 + var_Y * -1.5372 + var_Z * -0.4986f;
		double var_G = var_X * -0.9689 + var_Y * 1.8758 + var_Z * 0.0415f;
		double var_B = var_X * 0.0557 + var_Y * -0.2040 + var_Z * 1.0570f;

		if (var_R > 0.0031308)
			var_R = 1.055 * (Math.pow(var_R, (1 / 2.4))) - 0.055f;
		else
			var_R = 12.92 * var_R;
		if (var_G > 0.0031308)
			var_G = 1.055 * (Math.pow(var_G, (1 / 2.4))) - 0.055f;
		else
			var_G = 12.92 * var_G;
		if (var_B > 0.0031308)
			var_B = 1.055 * (Math.pow(var_B, (1 / 2.4))) - 0.055f;
		else
			var_B = 12.92 * var_B;

		rgb[0] = (int) (var_R * 255);
		rgb[1] = (int) (var_G * 255);
		rgb[2] = (int) (var_B * 255);
	}

	public static void saturate(int[] rgba) {
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
