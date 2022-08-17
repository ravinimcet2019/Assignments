package com.josh.java.training.collection.comparator;

public class Employee {

	private String name;
	private Double salary;
	private String department;
	
	
	public Employee(String name, double salary, String department) {
		super();
		this.name = name;
		this.salary = salary;
		this.department = department;
	}
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", salary=" + salary + ", department=" + department + "]";
	}
	
	
}
