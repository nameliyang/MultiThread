package com.ly.test;

import java.util.ArrayList;
import java.util.Iterator;

public class Test {
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		
		Iterator<String> iterator = list.iterator();
		
		while(iterator.hasNext()){
			System.out.println(iterator.next());
			iterator.remove();
		}
	}
}
