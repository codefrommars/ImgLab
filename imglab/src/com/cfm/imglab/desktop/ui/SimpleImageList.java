package com.cfm.imglab.desktop.ui;

import java.awt.Component;
import java.util.Enumeration;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import com.cfm.imglab.ImageDescriptor;

@SuppressWarnings("serial")
public class SimpleImageList extends JScrollPane {

	private DefaultListModel<ImageDescriptor> listModel;
	private JList<ImageDescriptor> imageList;

	public SimpleImageList() {
		listModel = new DefaultListModel<ImageDescriptor>();

		imageList = new JList<ImageDescriptor>();
		imageList.setModel(listModel);
		imageList.setCellRenderer(new ListCellRenderer<ImageDescriptor>() {

			protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

			@Override
			public Component getListCellRendererComponent(
					JList<? extends ImageDescriptor> list, ImageDescriptor value,
					int index, boolean isSelected, boolean cellHasFocus) {

				JLabel lbl = (JLabel) defaultRenderer
						.getListCellRendererComponent(list, value, index,
								isSelected, cellHasFocus);
				lbl.setIcon(value.getIcon());
				lbl.setText(value.getName());

				return lbl;
			}
		});

		setViewportView(imageList);
		revalidate();
	}

	public void addImage(ImageDescriptor img) {
		listModel.addElement(img);
	}

	public Enumeration<ImageDescriptor> getLoadedImages() {
		return listModel.elements();
	}

	public ImageDescriptor getSelected() {
		return imageList.getSelectedValue();
	}

	public void addImages(List<ImageDescriptor> images) {
		for(ImageDescriptor desc : images)
			listModel.addElement(desc);
	}
}
