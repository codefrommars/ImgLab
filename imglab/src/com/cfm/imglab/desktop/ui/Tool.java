package com.cfm.imglab.desktop.ui;

import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

public abstract class Tool implements MouseListener, MouseMotionListener, MouseWheelListener{
	
	public abstract void paint(Graphics2D g);
}
