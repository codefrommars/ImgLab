package com.cfm.imglab.composer;

import java.util.Stack;

import com.cfm.imglab.composer.instructions.Instruction;

public class Program {
	
	private Stack<Instruction> instructions;
	
	public Program(){
		instructions = new Stack<Instruction>();
	}
	
	public void pushInstruction(Instruction i){
		instructions.push(i);
	}
	
	public void run(Context ctx){
		
		int i = instructions.size() - 1;
		
		while( i >= 0 ){
			Instruction inst = instructions.get(i);
			inst.execute(ctx);
			i--;
		}
	}

	@Override
	public String toString() {
		String nl = System.getProperty("line.separator");
		
		StringBuilder str = new StringBuilder();
		str.append("=Program=").append(nl);
		
		for(Instruction i : instructions ){
			str.append(i.toString());
		}
		
		return str.toString();
	}
	
	
	
}
