package viewerUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import domain.IRBS;

public class CreateGUIViewerMaintenanceView {
	private JLabel maintenanceViewLabel;
	private JTextField maintenanceViewProductTextField;
	private JTextField maintenanceViewACRONYMTextField;
	private JTextField maintenanceViewDescriptionTextField;
	private JTextField maintenanceViewDutyCodeTextField;
	private JButton maintenanceViewSearchButton;
	private JButton maintenanceViewResetButton;
	private JTable maintenanceTable;

	private JScrollPane maintenanceViewScrollPane;
	private JPanel maintenanceViewPanel;
	private JFrame theFrame;

	public CreateGUIViewerMaintenanceView(){
		initUI();
		repaintToShowAllMaintenance();
	}

	private void initUI() {
		setUP();
	}

	private void setUP(){
		maintenanceViewPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		maintenanceViewScrollPane = new JScrollPane(maintenanceViewPanel);

		// ************************* maintenanceViewPanel UI SetUP
		// *************************
		// ========================= maintenanceViewPanel Level One UI Create
		// =========================
		maintenanceViewLabel = new JLabel("Search for maintenance");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		maintenanceViewPanel.add(maintenanceViewLabel, gbc);

		maintenanceViewLabel = new JLabel("Part No:                                                                                                                  ");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 1;
		maintenanceViewPanel.add(maintenanceViewLabel, gbc);

		maintenanceViewProductTextField = new JTextField("", 1);
		gbc.gridx = 1;
		gbc.gridy = 1;
		maintenanceViewProductTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
//				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
//					if(maintenanceViewProductTextField.getText().length() == 2 && notInteger(maintenanceViewProductTextField.getText()) == false){
//						maintenanceViewProductTextField.setText(maintenanceViewProductTextField.getText() + "-");
//					}
//				}
			}
		});
		maintenanceViewPanel.add(maintenanceViewProductTextField, gbc);

		maintenanceViewLabel = new JLabel("ACRONYM: ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		maintenanceViewPanel.add(maintenanceViewLabel, gbc);

		maintenanceViewACRONYMTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 2;
		maintenanceViewPanel.add(maintenanceViewACRONYMTextField, gbc);

		maintenanceViewLabel = new JLabel("Description: ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		maintenanceViewPanel.add(maintenanceViewLabel, gbc);

		maintenanceViewDescriptionTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 3;
		maintenanceViewPanel.add(maintenanceViewDescriptionTextField, gbc);

		maintenanceViewLabel = new JLabel("Duty Code: ");
		gbc.gridx = 0;
		gbc.gridy = 4;
		maintenanceViewPanel.add(maintenanceViewLabel, gbc);

		maintenanceViewDutyCodeTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 4;
		maintenanceViewPanel.add(maintenanceViewDutyCodeTextField, gbc);

		maintenanceViewLabel = new JLabel();
		gbc.gridx = 0;
		gbc.gridy = 5;
		maintenanceViewPanel.add(maintenanceViewLabel, gbc);

		maintenanceViewResetButton = new JButton("Show all");
		gbc.gridx = 0;
		gbc.gridy = 6;
		maintenanceViewResetButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				repaintToShowAllMaintenance();
			}
		});
		maintenanceViewPanel.add(maintenanceViewResetButton, gbc);

		maintenanceViewSearchButton = new JButton("Search");
		gbc.gridx = 1;
		gbc.gridy = 6;
		maintenanceViewSearchButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(maintenanceViewProductTextField.getText().equals("") && maintenanceViewACRONYMTextField.getText().equals("") && maintenanceViewDescriptionTextField.getText().equals("") && maintenanceViewDutyCodeTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null,
							"Please input some criteria for searching!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else{
					IRBS irbs = new IRBS();
					maintenanceTable.setModel(irbs.viewSearchMaintenance(searchMaintenanceSQL()));
					try {
						irbs.getCon().close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				maintenanceViewReset();
			}
		});
		maintenanceViewPanel.add(maintenanceViewSearchButton, gbc);

		maintenanceViewLabel = new JLabel();
		gbc.gridx = 0;
		gbc.gridy = 7;
		maintenanceViewPanel.add(maintenanceViewLabel, gbc);

		maintenanceTable = new JTable(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			//make the JTable non-editable
			public boolean isCellEditable(int row,int column){
				return false;
			}
		};
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		gbc.ipady = 10;
		gbc.ipadx = 400;
		maintenanceViewPanel.add(new JScrollPane(maintenanceTable), gbc);

	}

	// ========================= reset maintenance table in Maintenance Insert Panel
	// ========================= 
	private void repaintToShowAllMaintenance(){
		IRBS irbs = new IRBS();
		maintenanceTable.setModel(irbs.viewAllMaintenance());
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ************************* do the checking
	// *************************
	// ========================= check for TextField is integer
	// ========================= (If TextField is integer, return false. If TextField is not integer, return true.)
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

	// ========================= search for maintenance
	// ========================= (criteria: maintenance)
	private String searchMaintenanceSQL(){
		int cnt = 0;
		String sql = ("SELECT PRODUCT, ACRONYM, purchaseCode, vendor, DESCRIPTION, DUTY_CODE, fixedcost, DATE_FORMAT(dateOfConfirmation, '%m/%d/%y') AS dateOfConfirmation FROM maintenance WHERE");
		String addsql;
		if(!maintenanceViewProductTextField.getText().equals("")){
			if(cnt != 0){
				addsql = " AND";
				sql = sql + addsql;
			}
			addsql = " PRODUCT = \'" + maintenanceViewProductTextField.getText() + "\'";
			sql = sql + addsql;
			cnt++;
		}
		if(!maintenanceViewACRONYMTextField.getText().equals("")){
			if(cnt != 0){
				addsql = " AND";
				sql = sql + addsql;
			}
			addsql = " ACRONYM like \'%" + maintenanceViewACRONYMTextField.getText() + "%\'";
			sql = sql + addsql;
			cnt++;
		}
		if(!maintenanceViewDescriptionTextField.getText().equals("")){
			if(cnt != 0){
				addsql = " AND";
				sql = sql + addsql;
			}
			addsql = " DESCRIPTION like \'%" + maintenanceViewDescriptionTextField.getText() + "%\'";
			sql = sql + addsql;
			cnt++;
		}
		if(!maintenanceViewDutyCodeTextField.getText().equals("")){
			if(cnt != 0){
				addsql = " AND";
				sql = sql + addsql;
			}
			addsql = " DUTY_CODE like \'%" + maintenanceViewDutyCodeTextField.getText() + "%\'";
			sql = sql + addsql;
			cnt++;
		}
		System.out.println(sql);
		return sql;
	}

	// ========================= reset all the TextField in Maintenance View Panel
	// ========================= 
	private void maintenanceViewReset(){
		maintenanceViewProductTextField.setText(null);
		maintenanceViewACRONYMTextField.setText(null);
		maintenanceViewDescriptionTextField.setText(null);
		maintenanceViewDutyCodeTextField.setText(null);
	}
	
	public JScrollPane viewPane(){
		return maintenanceViewScrollPane;
	}
}
