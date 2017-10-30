package domain;

public class DataReportDateVendor {
	private int poNumber;
	private String subCode;
	private String PRODUCT;
	private String DESCRIPTION;
	private double price;
	private String fixedcost;
	private String DUTY_CODE;
	private String deposit;
	private String vendor;
	
	public DataReportDateVendor(int poNumber, String subCode, String PRODUCT, String DESCRIPTION, double price, String fixedcost, String DUTY_CODE, String deposit, String vendor){
		this.poNumber = poNumber;
		this.subCode = subCode;
		this.PRODUCT = PRODUCT;
		this.DESCRIPTION = DESCRIPTION;
		this.price = price;
		this.fixedcost = fixedcost;
		this.DUTY_CODE = DUTY_CODE;
		this.deposit = deposit;
		this.vendor = vendor;
	}
	
	public int getpoNumber(){
		return poNumber;
	}
	
	public String getsubCode(){
		return subCode;
	}
	
	public String getPRODUCT(){
		return PRODUCT;
	}
	
	public String getDESCRIPTION(){
		return DESCRIPTION;
	}
	
	public double getprice(){
		return price;
	}
	
	public String getfixedcost(){
		return fixedcost;
	}
	
	public String getDUTY_CODE(){
		return DUTY_CODE;
	}
	
	public String getdeposit(){
		return deposit;
	}
	
	public String getVendor(){
		return vendor;
	}
}
