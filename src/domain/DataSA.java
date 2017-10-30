package domain;

public class DataSA {
	private String saNo;
	private String saUnit;
	private String saInoviceNo;
	private String ETD;

	public DataSA(String saNo, String saUnit, String saInoviceNo, String ETD){
		this.saNo = saNo;
		this.saUnit = saUnit;
		this.saInoviceNo = saInoviceNo;
		this.ETD = ETD;
	}
	
	public String getSaNo(){
		return saNo;
	}
	
	public String getSaUnit(){
		return saUnit;
	}
	
	public String getSaInoviceNo(){
		return saInoviceNo;
	}
	
	public String getETD(){
		return ETD;
	}
}
 