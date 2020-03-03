package pdfSorter;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;

import pdfSorter.beans.Criteria;
import pdfSorter.tables.CriteriaManager;
import sorterGUI.SortElements;

public class SortPDFFiles {
	
	public static void sortFiles(Criteria dir){
		try(
				PDDocument inputPDF = PDDocument.load(new File(dir.getTempFileName()));
				) {
			PDPageTree pageList = inputPDF.getDocumentCatalog().getPages();
			extractPDFData(pageList, inputPDF, dir);			

		} catch (Exception e) {
			System.err.println(e);		
		}
	}
	
	public static void extractPDFData(PDPageTree pageList, PDDocument inputPDF, Criteria dir){
		@SuppressWarnings("resource")
		PDDocument newDoc = new PDDocument();
		ArrayList<String> resultList = new ArrayList<String>();
		
		try {
			PDFTextStripper textStripper = new PDFTextStripper();
			String pages = null;
			String binPattern = null;
			String orderPattern = null;
			boolean found = false;
						
			DefaultTableModel sortTable = CriteriaManager.getJTableData();	
						
			SortElements[] sortArr = new SortElements[pageList.getCount()];
			
			if(dir.isSortByBin()) {
				binPattern = getRegexPattern(dir.getBinNumFormat());
			}
			
			orderPattern = getRegexPattern(dir.getOrderNumFormat());
			
			Pattern locNum = Pattern.compile(binPattern);
			Pattern orderNum = Pattern.compile(orderPattern);
			
			resultList.add("Page Count @ Start: " + pageList.getCount());
			
			for(int i = 1; i <= pageList.getCount(); i++){	
				textStripper.setStartPage(i);
				textStripper.setEndPage(i);
				PDPage returnPage = null;
				pages = textStripper.getText(inputPDF); // pages contains text (print out pages to see text)
				
				int arrIndex = i-1;
				
				for (int j = 0; j < sortTable.getRowCount(); j++) {
					if(sortTable.getValueAt(j, sortTable.findColumn("Sort Method")).equals("E")){
						String valuePattern = getRegexPattern(sortTable.getValueAt(j, sortTable.findColumn("Value or Expression")).toString());
						Pattern value = Pattern.compile(valuePattern);

						Matcher valueMatch = value.matcher(pages);

						found = valueMatch.find();
					}


					if(sortTable.getValueAt(j, sortTable.findColumn("Sort Method")).equals("V")){
						found = pages.contains(sortTable.getValueAt(j, sortTable.findColumn("Value or Expression")).toString());
					}


					if (found) {
						returnPage = pageList.get(i-1); // pageList (PDPageTree) contains page models (needed for saving)
						
						sortArr[arrIndex] = new SortElements();
						sortArr[arrIndex].setPdfModelName(returnPage);
						sortArr[arrIndex].setPriorVal(sortTable.getValueAt(j, sortTable.findColumn("Priority")).toString());
						sortArr[arrIndex].setSortSecVal(sortTable.getValueAt(j, sortTable.findColumn("Sort Method")).toString());
						sortArr[arrIndex].setUniVal(sortTable.getValueAt(j, sortTable.findColumn("Value or Expression")).toString());
						
						if(dir.isSortByBin()) {
							Matcher locMatch = locNum.matcher(pages);
							if(locMatch.find()){
								//System.out.println("Found: " + locMatch.group());
								sortArr[arrIndex].setBinNumber(locMatch.group());
							} else { 
								//System.out.println("Match not Found"); 
								String defaultBin = getNotFoundRegexValue(dir.getBinNumFormat());
								sortArr[arrIndex].setBinNumber(defaultBin);
							}
						}
						
						Matcher orderMatch = orderNum.matcher(pages);
						if(orderMatch.find()){
							sortArr[arrIndex].setOrderNumber(orderMatch.group());
						}
						
//						newDoc.addPage(returnPage);
						break;
					}
				}	
			}
			
			if(dir.isSortByBin()){
				Arrays.sort(sortArr, new Comparator<SortElements>() {
					@Override
					public int compare(SortElements o1, SortElements o2) {
						return o1.getBinNumber().compareTo(o2.getBinNumber());
					}				
				}) ;
			}
			
			List<String> priList = new ArrayList<String>();
			for(int i = 0; i < sortTable.getRowCount(); i++){
				if(!(priList.contains(sortTable.getValueAt(i, sortTable.findColumn("Priority")).toString()))){
					priList.add(sortTable.getValueAt(i, sortTable.findColumn("Priority")).toString());
				}
			}			
			
			if(dir.isSortByUniqueValue()){
				for(int i = 0; i < priList.size(); i++){
					int occurance = 0;
					for(int j = 0; j < sortArr.length; j++){
						if(sortArr[j].getPriorVal().equals(priList.get(i))){
							newDoc.addPage(sortArr[j].getPdfModelName());
							occurance++;
						}
					}
					resultList.add("Count Of Priority " + priList.get(i) + ": " + occurance);					
					//System.out.println(sortArr[j].getPriorVal() + "    " + priList.get(i));
				}	
			}
			
			resultList.add("Page Count @ End: " + sortArr.length);
			
			for(int i = 0; i < resultList.size(); i++){
				System.out.println(resultList.get(i));
			}
					
			newDoc.save(dir.getFinalSortFileName());
			
		} catch (Exception e) {
			System.err.println(e);
		}
		
		printMergeInfo(dir);
		
	}

	private static String getRegexPattern(String pattern){
		StringBuilder sb = new StringBuilder();
		
		for(int k = 0; k < pattern.length(); k++){
			char check = Character.toUpperCase(pattern.charAt(k));
			switch (check) {
			case '#':
				sb.append("\\d");
				break;
			case '^':
				sb.append("\\D");
				break;
			default:
				sb.append(check);
				break;
			}
		}
		//System.out.println(sb);
		return sb.toString();	
	}
	
	private static String getNotFoundRegexValue(String pattern){
		StringBuilder sb = new StringBuilder();
		
		for(int k = 0; k < pattern.length(); k++){
			char check = Character.toUpperCase(pattern.charAt(k));
			switch (check) {
			case '#':
				sb.append("0");
				break;
			case '^':
				sb.append("A");
				break;
			default:
				sb.append(check);
				break;
			}
		}
		//System.out.println(sb);
		return sb.toString();		
	}
	
	private static void printMergeInfo(Criteria dir){
		JOptionPane.showMessageDialog(null, "Finished Sorting");
	}

}
