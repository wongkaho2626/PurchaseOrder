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

import domain.DataToolingOrderItem;
import domain.IRBS;

public class CreateGUIViewerPOViewTooling {
	private JScrollPane poInsertToolingScrollPane;
	private JPanel poInsertToolingPanel;
	private JPanel errorPanel;

	private JLabel poInsertToolingLabel;
	private JTextField poInsertToolingNoTextField;
	private JTextField[] poInsertToolingProductNoTextField = new JTextField[100];
	private JTextField[] poInsertToolingProductPriceTextField = new JTextField[100];
	private JTextField[] poInsertToolingProductQuantityTextField = new JTextField[100];
	private JTextField[] poInsertToolingProductFixedCostTextField = new JTextField[100];
	private JTextField poInsertToolingVendorTextField;
	private JTextField poInsertToolingDepositTextField;
	private JTextField[] poInsertToolingOrderDateTextField = new JTextField[100];
	private JTextField[] poInsertToolingACRONYMTextField = new JTextField[100];
	private JTextField[] poInsertToolingDescriptionTextField = new JTextField[100];
	private JTextField[] poInsertToolingDutyCodeTextField = new JTextField[100];
	private JTextField poInsertToolingTotalQuantityTextField;
	private JTextField poInsertToolingTotalPriceTextField;
	private JTextField poInsertToolingCompletionTextField;
	private JTextArea poInsertToolingRemarkTextArea;
	private int cntToolingOrderDate = 0;
	private int cntToolingProduct = 0;
	private int gbccntToolingOrderDate = 5;
	private int gbccntToolingProduct = 60;
	private int totalquantity = 0;
	
	private String poNumber;
	private JFrame theFrame;
	private JDialog d;
	
	private String orderDate;
	private String vendor;
	private String remark;
	private String completion;
	private String getCustomerName;
	private String deposit;
	private String[] toolingPurchaseOrderStatement;
	private ArrayList<String> toolingOrderDateStatement;
	private ArrayList<DataToolingOrderItem> toolingOrderItemStatement;


	public CreateGUIViewerPOViewTooling(JFrame theFrame, int poNumber){
		this.poNumber = Integer.toString(poNumber);
		this.theFrame = theFrame;
		initUI();
	}

	private void initUI() {
		createDialog();
		storeData();
		setUP();
		for(int i = 0; i < toolingOrderItemStatement.size(); i++)
			setMaintenance(i);
		setQuantity();
		setPrice();
		usePOPanel();
	}
	
	private void createDialog() {
		d = new JDialog(theFrame, "View the Tooling Purchase Order", true);
		d.setSize(1250,600);
		d.setLocationRelativeTo(null);
		d.setResizable(true);
	}
	
	private void storeData(){
		IRBS irbs = new IRBS();
		toolingPurchaseOrderStatement = irbs.toolingPurchaseOrderStatement(Integer.parseInt(poNumber));
		orderDate = toolingPurchaseOrderStatement[0];
		vendor = toolingPurchaseOrderStatement[1];
		remark = toolingPurchaseOrderStatement[2];
		completion = toolingPurchaseOrderStatement[3];
		getCustomerName = toolingPurchaseOrderStatement[4];
		deposit = toolingPurchaseOrderStatement[5];
		
		toolingOrderDateStatement = irbs.toolingOrderDateStatement(Integer.parseInt(poNumber));
		cntToolingOrderDate = cntToolingOrderDate + toolingOrderDateStatement.size();
		gbccntToolingOrderDate = gbccntToolingOrderDate + toolingOrderDateStatement.size();
		
		toolingOrderItemStatement = irbs.toolingOrderItemStatement(Integer.parseInt(poNumber));
		cntToolingProduct = cntToolingProduct + toolingOrderItemStatement.size() - 1;
		gbccntToolingProduct = gbccntToolingProduct + toolingOrderItemStatement.size() - 1;
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setUP() {
		poInsertToolingPanel = new JPanel(new GridBagLayout());
		poInsertToolingScrollPane = new JScrollPane(poInsertToolingPanel);
		GridBagConstraints gbc = new GridBagConstraints();

		// ************************* poPanel UI SetUP
		// *************************
		// ========================= poPanel Level One UI Create
		// =========================
		IRBS irbs = new IRBS();
		poInsertToolingLabel = new JLabel(irbs.customerName(getCustomerName));
		try {
			irbs.getCon().close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		gbc.weightx = 0;
		gbc.gridwidth = 9;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.insets = new Insets(5, 5, 5, 5);
		poInsertToolingPanel.add(poInsertToolingLabel, gbc);
		
		gbc.gridwidth = 9;
		gbc.gridx = 0;
		gbc.gridy = 1;
		poInsertToolingPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
//		poInsertToolingLabel = new JLabel(getCustomerName);
//		gbc.gridwidth = 1;
//		gbc.ipady = 10;
//		gbc.gridx = 0;
//		gbc.gridy = 1;
//		gbc.weightx = 1;
//		poInsertToolingPanel.add(poInsertToolingLabel, gbc);

		poInsertToolingLabel = new JLabel("P.O.");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		poInsertToolingPanel.add(poInsertToolingLabel, gbc);
		
		poInsertToolingLabel = new JLabel("Order Date");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 2;
		poInsertToolingPanel.add(poInsertToolingLabel, gbc);
		
		poInsertToolingLabel = new JLabel("Vendor");
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		poInsertToolingPanel.add(poInsertToolingLabel, gbc);
		
		poInsertToolingLabel = new JLabel("ACRONYM");
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy = 2;
		poInsertToolingPanel.add(poInsertToolingLabel, gbc);
		
		poInsertToolingLabel = new JLabel("Total Quantity");
		gbc.gridwidth = 1;
		gbc.gridx = 4;
		gbc.gridy = 2;
		poInsertToolingPanel.add(poInsertToolingLabel, gbc);
		
		poInsertToolingLabel = new JLabel("Total Price");
		gbc.gridwidth = 1;
		gbc.gridx = 5;
		gbc.gridy = 2;
		poInsertToolingPanel.add(poInsertToolingLabel, gbc);
		
		poInsertToolingLabel = new JLabel("Deposit");
		gbc.gridwidth = 1;
		gbc.gridx = 6;
		gbc.gridy = 2;
		poInsertToolingPanel.add(poInsertToolingLabel, gbc);
		
		poInsertToolingLabel = new JLabel("Tooling Completion");
		gbc.gridwidth = 1;
		gbc.gridx = 7;
		gbc.gridy = 2;
		poInsertToolingPanel.add(poInsertToolingLabel, gbc);
		
		poInsertToolingNoTextField = new JTextField(poNumber);
		poInsertToolingNoTextField.setEditable(false);;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 4;
		poInsertToolingPanel.add(poInsertToolingNoTextField, gbc);

		poInsertToolingOrderDateTextField[0] = new JTextField(orderDate);
		poInsertToolingOrderDateTextField[0].setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 4;
		poInsertToolingOrderDateTextField[0].addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(checkMM(poInsertToolingOrderDateTextField[0].getText())){
					poInsertToolingOrderDateTextField[0].setText(poInsertToolingOrderDateTextField[0].getText() + "/");
				}
				if(checkDD(poInsertToolingOrderDateTextField[0].getText())){
					poInsertToolingOrderDateTextField[0].setText(poInsertToolingOrderDateTextField[0].getText() + "/");
				}
				poInsertToolingPanel.revalidate();  
				poInsertToolingPanel.repaint();
			}
		});
		poInsertToolingPanel.add(poInsertToolingOrderDateTextField[0], gbc);
		
		for(int i = 1; i <= toolingOrderDateStatement.size(); i++){
			poInsertToolingOrderDateTextField[i] = new JTextField(toolingOrderDateStatement.get(i-1));
			poInsertToolingOrderDateTextField[i].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 1;
			gbc.gridy = 4 + i;
			poInsertToolingOrderDateTextField[i].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}
	
				@Override
				public void keyPressed(KeyEvent e) {}
	
				@Override
				public void keyReleased(KeyEvent e) {
					for(int i = 0; i < toolingOrderDateStatement.size(); i++){
						if(checkMM(poInsertToolingOrderDateTextField[i].getText())){
							poInsertToolingOrderDateTextField[i].setText(poInsertToolingOrderDateTextField[i].getText() + "/");
						}
						if(checkDD(poInsertToolingOrderDateTextField[i].getText())){
							poInsertToolingOrderDateTextField[i].setText(poInsertToolingOrderDateTextField[i].getText() + "/");
						}
						poInsertToolingPanel.revalidate();  
						poInsertToolingPanel.repaint();
					}
				}
			});
			poInsertToolingPanel.add(poInsertToolingOrderDateTextField[i], gbc);
		}

		poInsertToolingVendorTextField = new JTextField(vendor);
		poInsertToolingVendorTextField.setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 4;
		poInsertToolingPanel.add(poInsertToolingVendorTextField, gbc);
		
		poInsertToolingACRONYMTextField[0] = new JTextField();
		poInsertToolingACRONYMTextField[0].setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy = 4;
		poInsertToolingPanel.add(poInsertToolingACRONYMTextField[0], gbc);
		
		poInsertToolingTotalQuantityTextField = new JTextField("0");
		gbc.gridwidth = 1;
		gbc.gridx = 4;
		gbc.gridy = 4;
		poInsertToolingTotalQuantityTextField.setEditable(false);
		poInsertToolingPanel.add(poInsertToolingTotalQuantityTextField, gbc);
		
		poInsertToolingTotalPriceTextField = new JTextField("0.00");
		gbc.gridwidth = 1;
		gbc.gridx = 5;
		gbc.gridy = 4;
		poInsertToolingTotalPriceTextField.setEditable(false);
		poInsertToolingPanel.add(poInsertToolingTotalPriceTextField, gbc);
		
		poInsertToolingDepositTextField = new JTextField(deposit);
		gbc.gridwidth = 1;
		gbc.gridx = 6;
		gbc.gridy = 4;
		poInsertToolingDepositTextField.setEditable(false);
		poInsertToolingPanel.add(poInsertToolingDepositTextField, gbc);
		
		poInsertToolingCompletionTextField = new JTextField(completion);
		poInsertToolingCompletionTextField.setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 7;
		gbc.gridy = 4;
		poInsertToolingCompletionTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(checkMM(poInsertToolingCompletionTextField.getText())){
					poInsertToolingCompletionTextField.setText(poInsertToolingCompletionTextField.getText() + "/");
				}
				if(checkDD(poInsertToolingCompletionTextField.getText())){
					poInsertToolingCompletionTextField.setText(poInsertToolingCompletionTextField.getText() + "/");
				}
				poInsertToolingPanel.revalidate();  
				poInsertToolingPanel.repaint();
			}
		});
		poInsertToolingPanel.add(poInsertToolingCompletionTextField, gbc);
		
		gbc.gridwidth = 9;
		gbc.gridx = 0;
		gbc.gridy = 52;
		poInsertToolingPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
		poInsertToolingLabel = new JLabel("Part No");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 53;
		poInsertToolingPanel.add(poInsertToolingLabel, gbc);
		
		poInsertToolingLabel = new JLabel("Quantity");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 53;
		poInsertToolingPanel.add(poInsertToolingLabel, gbc);

		poInsertToolingLabel = new JLabel("Description");
		gbc.gridwidth = 5;
		gbc.gridx = 2;
		gbc.gridy = 53;
		poInsertToolingPanel.add(poInsertToolingLabel, gbc);
		
		poInsertToolingLabel = new JLabel("Unit Price");
		gbc.gridwidth = 1;
		gbc.gridx = 7;
		gbc.gridy = 53;
		poInsertToolingPanel.add(poInsertToolingLabel, gbc);
		
		for(int i = 0; i < toolingOrderItemStatement.size(); i++){
			poInsertToolingProductNoTextField[i] = new JTextField(toolingOrderItemStatement.get(i).getPRODUCT());
			poInsertToolingProductNoTextField[i].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 55 + i;
			poInsertToolingProductNoTextField[i].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {					
					for(int i = 0; i < toolingOrderItemStatement.size(); i++){
						
						setMaintenance(i);
						
//						if(poInsertToolingProductNoTextField[i].getText().length() == 2 && notInteger(poInsertToolingProductNoTextField[i].getText()) == false){
//							poInsertToolingProductNoTextField[i].setText(poInsertToolingProductNoTextField[i].getText() + "-");
//						}

						poInsertToolingPanel.revalidate();  
						poInsertToolingPanel.repaint();
					}
				}
			});
			poInsertToolingPanel.add(poInsertToolingProductNoTextField[i], gbc);

			NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
			poInsertToolingProductQuantityTextField[i] = new JTextField(nf.format(Integer.parseInt(toolingOrderItemStatement.get(i).getQuantity())));
			poInsertToolingProductQuantityTextField[i].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 1;
			gbc.gridy = 55 + i;
			poInsertToolingProductQuantityTextField[i].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {

					setQuantity();

					for(int i = 0; i < toolingOrderDateStatement.size(); i++){
						NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
						poInsertToolingProductQuantityTextField[i].setText(nf.format(Integer.parseInt(poInsertToolingProductQuantityTextField[i].getText().replace(",", ""))));
					}

					poInsertToolingPanel.revalidate();  
					poInsertToolingPanel.repaint();
				}
			});
			poInsertToolingPanel.add(poInsertToolingProductQuantityTextField[i], gbc);

			poInsertToolingDescriptionTextField[i] = new JTextField(toolingOrderItemStatement.get(i).getDescription());
			poInsertToolingDescriptionTextField[i].setEditable(false);
			gbc.gridwidth = 5;
			gbc.gridx = 2;
			gbc.gridy = 55 + i;
			poInsertToolingPanel.add(poInsertToolingDescriptionTextField[i], gbc);

			DecimalFormat df = new DecimalFormat("###,###,###,###,###.00");
			poInsertToolingProductPriceTextField[i] = new JTextField(df.format(Double.parseDouble(toolingOrderItemStatement.get(i).getPrice())));
			poInsertToolingProductPriceTextField[i].setEditable(false);
			gbc.gridwidth = 1;
			gbc.gridx = 7;
			gbc.gridy = 55 + i;
			poInsertToolingProductPriceTextField[i].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {
					setPrice();

					poInsertToolingPanel.revalidate();  
					poInsertToolingPanel.repaint();
				}
			});
			poInsertToolingPanel.add(poInsertToolingProductPriceTextField[i], gbc);
		}
		
		gbc.gridwidth = 9;
		gbc.gridx = 0;
		gbc.gridy = 199999;
		poInsertToolingPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
		poInsertToolingLabel = new JLabel("Remark");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 200001;
		poInsertToolingPanel.add(poInsertToolingLabel, gbc);

		poInsertToolingRemarkTextArea = new JTextArea(remark);
		poInsertToolingRemarkTextArea.setEditable(false);
		gbc.gridwidth = 11;
		gbc.gridx = 0;
		gbc.gridy = 200002;
		if(!remark.equals(""))
			poInsertToolingRemarkTextArea.setBackground(Color.YELLOW);
		poInsertToolingRemarkTextArea.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(!poInsertToolingRemarkTextArea.getText().equals("")){
					poInsertToolingRemarkTextArea.setBackground(Color.YELLOW);
				}else{
					poInsertToolingRemarkTextArea.setBackground(Color.WHITE);
				}
			}
		});
		poInsertToolingPanel.add(poInsertToolingRemarkTextArea, gbc);

	}

	private boolean checkToolingAllInsert(){
		boolean check = true;
		if(poInsertToolingNoTextField.getText().equals("")){
			check = false;
		}
		for(int i = 0; i <= cntToolingOrderDate; i++){
			if(poInsertToolingOrderDateTextField[i].getText().equals("")){
				check = false;
			}
		}
		if(poInsertToolingVendorTextField.getText().equals("")){
			check = false;
		}
		if(poInsertToolingCompletionTextField.getText().equals("")){
			check = false;
		}
		for(int i = 0; i <= cntToolingProduct; i++){
			if(poInsertToolingProductNoTextField[i].getText().equals(""))
				check = false;
			if(poInsertToolingProductQuantityTextField[i].getText().equals(""))
				check = false;
			if(poInsertToolingProductPriceTextField[i].getText().equals(""))
				check = false;
			if(poInsertToolingProductFixedCostTextField[i].getText().equals(""))
				check = false;
		}
		return check;
	}

	// ========================= check for Tooling Insert Panel has insert correct date format
	// ========================= (If correct date format, return null. If incorrect date format, return exception.)
	private Exception checkToolingDateValid(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		Exception exception = null;
		for(int i = 0; i <= cntToolingOrderDate; i++){
			try {
				sdf.setLenient(false);
				java.util.Date date = sdf.parse(poInsertToolingOrderDateTextField[i].getText());
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				String ChangedDate = poInsertToolingOrderDateTextField[i].getText().substring(6, 8) + "/" + poInsertToolingOrderDateTextField[i].getText().substring(0, 2) + "/" + poInsertToolingOrderDateTextField[i].getText().substring(3, 5);
			} catch (ParseException e1) {
				exception = e1;
			} catch (Exception e1){
				exception = e1;
			}
		}
		try {
			sdf.setLenient(false);
			java.util.Date date = sdf.parse(poInsertToolingCompletionTextField.getText());
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			String ChangedDate = poInsertToolingCompletionTextField.getText().substring(6, 8) + "/" + poInsertToolingCompletionTextField.getText().substring(0, 2) + "/" + poInsertToolingCompletionTextField.getText().substring(3, 5);
		} catch (ParseException e1) {
			exception = e1;
		} catch (Exception e1){
			exception = e1;
		}
		return exception;
	}

	// ========================= reset all the TextField in Purchase Order Insert Panel
	// ========================= 

	public void poInsertReset(){
		for(int i = 1; i <= cntToolingOrderDate; i++){
			poInsertToolingPanel.remove(poInsertToolingOrderDateTextField[i]);
		}
		for(int i = 1; i <= cntToolingProduct; i++){
			poInsertToolingPanel.remove(poInsertToolingProductNoTextField[i]);
			poInsertToolingPanel.remove(poInsertToolingProductQuantityTextField[i]);
			poInsertToolingPanel.remove(poInsertToolingProductPriceTextField[i]);
			poInsertToolingPanel.remove(poInsertToolingProductFixedCostTextField[i]);
			poInsertToolingPanel.remove(poInsertToolingDescriptionTextField[i]);
			poInsertToolingPanel.remove(poInsertToolingDutyCodeTextField[i]);
		}
		
		cntToolingOrderDate = 0;
		cntToolingProduct = 0;
		gbccntToolingOrderDate = 5;
		gbccntToolingProduct = 60;
		totalquantity = 0;
		poInsertToolingNoTextField.setText(null);
		poInsertToolingOrderDateTextField[0].setText(null);
		poInsertToolingVendorTextField.setText(null);
		poInsertToolingDepositTextField.setText(null);
		poInsertToolingACRONYMTextField[0].setText(null);
		poInsertToolingDescriptionTextField[0].setText(null);
		poInsertToolingDutyCodeTextField[0].setText(null);
		poInsertToolingTotalQuantityTextField.setText("0");
		poInsertToolingTotalPriceTextField.setText("0.00");
		poInsertToolingProductNoTextField[0].setText(null);
		poInsertToolingProductQuantityTextField[0].setText(null);
		poInsertToolingProductPriceTextField[0].setText(null);
		poInsertToolingProductFixedCostTextField[0].setText(null);
		poInsertToolingCompletionTextField.setText(null);
		poInsertToolingRemarkTextArea.setText(null);
		poInsertToolingRemarkTextArea.setBackground(Color.WHITE);
		
		poInsertToolingPanel.revalidate();  
		poInsertToolingPanel.repaint();
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
	
	public JScrollPane insertToolingPane(){
		return poInsertToolingScrollPane;
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
		
		maintenanceStatement = irbs.maintenanceStatement(poInsertToolingProductNoTextField[0].getText());
		poInsertToolingACRONYMTextField[0].setText(maintenanceStatement[0]);	
		
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setQuantity(){
		double [] price = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int [] quantity = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		double [] totalprice = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		double tp = 0;

		for( int i = 0; i <= cntToolingProduct; i++){
			if(!poInsertToolingProductQuantityTextField[i].getText().equals("") && !notInteger(poInsertToolingProductQuantityTextField[i].getText().replace(",", "")))
				quantity[i] = Integer.parseInt(poInsertToolingProductQuantityTextField[i].getText().replace(",", ""));
		}

		totalquantity = quantity[0] + quantity[1] + quantity[2] + quantity[3] + quantity[4] + quantity[5] + quantity[6] + quantity[7] + quantity[8] + quantity[9];

		NumberFormat nf = new DecimalFormat("###,###,###,###,###");
		System.out.println("total quantity: " + nf.format(totalquantity));

		poInsertToolingTotalQuantityTextField.setText(nf.format(totalquantity));

		for( int i = 0; i <= cntToolingProduct; i++){
			if(!poInsertToolingProductQuantityTextField[i].getText().equals("") && !notInteger(poInsertToolingProductQuantityTextField[i].getText().replace(",", "")) && !poInsertToolingProductPriceTextField[i].getText().equals("") && !notDouble(poInsertToolingProductPriceTextField[i].getText())){
				quantity[i] = Integer.parseInt(poInsertToolingProductQuantityTextField[i].getText().replace(",", ""));
				price[i] = Double.parseDouble(poInsertToolingProductPriceTextField[i].getText());
				totalprice[i] = quantity[i] * price[i];
			}
		}


		tp = totalprice[0] + totalprice[1] + totalprice[2] + totalprice[3] + totalprice[4] + totalprice[5] + totalprice[6] + totalprice[7] + totalprice[8] + totalprice[9];

		NumberFormat pf = new DecimalFormat("###,###,###,###,###.##");
		System.out.println("total price: " + pf.format(tp));

		poInsertToolingTotalPriceTextField.setText(pf.format(tp));
	}
	
	private void setPrice(){
		double [] price = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int [] quantity = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		double [] totalprice = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		double tp = 0;


		for( int i = 0; i <= cntToolingProduct; i++){
			if(!poInsertToolingProductQuantityTextField[i].getText().equals("") && !notInteger(poInsertToolingProductQuantityTextField[i].getText().replace(",", "")) && !poInsertToolingProductPriceTextField[i].getText().replace(",", "").equals("") && !notDouble(poInsertToolingProductPriceTextField[i].getText().replace(",", ""))){
				quantity[i] = Integer.parseInt(poInsertToolingProductQuantityTextField[i].getText().replace(",", ""));
				price[i] = Double.parseDouble(poInsertToolingProductPriceTextField[i].getText().replace(",", ""));
				totalprice[i] = quantity[i] * price[i];
			}
		}

		tp = totalprice[0] + totalprice[1] + totalprice[2] + totalprice[3] + totalprice[4] + totalprice[5] + totalprice[6] + totalprice[7] + totalprice[8] + totalprice[9];

		DecimalFormat nf = new DecimalFormat("###,###,###,###,##0.00");

		poInsertToolingTotalPriceTextField.setText(nf.format(tp));
	}

	private void usePOPanel() {
		d.setContentPane(poInsertToolingScrollPane);
		d.setVisible(true);		
	}
}

