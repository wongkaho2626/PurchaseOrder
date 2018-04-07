package viewerUI;

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

public class CreateGUIViewerOptionView {
	private JLabel optionViewLabel;
	private JTextField optionViewDescTextField;
	private JButton optionViewSearchButton;
	private JButton optionViewResetButton;
	private JTable optionTable;

	private JScrollPane optionViewScrollPane;
	private JPanel optionViewPanel;
	private JFrame theFrame;

	public CreateGUIViewerOptionView(){
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
				IRBS irbs = new IRBS();
				optionTable.setModel(irbs.viewAllOption());
				optionViewReset();
				try {
					irbs.getCon().close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		gbc.gridy = 11;
		gbc.gridwidth = 2;
		gbc.ipady = 10;
		gbc.ipadx = 200;
		optionViewPanel.add(new JScrollPane(optionTable), gbc);
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
