package com.cfm.imglab.desktop.ui.composer.graphic;

import com.cfm.imglab.NamedValue;
import com.cfm.imglab.composer.Context;
import com.cfm.imglab.composer.ExecutableNode;
import com.cfm.imglab.composer.InputPort;
import com.cfm.imglab.desktop.ImgLabFrame;


public class PreviewImageNode extends ExecutableNode{
	
	private ImgLabFrame imgLabFrame;
	
	public static final String 	PARAM_IMAGE = "Image",
								PARAM_NAME = "Name";
	
	public PreviewImageNode(ImgLabFrame imgLabFrame){
		super();
		inputs.add(new InputPort(this, new NamedValue(PARAM_IMAGE, NamedValue.TYPE_IMAGE)) );
		inputs.add(new InputPort(this, new NamedValue(PARAM_NAME, NamedValue.TYPE_STRING)) );
		
		this.imgLabFrame = imgLabFrame;
	}
	
	public String getName(){
		return "Executable";
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public void execute(Context ctx) {
		// TODO Auto-generated method stub
		System.out.println("Executing preview !");
	}
	
}
