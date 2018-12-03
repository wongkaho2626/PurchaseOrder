package adminAnduserUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import com.mysql.jdbc.StringUtils;

import domain.Backupdbtosql;
import domain.CreateSASparePartReport;
import domain.CreateTotalUnitReport;
import domain.DataReportDateVendor;
import domain.IRBS;
import domain.DataReportSASparePart;

public class CreateGUIReportSASparePart {
	private JFrame theFrame;
	
	private JLabel reportLabel;
	private JTextField startETDTextField;
	private JTextField endETDTextField;
	private JTextField productNoTextField;
	private JTextField poNoTextField;
	private JComboBox selectVendorComboBox;
	
	private JTable poTable;
	
	private ArrayList<DataReportSASparePart> data;
	private CreateSASparePartReport createSASparePartReport;
	
	private CreateGUIReportSASparePartViewTable createGUIReportSAViewTable;

	private JButton reportButton;

	private JPanel errorPanel;
	private JPanel reportPanel;
	private JScrollPane reportScrollPane;
	
	public CreateGUIReportSASparePart(){
		initUI();
	}

	private void initUI() {
		setUP();
	}

	private void setUP() {
		reportPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		reportScrollPane = new JScrollPane(reportPanel);

		// ************************* reportPanel UI SetUP
		// *************************
		// ========================= reportPanel Level One UI Create
		// =========================

		reportLabel = new JLabel("SA Spare Part Report");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 8;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		reportPanel.add(reportLabel, gbc);
		
		reportLabel = new JLabel("ETD (MM/DD/YY)*: ");
		gbc.ipady = 10;
		gbc.ipadx = 50;
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 100002;
		reportPanel.add(reportLabel, gbc);

		reportLabel = new JLabel("From: ");
		gbc.ipadx = 0;
		gbc.gridwidth = 1;
		gbc.gridx = 4;
		gbc.gridy = 100002;
		reportPanel.add(reportLabel, gbc);

		startETDTextField = new JTextField("", 3);
		gbc.ipadx = 100;
		gbc.gridx = 5;
		gbc.gridy = 100002;
		startETDTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(checkMM(startETDTextField.getText())){
						startETDTextField.setText(startETDTextField.getText() + "/");
					}
					if(checkDD(startETDTextField.getText())){
						startETDTextField.setText(startETDTextField.getText() + "/");
					}
					reportPanel.revalidate();  
					reportPanel.repaint();
				}
			}
		});
		reportPanel.add(startETDTextField, gbc);

		reportLabel = new JLabel("            To: ");
		gbc.gridx = 6;
		gbc.ipadx = 0;
		gbc.gridy = 100002;
		reportPanel.add(reportLabel, gbc);

		endETDTextField = new JTextField("", 3);
		gbc.ipadx = 100;
		gbc.gridx = 7;
		gbc.gridy = 100002;
		endETDTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){ 
					if(checkMM(endETDTextField.getText())){
						endETDTextField.setText(endETDTextField.getText() + "/");
					}
					if(checkDD(endETDTextField.getText())){
						endETDTextField.setText(endETDTextField.getText() + "/");
					}
					reportPanel.revalidate();  
					reportPanel.repaint();
				}
			}
		});
		reportPanel.add(endETDTextField, gbc);
		
//		reportLabel = new JLabel("Vendor*: ");
//		gbc.ipady = 10;
//		gbc.ipadx = 50;
//		gbc.gridwidth = 4;
//		gbc.gridx = 0;
//		gbc.gridy = 100003;
//		reportPanel.add(reportLabel, gbc);
		
//		IRBS irbs = new IRBS();
//		selectVendorComboBox = new JComboBox(irbs.vendorStatement(true).toArray());
//		selectVendorComboBox.setEditable(false);
//		try {
//			irbs.getCon().close();
//		} catch (SQLException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//		gbc.gridwidth = 4;
//		gbc.gridx = 4;
//		gbc.gridy = 100003;
//		reportPanel.add(selectVendorComboBox, gbc);
//		
//		reportLabel = new JLabel("Part No: ");
//		gbc.ipady = 10;
//		gbc.ipadx = 50;
//		gbc.gridwidth = 4;
//		gbc.gridx = 0;
//		gbc.gridy = 100004;
//		reportPanel.add(reportLabel, gbc);
//		
//		productNoTextField = new JTextField();
//		gbc.gridwidth = 4;
//		gbc.gridx = 4;
//		gbc.gridy = 100004;
//		reportPanel.add(productNoTextField, gbc);
//		
//		reportLabel = new JLabel("Purchase Order number: ");
//		gbc.ipady = 10;
//		gbc.ipadx = 50;
//		gbc.gridwidth = 4;
//		gbc.gridx = 0;
//		gbc.gridy = 100005;
//		reportPanel.add(reportLabel, gbc);
//		
//		poNoTextField = new JTextField();
//		gbc.gridwidth = 4;
//		gbc.gridx = 4;
//		gbc.gridy = 100005;
//		reportPanel.add(poNoTextField, gbc);
//		
//		
//		poTable = new JTable(){
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//			public Class getColumnClass(int column) {
//                switch (column) {
//                    case 0:
//                        return String.class;
//                    case 1:
//                    	return String.class;
//                    case 2:
//                    	return String.class;
//                    case 3:
//                    	return String.class;
//                    case 4:
//                    	return String.class;
//                    default:
//                        return Boolean.class;
//                }
//            }
//			@Override
//			public boolean isCellEditable(int row, int col) {
//			    return (col == 5); 
//			}
//			
//		};
//		 ((JComponent) poTable.getDefaultRenderer(Boolean.class)).setOpaque(true);
		 
		reportButton = new JButton("Report");
		gbc.ipadx = 0;
		gbc.gridwidth = 2;
		gbc.gridx = 8;
		gbc.gridy = 1000000;
		reportButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!startETDTextField.getText().equals("") && !endETDTextField.getText().equals("")){
					
					String sql = "SELECT sparepartorderitem.PRODUCT, sparepartorderitem.poNumber, sparepartorderitem.vendor, SUM(sparepartorderitem.quantity) as quantity, SUM(sparepartorderitem.quantity)-SUM(sparepartorderitem.SAremain) AS saUnit, SUM(sparepartorderitem.SAremain) as SAremain\n" 
							+ "FROM sparepartorderitem "
							+ "WHERE sparepartOrderItem.ETD >= STR_TO_DATE(\'" + startETDTextField.getText() + "\', '%m/%d/%Y') "
							+ "AND sparepartOrderItem.ETD <= STR_TO_DATE(\'" + endETDTextField.getText() + "\', '%m/%d/%Y') "
							+ "GROUP BY sparepartorderitem.poNumber, sparepartOrderItem.PRODUCT "
							+ "ORDER BY sparepartOrderItem.PRODUCT, sparepartOrderItem.poNumber ASC";
					
					System.out.println(sql);
					data = new ArrayList<DataReportSASparePart>();
					IRBS irbs = new IRBS();
					data = irbs.reportSASparePart(sql);
					for(DataReportSASparePart d : data) {
						System.out.println(d.getPRODUCT() + " | " + d.getPoNumber() + " | " + d.getVendor() + " | " + d.getQuantity() + " | " + d.getSaUnit() + " | " + d.getSAremain());
					}
					createSASparePartReport = new CreateSASparePartReport(data, startETDTextField.getText(), endETDTextField.getText());
					
					startETDTextField.setText(null);
					endETDTextField.setText(null);
					try {
						irbs.getCon().close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}else{
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"There must be at least one shipment day",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		reportPanel.add(reportButton, gbc);
	}
	
	private boolean checkDD(String d){
		String[] dd = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"
				, "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
		String[] mm = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		for(String i : dd){
			for(String j : mm){
				if(d.equals(j + "/" + i)){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkMM(String m){
		String[] mm = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		for(String i : mm){
			if(m.equals(i)){
				return true;
			}
		}
		return false;
	}

	public JScrollPane reportPane(){
		return reportScrollPane;
	}
}
