package com.cfm.imglab.operations;

import java.awt.image.BufferedImage;

import cfm.neograph.core.ValueSet;

import com.cfm.imglab.ImageDescriptor;

public class ContourDetectOperation extends ConvolutionOperation {
	
	@Override
	public String getName() {
		return "ContourDetect";
	}

	@Override
	protected ImageDescriptor prepareOutput(ValueSet input) {
		ImageDescriptor inputImage = input.get(INPUT_IMAGE).getAsImage();
		BufferedImage img = inputImage.getImage();
		
		
		kernel = new float[3][3];
		
		kernel[0][0] =  1; kernel[0][1] = 0; kernel[0][2] = -1; 
		kernel[1][0] =  0; kernel[1][1] = 0; kernel[1][2] =  0; 
		kernel[2][0] = -1; kernel[2][1] = 0; kernel[2][2] =  1; 
		
		BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		return new ImageDescriptor(out, OUTPUT_IMAGE);
	}

}
