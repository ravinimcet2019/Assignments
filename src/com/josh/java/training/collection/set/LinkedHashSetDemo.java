package com.josh.java.training.collection.set;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class LinkedHashSetDemo {

	public static void main(String[] args) {
		LinkedHashSet<String> map = new LinkedHashSet<>();
		  
        map.add("vishal");
        map.add("sachin");
        map.add("vaibhav");
  

        System.out.println("Size of map is: "
                           + map.size());
  
        // Printing elements in object of Map
        System.out.println(map);
}
}