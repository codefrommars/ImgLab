package com.cfm.imglab.desktop;

import java.io.FileNotFoundException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ImgLabMain {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		//System.setErr(new PrintStream(new FileOutputStream("output.txt")));
		
		//Loader.load(opencv_imgproc.class);
		
		try {
			// Set the Look and Feel of the application to the operating
			// system's look and feel.
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}

		ImgLabFrame frame = new ImgLabFrame();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
