package com.cfm.imglab.desktop.ui;

import javax.swing.JPanel;

import cfm.neograph.core.type.RuntimePrimitive;

import com.cfm.imglab.desktop.ImgLabFrame;

@SuppressWarnings("serial")
public abstract class ParameterWidget extends JPanel{
	
	protected ImgLabFrame frame;
	private String name;
	protected RuntimePrimitive value;
	
	public ParameterWidget(String name, ImgLabFrame frame) {
		this.frame = frame;
		this.name = name;
	}

	public String getName() {
		return name;
	}

//	public void setName(String name) {
//		this.name = name;
//	}
	
	public RuntimePrimitive pullValue(){
		return value;
	}

	public void pushValue(RuntimePrimitive value) {
		this.value = value;
	}
}
