package com.cfm.imglab.desktop.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.desktop.ImgLabFrame;

@SuppressWarnings("serial")
public class SaveAction extends AbstractAction {
	
	private ImgLabFrame frame;
	
	public SaveAction(ImgLabFrame imgLabFrame) {
		this.frame = imgLabFrame;
		
		putValue(Action.NAME, "Save image...");
		putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/save_image.png"));
		putValue(Action.SMALL_ICON, new ImageIcon("icons/save_image.png"));
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setFileFilter(new FileFilter(){

			@Override
			public String getDescription() {
				return "*.*";
			}

			@Override
			public boolean accept(File f) {
				return true;
			}
			
		});
		
		if( fileChooser.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION )
			return;
		
		File f = fileChooser.getSelectedFile(); 
		
		doSave(f.getParentFile(), f.getName());
	}

	private void doSave(File parentFile, String name) {
		
		int point = name.lastIndexOf('.');
		String ext = name.substring(point + 1).toUpperCase();
		
		ImageDescriptor imgFile = frame.getSelectedImage();
		
		File f = new File(parentFile, name);
		
		try {
			ImageIO.write(imgFile.getImage(), ext, f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
