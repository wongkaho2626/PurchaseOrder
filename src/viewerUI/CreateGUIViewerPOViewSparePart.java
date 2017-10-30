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

import domain.DataCompleteunitOrderItem;
import domain.DataSA;
import domain.DataSparepartOrderItem;
import domain.IRBS;

public class CreateGUIViewerPOViewSparePart {
	private JScrollPane poInsertSparePartScrollPane;
	private JPanel poInsertSparePartPanel;
	private JPanel errorPanel;

	private JLabel poInsertSparePartLabel;
	private JLabel[] poInsertSparePartCountLabel = new JLabel[100];
	private JLabel[] poInsertSparePartProductNoLabel = new JLabel[100];
	private JLabel[] poInsertSparePartProductSubCodeLabel = new JLabel[100];
	private JLabel[] poInsertSparePartProductQuantityLabel = new JLabel[100];
	private JLabel[] poInsertSparePartProductETDLabel = new JLabel[100];
	private JLabel[] poInsertSparePartProductSRDLabel = new JLabel[100];
	private JLabel[] poInsertSparePartProductDescriptionLabel = new JLabel[100];
	private JLabel[] poInsertSparePartProductUnitPriceLabel = new JLabel[100];
	private JLabel[] poInsertSparePartProductFixedCostLabel = new JLabel[100];
	private JLabel[] poInsertSparePartProductDutyCodeLabel = new JLabel[100];
	private JLabel[] poInsertSparePartProductVendorLabel = new JLabel[100];
	
	private JLabel[] poInsertSparePartSANoLabel = new JLabel[1000];
	private JLabel[] poInsertSparePartSAUnitLabel = new JLabel[1000];
	private JLabel[] poInsertSparePartSAInvoiceNoLabel = new JLabel[1000];
	private JLabel[] poInsertSparePartSARemainLabel = new JLabel[1000];
	private JLabel[] poInsertSparePartSAETDLabel = new JLabel[1000];

	private JSeparator[] poJSeparator = new JSeparator[100];

	private JTextField poInsertSparePartNoTextField;
	private JTextField[] poInsertSparePartSubCodeTextField = new JTextField[100];
	private JTextField[] poInsertSparePartProductNoTextField = new JTextField[100];
	private JTextField[] poInsertSparePartProductPriceTextField = new JTextField[100];
	private JTextField[] poInsertSparePartProductQuantityTextField = new JTextField[100];
	private JTextField[] poInsertSparePartProductETDTextField = new JTextField[100];
	private JTextField[] poInsertSparePartProductSRDTextField = new JTextField[100];
	private JTextField[] poInsertSparePartProductVendorTextField = new JTextField[100];
	private JTextField[] poInsertSparePartProductFixedCostTextField = new JTextField[100];
	private JTextField poInsertSparePartVendorTextField;
	private JTextField[] poInsertSparePartOrderDateTextField = new JTextField[100];
	private JTextField[] poInsertSparePartDescriptionTextField = new JTextField[100];
	private JTextField[] poInsertSparePartDutyCodeTextField = new JTextField[100];
	private JTextField poInsertSparePartTotalQuantityTextField;
	private JTextField poInsertSparePartTotalPriceTextField;
	private JTextField[][] poInsertSparePartSANoTextField = new JTextField[100][1000];
	private JTextField[][] poInsertSparePartSAUnitTextField = new JTextField[100][1000];
	private JTextField[][] poInsertSparePartSAInvoiceNoTextField = new JTextField[100][1000];
	private JTextField[][] poInsertSparePartSARemainTextField = new JTextField[100][1000];
	private JTextField[][] poInsertSparePartSAETDTextField = new JTextField[100][1000];
	
	private JTextArea poInsertSparePartRemarkTextArea;
	private int cntSparePartOrderDate = 0;
	private int cntSparePartProduct = 0;
	private int[] cntSparePartSA = new int[1000];
	private int gbccntSparePartOrderDate = 5;
	private int[] gbccntSparePartProduct = new int[100];
	private int gbccntSparePartSA = 205;
	private int totalquantity = 0;
	private String SAremain;
	
	private String poNumber;
	private JFrame theFrame;
	private JDialog d;
	
	private String orderDate;
	private String vendor;
	private String remark;
	private String getCustomerName;
	private String [] sparepartPurchaseOrderStatement;
	private ArrayList<String> sparepartOrderDateStatement;
	private ArrayList<DataSparepartOrderItem> sparepartOrderItemStatement;
	private ArrayList<ArrayList<DataSA>> SAStatement = new ArrayList<ArrayList<DataSA>>();

	public CreateGUIViewerPOViewSparePart(JFrame theFrame, int poNumber){
		this.poNumber = Integer.toString(poNumber);
		this.theFrame = theFrame;
		initUI();
	}

	private void initUI() {
		createDialog();
		storeData();
		setUP();
		for(int i = 0; i < sparepartOrderItemStatement.size(); i++)
			setMaintenance(i);
		setQuantity();
		setPrice();
		for(int i = 0; i < sparepartOrderItemStatement.size(); i++){
			for(int j = 0; j <= cntSparePartSA[i]; j++){
				setSARemain(i, j);
			}
		}
		usePOPanel();
	}
	
	private void createDialog() {
		d = new JDialog(theFrame, "View the Spare Part Purchase Order", true);
		d.setSize(1250,800);
		d.setLocationRelativeTo(null);
		d.setResizable(true);
	}
	
	private void storeData(){
		cntSparePartOrderDate = 0;
		cntSparePartProduct = 0;
		gbccntSparePartOrderDate = 5;
		for(int i = 0 ; i < 100; i++){
			gbccntSparePartProduct[i] = 10000 * i + 10000;
			cntSparePartSA[i] = -1;
		}
		gbccntSparePartSA = 205;
		totalquantity = 0;
		
		IRBS irbs = new IRBS();
		
		sparepartPurchaseOrderStatement = irbs.sparepartPurchaseOrderStatement(Integer.parseInt(poNumber));
		orderDate = sparepartPurchaseOrderStatement[0];
		vendor = sparepartPurchaseOrderStatement[1];
		remark = sparepartPurchaseOrderStatement[2];
		getCustomerName = sparepartPurchaseOrderStatement[3];
		
		sparepartOrderDateStatement = irbs.sparepartOrderDateStatement(Integer.parseInt(poNumber));
		cntSparePartOrderDate = cntSparePartOrderDate + sparepartOrderDateStatement.size();
		gbccntSparePartOrderDate = gbccntSparePartOrderDate + sparepartOrderDateStatement.size();
		
		sparepartOrderItemStatement = irbs.sparepartOrderItemStatement(Integer.parseInt(poNumber));
		cntSparePartProduct = cntSparePartProduct + sparepartOrderItemStatement.size() - 1;
		
		for(int i = 0; i < sparepartOrderItemStatement.size(); i++){
			String PRODUCT = sparepartOrderItemStatement.get(i).getPRODUCT() + sparepartOrderItemStatement.get(i).getsubCode();
			SAStatement.add(irbs.SAStatement(Integer.parseInt(poNumber), PRODUCT));
			cntSparePartSA[i] = SAStatement.get(i).size() - 1;
		}
		
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setUP() {
		poInsertSparePartPanel = new JPanel(new GridBagLayout());
		poInsertSparePartScrollPane = new JScrollPane(poInsertSparePartPanel);
		GridBagConstraints gbc = new GridBagConstraints();

		// ************************* poPanel UI SetUP
		// *************************
		// ========================= poPanel Level One UI Create
		// =========================
		IRBS irbs = new IRBS();
		poInsertSparePartLabel = new JLabel(irbs.customerName(getCustomerName));
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
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);

		gbc.gridwidth = 10;
		gbc.gridx = 0;
		gbc.gridy = 1;
		poInsertSparePartPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
//		poInsertSparePartLabel = new JLabel(getCustomerName);
//		gbc.gridwidth = 1;
//		gbc.ipady = 10;
//		gbc.gridx = 0;
//		gbc.gridy = 1;
//		gbc.weightx = 1;
//		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);
		
		poInsertSparePartLabel = new JLabel("P.O.");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);
		
		poInsertSparePartLabel = new JLabel("Order Date");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 2;
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);
		
//		poInsertSparePartLabel = new JLabel("Vendor");
//		gbc.gridwidth = 1;
//		gbc.gridx = 3;
//		gbc.gridy = 2;
//		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);
		
		poInsertSparePartLabel = new JLabel("Total Quantity");
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);
		
		poInsertSparePartLabel = new JLabel("Total Price");
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy = 2;
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);
		
		poInsertSparePartNoTextField = new JTextField(poNumber);
		poInsertSparePartNoTextField.setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 4;
		poInsertSparePartPanel.add(poInsertSparePartNoTextField, gbc);

		poInsertSparePartOrderDateTextField[0] = new JTextField(orderDate);
		poInsertSparePartOrderDateTextField[0].setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 4;
		poInsertSparePartOrderDateTextField[0].addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(checkMM(poInsertSparePartOrderDateTextField[0].getText())){
					poInsertSparePartOrderDateTextField[0].setText(poInsertSparePartOrderDateTextField[0].getText() + "/");
				}
				if(checkDD(poInsertSparePartOrderDateTextField[0].getText())){
					poInsertSparePartOrderDateTextField[0].setText(poInsertSparePartOrderDateTextField[0].getText() + "/");
				}
				poInsertSparePartPanel.revalidate();  
				poInsertSparePartPanel.repaint();
			}
		});
		poInsertSparePartPanel.add(poInsertSparePartOrderDateTextField[0], gbc);
		
		for(int i = 1; i <= sparepartOrderDateStatement.size(); i++){
			poInsertSparePartOrderDateTextField[i] = new JTextField(sparepartOrderDateStatement.get(i-1));
			poInsertSparePartOrderDateTextField[i].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 1;
			gbc.gridy = 4 + i;
			poInsertSparePartOrderDateTextField[i].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}
	
				@Override
				public void keyPressed(KeyEvent e) {}
	
				@Override
				public void keyReleased(KeyEvent e) {
					for(int i = 1; i <= sparepartOrderDateStatement.size(); i++){
						if(checkMM(poInsertSparePartOrderDateTextField[i].getText())){
							poInsertSparePartOrderDateTextField[i].setText(poInsertSparePartOrderDateTextField[i].getText() + "/");
						}
						if(checkDD(poInsertSparePartOrderDateTextField[i].getText())){
							poInsertSparePartOrderDateTextField[i].setText(poInsertSparePartOrderDateTextField[i].getText() + "/");
						}
						poInsertSparePartPanel.revalidate();  
						poInsertSparePartPanel.repaint();
					}
				}
			});
			poInsertSparePartPanel.add(poInsertSparePartOrderDateTextField[i], gbc);
		}

		poInsertSparePartVendorTextField = new JTextField(vendor);
//		gbc.gridwidth = 1;
//		gbc.gridx = 3;
//		gbc.gridy = 4;
//		poInsertSparePartPanel.add(poInsertSparePartVendorTextField, gbc);
		
		poInsertSparePartTotalQuantityTextField = new JTextField("0");
		poInsertSparePartTotalQuantityTextField.setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 4;
		poInsertSparePartPanel.add(poInsertSparePartTotalQuantityTextField, gbc);
		
		poInsertSparePartTotalPriceTextField = new JTextField("0.00");
		poInsertSparePartTotalPriceTextField.setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy = 4;
		poInsertSparePartPanel.add(poInsertSparePartTotalPriceTextField, gbc);
		
		gbc.gridwidth = 10;
		gbc.gridx = 0;
		gbc.gridy = 49;
		poInsertSparePartPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
		poInsertSparePartLabel = new JLabel("Remark");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 50;
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);

		poInsertSparePartRemarkTextArea = new JTextArea(remark);
		poInsertSparePartRemarkTextArea.setEditable(false);
		gbc.gridwidth = 11;
		gbc.gridx = 0;
		gbc.gridy = 51;
		if(!remark.equals("")){
			poInsertSparePartRemarkTextArea.setBackground(Color.YELLOW);
		}
		poInsertSparePartRemarkTextArea.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(!poInsertSparePartRemarkTextArea.getText().equals("")){
					poInsertSparePartRemarkTextArea.setBackground(Color.YELLOW);
				}else{
					poInsertSparePartRemarkTextArea.setBackground(Color.WHITE);
				}
			}
			
		});
		poInsertSparePartPanel.add(poInsertSparePartRemarkTextArea, gbc);
		
		gbc.gridwidth = 10;
		gbc.gridx = 0;
		gbc.gridy = 52;
		poInsertSparePartPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
		//the area of the first part ////////////////////////////////////////////////////////////////////////////////////////////////
		for(int i = 0; i < sparepartOrderItemStatement.size(); i++){
			final int cntlocation = i;
			poInsertSparePartCountLabel[cntlocation] = new JLabel(Integer.toString(cntlocation+1));
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 200 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartCountLabel[cntlocation], gbc);
			
			poInsertSparePartProductVendorLabel[cntlocation] = new JLabel("Vendor");
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartProductVendorLabel[cntlocation], gbc);

			poInsertSparePartProductNoLabel[cntlocation] = new JLabel("Part No");
			gbc.gridwidth = 1;
			gbc.gridx = 1;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartProductNoLabel[cntlocation], gbc);

			poInsertSparePartProductSubCodeLabel[cntlocation] = new JLabel("Sub Code");
			gbc.gridwidth = 1;
			gbc.gridx = 2;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartProductSubCodeLabel[cntlocation], gbc);
			
			poInsertSparePartProductDescriptionLabel[cntlocation] = new JLabel("Description                                                                                                               ");
			gbc.gridwidth = 1;
			gbc.gridx = 3;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartProductDescriptionLabel[cntlocation], gbc);

			poInsertSparePartProductQuantityLabel[cntlocation] = new JLabel("Quantity");
			gbc.gridwidth = 1;
			gbc.gridx = 4;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartProductQuantityLabel[cntlocation], gbc);
			
			poInsertSparePartProductETDLabel[cntlocation] = new JLabel("Delivery Date");
			gbc.gridwidth = 1;
			gbc.gridx = 5;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartProductETDLabel[cntlocation], gbc);
			
			poInsertSparePartProductSRDLabel[cntlocation] = new JLabel("Ship To Arrive Date");
			gbc.gridwidth = 1;
			gbc.gridx = 6;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartProductSRDLabel[cntlocation], gbc);

			poInsertSparePartProductUnitPriceLabel[cntlocation] = new JLabel("Unit Price");
			gbc.gridwidth = 1;
			gbc.gridx = 7;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartProductUnitPriceLabel[cntlocation], gbc);

			poInsertSparePartProductDutyCodeLabel[cntlocation] = new JLabel("Duty Code");
			gbc.gridwidth = 1;
			gbc.gridx = 8;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartProductDutyCodeLabel[cntlocation], gbc);
			
			poInsertSparePartProductFixedCostLabel[cntlocation] = new JLabel("Fixed Cost");
			gbc.gridwidth = 1;
			gbc.gridx = 9;
			gbc.gridy = 201 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartProductFixedCostLabel[cntlocation], gbc);
			
			poInsertSparePartProductVendorTextField[cntlocation] = new JTextField(sparepartOrderItemStatement.get(cntlocation).getVendor());
			poInsertSparePartProductVendorTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartProductVendorTextField[cntlocation], gbc);

			poInsertSparePartProductNoTextField[cntlocation] = new JTextField(sparepartOrderItemStatement.get(cntlocation).getPRODUCT());
			poInsertSparePartProductNoTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 1;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertSparePartProductNoTextField[cntlocation].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {
					IRBS irbs = new IRBS();
					String [] maintenanceStatement = irbs.maintenanceStatement(poInsertSparePartProductNoTextField[cntlocation].getText());
					if(!poInsertSparePartProductNoTextField[cntlocation].getText().equals("")){
						poInsertSparePartDescriptionTextField[cntlocation].setText(maintenanceStatement[1]);	
						poInsertSparePartDutyCodeTextField[cntlocation].setText(maintenanceStatement[2]);	
						poInsertSparePartProductFixedCostTextField[cntlocation].setText(maintenanceStatement[3]);
						poInsertSparePartProductVendorTextField[cntlocation].setText(maintenanceStatement[5]);
					}

					if(poInsertSparePartProductNoTextField[cntlocation].getText().length() == 2 && notInteger(poInsertSparePartProductNoTextField[cntlocation].getText()) == false){
						poInsertSparePartProductNoTextField[cntlocation].setText(poInsertSparePartProductNoTextField[cntlocation].getText() + "-");
					}
					try {
						irbs.getCon().close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					poInsertSparePartPanel.revalidate();  
					poInsertSparePartPanel.repaint();
				}
			});
			poInsertSparePartPanel.add(poInsertSparePartProductNoTextField[cntlocation], gbc);

			poInsertSparePartSubCodeTextField[cntlocation] = new JTextField(sparepartOrderItemStatement.get(cntlocation).getsubCode());
			poInsertSparePartSubCodeTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 2;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartSubCodeTextField[cntlocation], gbc);
			
			poInsertSparePartDescriptionTextField[cntlocation] = new JTextField();
			poInsertSparePartDescriptionTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 3;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartDescriptionTextField[cntlocation], gbc);

			NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
			poInsertSparePartProductQuantityTextField[cntlocation] = new JTextField(nf.format(Integer.parseInt(sparepartOrderItemStatement.get(cntlocation).getQuantity())));
			poInsertSparePartProductQuantityTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 4;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertSparePartProductQuantityTextField[cntlocation].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {

					setQuantity();
					setSARemain(cntlocation, cntSparePartSA[cntlocation]);

					NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
					poInsertSparePartProductQuantityTextField[cntlocation].setText(nf.format(Integer.parseInt(poInsertSparePartProductQuantityTextField[cntlocation].getText().replace(",", ""))));


					poInsertSparePartPanel.revalidate();  
					poInsertSparePartPanel.repaint();
				}
			});
			poInsertSparePartPanel.add(poInsertSparePartProductQuantityTextField[cntlocation], gbc);
			
			poInsertSparePartProductETDTextField[cntlocation] = new JTextField(sparepartOrderItemStatement.get(cntlocation).getETD());
			poInsertSparePartProductETDTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 5;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertSparePartProductETDTextField[cntlocation].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {
					if(checkMM(poInsertSparePartProductETDTextField[cntlocation].getText())){
						poInsertSparePartProductETDTextField[cntlocation].setText(poInsertSparePartProductETDTextField[cntlocation].getText() + "/");
					}
					if(checkDD(poInsertSparePartProductETDTextField[cntlocation].getText())){
						poInsertSparePartProductETDTextField[cntlocation].setText(poInsertSparePartProductETDTextField[cntlocation].getText() + "/");
					}
					poInsertSparePartPanel.revalidate();  
					poInsertSparePartPanel.repaint();

				}
			});
			poInsertSparePartPanel.add(poInsertSparePartProductETDTextField[cntlocation], gbc);
			
			poInsertSparePartProductSRDTextField[cntlocation] = new JTextField(sparepartOrderItemStatement.get(cntlocation).getSRD());
			poInsertSparePartProductSRDTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 6;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertSparePartProductSRDTextField[cntlocation].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {
					if(checkMM(poInsertSparePartProductSRDTextField[cntlocation].getText())){
						poInsertSparePartProductSRDTextField[cntlocation].setText(poInsertSparePartProductSRDTextField[cntlocation].getText() + "/");
					}
					if(checkDD(poInsertSparePartProductSRDTextField[cntlocation].getText())){
						poInsertSparePartProductSRDTextField[cntlocation].setText(poInsertSparePartProductSRDTextField[cntlocation].getText() + "/");
					}
					poInsertSparePartPanel.revalidate();  
					poInsertSparePartPanel.repaint();

				}
			});
			poInsertSparePartPanel.add(poInsertSparePartProductSRDTextField[cntlocation], gbc);

			poInsertSparePartProductPriceTextField[cntlocation] = new JTextField(sparepartOrderItemStatement.get(cntlocation).getPrice());
			poInsertSparePartProductPriceTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 7;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertSparePartProductPriceTextField[cntlocation].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {
					setPrice();

					poInsertSparePartPanel.revalidate();  
					poInsertSparePartPanel.repaint();
				}
			});
			poInsertSparePartPanel.add(poInsertSparePartProductPriceTextField[cntlocation], gbc);

			poInsertSparePartDutyCodeTextField[cntlocation] = new JTextField();
			poInsertSparePartDutyCodeTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 8;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartDutyCodeTextField[cntlocation], gbc);
			
			poInsertSparePartProductFixedCostTextField[cntlocation] = new JTextField(sparepartOrderItemStatement.get(cntlocation).getFixedcost());
			poInsertSparePartProductFixedCostTextField[cntlocation].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 9;
			gbc.gridy = 202 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartProductFixedCostTextField[cntlocation], gbc);

			poInsertSparePartSANoLabel[cntlocation] = new JLabel("SA No.");
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 203 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartSANoLabel[cntlocation], gbc);

			poInsertSparePartSAUnitLabel[cntlocation] = new JLabel("Unit Shipped");
			gbc.gridwidth = 1;
			gbc.gridx = 1;
			gbc.gridy = 203 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartSAUnitLabel[cntlocation], gbc);

			poInsertSparePartSAInvoiceNoLabel[cntlocation] = new JLabel("Invoice No");
			gbc.gridwidth = 1;
			gbc.gridx = 2;
			gbc.gridy = 203 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartSAInvoiceNoLabel[cntlocation], gbc);

			poInsertSparePartSARemainLabel[cntlocation] = new JLabel("Units Remain");
			gbc.gridwidth = 1;
			gbc.gridx = 3;
			gbc.gridy = 203 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartSARemainLabel[cntlocation], gbc);
			
			poInsertSparePartSAETDLabel[cntlocation] = new JLabel("ETD Date");
			gbc.gridwidth = 1;
			gbc.gridx = 4;
			gbc.gridy = 203 + cntlocation * 1000;
			poInsertSparePartPanel.add(poInsertSparePartSAETDLabel[cntlocation], gbc);
			
			for(int j = 0; j < SAStatement.get(cntlocation).size(); j++){
				final int finalj = j;
				poInsertSparePartSANoTextField[cntlocation][finalj] = new JTextField(SAStatement.get(cntlocation).get(j).getSaNo());
				poInsertSparePartSANoTextField[cntlocation][finalj].setEditable(false);
				gbc.gridwidth = 1;
				gbc.gridx = 0;
				gbc.gridy = gbccntSparePartSA + finalj + cntlocation * 1000;
				poInsertSparePartPanel.add(poInsertSparePartSANoTextField[cntlocation][finalj], gbc);

				poInsertSparePartSAUnitTextField[cntlocation][finalj] = new JTextField(nf.format(Integer.parseInt(SAStatement.get(cntlocation).get(j).getSaUnit())));
				poInsertSparePartSAUnitTextField[cntlocation][finalj].setEditable(false);
				gbc.gridwidth = 1;
				gbc.gridx = 1;
				gbc.gridy = gbccntSparePartSA + finalj + cntlocation * 1000;
				poInsertSparePartSAUnitTextField[cntlocation][finalj].addKeyListener(new KeyListener(){
					public void keyTyped(KeyEvent e) {}

					public void keyPressed(KeyEvent e) {}

					public void keyReleased(KeyEvent e) {
						setSARemain(cntlocation, finalj);

						for(int i = 0; i <= finalj; i++){
							NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
							poInsertSparePartSAUnitTextField[cntlocation][i].setText(nf.format(Integer.parseInt(poInsertSparePartSAUnitTextField[cntlocation][i].getText().replace(",", ""))));
						}

						poInsertSparePartPanel.revalidate();  
						poInsertSparePartPanel.repaint();
					}

				});
				poInsertSparePartPanel.add(poInsertSparePartSAUnitTextField[cntlocation][finalj], gbc);

				poInsertSparePartSAInvoiceNoTextField[cntlocation][finalj] = new JTextField(SAStatement.get(cntlocation).get(j).getSaInoviceNo());
				poInsertSparePartSAInvoiceNoTextField[cntlocation][finalj].setEditable(false);
				gbc.gridwidth = 1;
				gbc.gridx = 2;
				gbc.gridy = gbccntSparePartSA + finalj + cntlocation * 1000;
				poInsertSparePartPanel.add(poInsertSparePartSAInvoiceNoTextField[cntlocation][finalj], gbc);

				poInsertSparePartSARemainTextField[cntlocation][finalj] = new JTextField();
				poInsertSparePartSARemainTextField[cntlocation][finalj].setEditable(false);
				gbc.gridwidth = 1;
				gbc.gridx = 3;
				gbc.gridy = gbccntSparePartSA + finalj + cntlocation * 1000;
				poInsertSparePartSARemainTextField[cntlocation][finalj].setEditable(false);
				poInsertSparePartPanel.add(poInsertSparePartSARemainTextField[cntlocation][finalj], gbc);
				
				poInsertSparePartSAETDTextField[cntlocation][finalj] = new JTextField();
				poInsertSparePartSAETDTextField[cntlocation][finalj].setEditable(false);
				gbc.gridwidth = 1;
				gbc.gridx = 4;
				gbc.gridy = gbccntSparePartSA + finalj + cntlocation * 1000;
				poInsertSparePartSAETDTextField[cntlocation][finalj].setEditable(false);
				poInsertSparePartPanel.add(poInsertSparePartSAETDTextField[cntlocation][finalj], gbc);
			}
			
			poJSeparator[cntlocation] = new JSeparator(JSeparator.HORIZONTAL);
			gbc.gridwidth = 10;
			gbc.gridx = 0;
			gbc.gridy = 203 + cntlocation * 1000 + 200;
			poInsertSparePartPanel.add(poJSeparator[cntlocation], gbc);
			
		}
	}

	private boolean checkSparePartAllInsert(){
		boolean check = true;
		if(poInsertSparePartNoTextField.getText().equals("")){
			check = false;
		}
		for(int i = 0; i <= cntSparePartOrderDate; i++){
			if(poInsertSparePartOrderDateTextField[i].getText().equals("")){
				check = false;
			}
		}
//		if(poInsertSparePartVendorTextField.getText().equals("")){
//			check = false;
//		}
		for(int i = 0; i <= cntSparePartProduct; i++){
			if(poInsertSparePartProductNoTextField[i].getText().equals(""))
				check = false;
			if(poInsertSparePartProductQuantityTextField[i].getText().equals(""))
				check = false;
			if(poInsertSparePartProductSRDTextField[i].getText().equals(""))
				check = false;
			if(poInsertSparePartProductPriceTextField[i].getText().equals(""))
				check = false;
			if(poInsertSparePartProductVendorTextField[i].getText().equals(""))
				check = false;
			if(poInsertSparePartProductFixedCostTextField[i].getText().equals(""))
				check = false;
			for(int j = 0; j <= cntSparePartSA[i]; j++){
				if(poInsertSparePartSANoTextField[i][j].getText().equals(""))
					check = false;
				if(poInsertSparePartSAInvoiceNoTextField[i][j].getText().equals(""))
					check = false;
				if(poInsertSparePartSAUnitTextField[i][j].getText().equals(""))
					check = false;
			}
		}
		return check;
	}

	// ========================= check for Complete Unit Insert Panel has insert correct date format
	// ========================= (If correct date format, return null. If incorrect date format, return exception.)
	private Exception checkSparePartDateValid(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		Exception exception = null;
		for(int i = 0; i <= cntSparePartOrderDate; i++){
			try {
				sdf.setLenient(false);
				java.util.Date date = sdf.parse(poInsertSparePartOrderDateTextField[i].getText());
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				String ChangedDate = poInsertSparePartOrderDateTextField[i].getText().substring(6, 8) + "/" + poInsertSparePartOrderDateTextField[i].getText().substring(0, 2) + "/" + poInsertSparePartOrderDateTextField[i].getText().substring(3, 5);
			} catch (ParseException e1) {
				exception = e1;
			} catch (Exception e1){
				exception = e1;
			}
		}
		for(int i = 0; i <= cntSparePartProduct; i++){
			try {
				sdf.setLenient(false);
				java.util.Date date = sdf.parse(poInsertSparePartProductSRDTextField[i].getText());
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				String ChangedDate = poInsertSparePartProductSRDTextField[i].getText().substring(6, 8) + "/" + poInsertSparePartProductSRDTextField[i].getText().substring(0, 2) + "/" + poInsertSparePartProductSRDTextField[i].getText().substring(3, 5);
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
		for(int i = 1; i <= cntSparePartOrderDate; i++){
			poInsertSparePartPanel.remove(poInsertSparePartOrderDateTextField[i]);
		}
		for(int i = 1; i <= cntSparePartProduct; i++){
			poInsertSparePartPanel.remove(poInsertSparePartCountLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductNoLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductSubCodeLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductQuantityLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductSRDLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductDescriptionLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductUnitPriceLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductFixedCostLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductDutyCodeLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductVendorLabel[i]);
			
			poInsertSparePartPanel.remove(poInsertSparePartProductNoTextField[i]);
			poInsertSparePartPanel.remove(poInsertSparePartSubCodeTextField[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductQuantityTextField[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductSRDTextField[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductPriceTextField[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductVendorTextField[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductFixedCostTextField[i]);
			poInsertSparePartPanel.remove(poInsertSparePartDescriptionTextField[i]);
			poInsertSparePartPanel.remove(poInsertSparePartDutyCodeTextField[i]);
			
			poInsertSparePartPanel.remove(poInsertSparePartSANoLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartSAUnitLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartSAInvoiceNoLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartSARemainLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartSAETDLabel[i]);
			
			poInsertSparePartPanel.remove(poJSeparator[i]);

			for(int j = 0; j <= cntSparePartSA[i]; j++){
				poInsertSparePartPanel.remove(poInsertSparePartSANoTextField[i][j]);
				poInsertSparePartPanel.remove(poInsertSparePartSAUnitTextField[i][j]);
				poInsertSparePartPanel.remove(poInsertSparePartSAInvoiceNoTextField[i][j]);
				poInsertSparePartPanel.remove(poInsertSparePartSARemainTextField[i][j]);
				poInsertSparePartPanel.remove(poInsertSparePartSAETDTextField[i][j]);
			}
			cntSparePartSA[i] = -1;
		}
		for(int j = 0; j <= cntSparePartSA[0]; j++){
			poInsertSparePartPanel.remove(poInsertSparePartSANoTextField[0][j]);
			poInsertSparePartPanel.remove(poInsertSparePartSAUnitTextField[0][j]);
			poInsertSparePartPanel.remove(poInsertSparePartSAInvoiceNoTextField[0][j]);
			poInsertSparePartPanel.remove(poInsertSparePartSARemainTextField[0][j]);
			poInsertSparePartPanel.remove(poInsertSparePartSAETDTextField[0][j]);
		}
		cntSparePartSA[0] = -1;

		cntSparePartOrderDate = 0;
		cntSparePartProduct = 0;
		gbccntSparePartOrderDate = 5;
		for(int i = 0 ; i < 100; i++){
			gbccntSparePartProduct[i] = 10000 * i + 10000;
		}
		gbccntSparePartSA = 205;
		totalquantity = 0;
		poInsertSparePartNoTextField.setText(null);
		poInsertSparePartOrderDateTextField[0].setText(null);
		poInsertSparePartVendorTextField.setText(null);
		poInsertSparePartTotalQuantityTextField.setText("0");
		poInsertSparePartTotalPriceTextField.setText("0.00");
		
		poInsertSparePartProductNoTextField[0].setText(null);
		poInsertSparePartSubCodeTextField[0].setText(null);
		poInsertSparePartProductQuantityTextField[0].setText(null);
		poInsertSparePartProductSRDTextField[0].setText(null);
		poInsertSparePartProductPriceTextField[0].setText(null);
		poInsertSparePartProductVendorTextField[0].setText(null);
		poInsertSparePartProductFixedCostTextField[0].setText(null);
		poInsertSparePartDescriptionTextField[0].setText(null);
		poInsertSparePartDutyCodeTextField[0].setText(null);
		poInsertSparePartRemarkTextArea.setText(null);
		poInsertSparePartRemarkTextArea.setBackground(Color.WHITE);
		
		poInsertSparePartPanel.revalidate();  
		poInsertSparePartPanel.repaint();
		
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
	
	public JScrollPane insertSparePartPane(){
		return poInsertSparePartScrollPane;
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
		String [] maintenanceStatement = irbs.maintenanceStatement(poInsertSparePartProductNoTextField[i].getText());
		if(!poInsertSparePartProductNoTextField[i].getText().equals("")){
			poInsertSparePartDescriptionTextField[i].setText(maintenanceStatement[1]);	
			poInsertSparePartDutyCodeTextField[i].setText(maintenanceStatement[2]);	
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
				if(!poInsertSparePartSAUnitTextField[i][h].getText().equals("") && !notInteger(poInsertSparePartSAUnitTextField[i][h].getText().replace(",", ""))){
					remain = Integer.parseInt(poInsertSparePartProductQuantityTextField[i].getText().replace(",", "")) - Integer.parseInt(poInsertSparePartSAUnitTextField[i][h].getText().replace(",", ""));
					poInsertSparePartSARemainTextField[i][h].setText(nf.format(remain));
					System.out.println("remain: " + nf.format(remain));
				}else{
					poInsertSparePartSARemainTextField[i][h].setText(nf.format(Integer.parseInt(poInsertSparePartProductQuantityTextField[i].getText().replace(",", ""))));
				}
			}else{

				if(!poInsertSparePartSAUnitTextField[i][h].getText().equals("") && !notInteger(poInsertSparePartSAUnitTextField[i][h].getText().replace(",", ""))){
					remain = Integer.parseInt(poInsertSparePartProductQuantityTextField[i].getText().replace(",", ""));
					for( int k = 0; k <= h; k++){
						remain = remain - Integer.parseInt(poInsertSparePartSAUnitTextField[i][k].getText().replace(",", ""));
					}
					poInsertSparePartSARemainTextField[i][h].setText(nf.format(remain));
					System.out.println("remain: " + nf.format(remain));
				}else{
					poInsertSparePartSARemainTextField[i][h].setText(poInsertSparePartSARemainTextField[i-1][h].getText());
				}
			}
		}
		
		if(j == -1){
			SAremain = Integer.toString(totalquantity);
			System.out.println(SAremain);
		}else{
			SAremain = poInsertSparePartSARemainTextField[i][j].getText();
			System.out.println(SAremain);
		}
	}
	
	private void setQuantity(){
		double [] price = new double[100];
		int [] quantity = new int[100];
		double [] totalprice = new double[100];
		double tp = 0;

		for( int i = 0; i <= cntSparePartProduct; i++){
			if(!poInsertSparePartProductQuantityTextField[i].getText().equals("") && !notInteger(poInsertSparePartProductQuantityTextField[i].getText().replace(",", "")))
				quantity[i] = Integer.parseInt(poInsertSparePartProductQuantityTextField[i].getText().replace(",", ""));
		}
		
		totalquantity = 0;
		
		for(int i = 0; i <= cntSparePartProduct; i++){
			totalquantity = totalquantity + quantity[i];
		}

		NumberFormat nf = new DecimalFormat("###,###,###,###,###");
		System.out.println("total quantity: " + nf.format(totalquantity));

		poInsertSparePartTotalQuantityTextField.setText(nf.format(totalquantity));

		for( int i = 0; i <= cntSparePartProduct; i++){
			if(!poInsertSparePartProductQuantityTextField[i].getText().equals("") && !notInteger(poInsertSparePartProductQuantityTextField[i].getText().replace(",", "")) && !poInsertSparePartProductPriceTextField[i].getText().equals("") && !notDouble(poInsertSparePartProductPriceTextField[i].getText())){
				quantity[i] = Integer.parseInt(poInsertSparePartProductQuantityTextField[i].getText().replace(",", ""));
				price[i] = Double.parseDouble(poInsertSparePartProductPriceTextField[i].getText());
				totalprice[i] = quantity[i] * price[i];
			}
		}

		tp = 0;
		
		for(int i = 0; i <= cntSparePartProduct; i++){
			tp = tp + totalprice[i];
		}

		DecimalFormat pf = new DecimalFormat("###,###,###,###,###.00");
		System.out.println("total price: " + pf.format(tp));

		poInsertSparePartTotalPriceTextField.setText(pf.format(tp));
	}
	
	private void setPrice(){
		double [] price = new double[100];
		int [] quantity = new int[100];
		double [] totalprice = new double[100];
		double tp = 0;


		for( int i = 0; i <= cntSparePartProduct; i++){
			if(!poInsertSparePartProductQuantityTextField[i].getText().equals("") && !notInteger(poInsertSparePartProductQuantityTextField[i].getText().replace(",", "")) && !poInsertSparePartProductPriceTextField[i].getText().equals("") && !notDouble(poInsertSparePartProductPriceTextField[i].getText())){
				quantity[i] = Integer.parseInt(poInsertSparePartProductQuantityTextField[i].getText().replace(",", ""));
				price[i] = Double.parseDouble(poInsertSparePartProductPriceTextField[i].getText());
				totalprice[i] = quantity[i] * price[i];
			}
		}

		tp = 0;
		
		for(int i = 0; i <= cntSparePartProduct; i++){
			tp = tp + totalprice[i];
		}

		DecimalFormat nf = new DecimalFormat("###,###,###,###,###.00");
		System.out.println("total price: " + nf.format(tp));

		poInsertSparePartTotalPriceTextField.setText(nf.format(tp));
	}
	
	private void usePOPanel() {
		d.setContentPane(poInsertSparePartScrollPane);
		d.setVisible(true);		
	}
}

