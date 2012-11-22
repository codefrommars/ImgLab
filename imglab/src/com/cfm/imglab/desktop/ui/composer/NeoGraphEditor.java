package com.cfm.imglab.desktop.ui.composer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.JComponent;

import cfm.neograph.core.Graph;
import cfm.neograph.core.GraphLink;
import cfm.neograph.core.GraphNode;
import cfm.neograph.core.GraphNodeFactory;
import cfm.neograph.core.GraphNodeType;
import cfm.neograph.core.GraphPort;
import cfm.neograph.core.Operation;
import cfm.neograph.core.OperationRegister;
import cfm.neograph.core.ValueSet;
import cfm.neograph.core.type.RuntimePrimitive;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.desktop.ImgLabFrame;
import com.cfm.imglab.desktop.ui.ParameterFormDialog;

@SuppressWarnings("serial")
public class NeoGraphEditor extends JComponent{
	
	
	//Substance
	private Graph graph;

	//Callbacks
	private ImgLabFrame frame;
	
	//Render
	private NeoGraphRenderer renderer;
	
	private boolean canLink = false;
	private GraphPort draggedPort = null;
	private int dragStartX, dragStartY;
	
	private int mX, mY;
	
	//Selection
	private List<GraphNode> selection = new ArrayList<GraphNode>();
	
	public NeoGraphEditor(ImgLabFrame frame){
		this.frame = frame;
		
		setPreferredSize(new Dimension(500, 500));
		
		addMouseListener(new NeoGraphEditorMouseAdapter(this));
		addMouseMotionListener(new NeoGraphEditorMouseMotionAdapter(this));
		addKeyListener(new NeoGraphKeyAdapter(this));
		setFocusable(true);
		
		DropTarget dt = new DropTarget();
		try {
			dt.addDropTargetListener(new NeoGraphEditorDropTargetAdapter(this));
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
		
		setDropTarget(dt);
		
		renderer = new NeoGraphRenderer();
	}
	
	@Override
	public boolean isFocusable() {
		return true;
	}

	public void edit(Graph graphView){
		this.graph = graphView;
		repaint();
	}
	
	public void onDropOperation(Operation model, Point location) {
		GraphNodeFactory f = new GraphNodeFactory();
		GraphNode node = f.newOperationNode(model);
		node.setPosition(location.x, location.y);
		graph.addNode(node);
		repaint();
	}

	public void onDropImage(ImageDescriptor transferData, Point location) {
		GraphNodeFactory f = new GraphNodeFactory();
		
		GraphNode node = f.newImageNode(transferData);
		node.setPosition(location.x, location.y);
		graph.addNode(node);
		
		repaint();
	}
	
	public void onDropVariable(GraphNodeType transferData, Point location) {
		GraphNodeFactory f = new GraphNodeFactory();
		GraphNode node = f.newVariable(transferData);
		node.setPosition(location.x, location.y);
		graph.addNode(node);
		
		repaint();
		
	}

	public void mouseReleased(int x, int y) {
		GraphPort gp = graph.getPortAt(x, y);
		
		if( !canLink || draggedPort == null){
			
			draggedPort = null;
			repaint();
			return;
		}
		
		//Can Link
		GraphLink link = new GraphLink();
		
		if( graph.isInput(draggedPort) ){
			link.setTarget(draggedPort.getIndex());
			link.setSource(gp.getIndex());
		}else{
			link.setTarget(gp.getIndex());
			link.setSource(draggedPort.getIndex());
		}
		
		graph.addLink(link);
		
		draggedPort = null;
		repaint();
		repaint();
	}

	public void doubleClicked(int x, int y) {
		final GraphNode p = graph.getNodeAt(x, y);
		
		if( p == null )
			return;
		
		if( !p.isValueNode() )
			return;
		
		final RuntimePrimitive value = new RuntimePrimitive(p.getValue());
		
		ValueSet vs = new ValueSet();
		vs.addValue(value);
		
		final ParameterFormDialog dialog = new ParameterFormDialog(frame, vs);
		
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
				RuntimePrimitive val = dialog.getParameters().get(value.getName());
				p.getValue().setValue(val.getValue());				
				dialog.dispose();
				repaint();
			}
		});
		
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	public void mouseClicked(int x, int y, int modifiersEx) {
//		NeoGraphNodeView n = graphView.getNodeAt(x, y);
//		
//		if( (MouseEvent.CTRL_DOWN_MASK & modifiersEx) == MouseEvent.CTRL_DOWN_MASK ){
//			addToSelection(n);
//			return;
//		}
//		
//		select(n);
	}

	private void addToSelection(GraphNode n) {
		if( n != null && !selection.contains(n) )
			selection.add(n);
	}

	public void mousePressed(int x, int y, int modifiersEx) {
		//Selection and stuff
//		NeoGraphNodeView n = graphView.getNodeAt(x, y);
		GraphNode n = graph.getNodeAt(x, y);
		
		if( (MouseEvent.CTRL_DOWN_MASK & modifiersEx) == MouseEvent.CTRL_DOWN_MASK ){
			addToSelection(n);
			return;
		}
		
		
		GraphPort gp = graph.getPortAt(x, y);
		
		if( gp != null ){
			select(null);
			draggedPort = gp;
			Rectangle r = graph.getBoundsFor(gp);
			dragStartX = x;
			dragStartY = y;
			return;
		}
		
		select(n);
		
	}

	private void select(GraphNode n) {
		if( selection.contains(n) )
			return;
		
		selection.clear();
		addToSelection(n);
	}

	public void mouseDragged(int x, int y, int dx, int dy) {
		mX = x;
		mY = y;
		
		canLink = testCanLink(x, y);
		
		for(GraphNode n : selection)
			n.move(dx, dy);
		
		if( !selection.isEmpty() || draggedPort != null)
			repaint();
	}

	private boolean testCanLink(int x, int y) {
		
		if( draggedPort == null )
			return false;
		
		GraphPort gp = graph.getPortAt(x, y);
		if( gp == null )
			return false;
		
		if( graph.isInput(draggedPort) == graph.isInput(gp) ){
			return false;
		}
		
		return true;
	}

	public void mouseMoved(int x, int y) {
		
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		Graphics2D g2 = (Graphics2D)g;
		
		renderer.renderGraphView(graph, g2);
		
		if( draggedPort != null )
			renderer.renderTempLink(dragStartX, dragStartY, mX, mY, canLink, g2);
		
		for( GraphNode p : selection )
			renderer.renderSelected(p, g2);
	}

	public void save(File file, OperationRegister or) {
		try {
			Graph.saveTo(file, graph, or);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadGraph(File f, OperationRegister supportedOperations) {
		try {
			edit(Graph.loadFrom(f, supportedOperations));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Graph getGraph(){
		return graph;
	}

	public void deleteSelection() {
		for(GraphNode n : selection ){
			graph.deleteNode(n);
		}
		selection.clear();
		repaint();
	}
	
}
