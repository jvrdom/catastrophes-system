package com.catastrofe.view;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;

import com.catastrofe.dao.CatastrofeDao;
import com.catastrofe.model.Catastrofe;

@ViewScoped
@ManagedBean
public class UserIndex {
	
	@EJB private CatastrofeDao catastrofe;
	private MapModel map;
	private Circle circle;
	private List<Catastrofe> catastrofes;
	private Catastrofe catastrofeSelected;
	
	@PostConstruct
	public void init(){
		map = new DefaultMapModel();
		catastrofes = catastrofe.getAll();
		
		for(int i = 0; i < catastrofes.size(); i++){
			LatLng coordenadas = new LatLng(catastrofes.get(i).getLatitud(), catastrofes.get(i).getLongitud());
			circle = new Circle(coordenadas, catastrofes.get(i).getRadio());
			
			/**	
			 * Esto cambiará cuando se defina el tipo de catastrofe
			 */
			circle.setStrokeColor("#d93c3c");
			circle.setFillColor("#d93c3c");
			
			circle.setData(catastrofes.get(i));
			circle.setFillOpacity(0.5);
			map.addOverlay(circle);
		}
	}
	
	public void onCircleSelect(OverlaySelectEvent event) {
        
		catastrofeSelected = (Catastrofe) event.getOverlay().getData();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("catastrofe", catastrofeSelected); 
		
		if (catastrofeSelected != null) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/catastrofe/view.xhtml?id=" + catastrofeSelected.getId());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

	public MapModel getMap() {
		return map;
	}

	public void setMap(MapModel map) {
		this.map = map;
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public List<Catastrofe> getCatastrofes() {
		return catastrofes;
	}

	public void setCatastrofes(List<Catastrofe> catastrofes) {
		this.catastrofes = catastrofes;
	}

}
