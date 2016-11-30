package ss.ss_service;

import java.util.ArrayList;
import java.util.List;

import ss.ss_service.api.methods.ShipmentsApi;
import ss.ss_service.models.Shipment;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try {
    		ShipmentsApi sapi = new ShipmentsApi("NmNlN2U1ZTJkOWYxNDRiYmE4NjU1ZjQwMDA3Y2ZkMTE6Mzk1Yzk3ZDM3ZTFkNGY0OWI4NmNhZGFkN2E3ZTJmNzg=");
	    	try {
	    		List<String> list = new ArrayList<String>();
				list.add("229568201");
				list.add("229568202");
				list.add("229702367");
				List<Shipment> shipmentList = sapi.listAllShipmentsFilteringByOrderId(list);
	    	} catch(Exception ex) {
	    		System.out.println(ex.getMessage());
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
