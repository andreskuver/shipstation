package ss.ss_service.api;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public abstract class Api {
	protected String token;
	protected String entryPoint;
	protected WebTarget target;
	
	public Api (String token) {
		this.token = token;
	}
	
	protected void setTarget(String entryPoint) {
		this.entryPoint = entryPoint;
		this.target = ClientBuilder.newClient().target(this.entryPoint);
	};
}
