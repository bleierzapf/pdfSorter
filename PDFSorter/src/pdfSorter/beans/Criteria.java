package pdfSorter.beans;

public class Criteria {
	
	private int id;
	private String sortMethod;
	private String uniqueValue;
	private int priority;
	private int orderNumber;
	
	//in sorterGUI package
	//saved data
	//Settings Tab Values
	private String uploadDir;
	private String saveDir;
	private String sPrefix;
	private String sSuffix;
	private String sUserName;
	private String btnModelName;
	private String saveFileName;
	private String tempFileName;
	private String finalSortFileName;
		
	//Options Tab Values
	private String orderNumFormat;
	private String binNumFormat;
	private boolean sortByUniqueValue;
	private boolean sortByBin;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUniqueValue() {
		return uniqueValue;
	}
	public void setUniqueValue(String uniqueValue) {
		this.uniqueValue = uniqueValue;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getSortMethod() {
		return sortMethod;
	}
	public void setSortMethod(String sortMethod) {
		this.sortMethod = sortMethod;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	/*
	 * SAVED DATA TO TXT DOCS FOR SAVED SETTING
	 * SAVE METHODS IN CONFIGURATION PACKAGE
	 */
	public String getUploadDir() {
		return uploadDir;
	}
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	public String getSaveDir() {
		return saveDir;
	}
	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
	}	
	public String getsPrefix() {
		return sPrefix;
	}
	public void setsPrefix(String sPrefix) {
		this.sPrefix = sPrefix;
	}
	public String getsSuffix() {
		return sSuffix;
	}
	public void setsSuffix(String sSuffix) {
		this.sSuffix = sSuffix;
	}
	public String getsUserName() {
		return sUserName;
	}
	public void setsUserName(String sUserName) {
		this.sUserName = sUserName;
	}
	public String getBtnModelName() {
		return btnModelName;
	}
	public void setBtnModelName(String btnModelName) {
		this.btnModelName = btnModelName;
	}
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public String getTempFileName() {
		return tempFileName;
	}
	public void setTempFileName(String tempFileName) {
		this.tempFileName = tempFileName;
	}
	public String getFinalSortFileName() {
		return finalSortFileName;
	}
	public void setFinalSortFileName(String finalSortFileName) {
		this.finalSortFileName = finalSortFileName;
	}
	
	
	/*
	 * 
	 */
	public String getOrderNumFormat() {
		return orderNumFormat;
	}
	public void setOrderNumFormat(String orderNumFormat) {
		this.orderNumFormat = orderNumFormat;
	}
	public String getBinNumFormat() {
		return binNumFormat;
	}
	public void setBinNumFormat(String binNumFormat) {
		this.binNumFormat = binNumFormat;
	}
	public boolean isSortByUniqueValue() {
		return sortByUniqueValue;
	}
	public void setSortByUniqueValue(boolean sortByUniqueValue) {
		this.sortByUniqueValue = sortByUniqueValue;
	}
	public boolean isSortByBin() {
		return sortByBin;
	}
	public void setSortByBin(boolean sortByBin) {
		this.sortByBin = sortByBin;
	}

}
