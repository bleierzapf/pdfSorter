package sorterGUI;

import javax.swing.DefaultListModel;

import org.apache.pdfbox.pdmodel.*;

public class SortElements {
	PDPage pdfModelName;
	String uniVal;
	String priorVal;
	String sortSecVal;
	String binNumber;
	String orderNumber;
	
	public PDPage getPdfModelName() {
		return pdfModelName;
	}
	public void setPdfModelName(PDPage pdfModelName) {
		this.pdfModelName = pdfModelName;
	}
	public String getUniVal() {
		return uniVal;
	}
	public void setUniVal(String uniVal) {
		this.uniVal = uniVal;
	}
	public String getPriorVal() {
		return priorVal;
	}
	public void setPriorVal(String priorVal) {
		this.priorVal = priorVal;
	}
	public String getSortSecVal() {
		return sortSecVal;
	}
	public void setSortSecVal(String sortSecVal) {
		this.sortSecVal = sortSecVal;
	}
	public String getBinNumber() {
		return binNumber;
	}
	public void setBinNumber(String binNumber) {
		this.binNumber = binNumber;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
}
