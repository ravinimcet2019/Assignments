package com.josh.java.training.oops.question3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CylinderTest {

	private Cylinder cylinder;

	@BeforeEach
	public void initialize() throws Exception {
		cylinder =new Cylinder();
	}
	
	@Test
	public void shouldReturnVolumeOfCylinder() {
		cylinder.setRadius(5.0);
		cylinder.setHeight(7.0);
		double expected = Math.PI * Math.pow(cylinder.getRadius(), 2) * cylinder.getHeight();
		double actual = cylinder.getVolume() ;
		assertTrue(expected-actual == 0);
	}
	@AfterEach
	public void tearDown() throws Exception {
	}

}
