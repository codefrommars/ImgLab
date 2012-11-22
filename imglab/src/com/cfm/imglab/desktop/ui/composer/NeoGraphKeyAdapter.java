package com.cfm.imglab.desktop.ui.composer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NeoGraphKeyAdapter implements KeyListener {
	
	private NeoGraphEditor editor;
	
	public NeoGraphKeyAdapter(NeoGraphEditor editor){
		this.editor = editor;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_DELETE:
				editor.deleteSelection();
				return;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
