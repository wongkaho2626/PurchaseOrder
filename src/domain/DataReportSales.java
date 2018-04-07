package domain;

public class DataReportSales {
	private String PRODUCT;
	private String vendor;
	private int OrderMonth;
	private String OrderYear;
	private double MonthSales;
	private String customerName;
	
	public DataReportSales(String PRODUCT, String vendor, int OrderMonth, String OrderYear, double MonthSales, String customerName){
		this.PRODUCT = PRODUCT;
		this.vendor = vendor;
		this.OrderMonth = OrderMonth;
		this.OrderYear = OrderYear;
		this.MonthSales = MonthSales;
		this.customerName = customerName;
	}
	
	public String getPRODUCT(){
		return PRODUCT;
	}
	
	public String getVendor(){
		return vendor;
	}
	
	public int getOrderMonth(){
		return OrderMonth;
	}
	
	public String getOrderYear(){
		return OrderYear;
	}
	
	public double getMonthSales(){
		return MonthSales;
	}
	
	public String getCustomerName(){
		return customerName;
	}
}
