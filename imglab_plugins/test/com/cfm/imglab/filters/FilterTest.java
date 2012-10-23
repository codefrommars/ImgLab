package com.cfm.imglab.filters;

import java.io.File;

import com.cfm.imglab.Filter;
import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.NamedValue;
import com.cfm.imglab.ValueSet;

public class FilterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testRotation();
	}
	
	public static void testRotation(){
		ImageDescriptor m = ImageDescriptor.loadFromFile(new File("lena.jpg"));
		
		NamedValue imgParam1 = new NamedValue(UnaryOperatorFilter.INPUT_IMAGE);
		imgParam1.setImage(m);
		
		NamedValue angle = new NamedValue(RotateOperator.INPUT_ANGLE);
		angle.setNumber(-Math.PI * 0.25);
		
		NamedValue sameSize = new NamedValue(RotateOperator.INPUT_SAME_SIZE);
		sameSize.setBoolean(true);
		
		ValueSet input = new ValueSet();
		input.addValue(imgParam1);
		input.addValue(angle);
		input.addValue(sameSize);
		
		runFilter(new RotateOperator(), input);
	}
	
	public static void testUnaryOperation(UnaryOperatorFilter filter){
		ImageDescriptor m = ImageDescriptor.loadFromFile(new File("lena.jpg"));
		
		NamedValue imgParam1 = new NamedValue(UnaryOperatorFilter.INPUT_IMAGE);
		imgParam1.setImage(m);
		
		ValueSet input = new ValueSet();
		input.addValue(imgParam1);
		
		runFilter(filter, input);
	}
	
	public static void testBlending(){
		ImageDescriptor m1 = ImageDescriptor.loadFromFile(new File("lena.jpg"));
		ImageDescriptor m2 = ImageDescriptor.loadFromFile(new File("mandrill.jpg"));
		
		NamedValue imgParam1 = new NamedValue(BlendingFilter.FIRST_IMAGE);
		imgParam1.setImage(m1);
		
		NamedValue imgParam2 = new NamedValue(BlendingFilter.SECOND_IMAGE);
		imgParam2.setImage(m2);
		
		NamedValue alpha = new NamedValue(BlendingFilter.ALPHA_VALUE);
		alpha.setNumber(0.5);
		
		NamedValue beta = new NamedValue(BlendingFilter.BETA_VALUE);
		beta.setNumber(0.5);
		
		NamedValue gamma = new NamedValue(BlendingFilter.GAMMA_VALUE);
		gamma.setNumber(0);
		
		ValueSet input = new ValueSet();
		input.addValue(imgParam1);
		input.addValue(imgParam2);
		input.addValue(alpha);
		input.addValue(beta);
		input.addValue(gamma);
		
		runFilter(new BlendingFilter(), input);
	}
	
	public static void testBinaryOperation(BinaryOperatorFilter filter){
		ImageDescriptor m1 = ImageDescriptor.loadFromFile(new File("mask.jpg"));
		ImageDescriptor m2 = ImageDescriptor.loadFromFile(new File("mandrill.jpg"));
		
		NamedValue imgParam1 = new NamedValue(BinaryOperatorFilter.FIRST_IMAGE);
		imgParam1.setImage(m1);
		
		NamedValue imgParam2 = new NamedValue(BinaryOperatorFilter.SECOND_IMAGE);
		imgParam2.setImage(m2);
		
		ValueSet input = new ValueSet();
		input.addValue(imgParam1);
		input.addValue(imgParam2);
		
		runFilter(filter, input);
	}
	
	
	public static void testXOR(){
		ImageDescriptor m1 = ImageDescriptor.loadFromFile(new File("mask.jpg"));
		ImageDescriptor m2 = ImageDescriptor.loadFromFile(new File("mandrill.jpg"));
		
		NamedValue imgParam1 = new NamedValue(XORFilter.FIRST_IMAGE);
		imgParam1.setImage(m1);
		
		NamedValue imgParam2 = new NamedValue(XORFilter.SECOND_IMAGE);
		imgParam2.setImage(m2);
		
		ValueSet input = new ValueSet();
		input.addValue(imgParam1);
		input.addValue(imgParam2);
		
		runFilter(new XORFilter(), input);
	}
	
	public static void runFilter( Filter filter, ValueSet input){
		ValueSet outs = new ValueSet();
		filter.runFilter(input, outs);
		
		//Add the resulting Images
		for(String s : outs.keySet()){
			NamedValue v = outs.get(s);

			if( v.isImage() ){
				ImageDescriptor.saveToFile(v.getAsImage(), new File(v.getName() + ".png"));	
			}		

		}
		
	}
	
}
