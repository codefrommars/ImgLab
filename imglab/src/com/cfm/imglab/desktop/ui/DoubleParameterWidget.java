package com.cfm.imglab.desktop.ui;

import java.awt.BorderLayout;

import javax.swing.JTextField;

import cfm.neograph.core.type.RuntimePrimitive;

import com.cfm.imglab.desktop.ImgLabFrame;


@SuppressWarnings("serial")
public class DoubleParameterWidget extends ParameterWidget{
	
	private JTextField txtValue;

	public DoubleParameterWidget(String name, final ImgLabFrame frame){
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
		txtValue.setText(String.valueOf(value.getAsNumber()));
		revalidate();
		repaint();
	}

	@Override
	public RuntimePrimitive pullValue() {
		double val = 0;
		
		if( !txtValue.getText().isEmpty() )
			val = Double.parseDouble(txtValue.getText());
		
		value.setNumber(val);
		
		return value;
	}
}