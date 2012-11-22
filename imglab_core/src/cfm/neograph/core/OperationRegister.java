package cfm.neograph.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class OperationRegister {
	
	private HashMap<String , Operation> register;
	
	public OperationRegister(){
		register = new HashMap<String, Operation>();
	}
	
	public void registerOperation(Operation op) {
		register.put(op.getName(), op);
	}
	
	public Operation getOperation(String name){
		return register.get(name);
	}
	
	public Vector<Operation> getRegisteredOperations(){
		
		Vector<Operation> v = new Vector<Operation>();
		
		for(Operation o : register.values())
			v.add(o);
		
		return v;
	}
	
}
