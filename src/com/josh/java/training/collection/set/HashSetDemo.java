package com.josh.java.training.collection.set;

import java.util.HashSet;
import java.util.TreeSet;

public class HashSetDemo {

	 public static void main(String[] args) {

	        // HashSet does not retain order.
	        //HashSet<String> set1 = new HashSet<String>();
	        // TreeSet sorts in natural order
	        TreeSet<String> set1 = new TreeSet<String>();

	        if (set1.isEmpty()) {
	            System.out.println("Set is empty at start");
	        }

	        set1.add("dog");
	        set1.add("cat");
	        set1.add("mouse");
	        set1.add("snake");
	        set1.add("bear");

	        if (set1.isEmpty()) {
	        System.out.println("Set is empty after adding (no!)");
	        }

	        // Adding duplicate items does nothing.
	        set1.add("mouse");
	        System.out.println(set1);

	        for (String element : set1) {
	        System.out.println(element);
	        }

	        // Does set contains a given item?
	        if (set1.contains("sahil")) {
	        System.out.println("Contains a");
	        }

	        if (set1.contains("cat")) {
	        System.out.println("Contains cat");
	        }

	        // set2 contains some common elements with set1, and some new

	        TreeSet<String> set2 = new TreeSet<String>();

	        set2.add("dog");
	        set2.add("cat");
	        set2.add("giraffe");
	        set2.add("monkey");
	        set2.add("ant");

	        HashSet<String> common = new HashSet<String>(set1);

	        common.retainAll(set2);

	        System.out.println(common);

	        HashSet<String> difference = new HashSet<String>(set2);

	        difference.removeAll(set1);
	        System.out.println(difference);
	    }
}
