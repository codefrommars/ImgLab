package com.cfm.imglab.desktop.ui.composer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.TransferHandler;

import com.cfm.imglab.Filter;
import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.desktop.ui.ImageTable.ImageTableModel;

@SuppressWarnings("serial")
public class ElementTransferHandler extends TransferHandler{
	
	public static final DataFlavor DATA_FLAVOUR = new DataFlavor(Filter.class, "Filter");
	
	public static final int TYPE_UNKNOWN = 0,
					  		TYPE_FILTER = 1,
					  		TYPE_IMAGE = 2,
					  		TYPE_NUMBER = 3,
					  		TYPE_BOOLEAN = 4;
	
	public static final DataFlavor[] FLAVORS = {
		null,
		new DataFlavor(Filter.class, "Filter"),
		new DataFlavor(ImageDescriptor.class, "ImageDescriptor"),
		new DataFlavor(Double.class, "Number"),
		new DataFlavor(Boolean.class, "Boolean")
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
    			JList<Filter> list = (JList<Filter>)c;
    			return makeTransferable(type, list.getSelectedValue());
    			//return makeFilterTransferable(list.getSelectedValue());
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

	private Transferable makeFilterTransferable(final Filter selectedValue) {
		return new Transferable() {
			public DataFlavor[] getTransferDataFlavors() {
				return new DataFlavor[] {DATA_FLAVOUR};
			}
			
			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return flavor.equals(DATA_FLAVOUR);
			}
			
			public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
				return selectedValue;
			}
		};
	}

	public boolean canImport(TransferSupport support) {
        if (!inDrag || !support.isDataFlavorSupported(DATA_FLAVOUR)) {
            return false;
        }
        
        return true;
    }

    protected void exportDone(JComponent source, Transferable data, int action) {
        super.exportDone(source, data, action);
        inDrag = false;
    }
}
