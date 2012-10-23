package com.cfm.imglab.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ButtonGroup;
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
import javax.swing.JToggleButton;

import com.cfm.imglab.Filter;
import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.NamedValue;
import com.cfm.imglab.ValueSet;
import com.cfm.imglab.composer.Context;
import com.cfm.imglab.composer.Graph;
import com.cfm.imglab.composer.Program;
import com.cfm.imglab.desktop.actions.ExitAction;
import com.cfm.imglab.desktop.actions.LoadAction;
import com.cfm.imglab.desktop.actions.RunFilterAction;
import com.cfm.imglab.desktop.actions.SaveAction;
import com.cfm.imglab.desktop.actions.SaveComposedAction;
import com.cfm.imglab.desktop.ui.ImageComponent;
import com.cfm.imglab.desktop.ui.ImageTable;
import com.cfm.imglab.desktop.ui.composer.ElementTransferHandler;
import com.cfm.imglab.desktop.ui.composer.GraphEditor;
import com.cfm.imglab.filters.ANDFilter;
import com.cfm.imglab.filters.BlendingFilter;
import com.cfm.imglab.filters.MinusFilter;
import com.cfm.imglab.filters.MirrorOperator;
import com.cfm.imglab.filters.MultiplyFilter;
import com.cfm.imglab.filters.ORFilter;
import com.cfm.imglab.filters.PlusFilter;
import com.cfm.imglab.filters.RGBtoGrayFilter;
import com.cfm.imglab.filters.RotateOperator;
import com.cfm.imglab.filters.XORFilter;
import com.cfm.imglab.filters.ZoomOperator;

@SuppressWarnings("serial")
public class ImgLabFrame extends JFrame {
	
	private ImageTable table;
	private JDesktopPane desktop;
	private Vector<Filter> supportedFilters;
	private GraphEditor graphEditor;
		
	public ImgLabFrame(){
		super("ImgLab");
		initFilters();
		
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
		
		createFiltersList();
		showGraphEditor();
		setupMenu();
	}
	
	private void showGraphEditor() {
		
		if( graphEditor == null )
			graphEditor = new GraphEditor(this);
		
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		
		JPanel bar = new JPanel();
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Graph g = graphEditor.getGraph();
				
				Context c = new Context(g);
				Program p = g.compile();
				ValueSet v = c.runProgram(p);
				
				for(NamedValue value : v.values()){
					if( value.isImage() ){
						showImage(value.getAsImage().getImage());
						addImage(value.getAsImage().getImage(), value.getName());
					}
				}
			}
		});
		bar.add(btnPlay);
		
		
		String[] titles = {"Mouse", "Image", "Number", "String", "Boolean", "Preview"};
		JToggleButton btnTools[] = new JToggleButton[titles.length];
		
		ButtonGroup group = new ButtonGroup();
		for(int i = 0; i < btnTools.length; i++){
			btnTools[i] = new JToggleButton(titles[i]);
			group.add(btnTools[i]);
			bar.add(btnTools[i]);
			final int tool = i;
			btnTools[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					graphEditor.setTool(tool);
				}
			});
		}
		
		p.add(bar, BorderLayout.NORTH);
		p.add(graphEditor, BorderLayout.CENTER);
		
		JInternalFrame graphEditorFrame = new JInternalFrame("Composer", true, false, true, true);
		graphEditorFrame.setVisible(true);
		graphEditorFrame.add(p);
		graphEditorFrame.pack();
		graphEditorFrame.setLocation(150, 0);
		add(graphEditorFrame);
	}

	private void createFiltersList(){
		JList<Filter> list = new JList<Filter>(supportedFilters);
		list.setDragEnabled(true);
		list.setDropMode(DropMode.INSERT);
		list.setTransferHandler(new ElementTransferHandler(ElementTransferHandler.TYPE_FILTER));
		
		JInternalFrame filtersFrame = new JInternalFrame("FiltersList", true, false, true, true);
		filtersFrame.setVisible(true);
		filtersFrame.add(list);
		filtersFrame.pack();
		filtersFrame.setSize(140, filtersFrame.getHeight());
		filtersFrame.setLocation(0, 0);
		add(filtersFrame);
	}
	
	
	private void initFilters() {
		supportedFilters = new Vector<Filter>();
		supportedFilters.add( new ANDFilter() );
		supportedFilters.add( new BlendingFilter() );
		supportedFilters.add( new MinusFilter() );
		supportedFilters.add( new MirrorOperator() );
		supportedFilters.add( new MultiplyFilter() );
		supportedFilters.add( new ORFilter() );
		supportedFilters.add( new PlusFilter() );
		supportedFilters.add( new RGBtoGrayFilter() );
		supportedFilters.add( new RotateOperator() );
		supportedFilters.add( new XORFilter() );
		supportedFilters.add( new ZoomOperator() );
		
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
	        
	        JMenuItem saveComposed = new JMenuItem(new SaveComposedAction(this));
	        file.add(saveComposed);
	        
	        JMenuItem exit = new JMenuItem(new ExitAction(this));
	        file.add(exit);
	        
	        JMenu filters = new JMenu("Filter");
			
			for(Filter filter : supportedFilters ){
				JMenuItem menuItemFilter = new JMenuItem(new RunFilterAction(this, filter));
				filters.add(menuItemFilter);
			}
			
		
		menuBar.add(filters);
		
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
		graphEditor.save(file);
	}
}