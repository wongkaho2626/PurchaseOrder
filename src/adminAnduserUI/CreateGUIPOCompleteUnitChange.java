package adminAnduserUI;

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
import domain.CustomerNameItem;
import domain.DataCompleteunitOrderItem;
import domain.DataCompleteunitShipment;

public class CreateGUIPOCompleteUnitChange {
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
	
	private JTextArea poInsertCompleteUnitRemarkTextArea;
	private JComboBox ETDLocation;
	private String[] ETD = {"YANTIAN", "NINGBO", "SHANGHAI", ""};
	private JComboBox customerName;
	
	private JButton poInsertCompleteUnitIncreaseOrderDateButton;
	private JButton poInsertCompleteUnitDeleteOrderDateButton;
	
	private JButton poInsertCompleteUnitIncreaseProductButton;
	private JButton poInsertCompleteUnitDeleteProductButton;
	
	private JButton poInsertCompleteUnitIncreaseShipmentButton;
	private JButton poInsertCompleteUnitDeleteShipmentButton;
	
	private JButton[] poInsertCompleteUnitShowSAButton = new JButton[100];
	private JButton[] poInsertCompleteUnitIncreaseSAButton = new JButton[100];
	private JButton[] poInsertCompleteUnitDeleteSAButton = new JButton[100];
	
	private JButton poInsertCompleteUnitProductButton;
	
	private int cntCompleteUnitOrderDate = 0;
	private int cntCompleteUnitProduct = 0;
	private int cntCompleteUnitShipment = -1;
	
	private int[] cntCompleteUnitSA = new int[1000];

	private int gbccntCompleteUnitOrderDate = 5;
	private int gbccntCompleteUnitShipment = 4;
	private int[] gbccntCompleteUnitProduct = new int[100];
	private int gbccntCompleteUnitSA = 60;
	
	private int totalquantity = 0;
	private String SAremain;
	
	private String poNumber;
	private JFrame theFrame;
	private JTable theTable;
	private JDialog d;
	private String searchSQL;
	
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

	public CreateGUIPOCompleteUnitChange(JFrame theFrame, JTable theTable, int poNumber, String searchSQL){
		this.poNumber = Integer.toString(poNumber);
		this.theFrame = theFrame;
		this.theTable = theTable;
		this.searchSQL = searchSQL;
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
		d = new JDialog(theFrame, "Change the purchase order", true);
		d.setSize(1250, 800);
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
		cntCompleteUnitShipment = completeunitShipmentStatement.size() - 1;
		gbccntCompleteUnitShipment = gbccntCompleteUnitShipment + completeunitShipmentStatement.size();
		
		completeunitOrderItemStatement = irbs.completeunitOrderItemStatement(Integer.parseInt(poNumber));
		cntCompleteUnitProduct = cntCompleteUnitProduct + completeunitOrderItemStatement.size() - 1;
		
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
	}

	private void setUP() {
		poInsertCompleteUnitPanel = new JPanel(new GridBagLayout());
		poInsertCompleteUnitScrollPane = new JScrollPane(poInsertCompleteUnitPanel);
		GridBagConstraints gbc = new GridBagConstraints();

		// ************************* poPanel UI SetUP
		// *************************
		// ========================= poPanel Level One UI Create
		// =========================
		poInsertCompleteUnitLabel = new JLabel("Please insert Complete Part Purchase Order");
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
		
		poInsertCompleteUnitLabel = new JLabel("Customer Name");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 5, 0, 5);
		poInsertCompleteUnitLabel.setVerticalAlignment(JLabel.BOTTOM);
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		IRBS irbs = new IRBS();
		customerName = new JComboBox(irbs.customerNameStatement().toArray());
		customerName.setEditable(false);
		if(!StringUtils.isNullOrEmpty(getCustomerName)){
			customerName.setSelectedItem(irbs.customerName(getCustomerName));
		}else{
			customerName.setSelectedItem(irbs.customerNameStatement().get(0));
		}
		try {
			irbs.getCon().close();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		gbc.ipady = 10;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		poInsertCompleteUnitPanel.add(customerName, gbc);
		
		poInsertCompleteUnitLabel = new JLabel("Order Date");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 2;
		poInsertCompleteUnitLabel.setVerticalAlignment(JLabel.BOTTOM);
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		ETDLocation = new JComboBox(ETD);
		ETDLocation.setEditable(true);
		ETDLocation.setSelectedItem(getETDLocation);
		gbc.gridwidth = 1;
		gbc.gridx = 9;
		gbc.gridy = 2;
		poInsertCompleteUnitPanel.add(ETDLocation, gbc);

		poInsertCompleteUnitLabel = new JLabel("P.O.");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		poInsertCompleteUnitLabel = new JLabel("Vendor");
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy = 3;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		poInsertCompleteUnitLabel = new JLabel("ACRONYM");
		gbc.gridwidth = 1;
		gbc.gridx = 4;
		gbc.gridy = 3;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		poInsertCompleteUnitLabel = new JLabel("Total Quantity");
		gbc.gridwidth = 1;
		gbc.gridx = 5;
		gbc.gridy = 3;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		poInsertCompleteUnitLabel = new JLabel("Total Price");
		gbc.gridwidth = 1;
		gbc.gridx = 6;
		gbc.gridy = 3;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		poInsertCompleteUnitLabel = new JLabel("Deposit");
		gbc.gridwidth = 1;
		gbc.gridx = 7;
		gbc.gridy = 3;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
		
		poInsertCompleteUnitIncreaseShipmentButton = new JButton("Increase");
		gbc.gridwidth = 1;
		gbc.gridx = 8;
		gbc.gridy = 3;
		poInsertCompleteUnitIncreaseShipmentButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cntCompleteUnitShipment++;
				gbccntCompleteUnitShipment++;
				
				poInsertCompleteUnitShipmentETDTextField[cntCompleteUnitShipment] = new JTextField();
				gbc.gridwidth = 1;
				gbc.gridx = 8;
				gbc.gridy = gbccntCompleteUnitShipment;
				poInsertCompleteUnitShipmentETDTextField[cntCompleteUnitShipment].addKeyListener(new KeyListener(){
					@Override
					public void keyTyped(KeyEvent e) {}

					@Override
					public void keyPressed(KeyEvent e) {}

					@Override
					public void keyReleased(KeyEvent e) {
						for(int i = 0; i <= cntCompleteUnitShipment; i++){
							if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){  
								if(checkMM(poInsertCompleteUnitShipmentETDTextField[i].getText())){
									poInsertCompleteUnitShipmentETDTextField[i].setText(poInsertCompleteUnitShipmentETDTextField[i].getText() + "/");
								}
								if(checkDD(poInsertCompleteUnitShipmentETDTextField[i].getText())){
									poInsertCompleteUnitShipmentETDTextField[i].setText(poInsertCompleteUnitShipmentETDTextField[i].getText() + "/");
								}
								poInsertCompleteUnitPanel.revalidate();  
								poInsertCompleteUnitPanel.repaint();
							}
						}
					}
				});
				poInsertCompleteUnitPanel.add(poInsertCompleteUnitShipmentETDTextField[cntCompleteUnitShipment], gbc);
				
				poInsertCompleteUnitShipmentQuantityTextField[cntCompleteUnitShipment] = new JTextField();
				gbc.gridwidth = 1;
				gbc.gridx = 9;
				gbc.gridy = gbccntCompleteUnitShipment;
				poInsertCompleteUnitShipmentQuantityTextField[cntCompleteUnitShipment].addKeyListener(new KeyListener(){
					@Override
					public void keyTyped(KeyEvent e) {}

					@Override
					public void keyPressed(KeyEvent e) {}

					@Override
					public void keyReleased(KeyEvent e) {
						
						for(int i = 0; i <= cntCompleteUnitShipment; i++){
							NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
							if(!StringUtils.isEmptyOrWhitespaceOnly(poInsertCompleteUnitShipmentQuantityTextField[i].getText())){
								poInsertCompleteUnitShipmentQuantityTextField[i].setText(nf.format(Integer.parseInt(poInsertCompleteUnitShipmentQuantityTextField[i].getText().replace(",", ""))));
							}
						}

						poInsertCompleteUnitPanel.revalidate();  
						poInsertCompleteUnitPanel.repaint();
					}
				});
				poInsertCompleteUnitPanel.add(poInsertCompleteUnitShipmentQuantityTextField[cntCompleteUnitShipment], gbc);
				
				poInsertCompleteUnitPanel.revalidate();  
				poInsertCompleteUnitPanel.repaint();
			}
		});
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitIncreaseShipmentButton, gbc);
		
		poInsertCompleteUnitDeleteShipmentButton = new JButton("Delete");
		gbc.gridwidth = 1;
		gbc.gridx = 9;
		gbc.gridy = 3;
		poInsertCompleteUnitDeleteShipmentButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(cntCompleteUnitShipment >= 0){
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitShipmentQuantityTextField[cntCompleteUnitShipment]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitShipmentETDTextField[cntCompleteUnitShipment]);
					cntCompleteUnitShipment--;
					gbccntCompleteUnitShipment--;
				}else{
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"Error: There is no shipment in purchase order.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				poInsertCompleteUnitPanel.revalidate();  
				poInsertCompleteUnitPanel.repaint();
			}
			
		});
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitDeleteShipmentButton, gbc);
		
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
						if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){  
							if(checkMM(poInsertCompleteUnitShipmentETDTextField[i].getText())){
								poInsertCompleteUnitShipmentETDTextField[i].setText(poInsertCompleteUnitShipmentETDTextField[i].getText() + "/");
							}
							if(checkDD(poInsertCompleteUnitShipmentETDTextField[i].getText())){
								poInsertCompleteUnitShipmentETDTextField[i].setText(poInsertCompleteUnitShipmentETDTextField[i].getText() + "/");
							}
							poInsertCompleteUnitPanel.revalidate();  
							poInsertCompleteUnitPanel.repaint();
						}
					}
				}
			});
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitShipmentETDTextField[i], gbc);
			
			NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
			poInsertCompleteUnitShipmentQuantityTextField[i] = new JTextField(nf.format(Integer.parseInt(completeunitShipmentStatement.get(i).getQuantity())));
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
						if(!StringUtils.isEmptyOrWhitespaceOnly(poInsertCompleteUnitShipmentQuantityTextField[i].getText())){
							poInsertCompleteUnitShipmentQuantityTextField[i].setText(nf.format(Integer.parseInt(poInsertCompleteUnitShipmentQuantityTextField[i].getText().replace(",", ""))));
						}
					}
					
					poInsertCompleteUnitPanel.revalidate();  
					poInsertCompleteUnitPanel.repaint();
				}
			});
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitShipmentQuantityTextField[i], gbc);
		}
		
		poInsertCompleteUnitIncreaseOrderDateButton = new JButton("Increase");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 3;
		poInsertCompleteUnitIncreaseOrderDateButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cntCompleteUnitOrderDate++;
				if(cntCompleteUnitOrderDate < 20){
					poInsertCompleteUnitOrderDateTextField[cntCompleteUnitOrderDate] = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 1;
					gbc.gridy = gbccntCompleteUnitOrderDate;
					poInsertCompleteUnitOrderDateTextField[cntCompleteUnitOrderDate].addKeyListener(new KeyListener(){
						@Override
						public void keyTyped(KeyEvent e) {}

						@Override
						public void keyPressed(KeyEvent e) {}

						@Override
						public void keyReleased(KeyEvent e) {
							for(int i = 0; i <= cntCompleteUnitOrderDate; i++){
								if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){  
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
						}
					});
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitOrderDateTextField[cntCompleteUnitOrderDate], gbc);
					gbccntCompleteUnitOrderDate++;

					poInsertCompleteUnitPanel.revalidate();  
					poInsertCompleteUnitPanel.repaint();
				}else{
					JOptionPane
					.showMessageDialog(
							poInsertCompleteUnitPanel,
							"The maximum order date is 20.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitIncreaseOrderDateButton, gbc);
		
		poInsertCompleteUnitDeleteOrderDateButton = new JButton("Delete");
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 3;
		poInsertCompleteUnitDeleteOrderDateButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cntCompleteUnitOrderDate > 0){
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitOrderDateTextField[cntCompleteUnitOrderDate]);
					cntCompleteUnitOrderDate--;
					gbccntCompleteUnitOrderDate--;
				}else{
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"Error: There is at least one Order Date in purchase order.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				
				poInsertCompleteUnitPanel.revalidate();  
				poInsertCompleteUnitPanel.repaint();
			}
			
		});
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitDeleteOrderDateButton, gbc);
		
		poInsertCompleteUnitNoTextField = new JTextField(poNumber);
		poInsertCompleteUnitNoTextField.setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 4;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitNoTextField, gbc);

		poInsertCompleteUnitOrderDateTextField[0] = new JTextField(orderDate);
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
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){  
					if(checkMM(poInsertCompleteUnitOrderDateTextField[0].getText())){
						poInsertCompleteUnitOrderDateTextField[0].setText(poInsertCompleteUnitOrderDateTextField[0].getText() + "/");
					}
					if(checkDD(poInsertCompleteUnitOrderDateTextField[0].getText())){
						poInsertCompleteUnitOrderDateTextField[0].setText(poInsertCompleteUnitOrderDateTextField[0].getText() + "/");
					}
					poInsertCompleteUnitPanel.revalidate();  
					poInsertCompleteUnitPanel.repaint();
				}
			}
		});
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitOrderDateTextField[0], gbc);
		
		for(int i = 1; i <= completeunitOrderDateStatement.size(); i++){
			poInsertCompleteUnitOrderDateTextField[i] = new JTextField(completeunitOrderDateStatement.get(i-1));
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
						if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){  
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
				}
			});
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitOrderDateTextField[i], gbc);
		}

		poInsertCompleteUnitVendorTextField = new JTextField(vendor);
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy = 4;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitVendorTextField, gbc);
		
		poInsertCompleteUnitACRONYMTextField[0] = new JTextField();
		poInsertCompleteUnitACRONYMTextField[0].setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 4;
		gbc.gridy = 4;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitACRONYMTextField[0], gbc);
		
		poInsertCompleteUnitTotalQuantityTextField = new JTextField("0");
		gbc.gridwidth = 1;
		gbc.gridx = 5;
		gbc.gridy = 4;
		poInsertCompleteUnitTotalQuantityTextField.setEditable(false);
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitTotalQuantityTextField, gbc);
		
		poInsertCompleteUnitTotalPriceTextField = new JTextField("0.00");
		gbc.gridwidth = 1;
		gbc.gridx = 6;
		gbc.gridy = 4;
		poInsertCompleteUnitTotalPriceTextField.setEditable(false);
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitTotalPriceTextField, gbc);
		
		poInsertCompleteUnitDepositTextField = new JTextField(deposit);
		gbc.gridwidth = 1;
		gbc.gridx = 7;
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
		gbc.gridy = 52;
		poInsertCompleteUnitPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
		//the area of increase / delete the part product
		poInsertCompleteUnitLabel = new JLabel("Part");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 53;
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);

		poInsertCompleteUnitIncreaseProductButton = new JButton("Increase");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 54;
		poInsertCompleteUnitIncreaseProductButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cntCompleteUnitProduct < 100){
					cntCompleteUnitProduct++;
					final int finalcntCompleteUnitProduct = cntCompleteUnitProduct;
					cntCompleteUnitSA[finalcntCompleteUnitProduct] = -1;

					poInsertCompleteUnitCountLabel[finalcntCompleteUnitProduct] = new  JLabel(Integer.toString(cntCompleteUnitProduct+1));
					gbc.gridwidth = 1;
					gbc.gridx = 0;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] - 1;
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitCountLabel[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitProductNoLabel[finalcntCompleteUnitProduct] = new JLabel("Part No");
					gbc.gridwidth = 1;
					gbc.gridx = 0;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct];
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductNoLabel[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitProductSubCodeLabel[finalcntCompleteUnitProduct] = new JLabel("Sub Code");
					gbc.gridwidth = 1;
					gbc.gridx = 1;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct];
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductSubCodeLabel[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitProductDescriptionLabel[finalcntCompleteUnitProduct] = new JLabel("Description");
					gbc.gridwidth = 3;
					gbc.gridx = 2;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct];
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductDescriptionLabel[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitProductQuantityLabel[finalcntCompleteUnitProduct] = new JLabel("Quantity");
					gbc.gridwidth = 1;
					gbc.gridx = 5;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct];
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductQuantityLabel[finalcntCompleteUnitProduct], gbc);
					
					poInsertCompleteUnitProductUnitPriceLabel[finalcntCompleteUnitProduct] = new JLabel("Unit Price");
					gbc.gridwidth = 1;
					gbc.gridx = 6;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct];
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductUnitPriceLabel[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitProductPurchaseCodeLabel[finalcntCompleteUnitProduct] = new JLabel("Purchase Code");
					gbc.gridwidth = 1;
					gbc.gridx = 7;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct];
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductPurchaseCodeLabel[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitProductDutyCodeLabel[finalcntCompleteUnitProduct] = new JLabel("Duty Code");
					gbc.gridwidth = 1;
					gbc.gridx = 8;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct];
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductDutyCodeLabel[finalcntCompleteUnitProduct], gbc);
					
					poInsertCompleteUnitProductFixedCostLabel[finalcntCompleteUnitProduct] = new JLabel("Fixed Cost");
					gbc.gridwidth = 1;
					gbc.gridx = 9;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct];
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductFixedCostLabel[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitProductNoTextField[finalcntCompleteUnitProduct]  = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 0;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 1;
					poInsertCompleteUnitProductNoTextField[finalcntCompleteUnitProduct].addKeyListener(new KeyListener(){
						@Override
						public void keyTyped(KeyEvent e) {}

						@Override
						public void keyPressed(KeyEvent e) {}

						@Override
						public void keyReleased(KeyEvent e) {
							IRBS irbs = new IRBS();
							String [] maintenanceStatement = irbs.maintenanceStatement(poInsertCompleteUnitProductNoTextField[finalcntCompleteUnitProduct].getText());
							if(!poInsertCompleteUnitProductNoTextField[finalcntCompleteUnitProduct].getText().equals("")){
								poInsertCompleteUnitDescriptionTextField[finalcntCompleteUnitProduct].setText(maintenanceStatement[1]);	
								poInsertCompleteUnitDutyCodeTextField[finalcntCompleteUnitProduct].setText(maintenanceStatement[2]);	
								poInsertCompleteUnitProductFixedCostTextField[finalcntCompleteUnitProduct].setText(maintenanceStatement[3]);
								poInsertCompleteUnitProductPurchaseCodeTextField[finalcntCompleteUnitProduct].setText(maintenanceStatement[4]);
							}
							if(poInsertCompleteUnitProductNoTextField[finalcntCompleteUnitProduct].getText().length() == 2 && notInteger(poInsertCompleteUnitProductNoTextField[finalcntCompleteUnitProduct].getText()) == false){
								poInsertCompleteUnitProductNoTextField[finalcntCompleteUnitProduct].setText(poInsertCompleteUnitProductNoTextField[finalcntCompleteUnitProduct].getText() + "-");
							}
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
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductNoTextField[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitProductSubCodeTextField[finalcntCompleteUnitProduct]  = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 1;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 1;
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductSubCodeTextField[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitDescriptionTextField[finalcntCompleteUnitProduct] = new JTextField();
					poInsertCompleteUnitDescriptionTextField[finalcntCompleteUnitProduct].setEditable(false);
					gbc.gridwidth = 3;
					gbc.gridx = 2;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 1;
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitDescriptionTextField[finalcntCompleteUnitProduct], gbc);
					
					poInsertCompleteUnitProductQuantityTextField[finalcntCompleteUnitProduct]  = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 5;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 1;
					poInsertCompleteUnitProductQuantityTextField[finalcntCompleteUnitProduct].addKeyListener(new KeyListener(){
						@Override
						public void keyTyped(KeyEvent e) {}

						@Override
						public void keyPressed(KeyEvent e) {}

						@Override
						public void keyReleased(KeyEvent e) {
							setQuantity();
							setSARemain(finalcntCompleteUnitProduct, cntCompleteUnitSA[finalcntCompleteUnitProduct]);

							NumberFormat nf = new DecimalFormat("###,###,###,###,###");		
							if(!StringUtils.isEmptyOrWhitespaceOnly(poInsertCompleteUnitProductQuantityTextField[finalcntCompleteUnitProduct].getText())){
								poInsertCompleteUnitProductQuantityTextField[finalcntCompleteUnitProduct].setText(nf.format(Integer.parseInt(poInsertCompleteUnitProductQuantityTextField[finalcntCompleteUnitProduct].getText().replace(",", ""))));
							}

							poInsertCompleteUnitPanel.revalidate();  
							poInsertCompleteUnitPanel.repaint();
						}
					});
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductQuantityTextField[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitProductPriceTextField[finalcntCompleteUnitProduct]  = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 6;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 1;
					poInsertCompleteUnitProductPriceTextField[finalcntCompleteUnitProduct].addKeyListener(new KeyListener(){
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
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductPriceTextField[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitProductPurchaseCodeTextField[finalcntCompleteUnitProduct] = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 7;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 1;
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductPurchaseCodeTextField[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitDutyCodeTextField[finalcntCompleteUnitProduct] = new JTextField();
					poInsertCompleteUnitDutyCodeTextField[finalcntCompleteUnitProduct].setEditable(false);
					gbc.gridwidth = 1;
					gbc.gridx = 8;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 1;
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitDutyCodeTextField[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitProductFixedCostTextField[finalcntCompleteUnitProduct] = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 9;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 1;
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductFixedCostTextField[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitSANoLabel[finalcntCompleteUnitProduct] = new JLabel("SA No.");
					gbc.gridwidth = 1;
					gbc.gridx = 0;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 2;
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitSANoLabel[finalcntCompleteUnitProduct], gbc);
					
					poInsertCompleteUnitShowSAButton[finalcntCompleteUnitProduct] = new JButton("Hide SA");
					gbc.gridwidth = 1;
					gbc.gridx = 1;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 2;
					poInsertCompleteUnitShowSAButton[finalcntCompleteUnitProduct].addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							showSA(finalcntCompleteUnitProduct);
						}
					});
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitShowSAButton[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitSAUnitLabel[finalcntCompleteUnitProduct] = new JLabel("Unit Shipped");
					gbc.gridwidth = 1;
					gbc.gridx = 2;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 3;
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAUnitLabel[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitSAInvoiceNoLabel[finalcntCompleteUnitProduct] = new JLabel("Invoice No");
					gbc.gridwidth = 1;
					gbc.gridx = 3;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 3;
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAInvoiceNoLabel[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitSARemainLabel[finalcntCompleteUnitProduct] = new JLabel("Units Remain");
					gbc.gridwidth = 1;
					gbc.gridx = 4;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 3;
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitSARemainLabel[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitIncreaseSAButton[finalcntCompleteUnitProduct] = new JButton("Increase");
					gbc.gridwidth = 1;
					gbc.gridx = 0;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 3;
					cntCompleteUnitSA[finalcntCompleteUnitProduct] = -1;
					poInsertCompleteUnitIncreaseSAButton[finalcntCompleteUnitProduct].addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							cntCompleteUnitSA[finalcntCompleteUnitProduct]++;

							poInsertCompleteUnitSANoTextField[finalcntCompleteUnitProduct][cntCompleteUnitSA[finalcntCompleteUnitProduct]] = new JTextField("SA");
							gbc.gridwidth = 1;
							gbc.gridx = 0;
							gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + cntCompleteUnitSA[finalcntCompleteUnitProduct];
							poInsertCompleteUnitPanel.add(poInsertCompleteUnitSANoTextField[finalcntCompleteUnitProduct][cntCompleteUnitSA[finalcntCompleteUnitProduct]], gbc);
							
							poInsertCompleteUnitSAUnitTextField[finalcntCompleteUnitProduct][cntCompleteUnitSA[finalcntCompleteUnitProduct]] = new JTextField();
							gbc.gridwidth = 1;
							gbc.gridx = 2;
							gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + cntCompleteUnitSA[finalcntCompleteUnitProduct];
							poInsertCompleteUnitSAUnitTextField[finalcntCompleteUnitProduct][cntCompleteUnitSA[finalcntCompleteUnitProduct]].addKeyListener(new KeyListener(){
								public void keyTyped(KeyEvent e) {}

								public void keyPressed(KeyEvent e) {}

								public void keyReleased(KeyEvent e) {
									setSARemain(finalcntCompleteUnitProduct, cntCompleteUnitSA[finalcntCompleteUnitProduct]);

									for(int i = 0; i <= cntCompleteUnitSA[finalcntCompleteUnitProduct]; i++){
										NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
										if(!StringUtils.isEmptyOrWhitespaceOnly(poInsertCompleteUnitSAUnitTextField[finalcntCompleteUnitProduct][i].getText())){
											poInsertCompleteUnitSAUnitTextField[finalcntCompleteUnitProduct][i].setText(nf.format(Integer.parseInt(poInsertCompleteUnitSAUnitTextField[finalcntCompleteUnitProduct][i].getText().replace(",", ""))));
										}
									}

									poInsertCompleteUnitPanel.revalidate();  
									poInsertCompleteUnitPanel.repaint();
								}

							});
							poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAUnitTextField[finalcntCompleteUnitProduct][cntCompleteUnitSA[finalcntCompleteUnitProduct]], gbc);

							poInsertCompleteUnitSAInvoiceNoTextField[finalcntCompleteUnitProduct][cntCompleteUnitSA[finalcntCompleteUnitProduct]] = new JTextField("LR-");
							gbc.gridwidth = 1;
							gbc.gridx = 3;
							gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + cntCompleteUnitSA[finalcntCompleteUnitProduct];
							poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAInvoiceNoTextField[finalcntCompleteUnitProduct][cntCompleteUnitSA[finalcntCompleteUnitProduct]], gbc);

							poInsertCompleteUnitSARemainTextField[finalcntCompleteUnitProduct][cntCompleteUnitSA[finalcntCompleteUnitProduct]] = new JTextField();
							gbc.gridwidth = 1;
							gbc.gridx = 4;
							gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + cntCompleteUnitSA[finalcntCompleteUnitProduct];
							poInsertCompleteUnitSARemainTextField[finalcntCompleteUnitProduct][cntCompleteUnitSA[finalcntCompleteUnitProduct]].setEditable(false);
							poInsertCompleteUnitPanel.add(poInsertCompleteUnitSARemainTextField[finalcntCompleteUnitProduct][cntCompleteUnitSA[finalcntCompleteUnitProduct]], gbc);

							poInsertCompleteUnitPanel.revalidate();  
							poInsertCompleteUnitPanel.repaint();
						}
					});
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitIncreaseSAButton[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitDeleteSAButton[finalcntCompleteUnitProduct] = new JButton("Delete");
					gbc.gridwidth = 1;
					gbc.gridx = 1;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 3;
					poInsertCompleteUnitDeleteSAButton[finalcntCompleteUnitProduct].addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							if(cntCompleteUnitSA[finalcntCompleteUnitProduct] >= 0){
								poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSANoTextField[finalcntCompleteUnitProduct][cntCompleteUnitSA[finalcntCompleteUnitProduct]]);
								poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAUnitTextField[finalcntCompleteUnitProduct][cntCompleteUnitSA[finalcntCompleteUnitProduct]]);
								poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAInvoiceNoTextField[finalcntCompleteUnitProduct][cntCompleteUnitSA[finalcntCompleteUnitProduct]]);
								poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSARemainTextField[finalcntCompleteUnitProduct][cntCompleteUnitSA[finalcntCompleteUnitProduct]]);
								cntCompleteUnitSA[finalcntCompleteUnitProduct]--;
							}else{
								JOptionPane
								.showMessageDialog(
										errorPanel,
										"Error: There is no SA in purchase order.",
										"Error", JOptionPane.ERROR_MESSAGE);
							}
							poInsertCompleteUnitPanel.revalidate();  
							poInsertCompleteUnitPanel.repaint();
						}
					});
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitDeleteSAButton[finalcntCompleteUnitProduct], gbc);

					poJSeparator[finalcntCompleteUnitProduct] = new JSeparator(JSeparator.HORIZONTAL);
					gbc.gridwidth = 10;
					gbc.gridx = 0;
					gbc.gridy = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 5000;
					poInsertCompleteUnitPanel.add(poJSeparator[finalcntCompleteUnitProduct], gbc);

					poInsertCompleteUnitPanel.revalidate();  
					poInsertCompleteUnitPanel.repaint();
					gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] = gbccntCompleteUnitProduct[finalcntCompleteUnitProduct] + 1000;
				}else{
					JOptionPane
					.showMessageDialog(
							poInsertCompleteUnitPanel,
							"The maximum part no is 100.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitIncreaseProductButton, gbc);

		poInsertCompleteUnitDeleteProductButton = new JButton("Delete");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 54;
		poInsertCompleteUnitDeleteProductButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(cntCompleteUnitProduct > 0){
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitCountLabel[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductNoLabel[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductSubCodeLabel[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductQuantityLabel[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductDescriptionLabel[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductUnitPriceLabel[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductFixedCostLabel[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductDutyCodeLabel[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductPurchaseCodeLabel[cntCompleteUnitProduct]);

					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductNoTextField[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductSubCodeTextField[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitDescriptionTextField[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductQuantityTextField[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductPriceTextField[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductFixedCostTextField[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitDutyCodeTextField[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitProductPurchaseCodeTextField[cntCompleteUnitProduct]);

					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSANoLabel[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAUnitLabel[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAInvoiceNoLabel[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSARemainLabel[cntCompleteUnitProduct]);

					for(int i = 0; i <= cntCompleteUnitSA[cntCompleteUnitProduct]; i++){
						poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSANoTextField[cntCompleteUnitProduct][i]);
						poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAUnitTextField[cntCompleteUnitProduct][i]);
						poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAInvoiceNoTextField[cntCompleteUnitProduct][i]);
						poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSARemainTextField[cntCompleteUnitProduct][i]);
					}

					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitShowSAButton[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitIncreaseSAButton[cntCompleteUnitProduct]);
					poInsertCompleteUnitPanel.remove(poInsertCompleteUnitDeleteSAButton[cntCompleteUnitProduct]);

					poInsertCompleteUnitPanel.remove(poJSeparator[cntCompleteUnitProduct]);

					cntCompleteUnitProduct--;
					gbccntCompleteUnitProduct[cntCompleteUnitProduct]-=1000;
				}else{
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"Error: There is at least one product in purchase order.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}

				setQuantity();

				poInsertCompleteUnitPanel.revalidate();  
				poInsertCompleteUnitPanel.repaint();
			}

		});
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitDeleteProductButton, gbc);

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

			poInsertCompleteUnitProductNoTextField[cntlocation] = new JTextField(completeunitOrderItemStatement.get(cntlocation).getPRODUCT());
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

					if(poInsertCompleteUnitProductNoTextField[cntlocation].getText().length() == 2 && notInteger(poInsertCompleteUnitProductNoTextField[cntlocation].getText()) == false){
						poInsertCompleteUnitProductNoTextField[cntlocation].setText(poInsertCompleteUnitProductNoTextField[cntlocation].getText() + "-");
					}
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
					if(!StringUtils.isEmptyOrWhitespaceOnly(poInsertCompleteUnitProductQuantityTextField[cntlocation].getText())){
						poInsertCompleteUnitProductQuantityTextField[cntlocation].setText(nf.format(Integer.parseInt(poInsertCompleteUnitProductQuantityTextField[cntlocation].getText().replace(",", ""))));
					}

					poInsertCompleteUnitPanel.revalidate();  
					poInsertCompleteUnitPanel.repaint();
				}
			});
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductQuantityTextField[cntlocation], gbc);

			poInsertCompleteUnitProductPriceTextField[cntlocation] = new JTextField(completeunitOrderItemStatement.get(cntlocation).getPrice());
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
			gbc.gridx = 2;
			gbc.gridy = 204 + cntlocation * 1000;
			poInsertCompleteUnitSAUnitLabel[cntlocation].setVisible(false);
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAUnitLabel[cntlocation], gbc);

			poInsertCompleteUnitSAInvoiceNoLabel[cntlocation] = new JLabel("Invoice No");
			gbc.gridwidth = 1;
			gbc.gridx = 3;
			gbc.gridy = 204 + cntlocation * 1000;
			poInsertCompleteUnitSAInvoiceNoLabel[cntlocation].setVisible(false);
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAInvoiceNoLabel[cntlocation], gbc);

			poInsertCompleteUnitSARemainLabel[cntlocation] = new JLabel("Units Remain");
			gbc.gridwidth = 1;
			gbc.gridx = 4;
			gbc.gridy = 204 + cntlocation * 1000;
			poInsertCompleteUnitSARemainLabel[cntlocation].setVisible(false);
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitSARemainLabel[cntlocation], gbc);

			poInsertCompleteUnitIncreaseSAButton[cntlocation] = new JButton("Increase");
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 204 + cntlocation * 1000;
			poInsertCompleteUnitIncreaseSAButton[cntlocation].setVisible(false);
			poInsertCompleteUnitIncreaseSAButton[cntlocation].addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					cntCompleteUnitSA[cntlocation]++;

					poInsertCompleteUnitSANoTextField[cntlocation][cntCompleteUnitSA[cntlocation]] = new JTextField("SA");
					gbc.gridwidth = 1;
					gbc.gridx = 0;
					gbc.gridy = 205 + cntlocation * 1000 + cntCompleteUnitSA[cntlocation];
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitSANoTextField[cntlocation][cntCompleteUnitSA[cntlocation]], gbc);

					poInsertCompleteUnitSAUnitTextField[cntlocation][cntCompleteUnitSA[cntlocation]] = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 2;
					gbc.gridy = 205 + cntlocation * 1000 + cntCompleteUnitSA[cntlocation];
					poInsertCompleteUnitSAUnitTextField[cntlocation][cntCompleteUnitSA[cntlocation]].addKeyListener(new KeyListener(){
						public void keyTyped(KeyEvent e) {}

						public void keyPressed(KeyEvent e) {}

						public void keyReleased(KeyEvent e) {
							setSARemain(cntlocation, cntCompleteUnitSA[cntlocation]);

							for(int i = 0; i <= cntCompleteUnitSA[cntlocation]; i++){
								NumberFormat nf = new DecimalFormat("###,###,###,###,###");	
								if(!StringUtils.isEmptyOrWhitespaceOnly(poInsertCompleteUnitSAUnitTextField[cntlocation][i].getText())){
									poInsertCompleteUnitSAUnitTextField[cntlocation][i].setText(nf.format(Integer.parseInt(poInsertCompleteUnitSAUnitTextField[cntlocation][i].getText().replace(",", ""))));
								}
							}

							poInsertCompleteUnitPanel.revalidate();  
							poInsertCompleteUnitPanel.repaint();
						}

					});
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAUnitTextField[cntlocation][cntCompleteUnitSA[cntlocation]], gbc);

					poInsertCompleteUnitSAInvoiceNoTextField[cntlocation][cntCompleteUnitSA[cntlocation]] = new JTextField("LR-");
					gbc.gridwidth = 1;
					gbc.gridx = 3;
					gbc.gridy = 205 + cntlocation * 1000 + cntCompleteUnitSA[cntlocation];
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAInvoiceNoTextField[cntlocation][cntCompleteUnitSA[cntlocation]], gbc);

					poInsertCompleteUnitSARemainTextField[cntlocation][cntCompleteUnitSA[cntlocation]] = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 4;
					gbc.gridy = 205 + cntlocation * 1000 + cntCompleteUnitSA[cntlocation];
					poInsertCompleteUnitSARemainTextField[cntlocation][cntCompleteUnitSA[cntlocation]].setEditable(false);
					poInsertCompleteUnitPanel.add(poInsertCompleteUnitSARemainTextField[cntlocation][cntCompleteUnitSA[cntlocation]], gbc);

					gbccntCompleteUnitSA++;

					poInsertCompleteUnitPanel.revalidate();  
					poInsertCompleteUnitPanel.repaint();
				}

			});
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitIncreaseSAButton[cntlocation], gbc);

			poInsertCompleteUnitDeleteSAButton[cntlocation] = new JButton("Delete");
			gbc.gridwidth = 1;
			gbc.gridx = 1;
			gbc.gridy = 204 + cntlocation * 1000;
			poInsertCompleteUnitDeleteSAButton[cntlocation].setVisible(false);
			poInsertCompleteUnitDeleteSAButton[cntlocation].addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					if(cntCompleteUnitSA[cntlocation] >= 0){
						poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSANoTextField[cntlocation][cntCompleteUnitSA[cntlocation]]);
						poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAUnitTextField[cntlocation][cntCompleteUnitSA[cntlocation]]);
						poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSAInvoiceNoTextField[cntlocation][cntCompleteUnitSA[cntlocation]]);
						poInsertCompleteUnitPanel.remove(poInsertCompleteUnitSARemainTextField[cntlocation][cntCompleteUnitSA[cntlocation]]);
						gbccntCompleteUnitSA--;
						cntCompleteUnitSA[cntlocation]--;
					}else{
						JOptionPane
						.showMessageDialog(
								errorPanel,
								"Error: There is no SA in purchase order.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
					poInsertCompleteUnitPanel.revalidate();  
					poInsertCompleteUnitPanel.repaint();
				}
			});
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitDeleteSAButton[cntlocation], gbc);

			for(int j = 0; j < SAStatement.get(cntlocation).size(); j++){
				final int finalj = j;
				poInsertCompleteUnitSANoTextField[cntlocation][finalj] = new JTextField(SAStatement.get(cntlocation).get(j).getSaNo());
				gbc.gridwidth = 1;
				gbc.gridx = 0;
				gbc.gridy = gbccntCompleteUnitSA + finalj + cntlocation * 1000;
				poInsertCompleteUnitSANoTextField[cntlocation][finalj].setVisible(false);
				poInsertCompleteUnitPanel.add(poInsertCompleteUnitSANoTextField[cntlocation][finalj], gbc);

				poInsertCompleteUnitSAUnitTextField[cntlocation][finalj] = new JTextField(nf.format(Integer.parseInt(SAStatement.get(cntlocation).get(j).getSaUnit())));
				gbc.gridwidth = 1;
				gbc.gridx = 2;
				gbc.gridy = gbccntCompleteUnitSA + finalj + cntlocation * 1000;
				poInsertCompleteUnitSAUnitTextField[cntlocation][finalj].addKeyListener(new KeyListener(){
					public void keyTyped(KeyEvent e) {}

					public void keyPressed(KeyEvent e) {}

					public void keyReleased(KeyEvent e) {
						setSARemain(cntlocation, finalj);

						for(int i = 0; i <= finalj; i++){
							NumberFormat nf = new DecimalFormat("###,###,###,###,###");	
							if(!StringUtils.isEmptyOrWhitespaceOnly(poInsertCompleteUnitSAUnitTextField[cntlocation][i].getText())){
								poInsertCompleteUnitSAUnitTextField[cntlocation][i].setText(nf.format(Integer.parseInt(poInsertCompleteUnitSAUnitTextField[cntlocation][i].getText().replace(",", ""))));
							}
						}

						poInsertCompleteUnitPanel.revalidate();  
						poInsertCompleteUnitPanel.repaint();
					}

				});
				poInsertCompleteUnitSAUnitTextField[cntlocation][finalj].setVisible(false);
				poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAUnitTextField[cntlocation][finalj], gbc);

				poInsertCompleteUnitSAInvoiceNoTextField[cntlocation][finalj] = new JTextField(SAStatement.get(cntlocation).get(j).getSaInoviceNo());
				gbc.gridwidth = 1;
				gbc.gridx = 3;
				gbc.gridy = gbccntCompleteUnitSA + finalj + cntlocation * 1000;
				poInsertCompleteUnitSAInvoiceNoTextField[cntlocation][finalj].setVisible(false);
				poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAInvoiceNoTextField[cntlocation][finalj], gbc);

				poInsertCompleteUnitSARemainTextField[cntlocation][finalj] = new JTextField();
				gbc.gridwidth = 1;
				gbc.gridx = 4;
				gbc.gridy = gbccntCompleteUnitSA + finalj + cntlocation * 1000;
				poInsertCompleteUnitSARemainTextField[cntlocation][finalj].setEditable(false);
				poInsertCompleteUnitSARemainTextField[cntlocation][finalj].setVisible(false);
				poInsertCompleteUnitPanel.add(poInsertCompleteUnitSARemainTextField[cntlocation][finalj], gbc);
			}

			poJSeparator[cntlocation] = new JSeparator(JSeparator.HORIZONTAL);
			gbc.gridwidth = 10;
			gbc.gridx = 0;
			gbc.gridy = 203 + cntlocation * 1000 + 200;
			poInsertCompleteUnitPanel.add(poJSeparator[cntlocation], gbc);

		}
		
		poInsertCompleteUnitProductButton = new JButton("Save");
		gbc.gridwidth = 2;
		gbc.gridx = 9;
		gbc.gridy = 200000;
		poInsertCompleteUnitProductButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				IRBS irbs = new IRBS();
				try{
					irbs.getCon().setAutoCommit(false);
					if(checkCompleteUnitAllInsert()){
						if (checkCompleteUnitDateValid() != null){
							JOptionPane
							.showMessageDialog(
									errorPanel,
									"Error: Date is invalid",
									"Error", JOptionPane.ERROR_MESSAGE);
						}else{
							irbs.deletePO(Integer.parseInt(poNumber));
							SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
							String ChangedDate;
							ChangedDate = poInsertCompleteUnitOrderDateTextField[0].getText().substring(6, 8) + "/" + poInsertCompleteUnitOrderDateTextField[0].getText().substring(0, 2) + "/" + poInsertCompleteUnitOrderDateTextField[0].getText().substring(3, 5);
							irbs.insertCompleteUnitPO(Integer.parseInt(poNumber), ChangedDate, poInsertCompleteUnitVendorTextField.getText(), ETDLocation.getSelectedItem().toString(), poInsertCompleteUnitRemarkTextArea.getText(), poInsertCompleteUnitDepositTextField.getText(), CustomerNameItem.getCustomerNameId(customerName.getSelectedItem().toString()));
							if(cntCompleteUnitOrderDate > 0){
								for(int i = 1; i <= cntCompleteUnitOrderDate; i++){
									ChangedDate = poInsertCompleteUnitOrderDateTextField[i].getText().substring(6, 8) + "/" + poInsertCompleteUnitOrderDateTextField[i].getText().substring(0, 2) + "/" + poInsertCompleteUnitOrderDateTextField[i].getText().substring(3, 5);
									irbs.insertCompleteUnitOrderDate(Integer.parseInt(poInsertCompleteUnitNoTextField.getText()), ChangedDate);
								}
							}
							for(int i = 0; i <= cntCompleteUnitShipment; i++){
								ArrayList<String> PRODUCTarray = shipmentPRODUCT();
								ArrayList<String> PRICETarray = shipmentPRICE();
								ChangedDate = poInsertCompleteUnitShipmentETDTextField[i].getText().substring(6, 8) + "/" + poInsertCompleteUnitShipmentETDTextField[i].getText().substring(0, 2) + "/" + poInsertCompleteUnitShipmentETDTextField[i].getText().substring(3, 5);
								irbs.insertCompleteUnitShipment(Integer.parseInt(poInsertCompleteUnitNoTextField.getText()), PRODUCTarray.get(i), Integer.parseInt(poInsertCompleteUnitShipmentQuantityTextField[i].getText().replace(",", "")), Double.parseDouble(PRICETarray.get(i)), ChangedDate);
							}

							for(int i = 0; i <= cntCompleteUnitProduct; i++){
								if(cntCompleteUnitSA[i] >= 0){
									irbs.insertCompleteUnitOrderItem(Integer.parseInt(poInsertCompleteUnitNoTextField.getText()), poInsertCompleteUnitProductNoTextField[i].getText(), poInsertCompleteUnitProductSubCodeTextField[i].getText(), Integer.parseInt(poInsertCompleteUnitProductQuantityTextField[i].getText().replace(",", "")), Double.parseDouble(poInsertCompleteUnitProductPriceTextField[i].getText()), poInsertCompleteUnitProductFixedCostTextField[i].getText(), poInsertCompleteUnitProductPurchaseCodeTextField[i].getText(), "", Integer.parseInt(poInsertCompleteUnitSARemainTextField[i][cntCompleteUnitSA[i]].getText().replace(",", "")));
								}else{
									irbs.insertCompleteUnitOrderItem(Integer.parseInt(poInsertCompleteUnitNoTextField.getText()), poInsertCompleteUnitProductNoTextField[i].getText(), poInsertCompleteUnitProductSubCodeTextField[i].getText(), Integer.parseInt(poInsertCompleteUnitProductQuantityTextField[i].getText().replace(",", "")), Double.parseDouble(poInsertCompleteUnitProductPriceTextField[i].getText()), poInsertCompleteUnitProductFixedCostTextField[i].getText(), poInsertCompleteUnitProductPurchaseCodeTextField[i].getText(), "", Integer.parseInt(poInsertCompleteUnitProductQuantityTextField[i].getText().replace(",", "")));
								}
								for(int j = 0; j <= cntCompleteUnitSA[i]; j++){
									irbs.insertSA(Integer.parseInt(poInsertCompleteUnitNoTextField.getText()),poInsertCompleteUnitSANoTextField[i][j].getText(),poInsertCompleteUnitProductNoTextField[i].getText() + poInsertCompleteUnitProductSubCodeTextField[i].getText(),poInsertCompleteUnitSAInvoiceNoTextField[i][j].getText(),Integer.parseInt(poInsertCompleteUnitSAUnitTextField[i][j].getText().replace(",", "")), null);
								}
							}

							d.dispose();
							repaintToShowAll();
							JOptionPane
							.showMessageDialog(
									null,
									"Success update the complete unit purchase order: " + poNumber,
									"Success", JOptionPane.INFORMATION_MESSAGE);
						}
					}else{
						JOptionPane
						.showMessageDialog(
								errorPanel,
								"Error: You should insert all the text field",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
					irbs.getCon().commit();
	
				}catch (Exception ex){
					try {
						irbs.getCon().rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"Error: Error on insert the data. Please try again.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}finally{
					try {
						if(irbs.getCon() != null){
							irbs.getCon().close();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitProductButton, gbc);
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
			for(int j = 0; j <= cntCompleteUnitSA[i]; j++){
				if(poInsertCompleteUnitSANoTextField[i][j].getText().equals(""))
					check = false;
				if(poInsertCompleteUnitSAInvoiceNoTextField[i][j].getText().equals(""))
					check = false;
				if(poInsertCompleteUnitSAUnitTextField[i][j].getText().equals(""))
					check = false;
			}
		}
		for(int i = 0; i <= cntCompleteUnitShipment; i++){
			if(StringUtils.isEmptyOrWhitespaceOnly(poInsertCompleteUnitShipmentQuantityTextField[i].getText())){
				check = false;
			}
			if(StringUtils.isEmptyOrWhitespaceOnly(poInsertCompleteUnitShipmentETDTextField[i].getText())){
				check = false;
			}
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
				}else{
					poInsertCompleteUnitSARemainTextField[i][h].setText(poInsertCompleteUnitSARemainTextField[i-1][h].getText());
				}
			}
		}
		
		if(j == -1){
			SAremain = Integer.toString(totalquantity);
		}else{
			SAremain = poInsertCompleteUnitSARemainTextField[i][j].getText();
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

		poInsertCompleteUnitTotalQuantityTextField.setText(nf.format(totalquantity));

		for( int i = 0; i <= cntCompleteUnitProduct; i++){
			if(!poInsertCompleteUnitProductQuantityTextField[i].getText().equals("") && !notInteger(poInsertCompleteUnitProductQuantityTextField[i].getText().replace(",", "")) && !poInsertCompleteUnitProductPriceTextField[i].getText().equals("") && !notDouble(poInsertCompleteUnitProductPriceTextField[i].getText())){
				quantity[i] = Integer.parseInt(poInsertCompleteUnitProductQuantityTextField[i].getText().replace(",", ""));
				price[i] = Double.parseDouble(poInsertCompleteUnitProductPriceTextField[i].getText());
				totalprice[i] = quantity[i] * price[i];
			}
		}


		tp = totalprice[0] + totalprice[1] + totalprice[2] + totalprice[3] + totalprice[4] + totalprice[5] + totalprice[6] + totalprice[7] + totalprice[8] + totalprice[9];

		DecimalFormat pf = new DecimalFormat("###,###,###,###,##0.00");

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

		DecimalFormat nf = new DecimalFormat("###,###,###,###,##0.00");

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
				poInsertCompleteUnitIncreaseSAButton[cntlocation].setVisible(true);
				poInsertCompleteUnitDeleteSAButton[cntlocation].setVisible(true);
				for(int j = 0; j < 1000; j++){
					final int finalj = j;
					poInsertCompleteUnitSANoTextField[cntlocation][finalj].setVisible(true);
					poInsertCompleteUnitSAUnitTextField[cntlocation][finalj].setVisible(true);
					poInsertCompleteUnitSAInvoiceNoTextField[cntlocation][finalj].setVisible(true);
					poInsertCompleteUnitSARemainTextField[cntlocation][finalj].setVisible(true);
				}
			}else {
				poInsertCompleteUnitShowSAButton[cntlocation].setText("Show SA");
				poInsertCompleteUnitSAUnitLabel[cntlocation].setVisible(false);
				poInsertCompleteUnitSAInvoiceNoLabel[cntlocation].setVisible(false);
				poInsertCompleteUnitSARemainLabel[cntlocation].setVisible(false);
				poInsertCompleteUnitIncreaseSAButton[cntlocation].setVisible(false);
				poInsertCompleteUnitDeleteSAButton[cntlocation].setVisible(false);
				for(int j = 0; j < 1000; j++){
					final int finalj = j;
					poInsertCompleteUnitSANoTextField[cntlocation][finalj].setVisible(false);
					poInsertCompleteUnitSAUnitTextField[cntlocation][finalj].setVisible(false);
					poInsertCompleteUnitSAInvoiceNoTextField[cntlocation][finalj].setVisible(false);
					poInsertCompleteUnitSARemainTextField[cntlocation][finalj].setVisible(false);
				}
			}
		}catch(Exception ex) {
			
		}
	}
	
	private void usePOPanel() {
		d.setContentPane(poInsertCompleteUnitScrollPane);
		d.setVisible(true);		
	}
	
	private void repaintToShowAll(){
		IRBS irbs = new IRBS();
		theTable.setModel(irbs.viewSearchPO(searchSQL));
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

