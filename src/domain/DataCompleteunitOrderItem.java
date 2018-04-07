package domain;

public class DataCompleteunitOrderItem {
	private String PRODUCT;
	private String subCode;
	private String quantity;
	private String price;
	private String fixedcost;
	private String purchaseCode;
	private String deposit;
	
	public DataCompleteunitOrderItem(String PRODUCT, String subCode, String quantity, String price, String fixedcost, String purchaseCode, String deposit){
		this.PRODUCT = PRODUCT;
		this.subCode = subCode;
		this.quantity = quantity;
		this.price = price;
		this.fixedcost = fixedcost;
		this.purchaseCode = purchaseCode;
		this.deposit = deposit;
	}
	
	public String getPRODUCT(){
		return PRODUCT;
	}
	
	public String getSubCode(){
		return subCode;
	}
	
	public String getQuantity(){
		return quantity;
	}
	
	public String getPrice(){
		return price;
	}
	
	public String getFixedcost(){
		return fixedcost;
	}
	
	public String getPurchaseCode(){
		return purchaseCode;
	}
	
	public String getDeposit(){
		return deposit;
	}
}
