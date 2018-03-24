package adminAnduserUI;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import domain.Backupdbtosql;
import domain.Restoredbtosql;

public class CreateGUIBackgroundAdmin {
	private String userID;

	private JFrame theFrame;
	private JPanel errorPanel;
	private JPanel reportPanel;

	//Menu item
	private JMenuBar theMenuBar;

	private JMenu poMenu;
	private JMenu maintenanceMenu;
	private JMenu reportMenu;
	private JMenu AccountMenu;
	private JMenu BackupMenu;
	private JMenu exitMenu;

	private JMenuItem poViewMenuItem;
	private JMenuItem poInsertCompleteUnitMenuItem;
	private JMenuItem poInsertSparePartMenuItem;
	private JMenuItem poInsertToolingMenuItem;
	private JMenuItem poInsertMiscMenuItem;
	
	private JMenuItem maintenanceViewMenuItem;
	private JMenuItem maintenanceInsertMenuItem;
	
	private JMenuItem optionViewMenuItem;
	private JMenuItem optionInsertMenuItem; 
	
	private JMenuItem reportTotalUnitsMenuItem;
	private JMenuItem reportSalesMenuItem;
	private JMenuItem reportDateVendorMenuItem;
	private JMenuItem reportPRODUCTDataBaseMenuItem;
	private JMenuItem reportSACompleteUnitMenuItem;
	private JMenuItem reportSASparePartMenuItem;
	private JMenuItem reportTotalOfUnitsOrderedAndShippedMenuItem;
	
	private JMenuItem controlOwnAccountMenuItem;
	private JMenuItem controlOtherAccountMenuItem;
	
	private JMenuItem backupMenuItem;
	private JMenuItem restoreMenuItem;
	
	private JMenuItem exitMenuItem;
		
	public CreateGUIBackgroundAdmin(String userID){
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
		theFrame.setTitle("Leeray Purchase Order Database");
		theFrame.setResizable(true);
		theFrame.setLocationRelativeTo(null);	


		// Initialize the JMenuBar First
		theMenuBar = new JMenuBar();

		// Initialize the View Menu and the Item(s)
		// Add it to Menu with .addActionListener
		poMenu = new JMenu("Purchase Order");
		poViewMenuItem = new JMenuItem("View Purchase order");
		poInsertCompleteUnitMenuItem = new JMenuItem("Insert Complete Product");
		poInsertSparePartMenuItem = new JMenuItem("Insert Spare Part");
		poInsertToolingMenuItem = new JMenuItem("Insert Tooling");
		poInsertMiscMenuItem = new JMenuItem("Insert Misc.");
		poViewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIPOView viewGUI = new CreateGUIPOView();
				if (!theFrame.getContentPane().equals(viewGUI.viewPane())) {
					changePanelToPOViewPanel();
				}
			}
		});
		poInsertCompleteUnitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIPOCompleteUnitInsert insertCompleteUnitGUI = new CreateGUIPOCompleteUnitInsert();
				if (!theFrame.getContentPane().equals(insertCompleteUnitGUI.insertCompleteUnitPane())) {
					changePanelPOCompleteUnitPartPanel();
				}
			}
		});
		poInsertSparePartMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIPOSparePartInsert insertSparePartGUI = new CreateGUIPOSparePartInsert();
				if (!theFrame.getContentPane().equals(insertSparePartGUI.insertSparePartPane())) {
					changePanelToPOInsertSparePartPanel();
				}
			}
		});
		poInsertToolingMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIPOToolingInsert insertToolingGUI = new CreateGUIPOToolingInsert();
				if (!theFrame.getContentPane().equals(insertToolingGUI.insertToolingPane())) {
					changePanelToPOInsertToolingPanel();
				}
			}
		});
		poInsertMiscMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIPOMiscInsert insertMiscGUI = new CreateGUIPOMiscInsert();
				if (!theFrame.getContentPane().equals(insertMiscGUI.insertMiscPane())) {
					changePanelToPOInsertMiscPanel();
				}
			}
		});
		poMenu.add(poViewMenuItem);
		poMenu.add(poInsertCompleteUnitMenuItem);
		poMenu.add(poInsertSparePartMenuItem);
		poMenu.add(poInsertToolingMenuItem);
		poMenu.add(poInsertMiscMenuItem);

		// Initialize the Maintenance Menu and the Item(s)
		// Add it to Menu with .addActionListener
		maintenanceMenu = new JMenu("Maintenance");
		maintenanceViewMenuItem = new JMenuItem("View Maintenance");		
		optionViewMenuItem = new JMenuItem("View Customer Name");
		maintenanceInsertMenuItem = new JMenuItem("Insert Maintenance");
		optionInsertMenuItem = new JMenuItem("Insert Customer Name");
		maintenanceViewMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIMaintenanceView viewMaintenanceGUI = new CreateGUIMaintenanceView();
				if (!theFrame.getContentPane().equals(viewMaintenanceGUI.viewPane())) {
					changePanelToMaintenanceViewPanel();
				}
			}
		});
		optionViewMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIOptionView viewOptionGUI = new CreateGUIOptionView();
				if (!theFrame.getContentPane().equals(viewOptionGUI.viewPane())) {
					changePanelToOptionViewPanel();
				}
			}
		});
		maintenanceInsertMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIMaintenanceInsert insertMaintenanceGUI = new CreateGUIMaintenanceInsert();
				if (!theFrame.getContentPane().equals(insertMaintenanceGUI.insertPane())) {
					changePanelToMaintenanceInsertPanel();
				}
			}
		});
		optionInsertMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIOptionInsert insertOptionGUI = new CreateGUIOptionInsert();
				if (!theFrame.getContentPane().equals(insertOptionGUI.insertPane())) {
					changePanelToOptionInsertPanel();
				}
			}
		});
		maintenanceMenu.add(maintenanceViewMenuItem);
		maintenanceMenu.add(maintenanceInsertMenuItem);
		maintenanceMenu.add(optionViewMenuItem);
		maintenanceMenu.add(optionInsertMenuItem);

		// Initialize the Report Menu and the Item(s)
		// Add it to Menu with .addActionListener
		reportMenu = new JMenu("Report");
		reportTotalUnitsMenuItem = new JMenuItem("Total Units Report");
		reportSalesMenuItem = new JMenuItem("Sales Prediction Report");
		reportDateVendorMenuItem = new JMenuItem("Vendor & PO Report");
		reportPRODUCTDataBaseMenuItem = new JMenuItem("Vendor PRODUCT Database Report");
		reportSACompleteUnitMenuItem = new JMenuItem("SA Complete Unit Report");
		reportSASparePartMenuItem = new JMenuItem("SA Spare Part Report");
		reportTotalOfUnitsOrderedAndShippedMenuItem = new JMenuItem("Total of Units Ordered and Shipped Report");
		reportTotalUnitsMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIReportTotalUnit reportTotalUnitGUI = new CreateGUIReportTotalUnit();
				if (!theFrame.getContentPane().equals(reportTotalUnitGUI.reportPane())) {
					changePanelToReportTotalUnitPanel();
				}
			}
		});
		reportSalesMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIReportSales reportSalesGUI = new CreateGUIReportSales();
				if (!theFrame.getContentPane().equals(reportSalesGUI.reportPane())) {
					changePanelToReportSalesPanel();
				}
			}
		});
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
		reportMenu.add(reportTotalUnitsMenuItem);
		reportMenu.add(reportSalesMenuItem);
		reportMenu.add(reportDateVendorMenuItem);
		reportMenu.add(reportPRODUCTDataBaseMenuItem);
		reportMenu.add(reportSACompleteUnitMenuItem);		
		reportMenu.add(reportSASparePartMenuItem);
		reportMenu.add(reportTotalOfUnitsOrderedAndShippedMenuItem);

		// Initialize the Account Menu and the Item(s)
		// Add it to Menu with .addActionListener
		AccountMenu = new JMenu("Account");
		controlOwnAccountMenuItem = new JMenuItem("Change Password");
		controlOtherAccountMenuItem = new JMenuItem("Other Account");
		controlOwnAccountMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIAccountAdminView viewAccountGUI = new CreateGUIAccountAdminView(userID);
				if (!theFrame.getContentPane().equals(viewAccountGUI.viewPane())) {
					changePanelToAccountPanel();
				}
			}
		});
		controlOtherAccountMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGUIAccountOtherView viewOtherAccountGUI = new CreateGUIAccountOtherView();
				if (!theFrame.getContentPane().equals(viewOtherAccountGUI.viewPane())) {
					changePanelToOtherAccountPanel();
				}
			}
		});
		AccountMenu.add(controlOwnAccountMenuItem);
		AccountMenu.add(controlOtherAccountMenuItem);
		
		// Initialize the Backup Menu and the Item(s)
		// Add it to Menu with .addActionListener
		BackupMenu = new JMenu("Backup");
		backupMenuItem = new JMenuItem("Backup");
		restoreMenuItem = new JMenuItem("Restore");
		backupMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Backupdbtosql sql = new Backupdbtosql();
				try {
					if(sql.export()){
						JOptionPane
						.showMessageDialog(
								reportPanel,
								"The back up file has been generated.",
								"Success", JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane
						.showMessageDialog(
								errorPanel,
								"Error on create the back up file.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (HeadlessException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		restoreMenuItem.addActionListener(new ActionListener(){
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
		BackupMenu.add(backupMenuItem);
//		BackupMenu.add(restoreMenuItem);

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
		theMenuBar.add(BackupMenu);
		theMenuBar.add(exitMenu);

		// Set JMenuBar Of theFrame
		theFrame.setJMenuBar(theMenuBar);
	}

	// ************************* use of the different panel in different menu item
	// *************************
	// ========================= use the purchase order view panel to be default panel
	// ========================= 
	private void useDefaultPanel() {
		CreateGUIPOView viewGUI = new CreateGUIPOView();
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
		CreateGUIPOView viewGUI = new CreateGUIPOView();
		theFrame.setContentPane(viewGUI.viewPane());
		viewGUI.poViewReset();
		theFrame.setVisible(true);
	}

	// ========================= use the complete unit purchase order insert panel
	// =========================
	private void changePanelPOCompleteUnitPartPanel() {
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIPOCompleteUnitInsert insertCompleteUnitGUI = new CreateGUIPOCompleteUnitInsert();
		theFrame.setContentPane(insertCompleteUnitGUI.insertCompleteUnitPane());
		insertCompleteUnitGUI.poInsertReset();
		theFrame.setVisible(true);
	}

	// ========================= use the spare part purchase order insert panel
	// =========================
	private void changePanelToPOInsertSparePartPanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIPOSparePartInsert insertSparePartGUI = new CreateGUIPOSparePartInsert();
		theFrame.setContentPane(insertSparePartGUI.insertSparePartPane());
		insertSparePartGUI.poInsertReset();
		theFrame.setVisible(true);
	}
	
	// ========================= use the tooling purchase order insert panel
	// =========================
	private void changePanelToPOInsertToolingPanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIPOToolingInsert insertToolingGUI = new CreateGUIPOToolingInsert();
		theFrame.setContentPane(insertToolingGUI.insertToolingPane());
		insertToolingGUI.poInsertReset();
		theFrame.setVisible(true);
	}
	
	// ========================= use the tooling purchase order insert panel
	// =========================
	private void changePanelToPOInsertMiscPanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIPOMiscInsert insertMiscGUI = new CreateGUIPOMiscInsert();
		theFrame.setContentPane(insertMiscGUI.insertMiscPane());
		insertMiscGUI.poInsertReset();
		theFrame.setVisible(true);
	}

	// ========================= use the maintenance view panel
	// =========================
	private void changePanelToMaintenanceViewPanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIMaintenanceView viewMaintenanceGUI = new CreateGUIMaintenanceView();
		theFrame.setContentPane(viewMaintenanceGUI.viewPane());
		viewMaintenanceGUI.repaintToShowAllMaintenance();
		theFrame.setVisible(true);
	}

	// ========================= use the maintenance insert panel
	// =========================
	private void changePanelToMaintenanceInsertPanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIMaintenanceInsert insertMaintenanceGUI = new CreateGUIMaintenanceInsert();
		theFrame.setContentPane(insertMaintenanceGUI.insertPane());
		insertMaintenanceGUI.maintenanceInsertReset();
		theFrame.setVisible(true);
	}
	
	// ========================= use the option view panel
	// =========================
	private void changePanelToOptionViewPanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIOptionView viewOptionGUI = new CreateGUIOptionView();
		theFrame.setContentPane(viewOptionGUI.viewPane());
		viewOptionGUI.repaintToShowAllOption();
		theFrame.setVisible(true);
	}

	// ========================= use the option insert panel
	// =========================
	private void changePanelToOptionInsertPanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIOptionInsert insertOptionGUI = new CreateGUIOptionInsert();
		theFrame.setContentPane(insertOptionGUI.insertPane());
		insertOptionGUI.optionInsertReset();
		theFrame.setVisible(true);
	}

	// ========================= use the report total unit panel
	// =========================
	private void changePanelToReportTotalUnitPanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIReportTotalUnit reportTotalUnitGUI = new CreateGUIReportTotalUnit();
		theFrame.setContentPane(reportTotalUnitGUI.reportPane());
		theFrame.setVisible(true);
	}

	// ========================= use the report sales panel
	// =========================
	private void changePanelToReportSalesPanel(){
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIReportSales reportSalesGUI = new CreateGUIReportSales();
		theFrame.setContentPane(reportSalesGUI.reportPane());
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
		CreateGUIAccountAdminView viewAccountGUI = new CreateGUIAccountAdminView(userID);
		theFrame.setContentPane(viewAccountGUI.viewPane());
		theFrame.setVisible(true);
	}

	// ========================= use the account panel
	// =========================
	private void changePanelToOtherAccountPanel() {
		theFrame.repaint();
		theFrame.revalidate();
		theFrame.remove(theFrame.getContentPane());
		CreateGUIAccountOtherView viewOtherAccountGUI = new CreateGUIAccountOtherView();
		theFrame.setContentPane(viewOtherAccountGUI.viewPane());
		viewOtherAccountGUI.accountViewReset();
		viewOtherAccountGUI.repaintToShowAllAccount();
		theFrame.setVisible(true);
	}
}
