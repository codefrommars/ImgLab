package com.cfm.imglab.operations;

import java.awt.image.BufferedImage;

import cfm.neograph.core.Operation;
import cfm.neograph.core.ValueSet;
import cfm.neograph.core.type.RuntimePrimitive;
import cfm.neograph.core.type.RuntimeType;

import com.cfm.imglab.ImageDescriptor;

public abstract class UnaryOperation implements Operation{
	public static final String INPUT_IMAGE = "Input Image",
								OUTPUT_IMAGE = "Output Image";
	
	@Override
	public ValueSet getParameters() {
		ValueSet params = new ValueSet();
		params.addValue(new RuntimePrimitive(INPUT_IMAGE, RuntimeType.TYPE_IMAGE));
		return params;
	}
	
	
	@Override
	public void execute(ValueSet input, ValueSet output) {
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
		output.addValue(RuntimePrimitive.fromImage(outImage));
	}
	
	protected void setup(ValueSet input){
		
	}
	
	@Override
	public ValueSet getOutput() {
		ValueSet params = new ValueSet();
		
		params.addValue(new RuntimePrimitive(OUTPUT_IMAGE, RuntimeType.TYPE_IMAGE));		
		
		return params;
	}
	
	protected abstract ImageDescriptor prepareOutput(ValueSet input); 
	protected abstract void apply(BufferedImage in, BufferedImage out);


	@Override
	public String toString() {
		return getName();
	}
	
	
}
