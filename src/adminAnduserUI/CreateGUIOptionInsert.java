package adminAnduserUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.mysql.jdbc.StringUtils;

import domain.DateValidator;
import domain.IRBS;

public class CreateGUIOptionInsert {
	//option insert Panel
	private JLabel optionInsertLabel;
	private JTextField optionInsertDescTextField;
	private JButton optionInsertButton;

	private JScrollPane optionInsertScrollPane;
	private JPanel optionInsertPanel;
	private JPanel errorPanel;
	
	public CreateGUIOptionInsert(){
		initUI();
	}

	private void initUI() {
		setUP();
	}

	private void setUP(){
		optionInsertPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		optionInsertScrollPane = new JScrollPane(optionInsertPanel);

		// ************************* optionInsertPanel UI SetUP
		// *************************
		// ========================= optionInsertPanel Level One UI Create
		// =========================
		optionInsertLabel = new JLabel("Please insert the new customer name");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 200;
		gbc.gridwidth = 2;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		optionInsertPanel.add(optionInsertLabel, gbc);

		optionInsertLabel = new JLabel("Customer Name: ");
		gbc.gridwidth = 1;
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 1;
		optionInsertPanel.add(optionInsertLabel, gbc);

		optionInsertDescTextField = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 1;
		optionInsertPanel.add(optionInsertDescTextField, gbc);

		optionInsertLabel = new JLabel();
		gbc.gridx = 0;
		gbc.gridy = 9;
		optionInsertPanel.add(optionInsertLabel, gbc);

		optionInsertButton = new JButton("Save");
		gbc.gridx = 1;
		gbc.gridy = 9;
		optionInsertPanel.add(optionInsertButton, gbc);

		//insert option
		optionInsertButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					IRBS irbs = new IRBS();
					if(!StringUtils.isNullOrEmpty(optionInsertDescTextField.getText())){
						if(irbs.checkOptionDuplicate(optionInsertDescTextField.getText(), "CustomerName")){
							JOptionPane
							.showMessageDialog(
									errorPanel,
									"Error: Duplicate option",
									"Error", JOptionPane.ERROR_MESSAGE);
						}else{
							irbs.insertOption(optionInsertDescTextField.getText(), "CustomerName");
							JOptionPane
							.showMessageDialog(
									null,
									"Success insert the new option: " + optionInsertDescTextField.getText(),
									"Success", JOptionPane.INFORMATION_MESSAGE);
							optionInsertReset();
						}
					}else{
						JOptionPane
						.showMessageDialog(
								errorPanel,
								"Error: option cannot be null.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
					irbs.getCon().close();
				}catch(Exception ex){
					JOptionPane
					.showMessageDialog(
							errorPanel,
							"Error: " + ex,
							"Error", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
	}

	// ========================= reset all the TextField in option Insert Panel
	// =========================
	public void optionInsertReset(){
		optionInsertDescTextField.setText(null);
	}

	public JScrollPane insertPane(){
		return optionInsertScrollPane;
	}
}
