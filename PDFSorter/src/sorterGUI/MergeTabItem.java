package sorterGUI;

import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDPage;

import configuration.config;
import pdfSorter.SortPDFFiles;
import pdfSorter.beans.Criteria;

public class MergeTabItem {

	private static DefaultListModel<String> pdfList;
	private static DefaultListModel<String> resultsData;
	private static File[] pdfFiles;
	private static String mergeMethod = null;
	public static JRadioButton rdbtnMergeSort;
	public static JRadioButton rdbtnMergePdfFiles;

	public static void mergeTab(JTabbedPane tabbedPane, JFrame frame, Criteria dir){
		final ButtonGroup butgrp = new ButtonGroup();
		config save = new config();
		JList<String> uploadList = new JList<>(pdfList = new DefaultListModel<String>());

		JPanel mergeTab = new JPanel();
		tabbedPane.addTab("PDF Sort & Merge", null, mergeTab, null);
		mergeTab.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 30, 620, 180);
		mergeTab.add(panel);

		JLabel lblSelectedFileTitle = new JLabel("Selected PDF Files");
		lblSelectedFileTitle.setLabelFor(panel);
		panel.setLayout(null);

		uploadList.setBounds(5, 5, 610, 170);
		panel.add(uploadList);
		lblSelectedFileTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblSelectedFileTitle.setBounds(150, 10, 114, 16);
		mergeTab.add(lblSelectedFileTitle);	

		JButton btnUploadPdf = new JButton("Upload PDF");
		btnUploadPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFileChooser fc = new JFileChooser(dir.getUploadDir());
				fc.setMultiSelectionEnabled(true);
				fc.showOpenDialog(frame);
				pdfFiles = fc.getSelectedFiles();
				for(File pdf : pdfFiles){
					pdfList.addElement(pdf.getPath());
				}
			}
		});
		btnUploadPdf.setBounds(95, 211, 110, 29);
		mergeTab.add(btnUploadPdf);

		JButton btnRemovePdf = new JButton("Remove PDF");
		btnRemovePdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int listItem = uploadList.getSelectedIndex();
				if(listItem != -1) pdfList.removeElementAt(listItem);
			}
		});
		btnRemovePdf.setBounds(205, 211, 110, 29);
		mergeTab.add(btnRemovePdf);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(425, 246, 200, 180);
		mergeTab.add(panel_1);
		panel_1.setLayout(null);

		rdbtnMergePdfFiles = new JRadioButton("Merge PDF Files");
		rdbtnMergePdfFiles.setBounds(6, 18, 131, 23);
		panel_1.add(rdbtnMergePdfFiles);
		butgrp.add(rdbtnMergePdfFiles);

		rdbtnMergeSort = new JRadioButton("Merge & Sort PDF Files");
		rdbtnMergeSort.setBounds(6, 40, 173, 23);
		panel_1.add(rdbtnMergeSort);
		butgrp.add(rdbtnMergeSort);
		
		JPanel resultPanel = new JPanel();
		resultPanel.setBounds(5, 246, 410, 180);
		mergeTab.add(resultPanel);
		resultPanel.setLayout(null);
		
		JLabel lblResults = new JLabel("Results");
		lblResults.setBounds(6, 6, 61, 16);
		resultPanel.add(lblResults);
		
		JList<String> resultsList = new JList<>(resultsData = new DefaultListModel<String>());
		resultsList.setBounds(5, 25, 400, 150);
		resultPanel.add(resultsList);
		
		if(dir.getSortMethod() != null){
			if(dir.getSortMethod().equals(rdbtnMergePdfFiles.getText())) {rdbtnMergePdfFiles.setSelected(true);}
			if(dir.getSortMethod().equals(rdbtnMergeSort.getText())) {rdbtnMergeSort.setSelected(true);}
		}
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(104, 145, 88, 29);
		panel_1.add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {								
				SettingTabItem.saveSettingAction(dir, save);
				
				dir.setTempFileName(dir.getSaveDir() + "/" + "Temp-" + dir.getSaveFileName());
				dir.setFinalSortFileName(dir.getSaveDir() + "/" + dir.getSaveFileName());
				
				if(rdbtnMergePdfFiles.isSelected()) {
					mergeMethod = rdbtnMergePdfFiles.getText(); }
				if(rdbtnMergeSort.isSelected()) {
					mergeMethod = rdbtnMergeSort.getText(); }
				dir.setSortMethod(mergeMethod);
				save.saveProp("Merge Method ", mergeMethod);
				
				mergePDFFiles(dir, pdfList);
				
				if(dir.getSortMethod().equals("Merge & Sort PDF Files")){
					SortPDFFiles.sortFiles(dir);
					File tempFile = new File(dir.getTempFileName());
					tempFile.delete();
				}
		
			}
		});

	}

	public static void mergePDFFiles(Criteria dir, DefaultListModel<String> pdfList){
		PDFMergerUtility pdfMerge = new PDFMergerUtility();
			
		for (int i = 0; i < pdfList.size(); i++){
			try {
				pdfMerge.addSource(pdfList.getElementAt(i));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		try {
			if(dir.getSortMethod().equals("Merge PDF Files")){
				pdfMerge.setDestinationFileName(dir.getFinalSortFileName());
			}
			if(dir.getSortMethod().equals("Merge & Sort PDF Files")){
				pdfMerge.setDestinationFileName(dir.getTempFileName());
			}
			pdfMerge.mergeDocuments(null);
		
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}	
}
