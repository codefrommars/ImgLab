package com.cfm.imglab.desktop.ui;

import javax.swing.JPanel;

import com.cfm.imglab.NamedValue;
import com.cfm.imglab.desktop.ImgLabFrame;

@SuppressWarnings("serial")
public abstract class ParameterWidget extends JPanel{
	
	protected ImgLabFrame frame;
	private String name;
	protected NamedValue value;
	
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
	
	public NamedValue pullValue(){
		return value;
	}

	public void pushValue(NamedValue value) {
		this.value = value;
	}
}
