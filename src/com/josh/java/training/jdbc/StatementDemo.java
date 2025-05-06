package com.josh.java.training.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class StatementDemo {

	public static void main(String args[]) {
	      Connection connection = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/check","postgres","2820");
	         if(connection!=null) 
	         {
	        	 System.out.println("Connection started.");
	        	 Statement statement=connection.createStatement();
	        	 ResultSet results=statement.executeQuery("Select * from person");
	        	 System.out.println("Person table data:");
	        	 while(results.next()) {
	        		 System.out.println(results.getInt(1)+","+results.getString(2)+","+results.getString(3));
	        	 }
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	   }
}
