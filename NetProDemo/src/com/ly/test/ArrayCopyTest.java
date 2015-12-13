package com.ly.test;

public class ArrayCopyTest {
	
	public static void main(String[] args) {
		int[] array = {1,2,3,4,5,6,7,8,9};
		
		
		System.arraycopy(array, 0, array,1, 2);
		print(array);
	}
	public static void print(int[] array){
		StringBuilder sb = new StringBuilder();
		for(int i :array){
			sb.append(i).append(" ");
		}
		System.out.println(sb);
	}
}
