package adminAnduserUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.mysql.jdbc.StringUtils;

import domain.IRBS;

public class CreateGUIOptionView {
	private JLabel optionViewLabel;
	private JTextField optionViewDescTextField;
	private JButton optionViewSearchButton;
	private JButton optionViewResetButton;
	private JButton optionViewDeleteButton;
	private JButton optionViewChangeButton;
	private JTable optionTable;

	private JScrollPane optionViewScrollPane;
	private JPanel optionViewPanel;
	private JFrame theFrame;

	public CreateGUIOptionView(){
		initUI();
	}

	private void initUI() {
		setUP();
		repaintToShowAllOption();
	}

	private void setUP(){
		optionViewPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		optionViewScrollPane = new JScrollPane(optionViewPanel);

		// ************************* optionViewPanel UI SetUP
		// *************************
		// ========================= optionViewPanel Level One UI Create
		// =========================
		optionViewLabel = new JLabel("Search for customer name");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		optionViewPanel.add(optionViewLabel, gbc);

		optionViewLabel = new JLabel("Customer Name: ");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 1;
		optionViewPanel.add(optionViewLabel, gbc);

		optionViewDescTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 1;
		optionViewPanel.add(optionViewDescTextField, gbc);

		optionViewResetButton = new JButton("Show all");
		gbc.gridx = 0;
		gbc.gridy = 9;
		optionViewResetButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					IRBS irbs = new IRBS();
					optionTable.setModel(irbs.viewAllOption());
					optionViewReset();
					irbs.getCon().close();
				}catch(Exception e1){
					
				}
			}
		});
		optionViewPanel.add(optionViewResetButton, gbc);

		optionViewSearchButton = new JButton("Search");
		gbc.gridx = 1;
		gbc.gridy = 9;
		optionViewSearchButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(optionViewDescTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null,
							"Please input some criteria for searching!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else{
					IRBS irbs = new IRBS();
					optionTable.setModel(irbs.viewSearchOption(searchOptionSQL()));
					try {
						irbs.getCon().close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		optionViewPanel.add(optionViewSearchButton, gbc);

		optionViewLabel = new JLabel();
		gbc.gridx = 0;
		gbc.gridy = 10;
		optionViewPanel.add(optionViewLabel, gbc);

		IRBS irbs = new IRBS();
		irbs.viewAllOption().fireTableDataChanged();
		optionTable = new JTable(irbs.viewAllOption()){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			//make the JTable non-editable
			public boolean isCellEditable(int row,int column){
				return false;
			}
		};
		try {
			irbs.getCon().close();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		gbc.gridy = 11;
		gbc.gridwidth = 2;
		gbc.ipady = 10;
		gbc.ipadx = 200;
		optionTable.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me) {
		        JTable table =(JTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table.rowAtPoint(p);
		        if (me.getClickCount() == 2) {
		        	//get the selected option product #
					ListSelectionModel cellSelectionModel = optionTable.getSelectionModel();
					String selectedDesc = null;
					String selectedType = null;
					int[] selectedRow = optionTable.getSelectedRows();
					for (int i = 0; i < selectedRow.length; i++) {
						selectedDesc = (String) optionTable.getValueAt(selectedRow[i], 0);
						selectedType = (String) optionTable.getValueAt(selectedRow[i], 1);
					}
					CreateGUIOptionChange cgui = new CreateGUIOptionChange(theFrame, optionTable, selectedDesc, selectedType, searchOptionSQL());
					System.out.println("Selected: " + selectedDesc + ", " + selectedType);
					IRBS irbs = new IRBS ();
					optionTable.setModel(irbs.viewSearchOption(searchOptionSQL()));	
					try {
						irbs.getCon().close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		    }
		});
		optionViewPanel.add(new JScrollPane(optionTable), gbc);

		optionViewDeleteButton = new JButton("Delete");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 12;

		optionViewDeleteButton.addActionListener(new ActionListener(){
			//get the selected option product #
			ListSelectionModel cellSelectionModel = optionTable.getSelectionModel();
			String selectedDesc = null;
			String selectedType = null;
			public void actionPerformed(ActionEvent e) {
				if(!cellSelectionModel.isSelectionEmpty()){
					int[] selectedRow = optionTable.getSelectedRows();

					for (int i = 0; i < selectedRow.length; i++) {
						selectedDesc = (String) optionTable.getValueAt(selectedRow[i], 0).toString();
						selectedType = (String) optionTable.getValueAt(selectedRow[i], 1);
					}
					IRBS irbs = new IRBS();
					irbs.deleteOption(selectedDesc, selectedType);
					System.out.println("Selected: " + selectedDesc + ", " + selectedType);
					optionTable.setModel(irbs.viewSearchOption(searchOptionSQL()));		
					JOptionPane.showMessageDialog(null,
							"You delete the option " + selectedDesc + " successfully.", "Message",
							JOptionPane.INFORMATION_MESSAGE);
					try {
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
			}
		});
		optionViewPanel.add(optionViewDeleteButton, gbc);

		optionViewChangeButton = new JButton("Change / View");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 1;
		gbc.gridy = 12;

		optionViewChangeButton.addActionListener(new ActionListener(){
			//get the selected option product #
			ListSelectionModel cellSelectionModel = optionTable.getSelectionModel();
			String selectedDesc = null;
			String selectedType = null;
			public void actionPerformed(ActionEvent e) {
				if(!cellSelectionModel.isSelectionEmpty()){
					int[] selectedRow = optionTable.getSelectedRows();

					for (int i = 0; i < selectedRow.length; i++) {
						selectedDesc = (String) optionTable.getValueAt(selectedRow[i], 0);
						selectedType = (String) optionTable.getValueAt(selectedRow[i], 1);
					}
					CreateGUIOptionChange cgui = new CreateGUIOptionChange(theFrame, optionTable, selectedDesc, selectedType, searchOptionSQL());
					System.out.println("Selected: " + selectedDesc + ", " + selectedType);
//					repaintToShowAlloption();
					IRBS irbs = new IRBS();
					optionTable.setModel(irbs.viewSearchOption(searchOptionSQL()));		
					try {
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
			}
		});
		optionViewPanel.add(optionViewChangeButton, gbc);
	}

	// ========================= reset option table in option Insert Panel
	// ========================= 
	public void repaintToShowAllOption(){
		IRBS irbs = new IRBS();
		optionTable.setModel(irbs.viewAllOption());
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ========================= search for option
	// ========================= (criteria: option)
	private String searchOptionSQL(){
		String sql = ("SELECT description, type FROM `option`");
		sql = sql + " WHERE";
		String addsql;
		if(StringUtils.isEmptyOrWhitespaceOnly(optionViewDescTextField.getText())){
			addsql = " description <> ''";
			sql = sql + addsql;
		}else{
			addsql = " description = \'" + optionViewDescTextField.getText() + "\'";
			sql = sql + addsql;
		}
		System.out.println(sql);
		return sql;
	}

	// ========================= reset all the TextField in option View Panel
	// ========================= 
	private void optionViewReset(){
		optionViewDescTextField.setText(null);
	}
	
	public JScrollPane viewPane(){
		return optionViewScrollPane;
	}
}
