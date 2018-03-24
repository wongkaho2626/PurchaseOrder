package adminAnduserUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.sql.Date;
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

import com.mysql.jdbc.StringUtils;

import domain.IRBS;

public class CreateGUIMaintenanceView {
	private JLabel maintenanceViewLabel;
	private JTextField maintenanceViewProductTextField;
	private JTextField maintenanceViewACRONYMTextField;
	private JTextField maintenanceViewPurchaseCodeTextField;
	private JTextField maintenanceViewVendorTextField;
	private JTextField maintenanceViewDescriptionTextField;
	private JTextField maintenanceViewDutyCodeTextField;
	private JTextField maintenanceViewFixedcostTextField;
	private JTextField maintenanceDateOfConfirmationTextField;
	private JButton maintenanceViewSearchButton;
	private JButton maintenanceViewResetButton;
	private JButton maintenanceViewDeleteButton;
	private JButton maintenanceViewChangeButton;
	private JTable maintenanceTable;

	private JScrollPane maintenanceViewScrollPane;
	private JPanel maintenanceViewPanel;
	private JFrame theFrame;

	public CreateGUIMaintenanceView(){
		initUI();
	}

	private void initUI() {
		setUP();
		repaintToShowAllMaintenance();
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

		maintenanceViewLabel = new JLabel("Part No: ");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 1;
		maintenanceViewPanel.add(maintenanceViewLabel, gbc);

		maintenanceViewProductTextField = new JTextField();
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
		
		maintenanceViewLabel = new JLabel("Purchase Code: ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		maintenanceViewPanel.add(maintenanceViewLabel, gbc);

		maintenanceViewPurchaseCodeTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 3;
		maintenanceViewPanel.add(maintenanceViewPurchaseCodeTextField, gbc);
		
		maintenanceViewLabel = new JLabel("Vendor: ");
		gbc.gridx = 0;
		gbc.gridy = 4;
		maintenanceViewPanel.add(maintenanceViewLabel, gbc);

		maintenanceViewVendorTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 4;
		maintenanceViewPanel.add(maintenanceViewVendorTextField, gbc);

		maintenanceViewLabel = new JLabel("Description: ");
		gbc.gridx = 0;
		gbc.gridy = 5;
		maintenanceViewPanel.add(maintenanceViewLabel, gbc);

		maintenanceViewDescriptionTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 5;
		maintenanceViewPanel.add(maintenanceViewDescriptionTextField, gbc);

		maintenanceViewLabel = new JLabel("Duty Code: ");
		gbc.gridx = 0;
		gbc.gridy = 6;
		maintenanceViewPanel.add(maintenanceViewLabel, gbc);

		maintenanceViewDutyCodeTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 6;
		maintenanceViewDutyCodeTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(maintenanceViewDutyCodeTextField.getText().length() == 4){
						maintenanceViewDutyCodeTextField.setText(maintenanceViewDutyCodeTextField.getText() + ".");
					}else if(maintenanceViewDutyCodeTextField.getText().length() == 7){
						maintenanceViewDutyCodeTextField.setText(maintenanceViewDutyCodeTextField.getText() + ".");
					}
				}
			}
		});
		maintenanceViewPanel.add(maintenanceViewDutyCodeTextField, gbc);
		
		maintenanceViewLabel = new JLabel("Fixed Cost: ");
		gbc.gridx = 0;
		gbc.gridy = 7;
		maintenanceViewPanel.add(maintenanceViewLabel, gbc);

		maintenanceViewFixedcostTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 7;
		maintenanceViewPanel.add(maintenanceViewFixedcostTextField, gbc);

		maintenanceViewResetButton = new JButton("Show all");
		gbc.gridx = 0;
		gbc.gridy = 9;
		maintenanceViewResetButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				IRBS irbs = new IRBS();
				maintenanceTable.setModel(irbs.viewAllMaintenance());
				try {
					irbs.getCon().close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				maintenanceViewReset();
			}
		});
		maintenanceViewPanel.add(maintenanceViewResetButton, gbc);

		maintenanceViewSearchButton = new JButton("Search");
		gbc.gridx = 1;
		gbc.gridy = 9;
		maintenanceViewSearchButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(maintenanceViewProductTextField.getText().equals("") && maintenanceViewACRONYMTextField.getText().equals("") && maintenanceViewDescriptionTextField.getText().equals("") && maintenanceViewDutyCodeTextField.getText().equals("") && maintenanceViewFixedcostTextField.getText().equals("") && maintenanceViewPurchaseCodeTextField.getText().equals("") && maintenanceViewVendorTextField.getText().equals("")){
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
			}
		});
		maintenanceViewPanel.add(maintenanceViewSearchButton, gbc);

		maintenanceViewLabel = new JLabel();
		gbc.gridx = 0;
		gbc.gridy = 10;
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
		gbc.gridy = 11;
		gbc.gridwidth = 2;
		gbc.ipady = 5;
		gbc.ipadx = 400;
		maintenanceTable.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me) {
		        JTable table =(JTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table.rowAtPoint(p);
		        if (me.getClickCount() == 2) {
		        	//get the selected maintenance product #
					ListSelectionModel cellSelectionModel = maintenanceTable.getSelectionModel();
					String selectedPRODUCT = null;
					String selectedACRONYM = null;
					String selectedDESCRIPTION = null;
					String selectedDUTY_CODE = null;
					String selectedFixedcost = null;
					String selectedPurchaseCode = null;
					String selectedVendor = null;
					String selectedDateOfConfirmation = null;
					int[] selectedRow = maintenanceTable.getSelectedRows();
					for (int i = 0; i < selectedRow.length; i++) {
						selectedPRODUCT = (String) maintenanceTable.getValueAt(selectedRow[i], 0);
						selectedACRONYM = (String) maintenanceTable.getValueAt(selectedRow[i], 1);
						selectedPurchaseCode = (String) maintenanceTable.getValueAt(selectedRow[i], 2);
						selectedVendor = (String) maintenanceTable.getValueAt(selectedRow[i], 3);
						selectedDESCRIPTION = (String) maintenanceTable.getValueAt(selectedRow[i], 4);
						selectedDUTY_CODE = (String) maintenanceTable.getValueAt(selectedRow[i], 5);
						selectedFixedcost = (String) maintenanceTable.getValueAt(selectedRow[i], 6);
						selectedDateOfConfirmation = (String) maintenanceTable.getValueAt(selectedRow[i], 7);
//						if(maintenanceTable.getValueAt(selectedRow[i], 7) != null){
//							selectedDateOfConfirmation = ((Date) maintenanceTable.getValueAt(selectedRow[i], 7)).toString().substring(5, 7) + "/" + ((Date) maintenanceTable.getValueAt(selectedRow[i], 7)).toString().substring(8, 10) + "/" + ((Date) maintenanceTable.getValueAt(selectedRow[i], 7)).toString().substring(2, 4);
//						}
					}
					CreateGUIMaintenanceChange cgui = new CreateGUIMaintenanceChange(theFrame, maintenanceTable, selectedPRODUCT, selectedACRONYM, selectedDESCRIPTION, selectedDUTY_CODE, selectedFixedcost.replace(",", "").replace(".000", ""), selectedPurchaseCode, selectedVendor, selectedDateOfConfirmation, searchMaintenanceSQL());
					System.out.println("Selected: " + selectedPRODUCT + ", " + selectedACRONYM + ", " + selectedDESCRIPTION + ", " + selectedDUTY_CODE + ", " + selectedFixedcost + ", " + selectedPurchaseCode + ", " + selectedVendor + ", " + selectedDateOfConfirmation);
					IRBS irbs = new IRBS();
					maintenanceTable.setModel(irbs.viewSearchMaintenance(searchMaintenanceSQL()));	
					try {
						irbs.getCon().close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		    }
		});
		maintenanceViewPanel.add(new JScrollPane(maintenanceTable), gbc);

		maintenanceViewDeleteButton = new JButton("Delete");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 12;

		maintenanceViewDeleteButton.addActionListener(new ActionListener(){
			//get the selected maintenance product #
			ListSelectionModel cellSelectionModel = maintenanceTable.getSelectionModel();
			String selectedPRODUCT = null;
			String selectedACRONYM = null;
			String selectedDESCRIPTION = null;
			String selectedDUTY_CODE = null;
			String selectedFixedcost = null;
			public void actionPerformed(ActionEvent e) {
				if(!cellSelectionModel.isSelectionEmpty()){
					IRBS irbs = new IRBS();
					int[] selectedRow = maintenanceTable.getSelectedRows();

					for (int i = 0; i < selectedRow.length; i++) {
						selectedPRODUCT = (String) maintenanceTable.getValueAt(selectedRow[i], 0).toString();
						selectedACRONYM = (String) maintenanceTable.getValueAt(selectedRow[i], 1);
						selectedDESCRIPTION = (String) maintenanceTable.getValueAt(selectedRow[i], 2);
						selectedDUTY_CODE = (String) maintenanceTable.getValueAt(selectedRow[i], 3).toString();
						selectedFixedcost = (String) maintenanceTable.getValueAt(selectedRow[i], 4);
					}
					irbs.deleteMaintenance(selectedPRODUCT);
					System.out.println("Selected: " + selectedPRODUCT + ", " + selectedACRONYM + ", " + selectedDESCRIPTION + ", " + selectedDUTY_CODE + ", " + selectedFixedcost);
					maintenanceTable.setModel(irbs.viewSearchMaintenance(searchMaintenanceSQL()));		
					JOptionPane.showMessageDialog(null,
							"You delete the maintenance " + selectedPRODUCT + " successfully.", "Message",
							JOptionPane.INFORMATION_MESSAGE);
					try {
						irbs.getCon().close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null,
							"You did not select the item from the table", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		maintenanceViewPanel.add(maintenanceViewDeleteButton, gbc);

		maintenanceViewChangeButton = new JButton("Change / View");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 1;
		gbc.gridy = 12;

		maintenanceViewChangeButton.addActionListener(new ActionListener(){
			//get the selected maintenance product #
			ListSelectionModel cellSelectionModel = maintenanceTable.getSelectionModel();
			String selectedPRODUCT = null;
			String selectedACRONYM = null;
			String selectedDESCRIPTION = null;
			String selectedDUTY_CODE = null;
			String selectedFixedcost = null;
			String selectedPurchaseCode = null;
			String selectedVendor = null;
			String selectedDateOfConfirmation = null;
			public void actionPerformed(ActionEvent e) {
				if(!cellSelectionModel.isSelectionEmpty()){
					int[] selectedRow = maintenanceTable.getSelectedRows();

					for (int i = 0; i < selectedRow.length; i++) {
						selectedPRODUCT = (String) maintenanceTable.getValueAt(selectedRow[i], 0);
						selectedACRONYM = (String) maintenanceTable.getValueAt(selectedRow[i], 1);
						selectedPurchaseCode = (String) maintenanceTable.getValueAt(selectedRow[i], 2);
						selectedVendor = (String) maintenanceTable.getValueAt(selectedRow[i], 3);
						selectedDESCRIPTION = (String) maintenanceTable.getValueAt(selectedRow[i], 4);
						selectedDUTY_CODE = (String) maintenanceTable.getValueAt(selectedRow[i], 5);
						selectedFixedcost = (String) maintenanceTable.getValueAt(selectedRow[i], 6);
						selectedDateOfConfirmation = (String) maintenanceTable.getValueAt(selectedRow[i], 7);
//						if(maintenanceTable.getValueAt(selectedRow[i], 7) != null){
//							selectedDateOfConfirmation = ((Date) maintenanceTable.getValueAt(selectedRow[i], 7)).toString().substring(5, 7) + "/" + ((Date) maintenanceTable.getValueAt(selectedRow[i], 7)).toString().substring(8, 10) + "/" + ((Date) maintenanceTable.getValueAt(selectedRow[i], 7)).toString().substring(2, 4);
//						}
					}
					CreateGUIMaintenanceChange cgui = new CreateGUIMaintenanceChange(theFrame, maintenanceTable, selectedPRODUCT, selectedACRONYM, selectedDESCRIPTION, selectedDUTY_CODE, selectedFixedcost.replace(",", "").replace(".000", ""), selectedPurchaseCode, selectedVendor, selectedDateOfConfirmation, searchMaintenanceSQL());
					System.out.println("Selected: " + selectedPRODUCT + ", " + selectedACRONYM + ", " + selectedDESCRIPTION + ", " + selectedDUTY_CODE + ", " + selectedFixedcost + ", " + selectedPurchaseCode + ", " + selectedVendor + ", " + selectedDateOfConfirmation);
//					repaintToShowAllMaintenance();
					IRBS irbs = new IRBS();
					maintenanceTable.setModel(irbs.viewSearchMaintenance(searchMaintenanceSQL()));		
					try {
						irbs.getCon().close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null,
							"You did not select the item from the table", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		maintenanceViewPanel.add(maintenanceViewChangeButton, gbc);
	}

	// ========================= reset maintenance table in Maintenance Insert Panel
	// ========================= 
	public void repaintToShowAllMaintenance(){
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
		String sql = ("SELECT PRODUCT, ACRONYM, purchaseCode, vendor, DESCRIPTION, DUTY_CODE, fixedcost, DATE_FORMAT(dateOfConfirmation, '%m/%d/%y') AS dateOfConfirmation FROM maintenance");
		if(maintenanceViewProductTextField.getText().equals("") && maintenanceViewACRONYMTextField.getText().equals("") && maintenanceViewDescriptionTextField.getText().equals("") && maintenanceViewDutyCodeTextField.getText().equals("") && maintenanceViewFixedcostTextField.getText().equals("") && maintenanceViewPurchaseCodeTextField.getText().equals("") && maintenanceViewVendorTextField.getText().equals("")){
			sql = sql + ";";
		}else{
			sql = sql + " WHERE";
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
			if(!maintenanceViewFixedcostTextField.getText().equals("")){
				if(cnt != 0){
					addsql = " AND";
					sql = sql + addsql;
				}
				DecimalFormat pf = new DecimalFormat("###,###,###,###,###.000");
				addsql = " fixedcost = \'" + pf.format(Double.parseDouble(maintenanceViewFixedcostTextField.getText())) + "\'";
				sql = sql + addsql;
				cnt++;
			}
			if(!maintenanceViewPurchaseCodeTextField.getText().equals("")){
				if(cnt != 0){
					addsql = " AND";
					sql = sql + addsql;
				}
				addsql = " purchaseCode like \'%" + maintenanceViewPurchaseCodeTextField.getText() + "%\'";
				sql = sql + addsql;
				cnt++;
			}
			if(!maintenanceViewVendorTextField.getText().equals("")){
				if(cnt != 0){
					addsql = " AND";
					sql = sql + addsql;
				}
				addsql = " vendor like \'%" + maintenanceViewVendorTextField.getText() + "%\'";
				sql = sql + addsql;
				cnt++;
			}
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
		maintenanceViewFixedcostTextField.setText(null);
		maintenanceViewPurchaseCodeTextField.setText(null);
		maintenanceViewVendorTextField.setText(null);
	}
	
	public JScrollPane viewPane(){
		return maintenanceViewScrollPane;
	}
}
