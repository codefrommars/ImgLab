package com.cfm.imglab.desktop.ui;

import com.cfm.imglab.NamedValue;
import com.cfm.imglab.desktop.ImgLabFrame;

public class ParameterWidgetFactory {
	
	private ImgLabFrame frame;
	
	public ParameterWidgetFactory(ImgLabFrame frame){
		this.frame = frame;
	}
	
	public ParameterWidget createFrom(NamedValue param){
		
		ParameterWidget w = null;
		
		if( param.isImage() ){
			w = new ImageParameterWidget(param.getName(), frame);
		}
			
		if( param.isNumber() ){
			w = new DoubleParameterWidget(param.getName(), frame);
		}
		
		if( param.isString() ){
			w = new StringParameterWidget(param.getName(), frame);
		}
		
		if( param.isBoolean() ){
			w = new BooleanParameterWidget(param.getName(), frame);
		}
		
		//w.setName(param.getName());
		
		if( param.hasValue() )
			w.pushValue(param);
		
		return w;
		
	}
}
