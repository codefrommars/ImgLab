package cfm.neograph.core.instructions;

import cfm.neograph.core.type.RuntimePrimitive;

public class SetNumberInstruction extends SetMemoryInstruction{
	
	public SetNumberInstruction(String name, Double num, int index) {
		this.outIndex = index;
		val = new RuntimePrimitive(name);
		val.setNumber(num);
	}
}
