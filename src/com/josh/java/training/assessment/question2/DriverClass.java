package com.josh.java.training.assessment.question2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DriverClass {

	static Connection connection = null;
	static PreparedStatement preparedStatemet=null;
    public static Connection openConnection() throws Exception {
    	Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/check","postgres","2820");
        return connection;
    }
	public static void main(String args[]) {
	      Connection connection = null;
	      try {
		         connection = openConnection();
		         if(connection!=null) 
		         {
		        	 Scanner scanner=new Scanner(System.in);
		        	 System.out.println("Which one Data You want to ADD:");
		        	 System.out.println("1.Principle\n2.Teacher\n3.Student");
		        	 int input=scanner.nextInt();
		        	 switch(input) {
		        	 case 1:{
		        		 Person p=new Principal();
		        		 break;
		        	 }
		        	 case 2:{
		        		 Person p=new Teacher();
		        		 break;
		        	 }
		        	 case 3:{
		        		 Person p=new Student();
		        		 break;
		        	 }
		        	 }
		        	 Person p=new Principal();
		        	 System.out.println("What do you want to do:");
		        	 System.out.println("1.Show Data\n2.Add Data\n3.Update Data\n4.Delete Data");
		        	 
		        	 int choice=scanner.nextInt();
		        	 switch(choice) {
		        	 	case 1:
		        	 	{
		        	 		p.getData();
		    	        	break;
		        	 	}
		        	 	case 2:
		        	 	{
		        	 		System.out.println("Enter Id");
		        	 		int id=scanner.nextInt();
		        	 		p.setPersonId(id);
		        	 		System.out.println("Enter Name");
		        	 		String name=scanner.next();
		        	 		p.setPersonName(name);
		        	 		System.out.println("Enter Age");
		        	 		int age=scanner.nextInt();
		        	 		p.setPersonAge(age);
		        	 		System.out.println("Enter Address");
		        	 		String city=scanner.next();
		        	 		p.setPersonAddress(city);
		        	 		int answer= p.insertData(id,name,age,city);
		        	 		if(answer!=0) {
		        	 			System.out.println("Record Inserted.");
		        	 		}
		        	 		break;
		        	 	}
		        	 	case 3:
		        	 	{
		        	 		System.out.println("Enter Id whose data you want to update");
		        	 		int id=scanner.nextInt();
		        	 		p.setPersonId(id);
		        	 		System.out.println("Enter Name");
		        	 		String name=scanner.next();
		        	 		p.setPersonName(name);
		        	 		System.out.println("Enter Age");
		        	 		int age=scanner.nextInt();
		        	 		p.setPersonAge(age);
		        	 		System.out.println("Enter Address");
		        	 		String city=scanner.next();
		        	 		p.setPersonAddress(city);
		        	 		int answer=p.updateData(id, name,age, city);
		        	 		if(answer!=0) {
		        	 			System.out.println("Record Updated.");
		        	 		}
		        	 		break;
		        	 	}
		        	 	case 4:
		        	 	{
		        	 		System.out.println("Enter Id whose data you want to delete!");
		        	 		int id=scanner.nextInt();
		        	 		p.setPersonId(id);
		        	 		int answer=p.deleteData(id);
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
