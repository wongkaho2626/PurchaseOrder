package domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import com.mysql.fabric.xmlrpc.base.Data;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.StringUtils;

import domain.DBConnect;

public class IRBS {

	private JPanel errorPanel;
	private DBConnect connect;
	private String sql;
	private String addsql;

	public IRBS(){
		connect = new DBConnect();
	}
	
	public Connection getCon(){
		return connect.con;
	}
	
	//check the admin login 
	public boolean loginAdmin(String staffID, String password) throws SQLException{
		boolean found = false;
		String sql = ("select * from admin;");
		connect.rs = connect.st.executeQuery(sql);
		while(connect.rs.next()){
			String un = connect.rs.getString("username");
			String pw = connect.rs.getString("password");

			if (staffID.equals(un) && password.equals(pw)) {
				found = true;
			}
		}
		return found;
	}

	//check the user login 
	public boolean loginUser(String staffID, String password) throws SQLException{
		boolean found = false;
		String sql = ("select * from user;");
		connect.rs = connect.st.executeQuery(sql);
		while(connect.rs.next()){
			String un = connect.rs.getString("username");
			String pw = connect.rs.getString("password");

			if (staffID.equals(un) && password.equals(pw)) {
				found = true;
			}
		}
		return found;
	}

	//check the viewer login 
	public boolean loginViewer(String staffID, String password) throws SQLException{
		boolean found = false;
		String sql = ("select * from viewer;");
		connect.rs = connect.st.executeQuery(sql);

		while(connect.rs.next()){
			String un = connect.rs.getString("username");
			String pw = connect.rs.getString("password");

			if (staffID.equals(un) && password.equals(pw)) {
				found = true;
			}
		}
		return found;
	}

	//change the admin password to the database
	public void setAdminPasswordData(String userID, String password){
		try{
			String sql = ("UPDATE admin" + " SET password = ? " + "WHERE username = ?");
			java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
			updateQuery.setString(1, password);
			updateQuery.setString(2, userID);
			updateQuery.executeUpdate();
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	//change the user password to the database
	public void setUserPasswordData(String userID, String password){
		try{
			String sql = ("UPDATE user" + " SET password = ? " + "WHERE username = ?");
			java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
			updateQuery.setString(1, password);
			updateQuery.setString(2, userID);
			updateQuery.executeUpdate();
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	//change the viewer password to the database
	public void setViewerPasswordData(String userID, String password){
		try{
			String sql = ("UPDATE viewer" + " SET password = ? " + "WHERE username = ?");
			java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
			updateQuery.setString(1, password);
			updateQuery.setString(2, userID);
			updateQuery.executeUpdate();
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	//return all the account jtable
	public DefaultTableModel viewAllAccount(){
		try{
			String sql = ("select username, type from user;");
			connect.rs = connect.st.executeQuery(sql);
			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}

			// data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (connect.rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					vector.add(connect.rs.getObject(columnIndex));
				}
				data.add(vector);
			}
			String sql1 = ("select username, type from viewer;");
			connect.rs = connect.st.executeQuery(sql1);
			metaData = (ResultSetMetaData) connect.rs.getMetaData();
			columnNames = new Vector<String>();
			columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}
			while (connect.rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					vector.add(connect.rs.getObject(columnIndex));
				}
				data.add(vector);
			}
			return new DefaultTableModel(data, columnNames);
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	//return selected in username, type 
	//of the account jtable in user and viewer
	public DefaultTableModel viewSearchAccount(String sql1, String sql2){
		try{
			connect.rs = connect.st.executeQuery(sql1);
			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}

			// data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (connect.rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					vector.add(connect.rs.getObject(columnIndex));
				}
				data.add(vector);
			}

			if(sql2 != null){
				connect.rs = connect.st.executeQuery(sql2);
				metaData = (ResultSetMetaData) connect.rs.getMetaData();
				columnNames = new Vector<String>();
				columnCount = metaData.getColumnCount();
				for (int column = 1; column <= columnCount; column++) {
					columnNames.add(metaData.getColumnName(column));
				}

				// data of the table
				while (connect.rs.next()) {
					Vector<Object> vector = new Vector<Object>();
					for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
						vector.add(connect.rs.getObject(columnIndex));
					}
					data.add(vector);
				}
			}
			return new DefaultTableModel(data, columnNames);
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}	
	}
	
	//delete the account from the database
	public void deleteAccount(String selectedusername, String selectedtype){
		try{
			if(selectedtype.equals("user")){
				String sql = ("DELETE FROM user WHERE username = ?");
				java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
				updateQuery.setString(1, selectedusername);
				updateQuery.executeUpdate();
			}else if(selectedtype.equals("viewer")){
				String sql1 = ("DELETE FROM viewer WHERE username = ?");
				java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql1);
				updateQuery.setString(1, selectedusername);
				updateQuery.executeUpdate();
			}
			System.out.println("Delete: " + selectedusername + ", " + selectedtype);
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	//insert new account into user
	public void createUserAccount(String username, String password){
		try{
			String sql = ("INSERT INTO user (type, username, password) VALUES (?, ?, ?)");
			java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
			updateQuery.setString(1, "user");
			updateQuery.setString(2, username);
			updateQuery.setString(3, password);
			updateQuery.executeUpdate();
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	//insert new account into user
	public void createViewerAccount(String username, String password){
		try{
			String sql = ("INSERT INTO viewer (type, username, password) VALUES (?, ?, ?)");
			java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
			updateQuery.setString(1, "viewer");
			updateQuery.setString(2, username);
			updateQuery.setString(3, password);
			updateQuery.executeUpdate();
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	//return all the purchase order jtable
	public DefaultTableModel viewAllPO(){
		try{
			String sql = ("SELECT completeunitPurchaseOrder.poNumber AS PO_Number, completeunitPurchaseOrder.vendor AS Vendor, DATE_FORMAT(completeunitPurchaseOrder.orderDate, '%m/%d/%y') AS OrderDate FROM completeunitPurchaseOrder, completeunitOrderDate ORDER BY `completeunitPurchaseOrder`.`poNumber` ASC");
			connect.rs = connect.st.executeQuery(sql);
			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}

			// data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (connect.rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					vector.add(connect.rs.getObject(columnIndex));
				}
				data.add(vector);
			}
			String sql1 = ("SELECT sparepartPurchaseOrder.poNumber AS PO_Number, sparepartPurchaseOrder.vendor AS Vendor, DATE_FORMAT(sparepartPurchaseOrder.orderDate, '%m/%d/%y') AS OrderDate FROM sparepartPurchaseOrder ORDER BY `sparepartPurchaseOrder`.`poNumber` ASC");
			connect.rs = connect.st.executeQuery(sql1);
			metaData = (ResultSetMetaData) connect.rs.getMetaData();
			columnNames = new Vector<String>();
			columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}
			while (connect.rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					vector.add(connect.rs.getObject(columnIndex));
				}
				data.add(vector);
			}
			return new DefaultTableModel(data, columnNames);
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	//return complete unit purchase order jtable
//	public DefaultTableModel viewAllPOCompleteUnit(){
//		try{
////			String sql = ("SELECT completeunitPurchaseOrder.poNumber AS PO_Number, completeunitPurchaseOrder.vendor AS Vendor, DATE_FORMAT(completeunitPurchaseOrder.orderDate, '%m/%d/%y') AS OrderDate FROM completeunitPurchaseOrder ORDER BY `completeunitPurchaseOrder`.`poNumber` ASC");
//			connect.rs = connect.st.executeQuery(sql);
//			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
//			Vector<String> columnNames = new Vector<String>();
//			int columnCount = metaData.getColumnCount();
//			for (int column = 1; column <= columnCount; column++) {
//				columnNames.add(metaData.getColumnName(column));
//			}
//
//			// data of the table
//			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
//			while (connect.rs.next()) {
//				Vector<Object> vector = new Vector<Object>();
//				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
//					vector.add(connect.rs.getObject(columnIndex));
//				}
//				data.add(vector);
//			}
////			return new DefaultTableModel(data, columnNames);
//		}catch(Exception ex){
//			JOptionPane
//			.showMessageDialog(
//					errorPanel,
//					"Error: "+ex,
//					"Error", JOptionPane.ERROR_MESSAGE);
//			return null;
//		}
//	}

	//return spare part purchase order jtable
//	public DefaultTableModel viewAllPOSparePart(){
//		try{
//			String sql = ("SELECT sparepartPurchaseOrder.poNumber AS PO_Number, sparepartPurchaseOrder.vendor AS Vendor, DATE_FORMAT(sparepartPurchaseOrder.orderDate, '%m/%d/%y') AS OrderDate FROM sparepartPurchaseOrder ORDER BY `sparepartPurchaseOrder`.`poNumber` ASC");
//			connect.rs = connect.st.executeQuery(sql);
//			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
//			Vector<String> columnNames = new Vector<String>();
//			int columnCount = metaData.getColumnCount();
//			for (int column = 1; column <= columnCount; column++) {
//				columnNames.add(metaData.getColumnName(column));
//			}
//
//			// data of the table
//			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
//			while (connect.rs.next()) {
//				Vector<Object> vector = new Vector<Object>();
//				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
//					vector.add(connect.rs.getObject(columnIndex));
//				}
//				data.add(vector);
//			}
//			return new DefaultTableModel(data, columnNames);
//		}catch(Exception ex){
//			JOptionPane
//			.showMessageDialog(
//					errorPanel,
//					"Error: "+ex,
//					"Error", JOptionPane.ERROR_MESSAGE);
//			return null;
//		}
//	}

	//return selected in poNumber / vendor / referenceNo / FOB /paymentTerm 
	//of the purchase order jtable in complete unit, spare part, tooling and misc
	public DefaultTableModel viewSearchPO(String sql1){
		Map<String, String> map = new HashMap<String, String>();
		map.put("description", "CUSTOMER NAME");
		map.put("poNumber", "P.O.");
		map.put("vendor", "VENDOR");
		map.put("orderDate", "ORDER DATE");
		map.put("PRODUCT", "PART NO");
		map.put("ACRONYM", "ACRONYM");
		try{
			connect.rs = connect.st.executeQuery(sql1);
			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(map.get(metaData.getColumnName(column)));
			}

			// data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (connect.rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					vector.add(connect.rs.getObject(columnIndex));
				}
				data.add(vector);
			}
			
			return new DefaultTableModel(data, columnNames);
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}	
	}

	//insert the maintenance to the database
	public void insertMaintenance(String product, String ACRONYM, String description, String dutycode, String fixedcost, String purchaseCode, String vendor, String dateOfConfirmation){
		try{
			String sql = ("INSERT INTO maintenance (PRODUCT, ACRONYM, DESCRIPTION, DUTY_CODE, fixedcost, purchaseCode, vendor, dateOfConfirmation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
			updateQuery.setString(1, product);
			updateQuery.setString(2, ACRONYM);
			updateQuery.setString(3, description);
			updateQuery.setString(4, dutycode);
			updateQuery.setString(5, fixedcost);
			updateQuery.setString(6, purchaseCode);
			updateQuery.setString(7, vendor);
			updateQuery.setString(8, dateOfConfirmation);
			System.out.println("Insert maintenance: " + product + " " + ACRONYM + " " + description + " " + dutycode + " " + fixedcost + " " + purchaseCode + " " + vendor + " " + dateOfConfirmation);
			updateQuery.executeUpdate();
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	//return search criteria of the maintenance jtable
	public DefaultTableModel viewSearchMaintenance(String sql){
		Map<String, String> map = new HashMap<String, String>();
		map.put("PRODUCT", "PART NO");
		map.put("ACRONYM", "ACRONYM");
		map.put("purchaseCode", "PURCHASE CODE");
		map.put("vendor", "VENDOR");
		map.put("DESCRIPTION", "DESCRIPTION");
		map.put("DUTY_CODE", "DUTY CODE");
		map.put("fixedcost", "FIXED COST");
		map.put("dateOfConfirmation", "DATE OF CONFIRMATION");
		try{
			connect.rs = connect.st.executeQuery(sql);
			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				System.out.println(metaData.getColumnName(column) + " " + map.get(metaData.getColumnName(column)));
				columnNames.add(map.get(metaData.getColumnName(column)));
			}

			// data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (connect.rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					vector.add(connect.rs.getObject(columnIndex));
				}
				data.add(vector);
			}
			return new DefaultTableModel(data, columnNames);
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}	
	}

	//return all the maintenance jtable
	public DefaultTableModel viewAllMaintenance(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("PRODUCT", "PART NO");
		map.put("ACRONYM", "ACRONYM");
		map.put("purchaseCode", "PURCHASE CODE");
		map.put("vendor", "VENDOR");
		map.put("DESCRIPTION", "DESCRIPTION");
		map.put("DUTY_CODE", "DUTY CODE");
		map.put("fixedcost", "FIXED COST");
		map.put("dateOfConfirmation", "DATE OF CONFIRMATION");
		try{
			String sql = ("SELECT PRODUCT, ACRONYM, purchaseCode, vendor, DESCRIPTION, DUTY_CODE, fixedcost, DATE_FORMAT(dateOfConfirmation, '%m/%d/%y') AS dateOfConfirmation FROM maintenance;");
			connect.rs = connect.st.executeQuery(sql);
			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(map.get(metaData.getColumnName(column)));
			}

			// data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (connect.rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					vector.add(connect.rs.getObject(columnIndex));
				}
				data.add(vector);
			}  
			return new DefaultTableModel(data, columnNames);
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
		
	//delete the purchase order from the database
	public void deletePO(int poNumber) throws SQLException  {
		String sql;
		PreparedStatement 
		updateQuery1 = null, updateQuery2 = null, updateQuery3 = null,
		updateQuery4 = null, updateQuery5 = null, updateQuery6 = null, 
		updateQuery7 = null, updateQuery8 = null, updateQuery9 = null,
		updateQuery10 = null, updateQuery11 = null, updateQuery12 = null,
		updateQuery13 = null, updateQuery14 = null;
		
		//delete completeunitPurchaseOrder table
		sql = ("DELETE FROM completeunitPurchaseOrder WHERE completeunitPurchaseOrder.poNumber = ?");
		updateQuery1 = connect.con.prepareStatement(sql);
		updateQuery1.setInt(1, poNumber);
		updateQuery1.executeUpdate();
		System.out.println(updateQuery1);
		
		//delete completeunitOrderDate table
		sql = ("DELETE FROM completeunitOrderDate WHERE completeunitOrderDate.poNumber = ?");
		updateQuery2 = connect.con.prepareStatement(sql);
		updateQuery2.setInt(1, poNumber);
		updateQuery2.executeUpdate();
		System.out.println(updateQuery2);

		//delete completeunitOrderItem table
		sql = ("DELETE FROM completeunitOrderItem WHERE completeunitOrderItem.poNumber = ?");
		updateQuery3 = connect.con.prepareStatement(sql);
		updateQuery3.setInt(1, poNumber);
		updateQuery3.executeUpdate();
		System.out.println(updateQuery3);

		//delete completeunitShipment table
		sql = ("DELETE FROM completeunitShipment WHERE completeunitShipment.poNumber = ?");
		updateQuery4 = connect.con.prepareStatement(sql);
		updateQuery4.setInt(1, poNumber);
		updateQuery4.executeUpdate();
		System.out.println(updateQuery4);

		//delete sparepartPurchaseOrder table
		sql = ("DELETE FROM sparepartPurchaseOrder WHERE sparepartPurchaseOrder.poNumber = ?");
		updateQuery5 = connect.con.prepareStatement(sql);
		updateQuery5.setInt(1, poNumber);
		updateQuery5.executeUpdate();
		System.out.println(updateQuery5);

		//delete sparepartOrderDate table
		sql = ("DELETE FROM sparepartOrderDate WHERE sparepartOrderDate.poNumber = ?");
		updateQuery6 = connect.con.prepareStatement(sql);
		updateQuery6.setInt(1, poNumber);
		updateQuery6.executeUpdate();
		System.out.println(updateQuery6);

		//delete sparepartOrderItem table
		sql = ("DELETE FROM sparepartOrderItem WHERE sparepartOrderItem.poNumber = ?");
		updateQuery7 = connect.con.prepareStatement(sql);
		updateQuery7.setInt(1, poNumber);
		updateQuery7.executeUpdate();
		System.out.println(updateQuery7);

		//delete SA table
		sql = ("DELETE FROM SA WHERE SA.poNumber = ?");
		updateQuery8 = connect.con.prepareStatement(sql);
		updateQuery8.setInt(1, poNumber);
		updateQuery8.executeUpdate();
		System.out.println(updateQuery8);

		//delete toolingPurchaseOrder table
		sql = ("DELETE FROM toolingPurchaseOrder WHERE toolingPurchaseOrder.poNumber = ?");
		updateQuery9 = connect.con.prepareStatement(sql);
		updateQuery9.setInt(1, poNumber);
		updateQuery9.executeUpdate();
		System.out.println(updateQuery9);

		//delete toolingOrderDate table
		sql = ("DELETE FROM toolingOrderDate WHERE toolingOrderDate.poNumber = ?");
		updateQuery10 = connect.con.prepareStatement(sql);
		updateQuery10.setInt(1, poNumber);
		updateQuery10.executeUpdate();
		System.out.println(updateQuery10);

		//delete toolingOrderItem table
		sql = ("DELETE FROM toolingOrderItem WHERE toolingOrderItem.poNumber = ?");
		updateQuery11 = connect.con.prepareStatement(sql);
		updateQuery11.setInt(1, poNumber);
		updateQuery11.executeUpdate();
		System.out.println(updateQuery11);

		//delete miscPurchaseOrder table
		sql = ("DELETE FROM miscPurchaseOrder WHERE miscPurchaseOrder.poNumber = ?");
		updateQuery12 = connect.con.prepareStatement(sql);
		updateQuery12.setInt(1, poNumber);
		updateQuery12.executeUpdate();
		System.out.println(updateQuery12);

		//delete miscOrderDate table
		sql = ("DELETE FROM miscOrderDate WHERE miscOrderDate.poNumber = ?");
		updateQuery13 = connect.con.prepareStatement(sql);
		updateQuery13.setInt(1, poNumber);
		updateQuery13.executeUpdate();
		System.out.println(updateQuery13);

		//delete miscOrderItem table
		sql = ("DELETE FROM miscOrderItem WHERE miscOrderItem.poNumber = ?");
		updateQuery14 = connect.con.prepareStatement(sql);
		updateQuery14.setInt(1, poNumber);
		updateQuery14.executeUpdate();
		System.out.println(updateQuery14);
	}

	//delete the maintenance from the database
	public void deleteMaintenance(String selectedPRODUCT){
		try{
			String sql = ("DELETE FROM maintenance WHERE PRODUCT = ?");
			java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
			updateQuery.setString(1, selectedPRODUCT);
			updateQuery.executeUpdate();
			System.out.println("Delete: " + selectedPRODUCT);
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	//update the maintenance to the database 
	public void changeMaintenance(String selectedPRODUCT, String selectedACRONYM, String selectedDESCRIPTION, String selectedDUTY_CODE, String selectedFixedcost, String purchaseCode, String vendor, String selectedOriginalPRODUCT, String dateOfConfirmation) throws SQLException{
		String sql = ("UPDATE maintenance" + " SET PRODUCT = ?, ACRONYM = ?, DESCRIPTION = ?, DUTY_CODE = ?, fixedcost = ?, purchaseCode = ?, vendor = ?, dateOfConfirmation = ? " + "WHERE PRODUCT = ?");
		java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
		updateQuery.setString(1, selectedPRODUCT);
		updateQuery.setString(2, selectedACRONYM);
		updateQuery.setString(3, selectedDESCRIPTION);
		updateQuery.setString(4, selectedDUTY_CODE);
		updateQuery.setString(5, selectedFixedcost);
		updateQuery.setString(6, purchaseCode);
		updateQuery.setString(7, vendor);
		updateQuery.setString(8, dateOfConfirmation);
		updateQuery.setString(9, selectedOriginalPRODUCT);
		updateQuery.executeUpdate();
		System.out.println(updateQuery);
	}

	//select the maintenance
	public String[] maintenanceStatement(String selectedPRODUCT){
		String[] maintenanceStatement = new String[6];
		try{
			String sql = ("SELECT * FROM maintenance WHERE PRODUCT = \"" + selectedPRODUCT + "\"");
			System.out.println(sql);
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				maintenanceStatement[0] = connect.rs.getString("ACRONYM");
				maintenanceStatement[1] = connect.rs.getString("DESCRIPTION");
				maintenanceStatement[2] = connect.rs.getString("DUTY_CODE");
				maintenanceStatement[3] = connect.rs.getString("fixedcost");
				maintenanceStatement[4] = connect.rs.getString("purchaseCode");
				maintenanceStatement[5] = connect.rs.getString("vendor");

			}
			//System.out.println("ACRONYM: " + maintenanceStatement[0] + "    DESCRIPTION: " + maintenanceStatement[1] + "    DUTY CODE: " + maintenanceStatement[2]);
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return maintenanceStatement;	
	}

	//insert the Purchase Order of the Complete Unit Purchase Order
	public void insertCompleteUnitPO(int poNumber, String changedDate, String vendor, String ETDLocation, String remark, String deposit, String customerName) throws SQLException{
		String sql = ("INSERT INTO completeunitPurchaseOrder (poNumber, orderDate, vendor, ETDLocation, remark, deposit, customerName) VALUES (?, ?, ?, ?, ?, ?, ?)");
		java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
		updateQuery.setInt(1, poNumber);
		updateQuery.setString(2, changedDate);
		updateQuery.setString(3, vendor);
		updateQuery.setString(4, ETDLocation);
		updateQuery.setString(5, remark);
		updateQuery.setString(6, deposit);
		updateQuery.setString(7, customerName);
		updateQuery.executeUpdate();
		System.out.println(updateQuery);
	}
	
	//insert the Order Date of the Complete Unit Purchase Order
	public void insertCompleteUnitOrderDate(int poNumber, String changedDate) throws SQLException{
		String sql = ("INSERT INTO completeunitOrderDate (poNumber, orderDate) VALUES (?, ?)");
		java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
		updateQuery.setInt(1, poNumber);
		updateQuery.setString(2, changedDate);
		updateQuery.executeUpdate();
		System.out.println(updateQuery);
	}

	//insert the Order Item of the Complete Unit Purchase Order
	public void insertCompleteUnitOrderItem(int poNumber, String PRODUCT, String subCode, int quantity, double price, String fixedcost, String purchaseCode, String deposit, int SAremain) throws SQLException{
		String sql = ("INSERT INTO completeunitOrderItem (poNumber, PRODUCT, subCode, quantity, price, fixedcost, purchaseCode, deposit, SAremain) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
		updateQuery.setInt(1, poNumber);
		updateQuery.setString(2, PRODUCT);
		updateQuery.setString(3, subCode);
		updateQuery.setInt(4, quantity);
		updateQuery.setDouble(5, price);
		updateQuery.setString(6, fixedcost);
		updateQuery.setString(7, purchaseCode);
		updateQuery.setString(8, deposit);
		updateQuery.setInt(9, SAremain);
		updateQuery.executeUpdate();
		System.out.println(updateQuery);
	}
	
	//insert the Shipment of the Complete Unit Purchase Order
	public void insertCompleteUnitShipment(int poNumber, String PRODUCT, int quantity, double price, String ETD) throws SQLException{
		String sql = ("INSERT INTO completeunitShipment (poNumber, PRODUCT, quantity, price, ETD) VALUES (?, ?, ?, ?, ?)");
		java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
		updateQuery.setInt(1, poNumber);
		updateQuery.setString(2, PRODUCT);
		updateQuery.setInt(3, quantity);
		updateQuery.setDouble(4, price);
		updateQuery.setString(5, ETD);
		updateQuery.executeUpdate();
		System.out.println(updateQuery);
		
	}

	//insert the Purchase Order of the spare part Purchase Order
	public void insertSparePartPO(int poNumber, String orderDate, String vendor, String remark, String customerName){
		try{
			String sql = ("INSERT INTO sparepartPurchaseOrder (poNumber, orderDate, vendor, remark, customerName) VALUES (?, ?, ?, ?, ?)");
			java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
			updateQuery.setInt(1, poNumber);
			updateQuery.setString(2, orderDate);
			updateQuery.setString(3, vendor);
			updateQuery.setString(4, remark);
			updateQuery.setString(5, customerName);
			System.out.println("Insert sparepartPurchaseOrder: " + poNumber + " " + orderDate + " " + vendor + " " + remark + " " + customerName);
			updateQuery.executeUpdate();
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
	}
	
	//insert the Order Date of the spare part Purchase Order
	public void insertSparePartOrderDate(int poNumber, String changedDate) throws SQLException{
		String sql = ("INSERT INTO sparepartOrderDate (poNumber, orderDate) VALUES (?, ?)");
		java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
		updateQuery.setInt(1, poNumber);
		updateQuery.setString(2, changedDate);
		updateQuery.executeUpdate();
		System.out.println(updateQuery);	
	}

	//insert the Order Item of the spare part Purchase Order
	public void insertSparePartOrderItem(int poNumber, String PRODUCT, String subCode, int quantity, double price, String vendor, double fixedcost, int SAremain, String SRD, String ETD) throws SQLException{
		String sql = ("INSERT INTO sparepartOrderItem (poNumber, PRODUCT, subCode, quantity, price, vendor, fixedcost, SAremain, SRD, ETD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
		updateQuery.setInt(1, poNumber);
		updateQuery.setString(2, PRODUCT);
		updateQuery.setString(3, subCode);
		updateQuery.setInt(4, quantity);
		updateQuery.setDouble(5, price);
		updateQuery.setString(6, vendor);
		updateQuery.setDouble(7, fixedcost);
		updateQuery.setInt(8, SAremain);
		updateQuery.setString(9, SRD);
		updateQuery.setString(10, ETD);
		updateQuery.executeUpdate();
		System.out.println(updateQuery);
	}

	//insert SA
	public void insertSA(int poNumber, String saNo, String PRODUCT, String invoiceNo, int saUnit, String ETD) throws SQLException{
		String sql = ("INSERT INTO SA (poNumber, saNo, PRODUCT, invoiceNo, saUnit, ETD) VALUES (?, ?, ?, ?, ?, ?)");
		java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
		updateQuery.setInt(1, poNumber);
		updateQuery.setString(2, saNo);
		updateQuery.setString(3, PRODUCT);
		updateQuery.setString(4, invoiceNo);
		updateQuery.setInt(5, saUnit);
		updateQuery.setString(6, ETD);
		updateQuery.executeUpdate();
		System.out.println(updateQuery);
	}
	
	//insert the Purchase Order of the Tooling Purchase Order
	public void insertToolingPO(int poNumber, String orderDate, String vendor, String remark, String completion, String customerName, String deposit) throws SQLException{
		String sql = ("INSERT INTO toolingPurchaseOrder (poNumber, orderDate, vendor, remark, completion, customerName, deposit) VALUES (?, ?, ?, ?, ?, ?, ?)");
		java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
		updateQuery.setInt(1, poNumber);
		updateQuery.setString(2, orderDate);
		updateQuery.setString(3, vendor);
		updateQuery.setString(4, remark);
		updateQuery.setString(5, completion);
		updateQuery.setString(6, customerName);
		updateQuery.setString(7, deposit);
		updateQuery.executeUpdate();
		System.out.println(updateQuery);
	}
	
	//insert the Order Date of the Tooling Purchase Order
	public void insertToolingOrderDate(int poNumber, String changedDate) throws SQLException{
		String sql = ("INSERT INTO toolingOrderDate (poNumber, orderDate) VALUES (?, ?)");
		java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
		updateQuery.setInt(1, poNumber);
		updateQuery.setString(2, changedDate);
		updateQuery.executeUpdate();
		System.out.println(updateQuery);		
	}
	
	//insert the Order Item of the Tooling Purchase Order
	public void insertToolingOrderItem(int poNumber, String PRODUCT, int quantity, double price, String description) throws SQLException{
		String sql = ("INSERT INTO toolingOrderItem (poNumber, PRODUCT, quantity, price, description) VALUES (?, ?, ?, ?, ?)");
		java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
		updateQuery.setInt(1, poNumber);
		updateQuery.setString(2, PRODUCT);
		updateQuery.setInt(3, quantity);
		updateQuery.setDouble(4, price);
		updateQuery.setString(5, description);
		System.out.println(updateQuery);
		updateQuery.executeUpdate();
	}
	
	//insert the Purchase Order of the Misc Purchase Order
	public void insertMiscPO(int poNumber, String orderDate, String vendor, String remark, String customerName) throws SQLException{
		String sql = ("INSERT INTO miscPurchaseOrder (poNumber, orderDate, vendor, remark, customerName) VALUES (?, ?, ?, ?, ?)");
		java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
		updateQuery.setInt(1, poNumber);
		updateQuery.setString(2, orderDate);
		updateQuery.setString(3, vendor);
		updateQuery.setString(4, remark);
		updateQuery.setString(5, customerName);
		updateQuery.executeUpdate();
		System.out.println(updateQuery);
	}
	
	//insert the Order Date of the Misc Purchase Order
	public void insertMiscOrderDate(int poNumber, String changedDate) throws SQLException{
		String sql = ("INSERT INTO miscOrderDate (poNumber, orderDate) VALUES (?, ?)");
		java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
		updateQuery.setInt(1, poNumber);
		updateQuery.setString(2, changedDate);
		updateQuery.executeUpdate();
		System.out.println(updateQuery);		
	}
	
	//insert the Order Item of the Misc Purchase Order
	public void insertMiscOrderItem(int poNumber, String PRODUCT, int quantity, double price, String description) throws SQLException{
		String sql = ("INSERT INTO miscOrderItem (poNumber, PRODUCT, quantity, price, description) VALUES (?, ?, ?, ?, ?)");
		java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
		updateQuery.setInt(1, poNumber);
		updateQuery.setString(2, PRODUCT);
		updateQuery.setInt(3, quantity);
		updateQuery.setDouble(4, price);
		updateQuery.setString(5, description);
		updateQuery.executeUpdate();
		System.out.println(updateQuery);
	}

	//check the duplicate complete unit purchase order
	public boolean checkCompleteUnitDuplicatePO(int poNumber) throws SQLException{
		boolean check = false;
		String sql = ("SELECT * FROM completeunitPurchaseOrder");
		connect.rs = connect.st.executeQuery(sql);
		while(connect.rs.next()){
			int PurchaseOrderNumber = Integer.parseInt(connect.rs.getString("poNumber"));

			if (PurchaseOrderNumber == poNumber) {
				check = true;
			}
		}
		return check;
	}
	
	//check the duplicate maintenance
	public boolean checkMaintenanceDuplicatePO(String PRODUCT){
		boolean check = false;
		try{
			String sql = ("SELECT * FROM maintenance");
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				String product = connect.rs.getString("PRODUCT");

				if (product.equals(PRODUCT)) {
					check = true;
				}
			}
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return check;
	}

	//check the duplicate spare part purchase order
	public boolean checkSparePartDuplicatePO(int poNumber) throws SQLException{
		boolean check = false;
		String sql = ("SELECT * FROM sparepartPurchaseOrder");
		connect.rs = connect.st.executeQuery(sql);
		while(connect.rs.next()){
			int PurchaseOrderNumber = Integer.parseInt(connect.rs.getString("poNumber"));

			if (PurchaseOrderNumber == poNumber) {
				check = true;
			}
		}
		return check;
	}

	//check the duplicate tooling purchase order
	public boolean checkToolingDuplicatePO(int poNumber) throws SQLException{
		boolean check = false;
		String sql = ("SELECT * FROM toolingPurchaseOrder");
		connect.rs = connect.st.executeQuery(sql);
		while(connect.rs.next()){
			int PurchaseOrderNumber = Integer.parseInt(connect.rs.getString("poNumber"));

			if (PurchaseOrderNumber == poNumber) {
				check = true;
			}
		}
		return check;
	}
	
	//check the duplicate misc purchase order
	public boolean checkMiscDuplicatePO(int poNumber) throws SQLException{
		boolean check = false;
		String sql = ("SELECT * FROM miscPurchaseOrder");
		connect.rs = connect.st.executeQuery(sql);
		while(connect.rs.next()){
			int PurchaseOrderNumber = Integer.parseInt(connect.rs.getString("poNumber"));

			if (PurchaseOrderNumber == poNumber) {
				check = true;
			}
		}
		return check;
	}
	
	//select the completeunitPurchaseOrder
	public String[] completeunitPurchaseOrderStatement(int poNumber){
		String[] completeunitPurchaseOrderStatement = new String[6];
		try{
			String sql = ("SELECT DATE_FORMAT(completeunitPurchaseOrder.orderDate, '%m/%d/%y') AS orderDate, vendor, ETDLocation, remark, deposit, customerName FROM completeunitPurchaseOrder" + " WHERE poNumber = " + poNumber);
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				completeunitPurchaseOrderStatement[0] = connect.rs.getString("orderDate");
				completeunitPurchaseOrderStatement[1] = connect.rs.getString("vendor");
				completeunitPurchaseOrderStatement[2] = connect.rs.getString("ETDLocation");
				completeunitPurchaseOrderStatement[3] = connect.rs.getString("remark");
				completeunitPurchaseOrderStatement[4] = connect.rs.getString("deposit");
				completeunitPurchaseOrderStatement[5] = connect.rs.getString("customerName");
			}
			System.out.println("orderDate: " + completeunitPurchaseOrderStatement[0] + " vendor: " + completeunitPurchaseOrderStatement[1] + " ETDLocation: " + completeunitPurchaseOrderStatement[2] + " remark: " + completeunitPurchaseOrderStatement[3]);
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return completeunitPurchaseOrderStatement;	
	}
	
	//select the completeunitOrderDate
	public ArrayList<String> completeunitOrderDateStatement(int poNumber){
		ArrayList<String> completeunitOrderDateStatement = new ArrayList<String>();
		try{
			String sql = ("SELECT DATE_FORMAT(completeunitOrderDate.orderDate, '%m/%d/%y') AS orderDate FROM completeunitOrderDate	WHERE poNumber = " + poNumber); 
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				completeunitOrderDateStatement.add(connect.rs.getString("orderDate"));
			}
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return completeunitOrderDateStatement;	
	}
	
	//select the completeunitOrderItem
	public ArrayList<DataCompleteunitOrderItem> completeunitOrderItemStatement(int poNumber){
		ArrayList<DataCompleteunitOrderItem> completeunitOrderItemStatement = new ArrayList<DataCompleteunitOrderItem>();
		try{
			String sql = ("SELECT * FROM completeunitOrderItem	WHERE poNumber = " + poNumber); 
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				DataCompleteunitOrderItem temp = new DataCompleteunitOrderItem(connect.rs.getString("PRODUCT"), connect.rs.getString("subCode"), connect.rs.getString("quantity"), connect.rs.getString("price"), connect.rs.getString("fixedcost"), connect.rs.getString("purchaseCode"), connect.rs.getString("deposit"));
				completeunitOrderItemStatement.add(temp);
			}			
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return completeunitOrderItemStatement;	
	}
	
	//select the completeunitShipment
	public ArrayList<DataCompleteunitShipment> completeunitShipmentStatement(int poNumber){
		ArrayList<DataCompleteunitShipment> completeunitShipmentStatement = new ArrayList<DataCompleteunitShipment>();
		try{
			String sql = ("SELECT quantity, DATE_FORMAT(completeunitShipment.ETD, '%m/%d/%y') AS ETD FROM completeunitShipment WHERE poNumber = " + poNumber); 
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				DataCompleteunitShipment temp = new DataCompleteunitShipment(connect.rs.getString("quantity"), connect.rs.getString("ETD"));
				completeunitShipmentStatement.add(temp);
			}			
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return completeunitShipmentStatement;	
	}
	
	//select the sparepartPurchaseOrder
	public String[] sparepartPurchaseOrderStatement(int poNumber){
		String[] sparepartPurchaseOrderStatement = new String[4];
		try{
			String sql = ("SELECT DATE_FORMAT(sparepartPurchaseOrder.orderDate, '%m/%d/%y') AS orderDate, vendor, remark, customerName FROM sparepartPurchaseOrder" + " WHERE poNumber = " + poNumber);
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				sparepartPurchaseOrderStatement[0] = connect.rs.getString("orderDate");
				sparepartPurchaseOrderStatement[1] = connect.rs.getString("vendor");
				sparepartPurchaseOrderStatement[2] = connect.rs.getString("remark");
				sparepartPurchaseOrderStatement[3] = connect.rs.getString("customerName");
			}
			System.out.println("orderDate: " + sparepartPurchaseOrderStatement[0] + " vendor: " + sparepartPurchaseOrderStatement[1] + " remark: " + sparepartPurchaseOrderStatement[2] + " customerName: " + sparepartPurchaseOrderStatement[3]);
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return sparepartPurchaseOrderStatement;	
	}
	
	//select the sparepartOrderDate
	public ArrayList<String> sparepartOrderDateStatement(int poNumber){
		ArrayList<String> sparepartOrderDateStatement = new ArrayList<String>();
		try{
			String sql = ("SELECT DATE_FORMAT(sparepartOrderDate.orderDate, '%m/%d/%y') AS orderDate FROM sparepartOrderDate WHERE poNumber = " + poNumber); 
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				sparepartOrderDateStatement.add(connect.rs.getString("orderDate"));
			}
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return sparepartOrderDateStatement;	
	}
	
	//select the sparepartOrderItem
	public ArrayList<DataSparepartOrderItem> sparepartOrderItemStatement(int poNumber){
		ArrayList<DataSparepartOrderItem> sparepartOrderItemStatement = new ArrayList<DataSparepartOrderItem>();
		try{
			String sql = ("SELECT subCode, PRODUCT, quantity, price, vendor, fixedcost, DATE_FORMAT(sparepartOrderItem.SRD, '%m/%d/%y') AS SRD, DATE_FORMAT(sparepartOrderItem.ETD, '%m/%d/%y') AS ETD FROM sparepartOrderItem WHERE poNumber = " + poNumber); 
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				DataSparepartOrderItem temp = new DataSparepartOrderItem(connect.rs.getString("subCode"), connect.rs.getString("PRODUCT"), connect.rs.getString("quantity"), connect.rs.getString("price"), connect.rs.getString("vendor"), connect.rs.getString("fixedcost"), connect.rs.getString("SRD"), connect.rs.getString("ETD"));
				sparepartOrderItemStatement.add(temp);
			}			
			for(int i = 0; i < sparepartOrderItemStatement.size(); i++){
				System.out.println(sparepartOrderItemStatement.get(i).getPRODUCT());
			}
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return sparepartOrderItemStatement;	
	}
	
	//select the SA
	public ArrayList<DataSA> SAStatement(int poNumber, String PRODUCT){
		ArrayList<DataSA> SAStatement = new ArrayList<DataSA>();
		try{
			String sql = ("SELECT saNo, saUnit, invoiceNo, DATE_FORMAT(ETD, '%m/%d/%y') AS ETD FROM SA WHERE poNumber = " + poNumber + " AND PRODUCT = \'" + PRODUCT + "\'"); 
			System.out.println(sql);
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				DataSA temp = new DataSA(connect.rs.getString("saNo"), connect.rs.getString("saUnit"), connect.rs.getString("invoiceNo"), connect.rs.getString("ETD"));
				SAStatement.add(temp);
			}			
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return SAStatement;	
	}
	
	//select the toolingPurchaseOrder
	public String[] toolingPurchaseOrderStatement(int poNumber){
		String[] toolingPurchaseOrderStatement = new String[6];
		try{
			String sql = ("SELECT DATE_FORMAT(toolingPurchaseOrder.orderDate, '%m/%d/%y') AS orderDate, vendor, remark, DATE_FORMAT(toolingPurchaseOrder.completion, '%m/%d/%y') AS completion, customerName, deposit FROM toolingPurchaseOrder" + " WHERE poNumber = " + poNumber);
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				toolingPurchaseOrderStatement[0] = connect.rs.getString("orderDate");
				toolingPurchaseOrderStatement[1] = connect.rs.getString("vendor");
				toolingPurchaseOrderStatement[2] = connect.rs.getString("remark");
				toolingPurchaseOrderStatement[3] = connect.rs.getString("completion");
				toolingPurchaseOrderStatement[4] = connect.rs.getString("customerName");
				toolingPurchaseOrderStatement[5] = connect.rs.getString("deposit");
			}
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return toolingPurchaseOrderStatement;	
	}

	//select the toolingOrderDate
	public ArrayList<String> toolingOrderDateStatement(int poNumber){
		ArrayList<String> toolingOrderDateStatement = new ArrayList<String>();
		try{
			String sql = ("SELECT DATE_FORMAT(toolingOrderDate.orderDate, '%m/%d/%y') AS orderDate FROM toolingOrderDate	WHERE poNumber = " + poNumber); 
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				toolingOrderDateStatement.add(connect.rs.getString("orderDate"));
			}
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return toolingOrderDateStatement;	
	}
	
	//select the toolingOrderItem
	public ArrayList<DataToolingOrderItem> toolingOrderItemStatement(int poNumber){
		ArrayList<DataToolingOrderItem> toolingOrderItemStatement = new ArrayList<DataToolingOrderItem>();
		try{
			String sql = ("SELECT * FROM toolingOrderItem	WHERE poNumber = " + poNumber); 
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				DataToolingOrderItem temp = new DataToolingOrderItem(connect.rs.getString("PRODUCT"), connect.rs.getString("quantity"), connect.rs.getString("price"), connect.rs.getString("description"));
				toolingOrderItemStatement.add(temp);
			}			
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return toolingOrderItemStatement;	
	}
	
	//select the miscPurchaseOrder
		public String[] miscPurchaseOrderStatement(int poNumber){
			String[] miscPurchaseOrderStatement = new String[4];
			try{
//				connect = new DBConnect();
				String sql = ("SELECT DATE_FORMAT(miscPurchaseOrder.orderDate, '%m/%d/%y') AS orderDate, vendor, remark, customerName FROM miscPurchaseOrder" + " WHERE poNumber = " + poNumber);
				connect.rs = connect.st.executeQuery(sql);
				while(connect.rs.next()){
					miscPurchaseOrderStatement[0] = connect.rs.getString("orderDate");
					miscPurchaseOrderStatement[1] = connect.rs.getString("vendor");
					miscPurchaseOrderStatement[2] = connect.rs.getString("remark");
					miscPurchaseOrderStatement[3] = connect.rs.getString("customerName");
				}
				System.out.println("orderDate: " + miscPurchaseOrderStatement[0] + " vendor: " + miscPurchaseOrderStatement[1] + " remark: " + miscPurchaseOrderStatement[2] + " customerName: " + miscPurchaseOrderStatement[3]);
			}catch(Exception ex){
				System.out.println("Error: "+ex);
			}
			return miscPurchaseOrderStatement;	
		}

		//select the miscOrderDate
		public ArrayList<String> miscOrderDateStatement(int poNumber){
			ArrayList<String> miscOrderDateStatement = new ArrayList<String>();
			try{
				String sql = ("SELECT DATE_FORMAT(miscOrderDate.orderDate, '%m/%d/%y') AS orderDate FROM miscOrderDate	WHERE poNumber = " + poNumber); 
				connect.rs = connect.st.executeQuery(sql);
				while(connect.rs.next()){
					miscOrderDateStatement.add(connect.rs.getString("orderDate"));
				}
			}catch(Exception ex){
				System.out.println("Error: "+ex);
			}
			return miscOrderDateStatement;	
		}
		
		//select the miscOrderItem
		public ArrayList<DataMiscOrderItem> miscOrderItemStatement(int poNumber){
			ArrayList<DataMiscOrderItem> miscOrderItemStatement = new ArrayList<DataMiscOrderItem>();
			try{
				String sql = ("SELECT * FROM miscOrderItem	WHERE poNumber = " + poNumber); 
				connect.rs = connect.st.executeQuery(sql);
				while(connect.rs.next()){
					DataMiscOrderItem temp = new DataMiscOrderItem(connect.rs.getString("PRODUCT"), connect.rs.getString("quantity"), connect.rs.getString("price"), connect.rs.getString("description"));
					miscOrderItemStatement.add(temp);
				}			
			}catch(Exception ex){
				System.out.println("Error: "+ex);
			}
			return miscOrderItemStatement;	
		}

	//report the total unit
	public ArrayList<DataReportTotalUnit> reportTotalUnit(String sql){
		try{
			connect.rs = connect.st.executeQuery(sql);

			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}

			// data of the table
			ArrayList<DataReportTotalUnit> data = new ArrayList<DataReportTotalUnit>();
			while (connect.rs.next()) {
				DataReportTotalUnit temp = new DataReportTotalUnit((String)connect.rs.getObject(1), (String)connect.rs.getObject(2), connect.rs.getObject(3).toString());
				data.add(temp);			        
			}
			return data;
		}catch(Exception ex){
			System.out.println("Error: "+ex);
			return null;
		}
	}

	//report the sales
	public ArrayList<DataReportSales> reportSales(String sql){
		try{
			connect.rs = connect.st.executeQuery(sql);

			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}

			// data of the table
			ArrayList<DataReportSales> data = new ArrayList<DataReportSales>();
			while (connect.rs.next()) {
				DataReportSales temp = new DataReportSales((String)connect.rs.getObject(1), (String)connect.rs.getObject(2), (int)connect.rs.getObject(3),  (String)connect.rs.getObject(4), (double)connect.rs.getObject(5), (String)connect.rs.getObject(6));
				data.add(temp);			        
			}
			return data;
		}catch(Exception ex){
			System.out.println("Error: "+ex);
			return null;
		}
	}
	
	//report the date & vendor
	public ArrayList<DataReportDateVendor> reportDateVendor(String sql){
		try{
			connect.rs = connect.st.executeQuery(sql);

			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}

			// data of the table
			ArrayList<DataReportDateVendor> data = new ArrayList<DataReportDateVendor>();
			while (connect.rs.next()) {
				DataReportDateVendor temp = new DataReportDateVendor((int)connect.rs.getObject(1), (String)connect.rs.getObject(2), (String)connect.rs.getObject(3),  (String)connect.rs.getObject(4), (double)connect.rs.getObject(5), (String)connect.rs.getObject(6), (String)connect.rs.getObject(7), (String)connect.rs.getObject(8), (String)connect.rs.getObject(9));
				data.add(temp);	
			}
			return data;
		}catch(Exception ex){
			System.out.println("Error: "+ex);
			return null;
		}
	}
	
	//report the date & vendor
	public ArrayList<DataReportDateVendor> reportDateVendor2(String sql){
		try{
			connect.rs = connect.st.executeQuery(sql);

			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}

			// data of the table
			ArrayList<DataReportDateVendor> data = new ArrayList<DataReportDateVendor>();
			while (connect.rs.next()) {
				DataReportDateVendor temp = new DataReportDateVendor((int)connect.rs.getObject(1), (String)connect.rs.getObject(2), (String)connect.rs.getObject(3),  (String)connect.rs.getObject(4), (double)connect.rs.getObject(5), (String)connect.rs.getObject(6), (String)connect.rs.getObject(7), "0", (String)connect.rs.getObject(8));
				data.add(temp);	
			}
			return data;
		}catch(Exception ex){
			System.out.println("Error: "+ex);
			return null;
		}
	}
	
	//report the PRODUCT Database
	public ArrayList<DataReportPRODUCTDataBase> reportPRODUCTDataBase(String sql){
		try{
			connect.rs = connect.st.executeQuery(sql);

			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}

			// data of the table
			ArrayList<DataReportPRODUCTDataBase> data = new ArrayList<DataReportPRODUCTDataBase>();
			while (connect.rs.next()) {
				DataReportPRODUCTDataBase temp = new DataReportPRODUCTDataBase((String)connect.rs.getObject(1), (String)connect.rs.getObject(2), (String)connect.rs.getObject(3),  (String)connect.rs.getObject(4));
				data.add(temp);			        
			}
			return data;
		}catch(Exception ex){
			System.out.println("Error: "+ex);
			return null;
		}
	}
	
	
	//return selected in poNumber 
	//of the purchase order jtable in complete unit
	public DefaultTableModel reportSA(String sql, int objectSize){
		Map<String, String> map = new HashMap<String, String>();
		map.put("poNumber", "P.O.");
		map.put("subCode", "SUBCODE");
		map.put("vendor", "VENDOR");
		map.put("PRODUCT", "PRODUCT");
		map.put("ETD", "ETD");
		try{
			connect.rs = connect.st.executeQuery(sql);
			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(map.get(metaData.getColumnName(column)));
			}
			columnNames.add("Pick P.O.");

			// data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (connect.rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					for(int i = 1; i <= objectSize; i++){
						vector.add(connect.rs.getObject(i));
					}
					vector.add(Boolean.FALSE);
				}
				data.add(vector);
			}

			return new DefaultTableModel(data, columnNames);
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}	
	}
	
	//get SA report totalquantity
	public int getQuantitySAreport(int poNumber, String PRODUCT, String subCode){
		String quantity = null;
		try{
			String sql = ("SELECT quantity FROM completeunitOrderItem  WHERE poNumber = " + poNumber + " AND PRODUCT = \'" + PRODUCT + "\' AND subCode = \'" + subCode + "\'"); 
			System.out.println(sql);
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				quantity = connect.rs.getString("quantity");
			}
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return Integer.parseInt(quantity);
	}
	
	//get SA spare part report totalquantity
	public int getQuantitySASparePartreport(int poNumber, String PRODUCT, String subCode){
		String quantity = null;
		try{
			String sql = ("SELECT quantity FROM sparepartOrderItem  WHERE poNumber = " + poNumber + " AND PRODUCT = \'" + PRODUCT + "\' AND subCode = \'" + subCode + "\'"); 
			System.out.println(sql);
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				quantity = connect.rs.getString("quantity");
			}
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return Integer.parseInt(quantity);
	}
	
	//report the SA Spare Part Database
	public ArrayList<DataReportSASparePart> reportSASparePart(String sql){
		try{
			connect.rs = connect.st.executeQuery(sql);

			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}

			// data of the table
			ArrayList<DataReportSASparePart> data = new ArrayList<DataReportSASparePart>();
			while (connect.rs.next()) {
				DataReportSASparePart temp = new DataReportSASparePart((String)connect.rs.getObject(1), (int)connect.rs.getObject(2), (String)connect.rs.getObject(3), ((BigDecimal)connect.rs.getObject(4)).intValue(), ((BigDecimal)connect.rs.getObject(5)).intValue(), ((BigDecimal)connect.rs.getObject(6)).intValue());
				data.add(temp);
			}
			return data;
		}catch(Exception ex){
			System.out.println("Error: "+ex);
			return null;
		}
	}
	
	//report the total unit
	public int reportTotalOfUnitsOrderedAndShipped(ArrayList<String> sqlList){
		int result = 0;
		for(String sql : sqlList) {
			try{
				connect.rs = connect.st.executeQuery(sql);
	
				ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
				Vector<String> columnNames = new Vector<String>();
				int columnCount = metaData.getColumnCount();
				for (int column = 1; column <= columnCount; column++) {
					columnNames.add(metaData.getColumnName(column));
				}
	
				// data of the table
				ArrayList<DataReportTotalUnit> data = new ArrayList<DataReportTotalUnit>();
				while (connect.rs.next()) {
					int quantity = connect.rs.getInt("quantity");
					result += quantity;		        
				}
			}catch(Exception ex){
				System.out.println("Error: "+ex);
			}
		}
		return result;
	}
	
	//get customer Name
	public List<String> customerNameStatement(){
		List<String> customerNameList = new ArrayList<>();
		try{
			String sql = ("SELECT * FROM `option` WHERE type = 'CustomerName'");
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				String description = connect.rs.getString("description");
				if(!StringUtils.isNullOrEmpty(description)){
					customerNameList.add(description);
				}
			}
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return customerNameList;	
	}
	
	//get customer Name id
	public String customerNameId(String customerName){
		String id = null;
		try{
			String sql = ("SELECT * FROM `option` WHERE type = 'CustomerName' AND description = \'" + customerName + "\'");
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				id = connect.rs.getString("id");
			}
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return id;	
	}
	
	//get customer Name id
	public String customerName(String id){
		String customerName = null;
		try{
			String sql = ("SELECT * FROM `option` WHERE type = 'CustomerName' AND id = \'" + id + "\'");
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				customerName = connect.rs.getString("description");
			}
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return customerName;	
	}
	 
	//insert option
	public void insertOption(String description, String type){
		try{
			String sql = ("INSERT INTO `option` (description, type) VALUES (?, ?)");
			java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
			updateQuery.setString(1, description);
			updateQuery.setString(2, type);
			updateQuery.executeUpdate();
			System.out.println("Insert option: " + description + " " + type);
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
	}
	
	//check option duplicate
	public boolean checkOptionDuplicate(String description, String type){
		boolean check = false;
		try{
			String sql = ("SELECT * FROM `option` WHERE type = \'" + type + "\'");
			System.out.println(sql);
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				String data = connect.rs.getString("description");
				if (description.equals(data)) {
					check = true;
				}
			}
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return check;
	}
	
	//update the option to the database 
	public void deleteOption(String selectedDesc, String selectedType){
		try{
			String sql = ("UPDATE `option`" + " SET description = ? " + "WHERE description = ? AND type = ?");
			java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
			updateQuery.setString(1, "");
			updateQuery.setString(2, selectedDesc);
			updateQuery.setString(3, selectedType);
			System.out.println(sql);
			updateQuery.executeUpdate();
			System.out.println("Delete to: " + selectedDesc + ", " + selectedType);
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//return all the option jtable
	public DefaultTableModel viewAllOption(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("description", "DESCRIPTION");
		map.put("type", "TYPE");
		try{
			String sql = ("select description, type from `option` where description <> '';");
			connect.rs = connect.st.executeQuery(sql);
			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(map.get(metaData.getColumnName(column)));
			}

			// data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (connect.rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					vector.add(connect.rs.getObject(columnIndex));
				}
				data.add(vector);
			}  
			return new DefaultTableModel(data, columnNames);
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	//return search criteria of the option jtable
	public DefaultTableModel viewSearchOption(String sql){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("description", "DESCRIPTION");
		map.put("type", "TYPE");
		try{
			connect.rs = connect.st.executeQuery(sql);
			ResultSetMetaData metaData = (ResultSetMetaData) connect.rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				System.out.println(metaData.getColumnName(column) + " " + map.get(metaData.getColumnName(column)));
				columnNames.add(map.get(metaData.getColumnName(column)));
			}

			// data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (connect.rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					vector.add(connect.rs.getObject(columnIndex));
				}
				data.add(vector);
			}
			return new DefaultTableModel(data, columnNames);
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}	
	}
	
	//update the option to the database 
	public void changeOption(String description, String type, String selectedDesc, String selectedType){
		try{
			String sql = ("UPDATE `option`" + " SET description = ?, type = ? " + "WHERE description = ? AND type = ?");
			java.sql.PreparedStatement updateQuery = connect.con.prepareStatement(sql);
			updateQuery.setString(1, description);
			updateQuery.setString(2, type);
			updateQuery.setString(3, selectedDesc);
			updateQuery.setString(4, selectedType);
			System.out.println(sql);
			updateQuery.executeUpdate();
			System.out.println("Change to: " + description + ", " + type);
		}catch(Exception ex){
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: "+ex,
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//get vendor
	public List<String> vendorStatement(boolean withSpace){
		List<String> vendorList = new ArrayList<>();
		if(withSpace){
			vendorList.add("");
		}
		try{
			String sql = ("SELECT DISTINCT vendor FROM maintenance ORDER BY vendor ASC;");
			connect.rs = connect.st.executeQuery(sql);
			while(connect.rs.next()){
				String vendor = connect.rs.getString("vendor");
				if(!StringUtils.isNullOrEmpty(vendor)){
					vendorList.add(vendor);
				}
			}
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		return vendorList;	
	}
}
