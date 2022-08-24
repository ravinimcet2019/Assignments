package com.josh.java.training.assessment.question1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.josh.java.training.oops.question3.Circle;

class LinkedList_Test {

	private Circle circle;

	@BeforeEach
	public void initialize() throws Exception {
		circle =new Circle();
	}

	@Test
	public void shouldReturnLengthOfList() {
		List<Integer> list=List.of(1,4,8,10,19,20);
		assertEquals(6, LinkedList_.getLengthOfList(list));
	}
	
	@Test
	public void shouldReturnSumOfList() {
		List<Integer> list=List.of(1,4,8,10,19,20);
		assertEquals(62, LinkedList_.getSumOfList(list));
	}
	@Test
	public void shouldReturnEvenElementsList() {
		List<Integer> list=List.of(1,4,8,10,19,20);
		List<Integer> expected=List.of(4,8,10);
		assertEquals(expected, LinkedList_.removeLastElementAndPrintEvenElementsOfList(list));
	}
	@AfterEach
	public void tearDown() throws Exception {
	}
}
