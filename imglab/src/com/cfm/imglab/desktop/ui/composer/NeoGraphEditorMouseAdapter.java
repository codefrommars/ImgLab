package com.cfm.imglab.desktop.ui.composer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NeoGraphEditorMouseAdapter extends MouseAdapter{
	
	private NeoGraphEditor editor;

	public NeoGraphEditorMouseAdapter(NeoGraphEditor editor) {
		this.editor = editor;
	}
	
	public void mouseReleased(MouseEvent e) {
		editor.mouseReleased(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if( e.getClickCount() == 2 ){
			editor.doubleClicked(e.getX(), e.getY());
			return;
		}
		editor.mouseClicked(e.getX(), e.getY(), e.getModifiersEx());
		editor.requestFocusInWindow();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		editor.mousePressed(e.getX(), e.getY(), e.getModifiersEx());
		editor.requestFocusInWindow();
	}
}
