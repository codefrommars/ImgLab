package com.cfm.imglab.desktop.ui.composer;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

import cfm.neograph.core.GraphNodeType;
import cfm.neograph.core.Operation;

import com.cfm.imglab.ImageDescriptor;

public class NeoGraphEditorDropTargetAdapter extends DropTargetAdapter {

	private NeoGraphEditor	editor;

	public NeoGraphEditorDropTargetAdapter(NeoGraphEditor editor) {
		this.editor = editor;
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		try {
			Transferable t = dtde.getTransferable();
			if (t.isDataFlavorSupported(ElementTransferHandler.FLAVORS[ElementTransferHandler.TYPE_FILTER])) {
				editor.onDropOperation((Operation) t.getTransferData(ElementTransferHandler.FLAVORS[ElementTransferHandler.TYPE_FILTER]), dtde.getLocation());
			}

			if (t.isDataFlavorSupported(ElementTransferHandler.FLAVORS[ElementTransferHandler.TYPE_IMAGE])) {
				editor.onDropImage((ImageDescriptor) t.getTransferData(ElementTransferHandler.FLAVORS[ElementTransferHandler.TYPE_IMAGE]), dtde.getLocation());
			}
			
			if (t.isDataFlavorSupported(ElementTransferHandler.FLAVORS[ElementTransferHandler.TYPE_VARIABLE])) {
				editor.onDropVariable( (GraphNodeType) t.getTransferData(ElementTransferHandler.FLAVORS[ElementTransferHandler.TYPE_VARIABLE]), dtde.getLocation() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
