package com.josh.java.training.jdbc;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PreparedStatementTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void shouldOpenConnection() throws Exception {
		Connection connection=null;
	    connection=PreparedStatementDemo.openConnection();
		assertTrue(connection!=null);
	}
	
	@Test
	public void shouldInsertDataInDB() throws Exception {
	    int count=PreparedStatementDemo.insertData(11,"Ravi", "fagah");
	    assertTrue(count!=0);
	}
	
	@Test
	public void shouldUpdateDataInDB() throws Exception {
		int count=PreparedStatementDemo.updateData(10,"Ravi", "fagah");
	    assertTrue(count!=0);
	}
	
	@Test
	public void shouldDeleteDataInDB() throws Exception {
		int count=PreparedStatementDemo.deleteData(11);
	    assertTrue(count!=0);
	}

}
