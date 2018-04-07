package domain;

public class DataSparepartOrderItem {
	private String subCode;
	private String PRODUCT;
	private String quantity;
	private String price;
	private String vendor;
	private String fixedcost;
	private String SRD;
	private String ETD;
	
	public DataSparepartOrderItem(String subCode, String PRODUCT, String quantity, String price, String vendor, String fixedcost, String SRD, String ETD){
		this.subCode = subCode;
		this.PRODUCT = PRODUCT;
		this.quantity = quantity;
		this.price = price;
		this.vendor = vendor;
		this.fixedcost = fixedcost;
		this.SRD = SRD;
		this.ETD = ETD;
	}
	
	public String getsubCode(){
		return subCode;
	}
	
	public String getPRODUCT(){
		return PRODUCT;
	}
	
	public String getQuantity(){
		return quantity;
	}
	
	public String getPrice(){
		return price;
	}
	
	public String getVendor(){
		return vendor;
	}
	
	public String getFixedcost(){
		return fixedcost;
	}
	
	public String getSRD(){
		return SRD;
	}
	
	public String getETD(){
		return ETD;
	}
}
