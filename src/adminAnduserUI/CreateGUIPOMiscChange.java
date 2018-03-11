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

import domain.CustomerNameItem;
import domain.DataMiscOrderItem;
import domain.IRBS;

public class CreateGUIPOMiscChange {
	private JScrollPane poInsertMiscScrollPane;
	private JPanel poInsertMiscPanel;
	private JPanel errorPanel;

	private JLabel poInsertMiscLabel;
	private JTextField poInsertMiscNoTextField;
	private JTextField[] poInsertMiscProductNoTextField = new JTextField[100];
	private JTextField[] poInsertMiscProductPriceTextField = new JTextField[100];
	private JTextField[] poInsertMiscProductQuantityTextField = new JTextField[100];
	private JTextField[] poInsertMiscProductFixedCostTextField = new JTextField[100];
	private JTextField poInsertMiscVendorTextField;
	private JTextField[] poInsertMiscOrderDateTextField = new JTextField[100];
	private JTextField[] poInsertMiscACRONYMTextField = new JTextField[100];
	private JTextField[] poInsertMiscDescriptionTextField = new JTextField[100];
	private JTextField[] poInsertMiscDutyCodeTextField = new JTextField[100];
	private JTextField poInsertMiscTotalQuantityTextField;
	private JTextField poInsertMiscTotalPriceTextField;
	private JTextArea poInsertMiscRemarkTextArea;
	private JComboBox customerName;
	
	private JButton poInsertMiscIncreaseOrderDateButton;
	private JButton poInsertMiscDeleteOrderDateButton;
	private JButton poInsertMiscIncreaseProductButton;
	private JButton poInsertMiscDeleteProductButton;
	private JButton poInsertMiscProductButton;
	private int cntMiscOrderDate = 0;
	private int cntMiscProduct = 0;
	private int gbccntMiscOrderDate = 5;
	private int gbccntMiscProduct = 60;
	private int totalquantity = 0;
	
	private String poNumber;
	private JFrame theFrame;
	private JTable theTable;
	private String searchSQL;
	private JDialog d;
	
	private String orderDate;
	private String vendor;
	private String remark;
	private String getCustomerName;
	private String[] miscPurchaseOrderStatement;
	private ArrayList<String> miscOrderDateStatement;
	private ArrayList<DataMiscOrderItem> miscOrderItemStatement;


	public CreateGUIPOMiscChange(JFrame theFrame, JTable theTable, int poNumber, String searchSQL){
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
		for(int i = 0; i < miscOrderItemStatement.size(); i++)
			setMaintenance(i);
		setQuantity();
		setPrice();
		usePOPanel();
	}
	
	private void createDialog() {
		d = new JDialog(theFrame, "Change the purchase order", true);
		d.setSize(1250,800);
		d.setLocationRelativeTo(null);
		d.setResizable(true);
	}
	
	private void storeData(){
		IRBS irbs = new IRBS();
		miscPurchaseOrderStatement = irbs.miscPurchaseOrderStatement(Integer.parseInt(poNumber));
		orderDate = miscPurchaseOrderStatement[0];
		vendor = miscPurchaseOrderStatement[1];
		remark = miscPurchaseOrderStatement[2];
		getCustomerName = miscPurchaseOrderStatement[3];
		
		miscOrderDateStatement = irbs.miscOrderDateStatement(Integer.parseInt(poNumber));
		cntMiscOrderDate = cntMiscOrderDate + miscOrderDateStatement.size();
		gbccntMiscOrderDate = gbccntMiscOrderDate + miscOrderDateStatement.size();
		
		miscOrderItemStatement = irbs.miscOrderItemStatement(Integer.parseInt(poNumber));
		cntMiscProduct = cntMiscProduct + miscOrderItemStatement.size() - 1;
		gbccntMiscProduct = gbccntMiscProduct + miscOrderItemStatement.size() - 1;
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setUP() {
		poInsertMiscPanel = new JPanel(new GridBagLayout());
		poInsertMiscScrollPane = new JScrollPane(poInsertMiscPanel);
		GridBagConstraints gbc = new GridBagConstraints();

		// ************************* poPanel UI SetUP
		// *************************
		// ========================= poPanel Level One UI Create
		// =========================
		poInsertMiscLabel = new JLabel("Please change Misc Purchase Order");
		gbc.weightx = 0;
		gbc.gridwidth = 9;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.insets = new Insets(5, 5, 5, 5);
		poInsertMiscPanel.add(poInsertMiscLabel, gbc);
		
		gbc.gridwidth = 8;
		gbc.gridx = 0;
		gbc.gridy = 1;
		poInsertMiscPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
		poInsertMiscLabel = new JLabel("Customer Name");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 5, 0, 5);
		poInsertMiscLabel.setVerticalAlignment(JLabel.BOTTOM);
		poInsertMiscPanel.add(poInsertMiscLabel, gbc);
		
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
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.ipady = 10;
		poInsertMiscPanel.add(customerName, gbc);
		
		poInsertMiscLabel = new JLabel("Order Date");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 2;
		poInsertMiscPanel.add(poInsertMiscLabel, gbc);

		poInsertMiscLabel = new JLabel("P.O.");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		poInsertMiscPanel.add(poInsertMiscLabel, gbc);
		
		poInsertMiscLabel = new JLabel("Vendor");
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy = 3;
		poInsertMiscPanel.add(poInsertMiscLabel, gbc);
		
		poInsertMiscLabel = new JLabel("ACRONYM");
		gbc.gridwidth = 1;
		gbc.gridx = 4;
		gbc.gridy = 3;
		poInsertMiscPanel.add(poInsertMiscLabel, gbc);
		
		poInsertMiscLabel = new JLabel("Total Quantity");
		gbc.gridwidth = 1;
		gbc.gridx = 5;
		gbc.gridy = 3;
		poInsertMiscPanel.add(poInsertMiscLabel, gbc);
		
		poInsertMiscLabel = new JLabel("Total Price");
		gbc.gridwidth = 1;
		gbc.gridx = 6;
		gbc.gridy = 3;
		poInsertMiscPanel.add(poInsertMiscLabel, gbc);
		
		poInsertMiscIncreaseOrderDateButton = new JButton("Increase");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 3;
		poInsertMiscIncreaseOrderDateButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cntMiscOrderDate++;
				if(cntMiscOrderDate < 20){
					
					poInsertMiscOrderDateTextField[cntMiscOrderDate] = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 1;
					gbc.gridy = gbccntMiscOrderDate;
					poInsertMiscOrderDateTextField[cntMiscOrderDate].addKeyListener(new KeyListener(){
						@Override
						public void keyTyped(KeyEvent e) {}

						@Override
						public void keyPressed(KeyEvent e) {}

						@Override
						public void keyReleased(KeyEvent e) {
							for(int i = 0; i <= cntMiscOrderDate; i++){
								if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
									if(checkMM(poInsertMiscOrderDateTextField[i].getText())){
										poInsertMiscOrderDateTextField[i].setText(poInsertMiscOrderDateTextField[i].getText() + "/");
									}
									if(checkDD(poInsertMiscOrderDateTextField[i].getText())){
										poInsertMiscOrderDateTextField[i].setText(poInsertMiscOrderDateTextField[i].getText() + "/");
									}
									poInsertMiscPanel.revalidate();  
									poInsertMiscPanel.repaint();
								}
							}
						}
					});
					poInsertMiscPanel.add(poInsertMiscOrderDateTextField[cntMiscOrderDate], gbc);
					
					poInsertMiscPanel.revalidate();  
					poInsertMiscPanel.repaint();
					gbccntMiscOrderDate++;
				}else{
					JOptionPane
					.showMessageDialog(
							poInsertMiscPanel,
							"The maximum order date is 20.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		poInsertMiscPanel.add(poInsertMiscIncreaseOrderDateButton, gbc);
		
		poInsertMiscDeleteOrderDateButton = new JButton("Delete");
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 3;
		poInsertMiscDeleteOrderDateButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cntMiscOrderDate > 0){
					poInsertMiscPanel.remove(poInsertMiscOrderDateTextField[cntMiscOrderDate]);
					cntMiscOrderDate--;
					gbccntMiscOrderDate--;
				}else{
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"Error: There is at least one Order Date in purchase order.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				
				poInsertMiscPanel.revalidate();  
				poInsertMiscPanel.repaint();
			}
			
		});
		poInsertMiscPanel.add(poInsertMiscDeleteOrderDateButton, gbc);
		
		poInsertMiscNoTextField = new JTextField(poNumber);
		poInsertMiscNoTextField.setEditable(false);;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 4;
		poInsertMiscPanel.add(poInsertMiscNoTextField, gbc);

		poInsertMiscOrderDateTextField[0] = new JTextField(orderDate);
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 4;
		poInsertMiscOrderDateTextField[0].addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(checkMM(poInsertMiscOrderDateTextField[0].getText())){
						poInsertMiscOrderDateTextField[0].setText(poInsertMiscOrderDateTextField[0].getText() + "/");
					}
					if(checkDD(poInsertMiscOrderDateTextField[0].getText())){
						poInsertMiscOrderDateTextField[0].setText(poInsertMiscOrderDateTextField[0].getText() + "/");
					}
					poInsertMiscPanel.revalidate();  
					poInsertMiscPanel.repaint();
				}
			}
		});
		poInsertMiscPanel.add(poInsertMiscOrderDateTextField[0], gbc);
		
		for(int i = 1; i <= miscOrderDateStatement.size(); i++){
			poInsertMiscOrderDateTextField[i] = new JTextField(miscOrderDateStatement.get(i-1));
			gbc.gridwidth = 1;
			gbc.gridx = 1;
			gbc.gridy = 4 + i;
			poInsertMiscOrderDateTextField[i].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}
	
				@Override
				public void keyPressed(KeyEvent e) {}
	
				@Override
				public void keyReleased(KeyEvent e) {
					for(int i = 0; i < miscOrderDateStatement.size(); i++){
						if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
							if(checkMM(poInsertMiscOrderDateTextField[i].getText())){
								poInsertMiscOrderDateTextField[i].setText(poInsertMiscOrderDateTextField[i].getText() + "/");
							}
							if(checkDD(poInsertMiscOrderDateTextField[i].getText())){
								poInsertMiscOrderDateTextField[i].setText(poInsertMiscOrderDateTextField[i].getText() + "/");
							}
							poInsertMiscPanel.revalidate();  
							poInsertMiscPanel.repaint();
						}
					}
				}
			});
			poInsertMiscPanel.add(poInsertMiscOrderDateTextField[i], gbc);
		}

		poInsertMiscVendorTextField = new JTextField(vendor);
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy = 4;
		poInsertMiscPanel.add(poInsertMiscVendorTextField, gbc);
		
		poInsertMiscACRONYMTextField[0] = new JTextField();
		poInsertMiscACRONYMTextField[0].setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 4;
		gbc.gridy = 4;
		poInsertMiscPanel.add(poInsertMiscACRONYMTextField[0], gbc);
		
		poInsertMiscTotalQuantityTextField = new JTextField("0");
		gbc.gridwidth = 1;
		gbc.gridx = 5;
		gbc.gridy = 4;
		poInsertMiscTotalQuantityTextField.setEditable(false);
		poInsertMiscPanel.add(poInsertMiscTotalQuantityTextField, gbc);
		
		poInsertMiscTotalPriceTextField = new JTextField("0.00");
		gbc.gridwidth = 1;
		gbc.gridx = 6;
		gbc.gridy = 4;
		poInsertMiscTotalPriceTextField.setEditable(false);
		poInsertMiscPanel.add(poInsertMiscTotalPriceTextField, gbc);
		
		gbc.gridwidth = 8;
		gbc.gridx = 0;
		gbc.gridy = 52;
		poInsertMiscPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
		poInsertMiscLabel = new JLabel("Part No");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 53;
		poInsertMiscPanel.add(poInsertMiscLabel, gbc);
		
		poInsertMiscLabel = new JLabel("Quantity");
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 54;
		poInsertMiscPanel.add(poInsertMiscLabel, gbc);

		poInsertMiscLabel = new JLabel("Description");
		gbc.gridwidth = 4;
		gbc.gridx = 3;
		gbc.gridy = 54;
		poInsertMiscPanel.add(poInsertMiscLabel, gbc);
		
		poInsertMiscLabel = new JLabel("Unit Price");
		gbc.gridwidth = 1;
		gbc.gridx = 7;
		gbc.gridy = 54;
		poInsertMiscPanel.add(poInsertMiscLabel, gbc);
		
//		poInsertMiscLabel = new JLabel("Fixed Cost");
//		gbc.gridwidth = 1;
//		gbc.gridx = 8;
//		gbc.gridy = 53;
//		poInsertMiscPanel.add(poInsertMiscLabel, gbc);
//		
//		poInsertMiscLabel = new JLabel("Duty Code");
//		gbc.gridwidth = 1;
//		gbc.gridx = 9;
//		gbc.gridy = 53;
//		poInsertMiscPanel.add(poInsertMiscLabel, gbc);
		
		poInsertMiscIncreaseProductButton = new JButton("Increase");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 54;
		poInsertMiscIncreaseProductButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cntMiscProduct < 9){
					cntMiscProduct++;
					poInsertMiscProductNoTextField[cntMiscProduct]  = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 0;
					gbc.gridy = gbccntMiscProduct;
					poInsertMiscProductNoTextField[cntMiscProduct].addKeyListener(new KeyListener(){
						@Override
						public void keyTyped(KeyEvent e) {}

						@Override
						public void keyPressed(KeyEvent e) {}

						@Override
						public void keyReleased(KeyEvent e) {
							for(int i=0; i <= cntMiscProduct; i++){
								IRBS irbs = new IRBS();
								String [] maintenanceStatement = irbs.maintenanceStatement(poInsertMiscProductNoTextField[i].getText());
								if(!poInsertMiscProductNoTextField[i].getText().equals("")){
//									poInsertMiscDescriptionTextField[i].setText(maintenanceStatement[1]);	
									poInsertMiscDutyCodeTextField[i].setText(maintenanceStatement[2]);	
								}
								if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
									if(poInsertMiscProductNoTextField[i].getText().length() == 2 && notInteger(poInsertMiscProductNoTextField[i].getText()) == false){
										poInsertMiscProductNoTextField[i].setText(poInsertMiscProductNoTextField[i].getText() + "-");
									}
								}
								try {
									irbs.getCon().close();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							poInsertMiscPanel.revalidate();  
							poInsertMiscPanel.repaint();
						}
					});
					poInsertMiscPanel.add(poInsertMiscProductNoTextField[cntMiscProduct], gbc);

					poInsertMiscProductQuantityTextField[cntMiscProduct]  = new JTextField();
					gbc.gridx = 2;
					gbc.gridy = gbccntMiscProduct;
					poInsertMiscProductQuantityTextField[cntMiscProduct].addKeyListener(new KeyListener(){
						@Override
						public void keyTyped(KeyEvent e) {}

						@Override
						public void keyPressed(KeyEvent e) {}

						@Override
						public void keyReleased(KeyEvent e) {
							
							setQuantity();
							
							for(int i = 0; i <= cntMiscProduct; i++){
								NumberFormat nf = new DecimalFormat("###,###,###,###,###");	
								if(!StringUtils.isEmptyOrWhitespaceOnly(poInsertMiscProductQuantityTextField[i].getText())){
									poInsertMiscProductQuantityTextField[i].setText(nf.format(Integer.parseInt(poInsertMiscProductQuantityTextField[i].getText().replace(",", ""))));
								}
							}
							
							poInsertMiscPanel.revalidate();  
							poInsertMiscPanel.repaint();
						}
					});
					poInsertMiscPanel.add(poInsertMiscProductQuantityTextField[cntMiscProduct], gbc);
					
					poInsertMiscDescriptionTextField[cntMiscProduct] = new JTextField();
					gbc.gridwidth = 4;
					gbc.gridx = 3;
					gbc.gridy = gbccntMiscProduct;
					poInsertMiscPanel.add(poInsertMiscDescriptionTextField[cntMiscProduct], gbc);

					poInsertMiscProductPriceTextField[cntMiscProduct]  = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 7;
					gbc.gridy = gbccntMiscProduct;
					poInsertMiscProductPriceTextField[cntMiscProduct].addKeyListener(new KeyListener(){
						@Override
						public void keyTyped(KeyEvent e) {}

						@Override
						public void keyPressed(KeyEvent e) {}

						@Override
						public void keyReleased(KeyEvent e) {
							
							setPrice();
							if(e.getKeyChar() != '.')
								changePriceFormat(poInsertMiscProductPriceTextField[cntMiscProduct]);
							
							poInsertMiscPanel.revalidate();  
							poInsertMiscPanel.repaint();
						}
					});
					poInsertMiscPanel.add(poInsertMiscProductPriceTextField[cntMiscProduct], gbc);
					
					poInsertMiscProductFixedCostTextField[cntMiscProduct] = new JTextField();
//					gbc.gridwidth = 1;
//					gbc.gridx = 8;
//					gbc.gridy = gbccntMiscProduct;
//					poInsertMiscPanel.add(poInsertMiscProductFixedCostTextField[cntMiscProduct], gbc);
					
					poInsertMiscDutyCodeTextField[cntMiscProduct] = new JTextField();
//					poInsertMiscDutyCodeTextField[cntMiscProduct].setEditable(false);
//					gbc.gridwidth = 1;
//					gbc.gridx = 9;
//					gbc.gridy = gbccntMiscProduct;
//					poInsertMiscPanel.add(poInsertMiscDutyCodeTextField[cntMiscProduct], gbc);
					
					poInsertMiscPanel.revalidate();  
					poInsertMiscPanel.repaint();
					gbccntMiscProduct = gbccntMiscProduct + 1;
				}else{
					JOptionPane
					.showMessageDialog(
							poInsertMiscPanel,
							"The maximum part no is 10.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		poInsertMiscPanel.add(poInsertMiscIncreaseProductButton, gbc);
		
		poInsertMiscDeleteProductButton = new JButton("Delete");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 54;
		poInsertMiscDeleteProductButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(cntMiscProduct > 0){
					poInsertMiscPanel.remove(poInsertMiscProductNoTextField[cntMiscProduct]);
					poInsertMiscPanel.remove(poInsertMiscProductQuantityTextField[cntMiscProduct]);
					poInsertMiscPanel.remove(poInsertMiscProductPriceTextField[cntMiscProduct]);
					poInsertMiscPanel.remove(poInsertMiscProductFixedCostTextField[cntMiscProduct]);
					poInsertMiscPanel.remove(poInsertMiscDescriptionTextField[cntMiscProduct]);
					poInsertMiscPanel.remove(poInsertMiscDutyCodeTextField[cntMiscProduct]);
					cntMiscProduct--;
					gbccntMiscProduct--;
				}else{
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"Error: There is at least one product in purchase order.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				
				setQuantity();
				
				poInsertMiscPanel.revalidate();  
				poInsertMiscPanel.repaint();
			}
			
		});
		poInsertMiscPanel.add(poInsertMiscDeleteProductButton, gbc);
		
		for(int i = 0; i < miscOrderItemStatement.size(); i++){
			poInsertMiscProductNoTextField[i] = new JTextField(miscOrderItemStatement.get(i).getPRODUCT());
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 55 + i;
			poInsertMiscProductNoTextField[i].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {					
					for(int i = 0; i < miscOrderItemStatement.size(); i++){
						
						setMaintenance(i);
						
						if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
							if(poInsertMiscProductNoTextField[i].getText().length() == 2 && notInteger(poInsertMiscProductNoTextField[i].getText()) == false){
								poInsertMiscProductNoTextField[i].setText(poInsertMiscProductNoTextField[i].getText() + "-");
							}
						}

						poInsertMiscPanel.revalidate();  
						poInsertMiscPanel.repaint();
					}
				}
			});
			poInsertMiscPanel.add(poInsertMiscProductNoTextField[i], gbc);

			NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
			poInsertMiscProductQuantityTextField[i] = new JTextField(nf.format(Integer.parseInt(miscOrderItemStatement.get(i).getQuantity())));
			gbc.gridwidth = 1;
			gbc.gridx = 2;
			gbc.gridy = 55 + i;
			poInsertMiscProductQuantityTextField[i].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {

					setQuantity();

					for(int i = 0; i <= cntMiscProduct; i++){
						NumberFormat nf = new DecimalFormat("###,###,###,###,###");
						if(!StringUtils.isEmptyOrWhitespaceOnly(poInsertMiscProductQuantityTextField[i].getText())){
							poInsertMiscProductQuantityTextField[i].setText(nf.format(Integer.parseInt(poInsertMiscProductQuantityTextField[i].getText().replace(",", ""))));
						}
					}

					poInsertMiscPanel.revalidate();  
					poInsertMiscPanel.repaint();
				}
			});
			poInsertMiscPanel.add(poInsertMiscProductQuantityTextField[i], gbc);

			poInsertMiscDescriptionTextField[i] = new JTextField(miscOrderItemStatement.get(i).getDescription());
			gbc.gridwidth = 4;
			gbc.gridx = 3;
			gbc.gridy = 55 + i;
			poInsertMiscPanel.add(poInsertMiscDescriptionTextField[i], gbc);

			DecimalFormat df = new DecimalFormat("###,###,###,###,###.00");
			poInsertMiscProductPriceTextField[i] = new JTextField(df.format(Double.parseDouble(miscOrderItemStatement.get(i).getPrice())));
			gbc.gridwidth = 1;
			gbc.gridx = 7;
			gbc.gridy = 55 + i;
			poInsertMiscProductPriceTextField[i].addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {
					setPrice();
					for(int i = 0; i < miscOrderItemStatement.size(); i++){
						if(e.getKeyChar() != '.')
							changePriceFormat(poInsertMiscProductPriceTextField[i]);
					}
					
					poInsertMiscPanel.revalidate();  
					poInsertMiscPanel.repaint();
				}
			});
			poInsertMiscPanel.add(poInsertMiscProductPriceTextField[i], gbc);

			poInsertMiscProductFixedCostTextField[i] = new JTextField();
//			gbc.gridwidth = 1;
//			gbc.gridx = 8;
//			gbc.gridy = 55 + i;
//			poInsertMiscPanel.add(poInsertMiscProductFixedCostTextField[i], gbc);

			poInsertMiscDutyCodeTextField[i] = new JTextField();
//			poInsertMiscDutyCodeTextField[i].setEditable(false);
//			gbc.gridwidth = 1;
//			gbc.gridx = 9;
//			gbc.gridy = 55 + i;
//			poInsertMiscPanel.add(poInsertMiscDutyCodeTextField[i], gbc);
		}
		
		gbc.gridwidth = 8;
		gbc.gridx = 0;
		gbc.gridy = 199999;
		poInsertMiscPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
		poInsertMiscProductButton = new JButton("Save");
		gbc.gridwidth = 2;
		gbc.gridx = 6;
		gbc.gridy = 200000;
		poInsertMiscProductButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				IRBS irbs = new IRBS();
				try{
					irbs.getCon().setAutoCommit(false);
					if(checkMiscAllInsert()){
						if (checkMiscDateValid() != null){
							JOptionPane
							.showMessageDialog(
									errorPanel,
									"Error: Date is invalid",
									"Error", JOptionPane.ERROR_MESSAGE);
						}else{
							irbs.deletePO(Integer.parseInt(poNumber));
							SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
							String ChangedDate;
							ChangedDate = poInsertMiscOrderDateTextField[0].getText().substring(6, 8) + "/" + poInsertMiscOrderDateTextField[0].getText().substring(0, 2) + "/" + poInsertMiscOrderDateTextField[0].getText().substring(3, 5);
							irbs.insertMiscPO(Integer.parseInt(poInsertMiscNoTextField.getText()), ChangedDate, poInsertMiscVendorTextField.getText(), poInsertMiscRemarkTextArea.getText(), CustomerNameItem.getCustomerNameId(customerName.getSelectedItem().toString()));
							if(cntMiscOrderDate > 0){
								for(int i = 1; i <= cntMiscOrderDate; i++){
									ChangedDate = poInsertMiscOrderDateTextField[i].getText().substring(6, 8) + "/" + poInsertMiscOrderDateTextField[i].getText().substring(0, 2) + "/" + poInsertMiscOrderDateTextField[i].getText().substring(3, 5);
									irbs.insertMiscOrderDate(Integer.parseInt(poInsertMiscNoTextField.getText()), ChangedDate);
								}
							}
							for(int i = 0; i <= cntMiscProduct; i++){
								irbs.insertMiscOrderItem(Integer.parseInt(poInsertMiscNoTextField.getText()), poInsertMiscProductNoTextField[i].getText(), Integer.parseInt(poInsertMiscProductQuantityTextField[i].getText().replace(",", "")), Double.parseDouble(poInsertMiscProductPriceTextField[i].getText().replace(",", "")), poInsertMiscDescriptionTextField[i].getText());
							}
							d.dispose();
							repaintToShowAll();
							JOptionPane
							.showMessageDialog(
									null,
									"Success change the misc purchase order",
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
				}catch(Exception ex){
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
		poInsertMiscPanel.add(poInsertMiscProductButton, gbc);
		
		poInsertMiscLabel = new JLabel("Remark");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 200001;
		poInsertMiscPanel.add(poInsertMiscLabel, gbc);

		poInsertMiscRemarkTextArea = new JTextArea(remark);
		gbc.gridwidth = 11;
		gbc.gridx = 0;
		gbc.gridy = 200002;
		if(!remark.equals(""))
			poInsertMiscRemarkTextArea.setBackground(Color.YELLOW);
		poInsertMiscRemarkTextArea.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(!poInsertMiscRemarkTextArea.getText().equals("")){
					poInsertMiscRemarkTextArea.setBackground(Color.YELLOW);
				}else{
					poInsertMiscRemarkTextArea.setBackground(Color.WHITE);
				}
			}
		});
		poInsertMiscPanel.add(poInsertMiscRemarkTextArea, gbc);

	}

	private boolean checkMiscAllInsert(){
		boolean check = true;
		if(poInsertMiscNoTextField.getText().equals("")){
			check = false;
		}
		for(int i = 0; i <= cntMiscOrderDate; i++){
			if(poInsertMiscOrderDateTextField[i].getText().equals("")){
				check = false;
			}
		}
		if(poInsertMiscVendorTextField.getText().equals("")){
			check = false;
		}
		for(int i = 0; i <= cntMiscProduct; i++){
			if(poInsertMiscProductNoTextField[i].getText().equals(""))
				check = false;
			if(poInsertMiscProductQuantityTextField[i].getText().equals(""))
				check = false;
			if(poInsertMiscProductPriceTextField[i].getText().equals(""))
				check = false;
			if(poInsertMiscDescriptionTextField[i].getText().equals(""))
				check = false;
		}
		return check;
	}

	// ========================= check for Misc Insert Panel has insert correct date format
	// ========================= (If correct date format, return null. If incorrect date format, return exception.)
	private Exception checkMiscDateValid(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		Exception exception = null;
		for(int i = 0; i <= cntMiscOrderDate; i++){
			try {
				sdf.setLenient(false);
				java.util.Date date = sdf.parse(poInsertMiscOrderDateTextField[i].getText());
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				String ChangedDate = poInsertMiscOrderDateTextField[i].getText().substring(6, 8) + "/" + poInsertMiscOrderDateTextField[i].getText().substring(0, 2) + "/" + poInsertMiscOrderDateTextField[i].getText().substring(3, 5);
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
		for(int i = 1; i <= cntMiscOrderDate; i++){
			poInsertMiscPanel.remove(poInsertMiscOrderDateTextField[i]);
		}
		for(int i = 1; i <= cntMiscProduct; i++){
			poInsertMiscPanel.remove(poInsertMiscProductNoTextField[i]);
			poInsertMiscPanel.remove(poInsertMiscProductQuantityTextField[i]);
			poInsertMiscPanel.remove(poInsertMiscProductPriceTextField[i]);
			poInsertMiscPanel.remove(poInsertMiscProductFixedCostTextField[i]);
			poInsertMiscPanel.remove(poInsertMiscDescriptionTextField[i]);
			poInsertMiscPanel.remove(poInsertMiscDutyCodeTextField[i]);
		}
		
		cntMiscOrderDate = 0;
		cntMiscProduct = 0;
		gbccntMiscOrderDate = 5;
		gbccntMiscProduct = 60;
		totalquantity = 0;
		poInsertMiscNoTextField.setText(null);
		poInsertMiscOrderDateTextField[0].setText(null);
		poInsertMiscVendorTextField.setText(null);
		poInsertMiscACRONYMTextField[0].setText(null);
		poInsertMiscDescriptionTextField[0].setText(null);
		poInsertMiscDutyCodeTextField[0].setText(null);
		poInsertMiscTotalQuantityTextField.setText("0");
		poInsertMiscTotalPriceTextField.setText("0.00");
		poInsertMiscProductNoTextField[0].setText(null);
		poInsertMiscProductQuantityTextField[0].setText(null);
		poInsertMiscProductPriceTextField[0].setText(null);
		poInsertMiscProductFixedCostTextField[0].setText(null);
		poInsertMiscRemarkTextArea.setText(null);
		poInsertMiscRemarkTextArea.setBackground(Color.WHITE);
		
		poInsertMiscPanel.revalidate();  
		poInsertMiscPanel.repaint();
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
	
	public JScrollPane insertMiscPane(){
		return poInsertMiscScrollPane;
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
		
		maintenanceStatement = irbs.maintenanceStatement(poInsertMiscProductNoTextField[0].getText());
		poInsertMiscACRONYMTextField[0].setText(maintenanceStatement[0]);	
		
		maintenanceStatement = irbs.maintenanceStatement(poInsertMiscProductNoTextField[i].getText());
		if(!poInsertMiscProductNoTextField[i].getText().equals("")){
//			poInsertMiscDescriptionTextField[i].setText(maintenanceStatement[1]);	
			poInsertMiscDutyCodeTextField[i].setText(maintenanceStatement[2]);	
		}
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

		for( int i = 0; i <= cntMiscProduct; i++){
			if(!poInsertMiscProductQuantityTextField[i].getText().equals("") && !notInteger(poInsertMiscProductQuantityTextField[i].getText().replace(",", "")))
				quantity[i] = Integer.parseInt(poInsertMiscProductQuantityTextField[i].getText().replace(",", ""));
		}

		totalquantity = quantity[0] + quantity[1] + quantity[2] + quantity[3] + quantity[4] + quantity[5] + quantity[6] + quantity[7] + quantity[8] + quantity[9];

		NumberFormat nf = new DecimalFormat("###,###,###,###,###");
		System.out.println("total quantity: " + nf.format(totalquantity));

		poInsertMiscTotalQuantityTextField.setText(nf.format(totalquantity));

		for( int i = 0; i <= cntMiscProduct; i++){
			if(!poInsertMiscProductQuantityTextField[i].getText().equals("") && !notInteger(poInsertMiscProductQuantityTextField[i].getText().replace(",", "")) && !poInsertMiscProductPriceTextField[i].getText().equals("") && !notDouble(poInsertMiscProductPriceTextField[i].getText())){
				quantity[i] = Integer.parseInt(poInsertMiscProductQuantityTextField[i].getText().replace(",", ""));
				price[i] = Double.parseDouble(poInsertMiscProductPriceTextField[i].getText());
				totalprice[i] = quantity[i] * price[i];
			}
		}


		tp = totalprice[0] + totalprice[1] + totalprice[2] + totalprice[3] + totalprice[4] + totalprice[5] + totalprice[6] + totalprice[7] + totalprice[8] + totalprice[9];

		DecimalFormat pf = new DecimalFormat("###,###,###,###,###.00");
		System.out.println("total price: " + pf.format(tp));

		poInsertMiscTotalPriceTextField.setText(pf.format(tp));
	}
	
	private void setPrice(){
		double [] price = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int [] quantity = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		double [] totalprice = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		double tp = 0;


		for( int i = 0; i <= cntMiscProduct; i++){
			if(!poInsertMiscProductQuantityTextField[i].getText().equals("") && !notInteger(poInsertMiscProductQuantityTextField[i].getText().replace(",", "")) && !poInsertMiscProductPriceTextField[i].getText().equals("") && !notDouble(poInsertMiscProductPriceTextField[i].getText())){
				quantity[i] = Integer.parseInt(poInsertMiscProductQuantityTextField[i].getText().replace(",", ""));
				price[i] = Double.parseDouble(poInsertMiscProductPriceTextField[i].getText());
				totalprice[i] = quantity[i] * price[i];
			}
		}

		tp = totalprice[0] + totalprice[1] + totalprice[2] + totalprice[3] + totalprice[4] + totalprice[5] + totalprice[6] + totalprice[7] + totalprice[8] + totalprice[9];

		DecimalFormat nf = new DecimalFormat("###,###,###,###,###.00");
		System.out.println("total price: " + nf.format(tp));

		poInsertMiscTotalPriceTextField.setText(nf.format(tp));
	}

	private void usePOPanel() {
		d.setContentPane(poInsertMiscScrollPane);
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
	
	private void changePriceFormat(JTextField textField) {
		DecimalFormat nf = new DecimalFormat("###,###,###,###,###.##");
		textField.setText(nf.format(Double.parseDouble(textField.getText().replace(",", ""))));
	}
}

