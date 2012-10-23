package com.cfm.imglab.filters;

import com.cfm.imglab.NamedValue;
import com.cfm.imglab.ValueSet;

public class ANDFilter extends BinaryOperatorFilter{

	@Override
	public String getName() {
		return "And";
	}

	@Override
	public ValueSet getParameters() {
		ValueSet params = new ValueSet();
		
		params.addValue(new NamedValue(FIRST_IMAGE, NamedValue.TYPE_IMAGE));
		params.addValue(new NamedValue(SECOND_IMAGE, NamedValue.TYPE_IMAGE));
		
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
