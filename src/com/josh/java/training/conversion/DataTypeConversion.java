package com.josh.java.training.conversion;

public class DataTypeConversion {

	public static void main(String[] args) {
		// Converting integer to string
		// using valueOf() method
		int integer = 3456;
		String str1 = String.valueOf(integer);
		System.out.println("Integer to String : " + str1);

		// Converting integer to string
		// using toString() method
		String str2 = Integer.toString(integer);
		System.out.println("Integer to String : " + str2);

		// Creating an object of Integer class
		String str3 = new Integer(integer).toString();
		System.out.println("Integer to String : " + str3);

		// Converting string to long
		String string = "876543";
		long longvar = Long.valueOf(string);
		System.out.println("String to Long : " + longvar);

		// Converting Long to String
		String longToString = String.valueOf(longvar);
		System.out.println("Long to String : " + longToString);

		// Converting String to Double
		Double stringTodouble = Double.valueOf("341.25D");
		System.out.println("String to Double : " + stringTodouble);

		// Converting double to string
		String doubleTostring = String.valueOf(969.99D);
		System.out.println("Double to String : " + doubleTostring);

		// Converting double to integer
		int doubleTointeger = (int) 74.95D;
		System.out.println("Double to Integer : " + doubleTointeger);

		// Converting Integer to Double
		double intTodouble = doubleTointeger;
		System.out.println("Integer to Double : " + intTodouble);

		// Converting Integer to Long
		long intTolong = 1245;
		System.out.println("Integer to Long : " + intTolong);

		// Converting Long to Integer
		long longvartwo = 966373045;
		int longTointeger = (int) longvartwo;
		System.out.println("Long to Integer : " + longTointeger);

		// Converting integer to character
		// it allows only 4 digit integer value
		char intTochar = 122;
		System.out.println("Integer to Character : " + intTochar);

		// Converting character to int
		int charTointeger = intTochar;
		System.out.println("Charater to Ineger : " + charTointeger);

		// Converting float to string
		float floatvar = 95.99f;
		String floatTostring = String.valueOf(floatvar);
		System.out.println("Float to String : " + floatTostring);

		// Converting string to boolean
		String stringvarone = "true";
		String stringvartwo = "FALSE";
		String stringvarthree = "Something";
		boolean boolone = Boolean.parseBoolean(stringvarone);
		boolean booltwo = Boolean.parseBoolean(stringvartwo);
		boolean boolthree = Boolean.parseBoolean(stringvarthree);
		System.out.println("String to Boolean : " + boolone);
		System.out.println("String to Boolean : " + booltwo);
		System.out.println("String to Boolean : " + boolthree);

		// Converting boolean to string
		boolean boovar = true;
		String str = String.valueOf(boovar);
		System.out.println("Boolean to String : " + str);
	}
}
