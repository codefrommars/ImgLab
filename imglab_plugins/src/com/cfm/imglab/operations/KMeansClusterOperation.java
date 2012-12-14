package com.cfm.imglab.operations;

import java.awt.Color;
import java.awt.image.BufferedImage;

import cfm.neograph.core.ValueSet;
import cfm.neograph.core.type.RuntimePrimitive;
import cfm.neograph.core.type.RuntimeType;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.ImageUtils;
import com.cfm.imglab.algorithm.KMeans3D;

public class KMeansClusterOperation extends UnaryOperation{

	public static final String INPUT_K = "K";
	
	private int k;
	
	private static int MAX_ITERATIONS = 1000;
	
	
	@Override
	public ValueSet getParameters() {
		ValueSet s = super.getParameters();
		s.addValue(new RuntimePrimitive(INPUT_K, RuntimeType.TYPE_NUMBER));
		return s;
	}

	@Override
	public String getName() {
		return "K-Means Cluster";
	}

	@Override
	protected ImageDescriptor prepareOutput(ValueSet input) {
		
		k = input.get(INPUT_K).getAsNumber().intValue();
		
		ImageDescriptor inputImage = input.get(INPUT_IMAGE).getAsImage();
		BufferedImage img = inputImage.getImage();
		
		BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		
		return new ImageDescriptor(out, OUTPUT_IMAGE);
	}

	@Override
	protected void apply(BufferedImage in, BufferedImage out) {
		
		int w = in.getWidth();
		int h = in.getHeight();
		
		int n = w * h;
		
		float[] data_x = new float[n];
		float[] data_y = new float[n];
		float[] data_z = new float[n];
		
		int[] rgba = new int[4];
		float[] xyz = new float[3];
		float[] lab = new float[3];
		
		for(int i = 0; i < w; i ++){
			for(int j = 0; j < h; j++){
				ImageUtils.getRGBA(in, i, j, rgba);
				
				//Color.RGBtoHSB(rgba[0], rgba[1], rgba[2], hsv);
				
				ImageUtils.rgb2xyz(rgba, xyz);
//				System.out.println(xyz[0] + ", " + xyz[1] + ", " + xyz[2]);
				ImageUtils.xyz2cieLab(xyz, lab);
//				System.out.println(lab[0] + ", " + lab[1] + ", " + lab[2]);
				
				int idx = i + j * w;
				data_x[idx] = lab[0];
				data_y[idx] = lab[1];
				data_z[idx] = lab[2];
				
			//	System.out.println(data_x[idx] + ", " + data_y[idx] + ", " + data_z[idx]);
				
//				data_x[idx] = rgba[0] / 255f;
//				data_y[idx] = rgba[1] / 255f;
//				data_z[idx] = rgba[2] / 255f;
			}
		}
		
		int[] index = new int[n];
		
		float[] c_x = new float[k];
		float[] c_y = new float[k];
		float[] c_z = new float[k];
		
		
		for(int i = 0; i < k; i++){
			//random point (x,y)
			int x = (int) (Math.random() * w);
			int y = (int) (Math.random() * h);
			
			int idx = x + y * w;
			c_x[i] = data_x[idx];
			c_y[i] = data_y[idx];
			c_z[i] = data_z[idx];
		}
		
		int iteration = 0;
		boolean converged = false;
		
		KMeans3D algorithm = new KMeans3D();
		
		//Run KMeans.
		while( iteration < MAX_ITERATIONS && !converged){
//			System.out.println("Iterating...");
			converged = algorithm.iterate(data_x, data_y, data_z, c_x, c_y, c_z, index);
			iteration++;
		}
		
		System.out.println("Converged! " + iteration);
		
//		 
		
		int[] colors = generatePalette(k, 0, 60f/360, 0.5f, 1);
		
		//map data back
		for(int i = 0; i < w; i ++){
			for(int j = 0; j < h; j++){
				//float r, g, b;
				int idx = i + j * w;
				int curr_k = index[idx];
				
				//int color = Color.HSBtoRGB(c_x[curr_k], c_y[curr_k], c_z[curr_k]);
				lab[0] = c_x[curr_k];
				lab[1] = c_y[curr_k];
				lab[2] = c_z[curr_k];
				
				ImageUtils.cieLab2xyz(lab, xyz);
				ImageUtils.xyz2rgb(xyz, rgba);
				//ImageUtils.setRGBA(out, i, j, rgba);
				
				//ImageUtils.getComponents(color, rgba);
//				rgba[0] = (int)(c_x[curr_k] * 255);
//				rgba[1] = (int)(c_y[curr_k] * 255);
//				rgba[2] = (int)(c_z[curr_k] * 255);
				out.setRGB(i, j, colors[curr_k]);
			}
		}
		
	}
	
	protected int[] generatePalette(int k, float Ho, float Hn, float s, float b){
		int colors[] = new int[k];
		
		float dH = (Hn - Ho) / (k - 1);
		
		for(int i = 0; i < k; i++){
			float h = Ho + i * dH;
			colors[i] = Color.HSBtoRGB(h, s, b);
		}
		
		return colors;
	}
}
