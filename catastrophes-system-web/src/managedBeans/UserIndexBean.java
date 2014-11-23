package managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.ssacn.ejb.business.remote.CatastrofeManagerRemote;
import com.ssacn.ejb.persistence.entity.Catastrofe;

@ManagedBean(name="userIndex")
@ViewScoped
public class UserIndexBean {
	
	@EJB
	private CatastrofeManagerRemote catasM;
	
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
}
