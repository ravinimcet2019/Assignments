package com.josh.java.training.collection.list;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListDemo {

	public static void main(String[] args) {

        LinkedList<String> linkedList= new LinkedList<>();

        //add
        linkedList.add("hi");
        linkedList.add("my");
        linkedList.add("name");
        linkedList.add("is");
        linkedList.add("ravi");
        System.out.println("Content of LinkedList: " +linkedList);

        //addFirst:
        linkedList.addFirst("GoodMorning");
        linkedList.addLast("TakeCare");
        System.out.println("LinkedList: " +linkedList);

        //get
        System.out.println("first value: "+linkedList.get(0));

        //set
        linkedList.set(1, "rathore");
        System.out.println(linkedList.get(1));

        //remove first element:
        linkedList.removeFirst();
        linkedList.removeLast();
        System.out.println("LinkedList: " +linkedList);
        //remove from  index
        linkedList.remove(2);
        System.out.println("LinkedList: " +linkedList);

        //for loop
        System.out.println("using for loop");
        for (int i = 0; i < linkedList.size(); i++) {
            System.out.println(linkedList.get(i));
        }
        //advanced for loop
        System.out.println("using advanced for loop");
        for(String str : linkedList){
            System.out.println(str);
        }

        //iterator
        System.out.println("using iterator");
        Iterator<String> it = linkedList.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
