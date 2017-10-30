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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.mysql.jdbc.StringUtils;

import domain.CreateSalesReport;
import domain.CreateTotalUnitReport;
import domain.IRBS;
import domain.DataReportSales;
import domain.DataReportTotalUnit; 

public class CreateGUIReportSales {
	private JLabel reportLabel;
	private JComboBox selectCustomerNameComboBox;
	private JTextField startETDTextField;
	private JTextField endETDTextField;
	private JButton reportButton;

	private JPanel errorPanel;
	private JPanel reportPanel;
	private JScrollPane reportScrollPane;
	
	CreateSalesReport createReport;
	ArrayList<DataReportSales> data;

	public CreateGUIReportSales(){
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
		
		reportLabel = new JLabel("Sales Report");
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
		gbc.ipady = 10;
		reportPanel.add(selectCustomerNameComboBox, gbc);
		
		
		reportLabel = new JLabel("Shipment ETD (MM/YY): ");
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
					//Calculate the required month
					int startMonth = Integer.parseInt(startETDTextField.getText().substring(0, 2));
					int endMonth = Integer.parseInt(endETDTextField.getText().substring(0, 2));
					int startYear = Integer.parseInt(startETDTextField.getText().substring(3, 5));
					int endYear = Integer.parseInt(endETDTextField.getText().substring(3, 5));
					//System.out.println(startMonth + ", " + endMonth + ", " + startYear + ", " + endYear);
					int cntmonth = 1;
					String [] monthArray = new String[240];
					monthArray[0] = startMonth + "/" + startYear;
					while(((startMonth != endMonth) || (startYear != endYear)) && cntmonth < 240){
						if(startMonth < 12){
							startMonth++;
						}else{
							startMonth = 1;
							startYear++;
						}
						//System.out.println(startMonth + "/" + startYear);
						monthArray[cntmonth] = startMonth + "/" + startYear;
						//System.out.println(monthArray[cntmonth]);
						cntmonth++;
					}
					//System.out.println(cntmonth);
//					if(cntmonth == 240){
//						JOptionPane
//						.showMessageDialog(
//								errorPanel,
//								"The year is more than 10 years, Please try again!!!",
//								"Error", JOptionPane.ERROR_MESSAGE);
					if(cntmonth > 12){
						JOptionPane
						.showMessageDialog(
								errorPanel,
								"The insert is more than 1 year, Please try again!!!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}else{
						IRBS irbs = new IRBS();
						String sql = "";
						if(!StringUtils.isEmptyOrWhitespaceOnly(selectCustomerNameComboBox.getSelectedItem().toString())){
							sql = "SELECT maintenance.purchaseCode, completeunitPurchaseOrder.vendor, "
									+ "MONTH(completeunitShipment.ETD) AS OrderMonth, "
									+ "DATE_FORMAT(completeunitShipment.ETD, '%y') AS OrderYear, "
									+ "SUM((ROUND(completeunitShipment.quantity * completeunitShipment.price))) AS MonthSales, "
									+ "completeunitPurchaseOrder.customerName "
									+ "FROM completeunitPurchaseOrder, completeunitShipment, maintenance "
									+ "WHERE completeunitShipment.poNumber = completeunitPurchaseOrder.poNumber "
									+ "AND completeunitShipment.ETD >= STR_TO_DATE(\'"+ startETDTextField.getText() + "\', '%m/%Y') AND completeunitShipment.ETD <= STR_TO_DATE(\'" + (Integer.parseInt(endETDTextField.getText().toString().substring(0,2))+1) + endETDTextField.getText().toString().substring(2,5)+ "\', '%m/%Y') "
									+ "AND completeunitPurchaseOrder.customerName = \'" + irbs.customerNameId(selectCustomerNameComboBox.getSelectedItem().toString()) + "\' "
									+ "AND completeunitShipment.PRODUCT = maintenance.PRODUCT "
									+ "GROUP BY completeunitPurchaseOrder.vendor, maintenance.purchaseCode, OrderMonth, OrderYear "
									+ "ORDER BY `completeunitPurchaseOrder`.`vendor` ASC, `maintenance`.`purchaseCode` ASC, `completeunitShipment`.`ETD` ASC";
						}else{
							sql = "SELECT maintenance.purchaseCode, completeunitPurchaseOrder.vendor, "
									+ "MONTH(completeunitShipment.ETD) AS OrderMonth, "
									+ "DATE_FORMAT(completeunitShipment.ETD, '%y') AS OrderYear, "
									+ "SUM((ROUND(completeunitShipment.quantity * completeunitShipment.price))) AS MonthSales, "
									+ "completeunitPurchaseOrder.customerName "
									+ "FROM completeunitPurchaseOrder, completeunitShipment, maintenance "
									+ "WHERE completeunitShipment.poNumber = completeunitPurchaseOrder.poNumber "
									+ "AND completeunitShipment.ETD >= STR_TO_DATE(\'"+ startETDTextField.getText() + "\', '%m/%Y') AND completeunitShipment.ETD <= STR_TO_DATE(\'" + (Integer.parseInt(endETDTextField.getText().toString().substring(0,2))+1) + endETDTextField.getText().toString().substring(2,5)+ "\', '%m/%Y') "
											+ "AND completeunitShipment.PRODUCT = maintenance.PRODUCT "
									+ "GROUP BY completeunitPurchaseOrder.vendor, maintenance.purchaseCode, OrderMonth, OrderYear "
									+ "ORDER BY `completeunitPurchaseOrder`.`vendor` ASC, `maintenance`.`purchaseCode` ASC, `completeunitShipment`.`ETD` ASC";
						}
						System.out.println(sql);
						data = new ArrayList<DataReportSales>();
						data = irbs.reportSales(sql);
						createReport = new CreateSalesReport(data, startETDTextField.getText(), endETDTextField.getText(), monthArray, cntmonth);
						startETDTextField.setText(null);
						endETDTextField.setText(null);
						try {
							irbs.getCon().close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					//System.out.println(cntmonth);
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


