package com.cfm.imglab.operations;

import java.awt.image.BufferedImage;

import cfm.neograph.core.ValueSet;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.ImageUtils;

public class RGBtoGrayOperation extends UnaryOperation{
	public static final String INPUT_IMAGE = "Input Image";

	@Override
	public String getName() {
		return "RGB to Gray";
	}

	@Override
	protected ImageDescriptor prepareOutput(ValueSet input) {
		ImageDescriptor inputImage = input.get(INPUT_IMAGE).getAsImage();
		BufferedImage img = inputImage.getImage();
		
		BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		return new ImageDescriptor(out, OUTPUT_IMAGE);
	}

	@Override
	protected void apply(BufferedImage in, BufferedImage out) {
		int[] pixel = new int[4];
		
		for(int i = 0; i < in.getWidth(); i++){
			for(int j = 0; j < in.getHeight(); j++){
				ImageUtils.getRGBA(in, i, j, pixel);
				
				int sum = pixel[0] + pixel[1] + pixel[2];
				pixel[0] = (byte) (sum / 3);
				pixel[1] = (byte) (sum / 3);
				pixel[2] = (byte) (sum / 3);
				
				ImageUtils.setRGBA(out, i, j, pixel);
			}
		}
	}

//	@Override
//	public ValueSet getParameters() {
//		ValueSet params = new ValueSet();
//		params.addValue(new NamedValue(INPUT_IMAGE, NamedValue.TYPE_IMAGE));
//		return params;
//	}
//
//	@Override
//	public void runFilter(ValueSet input, ValueSet output) {
//		ImageDescriptor inputImage = input.get(INPUT_IMAGE).getAsImage();
//		BufferedImage image = inputImage.getImage();
//		
//		int[] pixel = new int[4];
//		
//		BufferedImage out = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
//				
//		for(int i = 0; i < image.getWidth(); i++){
//			for(int j = 0; j < image.getHeight(); j++){
//				ImageUtils.getRGBA(image, i, j, pixel);
//				
//				int sum = pixel[0] + pixel[1] + pixel[2];
//				pixel[0] = (byte) (sum / 3);
//				pixel[1] = (byte) (sum / 3);
//				pixel[2] = (byte) (sum / 3);
//				
//				ImageUtils.setRGBA(out, i, j, pixel);
//			}
//		}
//		
//		ImageDescriptor outImage = new ImageDescriptor(out, "Gray_" + inputImage.getName());
//		output.addValue(NamedValue.fromImage(outImage));
//	}
	
}
