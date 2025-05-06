package com.josh.java.training.conversion;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class StringConversion {

	public static void main(String[] args) throws IOException {
		int asciiVal = 78;
		String str = new Character((char) asciiVal).toString();
		System.out.println(str);

		// create a new writer
		StringWriter sw = new StringWriter();

		// write a string
		sw.write("Ravi Rathore");

		// print result by converting to string
		System.out.println("" + sw.toString());

		try {
			int i = 5 / 0;
			System.out.println(i);
		} catch (ArithmeticException e) {
			/*
			 * This block of code would convert the stacktrace to string by using
			 * Throwable.printStackTrace(PrintWriter pw) which sends the stacktrace to the
			 * writer that we can convert to string using tostring()
			 */
			StringWriter sw1 = new StringWriter();
			PrintWriter pw = new PrintWriter(sw1);
		
			String stacktraceString = sw1.toString();
			System.out.println("String is: " + stacktraceString);

			String str1 = "Rathore";

			// split string by no space
			String[] str1Split = str1.split("");

			// Now convert string into ArrayList
			ArrayList<String> str1List = new ArrayList<String>(Arrays.asList(str1Split));

			// Now print the ArrayList
			for (String s : str1List)
				System.out.println(s);

			// create a StringBuffer object
			// with a String pass as parameter
			StringBuffer str2 = new StringBuffer("I love Java");

			// print string
			System.out.println("String contains = " + str2.toString());

		}

		InputStreamReader isr = null;
		BufferedReader br = null;
		InputStream is = new ByteArrayInputStream("This is String Conversion".getBytes());
		StringBuilder sb = new StringBuilder();
		String content;
		try {
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			while ((content = br.readLine()) != null) {
				sb.append(content);
			}
		} catch (IOException ioe) {
			System.out.println("IO Exception occurred");
			ioe.printStackTrace();
		} finally {
			isr.close();
			br.close();
		}
		String mystring = sb.toString();
		System.out.println(mystring);
	}
}
