package com.cfm.imglab.desktop.ui.composer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.TransferHandler;

import cfm.neograph.core.GraphNodeType;
import cfm.neograph.core.Operation;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.desktop.ui.ImageTable.ImageTableModel;

@SuppressWarnings("serial")
public class ElementTransferHandler extends TransferHandler{
	
	//public static final DataFlavor DATA_FLAVOUR = new DataFlavor(Operation.class, "Filter");
	
	public static final int TYPE_UNKNOWN = 0,
					  		TYPE_FILTER = 1,
					  		TYPE_IMAGE = 2,
					  		TYPE_VARIABLE = 3;
	
	public static final DataFlavor[] FLAVORS = {
		null,
		new DataFlavor(Operation.class, "Filter"),
		new DataFlavor(ImageDescriptor.class, "ImageDescriptor"),
		new DataFlavor(GraphNodeType.class, "Value")
	};
	
    private boolean inDrag;
    private int type;
    
    public ElementTransferHandler(int type) {
    	this.type = type;
    }

    public int getSourceActions(JComponent c) {
        return TransferHandler.MOVE;
    }

    protected Transferable createTransferable(JComponent c) {
    	inDrag = true;
    	
    	switch(type){
    		case TYPE_FILTER:
    			JList<Operation> listOp = (JList<Operation>)c;
    			return makeTransferable(type, listOp.getSelectedValue());
    		case TYPE_VARIABLE:
    			JList<GraphNodeType> listVar = (JList<GraphNodeType>)c;
    			return makeTransferable(type, listVar.getSelectedValue());
    		case TYPE_IMAGE:
    			JTable table = (JTable)c;
    			int selected = table.getSelectedRow();
    			return makeTransferable(type, ((ImageTableModel)table.getModel()).getImageDescriptor(selected));
    	}

    	return null;
    }

    private Transferable makeTransferable(final int type, final Object obj) {
    	return new Transferable() {
			public DataFlavor[] getTransferDataFlavors() {
				return new DataFlavor[] { FLAVORS[type] };
			}
			
			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return flavor.equals(FLAVORS[type]);
			}
			
			public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
				return obj;
			}
		};
	}

//	private Transferable makeFilterTransferable(final Operation selectedValue) {
//		return new Transferable() {
//			public DataFlavor[] getTransferDataFlavors() {
//				return new DataFlavor[] {FLAVORS[]};
//			}
//			
//			public boolean isDataFlavorSupported(DataFlavor flavor) {
//				return flavor.equals(DATA_FLAVOUR);
//			}
//			
//			public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
//				return selectedValue;
//			}
//		};
//	}

	public boolean canImport(TransferSupport support) {
		boolean supported = false;
		
		for(int i = 1; i < FLAVORS.length; i++){
			if( support.isDataFlavorSupported(FLAVORS[i]) ){
				supported = true;
				break;
			}
		}
		
        if (!inDrag || !supported) {
            return false;
        }
        
        return true;
    }

    protected void exportDone(JComponent source, Transferable data, int action) {
        super.exportDone(source, data, action);
        inDrag = false;
    }
}
