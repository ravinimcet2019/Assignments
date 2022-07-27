package com.josh.java.training.oops.question3;

public class Rectangle extends Shape {

	 private double width;
	 private double length;
	 
	public Rectangle() {
		super();
		this.length=1.0;
		this.width=1.0;
	}
	public Rectangle(double width, double length) {
		super();
		this.width = width;
		this.length = length;
	}
	public Rectangle(double width, double length,String color, boolean filled) {
		super(color,filled);
		this.width = width;
		this.length = length;
	}
    public double getWidth() {
		return this.width;
	}
    public void setWidth(double width) {
		this.width = width;
	}
    public double getLength() {
		return this.length;
	}
    public void setLength(double length) {
		this.length = length;
	}
    
    /*
	 * to calculate area of Rectangle
	 */
	public double getArea() {
		return (this.length * this.width);
	}
	/*
	 * to calculate Perimeter of Rectangle
	 */
	public double getPerimeter() {
		return (2 * (this.length + this.width));
	}
	@Override
	public String toString() {
		return "Rectangle[Shape [color=" + super.getColor() + ", filled=" + super.isFilled() + "]width=" + width + ", length=" + length + "]";
	}
}
