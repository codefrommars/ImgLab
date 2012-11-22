package com.cfm.imglab.desktop.ui;

import java.awt.BorderLayout;

import javax.swing.JTextField;

import cfm.neograph.core.type.RuntimePrimitive;

import com.cfm.imglab.desktop.ImgLabFrame;


@SuppressWarnings("serial")
public class StringParameterWidget extends ParameterWidget{
	
	private JTextField txtValue;

	public StringParameterWidget(String name, final ImgLabFrame frame){
		super(name, frame);
		setLayout(new BorderLayout());
		
		value = new RuntimePrimitive(getName());
		
		txtValue = new JTextField();		
		add(txtValue, BorderLayout.CENTER);
		revalidate();
	}

	@Override
	public void pushValue(RuntimePrimitive value) {
		super.pushValue(value);
		
		//StringValue strValue = (NamedValue)value;
		
		txtValue.setText( value.getAsString());
		
		revalidate();
		repaint();
	}

	@Override
	public RuntimePrimitive pullValue() {
		value.setString(txtValue.getText());
		return value;
	}
}