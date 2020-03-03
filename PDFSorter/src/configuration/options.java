package configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class options {
	public static Properties prop = new Properties();
	static final String OPTIONSsaveFILE = "options.txt";
	
	public void saveProp(String title, String value){
		try {
			prop.setProperty(title, value);
			prop.store(new FileOutputStream(OPTIONSsaveFILE), null);	
		} catch (IOException e) {
			
		}
	}
	
	public String getProp(String title){
		try {
			prop.load(new FileInputStream(OPTIONSsaveFILE));
			return prop.getProperty(title);
		} catch (IOException e) {
			
		}
		return null;
	}

}
