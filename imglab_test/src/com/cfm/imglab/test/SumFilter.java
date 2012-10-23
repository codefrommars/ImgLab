package com.cfm.imglab.test;

import com.cfm.imglab.Filter;
import com.cfm.imglab.NamedValue;
import com.cfm.imglab.ValueSet;

public class SumFilter implements Filter{

	public static final String 	IN_SUM_1 = "Sumand 1",
								IN_SUM_2 = "Sumand 2",
								OUT_SUM = "sum";
	
	
	
	@Override
	public String getName() {
		return "Sum";
	}

	@Override
	public ValueSet getParameters() {
		ValueSet values = new ValueSet();
		
		values.addValue(new NamedValue(IN_SUM_1, NamedValue.TYPE_NUMBER));
		values.addValue(new NamedValue(IN_SUM_2, NamedValue.TYPE_NUMBER));
		
		return values;
	}

	@Override
	public void runFilter(ValueSet input, ValueSet output) {
		NamedValue sum = new NamedValue(OUT_SUM);
		
		double s1 = input.get(IN_SUM_1).getAsNumber();
		double s2 = input.get(IN_SUM_2).getAsNumber();
		
		sum.setNumber(s1 + s2);
		
		output.addValue(sum);
	}
	
	@Override
	public ValueSet getOutput() {
		ValueSet params = new ValueSet();
		
		params.addValue(new NamedValue(OUT_SUM, NamedValue.TYPE_NUMBER));		
		
		return params;
	}
	
}
