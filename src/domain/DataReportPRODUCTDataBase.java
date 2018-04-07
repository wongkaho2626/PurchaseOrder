package domain;

public class DataReportPRODUCTDataBase {
	private String PRODUCT;
	private String purchaseCode;
	private String DESCRIPTION;
	private String DUTY_CODE;
	
	public DataReportPRODUCTDataBase(String PRODUCT, String purchaseCode, String DESCRIPTION, String DUTY_CODE){
		this.PRODUCT = PRODUCT;
		this.purchaseCode = purchaseCode;
		this.DESCRIPTION = DESCRIPTION;
		this.DUTY_CODE = DUTY_CODE;
	}
	
	public String getPRODUCT(){
		return PRODUCT;
	}
	
	public String getpurchaseCode(){
		return purchaseCode;
	}
	
	public String getDESCRIPTION(){
		return DESCRIPTION;
	}
	
	public String getDUTY_CODE(){
		return DUTY_CODE;
	}
}
