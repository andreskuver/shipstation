package ss.ss_service.api.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import ss.ss_service.api.response.PaginatedListResponse;
import ss.ss_service.models.Order;
import ss.ss_service.models.Shipment;

public class ResponseParser {
	
	public static PaginatedListResponse<Order> parseListOrdersResponse(String stringResponse) throws Exception {
		PaginatedListResponse<Order> result = new PaginatedListResponse<Order>();
		
		JsonElement jelement = new JsonParser().parse(stringResponse);
	    JsonObject  root = jelement.getAsJsonObject();
	    
	    result.setTotal(root.getAsJsonPrimitive("total").getAsInt());
	    result.setPage(root.getAsJsonPrimitive("page").getAsInt());
	    result.setPages(root.getAsJsonPrimitive("pages").getAsInt());
	    
	    result.setData(new ArrayList<Order>());
    	
	    
	    JsonArray jarray = root.getAsJsonArray("orders");
	    for (JsonElement jsonElement : jarray) {
	    	Order order = new Order();
			JsonObject jobject = jsonElement.getAsJsonObject();
			
			JsonPrimitive orderId = jobject.getAsJsonPrimitive("orderId");
			order.setOrderId(orderId.getAsInt());
			
			JsonPrimitive orderNumber = jobject.getAsJsonPrimitive("orderNumber");
			order.setOrderNumber(orderNumber.getAsString());
			
		    JsonPrimitive orderstatus = jobject.getAsJsonPrimitive("orderStatus");
		    order.setOrderStatus(orderstatus.getAsString());
		    
		    result.addData(order);
		}
	    
		return result;
	}
	
	public static PaginatedListResponse<Shipment> parseListShipmentsResponse(String stringResponse) throws Exception {
		PaginatedListResponse<Shipment> result = new PaginatedListResponse<Shipment>();
		
		JsonElement jelement = new JsonParser().parse(stringResponse);
	    JsonObject  root = jelement.getAsJsonObject();
	    
	    result.setTotal(root.getAsJsonPrimitive("total").getAsInt());
	    result.setPage(root.getAsJsonPrimitive("page").getAsInt());
	    result.setPages(root.getAsJsonPrimitive("pages").getAsInt());
	    
	    result.setData(new ArrayList<Shipment>());
    	
	    
	    JsonArray jarray = root.getAsJsonArray("shipments");
	    for (JsonElement jsonElement : jarray) {
	    	Shipment shipment = new Shipment();
			JsonObject jobject = jsonElement.getAsJsonObject();
			
			JsonPrimitive orderId = jobject.getAsJsonPrimitive("orderId");
			shipment.setOrderId(orderId.getAsInt());

			JsonPrimitive trackingNumber = jobject.getAsJsonPrimitive("trackingNumber");
			shipment.setTrackingNumber(trackingNumber.getAsString());
			
			JsonPrimitive orderNumber = jobject.getAsJsonPrimitive("orderNumber");
			shipment.setOrderNumber(orderNumber.getAsString());
			
		    result.addData(shipment);
		}
	    
		return result;
	}
}
