package configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.ButtonModel;

public class config {
	public static Properties prop = new Properties();
	static final String CONFIGsaveFILE = "settings.txt";
	
	public void saveProp(String title, String value){
		try {
			prop.setProperty(title, value);
			prop.store(new FileOutputStream(CONFIGsaveFILE), null);	
		} catch (IOException e) {
			
		}
	}
	
	public String getProp(String title){
		try {
			prop.load(new FileInputStream(CONFIGsaveFILE));
			return prop.getProperty(title);
		} catch (IOException e) {
			
		}
		return null;
	}

}
