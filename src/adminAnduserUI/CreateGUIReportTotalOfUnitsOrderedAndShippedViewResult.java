package adminAnduserUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import domain.IRBS;

public class CreateGUIReportTotalOfUnitsOrderedAndShippedViewResult {
	private JFrame theFrame;
	private JPanel createGUIReportPanel;
	private JPanel errorPanel;
	private JDialog d;
		
	private JLabel createGUIReportLabel;
	private JTextField partNoTextField, orderedQuantityTextField, shippedQuantityTextField;
	private String partNo, startDate, endDate;
	private int orderedQuantity = 0;
	private int shippedQuantity = 0;
	private 	NumberFormat nf = new DecimalFormat("###,###,###,###,###");			

	public CreateGUIReportTotalOfUnitsOrderedAndShippedViewResult(String partNo, int orderedQuantity, int shippedQuantity, String startDate, String endDate) {
		this.partNo = partNo;
		this.orderedQuantity = orderedQuantity;
		this.shippedQuantity = shippedQuantity;
		this.startDate = startDate;
		this.endDate = endDate;
		initUI();
	}

	private void initUI() {
		createDialog();
		setUP();
		usePanel();
	}

	private void createDialog() {
		d = new JDialog(theFrame, "Search Result: ", true);
		d.setSize(600,300);
		d.setLocationRelativeTo(null);
		d.setResizable(false);
	}
	
	private void setUP() {
		createGUIReportPanel = new JPanel(new GridBagLayout());	
		GridBagConstraints gbc = new GridBagConstraints();
		
		// ************************* createGUIPOViewTablePanel UI SetUP
		// *************************
		// ========================= createGUIPOViewTablePanel Level One UI Create
		// =========================
		createGUIReportLabel = new JLabel("Search Result from " + startDate + " to " + endDate + ":");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		createGUIReportPanel.add(createGUIReportLabel, gbc);
		
		createGUIReportLabel = new JLabel("Part No: ");
		gbc.ipady = 10;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		createGUIReportPanel.add(createGUIReportLabel, gbc);
		
		partNoTextField = new JTextField(partNo);
		partNoTextField.setEditable(false);
		gbc.gridwidth = 5;
		gbc.gridx = 1;
		gbc.gridy = 1;
		createGUIReportPanel.add(partNoTextField, gbc);
		
		createGUIReportLabel = new JLabel("Total Units Ordered: ");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		createGUIReportPanel.add(createGUIReportLabel, gbc);
		
		orderedQuantityTextField = new JTextField(nf.format(orderedQuantity));
		orderedQuantityTextField.setEditable(false);
		gbc.gridwidth = 5;
		gbc.gridx = 1;
		gbc.gridy = 2;
		createGUIReportPanel.add(orderedQuantityTextField, gbc);
		
		createGUIReportLabel = new JLabel("Total Units Shipped: ");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		createGUIReportPanel.add(createGUIReportLabel, gbc);
		
		shippedQuantityTextField = new JTextField(nf.format(shippedQuantity));
		shippedQuantityTextField.setEditable(false);
		gbc.gridwidth = 5;
		gbc.gridx = 1;
		gbc.gridy = 3;
		createGUIReportPanel.add(shippedQuantityTextField, gbc);
	}
	
	private void usePanel() {
		d.setContentPane(createGUIReportPanel);
		d.setVisible(true);		
	}

}
