package com.josh.java.training.exceptionhandling;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DivisionTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void shouldRetrunDivisionOfTwoNumber() {
		assertEquals(5, Division.division(10, 2));
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
}
