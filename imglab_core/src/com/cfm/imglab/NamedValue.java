package com.cfm.imglab;


public class NamedValue {
	private String name;
	private Object value;

	public static final int TYPE_UNDEFINED 	= 0,
							TYPE_IMAGE 		= 1, 
							TYPE_NUMBER 	= 2,
							TYPE_STRING 	= 3,
							TYPE_BOOLEAN	= 4;
	
	private int type;
	
	public NamedValue() {
		super();
	}
	
	public NamedValue(String name) {
		super();
		this.name = name;
	}
	
	public NamedValue(String name, int type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
//	public void setValue(T value) {
//		this.value = value;
//	}
	public void setString(String text) {
		value = text;
		type = TYPE_STRING;
	}
	
	public void setNumber(double val) {
		value = val;
		type = TYPE_NUMBER;
	}
	
	public void setImage(ImageDescriptor img) {
		value = img;
		type = TYPE_IMAGE;
	}

	public void setBoolean(boolean b){
		value = b;
		type = TYPE_BOOLEAN;
	}
	
	public void setValue(NamedValue v){
		this.value = v.value;
		this.name = v.name;
		this.type = v.type;
	}
	
	public boolean isImage() {
		return type == TYPE_IMAGE;
	}

	public boolean isNumber() {
		return type == TYPE_NUMBER;
	}

	public boolean isString() {
		return type == TYPE_STRING;
	}
	
	public boolean isBoolean() {
		return type == TYPE_BOOLEAN;
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
	
	public static NamedValue fromImage(ImageDescriptor desc){
		NamedValue value = new NamedValue();
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
