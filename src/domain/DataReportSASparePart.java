package domain;

import java.util.Calendar;
import java.util.Date;

public class DataReportSASparePart {
	private String ETD;
	private String vendor;
	private String PRODUCT;
	private int poNumber;
	private String description;
	private int SAremain;
	
	public DataReportSASparePart(String ETD, String vendor, String PRODUCT, int poNumber, String description, int SAremain){
		this.ETD = ETD;
		this.vendor = vendor;
		this.PRODUCT = PRODUCT;
		this.poNumber = poNumber;
		this.description = description;
		this.SAremain = SAremain;
	}
	
	public String getETD(){
		return ETD;
	}
	
	public String getvendor(){
		return vendor;
	}
	
	public String getPRODUCT(){
		return PRODUCT;
	}
	
	public int getpoNumber(){
		return poNumber;
	}
	
	public String getdescription(){
		return description;
	}
	
	public int getSAremain(){
		return SAremain;
	}
	
}
