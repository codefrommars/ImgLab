package cfm.neograph.core.instructions;

import cfm.neograph.core.type.RuntimePrimitive;

public class SetBooleanInstruction extends SetMemoryInstruction{
	
	public SetBooleanInstruction(String name, Boolean b, int index) {
		this.outIndex = index;
		val = new RuntimePrimitive(name);
		val.setBoolean(b);
	}
}
