package com.josh.java.training.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.sql.Statement;
import java.util.Scanner;

public class PreparedStatementDemo {
	static Connection connection = null;
	static PreparedStatement preparedStatemet=null;
    public static Connection openConnection() throws Exception {
    	Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/check","postgres","2820");
        return connection;
    }
    public static void getData() throws Exception {
    	Statement statement=connection.createStatement();
    	ResultSet results=statement.executeQuery("Select * from person");
    	System.out.println("Person table data:");
    	while(results.next()) {
    		System.out.println(results.getInt(1)+","+results.getString(2)+","+results.getString(3));
    	}
    }
    public static int insertData(int id,String name,String city) throws Exception {
    	preparedStatemet=connection.prepareStatement("Insert into Person values(?,?,?)");
 		preparedStatemet.setInt(1, id);
 		preparedStatemet.setString(2, name);
 		preparedStatemet.setString(3, city);
 		int answer=preparedStatemet.executeUpdate();
 		return answer;
    }
    
    public static int updateData(int id,String name,String city) throws Exception {
    	preparedStatemet=connection.prepareStatement("Update Person set name=?,city=? where id=?");
 		preparedStatemet.setInt(3, id);
 		preparedStatemet.setString(1, name);
 		preparedStatemet.setString(2, city);
 		int answer=preparedStatemet.executeUpdate();
 		return answer;
    }
    public static int deleteData(int id) throws Exception{
    	preparedStatemet=connection.prepareStatement("Delete from Person where id=?");
 		preparedStatemet.setInt(1, id);
 		int answer=preparedStatemet.executeUpdate();
 		return answer;
    }
	public static void main(String args[]) {
	     
	      
	      try {
	         connection = openConnection();
	         if(connection!=null) 
	         {
	        	 System.out.println("What do you want to do:");
	        	 System.out.println("1.Show Data\n2.Add Data\n3.Update Data\n4.Delete Data");
	        	 Scanner scanner=new Scanner(System.in);
	        	 int choice=scanner.nextInt();
	        	 switch(choice) {
	        	 	case 1:
	        	 	{
	        	 		getData();
	    	        	break;
	        	 	}
	        	 	case 2:
	        	 	{
	        	 		System.out.println("Enter Id");
	        	 		int id=scanner.nextInt();
	        	 		System.out.println("Enter Name");
	        	 		String name=scanner.next();
	        	 		System.out.println("Enter Address");
	        	 		String city=scanner.next();
	        	 		int answer= insertData(id, name, city);
	        	 		if(answer!=0) {
	        	 			System.out.println("Record Inserted.");
	        	 		}
	        	 		break;
	        	 	}
	        	 	case 3:
	        	 	{
	        	 		System.out.println("Enter Id whose data you want to update");
	        	 		int id=scanner.nextInt();
	        	 		System.out.println("Enter Name");
	        	 		String name=scanner.next();
	        	 		System.out.println("Enter Address");
	        	 		String city=scanner.next();
	        	 		int answer=updateData(id, name, city);
	        	 		if(answer!=0) {
	        	 			System.out.println("Record Updated.");
	        	 		}
	        	 		break;
	        	 	}
	        	 	case 4:
	        	 	{
	        	 		System.out.println("Enter Id whose data you want to delete!");
	        	 		int id=scanner.nextInt();
	        	 		int answer=deleteData(id);
	        	 		if(answer!=0) {
	        	 			System.out.println("Record Deleted.");
	        	 		}
	        	 		break;
	        	 	}
	        	 	default:
	        	 		System.out.println("You entered wrong input!");
	        	 		break;
	        	 }
	        	
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	   }
}
