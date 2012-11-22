package com.cfm.imglab.desktop.operators;

import cfm.neograph.core.Operation;
import cfm.neograph.core.ValueSet;
import cfm.neograph.core.type.RuntimePrimitive;
import cfm.neograph.core.type.RuntimeType;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.desktop.ImgLabFrame;

public class AddImageOperation implements Operation{
	
	private ImgLabFrame frame;
	
	public AddImageOperation(ImgLabFrame frame){
		this.frame = frame;
	}
	
	@Override
	public String getName() {
		return "Add Image";
	}

	@Override
	public ValueSet getParameters() {
		ValueSet params = new ValueSet();
		
		params.addValue(new RuntimePrimitive("Image", RuntimeType.TYPE_IMAGE));
		params.addValue(new RuntimePrimitive("Name", RuntimeType.TYPE_STRING));
		return params;
	}

	@Override
	public ValueSet getOutput() {
		ValueSet params = new ValueSet();
		return params;
	}

	@Override
	public void execute(ValueSet input, ValueSet output) {
		ImageDescriptor img = input.get("Image").getAsImage();
		String name = input.get("Name").getAsString();
		
		if( img == null )
			return;
		
		frame.addImage(img.getImage(), name);
		
		System.out.println("Adding image! " + name);
	}

	@Override
	public String toString() {
		return getName();
	}
	
	

}
