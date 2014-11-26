package managedBeans;

<<<<<<< HEAD
import java.lang.reflect.Array;
import java.util.ArrayList;
=======
>>>>>>> 1ea78f21cc3cc8a2361d6df5a44a58fd3e29ebf2
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
<<<<<<< HEAD

import org.jboss.resteasy.util.CaseInsensitiveMap;

import com.google.gson.Gson;
=======
import javax.faces.bean.ViewScoped;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

>>>>>>> 1ea78f21cc3cc8a2361d6df5a44a58fd3e29ebf2
import com.ssacn.ejb.business.remote.CatastrofeManagerRemote;
import com.ssacn.ejb.persistence.entity.Catastrofe;

@ManagedBean(name="userIndex")
<<<<<<< HEAD
=======
@ViewScoped
>>>>>>> 1ea78f21cc3cc8a2361d6df5a44a58fd3e29ebf2
public class UserIndexBean {
	
	@EJB
	private CatastrofeManagerRemote catasM;
	
<<<<<<< HEAD
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
	
=======
	private List<Catastrofe> catastrofes;
	private MapModel mapModel;
	private Marker marker;
	
	@PostConstruct
	public void init() {
		mapModel = new DefaultMapModel();
		catastrofes = catasM.getCatastrofes();
		
		for (int i = 0; i < catastrofes.size(); i++) {
			LatLng coord = new LatLng(catastrofes.get(i).getCoordX(), catastrofes.get(i).getCoordY());
			mapModel.addOverlay(new Marker(coord, catastrofes.get(i).getNombre(),catastrofes.get(i)));
		}
		
	}

	public List<Catastrofe> getPrueba() {
		return catastrofes;
	}

	public void setPrueba(List<Catastrofe> catastrofes) {
		this.catastrofes = catastrofes;
	}

	public MapModel getMapModel() {
		return mapModel;
	}

	public Marker getMarker() {
		return marker;
	}
	
	public void onMarkerSelect(OverlaySelectEvent event){
		marker = (Marker) event.getOverlay();
	}
>>>>>>> 1ea78f21cc3cc8a2361d6df5a44a58fd3e29ebf2
}
