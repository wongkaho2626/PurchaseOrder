package viewerUI;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.mysql.jdbc.StringUtils;

import domain.CustomerNameItem;
import domain.IRBS;

public class CreateGUIViewerPOView {
	private JScrollPane poViewScrollPane;
	private JPanel poViewPanel;
	private JFrame theFrame;

	private JTable maintenanceTable;

	//purchase order view panel
	private JLabel poViewLabel;
	private JTextField poViewNoTextField;
	private JTextField poViewStartOrderDateTextField;
	private JTextField poViewEndOrderDateTextField;
	private JTextField poViewVendorTextField;
	private JTextField poViewProductNoTextField;
	private JTextField poViewShipmentStartETDTextField;
	private JTextField poViewShipmentEndETDTextField;
	private JComboBox selectPOComboBox;
	private JComboBox selectCustomerNameComboBox;
	private String[] selectPO = {"Complete Unit", "Spare Part", "Tooling", "Misc"};
	private JButton poViewSearchButton;
	private JTable poTable;

	public CreateGUIViewerPOView(){
		initUI();
	}

	private void initUI() {
		setUP();
	}

	private void setUP() {
		poViewPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		poViewScrollPane = new JScrollPane(poViewPanel);
		
		// ************************* poViewPanel UI SetUP
		// *************************
		// ========================= poViewPanel Level One UI Create
		// =========================
		poViewLabel = new JLabel("Search for Purchase Order: ");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 10, 5, 10);
		poViewPanel.add(poViewLabel, gbc);

		poViewLabel = new JLabel("Purchase Order number: ");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 1;
		poViewPanel.add(poViewLabel, gbc);

		poViewNoTextField = new JTextField();
		gbc.gridwidth = 4;
		gbc.gridx = 1;
		gbc.gridy = 1;
		poViewPanel.add(poViewNoTextField, gbc);

		poViewLabel = new JLabel("Order Date (MM/DD/YY): ");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		poViewPanel.add(poViewLabel, gbc);

		poViewLabel = new JLabel("From: ");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 2;
		poViewPanel.add(poViewLabel, gbc);

		poViewStartOrderDateTextField = new JTextField();
		gbc.gridwidth = 3;
		gbc.gridx = 2;
		gbc.gridy = 2;
		poViewStartOrderDateTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(checkMM(poViewStartOrderDateTextField.getText())){
						poViewStartOrderDateTextField.setText(poViewStartOrderDateTextField.getText() + "/");
					}
					if(checkDD(poViewStartOrderDateTextField.getText())){
						poViewStartOrderDateTextField.setText(poViewStartOrderDateTextField.getText() + "/");
					}
				}
				poViewPanel.revalidate();  
				poViewPanel.repaint();
			}
		});
		poViewPanel.add(poViewStartOrderDateTextField, gbc);

		poViewLabel = new JLabel("To:");		
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 3;
		poViewPanel.add(poViewLabel, gbc);

		poViewEndOrderDateTextField = new JTextField();
		gbc.gridwidth = 3;
		gbc.gridx = 2;
		gbc.gridy = 3;
		poViewEndOrderDateTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(checkMM(poViewEndOrderDateTextField.getText())){
						poViewEndOrderDateTextField.setText(poViewEndOrderDateTextField.getText() + "/");
					}
					if(checkDD(poViewEndOrderDateTextField.getText())){
						poViewEndOrderDateTextField.setText(poViewEndOrderDateTextField.getText() + "/");
					}
				}
				poViewPanel.revalidate();  
				poViewPanel.repaint();
			}
		});
		poViewPanel.add(poViewEndOrderDateTextField, gbc);

		poViewLabel = new JLabel("Vendor: ");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 4;
		poViewPanel.add(poViewLabel, gbc);

		poViewVendorTextField = new JTextField();
		gbc.gridwidth = 4;
		gbc.gridx = 1;
		gbc.gridy = 4;
		poViewPanel.add(poViewVendorTextField, gbc);

		poViewLabel = new JLabel("Part No: ");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 5;
		poViewPanel.add(poViewLabel, gbc);

		poViewProductNoTextField = new JTextField();
		gbc.gridwidth = 4;
		gbc.gridx = 1;
		gbc.gridy = 5;
		poViewProductNoTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(poViewProductNoTextField.getText().length() == 2 && notInteger(poViewProductNoTextField.getText()) == false){
						poViewProductNoTextField.setText(poViewProductNoTextField.getText() + "-");
					}
				}
				poViewPanel.revalidate();  
				poViewPanel.repaint();
			}
		});
		poViewPanel.add(poViewProductNoTextField, gbc);
		
		poViewLabel = new JLabel("Shipment ETD (MM/DD/YY):            ");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 6;
		poViewPanel.add(poViewLabel, gbc);

		poViewLabel = new JLabel("From: ");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 6;
		poViewPanel.add(poViewLabel, gbc);

		poViewShipmentStartETDTextField = new JTextField();
		gbc.gridwidth = 3;
		gbc.gridx = 2;
		gbc.gridy = 6;
		poViewShipmentStartETDTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(checkMM(poViewShipmentStartETDTextField.getText())){
						poViewShipmentStartETDTextField.setText(poViewShipmentStartETDTextField.getText() + "/");
					}
					if(checkDD(poViewShipmentStartETDTextField.getText())){
						poViewShipmentStartETDTextField.setText(poViewShipmentStartETDTextField.getText() + "/");
					}
				}
				poViewPanel.revalidate();  
				poViewPanel.repaint();
			}
		});
		poViewPanel.add(poViewShipmentStartETDTextField, gbc);

		poViewLabel = new JLabel("To:");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 7;
		poViewPanel.add(poViewLabel, gbc);

		poViewShipmentEndETDTextField = new JTextField();
		gbc.gridwidth = 3;
		gbc.gridx = 2;
		gbc.gridy = 7;
		poViewShipmentEndETDTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(checkMM(poViewShipmentEndETDTextField.getText())){
						poViewShipmentEndETDTextField.setText(poViewShipmentEndETDTextField.getText() + "/");
					}
					if(checkDD(poViewShipmentEndETDTextField.getText())){
						poViewShipmentEndETDTextField.setText(poViewShipmentEndETDTextField.getText() + "/");
					}
				}
				poViewPanel.revalidate();  
				poViewPanel.repaint();
			}
		});
		poViewPanel.add(poViewShipmentEndETDTextField, gbc);
		

		poViewLabel = new JLabel("Purchase Order Type: ");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 8;
		poViewPanel.add(poViewLabel, gbc);
		
		selectPOComboBox = new JComboBox(selectPO);
		selectPOComboBox.setEditable(false);
		selectPOComboBox.setSelectedItem("Complete Unit");
		gbc.gridwidth = 4;
		gbc.gridx = 1;
		gbc.gridy = 8;
		poViewPanel.add(selectPOComboBox, gbc);
		
		poViewLabel = new JLabel("Customer Name: ");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 9;
		poViewPanel.add(poViewLabel, gbc);
		
		IRBS irbs = new IRBS();
		List<String> customerNameList = new ArrayList<>();
		customerNameList.addAll(irbs.customerNameStatement());
		selectCustomerNameComboBox = new JComboBox(customerNameList.toArray());
		selectCustomerNameComboBox.setEditable(false);
		selectCustomerNameComboBox.setSelectedItem(customerNameList.get(0));
		try {
			irbs.getCon().close();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		gbc.gridwidth = 4;
		gbc.gridx = 1;
		gbc.gridy = 9;
		poViewPanel.add(selectCustomerNameComboBox, gbc);

		poViewSearchButton = new JButton("Search");
		gbc.gridwidth = 4;
		gbc.gridx = 1;
		gbc.gridy = 14;
		gbc.ipadx = 200;
		poViewSearchButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				IRBS irbs = new IRBS();
				JTable poTable = new JTable(){
					private static final long serialVersionUID = 1L;
					//make the JTable non-editable
					public boolean isCellEditable(int row,int column){
						return false;
					}
				};
				if(notInteger(poViewNoTextField.getText()) && !poViewNoTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null,
							"Purchase Order Number must be integer", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else if(checkPOViewDateValid(poViewStartOrderDateTextField.getText()) == null && !poViewStartOrderDateTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null,
							"Error: Order Date is invalid", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else if(checkPOViewDateValid(poViewEndOrderDateTextField.getText()) == null && !poViewEndOrderDateTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null,
							"Error: Order Date is invalid", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else if(checkPOViewDateValid(poViewShipmentStartETDTextField.getText()) == null && !poViewShipmentStartETDTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null,
							"Error: ETD is invalid", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else if(checkPOViewDateValid(poViewShipmentEndETDTextField.getText()) == null && !poViewShipmentEndETDTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null,
							"Error: ETD is invalid", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else if(selectPOComboBox.getSelectedItem().equals("Spare Part") && !poViewShipmentStartETDTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null,
							"Error: Spare Part Purchase Order does not have any shipment", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else if(selectPOComboBox.getSelectedItem().equals("Spare Part") && !poViewShipmentEndETDTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null,
							"Error: Spare Part Purchase Order does not have any shipment", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else if(selectPOComboBox.getSelectedItem().equals("Tooling") && !poViewShipmentStartETDTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null,
							"Error: Tooling Purchase Order does not have any shipment", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else if(selectPOComboBox.getSelectedItem().equals("Tooling") && !poViewShipmentEndETDTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null,
							"Error: Tooling Purchase Order does not have any shipment", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else if(selectPOComboBox.getSelectedItem().equals("Misc") && !poViewShipmentStartETDTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null,
							"Error: Misc Purchase Order does not have any shipment", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else if(selectPOComboBox.getSelectedItem().equals("Misc") && !poViewShipmentEndETDTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null,
							"Error: Misc Purchase Order does not have any shipment", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else{
					if(selectPOComboBox.getSelectedItem().equals("Complete Unit"))
						poTable.setModel(irbs.viewSearchPO(searchAllPOCompleteUnitSQL()));
					else if(selectPOComboBox.getSelectedItem().equals("Spare Part"))
						poTable.setModel(irbs.viewSearchPO(searchAllPOSparePartSQL()));
					else if (selectPOComboBox.getSelectedItem().equals("Tooling"))
						poTable.setModel(irbs.viewSearchPO(searchAllPOToolingSQL()));
					else if (selectPOComboBox.getSelectedItem().equals("Misc"))
						poTable.setModel(irbs.viewSearchPO(searchAllPOMiscSQL()));

					CreateGUIViewerPOViewTable gui = new CreateGUIViewerPOViewTable(theFrame, poTable);
				}
				try {
					irbs.getCon().close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}
		});
		poViewPanel.add(poViewSearchButton, gbc);
	}

	// ************************* reset the table to show all information
	// *************************
	// ========================= reset purchase order table in Purchase Order Insert Panel
	// ========================= 
	public void repaintToShowAllPO(){
		IRBS irbs = new IRBS();
		poTable.setModel(irbs.viewAllPO());
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ************************* do the checking
	// *************************
	// ========================= check for TextField is integer
	// ========================= (If TextField is integer, return false. If TextField is not integer, return true.)
	public static boolean notInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return true; 
		} catch(NullPointerException e) {
			return true;
		}
		// only got here if we didn't return false
		return false;
	}

	// ========================= check for View Purchase Order Panel has insert correct date format
	// ========================= (If correct date format, return date. If incorrect date format, return null.)
	private String checkPOViewDateValid(String inputDate){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		String sqlDate = null;
		if(!inputDate.equals("")){
			try {
				sdf.setLenient(false);
				java.util.Date date = sdf.parse(inputDate);
				String ChangedDate = inputDate.substring(6, 8) + "/" + inputDate.substring(0, 2) + "/" + inputDate.substring(3, 5);
				sqlDate = inputDate.substring(6, 8) + "/" + inputDate.substring(0, 2) + "/" + inputDate.substring(3, 5);
			} catch (ParseException e1) {
				System.out.println("error");
				sqlDate = null;
			} catch (Exception e1){		
				System.out.println("error");
				sqlDate = null;
			}
			//java.util.Date date = sdf.parse(ChangedDate);
			//java.util.Date date = sdf.parse(inputDate);
			//sqlDate = new java.sql.Date(date.getTime());
			System.out.println("SQLDate: " + sqlDate);
			//System.out.println("ChangedDate: " + ChangedDate);
		}
		return sqlDate;
	}

	// ************************* create the SQL statement
	// *************************
	// ========================= search for complete unit purchase order, order date, order item, shipment
	// ========================= (criteria: purchase order, order date, order item# and ETD)
	private String searchAllPOCompleteUnitSQL(){
		int cnt = 1;
		String sql = ("SELECT DISTINCT completeunitPurchaseOrder.poNumber, completeunitOrderItem.PRODUCT, maintenance.ACRONYM, `option`.description, completeunitPurchaseOrder.vendor, DATE_FORMAT(completeunitPurchaseOrder.orderDate, '%m/%d/%y') AS orderDate FROM completeunitPurchaseOrder, completeunitOrderItem, completeunitShipment, `option`, maintenance WHERE completeunitPurchaseOrder.poNumber = completeunitOrderItem.poNumber AND `option`.id = completeunitPurchaseOrder.customerName AND maintenance.PRODUCT = completeunitOrderItem.PRODUCT ");
		String addsql;
		if(!poViewNoTextField.getText().equals("")){
			if(cnt != 0){
				addsql = " AND";
				sql = sql + addsql;
			}
			addsql = " completeunitPurchaseOrder.poNumber = " + Integer.parseInt(poViewNoTextField.getText());
			sql = sql + addsql;
			cnt++;
		}
		if(!poViewVendorTextField.getText().equals("")){
			if(cnt != 0){
				addsql = " AND";
				sql = sql + addsql;
			}
			addsql = " completeunitPurchaseOrder.vendor like \'%" + poViewVendorTextField.getText() + "%\'";
			sql = sql + addsql;
			cnt++;
		}
		if(!poViewProductNoTextField.getText().equals("")){
			addsql = " AND completeunitOrderItem.PRODUCT = \"" + poViewProductNoTextField.getText() + "\"";
			sql = sql + addsql;
		}
		if(!poViewStartOrderDateTextField.getText().equals("")){
			addsql = " AND completeunitPurchaseOrder.orderDate >= \'" + checkPOViewDateValid(poViewStartOrderDateTextField.getText()) + "\'";
			sql = sql + addsql;
		}
		if(!poViewEndOrderDateTextField.getText().equals("")){
			addsql = " AND completeunitPurchaseOrder.orderDate <= \'" + checkPOViewDateValid(poViewEndOrderDateTextField.getText()) + "\'";
			sql = sql + addsql;
		}
		if(!poViewShipmentStartETDTextField.getText().equals("")){
			addsql = " AND completeunitPurchaseOrder.poNumber = completeunitShipment.poNumber"
					+ " AND completeunitShipment.ETD >= \'" + checkPOViewDateValid(poViewShipmentStartETDTextField.getText()) + "\'";
			sql = sql + addsql;
		}
		if(!poViewShipmentEndETDTextField.getText().equals("")){
			addsql = " AND completeunitPurchaseOrder.poNumber = completeunitShipment.poNumber"
					+ " AND completeunitShipment.ETD <= \'" + checkPOViewDateValid(poViewShipmentEndETDTextField.getText()) + "\'";
			sql = sql + addsql;
		}
		if(!StringUtils.isEmptyOrWhitespaceOnly(selectCustomerNameComboBox.getSelectedItem().toString())){
			addsql = " AND completeunitPurchaseOrder.customerName = \"" + CustomerNameItem.getCustomerNameId(selectCustomerNameComboBox.getSelectedItem().toString()) + "\"";
			sql = sql + addsql;
		}
		addsql = (" ORDER BY `completeunitPurchaseOrder`.`poNumber` ASC");
		sql = sql + addsql;
		System.out.println(sql);
		return sql;
	}

	// ========================= search for complete unit purchase order, order date, order item, shipment
	// ========================= (criteria: purchase order, order date, order item# and ETD)
	private String searchAllPOSparePartSQL(){
		int cnt = 1;
		String sql = ("SELECT DISTINCT sparepartPurchaseOrder.poNumber, sparepartOrderItem.PRODUCT, maintenance.ACRONYM, `option`.description, sparepartPurchaseOrder.vendor, DATE_FORMAT(sparepartPurchaseOrder.orderDate, '%m/%d/%y') AS orderDate FROM sparepartPurchaseOrder, sparepartOrderItem, `option`, maintenance WHERE sparepartPurchaseOrder.poNumber = sparepartOrderItem.poNumber AND `option`.id = sparepartPurchaseOrder.customerName  AND maintenance.PRODUCT = sparepartOrderItem.PRODUCT ");
		String addsql;
		if(!poViewNoTextField.getText().equals("")){
			if(cnt != 0){
				addsql = " AND";
				sql = sql + addsql;
			}
			addsql = " sparepartPurchaseOrder.poNumber = " + Integer.parseInt(poViewNoTextField.getText());
			sql = sql + addsql;
			cnt++;
		}
		if(!poViewVendorTextField.getText().equals("")){
			if(cnt != 0){
				addsql = " AND";
				sql = sql + addsql;
			}
			addsql = " sparepartPurchaseOrder.vendor = \'" + poViewVendorTextField.getText() + "\'";
			sql = sql + addsql;
			cnt++;
		}
		if(!poViewProductNoTextField.getText().equals("")){
			addsql = " AND sparepartOrderItem.PRODUCT = \"" + poViewProductNoTextField.getText() + "\"";
			sql = sql + addsql;
		}
		if(!poViewStartOrderDateTextField.getText().equals("")){
			addsql = " AND sparepartPurchaseOrder.orderDate >= \'" + checkPOViewDateValid(poViewStartOrderDateTextField.getText()) + "\'";
			sql = sql + addsql;
		}
		if(!poViewEndOrderDateTextField.getText().equals("")){
			addsql = " AND sparepartPurchaseOrder.orderDate <= \'" + checkPOViewDateValid(poViewEndOrderDateTextField.getText()) + "\'";
			sql = sql + addsql;
		}
		if(!StringUtils.isEmptyOrWhitespaceOnly(selectCustomerNameComboBox.getSelectedItem().toString())){
			addsql = " AND sparepartPurchaseOrder.customerName = \"" + CustomerNameItem.getCustomerNameId(selectCustomerNameComboBox.getSelectedItem().toString()) + "\"";
			sql = sql + addsql;
		}
		addsql = (" ORDER BY `sparepartPurchaseOrder`.`poNumber` ASC");
		sql = sql + addsql;
		System.out.println(sql);
		return sql;
	}
	
	// ************************* create the SQL statement
	// *************************
	// ========================= search for tooling purchase order, order date, order item
	// ========================= (criteria: purchase order, order date, order item#)
	private String searchAllPOToolingSQL(){
		int cnt = 1;
		String sql = ("SELECT DISTINCT toolingPurchaseOrder.poNumber, `option`.description, toolingPurchaseOrder.vendor, DATE_FORMAT(toolingPurchaseOrder.orderDate, '%m/%d/%y') AS orderDate FROM toolingPurchaseOrder, toolingOrderItem, `option` WHERE toolingPurchaseOrder.poNumber = toolingOrderItem.poNumber AND `option`.id = toolingPurchaseOrder.customerName ");
		String addsql;
		if(!poViewNoTextField.getText().equals("")){
			if(cnt != 0){
				addsql = " AND";
				sql = sql + addsql;
			}
			addsql = " toolingPurchaseOrder.poNumber = " + Integer.parseInt(poViewNoTextField.getText());
			sql = sql + addsql;
			cnt++;
		}
		if(!poViewVendorTextField.getText().equals("")){
			if(cnt != 0){
				addsql = " AND";
				sql = sql + addsql;
			}
			addsql = " toolingPurchaseOrder.vendor = \'" + poViewVendorTextField.getText() + "\'";
			sql = sql + addsql;
			cnt++;
		}
		if(!poViewProductNoTextField.getText().equals("")){
			addsql = " AND toolingOrderItem.PRODUCT = \'" + poViewProductNoTextField.getText() + "\'";
			sql = sql + addsql;
		}
		if(!poViewStartOrderDateTextField.getText().equals("")){
			addsql = " AND toolingPurchaseOrder.orderDate >= \'" + checkPOViewDateValid(poViewStartOrderDateTextField.getText()) + "\'";
			sql = sql + addsql;
		}
		if(!poViewEndOrderDateTextField.getText().equals("")){
			addsql = " AND toolingPurchaseOrder.orderDate <= \'" + checkPOViewDateValid(poViewEndOrderDateTextField.getText()) + "\'";
			sql = sql + addsql;
		}
		if(!StringUtils.isEmptyOrWhitespaceOnly(selectCustomerNameComboBox.getSelectedItem().toString())){
			addsql = " AND toolingPurchaseOrder.customerName = \"" + CustomerNameItem.getCustomerNameId(selectCustomerNameComboBox.getSelectedItem().toString()) + "\"";
			sql = sql + addsql;
		}
		addsql = (" ORDER BY `toolingPurchaseOrder`.`poNumber` ASC");
		sql = sql + addsql;
		System.out.println(sql);
		return sql;
	}
	
	// ************************* create the SQL statement
	// *************************
	// ========================= search for misc purchase order, order date, order item
	// ========================= (criteria: purchase order, order date, order item#)
	private String searchAllPOMiscSQL(){
		int cnt = 1;
		String sql = ("SELECT DISTINCT miscPurchaseOrder.poNumber, `option`.description, miscPurchaseOrder.vendor, DATE_FORMAT(miscPurchaseOrder.orderDate, '%m/%d/%y') AS orderDate FROM miscPurchaseOrder, miscOrderItem, `option` WHERE miscPurchaseOrder.poNumber = miscOrderItem.poNumber AND `option`.id = miscPurchaseOrder.customerName ");
		String addsql;
		if(!poViewNoTextField.getText().equals("")){
			if(cnt != 0){
				addsql = " AND";
				sql = sql + addsql;
			}
			addsql = " miscPurchaseOrder.poNumber = " + Integer.parseInt(poViewNoTextField.getText());
			sql = sql + addsql;
			cnt++;
		}
		if(!poViewVendorTextField.getText().equals("")){
			if(cnt != 0){
				addsql = " AND";
				sql = sql + addsql;
			}
			addsql = " miscPurchaseOrder.vendor = \'" + poViewVendorTextField.getText() + "\'";
			sql = sql + addsql;
			cnt++;
		}
		if(!poViewProductNoTextField.getText().equals("")){
			addsql = " AND miscOrderItem.PRODUCT = \"" + poViewProductNoTextField.getText() + "\"";
			sql = sql + addsql;
		}
		if(!poViewStartOrderDateTextField.getText().equals("")){
			addsql = " AND miscPurchaseOrder.orderDate >= \'" + checkPOViewDateValid(poViewStartOrderDateTextField.getText()) + "\'";
			sql = sql + addsql;
		}
		if(!poViewEndOrderDateTextField.getText().equals("")){
			addsql = " AND miscPurchaseOrder.orderDate <= \'" + checkPOViewDateValid(poViewEndOrderDateTextField.getText()) + "\'";
			sql = sql + addsql;
		}
		if(!StringUtils.isEmptyOrWhitespaceOnly(selectCustomerNameComboBox.getSelectedItem().toString())){
			addsql = " AND miscPurchaseOrder.customerName = \"" + CustomerNameItem.getCustomerNameId(selectCustomerNameComboBox.getSelectedItem().toString()) + "\"";
			sql = sql + addsql;
		}
		addsql = (" ORDER BY `miscPurchaseOrder`.`poNumber` ASC");
		sql = sql + addsql;
		System.out.println(sql);
		return sql;
	}

	// ************************* reset all the TextField to null
	// *************************
	// ========================= reset all the TextField in Purchase Order View Panel
	// ========================= 
	public void poViewReset(){
		poViewNoTextField.setText(null);
		poViewProductNoTextField.setText(null);
		poViewVendorTextField.setText(null);
		poViewStartOrderDateTextField.setText(null);
		poViewEndOrderDateTextField.setText(null);
		poViewShipmentStartETDTextField.setText(null);
		poViewShipmentEndETDTextField.setText(null);
		selectPOComboBox.setSelectedItem("Complete Unit");
		
	}

	// ========================= reset maintenance table in Maintenance Insert Panel
	// ========================= 
	private void repaintToShowAllMaintenance(){
		IRBS irbs = new IRBS();
		maintenanceTable.setModel(irbs.viewAllMaintenance());
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JScrollPane viewPane(){
		return poViewScrollPane;
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
}
