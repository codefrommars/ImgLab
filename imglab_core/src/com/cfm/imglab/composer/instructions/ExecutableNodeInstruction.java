package com.cfm.imglab.composer.instructions;

import com.cfm.imglab.composer.Context;
import com.cfm.imglab.composer.ExecutableNode;

public class ExecutableNodeInstruction implements Instruction {

	private ExecutableNode node;
	
	public ExecutableNodeInstruction(ExecutableNode node){
		this.node = node;
	}
	
	@Override
	public void execute(Context ctx) {
		node.execute(ctx);
	}

}
