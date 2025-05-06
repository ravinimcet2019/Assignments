package com.josh.java.training.assessment.question1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class LinkedList_ {

	public static int getLengthOfList(List<Integer> integerList) {
		int count=0;
		ListIterator<Integer> list_Iter = integerList.listIterator();
		while(list_Iter.hasNext()){
			 int element=list_Iter.next();
			 count++;
	     }
	    return count;
	}
	
	public static int getSumOfList(List<Integer> integerList) {
		int sum=0;
		ListIterator<Integer> list_Iter = integerList.listIterator();
		while(list_Iter.hasNext()){
			 int element=list_Iter.next();
			 sum=sum+element;
	     }
	    return sum;
	}
	public static List<Integer> removeLastElementAndPrintEvenElementsOfList(List<Integer> integerList) {
		List<Integer> modifiedList=new LinkedList<>();
		integerList.remove(5);
		integerList.forEach(s->{
        	if(s%2==0) {
        		modifiedList.add(s);
        	}
        });
		return modifiedList;
	}
	public static void main(String[] args) {
		int count=0,sum=0;;
		List<Integer> linkedList=new LinkedList<>();
		linkedList.add(1);
		linkedList.add(4);
		linkedList.add(8);
		linkedList.add(10);
		linkedList.add(19);
		linkedList.add(20);
		
        System.out.println("The length of LinkedList is:"+getLengthOfList(linkedList));
        System.out.println("The sum of LinkedList elements is:"+getSumOfList(linkedList));
        System.out.println("Even elements After removing last node:"+removeLastElementAndPrintEvenElementsOfList(linkedList));
        
	 }     
}
