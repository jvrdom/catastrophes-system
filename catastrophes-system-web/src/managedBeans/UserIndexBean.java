package managedBeans;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;

import com.ssacn.ejb.business.remote.CatastrofeManagerRemote;
import com.ssacn.ejb.persistence.entity.Catastrofe;

@ManagedBean(name="userIndex")
@ViewScoped
public class UserIndexBean {
	
	@EJB
	private CatastrofeManagerRemote catasM;
	
	private List<Catastrofe> catastrofes;
	private MapModel mapModel;
	private Circle circle;
	private Catastrofe catastrofe;
	
	@PostConstruct
	public void init() {
		mapModel = new DefaultMapModel();
		catastrofes = catasM.getCatastrofes();
		
		for (int i = 0; i < catastrofes.size(); i++) {
			LatLng coord = new LatLng(catastrofes.get(i).getCoordX(), catastrofes.get(i).getCoordY());
			
			circle = new Circle(coord, Math.sqrt(10) * 100);
			
			circle.setData(catastrofes.get(i).getCatastrofeId());
			circle.setStrokeColor("#d93c3c");
			circle.setFillColor("#d93c3c");
			circle.setFillOpacity(0.5);
			mapModel.addOverlay(circle);
			
		}
	}
	
	public void onCircleSelect(OverlaySelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Circle Selected", null));

        catastrofe = catasM.findCatastrofeById((int) event.getOverlay().getData());
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("catastrofe", catastrofe);
		
		if (catastrofe != null){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/catastrofe/catastrofeIndex.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

}
