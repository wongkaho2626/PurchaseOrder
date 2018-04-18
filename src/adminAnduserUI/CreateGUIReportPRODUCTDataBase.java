package adminAnduserUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import domain.CreateDateVendorReport;
import domain.CreatePRODUCTDataBaseReport;
import domain.CreateSalesReport;
import domain.CreateTotalUnitReport;
import domain.DataReportDateVendor;
import domain.DataReportPRODUCTDataBase;
import domain.IRBS;
import domain.DataReportSales;
import domain.DataReportTotalUnit; 

public class CreateGUIReportPRODUCTDataBase {
	private JLabel reportLabel;
	private JComboBox selectVendorComboBox;
	private JButton reportButton;

	private JPanel errorPanel;
	private JPanel reportPanel;
	private JScrollPane reportScrollPane;
	
	CreatePRODUCTDataBaseReport createReport;
	ArrayList<DataReportPRODUCTDataBase> data;
	ArrayList<DataReportPRODUCTDataBase> data2;

	public CreateGUIReportPRODUCTDataBase(){
		initUI();
	}

	private void initUI() {
		setUP();
	}

	private void setUP() {
		reportPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		reportScrollPane = new JScrollPane(reportPanel);

		// ************************* reportPanel UI SetUP
		// *************************
		// ========================= reportPanel Level One UI Create
		// =========================
		
		reportLabel = new JLabel("Vendor PRODUCT Database Report (mainly for checking \"Duty Code\" purpose)");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//gbc.ipadx = 100;
		gbc.gridwidth = 8;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		reportPanel.add(reportLabel, gbc);
		
		reportLabel = new JLabel("Vendor: ");
		gbc.ipady = 10;
		gbc.ipadx = 50;
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 100001;
		reportPanel.add(reportLabel, gbc);
		
//		vendorTextField = new JTextField();
//		gbc.ipadx = 0;
//		gbc.gridwidth = 4;
//		gbc.gridx = 4;
//		gbc.ipadx = 100;
//		gbc.gridy = 100001;
//		reportPanel.add(vendorTextField, gbc);
		
		IRBS irbs = new IRBS();
		selectVendorComboBox = new JComboBox(irbs.vendorStatement(false).toArray());
		selectVendorComboBox.setEditable(false);
		try {
			irbs.getCon().close();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		gbc.gridwidth = 4;
		gbc.gridx = 4;
		gbc.gridy = 100001;
		reportPanel.add(selectVendorComboBox, gbc);

		reportButton = new JButton("Report");
		gbc.ipadx = 0;
		gbc.gridwidth = 2;
		gbc.gridx = 8;
		gbc.gridy = 1000000;
		reportButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//select the data from complete unit
//				String sql = "SELECT completeunitOrderItem.PRODUCT, completeunitOrderItem.purchaseCode, "
//						+ "maintenance.DESCRIPTION, maintenance.DUTY_CODE "
//						+ "FROM completeunitOrderItem, completeunitPurchaseOrder, maintenance "
//						+ "WHERE completeunitPurchaseOrder.poNumber = completeunitOrderItem.poNumber "
//						+ "AND completeunitOrderItem.PRODUCT = maintenance.PRODUCT "
//						+ "AND completeunitPurchaseOrder.vendor = \'" + selectVendorComboBox.getSelectedItem().toString() + "\' "
//						+ "GROUP BY completeunitOrderItem.PRODUCT, completeunitOrderItem.purchaseCode, "
//						+ "maintenance.DESCRIPTION, maintenance.DUTY_CODE " 
//						+ "ORDER BY `completeunitOrderItem`.`PRODUCT` ASC";
//				
//				//select the data from spare part
//				String sql2 = "SELECT sparepartOrderItem.PRODUCT, maintenance.purchaseCode, "
//						+ "maintenance.DESCRIPTION, maintenance.DUTY_CODE "
//						+ "FROM sparepartOrderItem, sparepartPurchaseOrder, maintenance "
//						+ "WHERE sparepartPurchaseOrder.poNumber = sparepartOrderItem.poNumber "
//						+ "AND sparepartOrderItem.PRODUCT = maintenance.PRODUCT "
//						+ "AND sparepartPurchaseOrder.vendor = \'" + selectVendorComboBox.getSelectedItem().toString() + "\' "
//						+ "GROUP BY sparepartOrderItem.PRODUCT, maintenance.purchaseCode, "
//						+ "maintenance.DESCRIPTION, maintenance.DUTY_CODE " 
//						+ "ORDER BY `sparepartOrderItem`.`PRODUCT` ASC";
				
				//from maintenance
				String sql = "SELECT maintenance.PRODUCT, maintenance.purchaseCode, "
						+ "maintenance.DESCRIPTION, maintenance.DUTY_CODE "
						+ "FROM maintenance "
						+ "WHERE maintenance.vendor = \'" + selectVendorComboBox.getSelectedItem().toString() + "\' "
						+ "GROUP BY maintenance.purchaseCode, maintenance.DESCRIPTION, maintenance.DUTY_CODE " 
						+ "ORDER BY `maintenance`.`PRODUCT` ASC";
				
				IRBS irbs = new IRBS();
				data = new ArrayList<DataReportPRODUCTDataBase>();
//				data2 = new ArrayList<DataReportPRODUCTDataBase>();
				data = irbs.reportPRODUCTDataBase(sql);
//				data2 = irbs.reportPRODUCTDataBase(sql2);
//				data.addAll(data2);
				createReport = new CreatePRODUCTDataBaseReport(data, selectVendorComboBox.getSelectedItem().toString());
				try {
					irbs.getCon().close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(sql);
					
			}
		});
		reportPanel.add(reportButton, gbc);

	}
	
	public JScrollPane reportPane(){
		return reportScrollPane;
	}
}


