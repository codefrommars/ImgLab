package cfm.neograph.core.instructions;

import cfm.neograph.ExecutionContext;

public interface Instruction {
	public void execute(ExecutionContext ctx);
}
