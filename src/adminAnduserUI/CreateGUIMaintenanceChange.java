package adminAnduserUI;

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
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import com.mysql.jdbc.StringUtils;

import domain.DateValidator;
import domain.IRBS;

public class CreateGUIMaintenanceChange {
	private JFrame theFrame;
	private JPanel maintenancePanel;
    private JPanel errorPanel;
	private JLabel maintenanceLabel;
	private JTextField maintenanceProductTextField;
	private JTextField maintenanceACRONYMTextField;
	private JTextField maintenancePurchaseCodeTextField;
	private JTextField maintenanceVendorTextField;
	private JTextField maintenanceDescriptionTextField;
	private JTextField maintenanceDutyCodeTextField;
	private JTextField maintenanceFixedcostTextField;
	private JTextField maintenanceDateOfConfirmationTextField;
	private JButton maintenanceButton;
	private String selectedOriginalPRODUCT;
	private String selectedPRODUCT;
	private String selectedACRONYM;
	private String selectedDESCRIPTION;
	private String selectedDUTY_CODE;
	private String selectedFixedcost;
	private String selectedPurchaseCode;
	private String selectedVendor;
	private String selectedDateOfConfirmation;
	private JTable maintenanceTable;
	private JDialog d;
	private DateValidator dateValidator;
	private String searchSQL;

	public CreateGUIMaintenanceChange(JFrame theFrame, JTable maintenanceTable, String selectedPRODUCT, String selectedACRONYM, String selectedDESCRIPTION, String selectedDUTY_CODE, String selectedFixedcost, String selectedPurchaseCode, String selectedVendor, String selectedDateOfConfirmation, String searchSQL){
		this.selectedOriginalPRODUCT = selectedPRODUCT;
		this.selectedPRODUCT = selectedPRODUCT;
		this.selectedACRONYM = selectedACRONYM;
		this.selectedDESCRIPTION = selectedDESCRIPTION;
		this.selectedDUTY_CODE = selectedDUTY_CODE;
		this.selectedFixedcost = selectedFixedcost;
		this.selectedPurchaseCode = selectedPurchaseCode;
		this.selectedVendor = selectedVendor;
		this.selectedDateOfConfirmation = selectedDateOfConfirmation;
		this.theFrame = theFrame;
		this.maintenanceTable = maintenanceTable;
		this.searchSQL = searchSQL;
		initUI();
	}

	private void initUI() {
		createDialog();
		setUP();
		useMaintenancePanel();
	}


	private void createDialog() {
		d = new JDialog(theFrame, "Change the maintenance", true);
		d.setSize(800,500);
		d.setLocationRelativeTo(null);
		d.setResizable(false);
	}

	private void setUP() {
		maintenancePanel = new JPanel(new GridBagLayout());	
		GridBagConstraints gbc = new GridBagConstraints();
		dateValidator = new DateValidator();

		// ************************* maintenanceInsertPanel UI SetUP
		// *************************
		// ========================= maintenanceInsertPanel Level One UI Create
		// =========================
		maintenanceLabel = new JLabel("Please change the selected maintenance");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		maintenancePanel.add(maintenanceLabel, gbc);

		maintenanceLabel = new JLabel("Part No: ");
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.2;
		maintenancePanel.add(maintenanceLabel, gbc);

		maintenanceProductTextField = new JTextField(selectedPRODUCT);
		maintenanceProductTextField.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 1;
		maintenancePanel.add(maintenanceProductTextField, gbc);

		maintenanceLabel = new JLabel("ACRONYM: ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		maintenancePanel.add(maintenanceLabel, gbc);

		maintenanceACRONYMTextField = new JTextField(selectedACRONYM);
		gbc.gridx = 1;
		gbc.gridy = 2;
		maintenancePanel.add(maintenanceACRONYMTextField, gbc);
		
		maintenanceLabel = new JLabel("Purchase Code: ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		maintenancePanel.add(maintenanceLabel, gbc);

		maintenancePurchaseCodeTextField = new JTextField(selectedPurchaseCode);
		gbc.gridx = 1;
		gbc.gridy = 3;
		maintenancePanel.add(maintenancePurchaseCodeTextField, gbc);
		
		maintenanceLabel = new JLabel("Vendor: ");
		gbc.gridx = 0;
		gbc.gridy = 4;
		maintenancePanel.add(maintenanceLabel, gbc);

		maintenanceVendorTextField = new JTextField(selectedVendor);
		gbc.gridx = 1;
		gbc.gridy = 4;
		maintenancePanel.add(maintenanceVendorTextField, gbc);

		maintenanceLabel = new JLabel("DESCRIPTION: ");
		gbc.gridx = 0;
		gbc.gridy = 5;
		maintenancePanel.add(maintenanceLabel, gbc);

		maintenanceDescriptionTextField = new JTextField(selectedDESCRIPTION);
		gbc.gridx = 1;
		gbc.gridy = 5;
		maintenancePanel.add(maintenanceDescriptionTextField, gbc);

		maintenanceLabel = new JLabel("DUTY CODE: ");
		gbc.gridx = 0;
		gbc.gridy = 6;
		maintenancePanel.add(maintenanceLabel, gbc);

		maintenanceDutyCodeTextField = new JTextField(selectedDUTY_CODE);
		gbc.gridx = 1;
		gbc.gridy = 6;
		maintenanceDutyCodeTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(maintenanceDutyCodeTextField.getText().length() == 4){
						maintenanceDutyCodeTextField.setText(maintenanceDutyCodeTextField.getText() + ".");
					}else if(maintenanceDutyCodeTextField.getText().length() == 7){
						maintenanceDutyCodeTextField.setText(maintenanceDutyCodeTextField.getText() + ".");
					}
				}
			}
		});
		maintenancePanel.add(maintenanceDutyCodeTextField, gbc);
		
		maintenanceLabel = new JLabel("Fixed Cost: ");
		gbc.gridx = 0;
		gbc.gridy = 7;
		maintenancePanel.add(maintenanceLabel, gbc);

		maintenanceFixedcostTextField = new JTextField(selectedFixedcost);
		gbc.gridx = 1;
		gbc.gridy = 7;
		maintenancePanel.add(maintenanceFixedcostTextField, gbc);
		
		maintenanceLabel = new JLabel("Date of Confirmation: ");
		gbc.gridx = 0;
		gbc.gridy = 8;
		maintenancePanel.add(maintenanceLabel, gbc);
		
		maintenanceDateOfConfirmationTextField = new JTextField(selectedDateOfConfirmation);
		gbc.gridx = 1;
		gbc.gridy = 8;
		maintenanceDateOfConfirmationTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(dateValidator.checkMM(maintenanceDateOfConfirmationTextField.getText())){
						maintenanceDateOfConfirmationTextField.setText(maintenanceDateOfConfirmationTextField.getText() + "/");
					}
					if(dateValidator.checkDD(maintenanceDateOfConfirmationTextField.getText())){
						maintenanceDateOfConfirmationTextField.setText(maintenanceDateOfConfirmationTextField.getText() + "/");
					}
					maintenancePanel.revalidate();  
					maintenancePanel.repaint();
				}
			}
		});
		maintenancePanel.add(maintenanceDateOfConfirmationTextField, gbc);

		maintenanceButton = new JButton("Save");
		IRBS irbs = new IRBS();
		maintenanceButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String ChangedDate = null;
				IRBS irbs = new IRBS();
				try {
					if(dateValidator.checkCompleteUnitDateValid(maintenanceDateOfConfirmationTextField.getText()) != null){
						JOptionPane
						.showMessageDialog(
								errorPanel,
								"Error: Date is invalid",
								"Error", JOptionPane.ERROR_MESSAGE);
					}else{
						if(!StringUtils.isNullOrEmpty(maintenanceDateOfConfirmationTextField.getText()))
							ChangedDate = maintenanceDateOfConfirmationTextField.getText().substring(6, 8) + "/" + maintenanceDateOfConfirmationTextField.getText().substring(0, 2) + "/" + maintenanceDateOfConfirmationTextField.getText().substring(3, 5);
						if(!maintenanceFixedcostTextField.getText().equals("")){
							if(!notDouble(maintenanceFixedcostTextField.getText())){
								DecimalFormat pf = new DecimalFormat("###,###,###,###,##0.000");
								irbs.changeMaintenance(maintenanceProductTextField.getText(), maintenanceACRONYMTextField.getText(), maintenanceDescriptionTextField.getText(), maintenanceDutyCodeTextField.getText(), pf.format(Double.parseDouble(maintenanceFixedcostTextField.getText())), maintenancePurchaseCodeTextField.getText(), maintenanceVendorTextField.getText(), selectedOriginalPRODUCT, ChangedDate);
								d.dispose();	
								repaintToShowAll();
								JOptionPane
								.showMessageDialog(
										null,
										"Success update the maintenance: " + selectedOriginalPRODUCT,
										"Success", JOptionPane.INFORMATION_MESSAGE);
								
							}else{
								JOptionPane
								.showMessageDialog(
										errorPanel,
										"Error: Fixed Cost is not a number.",
										"Error", JOptionPane.ERROR_MESSAGE);
							}
						}else{
							irbs.changeMaintenance(maintenanceProductTextField.getText(), maintenanceACRONYMTextField.getText(), maintenanceDescriptionTextField.getText(), maintenanceDutyCodeTextField.getText(), maintenanceFixedcostTextField.getText(), maintenancePurchaseCodeTextField.getText(), maintenanceVendorTextField.getText(), selectedOriginalPRODUCT, ChangedDate);
							d.dispose();	
							repaintToShowAll();
							JOptionPane
							.showMessageDialog(
									null,
									"Success update the maintenance: " + selectedOriginalPRODUCT,
									"Success", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} catch(Exception e1) { 
					try {
						irbs.getCon().rollback();
					} catch (SQLException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"Error: Error on insert the data. Please try again.",
							"Error", JOptionPane.ERROR_MESSAGE);
				} finally {
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
		gbc.gridx = 1;
		gbc.gridy = 10;
		maintenancePanel.add(maintenanceButton, gbc);
	}

	private void useMaintenancePanel() {
		d.setContentPane(maintenancePanel);
		d.setVisible(true);		
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

	private void repaintToShowAll(){
		IRBS irbs = new IRBS();
		maintenanceTable.setModel(irbs.viewSearchMaintenance(searchSQL));	
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
