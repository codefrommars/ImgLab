package com.cfm.imglab.desktop.ui;

import java.awt.BorderLayout;

import javax.swing.JTextField;

import com.cfm.imglab.NamedValue;
import com.cfm.imglab.desktop.ImgLabFrame;


@SuppressWarnings("serial")
public class DoubleParameterWidget extends ParameterWidget{
	
	private JTextField txtValue;

	public DoubleParameterWidget(String name, final ImgLabFrame frame){
		super(name, frame);
		setLayout(new BorderLayout());
		
		value = new NamedValue(getName());
		
		txtValue = new JTextField();		
		add(txtValue, BorderLayout.CENTER);
		revalidate();
	}

	@Override
	public void pushValue(NamedValue value) {
		super.pushValue(value);
		txtValue.setText(String.valueOf(value.getAsNumber()));
		revalidate();
		repaint();
	}

	@Override
	public NamedValue pullValue() {
		double val = 0;
		
		if( !txtValue.getText().isEmpty() )
			val = Double.parseDouble(txtValue.getText());
		
		value.setNumber(val);
		
		return value;
	}
}