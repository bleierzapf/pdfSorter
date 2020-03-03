package sorterGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pdfSorter.tables.UserManager;

import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewUser extends JFrame{

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewUser userFrame = new NewUser();
					userFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private JPanel frame;
	private JTextField txtFieldEmailAddress;
	private JTextField txtFieldFirstName;
	private JTextField txtFieldLastName;
	private JPasswordField pwFieldA;
	private JPasswordField pwFieldB;
	private JTextField txtFieldCompany;
	
	public JTextField getTxtFieldEmailAddress() {
		return txtFieldEmailAddress;
	}
	public void setTxtFieldEmailAddress(JTextField txtFieldEmailAddress) {
		this.txtFieldEmailAddress = txtFieldEmailAddress;
	}
	public JTextField getTxtFieldFirstName() {
		return txtFieldFirstName;
	}
	public void setTxtFieldFirstName(JTextField txtFieldFirstName) {
		this.txtFieldFirstName = txtFieldFirstName;
	}
	public JTextField getTxtFieldLastName() {
		return txtFieldLastName;
	}
	public void setTxtFieldLastName(JTextField txtFieldLastName) {
		this.txtFieldLastName = txtFieldLastName;
	}
	public JPasswordField getPwFieldA() {
		return pwFieldA;
	}
	public void setPwFieldA(JPasswordField pwFieldA) {
		this.pwFieldA = pwFieldA;
	}
	public JPasswordField getPwFieldB() {
		return pwFieldB;
	}
	public void setPwFieldB(JPasswordField pwFieldB) {
		this.pwFieldB = pwFieldB;
	}
	public JTextField getTxtFieldCompany() {
		return txtFieldCompany;
	}
	public void setTxtFieldCompany(JTextField txtFieldCompany) {
		this.txtFieldCompany = txtFieldCompany;
	}
	
	/**
	 * Create the application.
	 */
	public NewUser() {
		setBounds(100, 100, 500, 300);
		frame = new JPanel();
		frame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(frame);
		frame.setLayout(null);		
		
		JLabel lblRegisterNewUser = new JLabel("Register New User");
		lblRegisterNewUser.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblRegisterNewUser.setBounds(122, 15, 142, 20);
		frame.add(lblRegisterNewUser);
		
		JLabel lblNewUserEmail = new JLabel("Email Address");
		lblNewUserEmail.setBounds(10, 50, 89, 16);
		frame.add(lblNewUserEmail);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(10, 75, 68, 16);
		frame.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(10, 100, 66, 16);
		frame.add(lblLastName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 125, 59, 16);
		frame.add(lblPassword);
		
		JLabel lblPasswordConfirm = new JLabel("Password Confirm");
		lblPasswordConfirm.setBounds(10, 150, 114, 16);
		frame.add(lblPasswordConfirm);
		
		txtFieldEmailAddress = new JTextField();
		txtFieldEmailAddress.setBounds(125, 45, 130, 26);
		frame.add(txtFieldEmailAddress);
		txtFieldEmailAddress.setColumns(10);
		
		txtFieldFirstName = new JTextField();
		txtFieldFirstName.setBounds(125, 70, 130, 26);
		frame.add(txtFieldFirstName);
		txtFieldFirstName.setColumns(10);
		
		txtFieldLastName = new JTextField();
		txtFieldLastName.setBounds(125, 95, 130, 26);
		frame.add(txtFieldLastName);
		txtFieldLastName.setColumns(10);
		
		pwFieldA = new JPasswordField();
		pwFieldA.setBounds(125, 120, 10, 26);
		frame.add(pwFieldA);
		
		pwFieldB = new JPasswordField();
		pwFieldB.setBounds(125, 145, 10, 26);
		frame.add(pwFieldB);
		
		JLabel lblCompany = new JLabel("Company");
		lblCompany.setBounds(10, 175, 59, 16);
		frame.add(lblCompany);
		
		txtFieldCompany = new JTextField();
		txtFieldCompany.setBounds(125, 170, 130, 26);
		frame.add(txtFieldCompany);
		txtFieldCompany.setColumns(10);
		
		JLabel lblEmailInvalidMes = new JLabel();
		lblEmailInvalidMes.setForeground(Color.RED);
		lblEmailInvalidMes.setBounds(325, 50, 0, 0);
		frame.add(lblEmailInvalidMes);
		
		JLabel lblPWInvalid = new JLabel();
		lblPWInvalid.setForeground(Color.RED);
		lblPWInvalid.setBounds(325, 125, 0, 0);
		frame.add(lblPWInvalid);
		
		UserManager userMng = new UserManager();
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Boolean pwFieldEmpty = getPwFieldA().getPassword().length == 0 || getPwFieldB().getPassword().length == 0;
				Boolean uniqueUser = userMng.uniqueUser(txtFieldEmailAddress.getText());	
				Boolean passwordMatch = userMng.passwordMatch(pwFieldA.getPassword(), pwFieldB.getPassword());
				Boolean newCompany = userMng.newCompany(txtFieldCompany.getText());
				
				if(!getTxtFieldEmailAddress().getText().contains("@") || !getTxtFieldEmailAddress().getText().contains(".")) {
					lblEmailInvalidMes.setText("Invalid Email Address");
					return;
				} else {
					lblEmailInvalidMes.setText("");
				}
				
				if(!uniqueUser){
					lblEmailInvalidMes.setText("Email Already Registered");
					return;
				} else {
					lblEmailInvalidMes.setText("");
				}
				
				if(pwFieldEmpty){
					System.out.println("empty");
					lblPWInvalid.setText("Enter Password");
					return;
				} else if (!passwordMatch) {
					System.out.println("pw mismatch");
					lblPWInvalid.setText("Passwords Don't Match");
					return;
				} else {
					lblPWInvalid.setText("");
				}
				
				if(!newCompany){
					int ans = JOptionPane.showConfirmDialog(null, "Company already on file.Send messege\nto admin to gain access?",
							"Company On File", JOptionPane.YES_NO_OPTION);
					if(ans == 0){
						userMng.adminEmail(txtFieldCompany.getText(), txtFieldEmailAddress.getText());
					}
				}
				
				userMng.registerUser(txtFieldEmailAddress.getText(), txtFieldFirstName.getText(),
						txtFieldLastName.getText(), pwFieldA.getPassword(), txtFieldCompany.getText(), newCompany);
				
			} 
		});
		btnRegister.setBounds(208, 200, 95, 29);
		frame.add(btnRegister);
	}
	
	
}

