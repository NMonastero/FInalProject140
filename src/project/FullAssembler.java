package project;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FullAssembler implements Assembler{
	@Override
	public int assemble(String inputFileName, String outputFileName, StringBuilder error) {
		int lineNumber = 0;
		
		try(Scanner s1 = new Scanner(new File(inputFileName))){
			int dataCount = 0;
			ArrayList<String> dataList = new ArrayList<String>();
			ArrayList<String> codeList = new ArrayList<String>();
			while(s1.hasNext()) {
				if(s1.next().trim().length() ==0) {
					throw new InputMismatchException("Blank line detected");
				}
				if(s1.next().trim().toUpperCase().equals("DATA")) {
					if(!s1.next().trim().equals("DATA")) {
						throw new InputMismatchException("\"DATA\" seperator line is not capital");
					}
					while(s1.hasNext()) {
						dataList.add(s1.next());
						lineNumber++;
						if(s1.next().trim().toUpperCase().equals("DATA")) {
							throw new InputMismatchException("Multiple DATA seperator lines");
						}
					}
				}
				else{
					codeList.add(s1.next());
					lineNumber++;
				}
			}
		} catch(Exception e) {
			
		}
		
		
		try(Scanner s2 = new Scanner(new File(inputFileName))){
			int dataCount = 0;
			ArrayList<String> dataList = new ArrayList<String>();
			ArrayList<String> codeList = new ArrayList<String>();
			while(s2.hasNext()) {
				
				if(s2.next().trim().toUpperCase().equals("DATA")) {
					while(s2.hasNext()) {
						dataList.add(s2.next());
						lineNumber++;
					}
				}
				else{
					codeList.add(s2.next());
					lineNumber++;
				}
			}
		} catch(Exception e) {
			
		}
		
	}
}
