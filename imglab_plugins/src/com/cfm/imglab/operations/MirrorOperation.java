package com.cfm.imglab.operations;

import java.awt.image.BufferedImage;

import cfm.neograph.core.ValueSet;
import cfm.neograph.core.type.RuntimePrimitive;
import cfm.neograph.core.type.RuntimeType;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.ImageUtils;

public class MirrorOperation extends UnaryOperation {

	
	public static final String INPUT_MIRROR_X = "Mirror X",
							   INPUT_MIRROR_Y = "Mirror Y";
	
	private boolean mirrorX, mirrorY;
	
	@Override
	public String getName() {
		return "Mirror";
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
		
		int width = out.getWidth();
		int height = out.getHeight();
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				ImageUtils.getRGBA(in, i, j, pixel);
				
				int dstX = i;
				int dstY = j;
				
				if( mirrorX )
					dstX = width - 1 - dstX;
				
				if( mirrorY )
					dstY = height - 1 - dstY;
				
				ImageUtils.setRGBA(out, dstX, dstY, pixel);
			}
		}
	}

	@Override
	public ValueSet getParameters() {
		ValueSet values = super.getParameters();
		
		RuntimePrimitive xMirror = new RuntimePrimitive(INPUT_MIRROR_X, RuntimeType.TYPE_BOOLEAN);
		values.addValue(xMirror);
		
		RuntimePrimitive yMirror = new RuntimePrimitive(INPUT_MIRROR_Y, RuntimeType.TYPE_BOOLEAN);
		values.addValue(yMirror);
		
		return values;
	}

	@Override
	protected void setup(ValueSet input) {
		super.setup(input);
		this.mirrorX = input.get(INPUT_MIRROR_X).getAsBoolean();
		this.mirrorY = input.get(INPUT_MIRROR_Y).getAsBoolean(); 
	}
	
}
