package org.jboss.samples.rs.webservices;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/ServicesUsuario")
public class UsuarioResource {
	
	@Path("/alta/{user}/{pass}")
	@POST
	@Produces("application/json")
	public String altaUsuario(@PathParam("user") String user, @PathParam("pass") String pass){
		String result = "User : " + user + " Password : " + pass; 
		return result;
	}
	
	@GET
	@Path("/get")
	@Produces("application/json")
	public String getProductInJSON() {
 
		return "Hola a todos";
 
	}

}
