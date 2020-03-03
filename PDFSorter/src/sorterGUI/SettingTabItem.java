package sorterGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

import configuration.config;
import pdfSorter.beans.Criteria;

public class SettingTabItem {
	
	private static JTextField txtFileUploadDir;
	private static JTextField txtFileSaveDir;
	private static JTextField txtPrefix;
	private static final ButtonGroup btnDateGrp = new ButtonGroup();
	private static JTextField txtSuffix;
	private static JTextField txtUserName;
	private static String dateBtnSelect = null;	

	public static JRadioButton rdbtnDate;
	public static JRadioButton rdbtnNoDate;
	public static JRadioButton rdbtnTimewH;
	public static JRadioButton rdbtnTimewHM;
	
	public static void settingsTab(JTabbedPane tabbedPane, Criteria dir){
				
		JPanel settingsTab = new JPanel();
		tabbedPane.addTab("Settings", null, settingsTab, null);
		settingsTab.setLayout(null);
		
		config save = new config();
		
		JLabel lblUploadDir = new JLabel("Default Directory For File Upload");
		lblUploadDir.setBounds(23, 17, 216, 16);
		settingsTab.add(lblUploadDir);
		
		JLabel lblSaveDir = new JLabel("Default Directory To Save File");
		lblSaveDir.setBounds(23, 45, 216, 16);
		settingsTab.add(lblSaveDir);
		
		txtFileUploadDir = new JTextField();
		txtFileUploadDir.setBounds(246, 12, 335, 26);
		settingsTab.add(txtFileUploadDir);
		txtFileUploadDir.setColumns(10);
		if(dir.getUploadDir() != null) { txtFileUploadDir.setText(dir.getUploadDir()); }
		
		txtFileSaveDir = new JTextField();
		txtFileSaveDir.setBounds(246, 40, 335, 26);
		settingsTab.add(txtFileSaveDir);
		txtFileSaveDir.setColumns(10);
		if(dir.getSaveDir() != null) { txtFileSaveDir.setText(dir.getSaveDir()); }
				
		JPanel fileNameSetting = new JPanel();
		fileNameSetting.setBounds(16, 180, 380, 208);
		settingsTab.add(fileNameSetting);
		fileNameSetting.setLayout(null);
		
		JLabel lblSavePanelTitle = new JLabel("Default Save File Name Preferences");
		lblSavePanelTitle.setBounds(5, 5, 368, 16);
		fileNameSetting.add(lblSavePanelTitle);
		
		JLabel lblPrefix = new JLabel("Prefix");
		lblPrefix.setBounds(5, 30, 61, 16);
		fileNameSetting.add(lblPrefix);
		
		txtPrefix = new JTextField();
		txtPrefix.setBounds(75, 25, 130, 26);
		fileNameSetting.add(txtPrefix);
		if(dir.getsPrefix() != null) { txtPrefix.setText(dir.getsPrefix()); }
		txtPrefix.setColumns(10);
		
		JLabel lblIncludeDateInformation = new JLabel("Include Date Information In File Name? (YYYY-MM-DD)");
		lblIncludeDateInformation.setBounds(6, 60, 353, 16);
		fileNameSetting.add(lblIncludeDateInformation);
		
		JLabel lblDatetime = new JLabel("Date/Time");
		lblDatetime.setBounds(16, 84, 86, 16);
		fileNameSetting.add(lblDatetime);
		
		rdbtnTimewHM = new JRadioButton("Date w/ Hours & Min");
		btnDateGrp.add(rdbtnTimewHM);
		rdbtnTimewHM.setBounds(15, 100, 164, 23);
		fileNameSetting.add(rdbtnTimewHM);
		
		rdbtnTimewH = new JRadioButton("Date w/ Hours");
		btnDateGrp.add(rdbtnTimewH);
		rdbtnTimewH.setBounds(15, 120, 141, 23);
		fileNameSetting.add(rdbtnTimewH);
		
		rdbtnDate = new JRadioButton("Date");
		btnDateGrp.add(rdbtnDate);
		rdbtnDate.setBounds(104, 80, 67, 23);
		fileNameSetting.add(rdbtnDate);
		
		rdbtnNoDate = new JRadioButton("No Date");
		btnDateGrp.add(rdbtnNoDate);
		rdbtnNoDate.setBounds(170, 80, 105, 23);
		fileNameSetting.add(rdbtnNoDate);

		if(dir.getBtnModelName() != null){
			if(dir.getBtnModelName().equals(rdbtnDate.getText())) {rdbtnDate.setSelected(true);}
			if(dir.getBtnModelName().equals(rdbtnNoDate.getText())) {rdbtnNoDate.setSelected(true);}
			if(dir.getBtnModelName().equals(rdbtnTimewH.getText())) {rdbtnTimewH.setSelected(true);}
			if(dir.getBtnModelName().equals(rdbtnTimewHM.getText())) {rdbtnTimewHM.setSelected(true);}
		}
		
		JLabel lblSuffix = new JLabel("Suffix");
		lblSuffix.setBounds(5, 145, 61, 16);
		fileNameSetting.add(lblSuffix);
		
		txtSuffix = new JTextField();
		txtSuffix.setBounds(75, 140, 130, 26);
		fileNameSetting.add(txtSuffix);
		if(dir.getsSuffix() != null) { txtSuffix.setText(dir.getsSuffix()); }
		txtSuffix.setColumns(10);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(5, 170, 75, 16);
		fileNameSetting.add(lblUserName);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(75, 165, 130, 26);
		fileNameSetting.add(txtUserName);
		if(dir.getsUserName() != null) { txtUserName.setText(dir.getsUserName()); }
		txtUserName.setColumns(10);

		saveSettingAction(dir, save);
		
		JButton btnSettingsUpdate = new JButton("Save Settings");
		btnSettingsUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveSettingAction(dir, save);
			}
		});
		btnSettingsUpdate.setBounds(438, 359, 143, 29);
		settingsTab.add(btnSettingsUpdate);	
	}
	
	public static void saveSettingAction(Criteria dir, config save){
		save.saveProp("Upload Dir ", txtFileUploadDir.getText());
		save.saveProp("Save Dir ", txtFileSaveDir.getText());
		save.saveProp("Save Prefix ", txtPrefix.getText());
		save.saveProp("Save Suffix ", txtSuffix.getText());
		save.saveProp("User Name ", txtUserName.getText());				

		if(rdbtnDate.isSelected()) {
			save.saveProp("Save Date Format ", rdbtnDate.getText()); 
			dateBtnSelect = rdbtnDate.getText();
			}
		if(rdbtnNoDate.isSelected()) {
			save.saveProp("Save Date Format ", rdbtnNoDate.getText()); 					
			dateBtnSelect = rdbtnNoDate.getText();
			}
		if(rdbtnTimewH.isSelected()) {
			save.saveProp("Save Date Format ", rdbtnTimewH.getText());
			dateBtnSelect = rdbtnTimewH.getText();
			}
		if(rdbtnTimewHM.isSelected()) {
			save.saveProp("Save Date Format ", rdbtnTimewHM.getText());
			dateBtnSelect = rdbtnTimewHM.getText();
			}
					
		dir.setUploadDir(txtFileUploadDir.getText());
		dir.setSaveDir(txtFileSaveDir.getText());
		dir.setsPrefix(txtPrefix.getText());
		dir.setsSuffix(txtSuffix.getText());
		dir.setsUserName(txtUserName.getText());
		dir.setBtnModelName(dateBtnSelect);
		
		getFileSaveName(dir);
		
		save.saveProp("Save File Name ", dir.getSaveFileName());
	}
	
	private static void getFileSaveName(Criteria dir) {
		String fileDate = "";
		LocalDateTime ldt = LocalDateTime.now();

		if(dir.getBtnModelName() != null) {
			if(dir.getBtnModelName().equals(rdbtnNoDate.getText())) { fileDate = ""; }

			if(dir.getBtnModelName().equals(rdbtnDate.getText())){
				DateTimeFormatter df = DateTimeFormatter.ofPattern("YYYY-MM-dd");
				fileDate = df.format(ldt)+ "-";
			}
			if(dir.getBtnModelName().equals(rdbtnTimewH.getText())){
				DateTimeFormatter df = DateTimeFormatter.ofPattern("YYYY-MM-dd-hh");
				fileDate = df.format(ldt) + "-";
			}
			if(dir.getBtnModelName().equals(rdbtnTimewHM.getText())){
				DateTimeFormatter df = DateTimeFormatter.ofPattern("YYYY-MM-dd-hh-mm");
				fileDate = df.format(ldt) + "-";
			}
		}
		
		dir.setSaveFileName(dir.getsPrefix() + "-" + fileDate + 
							dir.getsSuffix() + "-" + dir.getsUserName() + ".pdf");
		
	}
}
