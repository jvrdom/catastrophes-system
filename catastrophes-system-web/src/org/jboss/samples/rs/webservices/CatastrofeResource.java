package org.jboss.samples.rs.webservices;


import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.gson.Gson;
import com.ssacn.ejb.business.local.CatastrofeManager;
import com.ssacn.ejb.business.remote.CatastrofeManagerRemote;
import com.ssacn.ejb.persistence.entity.Catastrofe;
import com.ssacn.ejb.persistence.entity.PerosnaDesap;

@Path("/ServicesCatastrofe")
public class CatastrofeResource {
	
	private CatastrofeManagerRemote catastrofeM;

	public CatastrofeManagerRemote getCatastrofeManager() {
		if (catastrofeM == null) {
			catastrofeM = new CatastrofeManager();
		}
		return catastrofeM;

	}

	@Path("/list")
	@POST
	@Produces("application/json")
	public String listarCatastrofes() {
		try {	
			Gson gson=new Gson();
			List<Catastrofe> lista=getCatastrofeManager().getCatastrofes();
			String formatoJSON = gson.toJson(lista);
			return formatoJSON;
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}

	}
		

}
