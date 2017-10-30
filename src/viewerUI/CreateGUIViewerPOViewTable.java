package viewerUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import adminAnduserUI.CreateGUIMaintenanceChange;
import adminAnduserUI.CreateGUIPOMiscChange;
import adminAnduserUI.CreateGUIPOToolingChange;
import domain.IRBS;

public class CreateGUIViewerPOViewTable {
	private JFrame theFrame;
	private JPanel createGUIPOViewTablePanel;
	private JPanel errorPanel;
	private JDialog d;
	
	private JLabel createGUIPOViewTableLabel;
	private JButton createGUIPOViewTableDeleteButton;
	private JButton createGUIPOViewTableViewButton;
	private JTable poTable;
	
	public CreateGUIViewerPOViewTable(JFrame theFrame, JTable poTable) {
		this.theFrame = theFrame;
		this.poTable = poTable;
		initUI();
	}

	private void initUI() {
		createDialog();
		setUP();
		usePanel();
	}

	private void createDialog() {
		d = new JDialog(theFrame, "Search Result", true);
		d.setSize(850,500);
		d.setLocationRelativeTo(null);
		d.setResizable(false);
	}
	
	private void setUP() {
		createGUIPOViewTablePanel = new JPanel(new GridBagLayout());	
		GridBagConstraints gbc = new GridBagConstraints();
		
		// ************************* createGUIPOViewTablePanel UI SetUP
		// *************************
		// ========================= createGUIPOViewTablePanel Level One UI Create
		// =========================
		createGUIPOViewTableLabel = new JLabel("Search Result:                        ");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		createGUIPOViewTablePanel.add(createGUIPOViewTableLabel, gbc);
		
		gbc.gridwidth = 2;
		gbc.gridy = 13;
		gbc.ipady = 10;
		gbc.ipadx = 300;
		gbc.ipady = 250;
		
		poTable.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me) {
		        JTable table =(JTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table.rowAtPoint(p);
		        if (me.getClickCount() == 2) {
		        	//get the selected maintenance product #
		        	ListSelectionModel cellSelectionModel = poTable.getSelectionModel();
					String selectedPO = null;
					
					int[] selectedRow = poTable.getSelectedRows();

					for (int i = 0; i < selectedRow.length; i++) {
						selectedPO = (String) poTable.getValueAt(selectedRow[i], 0).toString();
					}
					IRBS irbs = new IRBS();
					try{
						//check whether the selected choice is complete unit or spare part or tooling
						if(irbs.checkCompleteUnitDuplicatePO(Integer.parseInt(selectedPO))){
							CreateGUIViewerPOViewCompleteUnit cgui = new CreateGUIViewerPOViewCompleteUnit(theFrame, Integer.parseInt(selectedPO));
						}else if (irbs.checkSparePartDuplicatePO(Integer.parseInt(selectedPO))){
							CreateGUIViewerPOViewSparePart cgui = new CreateGUIViewerPOViewSparePart(theFrame, Integer.parseInt(selectedPO));
						}else if (irbs.checkToolingDuplicatePO(Integer.parseInt(selectedPO))){
							CreateGUIViewerPOViewTooling cgui = new CreateGUIViewerPOViewTooling(theFrame, Integer.parseInt(selectedPO));
						}else if (irbs.checkMiscDuplicatePO(Integer.parseInt(selectedPO))){
							CreateGUIViewerPOViewMisc cgui = new CreateGUIViewerPOViewMisc(theFrame, Integer.parseInt(selectedPO));
						}
						System.out.println("Selected: " + selectedPO);
					}catch (Exception ex){
						ex.printStackTrace();
					}finally{
						try {
							if(irbs.getCon() != null){
								irbs.getCon().close();
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
		        }
		    }
		});
		
		createGUIPOViewTablePanel.add(new JScrollPane(poTable), gbc);
		
		createGUIPOViewTableLabel = new JLabel();
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 14;
		gbc.ipady = 10;
		createGUIPOViewTablePanel.add(createGUIPOViewTableLabel, gbc);
		
		createGUIPOViewTableViewButton = new JButton("View");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 14;
		gbc.ipady = 10;
		createGUIPOViewTableViewButton.addActionListener(new ActionListener(){
			//get the selected maintenance product #
			ListSelectionModel cellSelectionModel = poTable.getSelectionModel();
			String selectedPO = null;
			public void actionPerformed(ActionEvent e) {
				if(!cellSelectionModel.isSelectionEmpty()){
					int[] selectedRow = poTable.getSelectedRows();

					for (int i = 0; i < selectedRow.length; i++) {
						selectedPO = (String) poTable.getValueAt(selectedRow[i], 0).toString();
					}
					IRBS irbs = new IRBS();
					try{
						//check whether the selected choice is complete unit or spare part or tooling
						if(irbs.checkCompleteUnitDuplicatePO(Integer.parseInt(selectedPO))){
							CreateGUIViewerPOViewCompleteUnit cgui = new CreateGUIViewerPOViewCompleteUnit(theFrame, Integer.parseInt(selectedPO));
						}else if (irbs.checkSparePartDuplicatePO(Integer.parseInt(selectedPO))){
							CreateGUIViewerPOViewSparePart cgui = new CreateGUIViewerPOViewSparePart(theFrame, Integer.parseInt(selectedPO));
						}else if (irbs.checkToolingDuplicatePO(Integer.parseInt(selectedPO))){
							CreateGUIViewerPOViewTooling cgui = new CreateGUIViewerPOViewTooling(theFrame, Integer.parseInt(selectedPO));
						}else if (irbs.checkMiscDuplicatePO(Integer.parseInt(selectedPO))){
							CreateGUIViewerPOViewMisc cgui = new CreateGUIViewerPOViewMisc(theFrame, Integer.parseInt(selectedPO));
						}
						System.out.println("Selected: " + selectedPO);
					}catch (Exception ex){
						ex.printStackTrace();
					}finally{
						try {
							if(irbs.getCon() != null){
								irbs.getCon().close();
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}else{
					JOptionPane.showMessageDialog(null,
							"You did not select the item from the table", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		createGUIPOViewTablePanel.add(createGUIPOViewTableViewButton, gbc);
	}
	
	private void usePanel() {
		d.setContentPane(createGUIPOViewTablePanel);
		d.setVisible(true);		
	}

}
