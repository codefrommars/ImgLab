package com.cfm.imglab.operations;

import java.awt.image.BufferedImage;

import cfm.neograph.core.ValueSet;
import cfm.neograph.core.type.RuntimePrimitive;
import cfm.neograph.core.type.RuntimeType;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.ImageUtils;

public class MakeMaskOperation extends UnaryOperation {
	
	public static final String INPUT_R = "Red";
	public static final String INPUT_G = "Green";
	public static final String INPUT_B = "Blue";
	
	private int r, g, b;
	
	@Override
	public String getName() {
		return "Make Mask";
	}

	@Override
	public ValueSet getParameters() {
		ValueSet set = super.getParameters();
		
		set.addValue(new RuntimePrimitive(INPUT_R, RuntimeType.TYPE_NUMBER));
		set.addValue(new RuntimePrimitive(INPUT_G, RuntimeType.TYPE_NUMBER));
		set.addValue(new RuntimePrimitive(INPUT_B, RuntimeType.TYPE_NUMBER));
		
		return set;
	}



	@Override
	protected ImageDescriptor prepareOutput(ValueSet input) {
		ImageDescriptor inputImage = input.get(INPUT_IMAGE).getAsImage();
		BufferedImage img = inputImage.getImage();
		
		r = input.get(INPUT_R).getAsNumber().intValue();
		g = input.get(INPUT_G).getAsNumber().intValue();
		b = input.get(INPUT_B).getAsNumber().intValue();
		
		BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		return new ImageDescriptor(out, OUTPUT_IMAGE);
	}

	@Override
	protected void apply(BufferedImage in, BufferedImage out) {
		int[] pixel = new int[4];
		
		for(int i = 0; i < in.getWidth(); i++){
			for(int j = 0; j < in.getHeight(); j++){
				ImageUtils.getRGBA(in, i, j, pixel);
				
				if( pixel[0] != 0 || pixel[1] != 0 || pixel[2] != 0){
					pixel[0] = r;
					pixel[1] = g;
					pixel[2] = b;					
				}
				
				ImageUtils.setRGBA(out, i, j, pixel);		
			}
		}

	}

}
