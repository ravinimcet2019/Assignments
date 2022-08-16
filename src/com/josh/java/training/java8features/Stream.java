package com.josh.java.training.java8features;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Stream {

	public static void main(String[] args) {
		List<Integer> list1 = Arrays.asList(2, 8, 9, 5, 6, 7);
		/*
		 * Using .filter() to segregate the data according to specific conditions. Using
		 * .collect() to collect the data
		 */
		List<Integer> evenOnly = list1.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
		System.out.println("The Even Numbers in the list are: " + evenOnly);

		/*
		 * Using parallelSort() to achieve faster sorting of array elements.
		 */
		int array[] = { 5, 6, 9, 7, 2, 3, 4, 1 };
		Arrays.parallelSort(array);
		Arrays.stream(array).forEach(x -> System.out.print(x + " "));

		/*
		 * Using .map() to perform operations based on lambda functions.
		 */
		List<Integer> square = list1.stream().map((x) -> x * x).collect(Collectors.toList());
		System.out.println("\nThe Square of the numbers are: " + square);

		List<String> employees = Arrays.asList("Ravi", "Rathore", "rThor");

		/*
		 * Using .startsWith() to pick out a specific String for the given condition.
		 */
		List<String> name = employees.stream().filter(n1 -> n1.startsWith("G")).collect(Collectors.toList());
		System.out.println("Specified search result: " + name);

		/*
		 * Using .sorted() to sort the elements in the list.
		 */
		List<String> sorted = employees.stream().sorted().collect(Collectors.toList());
		System.out.println("Sorted names: " + sorted);

		/*
		 * Using parallel() to allow the filter() to process multiple data
		 * simultaneously. forEach() prints the data randomly whereas forEachOrdered()
		 * prints it as received.
		 */
		System.out.println("---------------UnOrdered Data-------------------");
		list1.stream().filter(x -> x % 2 == 0).parallel().forEach(System.out::println);
		System.out.println("---------------Ordered Data----------------");
		list1.stream().filter(x -> x % 2 == 0).parallel().forEachOrdered(System.out::println);

		/*
		 * Grouping the elements using .groupingBy()
		 */
		List<String> list2 = Arrays.asList("Sales", "Eng", "Production", "Sales", "Production", "Eng", "Management",
				"Sales", "Eng", "Production");
		System.out.println("--------The Grouped Elements--------");
		System.out.println(list2.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

	}
}
