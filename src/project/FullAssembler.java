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
				if(s1.next().trim().length() == 0) {
					boolean b = false;
					int hold = 0;
					if(b == true) {
						throw new InputMismatchException("Blank line detected on line " + hold);
					}
					b = true;
					hold = lineNumber;
				}
				if(s1.next().trim().toUpperCase().equals("DATA")) {
					if(!s1.next().trim().equals("DATA")) {
						throw new InputMismatchException("\"DATA\" seperator line is not capital");
					}
					while(s1.hasNext()) {
						if(s1.next().trim().length() == 0) {
							boolean b = false;
							int hold = 0;
							if(b == true) {
								throw new InputMismatchException("Blank line detected on line " + hold);
							}
							b = true;
							hold = lineNumber;
						}
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
			boolean d = false;
			while(s2.hasNext() && d == false) {
				if(s2.next().charAt(0) == ' ' || s2.next().charAt(0) == '\t') {
					throw new InputMismatchException("There can be no blank space at the begining of lines (Error on line: " + lineNumber+1 + ")");
				}

				String[] parts = s2.next().trim().split("\\s+");
				if(!InstrMap.toCode.keySet().contains(parts[0])) {
					throw new InputMismatchException("Key does not contain input at line: " + lineNumber+1);
				}

				if(parts.length == 1) {

				}
				else if(parts.length == 2) {

					int arg = Integer.parseInt(parts[1],16);
					throw new NumberFormatException("\nError on line " + (lineNumber+1) + 
							": argument is not a hex number");
				}
				else {
					throw new InputMismatchException("Mnemonic on line " + lineNumber+1 + " has an incorrect number of arguments");
				}

				codeList.add(s2.next());
				lineNumber++;

				if(s2.next().trim().toUpperCase().equals("DATA")) {
					d = true;
				}
			}

			while(s2.hasNext() && d == true) {
				if(s2.next().charAt(0) == ' ' || s2.next().charAt(0) == '\t') {
					throw new InputMismatchException("There can be no blank space at the begining of lines (Error on line: " + lineNumber+1 + ")");
				}

				String[] parts = s2.next().trim().split("\\s+");
				if(!InstrMap.toCode.keySet().contains(parts[0])) {
					throw new InputMismatchException("Key does not contain input on line: " + lineNumber+1);
				}
				
				if(!s2.next().trim().toUpperCase().equals(s2.next().trim())) {
					throw new InputMismatchException("Data is not in all caps on line: "+ lineNumber+1);
				}

				//This part (that deals with parts) is what 7) is talking about
				if(parts.length == 1) {

				}
				else if(parts.length == 2) {

					int arg = Integer.parseInt(parts[1],16);
					throw new NumberFormatException("\nError on line " + (lineNumber+1) + 
							": argument is not a hex number");
				}
				else {
					throw new InputMismatchException("Mnemonic on line " + lineNumber + " has an incorrect number of arguments");
				}

				//parts must have a length of 2 (7)

				dataList.add(s2.next());
				lineNumber++;
			}
		} catch(Exception e) {

		}

	}
}
