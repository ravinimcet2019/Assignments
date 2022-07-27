package com.josh.java.training.oops.question3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RectangleTest {
	private Rectangle rectangle;

	@BeforeEach
	public void initialize() throws Exception {
		rectangle =new Rectangle();
	}
	
	@Test
	public void shouldReturnAreaOfRectangle() {
		rectangle.setLength(5.0);
		rectangle.setLength(6.0);
		double expected= rectangle.getLength() * rectangle.getWidth();
		double actual= rectangle.getArea();
		assertTrue(expected-actual == 0);
	}
	
	@Test
	public void shouldReturnPerimeterOfRectangle() {
		rectangle.setLength(5.0);
		rectangle.setLength(6.0);
		double expected= 2 *(rectangle.getLength() + rectangle.getWidth());
		double actual= rectangle.getPerimeter();
		assertTrue(expected-actual == 0);
	}
	@AfterEach
	public void tearDown() throws Exception {
	}



}
