package com.ly.bufferDemo;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo {
	public static void main(String[] args) throws Exception{
		File f = new File("D:/test.txt");
		RandomAccessFile raf = new RandomAccessFile(f,"rw");
		FileChannel channel = raf.getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(10);
		channel.read(byteBuffer);
		System.out.println(byteBuffer.position());
		
	}
}
