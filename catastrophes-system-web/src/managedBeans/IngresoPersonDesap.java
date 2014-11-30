package managedBeans;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import utilesWeb.UtilesWeb;

import com.ssacn.ejb.business.remote.PersDesapManagerRemote;

@ManagedBean(name="ingresoPersona")
@RequestScoped
public class IngresoPersonDesap {
	
	@EJB
	private PersDesapManagerRemote personaDesM;
	
	private String nombre,apellido,telefono,descripcion;
	private UtilesWeb utiles;
	String finalPath;
	
	public IngresoPersonDesap() {
		utiles = new UtilesWeb();
	}
	
	public void ingresoPersonsaDesaparecida(){
		
	}
	
	public void handleFileUpload(FileUploadEvent event){
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        try {
			finalPath = utiles.fileUpload(event.getFile().getFileName(), event.getFile().getInputstream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
