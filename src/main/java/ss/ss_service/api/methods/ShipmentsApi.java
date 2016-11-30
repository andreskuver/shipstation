package ss.ss_service.api.methods;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ss.ss_service.api.Api;
import ss.ss_service.api.response.PaginatedListResponse;
import ss.ss_service.api.utils.ResponseParser;
import ss.ss_service.models.Order;
import ss.ss_service.models.Shipment;

public class ShipmentsApi extends Api {
	
	public ShipmentsApi(String token) {
		super(token);
		setTarget("https://ssapi.shipstation.com/shipments");
	}
	
	public PaginatedListResponse<Shipment> listPaginatedShipments(int page, int pageSize) throws Exception {
		if (page <= 0 || pageSize <= 0 || pageSize > 500) {
			throw new Exception("Invalid page or page size");
		}
		
    	Response response = 
    			this.target
    				.queryParam("page", page)
    				.queryParam("pageSize", pageSize)
    				.request(MediaType.APPLICATION_JSON)
    				.header("Authorization", "Basic "+ this.token)
    				.get();
    	
    	int statusCode = response.getStatus();
    	if (statusCode != 200) {
    		String message = "Error returned status code: " + statusCode + " \n";
    		message += response.readEntity(String.class);
    		throw new Exception(message);
    	}
    	
    	//System.out.println("headers: " + response.getHeaders());
    	
    	try {
        	String stringResponse = response.readEntity(String.class);
        	PaginatedListResponse<Shipment> parsedResponse = 
        			ResponseParser.parseListShipmentsResponse(stringResponse);

        	return parsedResponse;
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    		return null;
    	}
	}
	
	public List<Shipment> listAllShipments() throws Exception {
		List<Shipment> result = new ArrayList<Shipment>();
		int page = 1;
		int pageSize = 500;
		
		PaginatedListResponse<Shipment> res = listPaginatedShipments(page, pageSize);
		result.addAll(res.getData());
		
		while(res.hasNextPages()) {
			page++;
			res = listPaginatedShipments(page, pageSize);
			result.addAll(res.getData());
		}
		
		return result;
	}

	public List<Shipment> listAllShipmentsFilteringByOrderId(List<String> orderIdList) throws Exception {
		List<Shipment> result = new ArrayList<Shipment>();
		
		OrdersApi ordersApi = new OrdersApi(this.token);
		List<Order> orderList = ordersApi.listAllOrders();
		
		int i = 0;
		while (i < orderIdList.size()) {
			String orderId = orderIdList.get(i);
			boolean isCancelled = orderIsCancelled(orderList, orderId);
			
			if (isCancelled) {
				
				Shipment ship = new Shipment();
				ship.setOrderNumber(orderId);
				ship.setShipmentStatus("CANCELLED");
				
				orderIdList.remove(i);
				
				result.add(ship);
			} else {
				i++;
			}
		}
		
		
		List<Shipment> res = listAllShipments();
		for (Shipment shipment : res) {
			boolean exists = removeIfExists(orderIdList, shipment);
			if(exists) {
				result.add(shipment);
			}
		}

		for (String orderId : orderIdList) {
			boolean exists = isOrderInShipstation(orderList, orderId);
			
			Shipment ship = new Shipment();
			ship.setOrderNumber(orderId);
			
			if (exists) {
				ship.setShipmentStatus("ORDER");
				
			} else {
				ship.setShipmentStatus("");
			}	
			result.add(ship);
		}
		
		return result;
	}
	
	private boolean removeIfExists(List<String> list, Shipment shipment) {
		String orderId = Integer.toString(shipment.getOrderId());
		String orderNumber = shipment.getOrderNumber();
		boolean exists = false;
		
		int i = 0;
		int size = list.size();
		while (!exists && i < size) {
			String currentItem = list.get(i);
			if (currentItem.equals(orderId) || currentItem.equals(orderNumber)) {
				list.remove(i);
				exists = true;
			}
			i++;
		}
		return exists;
	}
	
	private boolean isOrderInShipstation(List<Order> orderList, String orderNumber) {
		boolean exists = false;
		int size = orderList.size();
		int i = 0;
		
		while (i < size && !exists) {
			if (orderList.get(i).getOrderNumber().compareTo(orderNumber) == 0) { 
				return true;
			}
			i++;
		}
		
		return false;
	}

	private boolean orderIsCancelled(List<Order> orderList, String orderNumber) {
		boolean exists = false;
		int size = orderList.size();
		int i = 0;
		
		while (i < size && !exists) {
			Order order = orderList.get(i);
			boolean equals = compareOrders(order, orderNumber);
			if (equals && order.getOrderStatus().compareTo("cancelled") == 0) { 
				exists = true;
			}
			i++;
		}
		
		return exists;
	}
	
	private boolean compareOrders(Order order, String orderIdentifier) {
		if (order.getOrderNumber().compareTo(orderIdentifier) == 0) {
			return true;
		}
		
		try {
			int orderId = Integer.parseInt(orderIdentifier);
			if (order.getOrderId() == orderId) {
				return true;
			}
		} catch(NumberFormatException e) {
			// DO NOTHING
		}
		
		return false;
	}
}
