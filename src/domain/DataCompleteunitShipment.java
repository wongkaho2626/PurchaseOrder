package domain;

public class DataCompleteunitShipment {
	private String quantity;
	private String ETD;
	
	public DataCompleteunitShipment(String quantity, String ETD){
		this.quantity = quantity;
		this.ETD = ETD;
	}
	
	public String getQuantity(){
		return quantity;
	}
	
	public String getETD(){
		return ETD;
	}
}
