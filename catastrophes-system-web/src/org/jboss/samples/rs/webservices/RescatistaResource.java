package org.jboss.samples.rs.webservices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.ssacn.ejb.bean.Sexo;
import com.ssacn.ejb.business.local.RescatistaManager;
import com.ssacn.ejb.business.local.UserManager;
import com.ssacn.ejb.business.remote.RescatistaManagerRemote;
import com.ssacn.ejb.business.remote.UserManagerRemote;
import com.ssacn.ejb.persistence.entity.Rescatista;

@Path("/ServicesRescatista")
public class RescatistaResource {

	private RescatistaManagerRemote rescatistaM;

	public RescatistaManagerRemote getRescatistaManager() {
		if (rescatistaM == null) {
			rescatistaM = new RescatistaManager();
		}
		return rescatistaM;

	}

	@Path("/alta")
	@POST
	@Produces("application/json")
	public String altaRescatista(@FormParam("nombre") String nombre,
			@FormParam("apellido") String ape,
			@FormParam("email") String email,
			@FormParam("fechaNac") String fechaNac,
			@FormParam("sexo") String sexo,
			@FormParam("tel") String tel,
			@FormParam("pass") String pass) {
		try {			

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date fecha = formatter.parse(fechaNac);
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			String f = formato.format(fecha);
			System.out.println(nombre + " " + ape + " " + email + " " + pass
					+ " " + formato.parse(f) + " " + sexo);
			getRescatistaManager().create(nombre, ape, email, pass, formato.parse(f), sexo);
			return "ok";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}

	}
	
	@Path("/login")
	@GET
	@Produces("application/json")
	public int login(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		try {			
			return getRescatistaManager().login(email,pass);			
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}

	}
	
	@Path("/actualizar")
	@POST
	@Produces("application/json")
	public String actualizarRescatista(@FormParam("idRescatista") int id,
			@FormParam("nombre") String nombre,
			@FormParam("apellido") String ape,
			@FormParam("email") String email,
			@FormParam("fechaNac") String fechaNac,
			@FormParam("sexo") String sexo, @PathParam("tel") String tel,
			@FormParam("pass") String pass) {
		try {			
			Rescatista user=new Rescatista();
			user.setApellido(ape);
			user.setEmail(email);
			user.setId(id);
			user.setNombre(nombre);
			user.setPassword(pass);
			if (sexo.equalsIgnoreCase(Sexo.Masculino.toString())){
				user.setSexo(Sexo.Masculino);
			} else {
				user.setSexo(Sexo.Femenino);
			}		
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date fecha = formatter.parse(fechaNac);
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			String f = formato.format(fecha);
			user.setNacimiento(formato.parse(f));			
			getRescatistaManager().actualizarRescatista(user);
			
			return "ok";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}

	}
	
	@GET()
	@Produces("text/plain")
	public String sayHello() {
	    return "Hello World from Rescatista Services!";
	}
}
