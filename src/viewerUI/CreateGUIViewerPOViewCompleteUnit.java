package viewerUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mysql.jdbc.StringUtils;

import domain.IRBS;
import domain.DataSA;
import domain.DataCompleteunitOrderItem;
import domain.DataCompleteunitShipment;

public class CreateGUIViewerPOViewCompleteUnit {
	private JScrollPane poInsertCompleteUnitScrollPane;
	private JPanel poInsertCompleteUnitPanel;
	private JPanel errorPanel;

	private JLabel poInsertCompleteUnitLabel;
	private JLabel[] poInsertCompleteUnitCountLabel = new JLabel[100];
	private JLabel[] poInsertCompleteUnitProductNoLabel = new JLabel[100];
	private JLabel[] poInsertCompleteUnitProductSubCodeLabel = new JLabel[100];
	private JLabel[] poInsertCompleteUnitProductQuantityLabel = new JLabel[100];
	private JLabel[] poInsertCompleteUnitProductDescriptionLabel = new JLabel[100];
	private JLabel[] poInsertCompleteUnitProductUnitPriceLabel = new JLabel[100];
	private JLabel[] poInsertCompleteUnitProductFixedCostLabel = new JLabel[100];
	private JLabel[] poInsertCompleteUnitProductDutyCodeLabel = new JLabel[100];
	private JLabel[] poInsertCompleteUnitProductPurchaseCodeLabel = new JLabel[100];
	private JLabel[] poInsertCompleteUnitProductDepositLabel = new JLabel[100];

	private JLabel[] poInsertCompleteUnitSANoLabel = new JLabel[1000];
	private JLabel[] poInsertCompleteUnitSAUnitLabel = new JLabel[1000];
	private JLabel[] poInsertCompleteUnitSAInvoiceNoLabel = new JLabel[1000];
	private JLabel[] poInsertCompleteUnitSARemainLabel = new JLabel[1000];
	private JLabel[] poInsertCompleteUnitSAETDLabel = new JLabel[1000];
	
	private JSeparator[] poJSeparator = new JSeparator[100];

	private JTextField poInsertCompleteUnitNoTextField;
	private JTextField poInsertCompleteUnitVendorTextField;
	private JTextField[] poInsertCompleteUnitOrderDateTextField = new JTextField[100];
	private JTextField[] poInsertCompleteUnitACRONYMTextField = new JTextField[100];
	private JTextField poInsertCompleteUnitTotalQuantityTextField;
	private JTextField poInsertCompleteUnitTotalPriceTextField;
	private JTextField poInsertCompleteUnitDepositTextField;
	
	private JTextField[] poInsertCompleteUnitShipmentQuantityTextField = new JTextField[100];
	private JTextField[] poInsertCompleteUnitShipmentETDTextField = new JTextField[100];

	private JTextField[] poInsertCompleteUnitProductNoTextField = new JTextField[100];
	private JTextField[] poInsertCompleteUnitProductSubCodeTextField = new JTextField[100];
	private JTextField[] poInsertCompleteUnitProductPriceTextField = new JTextField[100];
	private JTextField[] poInsertCompleteUnitProductQuantityTextField = new JTextField[100];
	private JTextField[] poInsertCompleteUnitProductFixedCostTextField = new JTextField[100];
	private JTextField[] poInsertCompleteUnitProductPurchaseCodeTextField = new JTextField[100];
	private JTextField[] poInsertCompleteUnitProductDepositTextField = new JTextField[100];
	private JTextField[] poInsertCompleteUnitDescriptionTextField = new JTextField[100];
	private JTextField[] poInsertCompleteUnitDutyCodeTextField = new JTextField[100];

	private JTextField[][] poInsertCompleteUnitSANoTextField = new JTextField[100][1000];
	private JTextField[][] poInsertCompleteUnitSAUnitTextField = new JTextField[100][1000];
	private JTextField[][] poInsertCompleteUnitSAInvoiceNoTextField = new JTextField[100][1000];
	private JTextField[][] poInsertCompleteUnitSARemainTextField = new JTextField[100][1000];
	private JTextField[][] poInsertCompleteUnitSAETDTextField = new JTextField[100][1000];
	
	private JTextArea poInsertCompleteUnitRemarkTextArea;
	private JComboBox ETDLocation;
	private String[] ETD = {"YANTIAN", "NINGBO", "SHANGHAI", ""};
	private JComboBox customerName;
	
	private JButton[] poInsertCompleteUnitShowSAButton = new JButton[100];
	
	private int cntCompleteUnitOrderDate = 0;
	private int cntCompleteUnitProduct = 0;
	private int cntCompleteUnitShipment = 0;
	
	private int[] cntCompleteUnitSA = new int[1000];

	private int gbccntCompleteUnitOrderDate = 5;
	private int gbccntCompleteUnitShipment = 5;
	private int[] gbccntCompleteUnitProduct = new int[100];
	private int gbccntCompleteUnitSA = 60;
	
	private int totalquantity = 0;
	private String SAremain;
	
	private String poNumber;
	private JFrame theFrame;
	private JDialog d;
	
	private String orderDate;
	private String vendor;
	private String getETDLocation;
	private String remark;
	private String deposit;
	private String getCustomerName;
	private String[] completeunitPurchaseOrderStatement;
	private ArrayList<String> completeunitOrderDateStatement;
	private ArrayList<DataCompleteunitOrderItem> completeunitOrderItemStatement;
	private ArrayList<DataCompleteunitShipment> completeunitShipmentStatement;
	private ArrayList<ArrayList<DataSA>> SAStatement = new ArrayList<ArrayList<DataSA>>();

	public CreateGUIViewerPOViewCompleteUnit(JFrame theFrame, int poNumber){
		this.poNumber = Integer.toString(poNumber);
		this.theFrame = theFrame;
		initUI();
	}

	private void initUI() {
		createDialog();
		storeData();
		setUP();
		for(int i = 0; i < completeunitOrderItemStatement.size(); i++)
			setMaintenance(i);
		setQuantity();
		setPrice();
		for(int i = 0; i < completeunitOrderItemStatement.size(); i++){
			for(int j = 0; j <= cntCompleteUnitSA[i]; j++){
				setSARemain(i, j);
			}
		}
		usePOPanel();
	}
	
	private void createDialog() {
		d = new JDialog(theFrame, "View the Complete Part Purchase Order", true);
		d.setSize(1250,800);
		d.setLocationRelativeTo(null);
		d.setResizable(true);
	}
	
	private void storeData(){
		cntCompleteUnitOrderDate = 0;
		cntCompleteUnitProduct = 0;
		gbccntCompleteUnitOrderDate = 5;
		for(int i = 0 ; i < 100; i++){
			gbccntCompleteUnitProduct[i] = 10000 * i + 10000;
			cntCompleteUnitSA[i] = -1;
		}
		gbccntCompleteUnitSA = 205;
		totalquantity = 0;
		
		IRBS irbs = new IRBS();
		
		completeunitPurchaseOrderStatement = irbs.completeunitPurchaseOrderStatement(Integer.parseInt(poNumber));
		orderDate = completeunitPurchaseOrderStatement[0];
		vendor = completeunitPurchaseOrderStatement[1];
		getETDLocation = completeunitPurchaseOrderStatement[2];
		remark = completeunitPurchaseOrderStatement[3];
		deposit = completeunitPurchaseOrderStatement[4];
		getCustomerName = completeunitPurchaseOrderStatement[5];
		
		completeunitOrderDateStatement = irbs.completeunitOrderDateStatement(Integer.parseInt(poNumber));
		cntCompleteUnitOrderDate = cntCompleteUnitOrderDate + completeunitOrderDateStatement.size();
		gbccntCompleteUnitOrderDate = gbccntCompleteUnitOrderDate + completeunitOrderDateStatement.size();
		
		completeunitShipmentStatement = irbs.completeunitShipmentStatement(Integer.parseInt(poNumber));
		cntCompleteUnitShipment = cntCompleteUnitShipment + completeunitShipmentStatement.size() - 1;
		gbccntCompleteUnitShipment = gbccntCompleteUnitShipment + completeunitShipmentStatement.size();
		
		completeunitOrderItemStatement = irbs.completeunitOrderItemStatement(Integer.parseInt(poNumber));
		cntCompleteUnitProduct = cntCompleteUnitProduct + completeunitOrderItemStatement.size() - 1;
		//gbccntCompleteUnitProduct = gbccntCompleteUnitProduct + completeunitOrderItemStatement.size() - 1;
		
		for(int i = 0; i < completeunitOrderItemStatement.size(); i++){
			String PRODUCT = completeunitOrderItemStatement.get(i).getPRODUCT() + completeunitOrderItemStatement.get(i).getSubCode();
			SAStatement.add(irbs.SAStatement(Integer.parseInt(poNumber), PRODUCT));
			cntCompleteUnitSA[i] = SAStatement.get(i).size() - 1;
		}
		
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		SAStatement = irbs.SAStatement(Integer.parseInt(poNumber));
//		cntCompleteUnitSA = cntCompleteUnitSA + SAStatement.size();
//		gbccntCompleteUnitSA = gbccntCompleteUnitSA + SAStatement.size() - 1;
	}

	private void setUP() {
		poInsertCompleteUnitPanel = new JPanel(new GridBagLayout());
		poInsertCompleteUnitScrollPane = new JScrollPane(poInsertCompleteUnitPanel);
		GridBagConstraints gbc = new GridBagConstraints();

		// ************************* poPanel UI SetUP
		// *************************
		// ========================= poPanel Level One UI Create
		// =========================
		IRBS irbs = new IRBS();
		poInsertCompleteUnitLabel = new JLabel(irbs.customerName(getCustomerName));
		try {
			irbs.getCon().close();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		gbc.weightx = 0;
		gbc.gridwidth = 9;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.insets = new Insets(5, 5, 5, 5);
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		gbc.gridwidth = 10;
		gbc.gridx = 0;
		gbc.gridy = 1;
		poInsertCompleteUnitPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
//		poInsertCompleteUnitLabel = new JLabel(getCustomerName);
//		gbc.gridwidth = 1;
//		gbc.ipady = 10;
//		gbc.gridx = 0;
//		gbc.gridy = 1;
//		gbc.weightx = 1;
//		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);

		poInsertCompleteUnitLabel = new JLabel("P.O.");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		poInsertCompleteUnitLabel = new JLabel("Order Date");
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 2;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		poInsertCompleteUnitLabel = new JLabel("Vendor");
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		poInsertCompleteUnitLabel = new JLabel("ACRONYM");
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy = 2;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		poInsertCompleteUnitLabel = new JLabel("Total Quantity");
		gbc.gridwidth = 1;
		gbc.gridx = 4;
		gbc.gridy = 2;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		poInsertCompleteUnitLabel = new JLabel("Total Price");
		gbc.gridwidth = 1;
		gbc.gridx = 5;
		gbc.gridy = 2;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		poInsertCompleteUnitLabel = new JLabel("Deposit");
		gbc.gridwidth = 1;
		gbc.gridx = 6;
		gbc.gridy = 2;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		ETDLocation = new JComboBox(ETD);
		ETDLocation.setEditable(true);
		ETDLocation.setEnabled(false);
		ETDLocation.setSelectedItem(getETDLocation);
		gbc.gridwidth = 1;
		gbc.gridx = 9;
		gbc.gridy = 2;
		poInsertCompleteUnitPanel.add(ETDLocation, gbc);
		
		poInsertCompleteUnitLabel = new JLabel("ETD Date");
		gbc.gridwidth = 1;
		gbc.gridx = 8;
		gbc.gridy = 4;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		poInsertCompleteUnitLabel = new JLabel("Quantity");
		gbc.gridwidth = 1;
		gbc.gridx = 9;
		gbc.gridy = 4;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		for(int i = 0; i < completeunitShipmentStatement.size(); i++){
			poInsertCompleteUnitShipmentETDTextField[i] = new JTextField(completeunitShipmentStatement.get(i).getETD());
			poInsertCompleteUnitShipmentETDTextField[i].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 8;
			gbc.gridy = 5 + i;
			poInsertCompleteUnitShipmentETDTextField[i].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {
					for(int i = 0; i < completeunitShipmentStatement.size(); i++){
						if(checkMM(poInsertCompleteUnitShipmentETDTextField[i].getText())){
							poInsertCompleteUnitShipmentETDTextField[i].setText(poInsertCompleteUnitShipmentETDTextField[i].getText() + "/");
						}
						if(checkDD(poInsertCompleteUnitShipmentETDTextField[i].getText())){
							poInsertCompleteUnitShipmentETDTextField[i].setText(poInsertCompleteUnitShipmentETDTextField[i].getText() + "/");
						}
					}
					poInsertCompleteUnitPanel.revalidate();  
					poInsertCompleteUnitPanel.repaint();
				}
			});
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitShipmentETDTextField[i], gbc);
			
			NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
			poInsertCompleteUnitShipmentQuantityTextField[i] = new JTextField(nf.format(Integer.parseInt(completeunitShipmentStatement.get(i).getQuantity())));
			poInsertCompleteUnitShipmentQuantityTextField[i].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 9;
			gbc.gridy = 5 + i;
			poInsertCompleteUnitShipmentQuantityTextField[i].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {
					
					for(int i = 0; i < completeunitShipmentStatement.size(); i++){
						NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
						poInsertCompleteUnitShipmentQuantityTextField[i].setText(nf.format(Integer.parseInt(poInsertCompleteUnitShipmentQuantityTextField[i].getText().replace(",", ""))));
					}
					
					poInsertCompleteUnitPanel.revalidate();  
					poInsertCompleteUnitPanel.repaint();
				}
			});
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitShipmentQuantityTextField[i], gbc);
		}
		
		poInsertCompleteUnitNoTextField = new JTextField(poNumber);
		poInsertCompleteUnitNoTextField.setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 4;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitNoTextField, gbc);

		poInsertCompleteUnitOrderDateTextField[0] = new JTextField(orderDate);
		poInsertCompleteUnitOrderDateTextField[0].setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 4;
		poInsertCompleteUnitOrderDateTextField[0].addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(checkMM(poInsertCompleteUnitOrderDateTextField[0].getText())){
					poInsertCompleteUnitOrderDateTextField[0].setText(poInsertCompleteUnitOrderDateTextField[0].getText() + "/");
				}
				if(checkDD(poInsertCompleteUnitOrderDateTextField[0].getText())){
					poInsertCompleteUnitOrderDateTextField[0].setText(poInsertCompleteUnitOrderDateTextField[0].getText() + "/");
				}
				poInsertCompleteUnitPanel.revalidate();  
				poInsertCompleteUnitPanel.repaint();
			}
		});
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitOrderDateTextField[0], gbc);
		
		for(int i = 1; i <= completeunitOrderDateStatement.size(); i++){
			poInsertCompleteUnitOrderDateTextField[i] = new JTextField(completeunitOrderDateStatement.get(i-1));
			poInsertCompleteUnitOrderDateTextField[i].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 1;
			gbc.gridy = 4 + i;
			poInsertCompleteUnitOrderDateTextField[i].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}
	
				@Override
				public void keyPressed(KeyEvent e) {}
	
				@Override
				public void keyReleased(KeyEvent e) {
					for(int i = 1; i <= completeunitOrderDateStatement.size(); i++){
						if(checkMM(poInsertCompleteUnitOrderDateTextField[i].getText())){
							poInsertCompleteUnitOrderDateTextField[i].setText(poInsertCompleteUnitOrderDateTextField[i].getText() + "/");
						}
						if(checkDD(poInsertCompleteUnitOrderDateTextField[i].getText())){
							poInsertCompleteUnitOrderDateTextField[i].setText(poInsertCompleteUnitOrderDateTextField[i].getText() + "/");
						}
						poInsertCompleteUnitPanel.revalidate();  
						poInsertCompleteUnitPanel.repaint();
					}
				}
			});
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitOrderDateTextField[i], gbc);
		}

		poInsertCompleteUnitVendorTextField = new JTextField(vendor);
		poInsertCompleteUnitVendorTextField.setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 4;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitVendorTextField, gbc);
		
		poInsertCompleteUnitACRONYMTextField[0] = new JTextField();
		poInsertCompleteUnitACRONYMTextField[0].setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy = 4;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitACRONYMTextField[0], gbc);
		
		poInsertCompleteUnitTotalQuantityTextField = new JTextField("0");
		gbc.gridwidth = 1;
		gbc.gridx = 4;
		gbc.gridy = 4;
		poInsertCompleteUnitTotalQuantityTextField.setEditable(false);
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitTotalQuantityTextField, gbc);
		
		poInsertCompleteUnitTotalPriceTextField = new JTextField("0.00");
		gbc.gridwidth = 1;
		gbc.gridx = 5;
		gbc.gridy = 4;
		poInsertCompleteUnitTotalPriceTextField.setEditable(false);
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitTotalPriceTextField, gbc);
		
		poInsertCompleteUnitDepositTextField = new JTextField(deposit);
		poInsertCompleteUnitDepositTextField.setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 6;
		gbc.gridy = 4;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitDepositTextField, gbc);
		
		gbc.gridwidth = 10;
		gbc.gridx = 0;
		gbc.gridy = 49;
		poInsertCompleteUnitPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
		poInsertCompleteUnitLabel = new JLabel("Remark");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 50;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);

		poInsertCompleteUnitRemarkTextArea = new JTextArea(remark);
		poInsertCompleteUnitRemarkTextArea.setEditable(false);
		gbc.gridwidth = 11;
		gbc.gridx = 0;
		gbc.gridy = 51;
		if(!remark.equals("")){
			poInsertCompleteUnitRemarkTextArea.setBackground(Color.YELLOW);
		}
		poInsertCompleteUnitRemarkTextArea.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(!poInsertCompleteUnitRemarkTextArea.getText().equals("")){
					poInsertCompleteUnitRemarkTextArea.setBackground(Color.YELLOW);
				}else{
					poInsertCompleteUnitRemarkTextArea.setBackground(Color.WHITE);
				}
			}
		});
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitRemarkTextArea, gbc);

		gbc.gridwidth = 10;
		gbc.gridx = 0;
		gbc.gridy = 55;
		poInsertCompleteUnitPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
		//the area of the first part ////////////////////////////////////////////////////////////////////////////////////////////////
		for(int i = 0; i < completeunitOrderItemStatement.size(); i++){
			final int cntlocation = i;
			poInsertCompleteUnitCountLabel[cntlocation] = new JLabel(Integer.toString(cntlocation+1));
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 200 + cntlocation * 1000;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitCountLabel[cntlocation], gbc);

			poInsertCompleteUnitProductNoLabel[cntlocation] = new JLabel("Part No");
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductNoLabel[cntlocation], gbc);

			poInsertCompleteUnitProductSubCodeLabel[cntlocation] = new JLabel("Sub Code");
			gbc.gridwidth = 1;
			gbc.gridx = 1;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductSubCodeLabel[cntlocation], gbc);

			poInsertCompleteUnitProductDescriptionLabel[cntlocation] = new JLabel("Description");
			gbc.gridwidth = 3;
			gbc.gridx = 2;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductDescriptionLabel[cntlocation], gbc);
			
			poInsertCompleteUnitProductQuantityLabel[cntlocation] = new JLabel("Quantity");
			gbc.gridwidth = 1;
			gbc.gridx = 5;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductQuantityLabel[cntlocation], gbc);

			poInsertCompleteUnitProductUnitPriceLabel[cntlocation] = new JLabel("Unit Price");
			gbc.gridwidth = 1;
			gbc.gridx = 6;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductUnitPriceLabel[cntlocation], gbc);
			
			poInsertCompleteUnitProductPurchaseCodeLabel[cntlocation] = new JLabel("Purchase Code");
			gbc.gridwidth = 1;
			gbc.gridx = 7;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductPurchaseCodeLabel[cntlocation], gbc);

			poInsertCompleteUnitProductDutyCodeLabel[cntlocation] = new JLabel("Duty Code");
			gbc.gridwidth = 1;
			gbc.gridx = 8;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductDutyCodeLabel[cntlocation], gbc);
			
			poInsertCompleteUnitProductFixedCostLabel[cntlocation] = new JLabel("Fixed Cost");
			gbc.gridwidth = 1;
			gbc.gridx = 9;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductFixedCostLabel[cntlocation], gbc);

			poInsertCompleteUnitProductDepositLabel[cntlocation] = new JLabel("Deposit");
//			gbc.gridwidth = 1;
//			gbc.gridx = 9;
//			gbc.gridy = 201 + cntlocation * 1000;
//			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductDepositLabel[cntlocation], gbc);

			poInsertCompleteUnitProductNoTextField[cntlocation] = new JTextField(completeunitOrderItemStatement.get(cntlocation).getPRODUCT());
			poInsertCompleteUnitProductNoTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertCompleteUnitProductNoTextField[cntlocation].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {
					IRBS irbs = new IRBS();
					String [] maintenanceStatement = irbs.maintenanceStatement(poInsertCompleteUnitProductNoTextField[cntlocation].getText());
					if(!poInsertCompleteUnitProductNoTextField[cntlocation].getText().equals("")){
						if(cntlocation == 0)
							poInsertCompleteUnitACRONYMTextField[0].setText(maintenanceStatement[0]);
						poInsertCompleteUnitDescriptionTextField[cntlocation].setText(maintenanceStatement[1]);	
						poInsertCompleteUnitDutyCodeTextField[cntlocation].setText(maintenanceStatement[2]);	
						poInsertCompleteUnitProductFixedCostTextField[cntlocation].setText(maintenanceStatement[3]);
						poInsertCompleteUnitProductPurchaseCodeTextField[cntlocation].setText(maintenanceStatement[4]);
					}

//					if(poInsertCompleteUnitProductNoTextField[cntlocation].getText().length() == 2 && notInteger(poInsertCompleteUnitProductNoTextField[cntlocation].getText()) == false){
//						poInsertCompleteUnitProductNoTextField[cntlocation].setText(poInsertCompleteUnitProductNoTextField[cntlocation].getText() + "-");
//					}
					try {
						irbs.getCon().close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					poInsertCompleteUnitPanel.revalidate();  
					poInsertCompleteUnitPanel.repaint();
				}
			});
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductNoTextField[cntlocation], gbc);

			poInsertCompleteUnitProductSubCodeTextField[cntlocation] = new JTextField(completeunitOrderItemStatement.get(cntlocation).getSubCode());
			poInsertCompleteUnitProductSubCodeTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 1;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductSubCodeTextField[cntlocation], gbc);

			poInsertCompleteUnitDescriptionTextField[cntlocation] = new JTextField();
			poInsertCompleteUnitDescriptionTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 3;
			gbc.gridx = 2;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitDescriptionTextField[cntlocation], gbc);
			
			NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
			poInsertCompleteUnitProductQuantityTextField[cntlocation] = new JTextField(nf.format(Integer.parseInt(completeunitOrderItemStatement.get(cntlocation).getQuantity())));
			poInsertCompleteUnitProductQuantityTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 5;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertCompleteUnitProductQuantityTextField[cntlocation].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {

					setQuantity();
					setSARemain(cntlocation, cntCompleteUnitSA[cntlocation]);

					NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
					poInsertCompleteUnitProductQuantityTextField[cntlocation].setText(nf.format(Integer.parseInt(poInsertCompleteUnitProductQuantityTextField[cntlocation].getText().replace(",", ""))));


					poInsertCompleteUnitPanel.revalidate();  
					poInsertCompleteUnitPanel.repaint();
				}
			});
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductQuantityTextField[cntlocation], gbc);

			poInsertCompleteUnitProductPriceTextField[cntlocation] = new JTextField(completeunitOrderItemStatement.get(cntlocation).getPrice());
			poInsertCompleteUnitProductPriceTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 6;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertCompleteUnitProductPriceTextField[cntlocation].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {
					setPrice();

					poInsertCompleteUnitPanel.revalidate();  
					poInsertCompleteUnitPanel.repaint();
				}
			});
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductPriceTextField[cntlocation], gbc);
			
			poInsertCompleteUnitProductPurchaseCodeTextField[cntlocation] = new JTextField(completeunitOrderItemStatement.get(cntlocation).getPurchaseCode());
			poInsertCompleteUnitProductPurchaseCodeTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 7;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductPurchaseCodeTextField[cntlocation], gbc);

			poInsertCompleteUnitDutyCodeTextField[cntlocation] = new JTextField();
			poInsertCompleteUnitDutyCodeTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 8;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitDutyCodeTextField[cntlocation], gbc);

			poInsertCompleteUnitProductFixedCostTextField[cntlocation] = new JTextField(completeunitOrderItemStatement.get(cntlocation).getFixedcost());
			poInsertCompleteUnitProductFixedCostTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 9;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductFixedCostTextField[cntlocation], gbc);

			poInsertCompleteUnitSANoLabel[cntlocation] = new JLabel("SA No.");
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 203 + cntlocation * 1000;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitSANoLabel[cntlocation], gbc);
			
			poInsertCompleteUnitShowSAButton[cntlocation] = new JButton("Show SA");
			gbc.gridwidth = 1;
			gbc.gridx = 1;
			gbc.gridy = 203 + cntlocation * 1000;
			poInsertCompleteUnitShowSAButton[cntlocation].addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					showSA(cntlocation);
				}
			});
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitShowSAButton[cntlocation], gbc);

			poInsertCompleteUnitSAUnitLabel[cntlocation] = new JLabel("Unit Shipped");
			gbc.gridwidth = 1;
			gbc.gridx = 1;
			gbc.gridy = 204 + cntlocation * 1000;
			poInsertCompleteUnitSAUnitLabel[cntlocation].setVisible(false);
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAUnitLabel[cntlocation], gbc);

			poInsertCompleteUnitSAInvoiceNoLabel[cntlocation] = new JLabel("Invoice No");
			gbc.gridwidth = 1;
			gbc.gridx = 2;
			gbc.gridy = 204 + cntlocation * 1000;
			poInsertCompleteUnitSAInvoiceNoLabel[cntlocation].setVisible(false);
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAInvoiceNoLabel[cntlocation], gbc);

			poInsertCompleteUnitSARemainLabel[cntlocation] = new JLabel("Units Remain");
			gbc.gridwidth = 1;
			gbc.gridx = 3;
			gbc.gridy = 204 + cntlocation * 1000;
			poInsertCompleteUnitSARemainLabel[cntlocation].setVisible(false);
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitSARemainLabel[cntlocation], gbc);
			
			poInsertCompleteUnitSAETDLabel[cntlocation] = new JLabel("ETD Date");
			gbc.gridwidth = 1;
			gbc.gridx = 4;
			gbc.gridy = 204 + cntlocation * 1000;
			poInsertCompleteUnitSAETDLabel[cntlocation].setVisible(false);
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAETDLabel[cntlocation], gbc);

			for(int j = 0; j < SAStatement.get(cntlocation).size(); j++){
				final int finalj = j;
				poInsertCompleteUnitSANoTextField[cntlocation][finalj] = new JTextField(SAStatement.get(cntlocation).get(j).getSaNo());
				poInsertCompleteUnitSANoTextField[cntlocation][finalj].setEditable(false);
				poInsertCompleteUnitSANoTextField[cntlocation][finalj].setVisible(false);
				gbc.gridwidth = 1;
				gbc.gridx = 0;
				gbc.gridy = gbccntCompleteUnitSA + finalj + cntlocation * 1000;
				poInsertCompleteUnitPanel.add(poInsertCompleteUnitSANoTextField[cntlocation][finalj], gbc);

				poInsertCompleteUnitSAUnitTextField[cntlocation][finalj] = new JTextField(nf.format(Integer.parseInt(SAStatement.get(cntlocation).get(j).getSaUnit())));
				poInsertCompleteUnitSAUnitTextField[cntlocation][finalj].setEditable(false);
				poInsertCompleteUnitSAUnitTextField[cntlocation][finalj].setVisible(false);
				gbc.gridwidth = 1;
				gbc.gridx = 1;
				gbc.gridy = gbccntCompleteUnitSA + finalj + cntlocation * 1000;
				poInsertCompleteUnitSAUnitTextField[cntlocation][finalj].addKeyListener(new KeyListener(){
					public void keyTyped(KeyEvent e) {}

					public void keyPressed(KeyEvent e) {}

					public void keyReleased(KeyEvent e) {
						setSARemain(cntlocation, finalj);

						for(int i = 0; i <= finalj; i++){
							NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
							poInsertCompleteUnitSAUnitTextField[cntlocation][i].setText(nf.format(Integer.parseInt(poInsertCompleteUnitSAUnitTextField[cntlocation][i].getText().replace(",", ""))));
						}

						poInsertCompleteUnitPanel.revalidate();  
						poInsertCompleteUnitPanel.repaint();
					}

				});
				poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAUnitTextField[cntlocation][finalj], gbc);

				poInsertCompleteUnitSAInvoiceNoTextField[cntlocation][finalj] = new JTextField(SAStatement.get(cntlocation).get(j).getSaInoviceNo());
				poInsertCompleteUnitSAInvoiceNoTextField[cntlocation][finalj].setEditable(false);
				poInsertCompleteUnitSAInvoiceNoTextField[cntlocation][finalj].setVisible(false);
				gbc.gridwidth = 1;
				gbc.gridx = 2;
				gbc.gridy = gbccntCompleteUnitSA + finalj + cntlocation * 1000;
				poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAInvoiceNoTextField[cntlocation][finalj], gbc);

				poInsertCompleteUnitSARemainTextField[cntlocation][finalj] = new JTextField();
				gbc.gridwidth = 1;
				gbc.gridx = 3;
				gbc.gridy = gbccntCompleteUnitSA + finalj + cntlocation * 1000;
				poInsertCompleteUnitSARemainTextField[cntlocation][finalj].setEditable(false);
				poInsertCompleteUnitSARemainTextField[cntlocation][finalj].setVisible(false);
				poInsertCompleteUnitPanel.add(poInsertCompleteUnitSARemainTextField[cntlocation][finalj], gbc);
				
				poInsertCompleteUnitSAETDTextField[cntlocation][finalj] = new JTextField(SAStatement.get(cntlocation).get(j).getETD());
				gbc.gridwidth = 1;
				gbc.gridx = 4;
				gbc.gridy = gbccntCompleteUnitSA + finalj + cntlocation * 1000;
				poInsertCompleteUnitSAETDTextField[cntlocation][finalj].setEditable(false);
				poInsertCompleteUnitSAETDTextField[cntlocation][finalj].setVisible(false);
				poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAETDTextField[cntlocation][finalj], gbc);
			}

			poJSeparator[cntlocation] = new JSeparator(JSeparator.HORIZONTAL);
			gbc.gridwidth = 10;
			gbc.gridx = 0;
			gbc.gridy = 203 + cntlocation * 1000 + 200;
			poInsertCompleteUnitPanel.add(poJSeparator[cntlocation], gbc);

		}
	}

	private boolean checkCompleteUnitAllInsert(){
		boolean check = true;
		if(poInsertCompleteUnitNoTextField.getText().equals("")){
			check = false;
		}
		for(int i = 0; i <= cntCompleteUnitOrderDate; i++){
			if(poInsertCompleteUnitOrderDateTextField[i].getText().equals("")){
				check = false;
			}
		}
		if(poInsertCompleteUnitVendorTextField.getText().equals("")){
			check = false;
		}
		if(poInsertCompleteUnitDepositTextField.getText().equals("")){
			check = false;
		}
		for(int i = 0; i <= cntCompleteUnitProduct; i++){
			if(poInsertCompleteUnitProductNoTextField[i].getText().equals(""))
				check = false;
			if(poInsertCompleteUnitProductQuantityTextField[i].getText().equals(""))
				check = false;
			if(poInsertCompleteUnitProductPriceTextField[i].getText().equals(""))
				check = false;
			if(poInsertCompleteUnitProductFixedCostTextField[i].getText().equals(""))
				check = false;
			if(poInsertCompleteUnitProductPurchaseCodeTextField[i].getText().equals(""))
				check = false;
//			if(poInsertCompleteUnitProductDepositTextField[i].getText().equals(""))
//				check = false;
			for(int j = 0; j <= cntCompleteUnitSA[i]; j++){
				if(poInsertCompleteUnitSANoTextField[i][j].getText().equals(""))
					check = false;
				if(poInsertCompleteUnitSAInvoiceNoTextField[i][j].getText().equals(""))
					check = false;
				if(poInsertCompleteUnitSAUnitTextField[i][j].getText().equals(""))
					check = false;
				if(poInsertCompleteUnitSARemainTextField[i][j].getText().equals(""))
					check = false;
				if(poInsertCompleteUnitSAETDTextField[i][j].getText().equals(""))
					check = false;
			}
		}
		for(int i = 0; i <= cntCompleteUnitShipment; i++){
			if(poInsertCompleteUnitShipmentQuantityTextField[i].getText().equals(""))
				check = false;
			if(poInsertCompleteUnitShipmentETDTextField[i].getText().equals(""))
				check = false;
		}
		return check;
	}

	// ========================= check for Complete Unit Insert Panel has insert correct date format
	// ========================= (If correct date format, return null. If incorrect date format, return exception.)
	private Exception checkCompleteUnitDateValid(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		Exception exception = null;
		for(int i = 0; i <= cntCompleteUnitOrderDate; i++){
			try {
				sdf.setLenient(false);
				java.util.Date date = sdf.parse(poInsertCompleteUnitOrderDateTextField[i].getText());
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				String ChangedDate = poInsertCompleteUnitOrderDateTextField[i].getText().substring(6, 8) + "/" + poInsertCompleteUnitOrderDateTextField[i].getText().substring(0, 2) + "/" + poInsertCompleteUnitOrderDateTextField[i].getText().substring(3, 5);
			} catch (ParseException e1) {
				exception = e1;
			} catch (Exception e1){
			 	exception = e1;
			}
		}
		for(int i = 0; i <= cntCompleteUnitShipment; i++){
			try {
				sdf.setLenient(false);
				java.util.Date ETDDate = sdf.parse(poInsertCompleteUnitShipmentETDTextField[i].getText());
				java.sql.Date sqlETDDate = new java.sql.Date(ETDDate.getTime());
				String ChangedDate = poInsertCompleteUnitShipmentETDTextField[i].getText().substring(6, 8) + "/" + poInsertCompleteUnitShipmentETDTextField[i].getText().substring(0, 2) + "/" + poInsertCompleteUnitShipmentETDTextField[i].getText().substring(3, 5);
			} catch (ParseException e1) {
				exception = e1;
			} catch (Exception e1){
				exception = e1;
			}
		}
		return exception;
	}

	// ========================= reset all the TextField in Purchase Order Insert Panel
	// ========================= 

	public void poInsertReset(){
		for(int i = 1; i <= cntCompleteUnitOrderDate; i++){
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitOrderDateTextField[i]);
		}
		for(int i = 1; i <= cntCompleteUnitProduct; i++){
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitCountLabel[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductNoLabel[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductSubCodeLabel[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductQuantityLabel[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductDescriptionLabel[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductUnitPriceLabel[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductFixedCostLabel[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductDutyCodeLabel[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductPurchaseCodeLabel[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductDepositLabel[i]);
			
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductNoTextField[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductSubCodeTextField[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductQuantityTextField[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductPriceTextField[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductFixedCostTextField[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitDescriptionTextField[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitDutyCodeTextField[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductPurchaseCodeTextField[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductDepositTextField[i]);
			
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitShowSAButton[i]);
			
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSANoLabel[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAUnitLabel[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAInvoiceNoLabel[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSARemainLabel[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAETDLabel[i]);			
			
			poInsertCompleteUnitPanel.remove(poJSeparator[i]);
			
			for(int j = 0; j <= cntCompleteUnitSA[i]; j++){
				poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSANoTextField[i][j]);
				poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAUnitTextField[i][j]);
				poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAInvoiceNoTextField[i][j]);
				poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSARemainTextField[i][j]);
				poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAETDTextField[i][j]);
			}			
			cntCompleteUnitSA[i] = -1;
		}
		for(int i = 1; i <= cntCompleteUnitShipment; i++){
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitShipmentQuantityTextField[i]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitShipmentETDTextField[i]);
		}
		for(int j = 0; j <= cntCompleteUnitSA[0]; j++){
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSANoTextField[0][j]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAUnitTextField[0][j]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAInvoiceNoTextField[0][j]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSARemainTextField[0][j]);
			poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAETDTextField[0][j]);
		}
		cntCompleteUnitSA[0] = -1;

		cntCompleteUnitOrderDate = 0;
		cntCompleteUnitProduct = 0;
		cntCompleteUnitShipment = 0;

		gbccntCompleteUnitSA = 205;
		gbccntCompleteUnitOrderDate = 5;
		gbccntCompleteUnitShipment = 5;
		for(int i = 0 ; i < 100; i++){
			gbccntCompleteUnitProduct[i] = 10000 * i + 10000;
		}

		totalquantity = 0;
		poInsertCompleteUnitNoTextField.setText(null);
		poInsertCompleteUnitOrderDateTextField[0].setText(null);
		poInsertCompleteUnitVendorTextField.setText(null);
		poInsertCompleteUnitACRONYMTextField[0].setText(null);
		poInsertCompleteUnitDutyCodeTextField[0].setText(null);
		poInsertCompleteUnitTotalQuantityTextField.setText("0");
		poInsertCompleteUnitTotalPriceTextField.setText("0.00");
		poInsertCompleteUnitDepositTextField.setText(null);
		ETDLocation.setSelectedItem("YANTIAN");
		poInsertCompleteUnitShipmentQuantityTextField[0].setText(null);
		poInsertCompleteUnitShipmentETDTextField[0].setText(null);
		poInsertCompleteUnitProductNoTextField[0].setText(null);
		poInsertCompleteUnitProductSubCodeTextField[0].setText(null);
		poInsertCompleteUnitProductQuantityTextField[0].setText(null);
		poInsertCompleteUnitDescriptionTextField[0].setText(null);
		poInsertCompleteUnitProductPriceTextField[0].setText(null);
		poInsertCompleteUnitProductFixedCostTextField[0].setText(null);
		poInsertCompleteUnitProductPurchaseCodeTextField[0].setText(null);
		poInsertCompleteUnitProductDepositTextField[0].setText(null);
		poInsertCompleteUnitRemarkTextArea.setText(null);
		poInsertCompleteUnitRemarkTextArea.setBackground(Color.WHITE);

		poInsertCompleteUnitPanel.revalidate();  
		poInsertCompleteUnitPanel.repaint();

	}
	
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
	
	public static boolean notDouble(String s) {
		try { 
			Double.parseDouble(s); 
		} catch(NumberFormatException e) { 
			return true; 
		} catch(NullPointerException e) {
			return true;
		}
		// only got here if we didn't return false
		return false;
	}
	
	public JScrollPane insertCompleteUnitPane(){
		return poInsertCompleteUnitScrollPane;
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
	
	private void setMaintenance(int i){
		IRBS irbs = new IRBS();
		String [] maintenanceStatement;
		maintenanceStatement = irbs.maintenanceStatement(poInsertCompleteUnitProductNoTextField[0].getText());
		poInsertCompleteUnitACRONYMTextField[0].setText(maintenanceStatement[0]);	

		maintenanceStatement = irbs.maintenanceStatement(poInsertCompleteUnitProductNoTextField[i].getText());
		if(!poInsertCompleteUnitProductNoTextField[i].getText().equals("")){
			poInsertCompleteUnitDescriptionTextField[i].setText(maintenanceStatement[1]);	
			poInsertCompleteUnitDutyCodeTextField[i].setText(maintenanceStatement[2]);
		}
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setSARemain(int i, int j){
		NumberFormat nf = new DecimalFormat("###,###,###,###,###");
		int remain = 0;

		for(int h = 0; h <= j; h++){
			if(h == 0){
				if(!poInsertCompleteUnitSAUnitTextField[i][h].getText().equals("") && !notInteger(poInsertCompleteUnitSAUnitTextField[i][h].getText().replace(",", ""))){
					remain = Integer.parseInt(poInsertCompleteUnitProductQuantityTextField[i].getText().replace(",", "")) - Integer.parseInt(poInsertCompleteUnitSAUnitTextField[i][h].getText().replace(",", ""));
					poInsertCompleteUnitSARemainTextField[i][h].setText(nf.format(remain));
					System.out.println("remain: " + nf.format(remain));
				}else{
					poInsertCompleteUnitSARemainTextField[i][h].setText(nf.format(Integer.parseInt(poInsertCompleteUnitProductQuantityTextField[i].getText().replace(",", ""))));
				}
			}else{

				if(!poInsertCompleteUnitSAUnitTextField[i][h].getText().equals("") && !notInteger(poInsertCompleteUnitSAUnitTextField[i][h].getText().replace(",", ""))){
					remain = Integer.parseInt(poInsertCompleteUnitProductQuantityTextField[i].getText().replace(",", ""));
					for( int k = 0; k <= h; k++){
						remain = remain - Integer.parseInt(poInsertCompleteUnitSAUnitTextField[i][k].getText().replace(",", ""));
					}
					poInsertCompleteUnitSARemainTextField[i][h].setText(nf.format(remain));
					System.out.println("remain: " + nf.format(remain));
				}else{
					poInsertCompleteUnitSARemainTextField[i][h].setText(poInsertCompleteUnitSARemainTextField[i-1][h].getText());
				}
			}
		}
		
		if(j == -1){
			SAremain = Integer.toString(totalquantity);
			System.out.println(SAremain);
		}else{
			SAremain = poInsertCompleteUnitSARemainTextField[i][j].getText();
			System.out.println(SAremain);
		}
	}
	
	private void setQuantity(){
		double [] price = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int [] quantity = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		double [] totalprice = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		double tp = 0;

		for( int i = 0; i <= cntCompleteUnitProduct; i++){
			if(!poInsertCompleteUnitProductQuantityTextField[i].getText().equals("") && !notInteger(poInsertCompleteUnitProductQuantityTextField[i].getText().replace(",", "")))
				quantity[i] = Integer.parseInt(poInsertCompleteUnitProductQuantityTextField[i].getText().replace(",", ""));
		}

		totalquantity = quantity[0] + quantity[1] + quantity[2] + quantity[3] + quantity[4] + quantity[5] + quantity[6] + quantity[7] + quantity[8] + quantity[9];

		NumberFormat nf = new DecimalFormat("###,###,###,###,###");
		System.out.println("total quantity: " + nf.format(totalquantity));

		poInsertCompleteUnitTotalQuantityTextField.setText(nf.format(totalquantity));

		for( int i = 0; i <= cntCompleteUnitProduct; i++){
			if(!poInsertCompleteUnitProductQuantityTextField[i].getText().equals("") && !notInteger(poInsertCompleteUnitProductQuantityTextField[i].getText().replace(",", "")) && !poInsertCompleteUnitProductPriceTextField[i].getText().equals("") && !notDouble(poInsertCompleteUnitProductPriceTextField[i].getText())){
				quantity[i] = Integer.parseInt(poInsertCompleteUnitProductQuantityTextField[i].getText().replace(",", ""));
				price[i] = Double.parseDouble(poInsertCompleteUnitProductPriceTextField[i].getText());
				totalprice[i] = quantity[i] * price[i];
			}
		}


		tp = totalprice[0] + totalprice[1] + totalprice[2] + totalprice[3] + totalprice[4] + totalprice[5] + totalprice[6] + totalprice[7] + totalprice[8] + totalprice[9];

		DecimalFormat pf = new DecimalFormat("###,###,###,###,###.00");
		System.out.println("total price: " + pf.format(tp));

		poInsertCompleteUnitTotalPriceTextField.setText(pf.format(tp));
		
	}
	
	private void setPrice(){
		double [] price = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int [] quantity = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		double [] totalprice = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		double tp = 0;

		for( int i = 0; i <= cntCompleteUnitProduct; i++){
			if(!poInsertCompleteUnitProductQuantityTextField[i].getText().equals("") && !notInteger(poInsertCompleteUnitProductQuantityTextField[i].getText().replace(",", "")) && !poInsertCompleteUnitProductPriceTextField[i].getText().equals("") && !notDouble(poInsertCompleteUnitProductPriceTextField[i].getText())){
				quantity[i] = Integer.parseInt(poInsertCompleteUnitProductQuantityTextField[i].getText().replace(",", ""));
				price[i] = Double.parseDouble(poInsertCompleteUnitProductPriceTextField[i].getText());
				totalprice[i] = quantity[i] * price[i];
			}
		}

		tp = totalprice[0] + totalprice[1] + totalprice[2] + totalprice[3] + totalprice[4] + totalprice[5] + totalprice[6] + totalprice[7] + totalprice[8] + totalprice[9];

		DecimalFormat nf = new DecimalFormat("###,###,###,###,###.00");
		System.out.println("total price: " + nf.format(tp));

		poInsertCompleteUnitTotalPriceTextField.setText(nf.format(tp));
	}
	
	private ArrayList<String> shipmentPRODUCT(){
		ArrayList<String> shipmentPRODUCT = new ArrayList<String>();
		for(int i = 0; i <= cntCompleteUnitShipment; i++){
			if(i < cntCompleteUnitProduct)
				shipmentPRODUCT.add(poInsertCompleteUnitProductNoTextField[i].getText().replace(",", ""));
			else{
				shipmentPRODUCT.add(poInsertCompleteUnitProductNoTextField[cntCompleteUnitProduct].getText().replace(",", ""));
			}
		}
		System.out.println(shipmentPRODUCT.size());
		return shipmentPRODUCT;		
	}
	
	private ArrayList<String> shipmentPRICE(){
		ArrayList<String> shipmentPRICE = new ArrayList<String>();
		for(int i = 0; i <= cntCompleteUnitShipment; i++){
			if(i < cntCompleteUnitProduct)
				shipmentPRICE.add(poInsertCompleteUnitProductPriceTextField[i].getText().replace(",", ""));
			else{
				shipmentPRICE.add(poInsertCompleteUnitProductPriceTextField[cntCompleteUnitProduct].getText().replace(",", ""));
			}
		}
		return shipmentPRICE;
	}
	
	private void showSA(int cntlocation) {
		try {
			if(poInsertCompleteUnitShowSAButton[cntlocation].getText().equals("Show SA")) {
				poInsertCompleteUnitShowSAButton[cntlocation].setText("Hide SA");
				poInsertCompleteUnitSAUnitLabel[cntlocation].setVisible(true);
				poInsertCompleteUnitSAInvoiceNoLabel[cntlocation].setVisible(true);
				poInsertCompleteUnitSARemainLabel[cntlocation].setVisible(true);
				poInsertCompleteUnitSAETDLabel[cntlocation].setVisible(true);
				for(int j = 0; j < 1000; j++){
					final int finalj = j;
					poInsertCompleteUnitSANoTextField[cntlocation][finalj].setVisible(true);
					poInsertCompleteUnitSAUnitTextField[cntlocation][finalj].setVisible(true);
					poInsertCompleteUnitSAInvoiceNoTextField[cntlocation][finalj].setVisible(true);
					poInsertCompleteUnitSARemainTextField[cntlocation][finalj].setVisible(true);
					poInsertCompleteUnitSAETDTextField[cntlocation][finalj].setVisible(true);
				}
			}else {
				poInsertCompleteUnitShowSAButton[cntlocation].setText("Show SA");
				poInsertCompleteUnitSAUnitLabel[cntlocation].setVisible(false);
				poInsertCompleteUnitSAInvoiceNoLabel[cntlocation].setVisible(false);
				poInsertCompleteUnitSARemainLabel[cntlocation].setVisible(false);
				poInsertCompleteUnitSAETDLabel[cntlocation].setVisible(false);
				for(int j = 0; j < 1000; j++){
					final int finalj = j;
					poInsertCompleteUnitSANoTextField[cntlocation][finalj].setVisible(false);
					poInsertCompleteUnitSAUnitTextField[cntlocation][finalj].setVisible(false);
					poInsertCompleteUnitSAInvoiceNoTextField[cntlocation][finalj].setVisible(false);
					poInsertCompleteUnitSARemainTextField[cntlocation][finalj].setVisible(false);
					poInsertCompleteUnitSAETDTextField[cntlocation][finalj].setVisible(false);
				}
			}
		}catch(Exception ex) {
			
		}
	}
	
	private void usePOPanel() {
		d.setContentPane(poInsertCompleteUnitScrollPane);
		d.setVisible(true);		
	}
}

