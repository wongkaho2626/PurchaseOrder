package domain;

public class DataReportTotalUnit {
	private String PRODUCT;
	private String vendor;
	private String quantity;
	
	public DataReportTotalUnit(String PRODUCT, String vendor, String quantity){
		this.PRODUCT = PRODUCT;
		this.vendor = vendor;
		this.quantity = quantity;
	}
	
	public String getPRODUCT(){
		return PRODUCT;
	}
	
	public String getVendor(){
		return vendor;
	}
	
	public String getQuantity(){
		return quantity;
	}
}