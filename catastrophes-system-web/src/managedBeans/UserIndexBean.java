package managedBeans;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.util.CaseInsensitiveMap;

import com.google.gson.Gson;
import com.ssacn.ejb.business.remote.CatastrofeManagerRemote;
import com.ssacn.ejb.persistence.entity.Catastrofe;

@ManagedBean(name="userIndex")
public class UserIndexBean {
	
	@EJB
	private CatastrofeManagerRemote catasM;
	
	private List<Catastrofe> prueba;
	private List<Catastrofe> prueba2;
	private String json;
	private Gson gson;
	
	@PostConstruct
	public void init() {
		prueba = catasM.getCatastrofes();
		prueba2 = prueba;
		gson = new Gson();
		json = gson.toJson(prueba2.toArray());
		//json = catasM.getCatastrofes().toArray(new Catastrofe[catasM.getCatastrofes().size()]);
	}

	public List<Catastrofe> getPrueba() {
		return prueba;
	}

	public void setPrueba(List<Catastrofe> prueba) {
		this.prueba = prueba;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
}
