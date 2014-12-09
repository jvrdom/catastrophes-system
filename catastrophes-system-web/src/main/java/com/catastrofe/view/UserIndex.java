package com.catastrofe.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;

import com.catastrofe.model.Catastrofe;

@Named
@Stateful
@ConversationScoped
public class UserIndex {
	
	@Inject private CatastrofeBean catastrofe;
	private MapModel map;
	private Circle circle;
	private List<Catastrofe> catastrofes;
	
	@PostConstruct
	public void init(){
		map = new DefaultMapModel();
		catastrofes = catastrofe.getAll();
		
		for(int i = 0; i < catastrofes.size(); i++){
			LatLng coordenadas = new LatLng(catastrofes.get(i).getLatitud(), catastrofes.get(i).getLongitud());
			circle = new Circle(coordenadas, Math.sqrt(10) * 1000);
			
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
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Circle Selected", null));
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
