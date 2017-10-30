package adminAnduserUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

public class CreateGUIReportSACompleteUnitViewTable {
	private JFrame theFrame;
	private JPanel createGUIReportSAViewTablePanel;
	private JPanel errorPanel;
	private JDialog d;
		
	private JLabel createGUIReportSAViewTableLabel;
	private JButton createGUIReportSAViewTableSearchButton;
	private JTable poTable;
	private String PRODUCT;
	
	public CreateGUIReportSACompleteUnitViewTable(JFrame theFrame, JTable poTable, String PRODUCT) {
		this.theFrame = theFrame;
		this.poTable = poTable;
		this.PRODUCT = PRODUCT;
		initUI();
	}

	private void initUI() {
		createDialog();
		setUP();
		usePanel();
	}

	private void createDialog() {
		d = new JDialog(theFrame, "Search Result: " + PRODUCT, true);
		d.setSize(800,700);
		d.setLocationRelativeTo(null);
		d.setResizable(false);
	}
	
	private void setUP() {
		createGUIReportSAViewTablePanel = new JPanel(new GridBagLayout());	
		GridBagConstraints gbc = new GridBagConstraints();
		
		// ************************* createGUIPOViewTablePanel UI SetUP
		// *************************
		// ========================= createGUIPOViewTablePanel Level One UI Create
		// =========================
		createGUIReportSAViewTableLabel = new JLabel("Search Result: " + PRODUCT);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		createGUIReportSAViewTablePanel.add(createGUIReportSAViewTableLabel, gbc);
		
		gbc.gridy = 13;
		gbc.ipady = 10;
		gbc.gridwidth = 8;
		gbc.ipadx = 300;
		gbc.ipady = 250;
		createGUIReportSAViewTablePanel.add(new JScrollPane(poTable), gbc);
		
		createGUIReportSAViewTableSearchButton = new JButton("Search");
		gbc.gridwidth = 4;
		gbc.gridx = 4;
		gbc.ipady = 10;
		gbc.gridy = 14;
		createGUIReportSAViewTableSearchButton.addActionListener(new ActionListener(){
			//get the selected PO
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> poNumber = new ArrayList<String>();
				ArrayList<String> subCode = new ArrayList<String>();
				for (int i = 0; i < poTable.getRowCount(); i++) {
					boolean isChecked = (Boolean) poTable.getValueAt(i, 2);
					if(isChecked){
						poNumber.add((String) poTable.getValueAt(i, 0).toString());
						subCode.add((String) poTable.getValueAt(i, 1).toString());
					}
				}
				if(poNumber.size() > 0){
					CreateGUIReportSACompleteUnitViewResult cgui = new CreateGUIReportSACompleteUnitViewResult(theFrame, poNumber, PRODUCT, subCode);
				}else{
					JOptionPane.showMessageDialog(null,
							"You did not select the item from the table", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				poNumber.clear();
				subCode.clear();
				for (int i = 0; i < poTable.getRowCount(); i++) {
					poTable.setValueAt(false ,i, 2);
				}
				d.repaint();
			}
		});
		createGUIReportSAViewTablePanel.add(createGUIReportSAViewTableSearchButton, gbc);
	}
	
	private void usePanel() {
		d.setContentPane(createGUIReportSAViewTablePanel);
		d.setVisible(true);		
	}

}
