package org.jboss.samples.rs.webservices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import com.ssacn.ejb.bean.Sexo;
import com.ssacn.ejb.business.local.UserManager;
import com.ssacn.ejb.business.remote.UserManagerRemote;
import com.ssacn.ejb.persistence.entity.Usuario;

@Path("/ServicesUsuario")
public class UsuarioResource {
	
	private UserManagerRemote userM;

	public UserManagerRemote getUserManager() {
		if (userM == null) {
			userM = new UserManager();
		}
		return userM;

	}

	@Path("/alta")
	@POST
	@Produces("application/json")
	public String altaUsuario(@FormParam("nombre") String nombre,
			@FormParam("apellido") String ape,
			@FormParam("email") String email,
			@FormParam("fechaNac") String fechaNac,
			@FormParam("sexo") String sexo,
			@FormParam("tel") String tel,
			@FormParam("pass") String pass) {
		try {			
			CorsFilter filter = new CorsFilter();
			filter.getAllowedOrigins().add("http://localhost");
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date fecha = formatter.parse(fechaNac);
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			String f = formato.format(fecha);
			System.out.println(nombre + " " + ape + " " + email + " " + pass
					+ " " + formato.parse(f) + " " + sexo);
			getUserManager().createUser(nombre, ape, email, pass, formato.parse(f), sexo, tel);
			return "ok";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}

	}
	
	@Path("/alta/{nombre}/{apellido}/{email}/{fechaNac}/{sexo}/{tel}/pass")
	@GET
	@Produces("application/json")
	public String altaUsuario2(@PathParam("nombre") String nombre,
			@PathParam("apellido") String ape,
			@PathParam("email") String email,
			@PathParam("fechaNac") String fechaNac,
			@PathParam("sexo") String sexo,
			@PathParam("tel") String tel,
			@PathParam("pass") String pass) {
		try {			

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date fecha = formatter.parse(fechaNac);
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			String f = formato.format(fecha);
			System.out.println(nombre + " " + ape + " " + email + " " + pass
					+ " " + formato.parse(f) + " " + sexo);
			getUserManager().createUser(nombre, ape, email, pass, formato.parse(f), sexo, tel);
			return "ok";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}

	}
	
	@Path("/login")
	@POST
	@Produces("application/json")
	public int login(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		try {			

			return getUserManager().login(email,pass);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}

	}
	
	@Path("/actualizar")
	@POST
	@Produces("application/json")
	public String actualizarUsuario(@FormParam("idUsuario") int id,
			@FormParam("nombre") String nombre,
			@FormParam("apellido") String ape,
			@FormParam("email") String email,
			@FormParam("fechaNac") String fechaNac,
			@FormParam("sexo") String sexo, 
			@FormParam("tel") String tel,
			@FormParam("pass") String pass) {
		try {			
			Usuario user=new Usuario();
			user.setApellido(ape);
			user.setEmail(email);
			user.setId(id);
			user.setNombre(nombre);
			user.setPassword(pass);
			user.setTelefono(tel);
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
			getUserManager().actualizarUsuario(user);
			
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
