package com.cfm.imglab.desktop.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

import com.cfm.imglab.Filter;
import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.NamedValue;
import com.cfm.imglab.ValueSet;
import com.cfm.imglab.desktop.ImgLabFrame;
import com.cfm.imglab.desktop.ui.ParameterFormDialog;

@SuppressWarnings("serial")
public class RunFilterAction extends AbstractAction {
	private ImgLabFrame imgLabFrame;
	private Filter filter;
	
	public RunFilterAction(ImgLabFrame imgLabFrame, Filter filter) {
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
				runFilter(imgLabFrame, filter, dialog.getParameters());
				dialog.dispose();
			}
		});
		
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	
	private void runFilter(ImgLabFrame frame, Filter filter, ValueSet params) {
		//Create the output Hashmap
		ValueSet outs = new ValueSet();
		
		//Run the filter
		filter.runFilter(params, outs);
		
		//Add the resulting Images
		for(String s : outs.keySet()){
			NamedValue v = outs.get(s);
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
