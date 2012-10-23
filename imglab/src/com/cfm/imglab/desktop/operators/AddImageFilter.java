package com.cfm.imglab.desktop.operators;

import java.awt.Graphics2D;

import com.cfm.imglab.NamedValue;
import com.cfm.imglab.ValueSet;
import com.cfm.imglab.desktop.ui.composer.graphic.PartShape;

public class AddImageFilter extends PartShape{

	public static final String PARAM_IMG_NAME = "Image Name",
							   PARAM_IMG = "Image";
	
//	@Override
//	public String getName() {
//		return "AddImage";
//	}
//
//	@Override
//	public ValueSet getParameters() {
//		ValueSet set = new ValueSet();
//		
//		set.addValue(new NamedValue(PARAM_IMG_NAME, NamedValue.TYPE_STRING));
//		set.addValue(new NamedValue(PARAM_IMG, NamedValue.TYPE_IMAGE));
//		
//		return set;
//	}
//
//	@Override
//	public ValueSet getOutput() {
//		return null;
//	}
//
//	@Override
//	public void runFilter(ValueSet input, ValueSet output) {
//		
//	}

	@Override
	public boolean couldContain(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void paint(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

}
