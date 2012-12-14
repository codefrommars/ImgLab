package com.cfm.imglab.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import cfm.neograph.Evaluator;
import cfm.neograph.core.Graph;
import cfm.neograph.core.GraphNodeType;
import cfm.neograph.core.Operation;
import cfm.neograph.core.OperationRegister;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.desktop.actions.ExitAction;
import com.cfm.imglab.desktop.actions.LoadAction;
import com.cfm.imglab.desktop.actions.LoadComposedAction;
import com.cfm.imglab.desktop.actions.RunOperationAction;
import com.cfm.imglab.desktop.actions.SaveAction;
import com.cfm.imglab.desktop.actions.SaveComposedAction;
import com.cfm.imglab.desktop.operators.AddImageOperation;
import com.cfm.imglab.desktop.ui.ImageComponent;
import com.cfm.imglab.desktop.ui.ImageTable;
import com.cfm.imglab.desktop.ui.composer.ElementTransferHandler;
import com.cfm.imglab.desktop.ui.composer.NeoGraphEditor;
import com.cfm.imglab.operations.ANDOperation;
import com.cfm.imglab.operations.BlendingOperation;
import com.cfm.imglab.operations.ContourDetectOperation;
import com.cfm.imglab.operations.GaussianBlurOperation;
import com.cfm.imglab.operations.KMeansClusterOperation;
import com.cfm.imglab.operations.MakeMaskOperation;
import com.cfm.imglab.operations.MinusOperation;
import com.cfm.imglab.operations.MirrorOperation;
import com.cfm.imglab.operations.MultiplyOperation;
import com.cfm.imglab.operations.OROperation;
import com.cfm.imglab.operations.PlusOperation;
import com.cfm.imglab.operations.RGBtoGrayOperation;
import com.cfm.imglab.operations.RotateOperation;
import com.cfm.imglab.operations.XOROperation;
import com.cfm.imglab.operations.ZoomOperation;

@SuppressWarnings("serial")
public class ImgLabFrame extends JFrame {
	
	private ImageTable table;
	private JDesktopPane desktop;
	private OperationRegister supportedOperations;
	private Vector<GraphNodeType> supportedVariables;
	private NeoGraphEditor graphEditor;
		
	public ImgLabFrame(){
		super("ImgLab");
		initOperations();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1280, 720));
		table = new ImageTable(this);
		
		desktop = new JDesktopPane();
		desktop.setPreferredSize(getPreferredSize());
		setContentPane(desktop);
		
		JInternalFrame tableFrame = new JInternalFrame("ImageList", true, false, true, true);
		tableFrame.setVisible(true);
		tableFrame.add(table);
		tableFrame.pack();
		tableFrame.setLocation(700, 0);
		add(tableFrame);
		
		createOperationsList();
		createVariablesList();
		showGraphEditor();
		setupMenu();
	}
	
	private void showGraphEditor() {
		
		if( graphEditor == null ){
			graphEditor = new NeoGraphEditor(this);
			graphEditor.edit(new Graph());
		}
		
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		
		JPanel bar = new JPanel();
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Graph g = graphEditor.getGraph();
				Evaluator evaluator = new Evaluator();
				evaluator.execute(g);
				
//				Graph g = graphEditor.getGraph();
//				
//				Context c = new Context(g);
//				Program p = g.compile();
//				ValueSet v = c.runProgram(p);
//				
//				for(RuntimePrimitive value : v.values()){
//					if( value.isImage() ){
//						showImage(value.getAsImage().getImage());
//						addImage(value.getAsImage().getImage(), value.getName());
//					}
//				}
			}
		});
		bar.add(btnPlay);
		
		
//		String[] titles = {"Mouse", "Image", "Number", "String", "Boolean", "Preview"};
//		JToggleButton btnTools[] = new JToggleButton[titles.length];
//		
//		ButtonGroup group = new ButtonGroup();
//		for(int i = 0; i < btnTools.length; i++){
//			btnTools[i] = new JToggleButton(titles[i]);
//			group.add(btnTools[i]);
//			bar.add(btnTools[i]);
//			final int tool = i;
//			btnTools[i].addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					//graphEditor.setTool(tool);
//				}
//			});
//		}
		
		p.add(bar, BorderLayout.NORTH);
		p.add(graphEditor, BorderLayout.CENTER);
		
		JInternalFrame graphEditorFrame = new JInternalFrame("Composer", true, false, true, true);
		graphEditorFrame.setVisible(true);
		graphEditorFrame.add(p);
		graphEditorFrame.pack();
		graphEditorFrame.setLocation(150, 0);
		add(graphEditorFrame);
	}

	private void createOperationsList(){
		JList<Operation> list = new JList<Operation>(supportedOperations.getRegisteredOperations());
		list.setDragEnabled(true);
		list.setDropMode(DropMode.INSERT);
		list.setTransferHandler(new ElementTransferHandler(ElementTransferHandler.TYPE_FILTER));
		
		JInternalFrame opFrame = new JInternalFrame("OperationsList", true, false, true, true);
		opFrame.setVisible(true);
		opFrame.add(list);
		opFrame.pack();
		opFrame.setSize(140, opFrame.getHeight());
		opFrame.setLocation(0, 150);
		add(opFrame);
	}
	
	private void createVariablesList(){
		
		supportedVariables = new Vector<GraphNodeType>();
		supportedVariables.add(GraphNodeType.Boolean);
		supportedVariables.add(GraphNodeType.Number);
		supportedVariables.add(GraphNodeType.String);
		
		JList<GraphNodeType> list = new JList<GraphNodeType>(supportedVariables);
		list.setDragEnabled(true);
		list.setDropMode(DropMode.INSERT);
		list.setTransferHandler(new ElementTransferHandler(ElementTransferHandler.TYPE_VARIABLE));
		
		JInternalFrame varFrame = new JInternalFrame("Variables", true, false, true, true);
		varFrame.setVisible(true);
		varFrame.add(list);
		varFrame.pack();
		varFrame.setSize(140, varFrame.getHeight());
		varFrame.setLocation(0, 0);
		add(varFrame);
	}
	
	
	private void initOperations() {
		supportedOperations = new OperationRegister();
		supportedOperations.registerOperation( new ANDOperation() );
		supportedOperations.registerOperation( new BlendingOperation() );
		supportedOperations.registerOperation( new MinusOperation() );
		supportedOperations.registerOperation( new MirrorOperation() );
		supportedOperations.registerOperation( new MultiplyOperation() );
		supportedOperations.registerOperation( new OROperation() );
		supportedOperations.registerOperation( new PlusOperation() );
		supportedOperations.registerOperation( new RGBtoGrayOperation() );
		supportedOperations.registerOperation( new RotateOperation() );
		supportedOperations.registerOperation( new XOROperation() );
		supportedOperations.registerOperation( new ZoomOperation() );
		supportedOperations.registerOperation( new AddImageOperation(this) );
		supportedOperations.registerOperation( new KMeansClusterOperation() );
		supportedOperations.registerOperation( new GaussianBlurOperation());
		supportedOperations.registerOperation( new ContourDetectOperation() );
		supportedOperations.registerOperation( new MakeMaskOperation());
		
	}

	private void setupMenu(){
		JMenuBar menuBar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		menuBar.add(file);
			
			JMenuItem load = new JMenuItem(new LoadAction(this));
	        file.add(load);
	
	        JMenuItem save = new JMenuItem(new SaveAction(this));
	        file.add(save);
	        
	        file.add(new JSeparator());
	        
	        JMenuItem loadComposed = new JMenuItem(new LoadComposedAction(this));
	        file.add(loadComposed);
	        
	        JMenuItem saveComposed = new JMenuItem(new SaveComposedAction(this));
	        file.add(saveComposed);
	        
	        JMenuItem exit = new JMenuItem(new ExitAction(this));
	        file.add(exit);
	        
	        JMenu Operations = new JMenu("Operation");
			
			for(Operation Operation : supportedOperations.getRegisteredOperations() ){
				JMenuItem menuItemOperation = new JMenuItem(new RunOperationAction(this, Operation));
				Operations.add(menuItemOperation);
			}
			
		
		menuBar.add(Operations);
		
		this.setJMenuBar(menuBar);
	}
	
	public void addImage(BufferedImage image, String filename){
		table.addImage(new ImageDescriptor(image, filename));
	}
	
	public void showImage(BufferedImage image){
		ImageComponent imgComp = new ImageComponent();
		imgComp.setImage(image);
		
		JInternalFrame imageFrame = new JInternalFrame("Image", true, true, true, true);
		imageFrame.add(imgComp);
		imageFrame.setVisible(true);
		imageFrame.pack();
		imageFrame.setLocation(500, 0);
		add(imageFrame);
		
	}
	
	public ImageTable getImageTable(){
		return table;
	}
	
	public ImageDescriptor getSelectedImage(){
		return table.getSelectedImage();
	}
	
	public void saveEditedGraph(File file) throws IOException{
		//graphEditor.save(file);
		graphEditor.save(file, supportedOperations);
	}

	public void loadGraph(File f) {
		graphEditor.loadGraph(f, supportedOperations);
	}
}