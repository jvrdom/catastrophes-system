package org.jboss.samples.rs.webservices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.ssacn.ejb.business.local.UserManager;
import com.ssacn.ejb.business.remote.UserManagerRemote;

@Path("/ServicesUsuario")
public class UsuarioResource {
	
	private UserManagerRemote userM;
	
	@Path("/alta/{nombre}/{apellido}/{email}/{fechaNac}/{sexo}/{tel}/{pass}")
	@POST
	@Produces("application/json")
	public String altaUsuario(@PathParam("nombre") String nombre,
			@PathParam("apellido") String ape,
			@PathParam("email") String email,	
			@PathParam("fechaNac") String fechaNac,
			@PathParam("sexo") String sexo,			
			@PathParam("tel") String tel,
			@PathParam("pass") String pass) {
		try {
			
			userM=new UserManager();
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date fecha = formatter.parse(fechaNac);	
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			String f=formato.format(fecha);
			System.out.println(nombre+" "+ape+" "+email+" "+pass+" "+formato.parse(f)+" "+sexo);
			userM.createUser(nombre, ape, email, pass, formato.parse(f), sexo);
			return "ok";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
		
	}
	
	@GET
	@Path("/get")
	@Produces("application/json")
	public String getProductInJSON() {
 
		return "Hola a todos";
 
	}

}
