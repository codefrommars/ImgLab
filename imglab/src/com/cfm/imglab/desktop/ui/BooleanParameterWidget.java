package com.cfm.imglab.desktop.ui;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;

import cfm.neograph.core.type.RuntimePrimitive;

import com.cfm.imglab.desktop.ImgLabFrame;


@SuppressWarnings("serial")
public class BooleanParameterWidget extends ParameterWidget{
	
	//private JTextField txtValue;
	private JCheckBox chkValue;

	public BooleanParameterWidget(String name, final ImgLabFrame frame){
		super(name, frame);
		setLayout(new BorderLayout());
		
		value = new RuntimePrimitive(getName());
		
		//txtValue = new JTextField();
		chkValue = new JCheckBox();
		add(chkValue, BorderLayout.CENTER);
		revalidate();
	}

	@Override
	public void pushValue(RuntimePrimitive value) {
		super.pushValue(value);
		
		chkValue.setText(String.valueOf(value.getName()));
		chkValue.setSelected(value.getAsBoolean());
		
		revalidate();
		repaint();
	}

	@Override
	public RuntimePrimitive pullValue() {
		boolean val = chkValue.isSelected();
		value.setBoolean(val);
		return value;
	}
}