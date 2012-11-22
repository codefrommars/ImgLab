package com.cfm.imglab.test;

import com.cfm.imglab.Filter;
import com.cfm.imglab.RuntimePrimitive;
import com.cfm.imglab.ValueSet;

public class DivFilter implements Filter {

	public static final String	IN_NUMERATOR	= "NUMERATOR", 
								IN_DENOMINATOR = "DENOMINATOR",
								OUT_DIV = "div",
								OUT_REST = "rest";

	@Override
	public String getName() {
		return "Div";
	}

	@Override
	public ValueSet getParameters() {
		ValueSet values = new ValueSet();

		values.addValue(new RuntimePrimitive(IN_NUMERATOR, RuntimePrimitive.TYPE_NUMBER));
		values.addValue(new RuntimePrimitive(IN_DENOMINATOR, RuntimePrimitive.TYPE_NUMBER));

		return values;
	}

	@Override
	public void runFilter(ValueSet input, ValueSet output) {
		RuntimePrimitive div = new RuntimePrimitive(OUT_DIV);
		RuntimePrimitive rest = new RuntimePrimitive(OUT_REST);

		double s1 = input.get(IN_NUMERATOR).getAsNumber();
		double s2 = input.get(IN_DENOMINATOR).getAsNumber();

		double coc = s1 / s2;
		
		div.setNumber(Math.floor(coc));
		rest.setNumber(coc - Math.floor(coc));
		
		output.addValue(div);
		output.addValue(rest);
	}
	@Override
	public ValueSet getOutput() {
		ValueSet params = new ValueSet();
		
		params.addValue(new RuntimePrimitive(OUT_DIV, RuntimePrimitive.TYPE_NUMBER));		
		params.addValue(new RuntimePrimitive(OUT_REST, RuntimePrimitive.TYPE_NUMBER));		
		
		return params;
	}
}
