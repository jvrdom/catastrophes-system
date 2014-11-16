package org.jboss.samples.rs.webservices;

import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/ServicesRescatista")
public class RescatistaResource {

	@GET()
	@Produces("text/plain")
	public String sayHello() {
	    return "Hello World from Rescatista Services!";
	}
}
