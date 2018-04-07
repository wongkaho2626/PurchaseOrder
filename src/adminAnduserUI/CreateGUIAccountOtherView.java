package adminAnduserUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import domain.IRBS;

public class CreateGUIAccountOtherView {
	private JScrollPane accountScrollPane;
	private JPanel accountPanel;
	private JFrame theFrame;

	private JTable accountTable;

	private JLabel accountViewLabel;
	private JTextField accountNameViewTextField;
	//	private JCheckBox userCheckBox;
	//	private JCheckBox viewerCheckBox;
	private JRadioButton userRadioButton;
	private JRadioButton viewerRadioButton;
	private ButtonGroup buttonGroup;
	//	private boolean userClick = false;
	//	private boolean viewerClick = false;
	private JButton SearchButton;
	private JButton ResetButton;
	private JButton CreateAccountButton;
	private JButton DeleteAccountButton;

	public CreateGUIAccountOtherView(){
		initUI();
	}

	private void initUI() {
		setUP();
	}

	private void setUP() {
		accountPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		accountScrollPane = new JScrollPane(accountPanel);

		// ************************* accountPanel UI SetUP
		// *************************
		// ========================= accountPanel Level One UI Create
		// =========================
		accountViewLabel = new JLabel("Search for Other Account: ");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		accountPanel.add(accountViewLabel, gbc);

		accountViewLabel = new JLabel("Username: ");
		gbc.gridwidth = 2;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 1;
		accountPanel.add(accountViewLabel, gbc);

		accountNameViewTextField = new JTextField();
		gbc.gridwidth = 2;
		gbc.gridx = 2;
		gbc.gridy = 1;
		accountPanel.add(accountNameViewTextField, gbc);

		accountViewLabel = new JLabel("User Type: ");
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 2;
		accountPanel.add(accountViewLabel, gbc);

		userRadioButton = new JRadioButton("User");
		viewerRadioButton = new JRadioButton("Viewer");
		buttonGroup = new ButtonGroup();
		buttonGroup.add(userRadioButton);
		buttonGroup.add(viewerRadioButton);

		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		accountPanel.add(userRadioButton, gbc);

		gbc.gridx = 3;
		gbc.gridy = 2;
		accountPanel.add(viewerRadioButton, gbc);

		accountViewLabel = new JLabel();
		gbc.gridx = 0;
		gbc.gridy = 3;
		accountPanel.add(accountViewLabel, gbc);

		ResetButton = new JButton("                             Show All                             ");
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 3;
		ResetButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				repaintToShowAllAccount();				
			}
		});
		accountPanel.add(ResetButton, gbc);

		SearchButton = new JButton("Search");
		gbc.gridwidth = 2;
		gbc.gridx = 2;
		gbc.gridy = 3;
		SearchButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(accountNameViewTextField.getText().equals("")  
						&& userRadioButton.isSelected() == false 
						&& viewerRadioButton.isSelected() == false){
					JOptionPane.showMessageDialog(null,
							"Please input some criteria for searching!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else{
					if(userRadioButton.isSelected() == false && viewerRadioButton.isSelected() == false){
						JOptionPane.showMessageDialog(null,
								"Error: There are at least one account type", "Error",
								JOptionPane.ERROR_MESSAGE);
					}else{
						IRBS irbs = new IRBS();

						if(userRadioButton.isSelected())
							//search for username and type (criteria: only user))
							accountTable.setModel(irbs.viewSearchAccount(searchAllUserAccountSQL(), null));
						else if(viewerRadioButton.isSelected())
							//search for username and type (criteria: only viewer))
							accountTable.setModel(irbs.viewSearchAccount(searchAllViewerAccountSQL(), null));
						try {
							irbs.getCon().close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						accountViewReset();
					}
				}
			}
		});
		accountPanel.add(SearchButton, gbc);

		accountTable = new JTable(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			//make the JTable non-editable
			public boolean isCellEditable(int row,int column){
				return false;
			}
		};
		gbc.gridy = 13;
		gbc.gridx = 0;
		gbc.gridwidth = 4;
		gbc.ipadx = 200;
		accountPanel.add(new JScrollPane(accountTable), gbc);

		DeleteAccountButton = new JButton("Delete Account");
		gbc.gridwidth = 2;
		gbc.gridy = 15;
		gbc.gridx = 0;
		DeleteAccountButton.addActionListener(new ActionListener(){
			//get the selected maintenance product #
			ListSelectionModel cellSelectionModel = accountTable.getSelectionModel();
			String selectedusername = null;
			String selectedtype = null;
			public void actionPerformed(ActionEvent e) {
				if(!cellSelectionModel.isSelectionEmpty()){
					int[] selectedRow = accountTable.getSelectedRows();

					for (int i = 0; i < selectedRow.length; i++) {
						selectedusername = (String) accountTable.getValueAt(selectedRow[i], 0);
						selectedtype = (String) accountTable.getValueAt(selectedRow[i], 1);
					}
					IRBS irbs = new IRBS();
					irbs.deleteAccount(selectedusername, selectedtype);
					try {
						irbs.getCon().close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace(); 
					}
					System.out.println("Selected: " + selectedusername + ", " + selectedtype);
					repaintToShowAllAccount();
				}else{
					JOptionPane.showMessageDialog(null,
							"You did not select the item from the table", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		accountPanel.add(DeleteAccountButton, gbc);

		CreateAccountButton = new JButton("Create Account");
		gbc.gridwidth = 2;
		gbc.gridy = 15;
		gbc.gridx = 2;
		CreateAccountButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateAccount gui = new CreateAccount(theFrame, accountTable);
			}
		});
		accountPanel.add(CreateAccountButton, gbc);
	}

	// ************************* create the SQL statement
	// *************************
	// ========================= search for user
	// ========================= (criteria: username, type)
	private String searchAllUserAccountSQL(){
		String sql = ("SELECT username, type From user");
		String addsql;
		if(!accountNameViewTextField.getText().equals("")){
			addsql = " WHERE username = \'" + accountNameViewTextField.getText() + "\'";
			sql = sql + addsql;
		}
		System.out.println(sql);
		return sql;
	}

	// ========================= search for viewer
	// ========================= (criteria: username, type)
	private String searchAllViewerAccountSQL(){
		String sql = ("SELECT username, type From viewer");
		String addsql;
		if(!accountNameViewTextField.getText().equals("")){
			addsql = " WHERE username = \'" + accountNameViewTextField.getText() + "\'";
			sql = sql + addsql;
		}
		System.out.println(sql);
		return sql;
	}

	// ************************* reset the table to show all information
	// *************************
	// ========================= reset account table in Other Account Panel
	// ========================= 
	public void repaintToShowAllAccount(){
		IRBS irbs = new IRBS();
		accountTable.setModel(irbs.viewAllAccount());
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ************************* reset all the TextField to null
	// *************************
	// ========================= reset all the TextField in Other Account View Panel
	// ========================= 
	public void accountViewReset(){
		accountNameViewTextField.setText(null);
		userRadioButton.setSelected(false);
		viewerRadioButton.setSelected(false);
		//		userClick = false;
		//		viewerClick = false;
		//		userCheckBox.setSelected(userClick);
		//		viewerCheckBox.setSelected(viewerClick);
	}

	public JScrollPane viewPane(){
		return accountScrollPane;
	}
}
