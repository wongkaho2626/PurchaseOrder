package domain;

public class DataToolingOrderItem {
	private String PRODUCT;
	private String quantity;
	private String price;
	private String description;
	
	public DataToolingOrderItem(String PRODUCT, String quantity, String price, String description){
		this.PRODUCT = PRODUCT;
		this.quantity = quantity;
		this.price = price;
		this.description = description;
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
	
	public String getDescription(){
		return description;
	}
}
