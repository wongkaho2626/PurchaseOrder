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
import domain.DataCompleteunitOrderItem;
import domain.DataCompleteunitShipment;

public class CreateGUIReportSACompleteUnitViewResult {
	private JScrollPane poInsertCompleteUnitScrollPane;
	private JPanel poInsertCompleteUnitPanel;
	private JPanel errorPanel;

	private JLabel poInsertCompleteUnitLabel;
	private JTextField[] poInsertCompleteUnitNoTextField = new JTextField[1000];
	private JTextField[] poInsertCompleteUnitTotalQuantityTextField = new JTextField[1000];
	private JTextField[] poInsertCompleteUnitPRODUCTTextField = new JTextField[1000];
	private JTextField[][] poInsertCompleteUnitSANoTextField = new JTextField[1000][1000];
	private JTextField[][] poInsertCompleteUnitSAUnitTextField = new JTextField[1000][1000];
	private JTextField[][] poInsertCompleteUnitSAInvoiceNoTextField = new JTextField[1000][1000];
	private JTextField[][] poInsertCompleteUnitSARemainTextField = new JTextField[1000][1000];
	private int cntCompleteUnitSA = -1;
	private int gbccntCompleteUnitSA = 61;
	private int totalquantity = 0;
	private String SAremain;
	NumberFormat nf = new DecimalFormat("###,###,###,###,###");
	
	private ArrayList<String> poNumber;
	private ArrayList<String> subCode;
	private String PRODUCT;
	private JFrame theFrame;
	private JDialog d;
	
	private ArrayList<DataSA> SAStatement;


	public CreateGUIReportSACompleteUnitViewResult(JFrame theFrame, ArrayList<String> poNumber, String PRODUCT, ArrayList<String> subCode){
		this.poNumber = poNumber;
		this.theFrame = theFrame;
		this.PRODUCT = PRODUCT;
		this.subCode = subCode;
		initUI();
	}

	private void initUI() {
		createDialog();
		storeData();
		setUP();
		for(int k = 0; k < poNumber.size(); k++){
			IRBS irbs = new IRBS();
			SAStatement = irbs.SAStatement(Integer.parseInt(poNumber.get(k)), PRODUCT + subCode.get(k));
			totalquantity = irbs.getQuantitySAreport(Integer.parseInt(poNumber.get(k)), PRODUCT, subCode.get(k));
			cntCompleteUnitSA = -1 + SAStatement.size();
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
		d = new JDialog(theFrame, "SA Complete Unit report: " + PRODUCT, true);
		d.setSize(1250,600);
		d.setLocationRelativeTo(null);
		d.setResizable(true);
	}

	private void storeData(){
		IRBS irbs = new IRBS();
		SAStatement = irbs.SAStatement(Integer.parseInt(poNumber.get(0)), PRODUCT + subCode.get(0));
		totalquantity = irbs.getQuantitySAreport(Integer.parseInt(poNumber.get(0)), PRODUCT, subCode.get(0));
		cntCompleteUnitSA = cntCompleteUnitSA + SAStatement.size();
		gbccntCompleteUnitSA = gbccntCompleteUnitSA + SAStatement.size() - 1;
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
		poInsertCompleteUnitLabel = new JLabel("Please view the result of SA Complete Unit report");
		gbc.weightx = 0;
		gbc.gridwidth = 5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.insets = new Insets(5, 5, 5, 5);
		poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);

		for(int k = 0; k < poNumber.size(); k++){
			IRBS irbs = new IRBS();
			SAStatement = irbs.SAStatement(Integer.parseInt(poNumber.get(k)), PRODUCT + subCode.get(k));
			totalquantity = irbs.getQuantitySAreport(Integer.parseInt(poNumber.get(k)), PRODUCT, subCode.get(k));
			try {
				irbs.getCon().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(poNumber.get(k));
			
			gbc.gridwidth = 4;
			gbc.gridx = 0;
			gbc.gridy = 1 + k * 100;
			poInsertCompleteUnitPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);

			poInsertCompleteUnitLabel = new JLabel("P.O.");
			gbc.gridwidth = 1;
			gbc.ipady = 10;
			gbc.gridx = 0;
			gbc.gridy = 2 + k * 100;
			gbc.weightx = 1;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);

			poInsertCompleteUnitNoTextField[k] = new JTextField(poNumber.get(k));
			poInsertCompleteUnitNoTextField[k].setEditable(false);
			gbc.ipady = 10;
			gbc.gridx = 0;
			gbc.gridy = 3 + k * 100;
			gbc.weightx = 1;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitNoTextField[k], gbc);

			poInsertCompleteUnitLabel = new JLabel("Part No");
			gbc.gridwidth = 1;
			gbc.ipady = 10;
			gbc.gridx = 1;
			gbc.gridy = 2 + k * 100;
			gbc.weightx = 1;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);
			
			poInsertCompleteUnitPRODUCTTextField[k] = new JTextField(PRODUCT + subCode.get(k));
			poInsertCompleteUnitPRODUCTTextField[k].setEditable(false);
			gbc.ipady = 10;
			gbc.gridx = 1;
			gbc.gridy = 3 + k * 100;
			gbc.weightx = 1;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitPRODUCTTextField[k], gbc);
			
			poInsertCompleteUnitLabel = new JLabel("Quantity");
			gbc.gridwidth = 1;
			gbc.ipady = 10;
			gbc.gridx = 2;
			gbc.gridy = 2 + k * 100;
			gbc.weightx = 1;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);

			poInsertCompleteUnitTotalQuantityTextField[k] = new JTextField(nf.format(totalquantity));
			poInsertCompleteUnitTotalQuantityTextField[k].setEditable(false);
			gbc.ipady = 10;
			gbc.gridx = 2;
			gbc.gridy = 3 + k * 100;
			gbc.weightx = 1;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitTotalQuantityTextField[k], gbc);
			
			
			poInsertCompleteUnitLabel = new JLabel("SA No.");
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 4 + k * 100;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);

			poInsertCompleteUnitLabel = new JLabel("Unit Shipment");
			gbc.gridwidth = 1;
			gbc.gridx = 1;
			gbc.gridy = 4 + k * 100;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);

			poInsertCompleteUnitLabel = new JLabel("Invoice No");
			gbc.gridwidth = 1;
			gbc.gridx = 2;
			gbc.gridy = 4 + k * 100;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);

			poInsertCompleteUnitLabel = new JLabel("Remain");
			gbc.gridwidth = 1;
			gbc.gridx = 3;
			gbc.gridy = 4 + k * 100;
			poInsertCompleteUnitPanel.add(poInsertCompleteUnitLabel, gbc);

			for(int i = 0; i < SAStatement.size(); i++){
				poInsertCompleteUnitSANoTextField[k][i] = new JTextField(SAStatement.get(i).getSaNo());
				poInsertCompleteUnitSANoTextField[k][i].setEditable(false);
				gbc.gridwidth = 1;
				gbc.gridx = 0;
				gbc.gridy = 61 + i + k * 100;
				poInsertCompleteUnitPanel.add(poInsertCompleteUnitSANoTextField[k][i], gbc);

				NumberFormat nf = new DecimalFormat("###,###,###,###,###");			
				poInsertCompleteUnitSAUnitTextField[k][i] = new JTextField(nf.format(Integer.parseInt(SAStatement.get(i).getSaUnit())));
				poInsertCompleteUnitSAUnitTextField[k][i].setEditable(false);
				gbc.gridwidth = 1;
				gbc.gridx = 1;
				gbc.gridy = 61 + i + k * 100;
				poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAUnitTextField[k][i], gbc);

				poInsertCompleteUnitSAInvoiceNoTextField[k][i] = new JTextField(SAStatement.get(i).getSaInoviceNo());
				poInsertCompleteUnitSAInvoiceNoTextField[k][i].setEditable(false);
				gbc.gridwidth = 1;
				gbc.gridx = 2;
				gbc.gridy = 61 + i + k * 100;
				poInsertCompleteUnitPanel.add(poInsertCompleteUnitSAInvoiceNoTextField[k][i], gbc);

				poInsertCompleteUnitSARemainTextField[k][i] = new JTextField();
				gbc.gridwidth = 1;
				gbc.gridx = 3;
				gbc.gridy = 61 + i + k * 100;
				poInsertCompleteUnitSARemainTextField[k][i].setEditable(false);
				poInsertCompleteUnitPanel.add(poInsertCompleteUnitSARemainTextField[k][i], gbc);
			}
		}
	}

	public JScrollPane insertCompleteUnitPane(){
		return poInsertCompleteUnitScrollPane;
	}

	private void setSARemain(int k){
		NumberFormat nf = new DecimalFormat("###,###,###,###,###");
		int remain = 0;
		for(int i = 0; i <= cntCompleteUnitSA; i++){
			if(i == 0){
				if(!poInsertCompleteUnitSAUnitTextField[k][i].getText().equals("") && !notInteger(poInsertCompleteUnitSAUnitTextField[k][i].getText().replace(",", ""))){
					remain = totalquantity - Integer.parseInt(poInsertCompleteUnitSAUnitTextField[k][i].getText().replace(",", ""));
					poInsertCompleteUnitSARemainTextField[k][i].setText(nf.format(remain));
					System.out.println("remain: " + nf.format(remain));
				}else{
					poInsertCompleteUnitSARemainTextField[k][i].setText(nf.format(totalquantity));
				}
			}else{
				if(!poInsertCompleteUnitSAUnitTextField[k][i].getText().equals("") && !notInteger(poInsertCompleteUnitSAUnitTextField[k][i].getText().replace(",", ""))){
					remain = totalquantity;
					for( int j = 0; j <= i; j++){
						remain = remain - Integer.parseInt(poInsertCompleteUnitSAUnitTextField[k][j].getText().replace(",", ""));
					}
					poInsertCompleteUnitSARemainTextField[k][i].setText(nf.format(remain));
					System.out.println("remain: " + nf.format(remain));
				}else{
					poInsertCompleteUnitSARemainTextField[k][i].setText(poInsertCompleteUnitSARemainTextField[k][i-1].getText());
				}
			}
		}

		if(cntCompleteUnitSA == -1){
			SAremain = Integer.toString(totalquantity);
			System.out.println(SAremain);
		}else{
			SAremain = poInsertCompleteUnitSARemainTextField[k][cntCompleteUnitSA].getText();
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
		d.setContentPane(poInsertCompleteUnitScrollPane);
		d.setVisible(true);		
	}
}

