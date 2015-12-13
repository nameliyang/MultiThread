package com.ly.chapter1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public class NIOServer {
	private final int port;
	private Selector selector;
	
	
	public NIOServer(int port) throws IOException{
		this.port = port;
		selector = Selector.open();
	}
	
	public void start() throws IOException, InterruptedException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		ServerSocket serverSocket = serverSocketChannel.socket();
		serverSocket.bind(new InetSocketAddress(port));
		
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while(selector.select()>0){
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			
			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			
			while(iterator.hasNext()){
				
				SelectionKey key = iterator.next();
				iterator.remove();
				
				if(key.isAcceptable()){
					ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
					SocketChannel socketChannel = serverChannel.accept();
					
					socketChannel.configureBlocking(false);
					
					socketChannel.register(selector, SelectionKey.OP_READ,new StringBuilder());
					System.out.println("---accept---");
				}
				
				if(key.isReadable()){
					SocketChannel socketChannel = (SocketChannel) key.channel();
					StringBuilder attachment = (StringBuilder) key.attachment();
					ByteBuffer buffer = ByteBuffer.allocate(20);
					socketChannel.read(buffer);
					buffer.flip();
					Thread.sleep(800);
					StringBuilder sb = new StringBuilder();
					while(buffer.hasRemaining()){
						sb.append((char)buffer.get());
					}
					attachment.append(sb);
					System.out.println(sb);
					String temp = attachment.toString();
					if(temp.contains("byte")){
						key.cancel();
						socketChannel.close();
					}
					
				}
				
				if(key.isWritable()){
					SocketChannel socketChannel = (SocketChannel) key.channel();
					StringBuilder sb = (StringBuilder) key.attachment();
					
				}
			}
			
		}
		
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		NIOServer server = new NIOServer(8080);
		server.start();
	}
	
	
}
