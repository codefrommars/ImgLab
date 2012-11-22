package com.cfm.imglab.test;

import com.cfm.imglab.RuntimePrimitive;
import com.cfm.imglab.ValueSet;
import com.cfm.imglab.composer.Context;
import com.cfm.imglab.composer.FilterNode;
import com.cfm.imglab.composer.Graph;
import com.cfm.imglab.composer.Link;
import com.cfm.imglab.composer.Node;
import com.cfm.imglab.composer.Program;
import com.cfm.imglab.composer.ValueNode;

public class ProgramTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph g = new Graph();
		
		Node sum = new FilterNode(new SumFilter());
		g.addNode(sum);
		
		RuntimePrimitive valA = new RuntimePrimitive("a");
		RuntimePrimitive valB = new RuntimePrimitive("b");
		
		valA.setNumber(2);
		valB.setNumber(5);
		
		ValueNode a = new ValueNode(valA);
		ValueNode b = new ValueNode(valB);
		
		g.addLink(new Link(a.getOutputPort(), sum.getInputs().get(0)));
		g.addLink(new Link(b.getOutputPort(), sum.getInputs().get(1)));
		
		//Mult part
		Node mult = new FilterNode(new MultFilter());
		g.addNode(mult);
		
		RuntimePrimitive valC = new RuntimePrimitive("c");
		valC.setNumber(3);
		
		ValueNode c = new ValueNode(valC);
		
		g.addLink(new Link(sum.getOutputs().get(0), mult.getInputs().get(0)));
		g.addLink(new Link(c.getOutputPort(), mult.getInputs().get(1)));
		
		
		
		
		//End mult part
		Program p = g.compile();
		Context ctx = new Context(g);
		
		ValueSet out = ctx.runProgram(p);
		System.out.println(out);
		
		out = ctx.runProgram(p);
		System.out.println(out);
		
		out = ctx.runProgram(p);
		System.out.println(out);
		
	}

}
