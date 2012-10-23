package com.cfm.imglab;

import java.util.HashMap;

@SuppressWarnings("serial")
public class ValueSet extends HashMap<String, NamedValue>{
	public void addValue(NamedValue value){
		
		if( value == null ){
			System.out.println("NULL VALUE !");
			return;
		}
		
		put(value.getName(), value);
	}
}
