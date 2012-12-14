package com.cfm.imglab.operations;

import java.awt.image.BufferedImage;

import cfm.neograph.core.ValueSet;
import cfm.neograph.core.type.RuntimePrimitive;
import cfm.neograph.core.type.RuntimeType;

import com.cfm.imglab.ImageDescriptor;

public class GaussianBlurOperation extends ConvolutionOperation {
	
	public static final String INPUT_KERNEL_SIZE = "Kernel Size";
	public static final String INPUT_SIGMA = "Sigma";
	
	@Override
	public String getName() {
		return "GaussianBlur";
	}

	@Override
	public ValueSet getParameters() {
		ValueSet set = super.getParameters();
		set.addValue(new RuntimePrimitive(INPUT_KERNEL_SIZE, RuntimeType.TYPE_NUMBER));
		set.addValue(new RuntimePrimitive(INPUT_SIGMA, RuntimeType.TYPE_NUMBER));
		return set;
	}

	@Override
	protected ImageDescriptor prepareOutput(ValueSet input) {
		ImageDescriptor inputImage = input.get(INPUT_IMAGE).getAsImage();
		BufferedImage img = inputImage.getImage();
		int radius = input.get(INPUT_KERNEL_SIZE).getAsNumber().intValue();
		int hRadius = radius / 2;
		
		kernel = new float[radius][radius];
		
		float sigma = input.get(INPUT_SIGMA).getAsNumber().floatValue();
		float u = 0;
		
		float fac1 = (float)(1 / Math.sqrt(2*Math.PI*sigma*sigma));
		float invSigma = -1f / (2 * sigma * sigma);
		
		float sum = 0;
		
		for(int i = -hRadius; i <= hRadius; i++ )
			for(int j = -hRadius; j <= hRadius; j++ ){
				System.out.println("i: " + i + ", j: " + j);
				float g = (float)( Math.exp( (i*i + j*j)*invSigma ) * fac1);
				kernel[i + hRadius][j + hRadius] = g;
				sum += g;
			}
		
		for(int i = 0; i < kernel.length; i++){
			for(int j = 0; j < kernel[i].length; j++){
				kernel[i][j] /= sum;
//				System.out.print(kernel[i][j] + "\t");
			}
//			System.out.println();
		}
		
		BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		return new ImageDescriptor(out, OUTPUT_IMAGE);
	}

}
