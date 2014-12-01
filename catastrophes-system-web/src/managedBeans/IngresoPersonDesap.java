package managedBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import utilesWeb.UtilesWeb;

import com.ssacn.ejb.business.remote.PersDesapManagerRemote;
import com.ssacn.ejb.persistence.entity.Catastrofe;
import com.ssacn.ejb.persistence.entity.ImagenPersonaDesap;
import com.ssacn.ejb.persistence.entity.Usuario;

@ManagedBean(name="ingresoPersona")
@RequestScoped
public class IngresoPersonDesap {
		
	private static final String estado = "Desaparecido";
	
	@EJB
	private PersDesapManagerRemote personaDesM;
	
	private String nombre,apellido,telefono,descripcion;
	private UtilesWeb utiles;
	private String finalPath;
	private List<ImagenPersonaDesap> images;
	private Usuario usuario;
	private Catastrofe catastrofe;
	
	@PostConstruct
	public void init(){
		utiles = new UtilesWeb();
		images = new ArrayList<ImagenPersonaDesap>();
		catastrofe = (Catastrofe) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("catastrofe");
		usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	}
	
	public void altaPersonDesaparecida(){
		personaDesM.createPerosnaDesap(nombre, apellido, telefono, descripcion, estado, usuario.getId(), catastrofe.getCatastrofeId(), this.images);
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		try {
			finalPath = utiles.fileUpload(event.getFile().getFileName(), nombre, event.getFile().getInputstream());
			
			ImagenPersonaDesap imagen = new ImagenPersonaDesap();
			imagen.setImagen(finalPath);
			
			images.add(imagen);
			
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
