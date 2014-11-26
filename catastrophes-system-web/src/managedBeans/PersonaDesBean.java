package managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ssacn.ejb.business.remote.PersDesapManagerRemote;
import com.ssacn.ejb.persistence.entity.Catastrofe;
import com.ssacn.ejb.persistence.entity.PerosnaDesap;

@ManagedBean(name="personaDes")
@ViewScoped
public class PersonaDesBean {
	
	@EJB
	private PersDesapManagerRemote personaDesM;
	
	private String search;
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
	
}
