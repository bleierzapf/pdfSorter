package pdfSorter;

import java.io.*;

public class InputHelper {
	
	public static String getInput(String prompt){	
		BufferedReader stdin = new BufferedReader(
				new InputStreamReader(System.in));
		System.out.print(prompt);
		System.out.flush();
		
		try {
			return stdin.readLine();
		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
	}
	
	public static String getPDF(String prompt) {
		String input = getInput(prompt);
		return input;
	}
	
	public static String getValidSort(String prompt) {
//		boolean cont = false;
		String input = null;
//		do{
		input = getInput(prompt);
//		if((input.equals("A")) || input.equals("B") || input.equals("C"));
//			cont = true;
//		}while (cont);
		return input;
	}
	
	public static int getInt(String prompt) {
		String input = getInput(prompt);
		return Integer.parseInt(input);
	}
	
	public static boolean getBoolean(String prompt) {
		String input = getInput(prompt);
		if(input.equalsIgnoreCase("Yes") || input.equalsIgnoreCase("Y")){
			return true;
		} else return false;		
	}
	
}
