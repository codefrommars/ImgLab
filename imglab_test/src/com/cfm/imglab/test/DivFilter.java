package com.cfm.imglab.test;

import com.cfm.imglab.Filter;
import com.cfm.imglab.NamedValue;
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

		values.addValue(new NamedValue(IN_NUMERATOR, NamedValue.TYPE_NUMBER));
		values.addValue(new NamedValue(IN_DENOMINATOR, NamedValue.TYPE_NUMBER));

		return values;
	}

	@Override
	public void runFilter(ValueSet input, ValueSet output) {
		NamedValue div = new NamedValue(OUT_DIV);
		NamedValue rest = new NamedValue(OUT_REST);

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
		
		params.addValue(new NamedValue(OUT_DIV, NamedValue.TYPE_NUMBER));		
		params.addValue(new NamedValue(OUT_REST, NamedValue.TYPE_NUMBER));		
		
		return params;
	}
}
