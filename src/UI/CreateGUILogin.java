package UI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import adminAnduserUI.CreateGUIBackgroundAdmin;
import adminAnduserUI.CreateGUIBackgroundUser;
import domain.IPandPort;
import domain.IRBS;
import domain.MD5;
import domain.Restoredbtosql;
import viewerUI.CreateViewerGUI;

public class CreateGUILogin {
	private JFrame theFrame;
	private JPanel loginPanel;
	private JPanel IPPanel;
	private JPanel errorPanel;
	private JPanel reportPanel;
	// =============================
	private JMenuBar theMenuBar;
	
	private JMenu LoginMenu;
	private JMenu IPMenu;
	private JMenu BackupMenu;
	private JMenu exitMenu;
	
	private JMenuItem LoginMenuItem;
	private JMenuItem IPMenuItem;
	private JMenuItem BackupMenuItem;
	private JMenuItem exitMenuItem;
	// =============================
	private JLabel loginLabel;
	private JLabel loginStaffIDLabel;
	private JLabel loginPasswordLabel;
	// =============================
	private JTextField loginStaffIDTextField;
	private JPasswordField loginPasswordTextField;
	private JTextField ipTextField;
	private JTextField portTextField;
	// =============================
	InetAddress localhost;
	// =============================
	private JButton loginButton;
	
	public CreateGUILogin() {
		initUI();
	}

	private void initUI() {
		createFrame();
		setUp();
		useDefaultPanel();
		//testing
//		loginStaffIDTextField.setText("admin");
//		loginPasswordTextField.setText("password");
		
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("leeray.config.properties");

			// load a properties file
			prop.load(input);
			ipTextField.setText(prop.getProperty("ip"));
			portTextField.setText(prop.getProperty("port"));
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void createFrame() {
		theFrame = new JFrame("Leeray Purchase Order Database");
		theFrame.setSize(400, 250);
		theFrame.setResizable(true);
		theFrame.setLocationRelativeTo(null);
	}

	private void setUp() {
		// Initialize the JMenuBar First
		theMenuBar = new JMenuBar();
		LoginMenu = new JMenu("Login");
		LoginMenuItem = new JMenuItem("Login");
		LoginMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				useLoginPanel();				
			}
		});
		LoginMenu.add(LoginMenuItem);
		
		IPMenu = new JMenu("IP");
		IPMenuItem = new JMenuItem("IP");
		IPMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				useIPPanel();				
			}
		});
		IPMenu.add(IPMenuItem);

		BackupMenu = new JMenu("Restore");
		BackupMenuItem = new JMenuItem("Restore");
		BackupMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Restoredbtosql sql = new Restoredbtosql();
				try {
					if(sql.restore()){
						JOptionPane
						.showMessageDialog(
								reportPanel,
								"The restore has been successful.",
								"Success", JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane
						.showMessageDialog(
								errorPanel,
								"Error on restore the database.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (HeadlessException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		BackupMenu.add(BackupMenuItem);
		
		// Initialize the Exit Menu and the Item(s)
		// Add it to Menu with .addActionListener
		exitMenu = new JMenu("Exit");
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitMenu.add(exitMenuItem);
		
		theMenuBar.add(LoginMenu);
		theMenuBar.add(IPMenu);
		theMenuBar.add(BackupMenu);	
		theMenuBar.add(exitMenu);
		
		loginPanel = new JPanel(new GridBagLayout());
		IPPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		loginStaffIDLabel = new JLabel("Staff: ");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 10, 5 ,10);
		loginPanel.add(loginStaffIDLabel, gbc);

		loginStaffIDTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.2;
		loginPanel.add(loginStaffIDTextField, gbc);

		loginPasswordLabel = new JLabel("Password: ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		loginPanel.add(loginPasswordLabel, gbc);

		loginPasswordTextField = new JPasswordField();
		gbc.gridx = 1;
		gbc.gridy = 2;
		loginPasswordTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					try {
						login();
					} catch (Exception e1) {
						
					}
				}
			}
		});
		loginPanel.add(loginPasswordTextField, gbc);
		
		try { 
			localhost = InetAddress.getLocalHost(); 
			System.out.println ("localhost: "+localhost.getHostAddress()); 
			System.out.println ("localhost: "+localhost.getHostName()); 
		} catch(UnknownHostException uhe) { 
			System.err.println ("Localhost not seeable. Something is odd. "); 
		} 
		
		loginLabel = new JLabel("Check IP Address: ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		IPPanel.add(loginLabel, gbc);
		
		loginLabel = new JLabel(localhost.getHostAddress());
		gbc.gridx = 1;
		gbc.gridy = 3;
		IPPanel.add(loginLabel, gbc);
		
		loginLabel = new JLabel("Set IP Address: ");
		gbc.gridx = 0;
		gbc.gridy = 4;
		IPPanel.add(loginLabel, gbc);
		
//		ipTextField = new JTextField("192.168.1.101");
//		gbc.gridx = 1;
//		gbc.gridy = 4;
//		IPPanel.add(ipTextField, gbc);
		
		ipTextField = new JTextField("localhost");
		gbc.gridx = 1;
		gbc.gridy = 4;
		IPPanel.add(ipTextField, gbc);
		
		loginLabel = new JLabel("Set Port: ");
		gbc.gridx = 0;
		gbc.gridy = 5;
		IPPanel.add(loginLabel, gbc);
		
		portTextField = new JTextField("3306");
		gbc.gridx = 1;
		gbc.gridy = 5;
		IPPanel.add(portTextField, gbc);
		
		loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					login();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 9;
		loginPanel.add(loginButton, gbc);
	}

	private void useDefaultPanel() {
		theFrame.setContentPane(loginPanel);
		theFrame.setJMenuBar(theMenuBar);
		theFrame.setVisible(true);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void useLoginPanel(){
		theFrame.setContentPane(loginPanel);
		theFrame.setJMenuBar(theMenuBar);
		theFrame.setVisible(true);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void useIPPanel(){
		theFrame.setContentPane(IPPanel);
		theFrame.setJMenuBar(theMenuBar);
		theFrame.setVisible(true);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void login() throws IOException {
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream("leeray.config.properties");
			// set the properties value
			prop.setProperty("ip", ipTextField.getText());
			prop.setProperty("port", portTextField.getText());

			// save properties to project root folder
			prop.store(output, null);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		IPandPort IPandPort = new IPandPort();
		IPandPort.setIP(ipTextField.getText());
		IPandPort.setPort(portTextField.getText());
		System.out.println(ipTextField.getText() + ":" + portTextField.getText());
		IRBS irbs = new IRBS();
		try {
			String staffID = loginStaffIDTextField.getText();
			String password = loginPasswordTextField.getText();
			MD5 md5 = new MD5();
			if (irbs.loginAdmin(staffID,md5.getMD5(password))) {						
				CreateGUIBackgroundAdmin gui = new CreateGUIBackgroundAdmin(loginStaffIDTextField.getText());
				theFrame.dispose();
			} else if(irbs.loginUser(staffID,md5.getMD5(password))){						
				CreateGUIBackgroundUser gui = new CreateGUIBackgroundUser(loginStaffIDTextField.getText());
				theFrame.dispose();
			}else if(irbs.loginViewer(staffID,md5.getMD5(password))){						
				CreateViewerGUI gui = new CreateViewerGUI(loginStaffIDTextField.getText());
				theFrame.dispose();
			}else{
				loginStaffIDTextField.setText("");
				loginPasswordTextField.setText("");
				JOptionPane
				.showMessageDialog(
						loginPanel,
						"Please make sure you have input correct Staff ID and corresponding password!",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				irbs.getCon().close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
