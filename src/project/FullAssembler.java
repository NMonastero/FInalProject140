package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FullAssembler implements Assembler{
	@Override
	public int assemble(String inputFileName, String outputFileName, StringBuilder error) {
		if(error==null) {
			throw new IllegalArgumentException("Coding error: the error buffer is null");
		}
		int retVal = 0;
		try(Scanner s1 = new Scanner(new File(inputFileName))){
			int lineNumber = 0;
			ArrayList<String> dataList = new ArrayList<String>();
			ArrayList<String> codeList = new ArrayList<String>();
			boolean incode = true;
			boolean D2 = false;
			boolean b = false;
			int hold = 0;
			while(s1.hasNext()) {
				lineNumber++;
				String line = s1.nextLine();
				String[] parts = line.trim().split("\\s+");
				System.out.println("line " + lineNumber + ": \t" + line);

				if(b && line.trim().length() != 0) {
					error.append("\nIllegal blank line in the source file: " + hold);
					retVal = hold;
					b = false;
				}
				else if(line.trim().length() == 0) {
					if(!b) {
						b = true;
						hold = lineNumber;
					}
				}

				if(line.trim().toUpperCase().equals("DATA")) { //checks if s1.next == DATA
					if(!line.trim().equals("DATA")) { //checks if DATA is formated correctly
						error.append("\nLine does not have DATA in upper case");
						retVal = lineNumber;
					}
					if(incode) {
						incode = false;
					}
					else {
						D2 = true;
						error.append("\nMultiple DATA seperator lines");
						retVal = lineNumber;
					}
				}
				
//				else if(!D2) {
//					if(parts.length > 1) {
//						error.append("\nNon numeric memmory address on line: " + lineNumber);
//						retVal = lineNumber;
//					}
//				}
				if(!incode) {
					dataList.add(line);
				}
				else{
					codeList.add(line);
				}

			}
		}  catch (FileNotFoundException e) {
			error.append("\nError: Unable to write the assembled program to the output file");
			return -1;
		} catch (IOException e) {
			error.append("\nUnexplained IO Exception");
			return -1;
		}

		try(Scanner s2 = new Scanner(new File(inputFileName))){
			int lineNumber = 0;
			int offset = 0;
			ArrayList<String> dataList = new ArrayList<String>();
			ArrayList<String> codeList = new ArrayList<String>();
			boolean d = false;
			while(s2.hasNext()) {
				lineNumber++;
				String line = s2.nextLine();
				if(line.trim().length() == 0) {
					continue;
				}
				String[] parts = line.trim().split("\\s+");
				
				if(line.trim().length() != 0 && (line.charAt(0) == ' ' || line.charAt(0) == '\t')) {
					error.append("\nError on line " + (lineNumber) + ": starts with illegal white space");
					retVal = lineNumber;
				}

				if(line.trim().toUpperCase().equals("DATA")) {
					d = true;
					continue;
				}

				if(!d) {
					if(!parts[0].toUpperCase().equals(parts[0])) {
						error.append("\nError on line " + (lineNumber) + ": mnemonic must be upper case");
						retVal = lineNumber;
					}

					if(InstrMap.toCode.containsKey(parts[0].toUpperCase())) {
						if(!InstrMap.toCode.containsKey(parts[0])){
							error.append("\nError on line " + (lineNumber) + ": this mnemonic has an incorrect argument");
							retVal = lineNumber+1;
						}
					}
					else {
						error.append("\nError on line " + (lineNumber) + ": illegal mnemonic");
						retVal = lineNumber+1;
					}

					if(noArgument.contains(parts[0].toUpperCase())) {
						if(parts.length > 1) {
							error.append("\nError on line " + (lineNumber) + ": mnemonic cannot take an argument");
							retVal = lineNumber+1;
						}
					}
					else if(parts.length < 2) {
						error.append("\nError on line " + (lineNumber) + ": mnemonic is missing an argument");
						retVal = lineNumber+1;
					}

					else if(parts.length > 2) {
						error.append("\nError on line " + (lineNumber) + ": mnemonic has too many arguments");
						retVal = lineNumber+1;
					}

					else if(parts.length == 2) {
						try {
								int arg = Integer.parseInt(parts[1],16);
						}catch(NumberFormatException e) {
							error.append("\nError on line " + (lineNumber) + 
									": argument is not a hex number");
							retVal = lineNumber;
						}
					}

					else {
						error.append("\nError on line " + (lineNumber+1) + ": illegal mnemonic");
						retVal = lineNumber+1;
					}
				}

				if(d) {
					if(parts.length == 2) {
						try {
								int arg = Integer.parseInt(parts[1],16);
						}catch(NumberFormatException e) {
							error.append("\nError on line " + (lineNumber) + 
									": argument is not a hex number");
							retVal = lineNumber;
						}
					}
					else {
						error.append("\nError on line " + (lineNumber) + 
								": line needs two numbers");
						retVal = lineNumber;
					}
						
					
					try {
						if(parts.length >= 1) {
							int address = Integer.parseInt(parts[0],16);
						}
					}catch(NumberFormatException e){
						error.append("\nError on line " + (lineNumber) + 
								": data has non-numeric memory address");
						retVal = offset + lineNumber;
					}
					try {
						if(parts.length > 1) {
							int value = Integer.parseInt(parts[1],16);
						}
					}catch(NumberFormatException e){
						error.append("\nError on line " + (lineNumber) + 
								": data has non-numeric memory value");
						retVal = offset + lineNumber;
					}
				}

				if(!d) {
					codeList.add(line);
				}
				else {
					dataList.add(line);
				}
			}

		} catch (FileNotFoundException e) {
			error.append("\nError: Unable to write the assembled program to the output file");
			return -1;
		} catch (IOException e) {
			error.append("\nUnexplained IO Exception");
			return -1;
		}
		if(error.length() == 0) {
			SimpleAssembler s = new SimpleAssembler();
			s.assemble(inputFileName, outputFileName, error);
		}
		else System.out.println(error.toString());
		return retVal;
	}
	public static void main(String[] args) {
		StringBuilder error = new StringBuilder();
		System.out.println("Enter the name of the file without extension: ");
		try (Scanner keyboard = new Scanner(System.in)) { 
			String filename = keyboard.nextLine();
			int i = new FullAssembler().assemble(filename + ".pasm", 
					filename + ".pexe", error);
			System.out.println("result = " + i);
		}
	}
}
