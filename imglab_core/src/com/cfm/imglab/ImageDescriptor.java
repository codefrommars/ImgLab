package com.cfm.imglab;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageDescriptor {
	private BufferedImage image;
	private String name;
	private ImageIcon icon;
	
	public ImageDescriptor(){
		
	}
	
	public ImageDescriptor(BufferedImage bi, String str) {
		this.image = bi;
		this.name = str;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
		this.icon = new ImageIcon(image.getScaledInstance(60,  60, Image.SCALE_FAST));
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	
	public int getWidth(){
		return image.getWidth();
	}
	
	public int getHeight(){
		return image.getHeight();
	}
	
	public int getDepth(){
		return image.getColorModel().getPixelSize();
	}
	
	public int getChannels(){
		return image.getColorModel().getColorSpace().getNumComponents();
	}
	
	
	
	public static ImageDescriptor loadFromFile(File file){
		ImageDescriptor desc = null;
		
		try {
			BufferedImage img = ImageIO.read(file);
			desc = new ImageDescriptor(img, file.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return desc;
	}
	
	public static void saveToFile(ImageDescriptor img, File file){
		try {
			ImageIO.write(img.getImage(), "PNG", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
