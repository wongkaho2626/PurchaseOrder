package adminAnduserUI;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import com.mysql.jdbc.StringUtils;

import domain.DateValidator;
import domain.IRBS;

public class CreateGUIOptionChange {
	private JFrame theFrame;
	private JPanel optionPanel;
    private JPanel errorPanel;
	private JLabel optionLabel;
	private JTextField optionDescriptionTextField;
	private JButton optionButton;
	private String selectedDesc;
	private String selectedType;
	private JTable optionTable;
	private JDialog d;
	private String searchSQL;

	public CreateGUIOptionChange(JFrame theFrame, JTable optionTable, String selectedDesc, String selectedType, String searchSQL){
		this.selectedDesc = selectedDesc;
		this.selectedType = selectedType;
		this.theFrame = theFrame;
		this.optionTable = optionTable;
		this.searchSQL = searchSQL;
		initUI();
	}

	private void initUI() {
		createDialog();
		setUP();
		useoptionPanel();
	}


	private void createDialog() {
		d = new JDialog(theFrame, "Change the customer name", true);
		d.setSize(800,400);
		d.setLocationRelativeTo(null);
		d.setResizable(false);
	}

	private void setUP() {
		optionPanel = new JPanel(new GridBagLayout());	
		GridBagConstraints gbc = new GridBagConstraints();

		// ************************* optionInsertPanel UI SetUP
		// *************************
		// ========================= optionInsertPanel Level One UI Create
		// =========================
		optionLabel = new JLabel("Please change the selected customer name");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		optionPanel.add(optionLabel, gbc);

		optionLabel = new JLabel("Customer Name: ");
		gbc.ipady = 10;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.2;
		optionPanel.add(optionLabel, gbc);

		optionDescriptionTextField = new JTextField(selectedDesc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		optionPanel.add(optionDescriptionTextField, gbc);

		optionButton = new JButton("Save");
		IRBS irbs = new IRBS();
		optionButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					IRBS irbs = new IRBS();
					if(!StringUtils.isNullOrEmpty(optionDescriptionTextField.getText())){
						if(irbs.checkOptionDuplicate(optionDescriptionTextField.getText(), "CustomerName")){
							JOptionPane
							.showMessageDialog(
									errorPanel,
									"Error: Duplicate option",
									"Error", JOptionPane.ERROR_MESSAGE);
						}else{
							irbs.changeOption(optionDescriptionTextField.getText(), "CustomerName", selectedDesc, selectedType);
							JOptionPane
							.showMessageDialog(
									null,
									"Success update the option: " + optionDescriptionTextField.getText(),
									"Success", JOptionPane.INFORMATION_MESSAGE);
							d.dispose();
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
		gbc.gridx = 1;
		gbc.gridy = 10;
		optionPanel.add(optionButton, gbc);
	}

	private void useoptionPanel() {
		d.setContentPane(optionPanel);
		d.setVisible(true);		
	}

	public static boolean notDouble(String s) {
		try { 
			Double.parseDouble(s); 
		} catch(NumberFormatException e) { 
			return true; 
		} catch(NullPointerException e) {
			return true;
		}
		// only got here if we didn't return false
		return false;
	}

	private void repaintToShowAll(){
		IRBS irbs = new IRBS();
//		optionTable.setModel(irbs.viewAlloption());
		optionTable.setModel(irbs.viewSearchOption(searchSQL));	
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
