package pdfSorter.tables;

import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pdfSorter.ConnectionManager;
import sorterGUI.OptionsTabItem;

public class CriteriaManager {

	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	//updated March
	public static DefaultTableModel getJTableData(){
		DefaultTableModel impTable = new DefaultTableModel();
		impTable.addColumn("ID");
		impTable.addColumn("Priority");
		impTable.addColumn("Sort Method"); //formally sortSection
		impTable.addColumn("Value or Expression");

		String sql = "SELECT * FROM Criteria";

		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				) {
			while(rs.next()){
				String id = rs.getString("id");
				String priority = rs.getString("priority");
				String sortMethod = rs.getString("sortMethod");
				String uniqueValue = rs.getString("uniqueValue");

				impTable.addRow(new String[] {id, priority, sortMethod, uniqueValue});
			}
			return impTable;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
				
	//updated March
	public boolean updateSortingMethod(String id, String sortMethod, String priority, String uniqueValue) throws SQLException{				
		String sql = "UPDATE Criteria SET sortMethod ='" + sortMethod.toUpperCase() + "',priority ='" + priority + 
									"',uniqueValue='" + uniqueValue +"' WHERE id ='" + id + "'";

		try(
				PreparedStatement stmt = conn.prepareStatement(sql);
				){			
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}		
	}
	
	//updated March
	public boolean insertSortingMethod(String sortMethod, String priority, String uniqueValue) throws SQLException{	
		String sql = "INSERT into Criteria (sortMethod, priority, uniqueValue) "
				+ "VALUES ('" + sortMethod.toUpperCase() + "','" + priority + "','" + uniqueValue + "');";
		String resetIncrement = "ALTER TABLE Criteria AUTO_INCREMENT = 1;";
			try(
				Statement stmt = conn.prepareStatement(sql);
				Statement incReset = conn.prepareStatement(resetIncrement);
				) {		
			incReset.execute(resetIncrement);
			stmt.execute(sql);
			return true;			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		} 
	}
	
	//updated March
	public boolean delete(String id) throws SQLException{		
		String sql = "DELETE FROM Criteria WHERE id = '" + id + "'";
		
		try(
				PreparedStatement stmt = conn.prepareStatement(sql);
				){			
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}	
	}	
}
