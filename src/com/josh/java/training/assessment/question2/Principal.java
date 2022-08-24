package com.josh.java.training.assessment.question2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Principal extends Person {
	 Connection connection = null;
	 PreparedStatement preparedStatemet=null;
	 
	public void getData() throws Exception {
    	Statement statement=connection.createStatement();
    	ResultSet results=statement.executeQuery("Select * from persons");
    	System.out.println("Persons table data:");
    	while(results.next()) {
    		System.out.println(results.getInt(1)+","+results.getString(2)+","+results.getString(3));
    	}
    }
    public int insertData(int id,String name,int age,String address) throws Exception {
    	preparedStatemet=connection.prepareStatement("Insert into persons values(?,?,?,?,?)");
 		preparedStatemet.setInt(1, id);
 		preparedStatemet.setString(2, name);
 		preparedStatemet.setInt(3, age);
 		preparedStatemet.setString(4, address);
 		preparedStatemet.setString(5, "Principal");
 		int answer=preparedStatemet.executeUpdate();
 		return answer;
    }
    
    public int updateData(int id,String name,int age,String address,String post) throws Exception {
    	preparedStatemet=connection.prepareStatement("Update persons set name=?, age=?, address=? where id=?");
 		preparedStatemet.setInt(4, id);
 		preparedStatemet.setString(1, name);
 		preparedStatemet.setInt(2, age);
 		preparedStatemet.setString(3, address);
 		int answer=preparedStatemet.executeUpdate();
 		return answer;
    }
    public int deleteData(int id) throws Exception{
    	preparedStatemet=connection.prepareStatement("Delete from persons where id=?");
 		preparedStatemet.setInt(1, id);
 		int answer=preparedStatemet.executeUpdate();
 		return answer;
    }
}
