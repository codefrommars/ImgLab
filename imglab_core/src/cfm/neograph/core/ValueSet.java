package cfm.neograph.core;

import java.util.HashMap;

import cfm.neograph.core.type.RuntimePrimitive;

@SuppressWarnings("serial")
public class ValueSet extends HashMap<String, RuntimePrimitive>{
	public void addValue(RuntimePrimitive value){
		
		if( value == null ){
			System.out.println("NULL VALUE !");
			return;
		}
		
		put(value.getName(), value);
	}
}
