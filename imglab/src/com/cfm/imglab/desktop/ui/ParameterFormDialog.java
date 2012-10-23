package com.cfm.imglab.desktop.ui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.cfm.imglab.NamedValue;
import com.cfm.imglab.ValueSet;
import com.cfm.imglab.desktop.ImgLabFrame;

@SuppressWarnings("serial")
public class ParameterFormDialog extends JDialog{
	private HashMap<String, ParameterWidget> widgets;
	
	private JButton btnAccept, btnCancel;
	
	public ParameterFormDialog(final ImgLabFrame frame, ValueSet arguments){
		super(frame, "Parameters");
		
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new SpringLayout());
		
		ParameterWidgetFactory factory = new ParameterWidgetFactory(frame);
		
		widgets = new HashMap<String, ParameterWidget>();
		
		for(NamedValue p : arguments.values()){
			ParameterWidget w = factory.createFrom(p);
			
			w.addPropertyChangeListener("value", new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					ParameterFormDialog.this.revalidate();
					ParameterFormDialog.this.doLayout();
					ParameterFormDialog.this.pack();
				}
			});
			
			widgets.put(w.getName(), w);

			panel.add(new JLabel(w.getName() + ": "));
			panel.add(w);
		}
		
		SpringUtilities.makeCompactGrid(panel,
										arguments.size(), 2,
										4, 4,
										4, 4);
		
		add(panel, BorderLayout.CENTER);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new SpringLayout());
		
			btnCancel = new JButton();
			btnCancel.setText("Cancel");
//			btnCancel.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					ParameterFormDialog.this.dispose();
//				}
//			});
			
			buttonsPanel.add(btnCancel);
			
			btnAccept = new JButton();
			btnAccept.setText("Accept");
			
			buttonsPanel.add(btnAccept);
		
		
		
		SpringUtilities.makeCompactGrid(buttonsPanel,
					1, 2,
					4, 4,
					4, 4);
		
		add(buttonsPanel, BorderLayout.SOUTH);
		
		pack();
	}
	
	public ValueSet getParameters(){
		ValueSet params = new ValueSet();
		
		for(String s : widgets.keySet()){
			NamedValue v = widgets.get(s).pullValue();
			params.put(s, v);
		}
		
		return params;
	}

	public JButton getBtnAccept() {
		return btnAccept;
	}

	public JButton getBtnCancel() {
		return btnCancel;
	}
	
	public void center(){
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
}
