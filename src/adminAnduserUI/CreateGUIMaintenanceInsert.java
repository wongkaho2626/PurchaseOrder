package adminAnduserUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.mysql.jdbc.StringUtils;

import domain.DateValidator;
import domain.IRBS;

public class CreateGUIMaintenanceInsert {
	//maintenance insert Panel
	private JLabel maintenanceInsertLabel;
	private JTextField maintenanceInsertProductTextField;
	private JTextField maintenanceInsertACRONYMTextField;
	private JTextField maintenanceInsertPurchaseCodeTextField;
	private JTextField maintenanceInsertVendorTextField;
	private JTextField maintenanceInsertDescriptionTextField;
	private JTextField maintenanceInsertDutyCodeTextField;
	private JTextField maintenanceInsertFixedCostTextField;
	private JTextField maintenanceInsertDateOfConfirmationTextField;
	private JButton maintenanceInsertButton;

	private JScrollPane maintenanceInsertScrollPane;
	private JPanel maintenanceInsertPanel;
	private JPanel errorPanel;
	
	private DateValidator dateValidator;

	public CreateGUIMaintenanceInsert(){
		initUI();
	}

	private void initUI() {
		setUP();
	}

	private void setUP(){
		maintenanceInsertPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		maintenanceInsertScrollPane = new JScrollPane(maintenanceInsertPanel);
		dateValidator = new DateValidator();

		// ************************* maintenanceInsertPanel UI SetUP
		// *************************
		// ========================= maintenanceInsertPanel Level One UI Create
		// =========================
		maintenanceInsertLabel = new JLabel("Please insert the new maintenance");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 200;
		gbc.gridwidth = 2;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		maintenanceInsertPanel.add(maintenanceInsertLabel, gbc);

		maintenanceInsertLabel = new JLabel("Part No: ");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 1;
		maintenanceInsertPanel.add(maintenanceInsertLabel, gbc);

		maintenanceInsertProductTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 1;
		maintenanceInsertProductTextField.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
//				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
//					if(maintenanceInsertProductTextField.getText().length() == 2 && notDouble(maintenanceInsertProductTextField.getText()) == false){
//						maintenanceInsertProductTextField.setText(maintenanceInsertProductTextField.getText() + "-");
//					}
//				}
			}
		});
		maintenanceInsertPanel.add(maintenanceInsertProductTextField, gbc);

		maintenanceInsertLabel = new JLabel("ACRONYM: ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		maintenanceInsertPanel.add(maintenanceInsertLabel, gbc);

		maintenanceInsertACRONYMTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 2;
		maintenanceInsertPanel.add(maintenanceInsertACRONYMTextField, gbc);

		maintenanceInsertLabel = new JLabel("Purchase Code: ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		maintenanceInsertPanel.add(maintenanceInsertLabel, gbc);

		maintenanceInsertPurchaseCodeTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 3;
		maintenanceInsertPanel.add(maintenanceInsertPurchaseCodeTextField, gbc);

		maintenanceInsertLabel = new JLabel("Vendor: ");
		gbc.gridx = 0;
		gbc.gridy = 4;
		maintenanceInsertPanel.add(maintenanceInsertLabel, gbc);

		maintenanceInsertVendorTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 4;
		maintenanceInsertPanel.add(maintenanceInsertVendorTextField, gbc);

		maintenanceInsertLabel = new JLabel("Description: ");
		gbc.gridx = 0;
		gbc.gridy = 5;
		maintenanceInsertPanel.add(maintenanceInsertLabel, gbc);

		maintenanceInsertDescriptionTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 5;
		maintenanceInsertPanel.add(maintenanceInsertDescriptionTextField, gbc);

		maintenanceInsertLabel = new JLabel("Duty Code: ");
		gbc.gridx = 0;
		gbc.gridy = 6;
		maintenanceInsertPanel.add(maintenanceInsertLabel, gbc);

		maintenanceInsertDutyCodeTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 6;
		maintenanceInsertDutyCodeTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(maintenanceInsertDutyCodeTextField.getText().length() == 4){
						maintenanceInsertDutyCodeTextField.setText(maintenanceInsertDutyCodeTextField.getText() + ".");
					}else if(maintenanceInsertDutyCodeTextField.getText().length() == 7){
						maintenanceInsertDutyCodeTextField.setText(maintenanceInsertDutyCodeTextField.getText() + ".");
					}
				}
			}
		});
		maintenanceInsertPanel.add(maintenanceInsertDutyCodeTextField, gbc);

		maintenanceInsertLabel = new JLabel("Fixed Cost: ");
		gbc.gridx = 0;
		gbc.gridy = 7;
		maintenanceInsertPanel.add(maintenanceInsertLabel, gbc);

		maintenanceInsertFixedCostTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 7;
		maintenanceInsertPanel.add(maintenanceInsertFixedCostTextField, gbc);
		
		maintenanceInsertLabel = new JLabel("Date of Confirmation: ");
		gbc.gridx = 0;
		gbc.gridy = 8;
		maintenanceInsertPanel.add(maintenanceInsertLabel, gbc);
		
		maintenanceInsertDateOfConfirmationTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 8;
		maintenanceInsertDateOfConfirmationTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(dateValidator.checkMM(maintenanceInsertDateOfConfirmationTextField.getText())){
						maintenanceInsertDateOfConfirmationTextField.setText(maintenanceInsertDateOfConfirmationTextField.getText() + "/");
					}
					if(dateValidator.checkDD(maintenanceInsertDateOfConfirmationTextField.getText())){
						maintenanceInsertDateOfConfirmationTextField.setText(maintenanceInsertDateOfConfirmationTextField.getText() + "/");
					}
					maintenanceInsertPanel.revalidate();  
					maintenanceInsertPanel.repaint();
				}
			}
		});
		maintenanceInsertPanel.add(maintenanceInsertDateOfConfirmationTextField, gbc);

		maintenanceInsertLabel = new JLabel();
		gbc.gridx = 0;
		gbc.gridy = 9;
		maintenanceInsertPanel.add(maintenanceInsertLabel, gbc);

		maintenanceInsertButton = new JButton("Save");
		gbc.gridx = 1;
		gbc.gridy = 9;
		maintenanceInsertPanel.add(maintenanceInsertButton, gbc);

		//insert maintenance
		maintenanceInsertButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				IRBS irbs = new IRBS();
				try{
					String ChangedDate = null;
					if(!maintenanceInsertProductTextField.getText().equals("")){
						if(irbs.checkMaintenanceDuplicatePO(maintenanceInsertProductTextField.getText())){
							JOptionPane
							.showMessageDialog(
									errorPanel,
									"Error: Duplicate Maintenance",
									"Error", JOptionPane.ERROR_MESSAGE);
						}else if(dateValidator.checkCompleteUnitDateValid(maintenanceInsertDateOfConfirmationTextField.getText()) != null){
							JOptionPane
							.showMessageDialog(
									errorPanel,
									"Error: Date is invalid",
									"Error", JOptionPane.ERROR_MESSAGE);
						}else{
							if(!StringUtils.isNullOrEmpty(maintenanceInsertDateOfConfirmationTextField.getText()))
								ChangedDate = maintenanceInsertDateOfConfirmationTextField.getText().substring(6, 8) + "/" + maintenanceInsertDateOfConfirmationTextField.getText().substring(0, 2) + "/" + maintenanceInsertDateOfConfirmationTextField.getText().substring(3, 5);
							if(!maintenanceInsertFixedCostTextField.getText().equals("")){
								if(!notDouble(maintenanceInsertFixedCostTextField.getText())){
									DecimalFormat pf = new DecimalFormat("###,###,###,###,##0.000");
									irbs.insertMaintenance(maintenanceInsertProductTextField.getText(), maintenanceInsertACRONYMTextField.getText(), maintenanceInsertDescriptionTextField.getText(),  maintenanceInsertDutyCodeTextField.getText(), pf.format(Double.parseDouble(maintenanceInsertFixedCostTextField.getText())), maintenanceInsertPurchaseCodeTextField.getText(), maintenanceInsertVendorTextField.getText(), ChangedDate);
									JOptionPane
									.showMessageDialog(
											null,
											"Success insert the new maintenance: " + maintenanceInsertProductTextField.getText(),
											"Success", JOptionPane.INFORMATION_MESSAGE);
									maintenanceInsertReset();
								}else{
									JOptionPane
									.showMessageDialog(
											errorPanel,
											"Error: Fixed Cost is not a number.",
											"Error", JOptionPane.ERROR_MESSAGE);
								}
							}else{
								irbs.insertMaintenance(maintenanceInsertProductTextField.getText(), maintenanceInsertACRONYMTextField.getText(), maintenanceInsertDescriptionTextField.getText(),  maintenanceInsertDutyCodeTextField.getText(), maintenanceInsertFixedCostTextField.getText(), maintenanceInsertPurchaseCodeTextField.getText(), maintenanceInsertVendorTextField.getText(), ChangedDate);
								JOptionPane
								.showMessageDialog(
										null,
										"Success insert the new maintenance: " + maintenanceInsertProductTextField.getText(),
										"Success", JOptionPane.INFORMATION_MESSAGE);
								maintenanceInsertReset();
							}
						}
					}else{
						JOptionPane
						.showMessageDialog(
								errorPanel,
								"Error: Part No cannot be null.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}catch(Exception ex){
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"Error: " + ex,
							"Error", JOptionPane.ERROR_MESSAGE);
				}finally{
					try {
						irbs.getCon().close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}

	// ========================= reset all the TextField in Maintenance Insert Panel
	// =========================
	public void maintenanceInsertReset(){
		maintenanceInsertProductTextField.setText(null);
		maintenanceInsertACRONYMTextField.setText(null);
		maintenanceInsertDescriptionTextField.setText(null);
		maintenanceInsertDutyCodeTextField.setText(null);
		maintenanceInsertFixedCostTextField.setText(null);
		maintenanceInsertPurchaseCodeTextField.setText(null);
		maintenanceInsertVendorTextField.setText(null);
		maintenanceInsertDateOfConfirmationTextField.setText(null);
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

	public JScrollPane insertPane(){
		return maintenanceInsertScrollPane;
	}
}
