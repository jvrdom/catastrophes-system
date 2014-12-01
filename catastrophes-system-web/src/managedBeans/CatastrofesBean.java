package managedBeans;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import utilesWeb.UtilesWeb;

import com.ssacn.ejb.bean.Tipo;
import com.ssacn.ejb.bean.TipoPlan;
import com.ssacn.ejb.business.remote.CatastrofeManagerRemote;

@ManagedBean(name="catastrofes")
@ViewScoped
public class CatastrofesBean {
	
	@EJB
	private CatastrofeManagerRemote castM;
	private String nombre,nombrePlan,url,descripcion,direccion,latLng;
	private UploadedFile file;
	private String finalPath, finalPathImg;
	private UtilesWeb utiles;
	private int idCatastrofe;
	private Map<String,String> countries;
	
	
	public CatastrofesBean() {
		utiles = new UtilesWeb();
	}
	
	@PostConstruct
	public void init(){
	}
	
	public void altaCatastrofe() throws IOException{
		
		castM.createCatastrofe(nombre, TipoPlan.Emergencia, this.finalPath, this.finalPathImg , descripcion, latLng, Tipo.Incendio);

		//galeria de imagenes
		//css
		//notificaciones
	}
	
	
	public void handleFileUploadImagen(FileUploadEvent event) {
        
        try {
			this.finalPathImg = utiles.fileUpload(event.getFile().getFileName(), nombre, event.getFile().getInputstream());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesful", event.getFile().getFileName() + " is uploaded.");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (IOException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail!", "Failed to upload file: " + event.getFile().getFileName() + ", reason: " + e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
    }
	
	public void handleFileUpload(FileUploadEvent event) {
               
        try {
			this.finalPath = utiles.fileUpload(event.getFile().getFileName(), nombre, event.getFile().getInputstream());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesful", event.getFile().getFileName() + " is uploaded.");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (IOException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail!", "Failed to upload file: " + event.getFile().getFileName() + ", reason: " + e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
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

	public int getIdCatastrofe() {
		return idCatastrofe;
	}

	public void setIdCatastrofe(int idCatastrofe) {
		this.idCatastrofe = idCatastrofe;
	}

	public Map<String, String> getCountries() {
		return countries;
	}

	public void setCountries(Map<String, String> countries) {
		this.countries = countries;
	}
	
}
