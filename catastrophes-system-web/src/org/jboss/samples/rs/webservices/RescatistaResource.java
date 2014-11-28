package org.jboss.samples.rs.webservices;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.interception.MessageBodyWriterContext;
import org.jboss.resteasy.util.Base64;
import org.json.simple.JSONObject;
import org.primefaces.json.JSONException;

import com.google.gson.JsonObject;
import com.ssacn.ejb.bean.Sexo;
import com.ssacn.ejb.business.local.PlanEmManager;
import com.ssacn.ejb.business.local.RescatistaManager;
import com.ssacn.ejb.business.local.UserManager;
import com.ssacn.ejb.business.remote.PlanEmManagerRemote;
import com.ssacn.ejb.business.remote.RescatistaManagerRemote;
import com.ssacn.ejb.business.remote.UserManagerRemote;
import com.ssacn.ejb.persistence.entity.Plan;
import com.ssacn.ejb.persistence.entity.Rescatista;

@Provider
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

	@Path("/login")
	@POST
	@Produces("application/json")
	public int login(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		try {
			return getRescatistaManager().login(email, pass);
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

	@Path("/idReg/{idResc}/{idReg}")
	@GET
	@Produces("application/json")
	public String actualizarIdReg(@PathParam("idResc") int id,
			@PathParam("idReg") String idReg) {
		try {
			Rescatista res = getRescatistaManager().findById(id);
			res.setRegId(idReg);
			getRescatistaManager().actualizarRescatista(res);

			return "ok";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}

	}

	@GET
	@Path("/PlanEmergencia/{idCatastrofe}")
	@Produces("application/pdf")
	public Response findPlan(@PathParam("idCatastrofe") int idCatastrofe) {
		PlanEmManagerRemote pm = new PlanEmManager();
		Plan plan = pm.findPlanByIdCatastrofe(idCatastrofe);

		File file = new File(plan.getUrl());

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=plan.pdf");
		return response.build();

		// convertFile(json.getString("file"), json.getString("file_name"));
		// Prints my json object
		// return json;
	}

	@GET
	@Path("/PlanEmergencia2/{idCatastrofe}")
	@Produces("application/json")
	public String findPlan2(@PathParam("idCatastrofe") int idCatastrofe) throws Exception {
		try{
		PlanEmManagerRemote pm = new PlanEmManager();
		//Plan plan = pm.findPlanByIdCatastrofe(idCatastrofe);

		//File file = new File(plan.getUrl());
		File file = new File("/home/nico/Descargas/pdf-sample.pdf");
		byte[] bytes = loadFile(file);
		String encoded = Base64.encodeBytes(bytes);
		// String encodedString = new String(encoded);
		//JsonObject json = new JsonObject();
		//json.addProperty("plan", encoded);
		//return json;
		return encoded;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex);
		}
		// convertFile(json.getString("file"), json.getString("file_name"));
		// Prints my json object
		// return json;
	}

	// Convert a Base64 string and create a file
	/*
	 * private void convertFile(String file_string, String file_name) throws
	 * IOException{ byte[] bytes = Base64.decode(file_string); File file = new
	 * File("local_path/"+file_name); FileOutputStream fop = new
	 * FileOutputStream(file); fop.write(bytes); fop.flush(); fop.close(); }
	 */

	private static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}
		byte[] bytes = new byte[(int) length];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "+ file.getName());
		}

		is.close();
		return bytes;
	}

	@GET()
	@Produces("text/plain")
	public String sayHello() {
		return "Hello World from Rescatista Services!";
	}

}
