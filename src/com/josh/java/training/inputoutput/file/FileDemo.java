package com.josh.java.training.inputoutput.file;

import java.io.File;

public class FileDemo {
	
	public static void main(String[] args) {
		File myFile=new File("D:\\New folder\\data.txt");
		if(myFile.exists()) {
			System.out.println(myFile.getName()+" is Present.");
		}
		else {
			System.out.println("File is not Present");
		}
		if(myFile.isFile()) {
			System.out.println(myFile.getName()+" is a file.");
		}
		else {
			System.out.println(myFile.getName()+" is a directory.");
		}
		if(myFile.isHidden()) {
			System.out.println(myFile.getName()+" is hidden");
		}
		else {
			System.out.println(myFile.getName()+" is not hidden.");
		}
	}
}
