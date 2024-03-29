package com.cfm.imglab.operations;

public class MinusOperation extends BinaryOperation {

	@Override
	public String getName() {
		return "Minus";
	}

	@Override
	protected void operatePixels(int[] p1, int[] p2, int[] out) {
		out[0] = p1[0] - p2[0];
		out[1] = p1[1] - p2[1];
		out[2] = p1[2] - p2[2];
		out[3] = p1[3] - p2[3];
	}

}
