package viewerUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import adminAnduserUI.CreateGUIReportSales;
import adminAnduserUI.CreateGUIReportTotalUnit;

public class CreateViewerGUI {
	private String userID;

	private JFrame theFrame;

	//Menu item
	private JMenuBar theMenuBar;

	private JMenu poMenu;
	private JMenu maintenanceMenu;
	private JMenu AccountMenu;
	private JMenu exitMenu;

	private JMenuItem poViewMenuItem;
	private JMenuItem maintenanceViewMenuItem;
	private JMenuItem optionViewMenuItem;
	private JMenuItem controlOwnAccountMenuItem;
	private JMenuItem exitMenuItem;

	public CreateViewerGUI(String userID){
		this.userID = userID;
		initUI();
	}

	private void initUI(){
		createFrame();
		setUP();
		useDefaultPanel();
	}

	private void setUP(){

	}

	private void createFrame() {
		theFrame = new JFrame("GUI");
		theFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		theFrame.setSize(1200, 800);
		theFrame.setTitle("Main Screen");
		theFrame.setResizable(true);
		theFrame.setLocationRelativeTo(null);	


		// Initialize the JMenuBar First
		theMenuBar = new JMenuBar();

		// Initialize the View Menu and the Item(s)
		// Add it to Menu with .addActionListener
		poMenu = new JMenu("Purchase Order");
		poViewMenuItem = new JMenuItem("View Purchase order");
		poViewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIViewerPOView viewGUI = new CreateGUIViewerPOView();
				if (!theFrame.getContentPane().equals(viewGUI.viewPane())) {
					changePanelToPOViewPanel();
				}
			}
		});
		poMenu.add(poViewMenuItem);

		// Initialize the Maintenance Menu and the Item(s)
		// Add it to Menu with .addActionListener
		maintenanceMenu = new JMenu("Maintenance");
		maintenanceViewMenuItem = new JMenuItem("View Maintenance");
		optionViewMenuItem = new JMenuItem("View Customer Name");
		maintenanceViewMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIViewerMaintenanceView viewMaintenanceGUI = new CreateGUIViewerMaintenanceView();
				if (!theFrame.getContentPane().equals(viewMaintenanceGUI.viewPane())) {
					changePanelToMaintenanceViewPanel();
				}
			}
		});
		optionViewMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIViewerOptionView viewOptionGUI = new CreateGUIViewerOptionView();
				if (!theFrame.getContentPane().equals(viewOptionGUI.viewPane())) {
					changePanelToOptionViewPanel();
				}
			}
		});
		maintenanceMenu.add(maintenanceViewMenuItem);
		maintenanceMenu.add(optionViewMenuItem);

		// Initialize the Account Menu and the Item(s)
		// Add it to Menu with .addActionListener
		AccountMenu = new JMenu("Account");
		controlOwnAccountMenuItem = new JMenuItem("Change Password");
		controlOwnAccountMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIViewerAccountView viewAccountGUI = new CreateGUIViewerAccountView(userID);
				if (!theFrame.getContentPane().equals(viewAccountGUI.viewPane())) {
					changePanelToAccountPanel();
				}
			}
		});
		AccountMenu.add(controlOwnAccountMenuItem);

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

		// Add Menu(s) back to the JMenuBar
		theMenuBar.add(poMenu);
		theMenuBar.add(maintenanceMenu);	
		theMenuBar.add(AccountMenu);
		theMenuBar.add(exitMenu);

		// Set JMenuBar Of theFrame
		theFrame.setJMenuBar(theMenuBar);
	}

	// ************************* use of the different panel in different menu item
	// *************************
	// ========================= use the purchase order view panel to be default panel
	// ========================= 
	private void useDefaultPanel() {
		CreateGUIViewerPOView viewGUI = new CreateGUIViewerPOView();
		theFrame.setContentPane(viewGUI.viewPane());
		theFrame.setVisible(true);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// ========================= use the purchase order view panel
	// =========================
	private void changePanelToPOViewPanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIViewerPOView viewGUI = new CreateGUIViewerPOView();
		theFrame.setContentPane(viewGUI.viewPane());
		viewGUI.poViewReset();
		theFrame.setVisible(true);
	}

	// ========================= use the maintenance view panel
	// =========================
	private void changePanelToMaintenanceViewPanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIViewerMaintenanceView viewMaintenanceGUI = new CreateGUIViewerMaintenanceView();
		theFrame.setContentPane(viewMaintenanceGUI.viewPane());
		theFrame.setVisible(true);
	}
	
	// ========================= use the maintenance view panel
	// =========================
	private void changePanelToOptionViewPanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIViewerOptionView viewOptionGUI = new CreateGUIViewerOptionView();
		theFrame.setContentPane(viewOptionGUI.viewPane());
		theFrame.setVisible(true);
	}

	// ========================= use the account panel
	// =========================
	private void changePanelToAccountPanel() {
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIViewerAccountView viewAccountGUI = new CreateGUIViewerAccountView(userID);
		theFrame.setContentPane(viewAccountGUI.viewPane());
		theFrame.setVisible(true);
	}
}
