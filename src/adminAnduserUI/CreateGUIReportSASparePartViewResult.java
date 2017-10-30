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

import domain.IRBS;
import domain.DataSA;

public class CreateGUIReportSASparePartViewResult {
	private JScrollPane poInsertSparePartScrollPane;
	private JPanel poInsertSparePartPanel;
	private JPanel errorPanel;

	private JLabel poInsertSparePartLabel;
	private JTextField[] poInsertSparePartNoTextField = new JTextField[1000];
	private JTextField[] poInsertSparePartTotalQuantityTextField = new JTextField[1000];
	private JTextField[] poInsertSparePartPRODUCTTextField = new JTextField[1000];
	private JTextField[][] poInsertSparePartSANoTextField = new JTextField[1000][1000];
	private JTextField[][] poInsertSparePartSAUnitTextField = new JTextField[1000][1000];
	private JTextField[][] poInsertSparePartSAInvoiceNoTextField = new JTextField[1000][1000];
	private JTextField[][] poInsertSparePartSARemainTextField = new JTextField[1000][1000];
	private JTextField[][] poInsertSparePartSAETDTextField = new JTextField[1000][1000];
	private int cntSparePartSA = -1;
	private int gbccntSparePartSA = 61;
	private int totalquantity = 0;
	private String SAremain;
	NumberFormat nf = new DecimalFormat("###,###,###,###,###");
	
	private ArrayList<String> poNumber;
	private ArrayList<String> PRODUCT;
	private ArrayList<String> subCode;
	private ArrayList<String> ETD;
	private ArrayList<String> vendor;
	private JFrame theFrame;
	private JDialog d;
	
	private ArrayList<DataSA> SAStatement;


	public CreateGUIReportSASparePartViewResult(JFrame theFrame, ArrayList<String> poNumber, ArrayList<String> PRODUCT, ArrayList<String> subCode, ArrayList<String> ETD, ArrayList<String> vendor){
		this.theFrame = theFrame;
		this.poNumber = poNumber;
		this.PRODUCT = PRODUCT;
		this.subCode = subCode;
		this.ETD = ETD;
		this.vendor = vendor;
		initUI();
	}

	private void initUI() {
		createDialog();
		storeData();
		setUP();
		for(int k = 0; k < poNumber.size(); k++){
			IRBS irbs = new IRBS();
			SAStatement = irbs.SAStatement(Integer.parseInt(poNumber.get(k)), PRODUCT.get(k) + subCode.get(k));
			totalquantity = irbs.getQuantitySASparePartreport(Integer.parseInt(poNumber.get(k)), PRODUCT.get(k), subCode.get(k));
			cntSparePartSA = -1 + SAStatement.size();
			setSARemain(k);
			try {
				irbs.getCon().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		usePOPanel();
	}
	
	private void createDialog() {
		d = new JDialog(theFrame, "SA Spare Part report: " + PRODUCT, true);
		d.setSize(1250,600);
		d.setLocationRelativeTo(null);
		d.setResizable(true);
	}

	private void storeData(){
		IRBS irbs = new IRBS();
		SAStatement = irbs.SAStatement(Integer.parseInt(poNumber.get(0)), PRODUCT.get(0) + subCode.get(0));
		totalquantity = irbs.getQuantitySASparePartreport(Integer.parseInt(poNumber.get(0)), PRODUCT.get(0), subCode.get(0));
		cntSparePartSA = cntSparePartSA + SAStatement.size();
		gbccntSparePartSA = gbccntSparePartSA + SAStatement.size() - 1;
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
		poInsertSparePartLabel = new JLabel("Please view the result of SA Spare Part report");
		gbc.weightx = 0;
		gbc.gridwidth = 5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.insets = new Insets(5, 5, 5, 5);
		poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);

		for(int k = 0; k < poNumber.size(); k++){
			IRBS irbs = new IRBS();
			SAStatement = irbs.SAStatement(Integer.parseInt(poNumber.get(k)), PRODUCT.get(k) + subCode.get(k));
			totalquantity = irbs.getQuantitySASparePartreport(Integer.parseInt(poNumber.get(k)), PRODUCT.get(k), subCode.get(k));
			try {
				irbs.getCon().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(poNumber.get(k));
			
			gbc.gridwidth = 5;
			gbc.gridx = 0;
			gbc.gridy = 1 + k * 100;
			poInsertSparePartPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);

			poInsertSparePartLabel = new JLabel("P.O.");
			gbc.gridwidth = 1;
			gbc.ipady = 10;
			gbc.gridx = 0;
			gbc.gridy = 2 + k * 100;
			gbc.weightx = 1;
			poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);

			poInsertSparePartNoTextField[k] = new JTextField(poNumber.get(k));
			poInsertSparePartNoTextField[k].setEditable(false);
			gbc.ipady = 10;
			gbc.gridx = 0;
			gbc.gridy = 3 + k * 100;
			gbc.weightx = 1;
			poInsertSparePartPanel.add(poInsertSparePartNoTextField[k], gbc);

			poInsertSparePartLabel = new JLabel("Part No");
			gbc.gridwidth = 1;
			gbc.ipady = 10;
			gbc.gridx = 1;
			gbc.gridy = 2 + k * 100;
			gbc.weightx = 1;
			poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);
			
			poInsertSparePartPRODUCTTextField[k] = new JTextField(PRODUCT.get(k) + subCode.get(k));
			poInsertSparePartPRODUCTTextField[k].setEditable(false);
			gbc.ipady = 10;
			gbc.gridx = 1;
			gbc.gridy = 3 + k * 100;
			gbc.weightx = 1;
			poInsertSparePartPanel.add(poInsertSparePartPRODUCTTextField[k], gbc);
			
			poInsertSparePartLabel = new JLabel("Quantity");
			gbc.gridwidth = 1;
			gbc.ipady = 10;
			gbc.gridx = 2;
			gbc.gridy = 2 + k * 100;
			gbc.weightx = 1;
			poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);

			poInsertSparePartTotalQuantityTextField[k] = new JTextField(nf.format(totalquantity));
			poInsertSparePartTotalQuantityTextField[k].setEditable(false);
			gbc.ipady = 10;
			gbc.gridx = 2;
			gbc.gridy = 3 + k * 100;
			gbc.weightx = 1;
			poInsertSparePartPanel.add(poInsertSparePartTotalQuantityTextField[k], gbc);
			
			
			poInsertSparePartLabel = new JLabel("SA No.");
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 4 + k * 100;
			poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);

			poInsertSparePartLabel = new JLabel("Unit Shipment");
			gbc.gridwidth = 1;
			gbc.gridx = 1;
			gbc.gridy = 4 + k * 100;
			poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);

			poInsertSparePartLabel = new JLabel("Invoice No");
			gbc.gridwidth = 1;
			gbc.gridx = 2;
			gbc.gridy = 4 + k * 100;
			poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);

			poInsertSparePartLabel = new JLabel("Remain");
			gbc.gridwidth = 1;
			gbc.gridx = 3;
			gbc.gridy = 4 + k * 100;
			poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);
			
			poInsertSparePartLabel = new JLabel("ETD");
			gbc.gridwidth = 1;
			gbc.gridx = 4;
			gbc.gridy = 4 + k * 100;
			poInsertSparePartPanel.add(poInsertSparePartLabel, gbc);

			for(int i = 0; i < SAStatement.size(); i++){
				poInsertSparePartSANoTextField[k][i] = new JTextField(SAStatement.get(i).getSaNo());
				poInsertSparePartSANoTextField[k][i].setEditable(false);
				gbc.gridwidth = 1;
				gbc.gridx = 0;
				gbc.gridy = 61 + i + k * 100;
				poInsertSparePartPanel.add(poInsertSparePartSANoTextField[k][i], gbc);

				NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
				poInsertSparePartSAUnitTextField[k][i] = new JTextField(nf.format(Integer.parseInt(SAStatement.get(i).getSaUnit())));
				poInsertSparePartSAUnitTextField[k][i].setEditable(false);
				gbc.gridwidth = 1;
				gbc.gridx = 1;
				gbc.gridy = 61 + i + k * 100;
				poInsertSparePartPanel.add(poInsertSparePartSAUnitTextField[k][i], gbc);

				poInsertSparePartSAInvoiceNoTextField[k][i] = new JTextField(SAStatement.get(i).getSaInoviceNo());
				poInsertSparePartSAInvoiceNoTextField[k][i].setEditable(false);
				gbc.gridwidth = 1;
				gbc.gridx = 2;
				gbc.gridy = 61 + i + k * 100;
				poInsertSparePartPanel.add(poInsertSparePartSAInvoiceNoTextField[k][i], gbc);

				poInsertSparePartSARemainTextField[k][i] = new JTextField();
				gbc.gridwidth = 1;
				gbc.gridx = 3;
				gbc.gridy = 61 + i + k * 100;
				poInsertSparePartSARemainTextField[k][i].setEditable(false);
				poInsertSparePartPanel.add(poInsertSparePartSARemainTextField[k][i], gbc);
				
				poInsertSparePartSAETDTextField[k][i] = new JTextField(SAStatement.get(i).getETD());
				poInsertSparePartSAETDTextField[k][i].setEditable(false);
				gbc.gridwidth = 1;
				gbc.gridx = 4;
				gbc.gridy = 61 + i + k * 100;
				poInsertSparePartPanel.add(poInsertSparePartSAETDTextField[k][i], gbc);
			}
		}
	}

	public JScrollPane insertSparePartPane(){
		return poInsertSparePartScrollPane;
	}

	private void setSARemain(int k){
		NumberFormat nf = new DecimalFormat("###,###,###,###,###");
		int remain = 0;
		for(int i = 0; i <= cntSparePartSA; i++){
			if(i == 0){
				if(!poInsertSparePartSAUnitTextField[k][i].getText().equals("") && !notInteger(poInsertSparePartSAUnitTextField[k][i].getText().replace(",", ""))){
					remain = totalquantity - Integer.parseInt(poInsertSparePartSAUnitTextField[k][i].getText().replace(",", ""));
					poInsertSparePartSARemainTextField[k][i].setText(nf.format(remain));
					System.out.println("remain: " + nf.format(remain));
				}else{
					poInsertSparePartSARemainTextField[k][i].setText(nf.format(totalquantity));
				}
			}else{
				if(!poInsertSparePartSAUnitTextField[k][i].getText().equals("") && !notInteger(poInsertSparePartSAUnitTextField[k][i].getText().replace(",", ""))){
					remain = totalquantity;
					for( int j = 0; j <= i; j++){
						remain = remain - Integer.parseInt(poInsertSparePartSAUnitTextField[k][j].getText().replace(",", ""));
					}
					poInsertSparePartSARemainTextField[k][i].setText(nf.format(remain));
					System.out.println("remain: " + nf.format(remain));
				}else{
					poInsertSparePartSARemainTextField[k][i].setText(poInsertSparePartSARemainTextField[k][i-1].getText());
				}
			}
		}

		if(cntSparePartSA == -1){
			SAremain = Integer.toString(totalquantity);
			System.out.println(SAremain);
		}else{
			SAremain = poInsertSparePartSARemainTextField[k][cntSparePartSA].getText();
			System.out.println(SAremain);
		}
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
	
	private void usePOPanel() {
		d.setContentPane(poInsertSparePartScrollPane);
		d.setVisible(true);		
	}
}

