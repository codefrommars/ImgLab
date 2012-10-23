package com.cfm.imglab.desktop.actions;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.cfm.imglab.desktop.ImgLabFrame;

@SuppressWarnings("serial")
public class LoadAction extends AbstractAction {
	
	private final static String[] EXTENSIONS = { ".jpg", ".jpeg", ".png", ".bmp" };
	
	private ImgLabFrame frame;
	
	public LoadAction(ImgLabFrame imgLabFrame) {
		this.frame = imgLabFrame;
		putValue(Action.NAME, "Load Image");
		putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/load_image.png"));
		putValue(Action.SMALL_ICON, new ImageIcon("icons/load_image.png"));
	}
	
	public static boolean isImage(String filename){
		for( String ext : EXTENSIONS )
			if( filename.toLowerCase().endsWith(ext))
				return true;
		
		return false;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setFileFilter(new FileFilter(){
			
			@Override
			public String getDescription() {
				return "Entity file";
			}

			@Override
			public boolean accept(File f) {
				return f.isDirectory() || isImage(f.getName());
			}
			
		});
		
		if( fileChooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION )
			return;
		
		File f = fileChooser.getSelectedFile(); 
		
		doLoad(f);
	}

	private void doLoad(File f) {
		try {
			BufferedImage img = ImageIO.read(f);
			frame.addImage(img, f.getName());
			frame.showImage(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
