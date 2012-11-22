package com.cfm.imglab.test;

import com.cfm.imglab.Filter;
import com.cfm.imglab.RuntimePrimitive;
import com.cfm.imglab.ValueSet;

public class MultFilter implements Filter {

	public static final String	IN_MULT_1	= "Mult 1", 
								IN_MULT_2 = "Mult 2",
								OUT_MULT = "mult";

	@Override
	public String getName() {
		return "Mult";
	}

	@Override
	public ValueSet getParameters() {
		ValueSet values = new ValueSet();

		values.addValue(new RuntimePrimitive(IN_MULT_1, RuntimePrimitive.TYPE_NUMBER));
		values.addValue(new RuntimePrimitive(IN_MULT_2, RuntimePrimitive.TYPE_NUMBER));

		return values;
	}

	@Override
	public void runFilter(ValueSet input, ValueSet output) {
		RuntimePrimitive mult = new RuntimePrimitive(OUT_MULT);

		double s1 = input.get(IN_MULT_1).getAsNumber();
		double s2 = input.get(IN_MULT_2).getAsNumber();

		mult.setNumber(s1 * s2);

		output.addValue(mult);
	}
	@Override
	public ValueSet getOutput() {
		ValueSet params = new ValueSet();
		
		params.addValue(new RuntimePrimitive(OUT_MULT, RuntimePrimitive.TYPE_NUMBER));		
		
		return params;
	}
}
