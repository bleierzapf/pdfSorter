package pdfSorter.tables;

import java.sql.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import pdfSorter.ConnectionManager;

public class UserManager {

	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	public void adminEmail(String companyName, String userEmail){
		String sql = "SELECT * FROM userInfoTable WHERE company = '" + companyName + "' AND comAccLvl = 'Admin'";
		String adminEmail = "";
		
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);		
				) {
			while(rs.next()){
			adminEmail = rs.getString("email");
			System.out.println(adminEmail);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String protocol = "smtp";
		String host = "localhost";
		String port = "2525";
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", protocol);
		properties.put("mail.stmp.host", host);
		properties.put("mail.stmp.port", port);
		
		Session session = Session.getDefaultInstance(properties);
		
		try {
			MimeMessage messege = new MimeMessage(session);
			messege.setFrom(new InternetAddress(adminEmail));
			messege.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
			messege.setSubject("New User Requesting Access");
			messege.setText("Body Of Email");
			Transport.send(messege);
			System.out.println("Email Sent");
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	/*
	 * Methods for new users 
	 */	
	public boolean passwordMatch(char[] pwA, char[] pwB){
		if(!(pwA.length == pwB.length)){
			return false;
		}
		
		for(int i = 0; i < pwA.length; i++){
			if(!(pwA[i] == (pwB[i]))){
				return false;
			}
		}
		
		
//		if(pwA.toString().equals(pwB.toString())){
//			return true;
//		}
		return true;
	}

	public boolean uniqueUser(String userName){
		String sql = "SELECT * FROM userInfoTable WHERE email = '" + userName + "'";
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				) {
			if(!rs.next()){
				return true;
			}
			return false;
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean newCompany(String companyName){
		String sql = "SELECT * FROM userInfoTable WHERE company = '" + companyName + "'";
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				) {
			if(!rs.next()){
				return true;
			}
			return false;
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public void registerUser(String email, String fName, String lName, char[] pw, String company, Boolean newCompany){
		String accLvl = "New User";
		
		if(newCompany) {
			accLvl = "Admin";
		}
	
		String sql = "INSERT into userInfoTable (email, pw, company, firstName, lastName, comAccLvl) "
				+ "VALUES('"+ email +"','"+ pw.toString() +"','"+ company +"','"+ fName +"','"+ lName + "','"+ accLvl +"');";
		String resetIncrement = "ALTER TABLE userInfoTable AUTO_INCREMENT = 1;";
		try(
				Statement stmt = conn.prepareStatement(sql);
				Statement incReset = conn.prepareStatement(resetIncrement);
				) {
			incReset.execute(resetIncrement);
			stmt.execute(sql);
			System.out.println("User Added");

		} catch (Exception e){
			e.printStackTrace();
		}	
	}
	
	
}
