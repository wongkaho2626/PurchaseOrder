package domain;

public class DataReportSASparePart {
	private String PRODUCT;
	private int poNumber;
	private String Vendor;
	private int quantity;
	private int saUnit;
	private int SAremain;
	
	public DataReportSASparePart(String PRODUCT, int poNumber, String Vendor, int quantity, int saUnit, int SAremain){
		this.PRODUCT = PRODUCT;
		this.poNumber = poNumber;
		this.Vendor = Vendor;
		this.quantity = quantity;
		this.saUnit = saUnit;
		this.SAremain = SAremain;
	}
	
	public String getPRODUCT() {
		return PRODUCT;
	}

	public int getPoNumber() {
		return poNumber;
	}

	public String getVendor() {
		return Vendor;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getSaUnit() {
		return saUnit;
	}

	public int getSAremain() {
		return SAremain;
	}
}
