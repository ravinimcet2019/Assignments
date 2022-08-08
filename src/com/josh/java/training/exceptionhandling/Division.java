package com.josh.java.training.exceptionhandling;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Division {

	/*
	 * Method for returning the result of Division of Two Numbers.
	 */
	public static int division(int firstNumber,int secondNumber) throws ArithmeticException {
		return firstNumber/secondNumber;
	}
	
	public static void main(String [] args) {
		Scanner scanner=new Scanner(System.in);
		try {
			System.out.println("Enter two values fron division.\nAnd 1st number must be greater than 2nd.");
			System.out.println("Enter 1st number:");
			int firstNumber=scanner.nextInt();
			if(firstNumber<0) {
				throw new InputWrongNumberException("ERROR!\nPlease, don't use negetive values!");
			}
			System.out.println("Enter 2nd number:");
			int secondNumber=scanner.nextInt();
			if(secondNumber<0) {
				throw new InputWrongNumberException("ERROR!\nPlease, don't use negetive values!");
			}
			if(firstNumber<secondNumber) {
				throw new InputWrongNumberException("ERROR!\n1st number can't be smaller than 2nd number!");
			}
			System.out.println("Division of a/b is: "+division(firstNumber,secondNumber));

		}
		catch(InputWrongNumberException nvx) {
			System.out.println(nvx.getMessage());
		}
		catch(InputMismatchException ime) {
			System.out.println("ERROR!\nUse only digits");
		}
		catch(ArithmeticException ae) {
			System.out.println(ae.getMessage());
		}
		finally {
			scanner.close();
			System.out.println("BYE..");
		}
	}
}
