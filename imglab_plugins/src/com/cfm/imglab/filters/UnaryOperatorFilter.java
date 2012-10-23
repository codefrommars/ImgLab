package com.cfm.imglab.filters;

import java.awt.image.BufferedImage;

import com.cfm.imglab.Filter;
import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.NamedValue;
import com.cfm.imglab.ValueSet;

public abstract class UnaryOperatorFilter implements Filter{
	public static final String INPUT_IMAGE = "Input Image",
								OUTPUT_IMAGE = "Output Image";
	
	@Override
	public ValueSet getParameters() {
		ValueSet params = new ValueSet();
		params.addValue(new NamedValue(INPUT_IMAGE, NamedValue.TYPE_IMAGE));
		return params;
	}
	
	
	@Override
	public void runFilter(ValueSet input, ValueSet output) {
		setup(input);
		
		ImageDescriptor inputImage = input.get(INPUT_IMAGE).getAsImage();
		BufferedImage img = inputImage.getImage();
		
		
		ImageDescriptor outImage = prepareOutput(input);
		BufferedImage out = outImage.getImage();
		//BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		
		apply(img, out);
//		int[] pixel = new int[4];
//				
//		for(int i = 0; i < img.getWidth(); i++){
//			for(int j = 0; j < img.getHeight(); j++){
//				ImageUtils.getRGBA(img, i, j, pixel);
//				
//				int sum = pixel[0] + pixel[1] + pixel[2];
//				pixel[0] = (byte) (sum / 3);
//				pixel[1] = (byte) (sum / 3);
//				pixel[2] = (byte) (sum / 3);
//				
//				ImageUtils.setRGBA(out, i, j, pixel);
//			}
//		}

		//ImageDescriptor outImage = new ImageDescriptor(out, getName() + "_" + inputImage.getName());
		output.addValue(NamedValue.fromImage(outImage));
	}
	
	protected void setup(ValueSet input){
		
	}
	
	@Override
	public ValueSet getOutput() {
		ValueSet params = new ValueSet();
		
		params.addValue(new NamedValue(OUTPUT_IMAGE, NamedValue.TYPE_IMAGE));		
		
		return params;
	}
	
	protected abstract ImageDescriptor prepareOutput(ValueSet input); 
	protected abstract void apply(BufferedImage in, BufferedImage out);


	@Override
	public String toString() {
		return getName();
	}
	
	
}
