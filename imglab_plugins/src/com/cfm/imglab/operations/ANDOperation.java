package com.cfm.imglab.operations;

import cfm.neograph.core.ValueSet;
import cfm.neograph.core.type.RuntimePrimitive;
import cfm.neograph.core.type.RuntimeType;

public class ANDOperation extends BinaryOperation{

	@Override
	public String getName() {
		return "And";
	}

	@Override
	public ValueSet getParameters() {
		ValueSet params = new ValueSet();
		
		params.addValue(new RuntimePrimitive(FIRST_IMAGE, RuntimeType.TYPE_IMAGE));
		params.addValue(new RuntimePrimitive(SECOND_IMAGE, RuntimeType.TYPE_IMAGE));
		
		return params;
	}

	@Override
	protected void operatePixels(int[] p1, int[] p2, int[] out) {
		out[0] = p1[0] & p2[0];
		out[1] = p1[1] & p2[1];
		out[2] = p1[2] & p2[2];
		out[3] = p1[3] & p2[3];
	}


}
