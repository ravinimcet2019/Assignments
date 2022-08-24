package com.josh.java.training.collection.comparator;

import java.util.Comparator;
import java.util.TreeSet;

public class ComparatorDemo {

	public static void main(String[] args) {
		
		Employee emp1=new Employee("ravi",70000,"Java");
		Employee emp2=new Employee("Yoyo",60000,"Python");
		Employee emp3=new Employee("Surya",65000,"C++");
		Employee emp4=new Employee("Prabhas",70000,"C");
        
		TreeSet<Employee> treeSet=new TreeSet<>(new SortByName());
		
		treeSet.add(emp1);
		treeSet.add(emp2);
		treeSet.add(emp3);
		treeSet.add(emp4);
		
		treeSet.forEach((x)-> System.out.println(x) );
	}
}
