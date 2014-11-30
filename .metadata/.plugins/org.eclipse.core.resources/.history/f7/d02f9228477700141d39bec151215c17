package org.jboss.samples.rs.webservices;


import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.gson.Gson;
import com.ssacn.ejb.business.local.PersDesapManager;
import com.ssacn.ejb.business.remote.PersDesapManagerRemote;
import com.ssacn.ejb.persistence.entity.PerosnaDesap;

@Path("/ServicesPersDesap")
public class PersDesapResource {
	
	private PersDesapManagerRemote persDesapM;

	public PersDesapManagerRemote getPersDesapManager() {
		if (persDesapM == null) {
			persDesapM = new PersDesapManager();
		}
		return persDesapM;

	}

	@Path("/ingreso")
	@POST
	@Produces("application/json")
	public String ingresarPersDesap(@FormParam("nombre") String nombre,
			@FormParam("apellido") String ape,
			@FormParam("telefono") String telefono,
			@FormParam("descripcion") String descripcion,
			@FormParam("status") String status, 
			@FormParam("idUsuario") int idUsuario,
			@FormParam("idCatastrofe") int idCatastrofe){
		
		try {			

			getPersDesapManager().createPerosnaDesap(nombre, ape, telefono, descripcion, status, idUsuario, idCatastrofe);
			return "ok";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}

	}
	
	@Path("/list/{idCatastrofe}")
	@GET
	@Produces("application/json")
	public String listarDesaparecidos(@PathParam("idCatastrofe") int idCatastrofe) {
		try {	
			Gson gson=new Gson();
			List<PerosnaDesap> lista=getPersDesapManager().getDesaparecidos(idCatastrofe);
			String formatoJSON = gson.toJson(lista);
			return formatoJSON;
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}

	}

}
