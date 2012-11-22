package com.cfm.imglab.operations;

import java.awt.image.BufferedImage;

import cfm.neograph.core.ValueSet;
import cfm.neograph.core.type.RuntimePrimitive;
import cfm.neograph.core.type.RuntimeType;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.ImageUtils;

public class RotateOperation extends UnaryOperation {

	
	public static final String INPUT_SAME_SIZE = "Same Size",
							   INPUT_ANGLE = "Angle";
	
	private double angle;
	private double m[][] = new double[2][2];
	private boolean sameSize;
	
	@Override
	public String getName() {
		return "Rotation";
	}

	@Override
	protected ImageDescriptor prepareOutput(ValueSet input) {
		ImageDescriptor inputImage = input.get(INPUT_IMAGE).getAsImage();
		BufferedImage img = inputImage.getImage();
		
		int outWidth = img.getWidth();
		int outHeight = img.getHeight();
		
		if( !sameSize ){
			outWidth = (int) (Math.sqrt(2) * outWidth);
			outHeight = (int) (Math.sqrt(2) * outHeight);
		}
		
		BufferedImage out = new BufferedImage(outWidth, outHeight, img.getType());
		return new ImageDescriptor(out, OUTPUT_IMAGE);
	}

	@Override
	protected void apply(BufferedImage in, BufferedImage out) {
		int[] pixel = new int[4];
		
		int h = out.getWidth() / 2;
		int k = out.getHeight() / 2;
		
		int maxX = out.getWidth();
		int maxY = out.getHeight();
		
		for(int i = 0; i < maxX; i++){
			for(int j = 0; j < maxY; j++){
				//Apply the inverse
				double rx = m[0][0] * (i - h) + m[1][0] * (j - k) + in.getWidth() / 2;
				double ry = m[0][1] * (i - h) + m[1][1] * (j - k) + in.getHeight() / 2;
				
				if( !ImageUtils.inBounds(in, (int)rx, (int)ry) )
					continue;
				
				ImageUtils.getRGBA(in, (int)rx, (int)ry, pixel);
				ImageUtils.setRGBA(out, i, j, pixel);
			}
		}
	}

	@Override
	public ValueSet getParameters() {
		ValueSet values = super.getParameters();
		
		RuntimePrimitive sameSize = new RuntimePrimitive(INPUT_SAME_SIZE, RuntimeType.TYPE_BOOLEAN);
		values.addValue(sameSize);
		
		RuntimePrimitive angle = new RuntimePrimitive(INPUT_ANGLE, RuntimeType.TYPE_NUMBER);
		values.addValue(angle);
		
		return values;
	}

	@Override
	protected void setup(ValueSet input) {
		super.setup(input);
		
		this.angle = input.get(INPUT_ANGLE).getAsNumber() * Math.PI / 180;
		this.sameSize = input.get(INPUT_SAME_SIZE).getAsBoolean(); 
		
		m[0][0] = Math.cos(angle); m[0][1] = -Math.sin(angle);
		
		m[1][0] = Math.sin(angle); m[1][1] = Math.cos(angle);
		
	}
	
}
