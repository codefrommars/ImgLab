package cfm.neograph.core.type;

import cfm.neograph.core.GraphNodeType;
import cfm.neograph.core.NodeValue;

import com.cfm.imglab.ImageDescriptor;


public class RuntimePrimitive {
	private String name;
	private Object value;
	private RuntimeType type;
	
	public RuntimePrimitive() {
		super();
	}
	
	public RuntimePrimitive(NodeValue v){
		name = v.getType().name();
		
		if( v.getType() == GraphNodeType.Boolean )
			setBoolean((Boolean)v.getValue());
		
		if( v.getType() == GraphNodeType.Image )
			setImage((ImageDescriptor)v.getValue());
		
		if( v.getType() == GraphNodeType.Number )
			setNumber((Double)v.getValue());
		
		if( v.getType() == GraphNodeType.String )
			setString((String)v.getValue());
	}
	
	public RuntimePrimitive(String name) {
		super();
		type = RuntimeType.Undefined;
		this.name = name;
	}
	
	public RuntimePrimitive(String name, RuntimeType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setString(String text) {
		value = text;
		type = RuntimeType.TYPE_STRING;
	}
	
	public void setNumber(double val) {
		value = val;
		type = RuntimeType.TYPE_NUMBER;
	}
	
	public void setImage(ImageDescriptor img) {
		value = img;
		type = RuntimeType.TYPE_IMAGE;
	}

	public void setBoolean(boolean b){
		value = b;
		type = RuntimeType.TYPE_BOOLEAN;
	}
	
	public void setValue(RuntimePrimitive v){
		this.value = v.value;
		this.name = v.name;
		this.type = v.type;
	}
	
	public boolean isImage() {
		return type == RuntimeType.TYPE_IMAGE;
	}

	public boolean isNumber() {
		return type == RuntimeType.TYPE_NUMBER;
	}

	public boolean isString() {
		return type == RuntimeType.TYPE_STRING;
	}
	
	public boolean isBoolean() {
		return type == RuntimeType.TYPE_BOOLEAN;
	}
	
	public ImageDescriptor getAsImage(){
		return (ImageDescriptor)value;
	}
	
	public Double getAsNumber(){
		return (Double)value;
	}
	
	public String getAsString(){
		return (String)value;
	}
	
	public Boolean getAsBoolean(){
		return (Boolean)value;
	}
	
	public static RuntimePrimitive fromImage(ImageDescriptor desc){
		RuntimePrimitive value = new RuntimePrimitive();
		value.setImage(desc);
		value.setName(desc.getName());
		return value;
	}
	
	public boolean hasValue(){
		return value != null;
	}

	public Object getValue(){
		return value;
	}
	
	@Override
	public String toString() {
		return "(" + name +": " + value +")";
	}
}
