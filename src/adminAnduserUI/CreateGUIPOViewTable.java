package adminAnduserUI;

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

import domain.IRBS;

public class CreateGUIPOViewTable {
	private JFrame theFrame;
	private JPanel createGUIPOViewTablePanel;
	private JPanel errorPanel;
	private JDialog d;
	
	private JLabel createGUIPOViewTableLabel;
	private JButton createGUIPOViewTableDeleteButton;
	private JButton createGUIPOViewTableChangeButton;
	private JTable poTable;
	private String searchSQL;
	
	public CreateGUIPOViewTable(JFrame theFrame, JTable poTable, String searchSQL) {
		this.theFrame = theFrame;
		this.poTable = poTable;
		this.searchSQL = searchSQL;
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
		createGUIPOViewTableLabel = new JLabel("Search Result: ");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		createGUIPOViewTablePanel.add(createGUIPOViewTableLabel, gbc);
		
		gbc.gridy = 13;
		gbc.ipady = 10;
		gbc.gridwidth = 8;
		gbc.ipadx = 300;
		gbc.ipady = 250;
		poTable.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me) {
		        JTable table =(JTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table.rowAtPoint(p);
		        System.out.println("Click" + me.getClickCount());
		        if (me.getClickCount() == 2) {
		        	//get the selected maintenance product #
//		        	ListSelectionModel cellSelectionModel = poTable.getSelectionModel();
		        	
					String selectedPO = null;
					int[] selectedRow = poTable.getSelectedRows();

					for (int i = 0; i < selectedRow.length; i++) {
						selectedPO = (String) poTable.getValueAt(selectedRow[i], 0).toString();
					}
					IRBS irbs = new IRBS();
					try{
						//check whether the selected choice is complete unit or spare part or tooling
						if(irbs.checkCompleteUnitDuplicatePO(Integer.parseInt(selectedPO))){
							CreateGUIPOCompleteUnitChange cgui = new CreateGUIPOCompleteUnitChange(theFrame, poTable, Integer.parseInt(selectedPO), searchSQL);
						}else if (irbs.checkSparePartDuplicatePO(Integer.parseInt(selectedPO))){
							CreateGUIPOSparePartChange cgui = new CreateGUIPOSparePartChange(theFrame, poTable, Integer.parseInt(selectedPO), searchSQL);
						}else if (irbs.checkToolingDuplicatePO(Integer.parseInt(selectedPO))){
							CreateGUIPOToolingChange cgui = new CreateGUIPOToolingChange(theFrame, poTable, Integer.parseInt(selectedPO), searchSQL);
						}else if (irbs.checkMiscDuplicatePO(Integer.parseInt(selectedPO))){
							CreateGUIPOMiscChange cgui = new CreateGUIPOMiscChange(theFrame, poTable, Integer.parseInt(selectedPO), searchSQL);
						}
						System.out.println("Selected: " + selectedPO);
					}catch(Exception ex){
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
		
		createGUIPOViewTableDeleteButton = new JButton("Delete");
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 14;
		gbc.ipady = 10;
		createGUIPOViewTableDeleteButton.addActionListener(new ActionListener(){
			//get the selected maintenance product #
			ListSelectionModel cellSelectionModel = poTable.getSelectionModel();
			String poNumber = null;
			public void actionPerformed(ActionEvent e) {
				if(!cellSelectionModel.isSelectionEmpty()){
					int[] selectedRow = poTable.getSelectedRows();

					for (int i = 0; i < selectedRow.length; i++) {
						poNumber = (String) poTable.getValueAt(selectedRow[i], 0).toString();
					}
					try {
						IRBS irbs = new IRBS();
						irbs.deletePO(Integer.parseInt(poNumber));
						System.out.println("Selected: " + poNumber);
						JOptionPane.showMessageDialog(null,
								"You delete the PO " + poNumber + " successfully.", "Message",
								JOptionPane.INFORMATION_MESSAGE);
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
				repaintToShowAll();
			}
		});
		createGUIPOViewTablePanel.add(createGUIPOViewTableDeleteButton, gbc);
		
		createGUIPOViewTableChangeButton = new JButton("Change / View");
		gbc.gridwidth = 4;
		gbc.gridx = 4;
		gbc.gridy = 14;
		createGUIPOViewTableChangeButton.addActionListener(new ActionListener(){
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
							CreateGUIPOCompleteUnitChange cgui = new CreateGUIPOCompleteUnitChange(theFrame, poTable, Integer.parseInt(selectedPO), searchSQL);
						}else if (irbs.checkSparePartDuplicatePO(Integer.parseInt(selectedPO))){
							CreateGUIPOSparePartChange cgui = new CreateGUIPOSparePartChange(theFrame, poTable, Integer.parseInt(selectedPO), searchSQL);
						}else if (irbs.checkToolingDuplicatePO(Integer.parseInt(selectedPO))){
							CreateGUIPOToolingChange cgui = new CreateGUIPOToolingChange(theFrame, poTable, Integer.parseInt(selectedPO), searchSQL);
						}else if (irbs.checkMiscDuplicatePO(Integer.parseInt(selectedPO))){
							CreateGUIPOMiscChange cgui = new CreateGUIPOMiscChange(theFrame, poTable, Integer.parseInt(selectedPO), searchSQL);
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
		createGUIPOViewTablePanel.add(createGUIPOViewTableChangeButton, gbc);
	}
	
	private void usePanel() {
		d.setContentPane(createGUIPOViewTablePanel);
		d.setVisible(true);		
	}
	
	private void repaintToShowAll(){
		IRBS irbs = new IRBS();
		poTable.setModel(irbs.viewSearchPO(searchSQL));
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
