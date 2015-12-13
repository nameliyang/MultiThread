package com.ly.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public class SelectorDemo {
	public static void main(String[] args) throws Exception {
		
		NioServer server = new NioServer(8080);
		server.start();
		
	}
}
class NioServer{
	
	private final int port ;
	private final Selector selector ;	
	
	public NioServer(int port) throws IOException{
		this.port = port;
		selector = Selector.open();
	}
	
	public void start() throws IOException{
		System.out.println("服务正在启动");
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		ServerSocket serverSocket = serverSocketChannel.socket();
	        //进行服务的绑定
	    serverSocket.bind(new InetSocketAddress(port));
	    
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while(selector.select()>0){
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			while(iterator.hasNext()){
				
				SelectionKey key = iterator.next();
				iterator.remove();
				int ops = key.readyOps();
				
				if((ops&SelectionKey.OP_ACCEPT)==SelectionKey.OP_ACCEPT){
					ServerSocketChannel channel =  (ServerSocketChannel) key.channel();
					SocketChannel clientChannel = channel.accept();
					System.out.println("-----accept a socket-----");
					clientChannel.configureBlocking(false);
					clientChannel.register(selector, SelectionKey.OP_READ);
				}
				
				if((ops&SelectionKey.OP_READ)==SelectionKey.OP_READ){
					
					SocketChannel channel = (SocketChannel) key.channel();
					ByteBuffer byteBuffer = ByteBuffer.allocate(10);
					byteBuffer.limit(byteBuffer.capacity());
					channel.read(byteBuffer);
					
					System.out.println("before "+ printBuffer(byteBuffer));
					byteBuffer.flip();
					StringBuilder sb = new StringBuilder();
					
					while(byteBuffer.hasRemaining()){
						sb.append((char)byteBuffer.get());
					}
					
					System.out.println(sb.toString());
					System.out.println("after "+ printBuffer(byteBuffer));
					byteBuffer.limit(byteBuffer.capacity());
					
				}
			}
		}
	}
	private String printBuffer(ByteBuffer byteBuffer){
		String str = "position"+byteBuffer.position()+",limit:"+byteBuffer.limit();
		return str;
	}
}
