package sorterGUI;

import java.awt.*;
import java.sql.SQLException;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.LineBorder;

import configuration.options;
import pdfSorter.beans.Criteria;
import pdfSorter.tables.CriteriaManager;

public class OptionsTabItem {

	private static JTable importTable = null;
	private static JTextField txtSortMethod;
	private static JTextField txtPriority;
	private static JTextField txtUniqueValue;
	private static JTextField txtOrdNumFormat;
	private static JTextField txtBinNumFormat;

	
	public static void optionsTab(JTabbedPane tabbedPane, Criteria dir, options saveOption){
		JPanel optionsTab = new JPanel();
		tabbedPane.addTab("Options", null, optionsTab, null);
		optionsTab.setLayout(null);
		
		importTable = new JTable();
		
		importTable.setModel(new DefaultTableModel(
	            new Object [][] {
	            },
	            new String [] {
	            }
	        ));

		importTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				String priority = importTable.getValueAt(importTable.getSelectedRow(), 1).toString();
				String sort = importTable.getValueAt(importTable.getSelectedRow(), 2).toString();
				String uniVal = importTable.getValueAt(importTable.getSelectedRow(), 3).toString();
				txtPriority.setText(priority);
				txtSortMethod.setText(sort);
				txtUniqueValue.setText(uniVal);
			}
		});
		
		JScrollPane jspDBjTable = new JScrollPane();
		jspDBjTable.setViewportView(importTable);
		jspDBjTable.setBounds(236, 165, 380, 200);
		optionsTab.add(jspDBjTable);
		
		getJTable();
		jTableModifier(optionsTab);
		additionalSortOptions(optionsTab, dir, saveOption);
		
	}
	
	private static void getJTable(){
		DefaultTableModel impTable = CriteriaManager.getJTableData();
		importTable.setModel(impTable);

		DefaultTableCellRenderer centRend = new DefaultTableCellRenderer();
		centRend.setHorizontalAlignment(JLabel.CENTER);

		for(int i = 0; i < importTable.getColumnCount(); i++){
			TableColumn col = importTable.getColumnModel().getColumn(i);
			if(i == 0) {
				col.setPreferredWidth(20);
				col.setCellRenderer(centRend);
			}
			if(i == 1) {
				col.setPreferredWidth(40);
				col.setCellRenderer(centRend);
			}
			if(i == 2) {
				col.setPreferredWidth(60);
				col.setCellRenderer(centRend);
			}
			if(i == 3) col.setPreferredWidth(180);
		}
	}
	
	public static void jTableModifier(JPanel optionsTab){
		JPanel jTableModifierPanel = new JPanel();
		jTableModifierPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		jTableModifierPanel.setBounds(6, 165, 227, 199);
		optionsTab.add(jTableModifierPanel);
		jTableModifierPanel.setLayout(null);
//
		JLabel lblPriority = new JLabel("Priority (1-9)");
		lblPriority.setBounds(6, 5, 132, 16);
		jTableModifierPanel.add(lblPriority);

		txtPriority = new JTextField();
		txtPriority.setBounds(150, 0, 71, 26);
		jTableModifierPanel.add(txtPriority);
		txtPriority.setColumns(10);
//
		JLabel lblSortMethod = new JLabel("Sort Section (V/E)");
		lblSortMethod.setBounds(6, 35, 132, 16);
		jTableModifierPanel.add(lblSortMethod);

		txtSortMethod = new JTextField();
		txtSortMethod.setBounds(150, 30, 71, 26);
		jTableModifierPanel.add(txtSortMethod);
		txtSortMethod.setColumns(10);
//		
		JLabel lblUniqueValue = new JLabel("Unique Value");
		lblUniqueValue.setBounds(6, 65, 132, 16);
		jTableModifierPanel.add(lblUniqueValue);

		txtUniqueValue = new JTextField();
		txtUniqueValue.setBounds(6, 85, 215, 26);
		jTableModifierPanel.add(txtUniqueValue);
		txtUniqueValue.setColumns(10);
//		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if(validateData(txtSortMethod.getText(), txtPriority.getText(), txtUniqueValue.getText(),
						lblSortMethod, lblPriority, lblUniqueValue)) {
					try {
						new CriteriaManager().insertSortingMethod(txtSortMethod.getText(), txtPriority.getText(), txtUniqueValue.getText());
						clearText();
						getJTable();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}  
			}
		});
		btnAdd.setBounds(6, 120, 108, 29);
		jTableModifierPanel.add(btnAdd);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int index = importTable.getSelectedRow();
				String id = importTable.getValueAt(index, 0).toString();
				if(validateData(txtSortMethod.getText(), txtPriority.getText(), txtUniqueValue.getText(),
						lblSortMethod, lblPriority, lblUniqueValue)) { 
					try {
						new CriteriaManager().updateSortingMethod(id, txtSortMethod.getText(), txtPriority.getText(), txtUniqueValue.getText());
						clearText();
						getJTable();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnUpdate.setBounds(113, 120, 108, 29);
		jTableModifierPanel.add(btnUpdate);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] options = {"Yes", "No"};
				int answ = JOptionPane.showOptionDialog(null, "Sure To Delete?", "Delete Confirm", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
				
				if (answ == 0) {
					int index = importTable.getSelectedRow();
					String id = importTable.getValueAt(index, 0).toString();
					try {
						new CriteriaManager().delete(id);
						clearText();           
						getJTable();
					} catch (HeadlessException | SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnRemove.setBounds(56, 150, 108, 29);
		jTableModifierPanel.add(btnRemove);
	}
	
	private static void additionalSortOptions(JPanel optionsTab, Criteria dir, options saveOption){
		JPanel pnlRegEx = new JPanel();
		pnlRegEx.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		pnlRegEx.setBounds(6, 6, 610, 147);
		optionsTab.add(pnlRegEx);
		pnlRegEx.setLayout(null);
		
		JLabel lblSortByOptions = new JLabel("Sort by Options");
		lblSortByOptions.setBounds(6, 5, 104, 16);
		pnlRegEx.add(lblSortByOptions);

		JCheckBox chckbxUniqueValue = new JCheckBox("Unique Value");
		chckbxUniqueValue.setBounds(6, 31, 128, 23);
		pnlRegEx.add(chckbxUniqueValue);
		chckbxUniqueValue.setSelected(dir.isSortByUniqueValue());

		JCheckBox chckbxBinNumber = new JCheckBox("Bin Number");
		chckbxBinNumber.setBounds(6, 55, 128, 23);
		pnlRegEx.add(chckbxBinNumber);
		chckbxBinNumber.setSelected(dir.isSortByBin());
		
		JLabel lblOrderNumberFormat = new JLabel("Order Number Format");
		lblOrderNumberFormat.setBounds(248, 5, 143, 16);
		pnlRegEx.add(lblOrderNumberFormat);
		
		JLabel lblBinNumberFormat = new JLabel("Bin Number Format");
		lblBinNumberFormat.setBounds(248, 35, 143, 16);
		pnlRegEx.add(lblBinNumberFormat);
		
		txtOrdNumFormat = new JTextField();
		txtOrdNumFormat.setBounds(391, 0, 213, 26);
		pnlRegEx.add(txtOrdNumFormat);
		txtOrdNumFormat.setColumns(10);
		txtOrdNumFormat.setText(dir.getOrderNumFormat());

		txtBinNumFormat = new JTextField();
		txtBinNumFormat.setBounds(391, 30, 213, 26);
		pnlRegEx.add(txtBinNumFormat);
		txtBinNumFormat.setColumns(10);
		txtBinNumFormat.setText(dir.getBinNumFormat());
				
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				saveOption.saveProp("orderNumFormat ", txtOrdNumFormat.getText());
				saveOption.saveProp("binNumFormat ", txtBinNumFormat.getText());
				boolean uniCheck = chckbxUniqueValue.isSelected();
				boolean binCheck = chckbxBinNumber.isSelected();	
				saveOption.saveProp("uniValBox ", Boolean.toString(uniCheck));
				saveOption.saveProp("binNumBox ", Boolean.toString(binCheck));
				dir.setOrderNumFormat(txtOrdNumFormat.getText());
				dir.setBinNumFormat(txtBinNumFormat.getText());
				dir.setSortByBin(binCheck);
				dir.setSortByUniqueValue(uniCheck);
			}
		});
		btnSave.setBounds(487, 112, 117, 29);
		pnlRegEx.add(btnSave);
	}
	
	private static void clearText(){
		//CLEAR TXT
		txtSortMethod.setText("");
		txtPriority.setText("");
		txtUniqueValue.setText("");
	}
	
	
	public static boolean validateData(String sortMethod, String priority, String uniqueValue,
										JLabel lblSortMethod, JLabel lblPriority, JLabel lblUniqueValue){
		boolean priorityCheck;
		try {
			priorityCheck = (1 <= Integer.parseInt(priority) && Integer.parseInt(priority) <= 9);
		} catch (Exception e) {
			priorityCheck = false;
		}
		
		boolean sortCheck = (sortMethod.equals("V") || sortMethod.equals("E"));
		boolean uniqueCheck = (!(uniqueValue.isEmpty()));
		int errors = 0;
		String errStmt = "";
				
		if(!sortCheck){
			errStmt = errStmt + "Sort Section Invalid Input (V/E) \n";
			lblSortMethod.setForeground(Color.RED);
			errors++;
		}
		if(!priorityCheck){
			errStmt = errStmt + "Priority Invalid Input (1-9) \n";
			lblPriority.setForeground(Color.RED);
			errors++;
		}
		if(!uniqueCheck){
			errStmt = errStmt + "Unique Value Cannot Be Blank";
			lblUniqueValue.setForeground(Color.RED);
			errors++;
		}
		if(errors > 0){
			JOptionPane.showMessageDialog(null, errStmt);
			return false;
		}
		lblSortMethod.setForeground(Color.BLACK);
		lblPriority.setForeground(Color.BLACK);
		lblUniqueValue.setForeground(Color.BLACK);
	
		return true;
	}
}
