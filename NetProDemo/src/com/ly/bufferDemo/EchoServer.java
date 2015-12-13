package com.ly.bufferDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;


public class EchoServer {
	
	private Selector selector = null;
	private ServerSocketChannel serverSocketChannel = null;
	private int port = 8000;
	private Charset charset = Charset.forName("GBK");
	
	
	public EchoServer() throws IOException{
		selector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		System.out.println("服务器启动");
		
	}
	
	public void service() throws IOException {
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while(selector.select()>0){
			Set readyKeys = selector.selectedKeys();
				Iterator it = readyKeys.iterator();
				while(it.hasNext()){
					SelectionKey key  = null;
					try{
						key = (SelectionKey) it.next();
						it.remove();
						
						if(key.isAcceptable()){
							
							ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
							SocketChannel socketChannel = ssc.accept();
							System.out.println("接收到客户连接,来自"+socketChannel.socket().getInetAddress()
									+":"+socketChannel.socket().getPort());
							socketChannel.configureBlocking(false);
							ByteBuffer buffer = ByteBuffer.allocate(1024);
							socketChannel.register(selector, SelectionKey.OP_READ);
							
						}
						
						if(key.isReadable()){
							System.out.println("-----------------------reading ===================");
							receive(key);
						/*	ByteBuffer byteBuffer = ByteBuffer.allocate(10);
							SocketChannel channel = (SocketChannel) key.channel();
							channel.read(byteBuffer);
							byteBuffer.flip();
							
							StringBuilder sb  = new StringBuilder();
							
							while(byteBuffer.hasRemaining()){
								sb.append((char)byteBuffer.get());
							}
							
							System.out.println(sb.toString());
							byteBuffer.position(0);*/
						}
						
						if(key.isWritable()){
						//	System.out.println("wriintg");
							send(key);
						/*	SocketChannel channel = (SocketChannel) key.channel();
							//ByteBuffer buffer = (ByteBuffer) key.attachment();
							ByteBuffer buffer = ByteBuffer.allocate(10); 
							buffer.put((byte)'h');buffer.put((byte)'e');buffer.put((byte)'l');
							buffer.put((byte)'l');
							buffer.put((byte)'o');
							Thread.sleep(1000);
						//	buffer.flip();
*/						//	channel.write(buffer);
						//	channel.register(selector, SelectionKey.OP_READ);
						}
					}catch(Exception e){
						e.printStackTrace();
						try{
							if(key!=null){
								key.cancel();
								key.channel().close();
							}
						}catch(Exception ee){
							ee.printStackTrace();
						}
					}
				}

			}
		}
	
	public void send (SelectionKey key) throws IOException{
		
		ByteBuffer  buffer = (ByteBuffer) key.attachment();
		SocketChannel socketChannel = (SocketChannel) key.channel();
		buffer.flip();
		String data = decode(buffer);
		if(data.indexOf("\r\n")==-1){
			return ;
		}
		String outputData = data.substring(0,data.indexOf("\n")+1);
		System.out.println(outputData);
		ByteBuffer outputBuffer = encode("echo:"+outputData);
		while(outputBuffer.hasRemaining()){
			socketChannel.write(outputBuffer);
		}
		ByteBuffer temp = encode(outputData);
		buffer.position(temp.limit());
		buffer.compact();
		if(outputData.equals("bye\r\n")){
			key.cancel();
			socketChannel.close();
			System.out.println("关闭与客户端的连接");
		}
	}
	
	
	public void receive(SelectionKey key) throws IOException{
		
		ByteBuffer buffer = (ByteBuffer) key.attachment();
		SocketChannel socketChannel = (SocketChannel) key.channel();
		ByteBuffer readBuff = ByteBuffer.allocate(32);
		socketChannel.read(readBuff);
		readBuff.flip();
		
		buffer.limit(buffer.capacity());
		buffer.put(readBuff);
		
// 		socketChannel.register(selector, SelectionKey.OP_WRITE,buffer);
		
	}
	
	public ByteBuffer encode(String str){
		return charset.encode(str);
	}
	
	public String decode(ByteBuffer buffer){
		CharBuffer charBuffer = charset.decode(buffer);
		return charBuffer.toString();
	}
	
	public static void main(String[] args) throws IOException {
		EchoServer server = new EchoServer();
		server.service();
	}
}
