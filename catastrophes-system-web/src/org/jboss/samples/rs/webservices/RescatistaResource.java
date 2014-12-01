package org.jboss.samples.rs.webservices;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.primefaces.json.JSONException;










import com.ssacn.ejb.bean.Sexo;
import com.ssacn.ejb.bean.TipoPlan;
import com.ssacn.ejb.business.local.CatastrofeManager;
import com.ssacn.ejb.business.local.PlanManager;
import com.ssacn.ejb.business.local.RescatistaManager;
import com.ssacn.ejb.business.local.UserManager;
import com.ssacn.ejb.business.remote.CatastrofeManagerRemote;
import com.ssacn.ejb.business.remote.PlanManagerRemote;
import com.ssacn.ejb.business.remote.RescatistaManagerRemote;
import com.ssacn.ejb.business.remote.UserManagerRemote;
import com.ssacn.ejb.persistence.entity.Catastrofe;
import com.ssacn.ejb.persistence.entity.Plan;
import com.ssacn.ejb.persistence.entity.Rescatista;

@Provider
@Path("/ServicesRescatista")
public class RescatistaResource {

	private RescatistaManagerRemote rescatistaM;
	private CatastrofeManagerRemote catastrofeM;
	private PlanManagerRemote planM;
	
	public RescatistaManagerRemote getRescatistaManager() {
		if (rescatistaM == null) {
			rescatistaM = new RescatistaManager();
		}
		return rescatistaM;
	}
	public CatastrofeManagerRemote getCatastrofeManager() {
		if (catastrofeM == null) {
			catastrofeM = new CatastrofeManager();
		}
		return catastrofeM;

	} 
	public PlanManagerRemote getPlanManager() {
		if (planM == null) {
			planM = new PlanManager();
		}
		return planM;
	}

	@Path("/alta")
	@POST
	@Produces("application/json")
	public String altaRescatista(@FormParam("nombre") String nombre,
			@FormParam("apellido") String ape,
			@FormParam("email") String email,
			@FormParam("fechaNac") String fechaNac,
			@FormParam("sexo") String sexo, @FormParam("tel") String tel,
			@FormParam("pass") String pass) {
		try {

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date fecha = formatter.parse(fechaNac);
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			String f = formato.format(fecha);
			System.out.println(nombre + " " + ape + " " + email + " " + pass
					+ " " + formato.parse(f) + " " + sexo);
			getRescatistaManager().create(nombre, ape, email, pass,
					formato.parse(f), sexo);
			return "ok";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}

	}

	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> login(@QueryParam("email") String email,
			@QueryParam("pass") String pass) {
		Map<String, String> map = new HashMap<String, String>();
		System.out.println("mail " + email + " pass " + pass);
		
		int resultado;
		try {
			resultado = getRescatistaManager().login(email,pass);
			//Integer.toString(i) or String.valueOf(i).
			map.put("login", String.valueOf(resultado) );
			return map;
		} catch (Exception ex) {
			ex.printStackTrace();
			map.put("login", String.valueOf(-1) );
			return map;
		}

	}
	
	
	@GET
	@Path("/insertRegId")
	@Produces(MediaType.APPLICATION_JSON)
	public  Map<String, String> insertRegId(@QueryParam("idResc") int id,
			@QueryParam("idReg") String idReg) {
		try {
			Rescatista res = getRescatistaManager().findById(id);
			res.setRegId(idReg);
			getRescatistaManager().actualizarRescatista(res);
			Map<String, String> map = new HashMap<String, String>();
			map.put("insertRegId", "ok" );
			return map;
		} catch (Exception ex) {
			ex.printStackTrace();
			Map<String, String> map = new HashMap<String, String>();
			map.put("insertRegId", ex.getMessage());
			return map;
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
			Rescatista user = new Rescatista();
			user.setApellido(ape);
			user.setEmail(email);
			user.setId(id);
			user.setNombre(nombre);
			user.setPassword(pass);
			if (sexo.equalsIgnoreCase(Sexo.Masculino.toString())) {
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

	

	@GET
	@Path("/catastrofes")
	@Produces("application/json")
	public JSONArray getCatastrofes(){
	//public JsonArray getCatastrofes(){
		
	List<Catastrofe> catastrofes;
	catastrofes = getCatastrofeManager().getCatastrofes();
	
	JSONObject jObject = new JSONObject();
	
	JSONArray jArray = new JSONArray();
    try {
            
            for (Catastrofe catastrofe : catastrofes) {
//            	String planDescripcion = catastrofe.getPlan().getUrl();
                JSONObject catastrofeJSON = new JSONObject();
                catastrofeJSON.put("idC", catastrofe.getCatastrofeId() );
                catastrofeJSON.put("nombre", catastrofe.getNombre() );
                catastrofeJSON.put("latitud", catastrofe.getCoordY() );
                catastrofeJSON.put("longitud", catastrofe.getCoordX() );
                
                for(Plan plan: catastrofe.getPlanes()){
                	if(plan.getTipo()==TipoPlan.Emergencia){
                		catastrofeJSON.put("planEmergencia", plan.getUrl());
                	}else if(plan.getTipo()==TipoPlan.Riesgo){
                		catastrofeJSON.put("planRiesgo", plan.getUrl());
                	}
                }                       
                jArray.add(catastrofeJSON);
                }
            jObject.put("StudentList", jArray);
    } catch (Exception jse) {
        jse.printStackTrace();
    }
    
    return jArray;

}





	@GET()
	@Produces("text/plain")
	public String sayHello() {
		return "Hello World from Rescatista Services!";
	}

}
