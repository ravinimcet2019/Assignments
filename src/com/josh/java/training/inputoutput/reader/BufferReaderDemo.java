package com.josh.java.training.inputoutput.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BufferReaderDemo {

	public static void main(String[] args) {
		FileWriter fileWriter=null;
		try {
			FileReader fileReader = new FileReader("D:\\New folder\\FileData.txt");
		    BufferedReader buffer=new BufferedReader(fileReader);
		    String data;
		    int count=0;
		    while(buffer.readLine()!=null) {
		    	data=buffer.readLine();
		    	System.out.println(data);
		    	count= count + data.length();
		    	
		    }
		    System.out.println("\n Total Characters read:"+count);
		}
		catch(Exception ex) {
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
