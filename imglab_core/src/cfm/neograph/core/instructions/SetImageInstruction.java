package cfm.neograph.core.instructions;

import cfm.neograph.core.type.RuntimePrimitive;

import com.cfm.imglab.ImageDescriptor;

public class SetImageInstruction extends  SetMemoryInstruction{
	
	public SetImageInstruction(String name, ImageDescriptor img, int outIndex) {
		this.outIndex = outIndex;
		val = new RuntimePrimitive(name);
		val.setImage(img);
	}
}
