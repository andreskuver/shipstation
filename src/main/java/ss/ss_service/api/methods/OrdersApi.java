package ss.ss_service.api.methods;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ss.ss_service.api.response.PaginatedListResponse;
import ss.ss_service.api.utils.ResponseParser;
import ss.ss_service.models.Order;

public class OrdersApi extends ss.ss_service.api.Api {

	public OrdersApi(String token) {
		super(token);
		setTarget("https://ssapi.shipstation.com/orders");
	}
	
	public PaginatedListResponse<Order> listPaginatedOrders(int page, int pageSize) throws Exception {
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
    	
    	try {
        	String stringResponse = response.readEntity(String.class);
        	PaginatedListResponse<Order> parsedResponse = 
        			ResponseParser.parseListOrdersResponse(stringResponse);

        	return parsedResponse;
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    		return null;
    	}
	}
	
	public List<Order> listAllOrders() throws Exception {
		List<Order> result = new ArrayList<Order>();
		int page = 1;
		int pageSize = 500;
		
		PaginatedListResponse<Order> res = listPaginatedOrders(page, pageSize);
		result.addAll(res.getData());
		
		while(res.hasNextPages()) {
			page++;
			res = listPaginatedOrders(page, pageSize);
			result.addAll(res.getData());
		}
		
		return result;
	}

}
