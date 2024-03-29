package com.cfm.imglab.filters;

import java.io.File;

import cfm.neograph.core.Operation;
import cfm.neograph.core.ValueSet;
import cfm.neograph.core.type.RuntimePrimitive;

import com.cfm.imglab.ImageDescriptor;
import com.cfm.imglab.operations.BinaryOperation;
import com.cfm.imglab.operations.BlendingOperation;
import com.cfm.imglab.operations.ContourDetectOperation;
import com.cfm.imglab.operations.GaussianBlurOperation;
import com.cfm.imglab.operations.KMeansClusterOperation;
import com.cfm.imglab.operations.MakeMaskOperation;
import com.cfm.imglab.operations.PlusOperation;
import com.cfm.imglab.operations.RotateOperation;
import com.cfm.imglab.operations.UnaryOperation;
import com.cfm.imglab.operations.XOROperation;

public class OperationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testGaussBlur(1.0f, 5, "dog.jpg");
		testKMeans(7, "Output Image.png");
		testContourDetect("Output Image.png");
		testMakeMask(255, 0, 255, "Output Image.png");
		testPlus("Output Image.png", "dog.jpg");
	}
	
	public static void testContourDetect(String image){
		ImageDescriptor m = ImageDescriptor.loadFromFile(new File(image));
		
		RuntimePrimitive imgParam1 = new RuntimePrimitive(UnaryOperation.INPUT_IMAGE);
		imgParam1.setImage(m);
		
		ValueSet input = new ValueSet();
		input.addValue(imgParam1);
		
		runOperation(new ContourDetectOperation(), input);
	}
	
	public static void testGaussBlur(float sigma, int kernelSize, String image){
		ImageDescriptor m = ImageDescriptor.loadFromFile(new File(image));
		
		RuntimePrimitive imgParam1 = new RuntimePrimitive(UnaryOperation.INPUT_IMAGE);
		imgParam1.setImage(m);
		
		RuntimePrimitive imgParam2 = new RuntimePrimitive(GaussianBlurOperation.INPUT_KERNEL_SIZE);
		imgParam2.setNumber(kernelSize);
		
		RuntimePrimitive imgParam3 = new RuntimePrimitive(GaussianBlurOperation.INPUT_SIGMA);
		imgParam3.setNumber(kernelSize);
		
		ValueSet input = new ValueSet();
		input.addValue(imgParam1);
		input.addValue(imgParam2);
		input.addValue(imgParam3);
		
		runOperation(new GaussianBlurOperation(), input);
	}
	
	public static void testKMeans(int k, String image){
		ImageDescriptor m = ImageDescriptor.loadFromFile(new File(image));
		
		RuntimePrimitive imgParam1 = new RuntimePrimitive(UnaryOperation.INPUT_IMAGE);
		imgParam1.setImage(m);
		
		RuntimePrimitive imgParam2 = new RuntimePrimitive(KMeansClusterOperation.INPUT_K);
		imgParam2.setNumber(k);
		
		ValueSet input = new ValueSet();
		input.addValue(imgParam1);
		input.addValue(imgParam2);
		
		runOperation(new KMeansClusterOperation(), input);
	}
	
	public static void testRotation(){
		ImageDescriptor m = ImageDescriptor.loadFromFile(new File("lena.jpg"));
		
		RuntimePrimitive imgParam1 = new RuntimePrimitive(UnaryOperation.INPUT_IMAGE);
		imgParam1.setImage(m);
		
		RuntimePrimitive angle = new RuntimePrimitive(RotateOperation.INPUT_ANGLE);
		angle.setNumber(-Math.PI * 0.25);
		
		RuntimePrimitive sameSize = new RuntimePrimitive(RotateOperation.INPUT_SAME_SIZE);
		sameSize.setBoolean(true);
		
		ValueSet input = new ValueSet();
		input.addValue(imgParam1);
		input.addValue(angle);
		input.addValue(sameSize);
		
		runOperation(new RotateOperation(), input);
	}
	
	public static void testUnaryOperation(UnaryOperation Operation){
		ImageDescriptor m = ImageDescriptor.loadFromFile(new File("lena.jpg"));
		
		RuntimePrimitive imgParam1 = new RuntimePrimitive(UnaryOperation.INPUT_IMAGE);
		imgParam1.setImage(m);
		
		ValueSet input = new ValueSet();
		input.addValue(imgParam1);
		
		runOperation(Operation, input);
	}
	
	public static void testBlending(){
		ImageDescriptor m1 = ImageDescriptor.loadFromFile(new File("lena.jpg"));
		ImageDescriptor m2 = ImageDescriptor.loadFromFile(new File("mandrill.jpg"));
		
		RuntimePrimitive imgParam1 = new RuntimePrimitive(BlendingOperation.FIRST_IMAGE);
		imgParam1.setImage(m1);
		
		RuntimePrimitive imgParam2 = new RuntimePrimitive(BlendingOperation.SECOND_IMAGE);
		imgParam2.setImage(m2);
		
		RuntimePrimitive alpha = new RuntimePrimitive(BlendingOperation.ALPHA_VALUE);
		alpha.setNumber(0.5);
		
		RuntimePrimitive beta = new RuntimePrimitive(BlendingOperation.BETA_VALUE);
		beta.setNumber(0.5);
		
		RuntimePrimitive gamma = new RuntimePrimitive(BlendingOperation.GAMMA_VALUE);
		gamma.setNumber(0);
		
		ValueSet input = new ValueSet();
		input.addValue(imgParam1);
		input.addValue(imgParam2);
		input.addValue(alpha);
		input.addValue(beta);
		input.addValue(gamma);
		
		runOperation(new BlendingOperation(), input);
	}
	
	public static void testBinaryOperation(BinaryOperation Operation){
		ImageDescriptor m1 = ImageDescriptor.loadFromFile(new File("mask.jpg"));
		ImageDescriptor m2 = ImageDescriptor.loadFromFile(new File("mandrill.jpg"));
		
		RuntimePrimitive imgParam1 = new RuntimePrimitive(BinaryOperation.FIRST_IMAGE);
		imgParam1.setImage(m1);
		
		RuntimePrimitive imgParam2 = new RuntimePrimitive(BinaryOperation.SECOND_IMAGE);
		imgParam2.setImage(m2);
		
		ValueSet input = new ValueSet();
		input.addValue(imgParam1);
		input.addValue(imgParam2);
		
		runOperation(Operation, input);
	}
	
	
	public static void testXOR(){
		ImageDescriptor m1 = ImageDescriptor.loadFromFile(new File("mask.jpg"));
		ImageDescriptor m2 = ImageDescriptor.loadFromFile(new File("mandrill.jpg"));
		
		RuntimePrimitive imgParam1 = new RuntimePrimitive(XOROperation.FIRST_IMAGE);
		imgParam1.setImage(m1);
		
		RuntimePrimitive imgParam2 = new RuntimePrimitive(XOROperation.SECOND_IMAGE);
		imgParam2.setImage(m2);
		
		ValueSet input = new ValueSet();
		input.addValue(imgParam1);
		input.addValue(imgParam2);
		
		runOperation(new XOROperation(), input);
	}
	
	public static void testPlus(String img1, String img2){
		ImageDescriptor m1 = ImageDescriptor.loadFromFile(new File(img1));
		ImageDescriptor m2 = ImageDescriptor.loadFromFile(new File(img2));
		
		RuntimePrimitive imgParam1 = new RuntimePrimitive(PlusOperation.FIRST_IMAGE);
		imgParam1.setImage(m1);
		
		RuntimePrimitive imgParam2 = new RuntimePrimitive(PlusOperation.SECOND_IMAGE);
		imgParam2.setImage(m2);
		
		ValueSet input = new ValueSet();
		input.addValue(imgParam1);
		input.addValue(imgParam2);
		
		runOperation(new XOROperation(), input);
	}
	
	public static void testMakeMask(int r, int g, int b, String image){
		ImageDescriptor m = ImageDescriptor.loadFromFile(new File(image));
		
		RuntimePrimitive imgParam1 = new RuntimePrimitive(UnaryOperation.INPUT_IMAGE);
		imgParam1.setImage(m);
		
		RuntimePrimitive intR = new RuntimePrimitive(MakeMaskOperation.INPUT_R);
		intR.setNumber(r);
		RuntimePrimitive intG = new RuntimePrimitive(MakeMaskOperation.INPUT_G);
		intG.setNumber(g);
		RuntimePrimitive intB = new RuntimePrimitive(MakeMaskOperation.INPUT_B);
		intB.setNumber(b);
		
		ValueSet input = new ValueSet();
		input.addValue(imgParam1);
		input.addValue(intR);
		input.addValue(intG);
		input.addValue(intB);
		
		runOperation(new MakeMaskOperation(), input);
	}
	
	public static void runOperation( Operation op, ValueSet input){
		ValueSet outs = new ValueSet();
		op.execute(input, outs);
		
		//Add the resulting Images
		for(String s : outs.keySet()){
			RuntimePrimitive v = outs.get(s);
			if( v.isImage() ){
				ImageDescriptor.saveToFile(v.getAsImage(), new File(v.getName() + ".png"));	
			}		
		}
	}
	
}
