package com.cfm.imglab.filters;

import java.awt.image.BufferedImage;

import com.cfm.imglab.Filter;
import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.ImageUtils;
import com.cfm.imglab.NamedValue;
import com.cfm.imglab.ValueSet;

public abstract class BinaryOperatorFilter implements Filter {
	public static final String	FIRST_IMAGE = "First Image",
		   						SECOND_IMAGE = "Second Image",
		   						OUTPUT_IMAGE = "Output Image";
	
	protected BufferedImage img1, img2, out;
	
	@Override
	public ValueSet getParameters() {
		ValueSet params = new ValueSet();
		
		params.addValue(new NamedValue(FIRST_IMAGE, NamedValue.TYPE_IMAGE));
		params.addValue(new NamedValue(SECOND_IMAGE, NamedValue.TYPE_IMAGE));
		
		return params;
	}
	
	protected abstract void operatePixels(int[] p1, int[] p2, int[] out);
	
	protected void setup(ValueSet input){
		ImageDescriptor image1 = input.get(FIRST_IMAGE).getAsImage();
		ImageDescriptor image2 = input.get(SECOND_IMAGE).getAsImage();
		
		img1 = image1.getImage();
		img2 = image2.getImage();
		out = new BufferedImage(img1.getWidth(), img1.getHeight(), img1.getType());
	}
	
	@Override
	public void runFilter(ValueSet input, ValueSet output) {
		
		setup(input);
		
		int[] p1 = new int[4], p2 = new int[4], outPixel = new int[4];
		
		for(int i = 0; i < img1.getWidth(); i++){
			for(int j = 0; j < img1.getHeight(); j++){
				ImageUtils.getRGBA(img1, i, j, p1);
				ImageUtils.getRGBA(img2, i, j, p2);
				
				operatePixels(p1, p2, outPixel);
				
				ImageUtils.saturate(outPixel);
				ImageUtils.setRGBA(out, i, j, outPixel);
			}
		}
		
		ImageDescriptor image1 = input.get(FIRST_IMAGE).getAsImage();
		ImageDescriptor image2 = input.get(SECOND_IMAGE).getAsImage();
		ImageDescriptor outImage = new ImageDescriptor(out, OUTPUT_IMAGE);
		
		output.addValue(NamedValue.fromImage(outImage));
	}

	@Override
	public ValueSet getOutput() {
		ValueSet params = new ValueSet();
		
		params.addValue(new NamedValue(OUTPUT_IMAGE, NamedValue.TYPE_IMAGE));		
		
		return params;
	}

	@Override
	public String toString() {
		return getName();
	}
	
	
}
