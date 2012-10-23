package com.cfm.imglab.filters;

import com.cfm.imglab.NamedValue;
import com.cfm.imglab.ValueSet;

public class BlendingFilter extends BinaryOperatorFilter{
	
	public static final String	ALPHA_VALUE = "Alpha value",
								BETA_VALUE = "Beta value",
								GAMMA_VALUE = "Gamma value";

	
	protected double alpha, beta, gamma;
	
	@Override
	public String getName() {
		return "Blending";
	}

	@Override
	public ValueSet getParameters() {
		ValueSet params = new ValueSet();
		
		params.addValue(new NamedValue(FIRST_IMAGE, NamedValue.TYPE_IMAGE));
		params.addValue(new NamedValue(ALPHA_VALUE, NamedValue.TYPE_NUMBER));
		params.addValue(new NamedValue(SECOND_IMAGE, NamedValue.TYPE_IMAGE));
		params.addValue(new NamedValue(BETA_VALUE, NamedValue.TYPE_NUMBER));
		params.addValue(new NamedValue(GAMMA_VALUE, NamedValue.TYPE_NUMBER));
		
		return params;
	}

	@Override
	protected void setup(ValueSet input) {
		super.setup(input);
		alpha = input.get(ALPHA_VALUE).getAsNumber();
		beta = input.get(BETA_VALUE).getAsNumber();
		gamma = input.get(GAMMA_VALUE).getAsNumber();
	}


	@Override
	protected void operatePixels(int[] p1, int[] p2, int[] out) {
		out[0] = (int)(alpha * p1[0] + beta * p2[0] + gamma);
		out[1] = (int)(alpha * p1[1] + beta * p2[1] + gamma);
		out[2] = (int)(alpha * p1[2] + beta * p2[2] + gamma);
		out[3] = (int)(alpha * p1[3] + beta * p2[3] + gamma);
	}
	
}
