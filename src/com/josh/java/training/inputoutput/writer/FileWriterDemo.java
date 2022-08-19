package com.josh.java.training.inputoutput.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileWriterDemo {

	public static void main(String[] args) {
		FileWriter fileWriter=null;
		try {
			fileWriter=new FileWriter("D:\\New folder\\FileData.txt");
			Scanner input=new Scanner(System.in);
			System.out.println("Enter your name:");
			String name=input.nextLine();
			System.out.println("Enter your address:");
			String address=input.nextLine();
			fileWriter.write(name);
			fileWriter.write("\r\n");
			fileWriter.write(address);
		}
		catch(IOException ex) {
			System.out.println("Error while writing.");
		}
		finally {
			if(fileWriter!=null) {
				try {
					fileWriter.close();
					System.out.println("File saved Succeddfully");
				}
				catch(Exception ex) {
					System.out.println("Error while closing.");
				}
			}
		}
	}
}
