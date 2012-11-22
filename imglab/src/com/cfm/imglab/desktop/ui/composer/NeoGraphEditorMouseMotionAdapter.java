package com.cfm.imglab.desktop.ui.composer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class NeoGraphEditorMouseMotionAdapter implements MouseMotionListener {
	
	private NeoGraphEditor editor;
	private int mX, mY;
	
	public NeoGraphEditorMouseMotionAdapter(NeoGraphEditor editor) {
		this.editor = editor;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		editor.mouseDragged(mX, mY, e.getX() - mX, e.getY() - mY);
		mX = e.getX();
		mY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mX = e.getX();
		mY = e.getY();
		editor.mouseMoved(mX, mY);
	}

}
