package cfm.neograph.core.instructions;

import cfm.neograph.core.type.RuntimePrimitive;

public class SetStringInstruction extends  SetMemoryInstruction{
	
	public SetStringInstruction(String label, String str, int index) {
		this.outIndex = index;
		val = new RuntimePrimitive(label);
		val.setString(str);
	}

}
