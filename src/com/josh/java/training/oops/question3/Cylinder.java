package com.josh.java.training.oops.question3;

public class Cylinder extends Circle {

	 private double height;
	
	 public Cylinder() {
		 super();
		 this.height=1.0;
	 }
	 public Cylinder(double radius) {
		 super(radius);
		 this.height=1.0;
	 }
	 public Cylinder(double radius,double height) {
		 super(radius);
		 this.height=height;
	 }
	 public Cylinder(double radius,double height,String color,boolean filled) {
		 super(color,filled,radius);
		 this.height=height;
	 }
	 public double getHeight() {
		 return this.height;
	 }
	 public void setHeight(double height) {
		 this.height=height;
	 }
	 /*
     * to calculate Volume of Cylinder
	 */
	 public double getVolume() {
		 return(Math.PI * Math.pow(super.getRadius(), 2) * this.height);
	 }
}
