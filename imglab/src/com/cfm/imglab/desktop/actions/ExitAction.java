package com.cfm.imglab.desktop.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import com.cfm.imglab.desktop.ImgLabFrame;

@SuppressWarnings("serial")
public class ExitAction extends AbstractAction {
	
	private ImgLabFrame frame;
	
	public ExitAction(ImgLabFrame imgLabFrame) {
		this.frame = imgLabFrame;
		putValue(Action.NAME, "Exit");
		putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/exit.png"));
		putValue(Action.SMALL_ICON, new ImageIcon("icons/exit.png"));
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		System.exit(0);
	}

}
