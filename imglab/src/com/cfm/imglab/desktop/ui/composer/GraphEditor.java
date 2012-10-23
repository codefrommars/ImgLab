package com.cfm.imglab.desktop.ui.composer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.JComponent;

import com.cfm.imglab.Filter;
import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.NamedValue;
import com.cfm.imglab.ValueSet;
import com.cfm.imglab.composer.ExecutableNode;
import com.cfm.imglab.composer.FilterNode;
import com.cfm.imglab.composer.Graph;
import com.cfm.imglab.composer.ValueNode;
import com.cfm.imglab.desktop.ImgLabFrame;
import com.cfm.imglab.desktop.ui.ParameterFormDialog;
import com.cfm.imglab.desktop.ui.composer.graphic.ExecutableNodeView;
import com.cfm.imglab.desktop.ui.composer.graphic.FilterNodeView;
import com.cfm.imglab.desktop.ui.composer.graphic.GraphView;
import com.cfm.imglab.desktop.ui.composer.graphic.HasInputPorts;
import com.cfm.imglab.desktop.ui.composer.graphic.HasOutputPorts;
import com.cfm.imglab.desktop.ui.composer.graphic.LinkView;
import com.cfm.imglab.desktop.ui.composer.graphic.PartShape;
import com.cfm.imglab.desktop.ui.composer.graphic.PortView;
import com.cfm.imglab.desktop.ui.composer.graphic.PreviewImageNode;
import com.cfm.imglab.desktop.ui.composer.graphic.SelectionHelper;
import com.cfm.imglab.desktop.ui.composer.graphic.ValueNodeView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("serial")
public class GraphEditor extends JComponent {
	
	private GraphView graphView; 
	
	private PartShape hoverNode;
	private PortView hoverPort;
	private LinkView tempLink;
	
	private int tool;
	
	private List<PartShape> selected = new ArrayList<PartShape>();
	
	public static final int TOOL_MOUSE = 0,
							TOOL_IMAGE = 1,
							TOOL_NUMBER = 2,
							TOOL_STRING = 3,
							TOOL_BOOLEAN = 4,
							TOOL_PREVIEW = 5;
	
	private int corner;
	private int mX, mY;
	private ImgLabFrame frame;
	
	private final static Color helperColor = new Color(0f, 0f, 0f, 0.3f);
	
	public GraphEditor(ImgLabFrame frame) {
		this.frame = frame;
		setPreferredSize(new Dimension(500, 500));
		setFocusable(true);
		
		graphView = new GraphView();
		
		DropTarget dt = new DropTarget();
		try {
			dt.addDropTargetListener(new DropTargetAdapter() {

				@Override
				public void drop(DropTargetDropEvent dtde) {
					try {
						
						Transferable t = dtde.getTransferable();
						
						if( t.isDataFlavorSupported(ElementTransferHandler.FLAVORS[ElementTransferHandler.TYPE_FILTER])){
							onDropFilter((Filter)t.getTransferData(ElementTransferHandler.FLAVORS[ElementTransferHandler.TYPE_FILTER]), dtde.getLocation());
						}
						
						if( t.isDataFlavorSupported(ElementTransferHandler.FLAVORS[ElementTransferHandler.TYPE_IMAGE])){
							onDropImage((ImageDescriptor)t.getTransferData(ElementTransferHandler.FLAVORS[ElementTransferHandler.TYPE_IMAGE]), dtde.getLocation());
						}
						//onDropFilter((Filter)dtde.getTransferable().getTransferData(ElementTransferHandler.DATA_FLAVOUR), dtde.getLocation());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
		setDropTarget(dt);
		
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e) {
				GraphEditor.this.mouseReleased(e.getX(), e.getY());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if( e.getClickCount() == 2 ){
					GraphEditor.this.doubleClicked(e.getX(), e.getY());
					return;
				}
				GraphEditor.this.mouseClicked(e.getX(), e.getY(), e.getModifiersEx());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				GraphEditor.this.mousePressed(e.getX(), e.getY(), e.getModifiersEx());
			}
			
		});

		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent evt) {
				if( hoverNode == null )
					return;
				
//				System.out.println(hoverPort);
				
				if( hoverPort == null ){
					draggingNode(evt.getX(), evt.getY());				
				}else{
					if( tempLink == null ){
						tempLink = new LinkView();
						tempLink.setSource(hoverPort);
						tempLink.setTarget(new PortView(null));
					}
					dragPort(evt.getX(), evt.getY());
				}
				
				mX = evt.getX();
				mY = evt.getY();
			}

			@Override
			public void mouseMoved(MouseEvent evt) {
				onMouseMoved(evt.getX(), evt.getY());
			}
			
		});
		
		requestFocus();
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("Pressed " + e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println("Released " + e);
				super.keyReleased(e);
			}
			
			
			
		});
	}
	
//	protected void createPopup(){
//		JPopupMenu popup = new JPopupMenu();
//		
//		
//	}
//	
//	protected JMenuItem itemFor(final int type){
//		JMenuItem addValue = new JMenuItem();
//		
//		addValue.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				NamedValue val = new NamedValue("Var", type);
//				
//			}
//		});
//				
//		return addValue;
//	}
	
	protected void mousePressed(int x, int y, int modifiers) {
		if( tool == TOOL_MOUSE ){
			
			
		}
	}

	protected void doubleClicked(int x, int y) {
		PartShape p = graphView.getNodeAt(x, y);
		
		if( p == null )
			return;
		
		if( !(p instanceof ValueNodeView) )
			return;
		
		ValueNodeView vnw = (ValueNodeView)p;
		
		final NamedValue value = vnw.getValueNode().getValue();
		
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
				NamedValue val = dialog.getParameters().get(value.getName());
				value.setValue(val);
				dialog.dispose();
				repaint();
			}
		});
		
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		
		
	}

	protected void mouseClicked(int x, int y, int modifiers) {
		
		boolean nodeAtPos = graphView.getNodeAt(x, y) != null; 
		
		if( tool == TOOL_MOUSE ){
			if( (MouseEvent.CTRL_DOWN_MASK & modifiers) == MouseEvent.CTRL_DOWN_MASK )
				addToSelection(graphView.getNodeAt(x, y));
			else
				select(graphView.getNodeAt(x, y));
			
			return;
		}
		
		if( tool == TOOL_PREVIEW && !nodeAtPos){
			addPreviewImageNode(x, y);
			return;
		}
		
		if( nodeAtPos )
			return;
		
		NamedValue val = new NamedValue("var", tool);
		addNamedValue(val, new Point(x, y));
		
	}

	private void addPreviewImageNode(int x, int y) {
		PreviewImageNode node = new PreviewImageNode(frame);
		
		ExecutableNodeView view = new ExecutableNodeView(node);
		view.setPosition(x, y);
		
		graphView.addNode(view);
		//nodes.add(view);
	}

	protected void mouseReleased(int x, int y) {
		
		PartShape shape = graphView.getNodeAt(x, y);
		if( (shape instanceof HasInputPorts) ||  (shape instanceof HasOutputPorts) ){
			if( shape != null ){
				PortView p = getPortAt(shape, x, y);
				if( p != null ){
					if( hoverPort.isInput() != p.isInput() ){
						tempLink.setTarget(p);
						graphView.addLink(tempLink);
						//links.add(tempLink);
					}
				}
			}
		}
		
		tempLink = null;
		repaint();
	}

	private void dragPort(int x, int y) {
		tempLink.getTarget().setPosition(x, y);
		
		PartShape shape = graphView.getNodeAt(x, y);
		tempLink.setColor(Color.red);
		
		if( (shape instanceof HasInputPorts) ||  (shape instanceof HasOutputPorts) ){
			//FilterNodeView n = (FilterNodeView)shape;
			
			if( shape != null ){
				PortView p = getPortAt(shape, x, y);
				if( p != null ){
					if( hoverPort.isInput() != p.isInput() ){
						tempLink.setColor(Color.blue);
					}
				}
			}
		}
		repaint();
	}
	
	private void draggingNode(int x, int y) {
		int dx = (x - mX);
		int dy = (y - mY);
		
		if( corner == SelectionHelper.HELPOS_CENTER_MIDDLE ){
			if( !selected.contains(hoverNode) )
				select(hoverNode);

			for(PartShape p : selected)
				p.move(dx, dy);
			
		}else{
			hoverNode.grow(corner, x, y);
		}
		
		
		repaint();
	}
	
	public void onMouseMoved(int x, int y){
		mX = x;
		mY = y;
		
		hoverAt(x, y);		
		repaint();
	}

	public void onDropFilter(Filter model, Point location){
		FilterNodeView n = new FilterNodeView(new FilterNode(model));
		n.setPosition(location.x, location.y);
		//nodes.add(n);
		graphView.addNode(n);
		repaint();
	}
	
	public void onDropImage(ImageDescriptor img, Point location){
		//System.out.println("Added image: " + img.getName());
		
		NamedValue imgVal = new NamedValue("Image");
		imgVal.setImage(img);
		
		ValueNodeView n = new ValueNodeView(new ValueNode(imgVal));
		n.setPosition(location.x, location.y);
		//nodes.add(n);
		graphView.addNode(n);
		repaint();
	}
	
	public void addNamedValue(NamedValue val, Point location){
		ValueNodeView n = new ValueNodeView( new ValueNode(val) );
		n.setPosition(location.x, location.y);
		//nodes.add(n);
		graphView.addNode(n);
		repaint();
	}
	
//	private PartShape getNodeAt(int x, int y){
//		for(PartShape n : graphView.getNodes()){
//			if( n.couldContain(x, y) ){
//				return n;
//			}
//		}
//		return null;
//	}
	
	private PortView getPortAt(Object shape, int x, int y){
		
		if( shape instanceof HasOutputPorts ){
			HasOutputPorts s = (HasOutputPorts)shape;
			
			for(PortView p : s.getOutputPorts())
				if( p.contains(x, y) )
					return p;
		}
		
		if( shape instanceof HasInputPorts ){
			HasInputPorts node = (HasInputPorts)shape;
			
			for(PortView p : node.getInputPorts())
				if( p.contains(x, y) )
					return p;
		}
		
		return null;
	}
	
	private void hoverAt(int x, int y){
		hoverNode = graphView.getNodeAt(x, y);
		
		hoverPort = null;
		
		if( hoverNode == null ){
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			return;
		}
		
		hoverPort = getPortAt(hoverNode, x, y);
		
		if( hoverPort != null ){
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			return;
		}
		
		corner = hoverNode.getCorner(x, y);
		setCursor(Cursor.getPredefinedCursor(SelectionHelper.CURSOR_FOR_POS[corner]));
		
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		Graphics2D g2 = (Graphics2D)g;
		
		graphView.paint(g2);
		
		if( tempLink != null )
			tempLink.render(g2);
		
		for( PartShape p : selected )
			SelectionHelper.renderSelection(p, g2);
	}
	
	public void addToSelection(PartShape p){
		if( p != null )
			if( !selected.contains(p) )
				selected.add(p);
		
		repaint();
	}
	
	public void select(PartShape p){
		//System.out.println("Selected " + p);
		selected.clear();
		addToSelection(p);
	}
	
	public int getTool() {
		return tool;
	}

	public void setTool(int tool) {
		this.tool = tool;
	}
	
	
	
	public void save(File file) throws IOException{
		Gson gson = new GsonBuilder().create();
		
		String json = gson.toJson(graphView);
		
		FileWriter out = new FileWriter(file);
		out.write(json);
		out.close();
	}

	public Graph getGraph() {
		return graphView.getGraph();
	}
}
