package cfm.neograph.core.instructions;

import java.util.ArrayList;

import cfm.neograph.ExecutionContext;

public class BlockInstruction implements Instruction{

	private ArrayList<Instruction> instructions;
	
	public BlockInstruction(){
		instructions = new ArrayList<Instruction>();
	}
	
	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}
	public void addInstruction(Instruction i) {
		instructions.add(i);
	}

	public void execute(ExecutionContext ctx) {
		for(Instruction i : instructions)
			i.execute(ctx);
	}

	@Override
	public String toString() {
		
		String str = "{\n";
		
		for(Instruction i : instructions)
			str = str + i.toString() + "\n";
		
		return str + "}\n";
		
	}
	
	

}
