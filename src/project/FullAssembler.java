package project;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class FullAssembler implements Assembler{
	@Override
	public int assemble(String inputFileName, String outputFileName, StringBuilder error) {
		Scanner sD = new Scanner(new File(inputFileName));
		Scanner sC = new Scanner(new File(inputFileName));
		ArrayList<String> dataList = new ArrayList<String>();
		while(sD.hasNext()) {
			dataList.add(sD.next());
		}
		ArrayList<String> codeList = new ArrayList<String>();
		while(sC.hasNext()) {
			codeList.add(sC.next());
		}
	}
}
