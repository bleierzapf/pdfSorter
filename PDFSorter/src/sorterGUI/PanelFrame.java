package sorterGUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import configuration.config;
import configuration.options;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import pdfSorter.beans.Criteria;
import configuration.config;
import pdfSorter.tables.CriteriaManager;
import pdfSorter.tables.UserManager;

import javax.swing.border.LineBorder;

public class PanelFrame {

	private JFrame frame;
	
	private DefaultListModel<String> model;
	private File[] pdfFiles;
	private JTextField loginEmail;
	private JPasswordField loginPW;

	/*
	 * Enable Program Functions
	 */
	private boolean requireLogin = false;
	private boolean addUsers = false;
	
	
	private static String mergeMethod = null;
	public static JRadioButton rdbtnMergeSort;
	public static JRadioButton rdbtnMergePdfFiles;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelFrame window = new PanelFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PanelFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 650, 478);
		frame.getContentPane().add(tabbedPane);

		Criteria dir = new Criteria();
		config save = new config();
		options saveOption = new options();
		
		/*
		 * Import Settings User Information
		 */
		dir.setUploadDir(save.getProp("Upload Dir "));
		dir.setSaveDir(save.getProp("Save Dir "));
		dir.setsPrefix(save.getProp("Save Prefix "));
		dir.setsSuffix(save.getProp("Save Suffix "));
		dir.setsUserName(save.getProp("User Name "));
		dir.setBtnModelName(save.getProp("Save Date Format "));	
		dir.setSaveFileName(save.getProp("Save File Name "));
		dir.setSortMethod(save.getProp("Merge Method "));
		
		/*
		 * Import Options User Information
		 */
		dir.setBinNumFormat(saveOption.getProp("binNumFormat "));
		dir.setOrderNumFormat(saveOption.getProp("orderNumFormat "));
		String uniCheckInput = saveOption.getProp("uniValBox ");
		String binCheckInput = saveOption.getProp("binNumBox ");
		dir.setSortByBin(Boolean.valueOf(binCheckInput));
		dir.setSortByUniqueValue(Boolean.valueOf(uniCheckInput));
		

		if(requireLogin){
			JPanel login = new JPanel();
			tabbedPane.addTab("Login", null, login, null);
			login.setLayout(null);

			JLabel lblEmailAddress = new JLabel("Email Address");
			lblEmailAddress.setBounds(68, 153, 110, 16);
			login.add(lblEmailAddress);

			JLabel lblPassword = new JLabel("Password");
			lblPassword.setBounds(68, 181, 110, 16);
			login.add(lblPassword);	

			JLabel lblSignInFailedMsg = new JLabel("");
			lblSignInFailedMsg.setForeground(Color.RED);
			lblSignInFailedMsg.setBounds(409, 153, 188, 16);
			login.add(lblSignInFailedMsg);

			loginEmail = new JTextField();
			loginEmail.setText("sample@example.com");
			loginEmail.setBounds(190, 148, 214, 26);
			login.add(loginEmail);
			loginEmail.setColumns(10);

			loginPW = new JPasswordField();
			loginPW.setBounds(190, 176, 214, 26);
			login.add(loginPW);

			JButton btnSignIn = new JButton("Sign In");
			btnSignIn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					UserManager userMng = new UserManager();
					//userMng.validLogin(loginEmail.getText(), loginPW.getPassword());

				}
			});
			btnSignIn.setBounds(190, 214, 117, 29);
			login.add(btnSignIn);

			JCheckBox chckbxRemMe = new JCheckBox("Remember Me");
			chckbxRemMe.setBounds(319, 215, 128, 23);
			login.add(chckbxRemMe);

			JButton btnNewUser = new JButton("New User");
			btnNewUser.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					NewUser newUser = new NewUser();
					newUser.setVisible(true);		
				}
			});
			btnNewUser.setBounds(190, 255, 117, 29);
			login.add(btnNewUser);
		}
		
		
		/*
		 * Below Section For Designing Do Not Keep
		 */
		

		
		
	/*
	 * 	
	 */
		programTabs(tabbedPane, frame, dir, saveOption);	
		
	}
	

	public void programTabs(JTabbedPane tabbedPane, JFrame frame, Criteria dir, options saveOption){
		/*
		 * Program Tabs
		 */
		MergeTabItem.mergeTab(tabbedPane, frame, dir);		
		OptionsTabItem.optionsTab(tabbedPane, dir, saveOption);	
		SettingTabItem.settingsTab(tabbedPane, dir);
		SystemInformation.systemInfo(tabbedPane);
	}
}


