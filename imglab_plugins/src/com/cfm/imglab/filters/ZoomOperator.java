package com.cfm.imglab.filters;

import java.awt.image.BufferedImage;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.ImageUtils;
import com.cfm.imglab.NamedValue;
import com.cfm.imglab.ValueSet;

public class ZoomOperator extends UnaryOperatorFilter {
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
		
		NamedValue xMirror = new NamedValue(INPUT_ZOOM_X, NamedValue.TYPE_NUMBER);
		xMirror.setNumber(1);
		values.addValue(xMirror);
		
		NamedValue yMirror = new NamedValue(INPUT_ZOOM_Y, NamedValue.TYPE_NUMBER);
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
