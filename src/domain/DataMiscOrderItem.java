package domain;

public class DataMiscOrderItem {
	private String PRODUCT;
	private String quantity;
	private String price;
	private String fixedcost;
	
	public DataMiscOrderItem(String PRODUCT, String quantity, String price, String fixedcost){
		this.PRODUCT = PRODUCT;
		this.quantity = quantity;
		this.price = price;
		this.fixedcost = fixedcost;
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
	
	public String getFixedcost(){
		return fixedcost;
	}
}
