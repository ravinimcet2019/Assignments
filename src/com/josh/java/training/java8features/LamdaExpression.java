package com.josh.java.training.java8features;

import java.util.ArrayList;
import java.util.List;

public class LamdaExpression {

	public static void main(String[] args) {

		List<String> list = new ArrayList<String>();
		list.add("Ravi");
		list.add("Rathore");
		list.add("rThor");
		list.forEach((names) -> System.out.println(names));
	}
}
