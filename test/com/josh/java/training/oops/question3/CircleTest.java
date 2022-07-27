package com.josh.java.training.oops.question3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CircleTest {
	private Circle circle;

	@BeforeEach
	public void initialize() throws Exception {
		circle =new Circle();
	}

	@Test
	public void shouldReturnAreaOfCircle() {
		circle.setRadius(5.0);
		double expected = (Math.PI * Math.pow(circle.getRadius(), 2));
		double actual = circle.getArea();
		assertTrue(expected-actual == 0);
	}
	
	@Test
	public void shouldReturnPerimeterOfCircle() {
		circle.setRadius(7.0);
		double expected=2 * Math.PI * circle.getRadius();
		double actual=circle.getPerimeter();
		assertTrue(expected-actual == 0);
	}
	@AfterEach
	public void tearDown() throws Exception {
	}

}
