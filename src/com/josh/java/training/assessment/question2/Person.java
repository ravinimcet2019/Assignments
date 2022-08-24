package com.josh.java.training.assessment.question2;

public class Person {

	private int personId;
	private String personName;
	private int personAge;
	private String personAddress;
	
	public Person(int personId, String personName, int personAge, String personAddress) {
		super();
		this.personId = personId;
		this.personName = personName;
		this.personAge = personAge;
		this.personAddress = personAddress;
	}

	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public int getPersonAge() {
		return personAge;
	}

	public void setPersonAge(int personAge) {
		this.personAge = personAge;
	}

	public String getPersonAddress() {
		return personAddress;
	}

	public void setPersonAddress(String personAddress) {
		this.personAddress = personAddress;
	}
	
	
}
