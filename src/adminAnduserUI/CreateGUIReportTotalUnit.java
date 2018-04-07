package adminAnduserUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

import domain.CreateTotalUnitReport;
import domain.IRBS;
import domain.DataReportTotalUnit;

public class CreateGUIReportTotalUnit {
	private JLabel reportLabel;
	private JComboBox selectCustomerNameComboBox;
	private JTextField startETDTextField;
	private JTextField endETDTextField;

	private JButton reportButton;

	private JPanel errorPanel;
	private JPanel reportPanel;
	private JScrollPane reportScrollPane;

	CreateTotalUnitReport createReport;
	ArrayList<DataReportTotalUnit> data;


	public CreateGUIReportTotalUnit(){
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

		reportLabel = new JLabel("Total Unit Ordered Report");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//gbc.ipadx = 100;
		gbc.gridwidth = 8;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		reportPanel.add(reportLabel, gbc);
		
		reportLabel = new JLabel("Customer Name: ");
		gbc.gridwidth = 4;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipady = 10;
		reportPanel.add(reportLabel, gbc);
		
		IRBS irbs = new IRBS();
		selectCustomerNameComboBox = new JComboBox(irbs.customerNameStatement().toArray());
		selectCustomerNameComboBox.setEditable(false);
		try {
			irbs.getCon().close();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		gbc.gridwidth = 4;
		gbc.gridx = 4;
		gbc.gridy = 1;
		reportPanel.add(selectCustomerNameComboBox, gbc);

		reportLabel = new JLabel("Shipment ETD (MM/DD/YY): ");
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

		startETDTextField = new JTextField("", 3);
		gbc.ipadx = 100;
		gbc.gridx = 5;
		gbc.gridy = 100000;
		startETDTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(checkMM(startETDTextField.getText())){
						startETDTextField.setText(startETDTextField.getText() + "/");
					}
					if(checkDD(startETDTextField.getText())){
						startETDTextField.setText(startETDTextField.getText() + "/");
					}
					reportPanel.revalidate();  
					reportPanel.repaint();
				}
			}
		});
		reportPanel.add(startETDTextField, gbc);

		reportLabel = new JLabel("            To: ");
		gbc.gridx = 6;
		gbc.ipadx = 0;
		gbc.gridy = 100000;
		reportPanel.add(reportLabel, gbc);

		endETDTextField = new JTextField("", 3);
		gbc.ipadx = 100;
		gbc.gridx = 7;
		gbc.gridy = 100000;
		endETDTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(checkMM(endETDTextField.getText())){
						endETDTextField.setText(endETDTextField.getText() + "/");
					}
					if(checkDD(endETDTextField.getText())){
						endETDTextField.setText(endETDTextField.getText() + "/");
					}
					reportPanel.revalidate();  
					reportPanel.repaint();
				}
			}
		});
		reportPanel.add(endETDTextField, gbc);

		reportButton = new JButton("Report");
		gbc.ipadx = 0;
		gbc.gridwidth = 2;
		gbc.gridx = 8;
		gbc.gridy = 1000000;
		reportButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!startETDTextField.getText().equals("") && !endETDTextField.getText().equals("")){
					IRBS irbs = new IRBS();
					String sql = "";
					if(!StringUtils.isEmptyOrWhitespaceOnly(selectCustomerNameComboBox.getSelectedItem().toString())){
						//for shipment quantity
//						sql = "SELECT maintenance.purchaseCode, "
//								+ "completeunitPurchaseOrder.vendor, "
//								+ "SUM(completeunitShipment.quantity) AS quantity "
//								+ "FROM completeunitPurchaseOrder, completeunitShipment, maintenance "
//								+ "WHERE completeunitShipment.poNumber = completeunitPurchaseOrder.poNumber "
//								+ "AND completeunitShipment.PRODUCT = maintenance.PRODUCT "
//								+ "AND completeunitShipment.ETD >= STR_TO_DATE(\'" + startETDTextField.getText() + "\', '%m/%d/%Y') "
//								+ "AND completeunitShipment.ETD <= STR_TO_DATE(\'" + endETDTextField.getText() + "\', '%m/%d/%Y') "
//								+ "AND completeunitPurchaseOrder.customerName = \'" + irbs.customerNameId(selectCustomerNameComboBox.getSelectedItem().toString()) + "\' "
//								+ "GROUP BY completeunitPurchaseOrder.vendor, maintenance.purchaseCode "
//								+ "ORDER BY completeunitPurchaseOrder.vendor, maintenance.purchaseCode";
						
						//for order quantity
						sql = "SELECT maintenance.purchaseCode, "
								+ "completeunitPurchaseOrder.vendor, "
								+ "SUM(completeunitOrderItem.quantity) AS quantity "
								+ "FROM completeunitPurchaseOrder, completeunitOrderItem, completeunitShipment, maintenance "
								+ "WHERE completeunitShipment.poNumber = completeunitPurchaseOrder.poNumber "
								+ "AND completeunitOrderItem.poNumber = completeunitPurchaseOrder.poNumber "
								+ "AND completeunitShipment.PRODUCT = maintenance.PRODUCT "
								+ "AND completeunitShipment.ETD >= STR_TO_DATE(\'" + startETDTextField.getText() + "\', '%m/%d/%Y') "
								+ "AND completeunitShipment.ETD <= STR_TO_DATE(\'" + endETDTextField.getText() + "\', '%m/%d/%Y') "
								+ "AND completeunitPurchaseOrder.customerName = \'" + irbs.customerNameId(selectCustomerNameComboBox.getSelectedItem().toString()) + "\' "
								+ "GROUP BY completeunitPurchaseOrder.vendor, maintenance.purchaseCode "
								+ "ORDER BY completeunitPurchaseOrder.vendor, maintenance.purchaseCode";
					}else{
						//for shipment quantity
//						sql = "SELECT maintenance.purchaseCode, "
//								+ "completeunitPurchaseOrder.vendor, "
//								+ "SUM(completeunitShipment.quantity) AS quantity "
//								+ "FROM completeunitPurchaseOrder, completeunitShipment, maintenance "
//								+ "WHERE completeunitShipment.poNumber = completeunitPurchaseOrder.poNumber "
//								+ "AND completeunitShipment.PRODUCT = maintenance.PRODUCT "
//								+ "AND completeunitShipment.ETD >= STR_TO_DATE(\'" + startETDTextField.getText() + "\', '%m/%d/%Y') "
//								+ "AND completeunitShipment.ETD <= STR_TO_DATE(\'" + endETDTextField.getText() + "\', '%m/%d/%Y') "
//								+ "GROUP BY completeunitPurchaseOrder.vendor, maintenance.purchaseCode "
//								+ "ORDER BY completeunitPurchaseOrder.vendor, maintenance.purchaseCode";
						
						//for order quantity
						sql = "SELECT maintenance.purchaseCode, "
								+ "completeunitPurchaseOrder.vendor, "
								+ "SUM(completeunitOrderItem.quantity) AS quantity "
								+ "FROM completeunitPurchaseOrder, completeunitOrderItem, completeunitShipment, maintenance "
								+ "WHERE completeunitShipment.poNumber = completeunitPurchaseOrder.poNumber "
								+ "AND completeunitOrderItem.poNumber = completeunitPurchaseOrder.poNumber "
								+ "AND completeunitShipment.PRODUCT = maintenance.PRODUCT "
								+ "AND completeunitShipment.ETD >= STR_TO_DATE(\'" + startETDTextField.getText() + "\', '%m/%d/%Y') "
								+ "AND completeunitShipment.ETD <= STR_TO_DATE(\'" + endETDTextField.getText() + "\', '%m/%d/%Y') "
								+ "GROUP BY completeunitPurchaseOrder.vendor, maintenance.purchaseCode "
								+ "ORDER BY completeunitPurchaseOrder.vendor, maintenance.purchaseCode";
					}
					System.out.println(sql);
					data = new ArrayList<DataReportTotalUnit>();
					data = irbs.reportTotalUnit(sql);
					createReport = new CreateTotalUnitReport(data, startETDTextField.getText(), endETDTextField.getText());
					startETDTextField.setText(null);
					endETDTextField.setText(null);
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
							"There must be at least one shipment day",
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
