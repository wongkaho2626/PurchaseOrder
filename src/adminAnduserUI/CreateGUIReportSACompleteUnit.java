package adminAnduserUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import domain.IRBS;

public class CreateGUIReportSACompleteUnit {
	private JFrame theFrame;
	
	private JLabel reportLabel;
	private JTextField productNoTextField;
	
	private JButton reportButton;

	private JPanel errorPanel;
	private JPanel reportPanel;
	private JScrollPane reportScrollPane;

	private JTable poTable;
	
	private CreateGUIReportSACompleteUnitViewTable createGUIReportSAViewTable;

	public CreateGUIReportSACompleteUnit(){
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

		reportLabel = new JLabel("SA Complete Unit Report");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//gbc.ipadx = 100;
		gbc.gridwidth = 8;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		reportPanel.add(reportLabel, gbc);

		reportLabel = new JLabel("Part No: ");
		gbc.ipady = 10;
		gbc.ipadx = 50;
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 100000;
		reportPanel.add(reportLabel, gbc);
		
		productNoTextField = new JTextField("", 3);
		gbc.gridwidth = 4;
		gbc.gridx = 4;
		gbc.gridy = 100000;
		productNoTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
					if(productNoTextField.getText().length() == 2 && notInteger(productNoTextField.getText()) == false){
						productNoTextField.setText(productNoTextField.getText() + "-");
					}
					
					reportPanel.revalidate();  
					reportPanel.repaint();
				}
			}
		});
		reportPanel.add(productNoTextField, gbc);
		
		poTable = new JTable(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                    	return String.class;
                    default:
                        return Boolean.class;
                }
            }
			@Override
			public boolean isCellEditable(int row, int col) {
			    return (col == 2); 
			}
			
		};
		 ((JComponent) poTable.getDefaultRenderer(Boolean.class)).setOpaque(true);
		gbc.gridy = 13;
		gbc.gridwidth = 8;
		gbc.ipadx = 200;

		reportButton = new JButton("Report");
		gbc.ipadx = 0;
		gbc.gridwidth = 2;
		gbc.gridx = 8;
		gbc.gridy = 1000000;
		reportButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!productNoTextField.getText().equals("")){
					String sql = "SELECT completeunitPurchaseOrder.poNumber, completeunitOrderItem.subCode "
							+ "FROM completeunitPurchaseOrder, completeunitOrderItem "
							+ "WHERE completeunitPurchaseOrder.poNumber = completeunitOrderItem.poNumber "
							+ "AND completeunitOrderItem.PRODUCT = \'" + productNoTextField.getText() + "\' "
									+ "AND completeunitOrderItem.SAremain > 0 "
							+ "ORDER BY completeunitPurchaseOrder.poNumber";
					System.out.println(sql);
					IRBS irbs = new IRBS();
					poTable.setModel(irbs.reportSA(sql, 2));
					DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
					centerRenderer.setHorizontalAlignment(JLabel.CENTER);
					poTable.setDefaultRenderer(String.class, centerRenderer);
					poTable.getTableHeader().setDefaultRenderer(centerRenderer);
					createGUIReportSAViewTable = new CreateGUIReportSACompleteUnitViewTable(theFrame, poTable, productNoTextField.getText());
					productNoTextField.setText(null);
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
							"There must be at least one Part No.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		reportPanel.add(reportButton, gbc);
	}
	
	public static boolean notInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return true; 
		} catch(NullPointerException e) {
			return true;
		}
		// only got here if we didn't return false
		return false;
	}

	public JScrollPane reportPane(){
		return reportScrollPane;
	}
}
