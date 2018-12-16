
public class Purchase implements com.flexionmobile.codingchallenge.integration.Purchase {
	private String id;
	private boolean consumed;
	private String itemID;

	public Purchase(String id, boolean consumed, String itemID) {
		this.id = id;
		this.consumed = consumed;
		this.itemID = itemID;
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public boolean getConsumed(){
		return consumed;
	}
	
	@Override
	public  String getItemId(){
		return itemID;
	}
}