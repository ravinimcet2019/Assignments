package com.josh.java.training.collection.comparator;

import java.util.Comparator;

public class SortBySalary implements Comparator {

	public int compare(Object obj1, Object obj2) {
		Employee I1=(Employee)obj1;
		Employee I2=(Employee)obj2;
	
		return I2.getSalary().compareTo(I1.getSalary());
    }
}
