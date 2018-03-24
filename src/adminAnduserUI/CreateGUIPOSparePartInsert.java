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
import java.util.Calendar;
import java.util.Date;

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
import domain.DateValidator;
import domain.IRBS;

public class CreateGUIPOSparePartInsert {
	private JScrollPane poInsertSparePartScrollPane;
	private JPanel poInsertSparePartPanel;
	private JPanel errorPanel;
	private DateValidator dateValidator;

	private JLabel poInsertSparePartLabel;
	private JLabel[] poInsertSparePartCountLabel = new JLabel[100];
	private JLabel[] poInsertSparePartProductNoLabel = new JLabel[100];
	private JLabel[] poInsertSparePartProductSubCodeLabel = new JLabel[100];
	private JLabel[] poInsertSparePartProductQuantityLabel = new JLabel[100];
	private JLabel[] poInsertSparePartProductSRDLabel = new JLabel[100];
	private JLabel[] poInsertSparePartProductETDLabel = new JLabel[100];
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
	private JTextField poInsertSparePartVendorTextField;
	private JTextField[] poInsertSparePartOrderDateTextField = new JTextField[100];
	private JTextField poInsertSparePartTotalQuantityTextField;
	private JTextField poInsertSparePartTotalPriceTextField;
	
	private JTextField[] poInsertSparePartSubCodeTextField = new JTextField[100];
	private JTextField[] poInsertSparePartProductNoTextField = new JTextField[100];
	private JTextField[] poInsertSparePartProductPriceTextField = new JTextField[100];
	private JTextField[] poInsertSparePartProductQuantityTextField = new JTextField[100];
	private JTextField[] poInsertSparePartProductSRDTextField = new JTextField[100];
	private JTextField[] poInsertSparePartProductETDTextField = new JTextField[100];
	private JTextField[] poInsertSparePartProductVendorTextField = new JTextField[100];
	private JTextField[] poInsertSparePartProductFixedCostTextField = new JTextField[100];
	private JTextField[] poInsertSparePartDescriptionTextField = new JTextField[100];
	private JTextField[] poInsertSparePartDutyCodeTextField = new JTextField[100];
	
	private JTextField[][] poInsertSparePartSANoTextField = new JTextField[100][1000];
	private JTextField[][] poInsertSparePartSAUnitTextField = new JTextField[100][1000];
	private JTextField[][] poInsertSparePartSAInvoiceNoTextField = new JTextField[100][1000];
	private JTextField[][] poInsertSparePartSARemainTextField = new JTextField[100][1000];
	private JTextField[][] poInsertSparePartSAETDTextField =  new JTextField[100][1000];
	
	private JTextArea poInsertSparePartRemarkTextArea;
	private JComboBox customerName;
	
	private JButton poInsertSparePartIncreaseOrderDateButton;
	private JButton poInsertSparePartDeleteOrderDateButton;
	private JButton poInsertSparePartIncreaseProductButton;
	private JButton poInsertSparePartDeleteProductButton;
	private JButton[] poInsertSparePartShowSAButton = new JButton[100];
	private JButton[] poInsertSparePartIncreaseSAButton = new JButton[100];
	private JButton[] poInsertSparePartDeleteSAButton = new JButton[100];
	private JButton poInsertSparePartProductButton;
	
	private int cntSparePartOrderDate = 0;
	private int cntSparePartProduct = 0;
	private int[] cntSparePartSA = new int[1000];
	
	private int gbccntSparePartOrderDate = 5;
	private int[] gbccntSparePartProduct = new int[100];
	private int gbccntSparePartSA = 205;
	
	private int totalquantity = 0;
	private String SAremain;

	public CreateGUIPOSparePartInsert(){
		initUI();
	}

	private void initUI() {
		setUP();
	}

	private void setUP() {
		poInsertSparePartPanel = new JPanel(new GridBagLayout());
		poInsertSparePartScrollPane = new JScrollPane(poInsertSparePartPanel);
		GridBagConstraints gbc = new GridBagConstraints();
		dateValidator = new DateValidator();

		// ************************* poPanel UI SetUP
		// *************************
		// ========================= poPanel Level One UI Create
		// =========================
		poInsertSparePartLabel = new JLabel("Please insert Spare Part Purchase Order");
		gbc.weightx = 0;
		gbc.gridwidth = 9;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.insets = new Insets(5, 5, 5, 5);
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);

		gbc.gridwidth = 12;
		gbc.gridx = 0;
		gbc.gridy = 1;
		poInsertSparePartPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
		poInsertSparePartLabel = new JLabel("Customer Name");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 5, 0, 5);
		poInsertSparePartLabel.setVerticalAlignment(JLabel.BOTTOM);
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);

		IRBS irbs = new IRBS();
		customerName = new JComboBox(irbs.customerNameStatement().toArray());
		customerName.setEditable(false);
		customerName.setSelectedItem(irbs.customerNameStatement().get(0));
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
		poInsertSparePartPanel.add(customerName, gbc);
		
		poInsertSparePartLabel = new JLabel("Order Date");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 2;
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);
		
		poInsertSparePartLabel = new JLabel("P.O.");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1;
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);
		
		poInsertSparePartIncreaseOrderDateButton = new JButton("Increase");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 3;
		poInsertSparePartIncreaseOrderDateButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cntSparePartOrderDate++;
				if(cntSparePartOrderDate < 20){
					
					poInsertSparePartOrderDateTextField[cntSparePartOrderDate] = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 1;
					gbc.gridy = gbccntSparePartOrderDate;
					poInsertSparePartOrderDateTextField[cntSparePartOrderDate].addKeyListener(new KeyListener(){
						@Override
						public void keyTyped(KeyEvent e) {}

						@Override
						public void keyPressed(KeyEvent e) {}

						@Override
						public void keyReleased(KeyEvent e) {
							for(int i = 0; i <= cntSparePartOrderDate; i++){
								if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){  
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
						}
					});
					poInsertSparePartPanel.add(poInsertSparePartOrderDateTextField[cntSparePartOrderDate], gbc);
					
					poInsertSparePartPanel.revalidate();  
					poInsertSparePartPanel.repaint();
					gbccntSparePartOrderDate++;
				}else{
					JOptionPane
					.showMessageDialog(
							poInsertSparePartPanel,
							"The maximum order date is 20.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		poInsertSparePartPanel.add(poInsertSparePartIncreaseOrderDateButton, gbc);
		
		poInsertSparePartDeleteOrderDateButton = new JButton("Delete");
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 3;
		poInsertSparePartDeleteOrderDateButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cntSparePartOrderDate > 0){
					poInsertSparePartPanel.remove(poInsertSparePartOrderDateTextField[cntSparePartOrderDate]);
					cntSparePartOrderDate--;
					gbccntSparePartOrderDate--;
				}else{
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"Error: There is at least one Order Date in purchase order.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				
				poInsertSparePartPanel.revalidate();  
				poInsertSparePartPanel.repaint();
			}
			
		});
		poInsertSparePartPanel.add(poInsertSparePartDeleteOrderDateButton, gbc);
		
		poInsertSparePartLabel = new JLabel("Total Quantity");
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy = 3;
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);
		
		poInsertSparePartLabel = new JLabel("Total Price");
		gbc.gridwidth = 2;
		gbc.gridx = 4;
		gbc.gridy = 3;
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);
		
		poInsertSparePartLabel = new JLabel("Vendor");
		gbc.gridwidth = 1;
		gbc.gridx = 6;
		gbc.gridy = 3;
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);
		
		poInsertSparePartNoTextField = new JTextField();
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 5, 5, 5);
		poInsertSparePartPanel.add(poInsertSparePartNoTextField, gbc);

		poInsertSparePartOrderDateTextField[0] = new JTextField();
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
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){  
					if(checkMM(poInsertSparePartOrderDateTextField[0].getText())){
						poInsertSparePartOrderDateTextField[0].setText(poInsertSparePartOrderDateTextField[0].getText() + "/");
					}
					if(checkDD(poInsertSparePartOrderDateTextField[0].getText())){
						poInsertSparePartOrderDateTextField[0].setText(poInsertSparePartOrderDateTextField[0].getText() + "/");
					}
					poInsertSparePartPanel.revalidate();  
					poInsertSparePartPanel.repaint();
				}
			}
		});
		poInsertSparePartPanel.add(poInsertSparePartOrderDateTextField[0], gbc);
		
		poInsertSparePartTotalQuantityTextField = new JTextField("0");
		poInsertSparePartTotalQuantityTextField.setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy = 4;
		poInsertSparePartPanel.add(poInsertSparePartTotalQuantityTextField, gbc);
		
		poInsertSparePartTotalPriceTextField = new JTextField("0.00");
		poInsertSparePartTotalPriceTextField.setEditable(false);
		gbc.gridwidth = 2;
		gbc.gridx = 4;
		gbc.gridy = 4;
		poInsertSparePartPanel.add(poInsertSparePartTotalPriceTextField, gbc);
		
		poInsertSparePartVendorTextField = new JTextField();
		gbc.gridwidth = 1;
		gbc.gridx = 6;
		gbc.gridy = 4;
		poInsertSparePartPanel.add(poInsertSparePartVendorTextField, gbc);
		
		gbc.gridwidth = 12;
		gbc.gridx = 0;
		gbc.gridy = 49;
		poInsertSparePartPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
		poInsertSparePartLabel = new JLabel("Remark");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 50;
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);

		poInsertSparePartRemarkTextArea = new JTextArea();
		gbc.gridwidth = 12;
		gbc.gridx = 0;
		gbc.gridy = 51;
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
		
		gbc.gridwidth = 12;
		gbc.gridx = 0;
		gbc.gridy = 52;
		poInsertSparePartPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
		//the area of increase / delete the part product
		poInsertSparePartLabel = new JLabel("Part");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 53;
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);
		
		poInsertSparePartIncreaseProductButton = new JButton("Increase");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 54;
		poInsertSparePartIncreaseProductButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cntSparePartProduct < 100){
					cntSparePartProduct++;
					final int finalcntSparePartProduct = cntSparePartProduct;
					cntSparePartSA[finalcntSparePartProduct] = -1;
					
					poInsertSparePartCountLabel[finalcntSparePartProduct] = new  JLabel(Integer.toString(cntSparePartProduct+1));
					gbc.gridwidth = 1;
					gbc.gridx = 0;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] - 1;
					poInsertSparePartPanel.add(poInsertSparePartCountLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartProductVendorLabel[finalcntSparePartProduct] = new JLabel("Vendor");
					gbc.gridwidth = 1;
					gbc.gridx = 0;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct];
					poInsertSparePartPanel.add(poInsertSparePartProductVendorLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartProductNoLabel[finalcntSparePartProduct] = new JLabel("Part No");
					gbc.gridwidth = 1;
					gbc.gridx = 1;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct];
					poInsertSparePartPanel.add(poInsertSparePartProductNoLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartProductSubCodeLabel[finalcntSparePartProduct] = new JLabel("Sub Code");
					gbc.gridwidth = 1;
					gbc.gridx = 2;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct];
					poInsertSparePartPanel.add(poInsertSparePartProductSubCodeLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartProductDescriptionLabel[finalcntSparePartProduct] = new JLabel("Description");
					gbc.gridwidth = 3;
					gbc.gridx = 3;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct];
					poInsertSparePartPanel.add(poInsertSparePartProductDescriptionLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartProductQuantityLabel[finalcntSparePartProduct] = new JLabel("Quantity");
					gbc.gridwidth = 1;
					gbc.gridx = 6;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct];
					poInsertSparePartPanel.add(poInsertSparePartProductQuantityLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartProductETDLabel[finalcntSparePartProduct] = new JLabel("Delivery Date");
					gbc.gridwidth = 1;
					gbc.gridx = 7;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct];
					poInsertSparePartPanel.add(poInsertSparePartProductETDLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartProductSRDLabel[finalcntSparePartProduct] = new JLabel("Ship To Arrive Date");
					gbc.gridwidth = 1;
					gbc.gridx = 8;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct];
					poInsertSparePartPanel.add(poInsertSparePartProductSRDLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartProductUnitPriceLabel[finalcntSparePartProduct] = new JLabel("Unit Price");
					gbc.gridwidth = 1;
					gbc.gridx = 9;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct];
					poInsertSparePartPanel.add(poInsertSparePartProductUnitPriceLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartProductDutyCodeLabel[finalcntSparePartProduct] = new JLabel("Duty Code");
					gbc.gridwidth = 1;
					gbc.gridx = 10;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct];
					poInsertSparePartPanel.add(poInsertSparePartProductDutyCodeLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartProductFixedCostLabel[finalcntSparePartProduct] = new JLabel("Fixed Cost");
					gbc.gridwidth = 1;
					gbc.gridx = 11;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct];
					poInsertSparePartPanel.add(poInsertSparePartProductFixedCostLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartProductVendorTextField[finalcntSparePartProduct] = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 0;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 1;
					poInsertSparePartPanel.add(poInsertSparePartProductVendorTextField[finalcntSparePartProduct], gbc);
					
					poInsertSparePartProductNoTextField[finalcntSparePartProduct]  = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 1;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 1;
					poInsertSparePartProductNoTextField[finalcntSparePartProduct].addKeyListener(new KeyListener(){
						@Override
						public void keyTyped(KeyEvent e) {}

						@Override
						public void keyPressed(KeyEvent e) {}

						@Override
						public void keyReleased(KeyEvent e) {
							IRBS irbs = new IRBS();
							String [] maintenanceStatement = irbs.maintenanceStatement(poInsertSparePartProductNoTextField[finalcntSparePartProduct].getText());
							if(!poInsertSparePartProductNoTextField[finalcntSparePartProduct].getText().equals("")){
								poInsertSparePartDescriptionTextField[finalcntSparePartProduct].setText(maintenanceStatement[1]);	
								poInsertSparePartDutyCodeTextField[finalcntSparePartProduct].setText(maintenanceStatement[2]);	
								poInsertSparePartProductFixedCostTextField[finalcntSparePartProduct].setText(maintenanceStatement[3]);
								poInsertSparePartProductVendorTextField[finalcntSparePartProduct].setText(maintenanceStatement[5]);
							}
//							if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){  
//								if(poInsertSparePartProductNoTextField[finalcntSparePartProduct].getText().length() == 2 && notInteger(poInsertSparePartProductNoTextField[finalcntSparePartProduct].getText()) == false){
//									poInsertSparePartProductNoTextField[finalcntSparePartProduct].setText(poInsertSparePartProductNoTextField[finalcntSparePartProduct].getText() + "-");
//								}
//							}
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
					poInsertSparePartPanel.add(poInsertSparePartProductNoTextField[finalcntSparePartProduct], gbc);
					
					poInsertSparePartSubCodeTextField[finalcntSparePartProduct]  = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 2;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 1;
					poInsertSparePartPanel.add(poInsertSparePartSubCodeTextField[finalcntSparePartProduct], gbc);
					
					poInsertSparePartDescriptionTextField[finalcntSparePartProduct] = new JTextField();
					poInsertSparePartDescriptionTextField[finalcntSparePartProduct].setEditable(false);
					gbc.gridwidth = 3;
					gbc.gridx = 3;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 1;
					poInsertSparePartPanel.add(poInsertSparePartDescriptionTextField[finalcntSparePartProduct], gbc);

					poInsertSparePartProductQuantityTextField[finalcntSparePartProduct]  = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 6;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 1;
					poInsertSparePartProductQuantityTextField[finalcntSparePartProduct].addKeyListener(new KeyListener(){
						@Override
						public void keyTyped(KeyEvent e) {}

						@Override
						public void keyPressed(KeyEvent e) {}

						@Override
						public void keyReleased(KeyEvent e) {
							setQuantity();
							setSARemain(finalcntSparePartProduct, cntSparePartSA[finalcntSparePartProduct]);

							NumberFormat nf = new DecimalFormat("###,###,###,###,###");	
							if(!StringUtils.isEmptyOrWhitespaceOnly(poInsertSparePartProductQuantityTextField[finalcntSparePartProduct].getText())){
								poInsertSparePartProductQuantityTextField[finalcntSparePartProduct].setText(nf.format(Integer.parseInt(poInsertSparePartProductQuantityTextField[finalcntSparePartProduct].getText().replace(",", ""))));
							}
							
							poInsertSparePartPanel.revalidate();  
							poInsertSparePartPanel.repaint();
						}
					});
					poInsertSparePartPanel.add(poInsertSparePartProductQuantityTextField[finalcntSparePartProduct], gbc);
					
					poInsertSparePartProductETDTextField[finalcntSparePartProduct] = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 7;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 1;
					poInsertSparePartProductETDTextField[finalcntSparePartProduct].addKeyListener(new KeyListener(){
						@Override
						public void keyTyped(KeyEvent e) {}

						@Override
						public void keyPressed(KeyEvent e) {}

						@Override
						public void keyReleased(KeyEvent e) {
							if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){  
								if(checkMM(poInsertSparePartProductETDTextField[finalcntSparePartProduct].getText())){
									poInsertSparePartProductETDTextField[finalcntSparePartProduct].setText(poInsertSparePartProductETDTextField[finalcntSparePartProduct].getText() + "/");
								}
								if(checkDD(poInsertSparePartProductETDTextField[finalcntSparePartProduct].getText())){
									poInsertSparePartProductETDTextField[finalcntSparePartProduct].setText(poInsertSparePartProductETDTextField[finalcntSparePartProduct].getText() + "/");
								}
								poInsertSparePartPanel.revalidate();  
								poInsertSparePartPanel.repaint();
							}
						}
					});
					poInsertSparePartPanel.add(poInsertSparePartProductETDTextField[finalcntSparePartProduct], gbc);
					
					poInsertSparePartProductSRDTextField[finalcntSparePartProduct] = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 8;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 1;
					poInsertSparePartProductSRDTextField[finalcntSparePartProduct].addKeyListener(new KeyListener(){
						@Override
						public void keyTyped(KeyEvent e) {}

						@Override
						public void keyPressed(KeyEvent e) {}

						@Override
						public void keyReleased(KeyEvent e) {
							if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
								if(checkMM(poInsertSparePartProductSRDTextField[finalcntSparePartProduct].getText())){
									poInsertSparePartProductSRDTextField[finalcntSparePartProduct].setText(poInsertSparePartProductSRDTextField[finalcntSparePartProduct].getText() + "/");
								}
								if(checkDD(poInsertSparePartProductSRDTextField[finalcntSparePartProduct].getText())){
									poInsertSparePartProductSRDTextField[finalcntSparePartProduct].setText(poInsertSparePartProductSRDTextField[finalcntSparePartProduct].getText() + "/");
								}
								poInsertSparePartProductETDTextField[finalcntSparePartProduct].setText(dateValidator.ETDcalculation(poInsertSparePartProductSRDTextField[finalcntSparePartProduct].getText()));
								poInsertSparePartPanel.revalidate();  
								poInsertSparePartPanel.repaint();
							}
						}
					});
					poInsertSparePartPanel.add(poInsertSparePartProductSRDTextField[finalcntSparePartProduct], gbc);

					poInsertSparePartProductPriceTextField[finalcntSparePartProduct]  = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 9;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 1;
					poInsertSparePartProductPriceTextField[finalcntSparePartProduct].addKeyListener(new KeyListener(){
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
					poInsertSparePartPanel.add(poInsertSparePartProductPriceTextField[finalcntSparePartProduct], gbc);
					
					poInsertSparePartDutyCodeTextField[finalcntSparePartProduct] = new JTextField();
					poInsertSparePartDutyCodeTextField[finalcntSparePartProduct].setEditable(false);
					gbc.gridwidth = 1;
					gbc.gridx = 10;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 1;
					poInsertSparePartPanel.add(poInsertSparePartDutyCodeTextField[finalcntSparePartProduct], gbc);
					
					poInsertSparePartProductFixedCostTextField[finalcntSparePartProduct] = new JTextField();
					gbc.gridwidth = 1;
					gbc.gridx = 11;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 1;
					poInsertSparePartPanel.add(poInsertSparePartProductFixedCostTextField[finalcntSparePartProduct], gbc);
					
					poInsertSparePartSANoLabel[finalcntSparePartProduct] = new JLabel("SA No.");
					gbc.gridwidth = 1;
					gbc.gridx = 0;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 2;
					poInsertSparePartPanel.add(poInsertSparePartSANoLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartShowSAButton[finalcntSparePartProduct] = new JButton("Hide SA");
					gbc.gridwidth = 1;
					gbc.gridx = 1;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 2;
					poInsertSparePartShowSAButton[finalcntSparePartProduct].addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							showSA(finalcntSparePartProduct);
						}
					});
					poInsertSparePartPanel.add(poInsertSparePartShowSAButton[finalcntSparePartProduct], gbc);
					
					poInsertSparePartSAUnitLabel[finalcntSparePartProduct] = new JLabel("Unit Shipped");
					gbc.gridwidth = 1;
					gbc.gridx = 2;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 3;
					poInsertSparePartPanel.add(poInsertSparePartSAUnitLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartSAInvoiceNoLabel[finalcntSparePartProduct] = new JLabel("Invoice No");
					gbc.gridwidth = 1;
					gbc.gridx = 3;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 3;
					poInsertSparePartPanel.add(poInsertSparePartSAInvoiceNoLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartSARemainLabel[finalcntSparePartProduct] = new JLabel("Units Remain");
					gbc.gridwidth = 2;
					gbc.gridx = 4;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 3;
					poInsertSparePartPanel.add(poInsertSparePartSARemainLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartSAETDLabel[finalcntSparePartProduct] = new JLabel("ETD Date");
					gbc.gridwidth = 1;
					gbc.gridx = 6;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 3;
					poInsertSparePartPanel.add(poInsertSparePartSAETDLabel[finalcntSparePartProduct], gbc);
					
					poInsertSparePartIncreaseSAButton[finalcntSparePartProduct] = new JButton("Increase");
					gbc.gridwidth = 1;
					gbc.gridx = 0;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 3;
					cntSparePartSA[finalcntSparePartProduct] = -1;
					poInsertSparePartIncreaseSAButton[finalcntSparePartProduct].addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							cntSparePartSA[finalcntSparePartProduct]++;
							
							poInsertSparePartSANoTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]] = new JTextField("SA");
							gbc.gridwidth = 1;
							gbc.gridx = 0;
							gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + cntSparePartSA[finalcntSparePartProduct];
							poInsertSparePartPanel.add(poInsertSparePartSANoTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]], gbc);
							
							poInsertSparePartSAUnitTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]] = new JTextField();
							gbc.gridwidth = 1;
							gbc.gridx = 2;
							gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + cntSparePartSA[finalcntSparePartProduct];
							poInsertSparePartSAUnitTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]].addKeyListener(new KeyListener(){
								public void keyTyped(KeyEvent e) {}

								public void keyPressed(KeyEvent e) {}

								public void keyReleased(KeyEvent e) {
									setSARemain(finalcntSparePartProduct, cntSparePartSA[finalcntSparePartProduct]);
									
									for(int i = 0; i <= cntSparePartSA[finalcntSparePartProduct]; i++){
										NumberFormat nf = new DecimalFormat("###,###,###,###,###");		
										if(!StringUtils.isEmptyOrWhitespaceOnly(poInsertSparePartSAUnitTextField[finalcntSparePartProduct][i].getText())){
											poInsertSparePartSAUnitTextField[finalcntSparePartProduct][i].setText(nf.format(Integer.parseInt(poInsertSparePartSAUnitTextField[finalcntSparePartProduct][i].getText().replace(",", ""))));
										}
									}
									
									poInsertSparePartPanel.revalidate();  
									poInsertSparePartPanel.repaint();
								}

							});
							poInsertSparePartPanel.add(poInsertSparePartSAUnitTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]], gbc);
							
							poInsertSparePartSAInvoiceNoTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]] = new JTextField("LR-");
							gbc.gridwidth = 1;
							gbc.gridx = 3;
							gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + cntSparePartSA[finalcntSparePartProduct];
							poInsertSparePartPanel.add(poInsertSparePartSAInvoiceNoTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]], gbc);
							
							poInsertSparePartSARemainTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]] = new JTextField();
							gbc.gridwidth = 2;
							gbc.gridx = 4;
							gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + cntSparePartSA[finalcntSparePartProduct];
							poInsertSparePartSARemainTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]].setEditable(false);
							poInsertSparePartPanel.add(poInsertSparePartSARemainTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]], gbc);
													
							poInsertSparePartSAETDTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]] = new JTextField();
							gbc.gridwidth = 1;
							gbc.gridx = 6;
							gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + cntSparePartSA[finalcntSparePartProduct];
							poInsertSparePartSAETDTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]].addKeyListener(new KeyListener(){
								@Override
								public void keyTyped(KeyEvent e) {}

								@Override
								public void keyPressed(KeyEvent e) {}

								@Override
								public void keyReleased(KeyEvent e) {
									for(int i = 0; i <= cntSparePartSA[finalcntSparePartProduct]; i++){
										if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){  
											if(checkMM(poInsertSparePartSAETDTextField[finalcntSparePartProduct][i].getText())){
												poInsertSparePartSAETDTextField[finalcntSparePartProduct][i].setText(poInsertSparePartSAETDTextField[finalcntSparePartProduct][i].getText() + "/");
											}
											if(checkDD(poInsertSparePartSAETDTextField[finalcntSparePartProduct][i].getText())){
												poInsertSparePartSAETDTextField[finalcntSparePartProduct][i].setText(poInsertSparePartSAETDTextField[finalcntSparePartProduct][i].getText() + "/");
											}
											poInsertSparePartPanel.revalidate();  
											poInsertSparePartPanel.repaint();						   
										}
									}
								}
							});
							poInsertSparePartPanel.add(poInsertSparePartSAETDTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]], gbc);
							
							poInsertSparePartPanel.revalidate();  
							poInsertSparePartPanel.repaint();
						}
					});
					poInsertSparePartPanel.add(poInsertSparePartIncreaseSAButton[finalcntSparePartProduct], gbc);
					
					poInsertSparePartDeleteSAButton[finalcntSparePartProduct] = new JButton("Delete");
					gbc.gridwidth = 1;
					gbc.gridx = 1;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 3;
					poInsertSparePartDeleteSAButton[finalcntSparePartProduct].addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							if(cntSparePartSA[finalcntSparePartProduct] >= 0){
								poInsertSparePartPanel.remove(poInsertSparePartSANoTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]]);
								poInsertSparePartPanel.remove(poInsertSparePartSAUnitTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]]);
								poInsertSparePartPanel.remove(poInsertSparePartSAInvoiceNoTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]]);
								poInsertSparePartPanel.remove(poInsertSparePartSARemainTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]]);
								poInsertSparePartPanel.remove(poInsertSparePartSAETDTextField[finalcntSparePartProduct][cntSparePartSA[finalcntSparePartProduct]]);
								cntSparePartSA[finalcntSparePartProduct]--;
							}else{
								JOptionPane
								.showMessageDialog(
										errorPanel,
										"Error: There is no SA in purchase order.",
										"Error", JOptionPane.ERROR_MESSAGE);
							}
							poInsertSparePartPanel.revalidate();  
							poInsertSparePartPanel.repaint();
						}
					});
					poInsertSparePartPanel.add(poInsertSparePartDeleteSAButton[finalcntSparePartProduct], gbc);
					
					poJSeparator[finalcntSparePartProduct] = new JSeparator(JSeparator.HORIZONTAL);
					gbc.gridwidth = 12;
					gbc.gridx = 0;
					gbc.gridy = gbccntSparePartProduct[finalcntSparePartProduct] + 5000;
					poInsertSparePartPanel.add(poJSeparator[finalcntSparePartProduct], gbc);
					
					poInsertSparePartPanel.revalidate();  
					poInsertSparePartPanel.repaint();
					gbccntSparePartProduct[finalcntSparePartProduct] = gbccntSparePartProduct[finalcntSparePartProduct] + 1000;
				}else{
					JOptionPane
					.showMessageDialog(
							poInsertSparePartPanel,
							"The maximum part no is 100.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		poInsertSparePartPanel.add(poInsertSparePartIncreaseProductButton, gbc);
		
		poInsertSparePartDeleteProductButton = new JButton("Delete");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 54;
		poInsertSparePartDeleteProductButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(cntSparePartProduct > 0){
					poInsertSparePartPanel.remove(poInsertSparePartCountLabel[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductNoLabel[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductSubCodeLabel[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductQuantityLabel[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductETDLabel[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductSRDLabel[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductDescriptionLabel[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductUnitPriceLabel[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductFixedCostLabel[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductDutyCodeLabel[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductVendorLabel[cntSparePartProduct]);

					poInsertSparePartPanel.remove(poInsertSparePartProductNoTextField[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartSubCodeTextField[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartDescriptionTextField[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductQuantityTextField[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductETDTextField[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductSRDTextField[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductPriceTextField[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductFixedCostTextField[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartDutyCodeTextField[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartProductVendorTextField[cntSparePartProduct]);
					
					poInsertSparePartPanel.remove(poInsertSparePartSANoLabel[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartSAUnitLabel[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartSAInvoiceNoLabel[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartSARemainLabel[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartSAETDLabel[cntSparePartProduct]);
					
					for(int i = 0; i <= cntSparePartSA[cntSparePartProduct]; i++){
						poInsertSparePartPanel.remove(poInsertSparePartSANoTextField[cntSparePartProduct][i]);
						poInsertSparePartPanel.remove(poInsertSparePartSAUnitTextField[cntSparePartProduct][i]);
						poInsertSparePartPanel.remove(poInsertSparePartSAInvoiceNoTextField[cntSparePartProduct][i]);
						poInsertSparePartPanel.remove(poInsertSparePartSARemainTextField[cntSparePartProduct][i]);
						poInsertSparePartPanel.remove(poInsertSparePartSAETDTextField[cntSparePartProduct][i]);
					}
					
					poInsertSparePartPanel.remove(poInsertSparePartShowSAButton[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartIncreaseSAButton[cntSparePartProduct]);
					poInsertSparePartPanel.remove(poInsertSparePartDeleteSAButton[cntSparePartProduct]);

					poInsertSparePartPanel.remove(poJSeparator[cntSparePartProduct]);
					
					cntSparePartProduct--;
					gbccntSparePartProduct[cntSparePartProduct]-=1000;
				}else{
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"Error: There is at least one product in purchase order.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				
				setQuantity();
				
				poInsertSparePartPanel.revalidate();  
				poInsertSparePartPanel.repaint();
			}
			
		});
		poInsertSparePartPanel.add(poInsertSparePartDeleteProductButton, gbc);
		
		gbc.gridwidth = 12;
		gbc.gridx = 0;
		gbc.gridy = 55;
		poInsertSparePartPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
		//the area of the first part 
		poInsertSparePartCountLabel[0] = new JLabel("1");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 200;
		poInsertSparePartPanel.add(poInsertSparePartCountLabel[0], gbc);
		
		poInsertSparePartProductVendorLabel[0] = new JLabel("Vendor");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 201;
		poInsertSparePartPanel.add(poInsertSparePartProductVendorLabel[0], gbc);
		
		poInsertSparePartProductNoLabel[0] = new JLabel("Part No");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 201;
		poInsertSparePartPanel.add(poInsertSparePartProductNoLabel[0], gbc);
		
		poInsertSparePartProductSubCodeLabel[0] = new JLabel("Sub Code");
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 201;
		poInsertSparePartPanel.add(poInsertSparePartProductSubCodeLabel[0], gbc);
		
		poInsertSparePartProductDescriptionLabel[0] = new JLabel("Description");
		gbc.gridwidth = 3;
		gbc.gridx = 3;
		gbc.gridy = 201;
		poInsertSparePartPanel.add(poInsertSparePartProductDescriptionLabel[0], gbc);
		
		poInsertSparePartProductQuantityLabel[0] = new JLabel("Quantity");
		gbc.gridwidth = 1;
		gbc.gridx = 6;
		gbc.gridy = 201;
		poInsertSparePartPanel.add(poInsertSparePartProductQuantityLabel[0], gbc);
		
		poInsertSparePartProductETDLabel[0] = new JLabel("Delivery Date");
		gbc.gridwidth = 1;
		gbc.gridx = 7;
		gbc.gridy = 201;
		poInsertSparePartPanel.add(poInsertSparePartProductETDLabel[0], gbc);
		
		poInsertSparePartProductSRDLabel[0] = new JLabel("Ship To Arrive Date");
		gbc.gridwidth = 1;
		gbc.gridx = 8;
		gbc.gridy = 201;
		poInsertSparePartPanel.add(poInsertSparePartProductSRDLabel[0], gbc);
		
		poInsertSparePartProductUnitPriceLabel[0] = new JLabel("Unit Price");
		gbc.gridwidth = 1;
		gbc.gridx = 9;
		gbc.gridy = 201;
		poInsertSparePartPanel.add(poInsertSparePartProductUnitPriceLabel[0], gbc);
		
		poInsertSparePartProductDutyCodeLabel[0] = new JLabel("Duty Code");
		gbc.gridwidth = 1;
		gbc.gridx = 10;
		gbc.gridy = 201;
		poInsertSparePartPanel.add(poInsertSparePartProductDutyCodeLabel[0], gbc);
		
		poInsertSparePartProductFixedCostLabel[0] = new JLabel("Fixed Cost");
		gbc.gridwidth = 1;
		gbc.gridx = 11;
		gbc.gridy = 201;
		poInsertSparePartPanel.add(poInsertSparePartProductFixedCostLabel[0], gbc);
		
		poInsertSparePartProductVendorTextField[0] = new JTextField();
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 202;
		poInsertSparePartPanel.add(poInsertSparePartProductVendorTextField[0], gbc);
		
		poInsertSparePartProductNoTextField[0] = new JTextField();
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 202;
		poInsertSparePartProductNoTextField[0].addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				IRBS irbs = new IRBS();
				String [] maintenanceStatement = irbs.maintenanceStatement(poInsertSparePartProductNoTextField[0].getText());
				if(!poInsertSparePartProductNoTextField[0].getText().equals("")){
					poInsertSparePartDescriptionTextField[0].setText(maintenanceStatement[1]);	
					poInsertSparePartDutyCodeTextField[0].setText(maintenanceStatement[2]);	
					poInsertSparePartProductFixedCostTextField[0].setText(maintenanceStatement[3]);
					poInsertSparePartProductVendorTextField[0].setText(maintenanceStatement[5]);
					poInsertSparePartVendorTextField.setText(maintenanceStatement[5]);
				}

//				if(poInsertSparePartProductNoTextField[0].getText().length() == 2 && notInteger(poInsertSparePartProductNoTextField[0].getText()) == false){
//					poInsertSparePartProductNoTextField[0].setText(poInsertSparePartProductNoTextField[0].getText() + "-");
//				}
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
		poInsertSparePartPanel.add(poInsertSparePartProductNoTextField[0], gbc);
		
		poInsertSparePartSubCodeTextField[0] = new JTextField();
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 202;
		poInsertSparePartPanel.add(poInsertSparePartSubCodeTextField[0], gbc);
		
		poInsertSparePartDescriptionTextField[0] = new JTextField();
		poInsertSparePartDescriptionTextField[0].setEditable(false);
		gbc.gridwidth = 3;
		gbc.gridx = 3;
		gbc.gridy = 202;
		poInsertSparePartPanel.add(poInsertSparePartDescriptionTextField[0], gbc);

		poInsertSparePartProductQuantityTextField[0] = new JTextField();
		gbc.gridwidth = 1;
		gbc.gridx = 6;
		gbc.gridy = 202;
		poInsertSparePartProductQuantityTextField[0].addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				
				setQuantity();
				setSARemain(0, cntSparePartSA[0]);

				NumberFormat nf = new DecimalFormat("###,###,###,###,###");
				if(!StringUtils.isEmptyOrWhitespaceOnly(poInsertSparePartProductQuantityTextField[0].getText())){
					poInsertSparePartProductQuantityTextField[0].setText(nf.format(Integer.parseInt(poInsertSparePartProductQuantityTextField[0].getText().replace(",", ""))));
				}
				
				poInsertSparePartPanel.revalidate();  
				poInsertSparePartPanel.repaint();
			}
		});
		poInsertSparePartPanel.add(poInsertSparePartProductQuantityTextField[0], gbc);
		
		poInsertSparePartProductETDTextField[0] = new JTextField();
		gbc.gridwidth = 1;
		gbc.gridx = 7;
		gbc.gridy = 202;
		poInsertSparePartProductETDTextField[0].addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(checkMM(poInsertSparePartProductETDTextField[0].getText())){
						poInsertSparePartProductETDTextField[0].setText(poInsertSparePartProductETDTextField[0].getText() + "/");
					}
					if(checkDD(poInsertSparePartProductETDTextField[0].getText())){
						poInsertSparePartProductETDTextField[0].setText(poInsertSparePartProductETDTextField[0].getText() + "/");
					}
					poInsertSparePartPanel.revalidate();  
					poInsertSparePartPanel.repaint();
				}
			}
		});
		poInsertSparePartPanel.add(poInsertSparePartProductETDTextField[0], gbc);
		
		poInsertSparePartProductSRDTextField[0] = new JTextField();
		gbc.gridwidth = 1;
		gbc.gridx = 8;
		gbc.gridy = 202;
		poInsertSparePartProductSRDTextField[0].addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(checkMM(poInsertSparePartProductSRDTextField[0].getText())){
						poInsertSparePartProductSRDTextField[0].setText(poInsertSparePartProductSRDTextField[0].getText() + "/");
					}
					if(checkDD(poInsertSparePartProductSRDTextField[0].getText())){
						poInsertSparePartProductSRDTextField[0].setText(poInsertSparePartProductSRDTextField[0].getText() + "/");
					}
					poInsertSparePartProductETDTextField[0].setText(dateValidator.ETDcalculation(poInsertSparePartProductSRDTextField[0].getText()));
					poInsertSparePartPanel.revalidate();  
					poInsertSparePartPanel.repaint();
				}
			}
		});
		poInsertSparePartPanel.add(poInsertSparePartProductSRDTextField[0], gbc);

		poInsertSparePartProductPriceTextField[0] = new JTextField();
		gbc.gridwidth = 1;
		gbc.gridx = 9;
		gbc.gridy = 202;
		poInsertSparePartProductPriceTextField[0].addKeyListener(new KeyListener(){
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
		poInsertSparePartPanel.add(poInsertSparePartProductPriceTextField[0], gbc);
		
		poInsertSparePartDutyCodeTextField[0] = new JTextField();
		poInsertSparePartDutyCodeTextField[0].setEditable(false);
		gbc.gridwidth = 1;
		gbc.gridx = 10;
		gbc.gridy = 202;
		poInsertSparePartPanel.add(poInsertSparePartDutyCodeTextField[0], gbc);
		
		poInsertSparePartProductFixedCostTextField[0] = new JTextField();
		gbc.gridwidth = 1;
		gbc.gridx = 11;
		gbc.gridy = 202;
		poInsertSparePartPanel.add(poInsertSparePartProductFixedCostTextField[0], gbc);
		
		poInsertSparePartSANoLabel[0] = new JLabel("SA No.");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 203;
		poInsertSparePartPanel.add(poInsertSparePartSANoLabel[0], gbc);
		
		poInsertSparePartShowSAButton[0] = new JButton("Hide SA");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 203;
		poInsertSparePartShowSAButton[0].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				showSA(0);
			}
		});
		poInsertSparePartPanel.add(poInsertSparePartShowSAButton[0], gbc);
		
		poInsertSparePartSAUnitLabel[0] = new JLabel("Unit Shipped");
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 204;
		poInsertSparePartPanel.add(poInsertSparePartSAUnitLabel[0], gbc);
		
		poInsertSparePartSAInvoiceNoLabel[0] = new JLabel("Invoice No");
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy = 204;
		poInsertSparePartPanel.add(poInsertSparePartSAInvoiceNoLabel[0], gbc);
		
		poInsertSparePartSARemainLabel[0] = new JLabel("Units Remain");
		gbc.gridwidth = 2;
		gbc.gridx = 4;
		gbc.gridy = 204;
		poInsertSparePartPanel.add(poInsertSparePartSARemainLabel[0], gbc);
		
		poInsertSparePartSAETDLabel[0] = new JLabel("ETD Date");
		gbc.gridwidth = 1;
		gbc.gridx = 6;
		gbc.gridy = 204;
		poInsertSparePartPanel.add(poInsertSparePartSAETDLabel[0], gbc);
		
		poInsertSparePartIncreaseSAButton[0] = new JButton("Increase");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 204;
		cntSparePartSA[0] = -1;
		poInsertSparePartIncreaseSAButton[0].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				cntSparePartSA[0]++;
				
				poInsertSparePartSANoTextField[0][cntSparePartSA[0]] = new JTextField("SA");
				gbc.gridwidth = 1;
				gbc.gridx = 0;
				gbc.gridy = gbccntSparePartSA;
				poInsertSparePartPanel.add(poInsertSparePartSANoTextField[0][cntSparePartSA[0]], gbc);
				
				poInsertSparePartSAUnitTextField[0][cntSparePartSA[0]] = new JTextField();
				gbc.gridwidth = 1;
				gbc.gridx = 2;
				gbc.gridy = gbccntSparePartSA;
				poInsertSparePartSAUnitTextField[0][cntSparePartSA[0]].addKeyListener(new KeyListener(){
					public void keyTyped(KeyEvent e) {}

					public void keyPressed(KeyEvent e) {}

					public void keyReleased(KeyEvent e) {
						setSARemain(0, cntSparePartSA[0]);
						
						for(int i = 0; i <= cntSparePartSA[0]; i++){
							NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
							if(!StringUtils.isEmptyOrWhitespaceOnly(poInsertSparePartSAUnitTextField[0][i].getText())){
								poInsertSparePartSAUnitTextField[0][i].setText(nf.format(Integer.parseInt(poInsertSparePartSAUnitTextField[0][i].getText().replace(",", ""))));
							}
						}
						
						poInsertSparePartPanel.revalidate();  
						poInsertSparePartPanel.repaint();
					}

				});
				poInsertSparePartPanel.add(poInsertSparePartSAUnitTextField[0][cntSparePartSA[0]], gbc);
				
				poInsertSparePartSAInvoiceNoTextField[0][cntSparePartSA[0]] = new JTextField("LR-");
				gbc.gridwidth = 1;
				gbc.gridx = 3;
				gbc.gridy = gbccntSparePartSA;
				poInsertSparePartPanel.add(poInsertSparePartSAInvoiceNoTextField[0][cntSparePartSA[0]], gbc);
				
				poInsertSparePartSARemainTextField[0][cntSparePartSA[0]] = new JTextField();
				gbc.gridwidth = 2;
				gbc.gridx = 4;
				gbc.gridy = gbccntSparePartSA;
				poInsertSparePartSARemainTextField[0][cntSparePartSA[0]].setEditable(false);
				poInsertSparePartPanel.add(poInsertSparePartSARemainTextField[0][cntSparePartSA[0]], gbc);
				
				poInsertSparePartSAETDTextField[0][cntSparePartSA[0]] = new JTextField();
				gbc.gridwidth = 1;
				gbc.gridx = 6;
				gbc.gridy = gbccntSparePartSA;
				poInsertSparePartSAETDTextField[0][cntSparePartSA[0]].addKeyListener(new KeyListener(){
					@Override
					public void keyTyped(KeyEvent e) {}

					@Override
					public void keyPressed(KeyEvent e) {}

					@Override
					public void keyReleased(KeyEvent e) {
						for(int i = 0; i <= cntSparePartSA[0]; i++){
							if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){  
								if(checkMM(poInsertSparePartSAETDTextField[0][i].getText())){
									poInsertSparePartSAETDTextField[0][i].setText(poInsertSparePartSAETDTextField[0][i].getText() + "/");
								}
								if(checkDD(poInsertSparePartSAETDTextField[0][i].getText())){
									poInsertSparePartSAETDTextField[0][i].setText(poInsertSparePartSAETDTextField[0][i].getText() + "/");
								}
								poInsertSparePartPanel.revalidate();  
								poInsertSparePartPanel.repaint();						   
							}
						}
					}
				});
				poInsertSparePartPanel.add(poInsertSparePartSAETDTextField[0][cntSparePartSA[0]], gbc);
				
				gbccntSparePartSA++;
				
				poInsertSparePartPanel.revalidate();  
				poInsertSparePartPanel.repaint();
				}
			
		});
		poInsertSparePartPanel.add(poInsertSparePartIncreaseSAButton[0], gbc);
		
		poInsertSparePartDeleteSAButton[0] = new JButton("Delete");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 204;
		poInsertSparePartDeleteSAButton[0].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(cntSparePartSA[0] >= 0){
					poInsertSparePartPanel.remove(poInsertSparePartSANoTextField[0][cntSparePartSA[0]]);
					poInsertSparePartPanel.remove(poInsertSparePartSAUnitTextField[0][cntSparePartSA[0]]);
					poInsertSparePartPanel.remove(poInsertSparePartSAInvoiceNoTextField[0][cntSparePartSA[0]]);
					poInsertSparePartPanel.remove(poInsertSparePartSARemainTextField[0][cntSparePartSA[0]]);
					poInsertSparePartPanel.remove(poInsertSparePartSAETDTextField[0][cntSparePartSA[0]]);
					gbccntSparePartSA--;
					cntSparePartSA[0]--;
				}else{
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"Error: There is no SA in purchase order.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				//setSARemain(0, cntSparePartSA[0]);
				poInsertSparePartPanel.revalidate();  
				poInsertSparePartPanel.repaint();
				}
		});
		poInsertSparePartPanel.add(poInsertSparePartDeleteSAButton[0], gbc);
		
		gbc.gridwidth = 12;
		gbc.gridx = 0;
		gbc.gridy = 405;
		poInsertSparePartPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		
		poInsertSparePartProductButton = new JButton("Save");
		gbc.gridwidth = 2;
		gbc.gridx = 10;
		gbc.gridy = 200000;
		poInsertSparePartProductButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				IRBS irbs = new IRBS();
				try{
					irbs.getCon().setAutoCommit(false);
					if(checkSparePartAllInsert()){
						if(irbs.checkSparePartDuplicatePO(Integer.parseInt(poInsertSparePartNoTextField.getText()))){
							JOptionPane
							.showMessageDialog(
									errorPanel,
									"Error: Duplicate Purchase Order",
									"Error", JOptionPane.ERROR_MESSAGE);
						}else{
							if (checkSparePartDateValid() != null){
								JOptionPane
								.showMessageDialog(
										errorPanel,
										"Error: Date is invalid",
										"Error", JOptionPane.ERROR_MESSAGE);
							}else{
								String ChangedDate, ChangedDate2, ChangedDate3;
								ChangedDate = poInsertSparePartOrderDateTextField[0].getText().substring(6, 8) + "/" + poInsertSparePartOrderDateTextField[0].getText().substring(0, 2) + "/" + poInsertSparePartOrderDateTextField[0].getText().substring(3, 5);
								irbs.insertSparePartPO(Integer.parseInt(poInsertSparePartNoTextField.getText()), ChangedDate, poInsertSparePartVendorTextField.getText(), poInsertSparePartRemarkTextArea.getText(), CustomerNameItem.getCustomerNameId(customerName.getSelectedItem().toString()));
								if(cntSparePartOrderDate > 0){
									for(int i = 1; i <= cntSparePartOrderDate; i++){
										ChangedDate = poInsertSparePartOrderDateTextField[i].getText().substring(6, 8) + "/" + poInsertSparePartOrderDateTextField[i].getText().substring(0, 2) + "/" + poInsertSparePartOrderDateTextField[i].getText().substring(3, 5);
										irbs.insertSparePartOrderDate(Integer.parseInt(poInsertSparePartNoTextField.getText()), ChangedDate);
									}
								}
								for(int i = 0; i <= cntSparePartProduct; i++){
									ChangedDate = poInsertSparePartProductSRDTextField[i].getText().substring(6, 8) + "/" + poInsertSparePartProductSRDTextField[i].getText().substring(0, 2) + "/" + poInsertSparePartProductSRDTextField[i].getText().substring(3, 5);
									ChangedDate2 = poInsertSparePartProductETDTextField[i].getText().substring(6, 8) + "/" + poInsertSparePartProductETDTextField[i].getText().substring(0, 2) + "/" + poInsertSparePartProductETDTextField[i].getText().substring(3, 5);
									if(cntSparePartSA[i] >= 0){
										irbs.insertSparePartOrderItem(Integer.parseInt(poInsertSparePartNoTextField.getText()), poInsertSparePartProductNoTextField[i].getText(), poInsertSparePartSubCodeTextField[i].getText(), Integer.parseInt(poInsertSparePartProductQuantityTextField[i].getText().replace(",", "")), Double.parseDouble(poInsertSparePartProductPriceTextField[i].getText()), poInsertSparePartProductVendorTextField[i].getText(), Double.parseDouble(poInsertSparePartProductFixedCostTextField[i].getText()), Integer.parseInt(poInsertSparePartSARemainTextField[i][cntSparePartSA[i]].getText().replace(",", "")), ChangedDate, ChangedDate2);
									}else{
										irbs.insertSparePartOrderItem(Integer.parseInt(poInsertSparePartNoTextField.getText()), poInsertSparePartProductNoTextField[i].getText(), poInsertSparePartSubCodeTextField[i].getText(), Integer.parseInt(poInsertSparePartProductQuantityTextField[i].getText().replace(",", "")), Double.parseDouble(poInsertSparePartProductPriceTextField[i].getText()), poInsertSparePartProductVendorTextField[i].getText(), Double.parseDouble(poInsertSparePartProductFixedCostTextField[i].getText()), Integer.parseInt(poInsertSparePartProductQuantityTextField[i].getText().replace(",", "")), ChangedDate, ChangedDate2);
									}
									for(int j = 0; j <= cntSparePartSA[i]; j++){
										ChangedDate3 = poInsertSparePartSAETDTextField[i][j].getText().substring(6, 8) + "/" + poInsertSparePartSAETDTextField[i][j].getText().substring(0, 2) + "/" + poInsertSparePartSAETDTextField[i][j].getText().substring(3, 5);
										irbs.insertSA(Integer.parseInt(poInsertSparePartNoTextField.getText()), poInsertSparePartSANoTextField[i][j].getText(), poInsertSparePartProductNoTextField[i].getText() + poInsertSparePartSubCodeTextField[i].getText(), poInsertSparePartSAInvoiceNoTextField[i][j].getText(), Integer.parseInt(poInsertSparePartSAUnitTextField[i][j].getText().replace(",", "")), ChangedDate3);
									}
								}

								poInsertReset();
								JOptionPane
								.showMessageDialog(
										null,
										"Success insert the new complete unit purchase order",
										"Success", JOptionPane.INFORMATION_MESSAGE);
							}
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
		poInsertSparePartPanel.add(poInsertSparePartProductButton, gbc);
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
		for(int i = 0; i <= cntSparePartProduct; i++){
			if(poInsertSparePartProductNoTextField[i].getText().equals(""))
				check = false;
			if(poInsertSparePartProductQuantityTextField[i].getText().equals(""))
				check = false;
			if(poInsertSparePartProductETDTextField[i].getText().equals(""))
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
				if(poInsertSparePartSAETDTextField[i][j].getText().equals(""))
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
				java.util.Date date = sdf.parse(poInsertSparePartProductETDTextField[i].getText());
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				String ChangedDate = poInsertSparePartProductETDTextField[i].getText().substring(6, 8) + "/" + poInsertSparePartProductETDTextField[i].getText().substring(0, 2) + "/" + poInsertSparePartProductETDTextField[i].getText().substring(3, 5);
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
		for(int i = 0; i <= cntSparePartProduct; i++){
			for(int j = 0; j <= cntSparePartSA[i]; j++){
				try {
					sdf.setLenient(false);
					java.util.Date date = sdf.parse(poInsertSparePartSAETDTextField[i][j].getText());
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());
					String ChangedDate = poInsertSparePartSAETDTextField[i][j].getText().substring(6, 8) + "/" + poInsertSparePartSAETDTextField[i][j].getText().substring(0, 2) + "/" + poInsertSparePartSAETDTextField[i][j].getText().substring(3, 5);
				} catch (ParseException e1) {
					exception = e1;
				} catch (Exception e1){
					exception = e1;
				}
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
			poInsertSparePartPanel.remove(poInsertSparePartProductETDLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductSRDLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductDescriptionLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductUnitPriceLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductFixedCostLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductDutyCodeLabel[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductVendorLabel[i]);
			
			poInsertSparePartPanel.remove(poInsertSparePartProductNoTextField[i]);
			poInsertSparePartPanel.remove(poInsertSparePartSubCodeTextField[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductQuantityTextField[i]);
			poInsertSparePartPanel.remove(poInsertSparePartProductETDTextField[i]);
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
			poInsertSparePartPanel.remove(poInsertSparePartIncreaseSAButton[i]);
			poInsertSparePartPanel.remove(poInsertSparePartDeleteSAButton[i]);
			
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
		poInsertSparePartProductETDTextField[0].setText(null);
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
	
	private void setSARemain(int i, int j){
		NumberFormat nf = new DecimalFormat("###,###,###,###,###");
		int remain = 0;

		for(int h = 0; h <= j; h++){
			if(h == 0){
				if(!poInsertSparePartSAUnitTextField[i][h].getText().equals("") && !notInteger(poInsertSparePartSAUnitTextField[i][h].getText().replace(",", ""))){
					remain = Integer.parseInt(poInsertSparePartProductQuantityTextField[i].getText().replace(",", "")) - Integer.parseInt(poInsertSparePartSAUnitTextField[i][h].getText().replace(",", ""));
					poInsertSparePartSARemainTextField[i][h].setText(nf.format(remain));
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
				}else{
					poInsertSparePartSARemainTextField[i][h].setText(poInsertSparePartSARemainTextField[i-1][h].getText());
				}
			}
		}
		
		if(j == -1){
			SAremain = Integer.toString(totalquantity);
		}else{
			SAremain = poInsertSparePartSARemainTextField[i][j].getText();
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

		DecimalFormat pf = new DecimalFormat("###,###,###,###,##0.00");

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

		DecimalFormat nf = new DecimalFormat("###,###,###,###,##0.00");

		poInsertSparePartTotalPriceTextField.setText(nf.format(tp));
	}
	
	private void showSA(int cntlocation) {
		try {
			if(poInsertSparePartShowSAButton[cntlocation].getText().equals("Show SA")) {
				poInsertSparePartShowSAButton[cntlocation].setText("Hide SA");
				poInsertSparePartSAUnitLabel[cntlocation].setVisible(true);
				poInsertSparePartSAInvoiceNoLabel[cntlocation].setVisible(true);
				poInsertSparePartSARemainLabel[cntlocation].setVisible(true);
				poInsertSparePartSAETDLabel[cntlocation].setVisible(true);
				poInsertSparePartIncreaseSAButton[cntlocation].setVisible(true);
				poInsertSparePartDeleteSAButton[cntlocation].setVisible(true);
				for(int j = 0; j < 1000; j++){
					final int finalj = j;
					poInsertSparePartSANoTextField[cntlocation][finalj].setVisible(true);
					poInsertSparePartSAUnitTextField[cntlocation][finalj].setVisible(true);
					poInsertSparePartSAInvoiceNoTextField[cntlocation][finalj].setVisible(true);
					poInsertSparePartSARemainTextField[cntlocation][finalj].setVisible(true);
					poInsertSparePartSAETDTextField[cntlocation][finalj].setVisible(true);
				}
			}else {
				poInsertSparePartShowSAButton[cntlocation].setText("Show SA");
				poInsertSparePartSAUnitLabel[cntlocation].setVisible(false);
				poInsertSparePartSAInvoiceNoLabel[cntlocation].setVisible(false);
				poInsertSparePartSARemainLabel[cntlocation].setVisible(false);
				poInsertSparePartSAETDLabel[cntlocation].setVisible(false);
				poInsertSparePartIncreaseSAButton[cntlocation].setVisible(false);
				poInsertSparePartDeleteSAButton[cntlocation].setVisible(false);
				for(int j = 0; j < 1000; j++){
					final int finalj = j;
					poInsertSparePartSANoTextField[cntlocation][finalj].setVisible(false);
					poInsertSparePartSAUnitTextField[cntlocation][finalj].setVisible(false);
					poInsertSparePartSAInvoiceNoTextField[cntlocation][finalj].setVisible(false);
					poInsertSparePartSARemainTextField[cntlocation][finalj].setVisible(false);
					poInsertSparePartSAETDTextField[cntlocation][finalj].setVisible(false);
				}
			}
		}catch(Exception ex) {
			
		}
	}
}

