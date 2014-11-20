package managedBeans;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import utilesWeb.UtilesWeb;

import com.ssacn.ejb.business.remote.CatastrofeManagerRemote;

@ManagedBean(name="catastrofes")
@RequestScoped
public class CatastrofesBean {
	
	@EJB
	private CatastrofeManagerRemote castM;
	private String nombre,nombrePlan,url,descripcion,direccion,latLng;
	private UploadedFile file;
	private String finalPath;
	private UtilesWeb utiles;
	
	public CatastrofesBean() {
		utiles = new UtilesWeb();
	}
	
	public void altaCatastrofe(){
		System.out.println("Uploaded File Name Is :: "+file.getFileName()+" :: Uploaded File Size :: "+file.getSize());
		
		try {
			finalPath = utiles.fileUpload(file.getFileName(), file.getInputstream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(finalPath);
		
		castM.createCatastrofe(nombre, "prueba", "final", descripcion, latLng);
		//falta creacion del plan
		//galeria de imagenes
		//iconos
		//notificaciones
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombrePlan() {
		return nombrePlan;
	}

	public void setNombrePlan(String nombrePlan) {
		this.nombrePlan = nombrePlan;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLatLng() {
		return latLng;
	}

	public void setLatLng(String latLng) {
		this.latLng = latLng;
	}
	
}