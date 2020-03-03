package sorterGUI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class SystemInformation {

	public static void systemInfo (JTabbedPane tabbedPane){
		JPanel sysInfo = new JPanel();
		tabbedPane.addTab("Program Information", null, sysInfo, null);
		sysInfo.setLayout(null);

		JLabel lblName = new JLabel("Built by: Brian Leierzapf");
		lblName.setBounds(27, 37, 217, 16);
		sysInfo.add(lblName);

		JLabel lblVerson = new JLabel("Verson: 2.0");
		lblVerson.setBounds(27, 65, 217, 16);
		sysInfo.add(lblVerson);
		
		JLabel lblDate = new JLabel("Date: April 5th 2017");
		lblDate.setBounds(27, 93, 217, 16);
		sysInfo.add(lblDate);

	}
}
