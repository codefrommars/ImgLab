package com.cfm.imglab.composer;

import com.cfm.imglab.Filter;
import com.cfm.imglab.NamedValue;

public class FilterNode extends Node{
	protected Filter filter;

	public FilterNode(Filter filter) {
		super();
		this.filter = filter;
		
		//Add input ports
		for( NamedValue v : filter.getParameters().values() )
			inputs.add(new InputPort(this, v));
		
		//Add output ports
		for( NamedValue v : filter.getOutput().values() )
			outputs.add(new OutputPort(this, v));
	}

	@Override
	public String toString() {
		return "[" + filter.getName() + "]";
	}

	public Filter getFilter() {
		return filter;
	}
}
