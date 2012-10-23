package com.cfm.imglab.desktop.ui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.cfm.imglab.NamedValue;
import com.cfm.imglab.desktop.ImgLabFrame;


@SuppressWarnings("serial")
public class ImageParameterWidget extends ParameterWidget{
	
	protected JButton btnImg;
	
	public ImageParameterWidget(String name, final ImgLabFrame frame){
		super(name, frame);
		btnImg = new JButton("Select Image...");
		btnImg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SelectImageFileDialog dialog = new SelectImageFileDialog(ImageParameterWidget.this, frame);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}
		});
		add(btnImg);
	}

	@Override
	public void pushValue(NamedValue value) {
		super.pushValue(value);
		btnImg.setText("");
		btnImg.setIcon(new ImageIcon( value.getAsImage().getImage().getScaledInstance(40, 40, Image.SCALE_FAST)) );

		firePropertyChange("value", null, null);
		revalidate();
		repaint();
	}
	
}
