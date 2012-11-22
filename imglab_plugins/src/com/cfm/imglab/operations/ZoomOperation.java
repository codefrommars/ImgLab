package com.cfm.imglab.operations;

import java.awt.image.BufferedImage;

import cfm.neograph.core.ValueSet;
import cfm.neograph.core.type.RuntimePrimitive;
import cfm.neograph.core.type.RuntimeType;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.ImageUtils;

public class ZoomOperation extends UnaryOperation {
	public static final String INPUT_ZOOM_X = "Zoom X",
							   INPUT_ZOOM_Y = "Zoom Y";
	
	private double zoomX, zoomY;
	
	@Override
	public String getName() {
		return "Zoom";
	}

	@Override
	protected ImageDescriptor prepareOutput(ValueSet input) {
		ImageDescriptor inputImage = input.get(INPUT_IMAGE).getAsImage();
		BufferedImage img = inputImage.getImage();
		
		
		BufferedImage out = new BufferedImage((int)(img.getWidth() * zoomX), (int)(img.getHeight() * zoomY), img.getType());
		return new ImageDescriptor(out, OUTPUT_IMAGE);
	}

	@Override
	protected void apply(BufferedImage in, BufferedImage out) {
		int[] pixel = new int[4];
		
		int width = out.getWidth();
		int height = out.getHeight();
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				ImageUtils.getRGBA(in, (int) (i / zoomX), (int)(j / zoomY), pixel);
				ImageUtils.setRGBA(out, i, j, pixel);
			}
		}
	}

	@Override
	public ValueSet getParameters() {
		ValueSet values = super.getParameters();
		
		RuntimePrimitive xMirror = new RuntimePrimitive(INPUT_ZOOM_X, RuntimeType.TYPE_NUMBER);
		xMirror.setNumber(1);
		values.addValue(xMirror);
		
		RuntimePrimitive yMirror = new RuntimePrimitive(INPUT_ZOOM_Y, RuntimeType.TYPE_NUMBER);
		yMirror.setNumber(1);
		values.addValue(yMirror);
		
		return values;
	}

	@Override
	protected void setup(ValueSet input) {
		super.setup(input);
		this.zoomX = input.get(INPUT_ZOOM_X).getAsNumber();
		this.zoomY = input.get(INPUT_ZOOM_Y).getAsNumber(); 
	}
	
}
