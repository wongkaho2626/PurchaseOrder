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
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import domain.IRBS;
import domain.MD5;

public class CreateAccount {
	private JFrame theFrame;
	private JPanel createAccountPanel;
	private JPanel errorPanel;
	private JDialog d;

	private JLabel createUserLabel;
	private ButtonGroup group = new ButtonGroup();
	private JRadioButton userButton;
	private JRadioButton viewerButton = new JRadioButton();
	private JTextField createUsernameTextField;
	private JTextField createPassword1TextField;
	private JTextField createPassword2TextField;
	private JButton createUsernameButton;
	private String password;
	private JTable accountTable;

	public CreateAccount(JFrame theFrame, JTable accountTable){
		this.theFrame = theFrame;
		this.accountTable = accountTable;
		initUI();
	}

	private void initUI() {
		createDialog();
		setUP();
		usePanel();
	}

	private void createDialog() {
		d = new JDialog(theFrame, "Create user account", true);
		d.setSize(400,400);
		d.setLocationRelativeTo(null);
		d.setResizable(false);
	}

	private void setUP() {
		createAccountPanel = new JPanel(new GridBagLayout());	
		GridBagConstraints gbc = new GridBagConstraints();

		// ************************* createUserPanel UI SetUP
		// *************************
		// ========================= createUserPanel Level One UI Create
		// =========================
		createUserLabel = new JLabel("Please insert user account information");
		gbc.weightx = 0;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.insets = new Insets(5, 5, 5, 5);
		createAccountPanel.add(createUserLabel, gbc);
		
		createUserLabel = new JLabel("Staff Type: ");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 1;
		createAccountPanel.add(createUserLabel, gbc);
		
		userButton = new JRadioButton("User");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 1;
		gbc.gridy = 1;
		group.add(userButton);
		userButton.setSelected(true);
		createAccountPanel.add(userButton, gbc);
		
		viewerButton = new JRadioButton("Viewer");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 2;
		gbc.gridy = 1;
		group.add(viewerButton);
		createAccountPanel.add(viewerButton, gbc);

		createUserLabel = new JLabel("Staff: ");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 2;
		createAccountPanel.add(createUserLabel, gbc);

		createUsernameTextField = new JTextField();
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 2;
		createAccountPanel.add(createUsernameTextField, gbc);

		createUserLabel = new JLabel("New Password: ");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		createAccountPanel.add(createUserLabel, gbc);

		createPassword1TextField = new JPasswordField();
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 3;
		createAccountPanel.add(createPassword1TextField, gbc);

		createUserLabel = new JLabel("Reconfirm New Password: ");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 4;
		createAccountPanel.add(createUserLabel, gbc);

		createPassword2TextField = new JPasswordField();
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 4;
		createAccountPanel.add(createPassword2TextField, gbc);

		createUserLabel = new JLabel();
		gbc.gridx = 0;
		gbc.gridy = 5;
		createAccountPanel.add(createUserLabel, gbc);

		createUsernameButton = new JButton("Save");
		gbc.gridx = 1;
		gbc.gridy = 6;
		//change account password
		createUsernameButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				password = createPassword1TextField.getText();
				if(createPassword1TextField.getText().equals("") || createPassword2TextField.getText().equals("")){
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"Error: Password field cannot be null, Please try again.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}else if(createUsernameTextField.getText().equals("")){
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"Error: Username field cannot be null, Please try again.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}else{
					if(createPassword1TextField.getText().equals(createPassword2TextField.getText())){
						IRBS irbs = new IRBS();
						MD5 md5 = new MD5();
						try {
							if(userButton.isSelected()){
								irbs.createUserAccount(createUsernameTextField.getText(), md5.getMD5(password));
								JOptionPane
								.showMessageDialog(
										null,
										"Success create user account",
										"Success", JOptionPane.INFORMATION_MESSAGE);
							}else if(viewerButton.isSelected()){
								irbs.createViewerAccount(createUsernameTextField.getText(), md5.getMD5(password));
								JOptionPane
								.showMessageDialog(
										null,
										"Success create viewer account",
										"Success", JOptionPane.INFORMATION_MESSAGE);
							}else{
								JOptionPane
								.showMessageDialog(
										errorPanel,
										"Error: Staff type cannot be null, Please try again.",
										"Error", JOptionPane.ERROR_MESSAGE);
							}
							irbs.getCon().close();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else{
						JOptionPane
						.showMessageDialog(
								errorPanel,
								"Error: New Password are not the same, Please try again.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				d.dispose();
				repaintToShowAllAccount();
			}
		});
		createAccountPanel.add(createUsernameButton, gbc);
	}

	private void usePanel() {
		d.setContentPane(createAccountPanel);
		d.setVisible(true);		
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
}
