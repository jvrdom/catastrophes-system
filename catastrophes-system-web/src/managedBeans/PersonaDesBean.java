package managedBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import utilesWeb.UtilesWeb;

import com.ssacn.ejb.business.remote.PersDesapManagerRemote;
import com.ssacn.ejb.persistence.entity.Catastrofe;
import com.ssacn.ejb.persistence.entity.ImagenPersonaDesap;
import com.ssacn.ejb.persistence.entity.PerosnaDesap;
import com.ssacn.ejb.persistence.entity.Usuario;

@ManagedBean(name="personaDes")
@ViewScoped
public class PersonaDesBean {
	
	@EJB
	private PersDesapManagerRemote personaDesM;
	
	private String search;
	private String nombre, apellido,telefono,descripcion;
	private PerosnaDesap persona;
	private Catastrofe catastrofe;
	private List<PerosnaDesap> listadoPersonasDes;
	
	
	@PostConstruct
	public void init(){
		catastrofe = (Catastrofe) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("catastrofe");
		listadoPersonasDes = personaDesM.getDesaparecidos(catastrofe.getCatastrofeId());
	}
	
	public void handleKeyEvent(){
		if (!search.isEmpty()){
			listadoPersonasDes = personaDesM.getDesaparecidosByName(catastrofe.getCatastrofeId(), search);
		} else {
			listadoPersonasDes = personaDesM.getDesaparecidos(catastrofe.getCatastrofeId());
		}
	}
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public PerosnaDesap getPersona() {
		return persona;
	}
	
	public void setPersona(PerosnaDesap persona) {
		this.persona = persona;
	}
	
	public List<PerosnaDesap> getListadoPersonasDes() {
		return listadoPersonasDes;
	}
	
	public void setListadoPersonasDes(List<PerosnaDesap> listadoPersonasDes) {
		this.listadoPersonasDes = listadoPersonasDes;
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

	public Catastrofe getCatastrofe() {
		return catastrofe;
	}

	public void setCatastrofe(Catastrofe catastrofe) {
		this.catastrofe = catastrofe;
	}

}
