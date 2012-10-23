package com.cfm.imglab.test;

import com.cfm.imglab.Filter;
import com.cfm.imglab.NamedValue;
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

		values.addValue(new NamedValue(IN_MULT_1, NamedValue.TYPE_NUMBER));
		values.addValue(new NamedValue(IN_MULT_2, NamedValue.TYPE_NUMBER));

		return values;
	}

	@Override
	public void runFilter(ValueSet input, ValueSet output) {
		NamedValue mult = new NamedValue(OUT_MULT);

		double s1 = input.get(IN_MULT_1).getAsNumber();
		double s2 = input.get(IN_MULT_2).getAsNumber();

		mult.setNumber(s1 * s2);

		output.addValue(mult);
	}
	@Override
	public ValueSet getOutput() {
		ValueSet params = new ValueSet();
		
		params.addValue(new NamedValue(OUT_MULT, NamedValue.TYPE_NUMBER));		
		
		return params;
	}
}
