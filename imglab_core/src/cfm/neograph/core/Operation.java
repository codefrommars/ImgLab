package cfm.neograph.core;


public interface Operation {
	
	public String getName();
	
	public ValueSet getParameters();
	public ValueSet getOutput();
	
	public void execute(ValueSet input, ValueSet output);
}
