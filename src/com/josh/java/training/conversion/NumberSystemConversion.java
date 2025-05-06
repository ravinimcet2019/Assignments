package com.josh.java.training.conversion;

public class NumberSystemConversion {

	public static void main(String[] args) {
		// Binary to Decimal by passing base 2
		String binaryNumber = "1100";
		System.out.println("Binary to Decimal : " + Integer.parseInt(binaryNumber, 2));

		// Decimal to binary by passing base 2
		String binarystring = Integer.toBinaryString(Integer.parseInt(binaryNumber, 2));
		System.out.println("Decimal to Binary : " + binarystring);

		// Binary to Octal
		String ostr = Integer.toOctalString(Integer.parseInt(binaryNumber, 2));
		System.out.println("Binary to Octal : " + ostr);

		// Hex to Decimal by passing base 16
		String hexnum = "8A";
		int decimalnum = Integer.parseInt(hexnum, 16);
		System.out.println("Hex to Decimal : " + decimalnum);

		// Decimal to Octal
		String octalString = Integer.toOctalString(128);
		System.out.println("Decimal to Octal : " + octalString);

		// Octal to decimal using Integer.parseInt()
		int num = Integer.parseInt(octalString, 8);
		System.out.println("Octal to Decimal : " + num);
	}
}
