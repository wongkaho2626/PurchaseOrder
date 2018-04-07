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

import com.mysql.jdbc.StringUtils;

import domain.CreateDateVendorReport;
import domain.CreateSalesReport;
import domain.CreateTotalUnitReport;
import domain.DataReportDateVendor;
import domain.IRBS;
import domain.DataReportSales;
import domain.DataReportTotalUnit; 

public class CreateGUIReportDateVendor {
	private JLabel reportLabel;
	private JTextField startOrderDateTextField;
	private JTextField endOrderDateTextField;
	private JComboBox selectVendorComboBox;
	private JButton reportButton;

	private JPanel errorPanel;
	private JPanel reportPanel;
	private JScrollPane reportScrollPane;
	
	CreateDateVendorReport createReport;
	ArrayList<DataReportDateVendor> data, data2;

	public CreateGUIReportDateVendor(){
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
		
		reportLabel = new JLabel("Vendor & PO Report");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//gbc.ipadx = 100;
		gbc.gridwidth = 8;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		reportPanel.add(reportLabel, gbc);
		
		reportLabel = new JLabel("OrderDate (MM/DD/YY): ");
		gbc.ipady = 10;
		gbc.ipadx = 50;
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 100000;
		reportPanel.add(reportLabel, gbc);

		reportLabel = new JLabel("From: ");
		gbc.ipadx = 0;
		gbc.gridwidth = 1;
		gbc.gridx = 4;
		gbc.gridy = 100000;
		reportPanel.add(reportLabel, gbc);

		startOrderDateTextField = new JTextField("", 3);
		gbc.ipadx = 100;
		gbc.gridx = 5;
		gbc.gridy = 100000;
		startOrderDateTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(checkMM(startOrderDateTextField.getText())){
						startOrderDateTextField.setText(startOrderDateTextField.getText() + "/");
					}
					if(checkDD(startOrderDateTextField.getText())){
						startOrderDateTextField.setText(startOrderDateTextField.getText() + "/");
					}
					reportPanel.revalidate();  
					reportPanel.repaint();
				}
			}
		});
		reportPanel.add(startOrderDateTextField, gbc);

		reportLabel = new JLabel("            To: ");
		gbc.gridx = 6;
		gbc.ipadx = 0;
		gbc.gridy = 100000;
		reportPanel.add(reportLabel, gbc);

		endOrderDateTextField = new JTextField("", 3);
		gbc.ipadx = 100;
		gbc.gridx = 7;
		gbc.gridy = 100000;
		endOrderDateTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(checkMM(endOrderDateTextField.getText())){
						endOrderDateTextField.setText(endOrderDateTextField.getText() + "/");
					}
					if(checkDD(endOrderDateTextField.getText())){
						endOrderDateTextField.setText(endOrderDateTextField.getText() + "/");
					}
					reportPanel.revalidate();  
					reportPanel.repaint();
				}
			}
		});
		reportPanel.add(endOrderDateTextField, gbc);
		
		reportLabel = new JLabel("Vendor: ");
		gbc.ipady = 10;
		gbc.ipadx = 50;
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 100001;
		reportPanel.add(reportLabel, gbc);
		
		IRBS irbs = new IRBS();
		selectVendorComboBox = new JComboBox(irbs.vendorStatement(true).toArray());
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
				if(!startOrderDateTextField.getText().equals("") && !endOrderDateTextField.getText().equals("")){
					
					//get the complete unit data
					String sql = "SELECT completeunitOrderItem.poNumber, "
							+ "completeunitOrderItem.subCode, completeunitOrderItem.PRODUCT, "
							+ "maintenance.DESCRIPTION, completeunitOrderItem.price, "
							+ "completeunitOrderItem.fixedcost, maintenance.DUTY_CODE, "
							+ "completeunitPurchaseOrder.deposit, maintenance.vendor "
							+ "FROM completeunitPurchaseOrder, completeunitOrderItem, maintenance "
							+ "WHERE completeunitOrderItem.poNumber = completeunitPurchaseOrder.poNumber "
							+ "AND completeunitOrderItem.PRODUCT = maintenance.PRODUCT "
							+ "AND completeunitPurchaseOrder.orderDate >= STR_TO_DATE(\'"+ startOrderDateTextField.getText() + "\', '%m/%d/%Y') "
							+ "AND completeunitPurchaseOrder.orderDate <= STR_TO_DATE(\'" + endOrderDateTextField.getText() + "\', '%m/%d/%Y') ";
					
					//check is there are vendor condition
					if(!StringUtils.isEmptyOrWhitespaceOnly(selectVendorComboBox.getSelectedItem().toString())){
						sql = sql + "AND completeunitPurchaseOrder.vendor = \'" + selectVendorComboBox.getSelectedItem().toString() + "\' ";
					}
					sql = sql + "ORDER BY completeunitOrderItem.poNumber ASC";
					
					//get the spare part data
					String sql2 = "SELECT sparepartOrderItem.poNumber, "
							+ "sparepartOrderItem.subCode, sparepartOrderItem.PRODUCT, "
							+ "maintenance.DESCRIPTION, sparepartOrderItem.price, "
							+ "sparepartOrderItem.fixedcost, maintenance.DUTY_CODE, "
							+ "maintenance.vendor "
							+ "FROM sparepartPurchaseOrder, sparepartOrderItem, maintenance "
							+ "WHERE sparepartOrderItem.poNumber = sparepartPurchaseOrder.poNumber "
							+ "AND sparepartOrderItem.PRODUCT = maintenance.PRODUCT "
							+ "AND sparepartPurchaseOrder.orderDate >= STR_TO_DATE(\'"+ startOrderDateTextField.getText() + "\', '%m/%d/%Y') "
							+ "AND sparepartPurchaseOrder.orderDate <= STR_TO_DATE(\'" + endOrderDateTextField.getText() + "\', '%m/%d/%Y') ";
					
					//check is there are vendor condition
					if(!StringUtils.isEmptyOrWhitespaceOnly(selectVendorComboBox.getSelectedItem().toString())){
						sql2 = sql2 + "AND sparepartPurchaseOrder.vendor = \'" + selectVendorComboBox.getSelectedItem().toString() + "\' ";
					}
					sql2 = sql2 + "ORDER BY sparepartOrderItem.poNumber ASC";
					
					IRBS irbs = new IRBS();
					data = new ArrayList<DataReportDateVendor>();
					data2 = new ArrayList<DataReportDateVendor>();
					data = irbs.reportDateVendor(sql);
					data2 = irbs.reportDateVendor2(sql2);
					data.addAll(data2);
					createReport = new CreateDateVendorReport(data, startOrderDateTextField.getText(), endOrderDateTextField.getText());
					
					startOrderDateTextField.setText(null);
					endOrderDateTextField.setText(null);
					try {
						irbs.getCon().close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"There must be at least one order date",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		reportPanel.add(reportButton, gbc);

	}

	private boolean checkDD(String d){
		String[] dd = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"
				, "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
		String[] mm = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		for(String i : dd){
			for(String j : mm){
				if(d.equals(j + "/" + i)){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkMM(String m){
		String[] mm = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		for(String i : mm){
			if(m.equals(i)){
				return true;
			}
		}
		return false;
	}

	public JScrollPane reportPane(){
		return reportScrollPane;
	}
}


