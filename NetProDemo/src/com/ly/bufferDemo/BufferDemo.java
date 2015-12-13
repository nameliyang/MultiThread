package com.ly.bufferDemo;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;


public class BufferDemo {
	public static void main(String[] args) {
		CharBuffer buffer = CharBuffer.allocate(10);
		buffer.put('l');
		buffer.put('i');
		buffer.put('y');
		buffer.put('a');
		
		print(buffer);
		printBuffer(buffer);
		
		print(buffer);
		buffer.compact();
		print(buffer);
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(10);
		
		ByteBuffer.allocateDirect(10);
		
		
	}
	public static void printBuffer(CharBuffer buffer){
		
		buffer.flip();
		
		for(int i =0;i<buffer.limit();i++){
			System.out.print(buffer.get()+" ");
		}
		
	}
	
	
	public static void print(CharBuffer buffer){
		System.out.println("limit:"+buffer.limit()+
				" position:"+buffer.position());
	}
}
