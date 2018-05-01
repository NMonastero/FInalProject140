package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Loader {
	public static String load(MachineModel model, File file, int codeOffset, int memoryOffset) {
		int codeSize = 0;

		if(model == null || file == null) {
			return null;
		}

		try(Scanner input = new Scanner(file)){
			boolean incode = true;
			while(input.hasNextLine()) {
				String line1 = input.nextLine();
				String line2 = input.nextLine();
				Scanner parser = new Scanner(line1 + " " + line2);
				if(parser.nextInt() == -1 && incode == true) {
					incode = false;
				}
				else if(incode == true){
					model.setCode(codeOffset+codeSize, parser.nextInt()-1, parser.nextInt());
					codeSize++;
				}
				else {
					model.setData(parser.nextInt() + memoryOffset, parser.nextInt()); //The address might not be correct here
					parser.close();
				}
			}
		}

		catch(ArrayIndexOutOfBoundsException e) {
			return "Array Index"+e.getMessage();
		}

		catch(NoSuchElementException e) {
			return "From Scanner: NoSuchElementExceptions";
		}

		catch(FileNotFoundException e) {
			return "File "+file.getName()+" Not Found";
		}

		return ""+codeSize;

	}
	
	public static void main(String[] args) {
		MachineModel model = new MachineModel();
		String s = Loader.load(model, new File("factorial8.pexe"),100,200);
		for(int i = 100; i < 100+Integer.parseInt(s); i++) {
			System.out.println(model.getOp(i));			
			System.out.println(model.getArg(i));			
		}
		for (int i = 200; i < 203; i++)
		System.out.println(i + " " + model.getData(i));
	}
}
