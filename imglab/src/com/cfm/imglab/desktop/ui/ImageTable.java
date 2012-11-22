package com.cfm.imglab.desktop.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import cfm.neograph.core.ValueSet;
import cfm.neograph.core.type.RuntimePrimitive;
import cfm.neograph.core.type.RuntimeType;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.desktop.ImgLabFrame;
import com.cfm.imglab.desktop.ui.composer.ElementTransferHandler;

@SuppressWarnings("serial")
public class ImageTable extends JScrollPane{
	
	static class IconRenderer extends JLabel implements TableCellRenderer{

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			this.setIcon((ImageIcon)value);
			return this;
		}
		
	}
	
	static class TextRenderer extends JLabel implements TableCellRenderer{
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			setOpaque(true);
			setBackground(Color.white);
			
			if( isSelected )
				setBackground(Color.lightGray);
			
			setHorizontalTextPosition(JLabel.CENTER);
			setHorizontalAlignment(JLabel.CENTER);
			setText(String.valueOf(value));
			return this;
		}
	}
	
	public static class ImageTableModel extends AbstractTableModel{
		
		private ArrayList<ImageDescriptor> data;
		
		public ImageTableModel(){
			data = new ArrayList<ImageDescriptor>();
		}
		
		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public int getColumnCount() {
			return 5;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			
			ImageDescriptor desc = data.get(rowIndex);
			
			switch(columnIndex){
				case 0:
					return desc.getIcon();
				case 1:
					return desc.getName();
				case 2:
					return desc.getWidth() + "x" + desc.getHeight();
				case 3:
					return desc.getDepth();
				case 4:
					return desc.getChannels();
			}
			
			return "-";
		}
		
		public int addImage(ImageDescriptor img){
			int index = data.size();
			data.add(img);
			return index;
		}
		public ImageDescriptor getImageDescriptor(int row){
			return data.get(row);
		}
		
		public List<ImageDescriptor> getData(){
			return data;
		}
	}
	
	private JTable table;
	private ImageTableModel model;
	private ImgLabFrame frame;
	private DropTarget dropTarget;
	
	public ImageTable(ImgLabFrame frame){
		configureTable();
		this.setViewportView(table);
		this.frame = frame;
	}
	
	private void configureTable(){
		model = new ImageTableModel();
		table = new JTable(model);
		
		table.setRowHeight(60);
		
		TableColumn previewCol = table.getColumnModel().getColumn(0);
		previewCol.setHeaderValue("Preview");
		previewCol.setMinWidth(60);
		previewCol.setCellRenderer(new IconRenderer());
		
		TableColumn nameCol = table.getColumnModel().getColumn(1);
		nameCol.setHeaderValue("Name");
		nameCol.setCellRenderer(new TextRenderer());
		
		TableColumn sizeCol = table.getColumnModel().getColumn(2);
		sizeCol.setHeaderValue("Size");
		sizeCol.setCellRenderer(new TextRenderer());
		
		TableColumn depthCol = table.getColumnModel().getColumn(3);
		depthCol.setHeaderValue("Depth");
		depthCol.setCellRenderer(new TextRenderer());
		
		TableColumn channelsCol = table.getColumnModel().getColumn(4);
		channelsCol.setHeaderValue("Channels");
		channelsCol.setCellRenderer(new TextRenderer());
		
		dropTarget = new DropTarget() {
			@Override
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					
					if( droppedFiles.size() > 1 )
						return;
					
					File file = droppedFiles.get(0);
					final BufferedImage bi = ImageIO.read(file);
					
					final String FILE_NAME = "File name";
					
//					NamedValue[] params = { new NamedValue(FILE_NAME) };
					ValueSet params = new ValueSet();
					params.put(FILE_NAME, new RuntimePrimitive(FILE_NAME, RuntimeType.TYPE_STRING));
					
					final  ParameterFormDialog dialog = new ParameterFormDialog(frame, params);
					
					dialog.getBtnCancel().addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							dialog.dispose();
						}
					});
					
					dialog.getBtnAccept().addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							HashMap<String, RuntimePrimitive> params = dialog.getParameters();
							String str = (params.get(FILE_NAME)).getAsString();
							
							ImageDescriptor imgFile = new ImageDescriptor(bi, str);
							addImage(imgFile);
							
							dialog.dispose();
						}
					});
					
					dialog.center();
					
				} catch (UnsupportedFlavorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		table.setDropTarget(dropTarget);
		setDropTarget(dropTarget);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnAdjuster tca = new TableColumnAdjuster(table);
		tca.adjustColumns();
		
		table.addPropertyChangeListener(tca);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					ImageDescriptor imgDesc = getSelectedImage();
					frame.showImage(imgDesc.getImage());
				}
			}
		});
		
		table.setDragEnabled(true);
		table.setTransferHandler(new ElementTransferHandler(ElementTransferHandler.TYPE_IMAGE));
		
	}
	
	public void addImage(ImageDescriptor img){
		ImageDescriptor desc = new ImageDescriptor();
		desc.setName(img.getName());
		desc.setImage(img.getImage());
		model.addImage(desc);
		model.fireTableDataChanged();
	}
	
	public List<ImageDescriptor> getImages() {
		return model.getData();
	}
	
	public ImageDescriptor getSelectedImage(){
		int selected = table.getSelectedRow();
		return model.getImageDescriptor(selected);
	}
}
