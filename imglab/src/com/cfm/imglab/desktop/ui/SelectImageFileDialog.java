package com.cfm.imglab.desktop.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.NamedValue;
import com.cfm.imglab.desktop.ImgLabFrame;

@SuppressWarnings("serial")
public class SelectImageFileDialog extends JDialog {
	
	public SelectImageFileDialog(final ImageParameterWidget caller, ImgLabFrame frame){
		super(frame, "Select Image...");
		setLayout(new BorderLayout());
		
		final SimpleImageList imgList = new SimpleImageList();
		
		imgList.addImages(frame.getImageTable().getImages());
		
		imgList.doLayout();
		imgList.revalidate();
		imgList.repaint();
		
		System.out.println(imgList.getSize());
		add(imgList, BorderLayout.CENTER);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new SpringLayout());
		
			JButton btnCancel = new JButton();
			btnCancel.setText("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SelectImageFileDialog.this.dispose();
				}
			});
			
			buttonsPanel.add(btnCancel);
			
			JButton btnAccept = new JButton();
			btnAccept.setText("Accept");
			btnAccept.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ImageDescriptor img = imgList.getSelected();
					
					NamedValue val = new NamedValue(img.getName());
					val.setImage(img);
					
					caller.pushValue(val);
					SelectImageFileDialog.this.dispose();
				}
			});
			
			buttonsPanel.add(btnAccept);
		
		SpringUtilities.makeCompactGrid(buttonsPanel,
					1, 2,
					4, 4,
					4, 4);
		
		add(buttonsPanel, BorderLayout.SOUTH);
		pack();
	}	
}
