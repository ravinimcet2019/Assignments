package com.josh.java.training.collection.comparator;

import java.util.Comparator;

public class SortByName implements Comparator {

	public int compare(Object obj1, Object obj2) {
		Employee I1=(Employee)obj1;
		Employee I2=(Employee)obj2;
	    
		return (I1.getName().toUpperCase().compareTo(I2.getName().toUpperCase()));
    }
}
