package cfm.neograph.core.instructions;

import cfm.neograph.ExecutionContext;
import cfm.neograph.core.type.RuntimePrimitive;

public class SetMemoryInstruction implements Instruction{
	protected RuntimePrimitive val;
	protected int outIndex;
	
	public void execute(ExecutionContext ctx) {
		//ctx.getMemory()[outIndex] = val;
		ctx.setMemory(outIndex, val);
	}
	
	@Override
	public String toString() {
		return "Set " + val.toString() + " to: " + outIndex + "\n";
	}
	
}
