package pdfSorter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class ImportPDFFiles {

	public static String getPDFFile(String tempFile, String sourceDir) throws IOException {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("YYYY-MM-dd-hh");
		
		boolean addFile = false;
		String fileName = null;
		PDFMergerUtility pdfMerge = new PDFMergerUtility();
		
		do {
			try {
				fileName = InputHelper.getInput("Enter PDF File Name (no .pdf): ");
				pdfMerge.addSource(sourceDir + fileName + ".pdf");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			System.out.println("file added");
			
			addFile = InputHelper.getBoolean("Upload additional PDF file? (Y/N) ");
			
		} while (addFile);
		
		pdfMerge.setDestinationFileName(sourceDir + df.format(ldt) + "-Temp.pdf");
	    pdfMerge.mergeDocuments(null);
		
	    return tempFile = sourceDir + df.format(ldt) + "-Temp.pdf";
	}
}
