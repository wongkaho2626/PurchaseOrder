package viewerUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import domain.IRBS;
import domain.MD5;

public class CreateGUIViewerAccountView {
	private String userID;
	private String currentPassword;
	private String password;
	private JLabel accountLabel;
	private JPasswordField currentPasswordField;
	private JPasswordField passwordField1;
	private JPasswordField passwordField2;
	private JButton accountButton;
	private JPanel errorPanel;

	private JPanel accountPanel;

	public CreateGUIViewerAccountView(String userID){
		this.userID = userID;
		initUI();
	}

	private void initUI() {
		setUP();
	}

	private void setUP() {
		accountPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// ************************* accountPanel UI SetUP
		// *************************
		// ========================= accountPanel Level One UI Create
		// =========================
		accountLabel = new JLabel("Change your password (Viewer)");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 200;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		accountPanel.add(accountLabel, gbc);

		accountLabel = new JLabel("Staff: ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		accountPanel.add(accountLabel, gbc);

		accountLabel = new JLabel(userID);
		gbc.gridx = 1;
		gbc.gridy = 1;
		accountPanel.add(accountLabel, gbc);
		
		accountLabel = new JLabel("Current Password: ");
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 2;
		accountPanel.add(accountLabel, gbc);

		currentPasswordField = new JPasswordField();
		gbc.gridx = 1;
		gbc.gridy = 2;
		accountPanel.add(currentPasswordField, gbc);

		accountLabel = new JLabel("New Password: ");
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 3;
		accountPanel.add(accountLabel, gbc);

		passwordField1 = new JPasswordField();
		gbc.gridx = 1;
		gbc.gridy = 3;
		accountPanel.add(passwordField1, gbc);

		accountLabel = new JLabel("Reconfirm New Password: ");
		gbc.gridx = 0;
		gbc.gridy = 4;
		accountPanel.add(accountLabel, gbc);

		passwordField2 = new JPasswordField();
		gbc.gridx = 1;
		gbc.gridy = 4;
		accountPanel.add(passwordField2, gbc);

		accountLabel = new JLabel();
		gbc.gridx = 0;
		gbc.gridy = 5;
		accountPanel.add(accountLabel, gbc);

		accountButton = new JButton("Save");
		gbc.gridx = 1;
		gbc.gridy = 6;
		accountPanel.add(accountButton, gbc);

		//change account password
		accountButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				password = passwordField1.getText();
				currentPassword = currentPasswordField.getText();
				if(passwordField1.getText().equals("") || passwordField2.getText().equals("")){
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"Error: Password field cannot be null, Please try again.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}else{
					IRBS irbs = new IRBS();
					MD5 md5 = new MD5();
					try {
						if(irbs.loginViewer(userID, md5.getMD5(currentPassword))){
							if(passwordField1.getText().equals(passwordField2.getText())){
								irbs.setViewerPasswordData(userID, md5.getMD5(password));
								JOptionPane
								.showMessageDialog(
										null,
										"Success change the password",
										"Success", JOptionPane.INFORMATION_MESSAGE);

							}else{
								JOptionPane
								.showMessageDialog(
										errorPanel,
										"Error: New Password are not the same, Please try again.",
										"Error", JOptionPane.ERROR_MESSAGE);
							}
						}else{
							JOptionPane
							.showMessageDialog(
									errorPanel,
									"Error: Current password is wrong, Please try again.",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						try {
							irbs.getCon().close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				currentPasswordField.setText(null);
				passwordField1.setText(null);
				passwordField2.setText(null);
			}
		});
	}
	
	public JPanel viewPane(){
		return accountPanel;
	}
}
