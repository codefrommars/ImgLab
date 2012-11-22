package com.cfm.imglab.desktop.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

import cfm.neograph.core.Operation;
import cfm.neograph.core.ValueSet;
import cfm.neograph.core.type.RuntimePrimitive;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.desktop.ImgLabFrame;
import com.cfm.imglab.desktop.ui.ParameterFormDialog;

@SuppressWarnings("serial")
public class RunOperationAction extends AbstractAction {
	private ImgLabFrame imgLabFrame;
	private Operation filter;
	
	public RunOperationAction(ImgLabFrame imgLabFrame, Operation filter) {
		this.imgLabFrame = imgLabFrame;
		this.putValue(NAME, filter.getName());
		
		this.filter = filter;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		final ParameterFormDialog dialog = new ParameterFormDialog(imgLabFrame, filter.getParameters());
		
		//Binding...
		dialog.getBtnCancel().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		
		dialog.getBtnAccept().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runOperation(imgLabFrame, filter, dialog.getParameters());
				dialog.dispose();
			}
		});
		
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	
	private void runOperation(ImgLabFrame frame, Operation filter, ValueSet params) {
		//Create the output Hashmap
		ValueSet outs = new ValueSet();
		
		//Run the filter
		filter.execute(params, outs);
		
		//Add the resulting Images
		for(String s : outs.keySet()){
			RuntimePrimitive v = outs.get(s);
			//switch(v.getType()){
				//case Value.TYPE_IMAGE:
			if( v.isImage() ){
					//It's an image
					ImageDescriptor imgFile = (ImageDescriptor)v.getAsImage();
					frame.addImage(imgFile.getImage(), imgFile.getName());
			}		
			//}
		}
	}
	
}
