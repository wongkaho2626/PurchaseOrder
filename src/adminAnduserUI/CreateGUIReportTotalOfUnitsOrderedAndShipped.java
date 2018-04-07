package adminAnduserUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.mysql.jdbc.StringUtils;

import domain.IRBS;

public class CreateGUIReportTotalOfUnitsOrderedAndShipped {
	private JLabel reportLabel;
	private JTextField startDateTextField;
	private JTextField endDateTextField;
	private JTextField partNoTextField;
	private JButton reportButton;

	private JPanel errorPanel;
	private JPanel reportPanel;
	private JScrollPane reportScrollPane;
	
	CreateGUIReportTotalOfUnitsOrderedAndShippedViewResult createReport;

	public CreateGUIReportTotalOfUnitsOrderedAndShipped(){
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
		
		reportLabel = new JLabel("Total of Units Ordered and Shipped");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//gbc.ipadx = 100;
		gbc.gridwidth = 8;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		reportPanel.add(reportLabel, gbc);
		
		reportLabel = new JLabel("Date (MM/DD/YY): ");
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

		startDateTextField = new JTextField("", 3);
		gbc.ipadx = 100;
		gbc.gridx = 5;
		gbc.gridy = 100000;
		startDateTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(checkMM(startDateTextField.getText())){
						startDateTextField.setText(startDateTextField.getText() + "/");
					}
					if(checkDD(startDateTextField.getText())){
						startDateTextField.setText(startDateTextField.getText() + "/");
					}
					reportPanel.revalidate();  
					reportPanel.repaint();
				}
			}
		});
		reportPanel.add(startDateTextField, gbc);

		reportLabel = new JLabel("            To: ");
		gbc.gridx = 6;
		gbc.ipadx = 0;
		gbc.gridy = 100000;
		reportPanel.add(reportLabel, gbc);

		endDateTextField = new JTextField("", 3);
		gbc.ipadx = 100;
		gbc.gridx = 7;
		gbc.gridy = 100000;
		endDateTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(checkMM(endDateTextField.getText())){
						endDateTextField.setText(endDateTextField.getText() + "/");
					}
					if(checkDD(endDateTextField.getText())){
						endDateTextField.setText(endDateTextField.getText() + "/");
					}
					reportPanel.revalidate();  
					reportPanel.repaint();
				}
			}
		});
		reportPanel.add(endDateTextField, gbc);
		
		reportLabel = new JLabel("Part No: ");
		gbc.ipady = 10;
		gbc.ipadx = 50;
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 100001;
		reportPanel.add(reportLabel, gbc);
		
		partNoTextField = new JTextField();
		gbc.gridwidth = 4;
		gbc.gridx = 4;
		gbc.gridy = 100001;
		reportPanel.add(partNoTextField, gbc);

		reportButton = new JButton("Report");
		gbc.ipadx = 0;
		gbc.gridwidth = 2;
		gbc.gridx = 8;
		gbc.gridy = 1000000;
		reportButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkAllInsert()){
					if(checkDateValid() != null) {
						JOptionPane
						.showMessageDialog(
								errorPanel,
								"Error: Date is invalid",
								"Error", JOptionPane.ERROR_MESSAGE);
					}else {
						int orderedQuantity, shippedQuantity;
						ArrayList<String> OrderedsqlList = new ArrayList<String>();
						ArrayList<String> ShippedsqlList = new ArrayList<String>();
						//get the complete unit data with purchase order date
						String completeunitQuantitysql = "SELECT SUM(completeunitOrderItem.quantity) AS quantity "
								+ "FROM completeunitPurchaseOrder, completeunitOrderItem "
								+ "WHERE completeunitOrderItem.poNumber = completeunitPurchaseOrder.poNumber "
								+ "AND completeunitPurchaseOrder.orderDate >= STR_TO_DATE(\'"+ startDateTextField.getText() + "\', '%m/%d/%Y') "
								+ "AND completeunitPurchaseOrder.orderDate <= STR_TO_DATE(\'" + endDateTextField.getText() + "\', '%m/%d/%Y') "
								+ "AND completeunitOrderItem.PRODUCT = \"" + partNoTextField.getText() + "\"";
						OrderedsqlList.add(completeunitQuantitysql);
						
						//get the spare part data with purchase order date
						String sparepartQuantitysql = "SELECT SUM(sparepartOrderItem.quantity) AS quantity "
								+ "FROM sparepartPurchaseOrder, sparepartOrderItem "
								+ "WHERE sparepartOrderItem.poNumber = sparepartPurchaseOrder.poNumber "
								+ "AND sparepartPurchaseOrder.orderDate >= STR_TO_DATE(\'"+ startDateTextField.getText() + "\', '%m/%d/%Y') "
								+ "AND sparepartPurchaseOrder.orderDate <= STR_TO_DATE(\'" + endDateTextField.getText() + "\', '%m/%d/%Y') "
								+ "AND sparepartOrderItem.PRODUCT = \"" + partNoTextField.getText() + "\"";
						OrderedsqlList.add(sparepartQuantitysql);
						
						//get the tooling data with purchase order date
						String toolingQuantitysql = "SELECT SUM(toolingOrderItem.quantity) AS quantity "
								+ "FROM toolingPurchaseOrder, toolingOrderItem "
								+ "WHERE toolingOrderItem.poNumber = toolingPurchaseOrder.poNumber "
								+ "AND toolingPurchaseOrder.orderDate >= STR_TO_DATE(\'"+ startDateTextField.getText() + "\', '%m/%d/%Y') "
								+ "AND toolingPurchaseOrder.orderDate <= STR_TO_DATE(\'" + endDateTextField.getText() + "\', '%m/%d/%Y') "
								+ "AND toolingOrderItem.PRODUCT = \"" + partNoTextField.getText() + "\"";
						OrderedsqlList.add(toolingQuantitysql);
						
						//get the misc data with purchase order date
						String miscQuantitysql = "SELECT SUM(miscOrderItem.quantity) AS quantity "
								+ "FROM miscPurchaseOrder, miscOrderItem "
								+ "WHERE miscOrderItem.poNumber = miscPurchaseOrder.poNumber "
								+ "AND miscPurchaseOrder.orderDate >= STR_TO_DATE(\'"+ startDateTextField.getText() + "\', '%m/%d/%Y') "
								+ "AND miscPurchaseOrder.orderDate <= STR_TO_DATE(\'" + endDateTextField.getText() + "\', '%m/%d/%Y') "
								+ "AND miscOrderItem.PRODUCT = \"" + partNoTextField.getText() + "\"";
						OrderedsqlList.add(miscQuantitysql);
						
						//get the complete unit data with purchase order date
						String completeunitQuantitysql2 = "SELECT SUM(sa.saUnit) AS quantity "
								+ "FROM sa "
								+ "WHERE sa.ETD >= STR_TO_DATE(\'"+ startDateTextField.getText() + "\', '%m/%d/%Y') "
								+ "AND sa.ETD <= STR_TO_DATE(\'" + endDateTextField.getText() + "\', '%m/%d/%Y') "
								+ "AND sa.PRODUCT like \"" + partNoTextField.getText() + "%\"";
						ShippedsqlList.add(completeunitQuantitysql2);
						System.out.println(completeunitQuantitysql2);

						IRBS irbs = new IRBS();
						orderedQuantity = irbs.reportTotalOfUnitsOrderedAndShipped(OrderedsqlList);
						System.out.println(orderedQuantity);
						shippedQuantity = irbs.reportTotalOfUnitsOrderedAndShipped(ShippedsqlList);
						System.out.println(shippedQuantity);
						createReport = new CreateGUIReportTotalOfUnitsOrderedAndShippedViewResult(partNoTextField.getText(), orderedQuantity, shippedQuantity, startDateTextField.getText(), endDateTextField.getText());

						startDateTextField.setText(null);
						endDateTextField.setText(null);
						partNoTextField.setText(null);
						try {
							irbs.getCon().close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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
	
	private boolean checkAllInsert(){
		boolean check = true;
		if(StringUtils.isNullOrEmpty(startDateTextField.getText())){
			check = false;
		}
		if(StringUtils.isNullOrEmpty(endDateTextField.getText())){
			check = false;
		}
		if(StringUtils.isNullOrEmpty(partNoTextField.getText())){
			check = false;
		}
		return check;
	}

	// ========================= check for Complete Unit Insert Panel has insert correct date format
	// ========================= (If correct date format, return null. If incorrect date format, return exception.)
	private Exception checkDateValid(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		Exception exception = null;
		try {
			sdf.setLenient(false);
			java.util.Date date = sdf.parse(startDateTextField.getText());
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			String ChangedDate = startDateTextField.getText().substring(6, 8) + "/" + startDateTextField.getText().substring(0, 2) + "/" + startDateTextField.getText().substring(3, 5);
		} catch (ParseException e1) {
			exception = e1;
		} catch (Exception e1){
			exception = e1;
		}
		try {
			sdf.setLenient(false);
			java.util.Date date = sdf.parse(endDateTextField.getText());
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			String ChangedDate = endDateTextField.getText().substring(6, 8) + "/" + endDateTextField.getText().substring(0, 2) + "/" + endDateTextField.getText().substring(3, 5);
		} catch (ParseException e1) {
			exception = e1;
		} catch (Exception e1){
			exception = e1;
		}
		return exception;
	}

	public JScrollPane reportPane(){
		return reportScrollPane;
	}
}


