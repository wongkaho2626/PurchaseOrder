package domain;

import java.sql.SQLException;
import java.util.List;

public class CustomerNameItem {
	public static String getCustomerNameId(String customerName){
		IRBS irbs = new IRBS();
		String customerNameId = irbs.customerNameId(customerName);
		try {
			irbs.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerNameId;
	}
}
