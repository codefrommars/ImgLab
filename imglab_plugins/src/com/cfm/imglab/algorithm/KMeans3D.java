package com.cfm.imglab.algorithm;

public class KMeans3D {
	
	public KMeans3D(){
		
	}
	
	public boolean iterate(float data_x[], float data_y[], float data_z[], float c_x[], float c_y[], float c_z[], int index[]){
		int n = data_x.length;
		int k = c_x.length;
		
		boolean converged = true;
		
		//Assignment - check for changes.
		for(int i = 0; i < n; i++){
			float min = Float.MAX_VALUE;
			int min_idx = 0;
			for(int c = 0; c < k; c++){
				float mean = mean(data_x[i], data_y[i], data_z[i], c_x[c], c_y[c], c_z[c]);
				if( mean < min ){
					min = mean;
					min_idx = c;
				}
			}
			
			//check for changes
			if( min_idx != index[i] )
				converged = false;
			
			index[i] = min_idx;
		}
		
		float sum_x[] = new float[k];
		float sum_y[] = new float[k];
		float sum_z[] = new float[k];
		int count[] = new int[k];
		
		for(int i = 0; i < n; i++){
			int curr_k = index[i];
			sum_x[curr_k] += data_x[i]; 
			sum_y[curr_k] += data_y[i]; 
			sum_z[curr_k] += data_z[i];
			count[curr_k]++;
		}
		
		for(int i = 0; i < k; i++){
			
			if( count[i] == 0 )
				continue;
			
			c_x[i] = sum_x[i] / count[i];
			c_y[i] = sum_y[i] / count[i];
			c_z[i] = sum_z[i] / count[i];
		}
		
		return converged;
	}
	
	private float mean(float x, float y, float z, float cx, float cy, float cz){
		return mag(cx - x, cy - y, cz - z);
//		float xx = cx - x;
//		float yy = cy - y;
//		float zz = cz - z;
//		
//		return xx * xx + yy * yy + zz * zz;  
	}
	
	private float mag(float x, float y, float z){
		float xx = x * x;
		float yy = y * y;
		float zz = z * z;
		
		return xx + yy + zz;
	}
}
