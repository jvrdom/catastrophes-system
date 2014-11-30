package org.jboss.samples.rs.webservices;


import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.ssacn.ejb.business.local.AyudaManager;
import com.ssacn.ejb.business.local.UserManager;
import com.ssacn.ejb.business.remote.AyudaManagerRemote;
import com.ssacn.ejb.business.remote.UserManagerRemote;
import com.ssacn.ejb.persistence.entity.PedidoAyuda;


@Path("/ServicePedidoDeAyuda")
public class PedidoDeAyudaResource {
	private AyudaManagerRemote ayudaM;

	public AyudaManagerRemote getAyudaManager() {
		if (ayudaM == null) {
			ayudaM = new AyudaManager();
		}
		return ayudaM;
	}
	
	@Path("/alta")
	@POST
	@Produces("application/json")
	public String altaUsuario(@FormParam("latitud") float latitud,
			@FormParam("longitud") float longitud,
			@FormParam("descrpcion") String descripcion,
			@FormParam("idUsuario") int idUsuario) {
		try {			
			PedidoAyuda pedido=new PedidoAyuda();
			pedido.setDescripcion(descripcion);
			pedido.setLatitud(latitud);
			pedido.setLongitud(longitud);
			UserManagerRemote um=new UserManager();
			pedido.setUsuario(um.findUserById(idUsuario));
			getAyudaManager().create(pedido);
			return "ok";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}

	}

}
