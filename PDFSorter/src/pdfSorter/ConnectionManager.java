package pdfSorter;

import pdfSorter.beans.SqlConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
	private static ConnectionManager instance = null;
	private Connection conn = null;

	SqlConnection sqlCon = new SqlConnection();

	private ConnectionManager() {
	}
	
	public static ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}
	
	private boolean openConnection()
	{
		try {
				conn = DriverManager.getConnection(sqlCon.getM_CONN_STRING(), sqlCon.getUSERNAME(), sqlCon.getPASSWORD());
				return true;
			}
		catch (SQLException e) {
			System.err.println(e);
			return false;
		}
	}
	
	public Connection getConnection()
	{
		if (conn == null) {
			if (openConnection()) {
				return conn;
			} else {
				return null;
			}
		}
		return conn;
	}

	public void close() {
		System.out.println("Closing connection");
		try {
			conn.close();
			conn = null;
		} catch (Exception e) {
		}
	}	
}
