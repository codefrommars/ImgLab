package com.cfm.imglab.operations;

import java.awt.image.BufferedImage;

import cfm.neograph.core.ValueSet;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.ImageUtils;

public abstract class ConvolutionOperation extends UnaryOperation {
	
	protected float[][] kernel;
	
	@Override
	protected void apply(BufferedImage in, BufferedImage out) {
		//w, h
		int w = in.getWidth(), h = in.getHeight();

		int[] rgba = new int[4];
		for(int i = 0; i < w; i++)
			for(int j = 0; j < w; j++){
//				int count = 0;
				int k2 = kernel.length / 2;
				
				float sum_r = 0;
				float sum_g = 0;
				float sum_b = 0;
				
				
				for(int u = -k2; u <= k2; u++)
					for(int v = -k2; v <= k2; v++){
						int ii = i + u;
						int jj = j + v;
						
						if( ii < 0 || ii >= w || jj < 0 || jj >= h)
							continue;
						
						//count++;
						
						ImageUtils.getRGBA(in, ii, jj, rgba);
						
						sum_r += kernel[u+k2][v+k2] * rgba[0];
						sum_g += kernel[u+k2][v+k2] * rgba[1];
						sum_b += kernel[u+k2][v+k2] * rgba[2];
					}
				
				rgba[0] = (int)sum_r;
				rgba[1] = (int)sum_g;
				rgba[2] = (int)sum_b;
				
				ImageUtils.saturate(rgba);
				ImageUtils.setRGBA(out, i, j, rgba);
			}
			
		
	}
	
}
