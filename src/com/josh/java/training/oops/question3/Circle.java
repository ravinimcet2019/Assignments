package com.josh.java.training.oops.question3;

public class Circle extends Shape {

	private double radius;

	public Circle() {
		this.radius=1.0;
	}
	
	public Circle(double radius) {
		this.radius = radius;
	}
	
	public Circle(String color, boolean filled, double radius) {
		super(color, filled);
		this.radius = radius;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	/*
	 * to calculate area of Circle
	 */
	public double getArea() {
		return (Math.PI * Math.pow(this.radius, 2));
	}
	
	/*
	 * to calculate Perimeter of Circle
	 */
	public double getPerimeter() {
       	return (2 * Math.PI * this.radius);
	}
	
	@Override
	public String toString() {
		return "Circle[Shape [color=" + super.getColor() + ", filled=" + super.isFilled() + "]radius=" + radius + "]";
	}
}
