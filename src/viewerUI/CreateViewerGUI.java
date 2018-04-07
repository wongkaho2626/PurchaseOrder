package viewerUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import adminAnduserUI.CreateGUIReportDateVendor;
import adminAnduserUI.CreateGUIReportPRODUCTDataBase;
import adminAnduserUI.CreateGUIReportSACompleteUnit;
import adminAnduserUI.CreateGUIReportSASparePart;
import adminAnduserUI.CreateGUIReportSales;
import adminAnduserUI.CreateGUIReportTotalOfUnitsOrderedAndShipped;
import adminAnduserUI.CreateGUIReportTotalUnit;

public class CreateViewerGUI {
	private String userID;

	private JFrame theFrame;

	//Menu item
	private JMenuBar theMenuBar;

	private JMenu poMenu;
	private JMenu maintenanceMenu;
	private JMenu reportMenu;
	private JMenu AccountMenu;
	private JMenu exitMenu;

	private JMenuItem poViewMenuItem;
	private JMenuItem maintenanceViewMenuItem;
	private JMenuItem optionViewMenuItem;
	private JMenuItem controlOwnAccountMenuItem;
	private JMenuItem exitMenuItem;
	
	private JMenuItem reportDateVendorMenuItem;
	private JMenuItem reportPRODUCTDataBaseMenuItem;
	private JMenuItem reportSACompleteUnitMenuItem;
	private JMenuItem reportSASparePartMenuItem;
	private JMenuItem reportTotalOfUnitsOrderedAndShippedMenuItem;

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
		
		// Initialize the Report Menu and the Item(s)
		// Add it to Menu with .addActionListener
		reportMenu = new JMenu("Report");
		reportDateVendorMenuItem = new JMenuItem("Vendor & PO Report (for checking SA purpose)");
		reportPRODUCTDataBaseMenuItem = new JMenuItem("Vendor PRODUCT Database Report (mainly for checking \"Duty Code\" purpose)");
		reportSACompleteUnitMenuItem = new JMenuItem("SA Complete Unit Report");
		reportSASparePartMenuItem = new JMenuItem("SA Spare Part Report");
		reportTotalOfUnitsOrderedAndShippedMenuItem = new JMenuItem("Total of Units Ordered and Shipped Report");
		reportDateVendorMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIReportDateVendor reportDateVendorGUI = new CreateGUIReportDateVendor();
				if (!theFrame.getContentPane().equals(reportDateVendorGUI.reportPane())) {
					changePanelToReportDateVendorPanel();
				}
			}
		});
		reportPRODUCTDataBaseMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIReportPRODUCTDataBase reportPRODUCTDataBaseGUI = new CreateGUIReportPRODUCTDataBase();
				if (!theFrame.getContentPane().equals(reportPRODUCTDataBaseGUI.reportPane())) {
					changePanelToReportPRODUCTDataBasePanel();
				}
			}
		});
		reportSACompleteUnitMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIReportSACompleteUnit reportSAGUICompleteUnit = new CreateGUIReportSACompleteUnit();
				if (!theFrame.getContentPane().equals(reportSAGUICompleteUnit.reportPane())) {
					changePanelToReportSAPanel();
				}
			}
		});
		reportSASparePartMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIReportSASparePart reportSASparePartGUI = new CreateGUIReportSASparePart();
				if (!theFrame.getContentPane().equals(reportSASparePartGUI.reportPane())) {
					changePanelToReportSASparePartPanel();
				}
			}
		});
		reportTotalOfUnitsOrderedAndShippedMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIReportTotalOfUnitsOrderedAndShipped reportTotalOfUnitsOrderedAndShippedGUI = new CreateGUIReportTotalOfUnitsOrderedAndShipped();
				if (!theFrame.getContentPane().equals(reportTotalOfUnitsOrderedAndShippedGUI.reportPane())) {
					changePanelToReportTotalOfUnitsOrderedAndShippedPanel();
				}
			}
		});
		reportMenu.add(reportDateVendorMenuItem);
		reportMenu.add(reportPRODUCTDataBaseMenuItem);
		reportMenu.add(reportSACompleteUnitMenuItem);
		reportMenu.add(reportSASparePartMenuItem);
		reportMenu.add(reportTotalOfUnitsOrderedAndShippedMenuItem);

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
		theMenuBar.add(reportMenu);
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
	
	// ========================= use the report Date & Vendor panel
	// =========================
	private void changePanelToReportDateVendorPanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIReportDateVendor reportDateVendorGUI = new CreateGUIReportDateVendor();
		theFrame.setContentPane(reportDateVendorGUI.reportPane());
		theFrame.setVisible(true);
	}

	// ========================= use the report PRODUCT DataBase panel
	// =========================
	private void changePanelToReportPRODUCTDataBasePanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIReportPRODUCTDataBase reportPRODUCTDataBaseGUI = new CreateGUIReportPRODUCTDataBase();
		theFrame.setContentPane(reportPRODUCTDataBaseGUI.reportPane());
		theFrame.setVisible(true);
	}

	// ========================= use the report SA panel
	// =========================
	private void changePanelToReportSAPanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIReportSACompleteUnit reportSAGUICompleteUnit = new CreateGUIReportSACompleteUnit();
		theFrame.setContentPane(reportSAGUICompleteUnit.reportPane());
		theFrame.setVisible(true);
	}

	// ========================= use the report SA Spare Part panel
	// =========================
	private void changePanelToReportSASparePartPanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIReportSASparePart reportSASparePartGUI = new CreateGUIReportSASparePart();
		theFrame.setContentPane(reportSASparePartGUI.reportPane());
		theFrame.setVisible(true);
	}
	
	// ========================= use the report Total of Units Ordered and Shipped panel
	// =========================
	private void changePanelToReportTotalOfUnitsOrderedAndShippedPanel() {
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIReportTotalOfUnitsOrderedAndShipped reportTotalOfUnitsOrderedAndShippedGUI = new CreateGUIReportTotalOfUnitsOrderedAndShipped();
		theFrame.setContentPane(reportTotalOfUnitsOrderedAndShippedGUI.reportPane());
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
