package com.ly.test;

import java.nio.ByteBuffer;


public class BufferTest {
	public static void main(String[] args) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(10);
		System.out.println(byteBuffer.getClass().getName());
		
		byteBuffer.put((byte)'H');
		byteBuffer.put((byte)'H');
		byteBuffer.put((byte)'H');
		
		print(byteBuffer);
		
		//byteBuffer.flip();
		byteBuffer.compact();
		print(byteBuffer);
	}
	
	public static void print(ByteBuffer byteBuffer){
		System.out.println("postion:"+byteBuffer.position()+",limit:"+byteBuffer.limit());
	}
}
