package cfm.neograph.core.instructions;

import cfm.neograph.ExecutionContext;
import cfm.neograph.core.Operation;
import cfm.neograph.core.type.RuntimePrimitive;

public class MoveMemoryInstruction implements Instruction{
	protected int fromIndex, toIndex;
	protected String newName;
	
	public MoveMemoryInstruction(int fromIndex, int toIndex, String newName) {
		super();
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
		this.newName = newName;
	}

	public void execute(ExecutionContext ctx) {
		RuntimePrimitive p = new RuntimePrimitive();
		//p.setValue(ctx.getMemory()[fromIndex]);
		p.setValue(ctx.getMemory(fromIndex));
		p.setName(newName);
		
		//ctx.getMemory()[toIndex] = p;
		ctx.setMemory(toIndex, p);
	}
	
	@Override
	public String toString() {
		return "Move from: " + fromIndex + " to: " + toIndex + " as: " + newName + "\n";
	}
	
}
