import java.io.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.*;

import javax.json.*;

public class Integration {
	private static final String URL = "http://sandbox.flexionmobile.com/javachallenge/rest/developer/";
	private static final String DEV_ID = "mbishop";
	
	public Purchase buy(String itemID) {
		String buyURL = URL + DEV_ID + "/buy/"+itemID;
		
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpResponse response = httpClient.execute(new HttpPost(buyURL));

			String jsonString = EntityUtils.toString(response.getEntity(), "UTF-8");
			JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
		    JsonObject resultObject = jsonReader.readObject();
		    jsonReader.close();

			return createPurchase(resultObject);
		}
		catch (Exception e) {
			return null;
		}
	}

	public List<Purchase> getPurchases(){
		String getPurchasesURL = URL + DEV_ID + "/all";
		List<Purchase> allPurchases = new ArrayList<>();
		
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpResponse response = httpClient.execute(new HttpGet(getPurchasesURL));
			
			String jsonString = EntityUtils.toString(response.getEntity(), "UTF-8");
			JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
			JsonObject object = jsonReader.readObject();
			JsonArray resultArray = object.getJsonArray("purchases");
		    jsonReader.close();

		    for (Object resultElement:resultArray) {
				JsonObject resultJSON = (JsonObject)resultElement;
				allPurchases.add(createPurchase(resultJSON));
			}
		}
		catch (Exception e) {
		}
		return allPurchases;
	}

	public void consume(Purchase toBeConsumed) {
		String consumeURL = URL + DEV_ID + "/consume/"+toBeConsumed.getId();
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()){
			httpClient.execute(new HttpPost(consumeURL));
		}
		catch (Exception e) {
		}
	}
	
	private Purchase createPurchase(JsonObject object) {
		String id = object.getString("id");
		boolean consumed = object.getBoolean("consumed");
		String itemID = object.getString("itemId");
		
		return new Purchase(id, consumed, itemID);
	}
}
