package com.cfm.imglab.desktop.ui.composer.graphic;

import com.cfm.imglab.desktop.ImgLabFrame;


public class PreviewImageNode {
	
	private ImgLabFrame imgLabFrame;
	
	public static final String 	PARAM_IMAGE = "Image",
								PARAM_NAME = "Name";
	
	public PreviewImageNode(ImgLabFrame imgLabFrame){
		super();
//		inputs.add(new InputPort(this, new RuntimePrimitive(PARAM_IMAGE, RuntimeType.TYPE_IMAGE)) );
//		inputs.add(new InputPort(this, new RuntimePrimitive(PARAM_NAME, RuntimeType.TYPE_STRING)) );
//		
		this.imgLabFrame = imgLabFrame;
	}
	
	public String getName(){
		return "Executable";
	}

	
	public String toString() {
		return getName();
	}

	
}
