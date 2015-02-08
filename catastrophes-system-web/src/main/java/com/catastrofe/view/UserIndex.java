package com.catastrofe.view;

import java.io.IOException;
import java.util.Iterator;
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

import com.catastrofe.dao.CatastrofeDao;
import com.catastrofe.model.Catastrofe;
import com.catastrofe.model.Usuario;

@ViewScoped
@ManagedBean
public class UserIndex {

	/***
	 * Constantes de tipo de catástrofes.
	 */
	private final static String INCENDIO = "#FF0000";
	private final static String TERREMOTO = "#FE9A2E";
	private final static String INUNDACION = "#0101DF";
	private final static String SUDESTADA = "#58D3F7";
	private final static String TORMENTA = "#0B0B61";
 
	@EJB
	private CatastrofeDao catastrofe;
	private MapModel map;
	private Circle circle;
	private List<Catastrofe> catastrofes;
	private Catastrofe catastrofeSelected;
	private String selectedTemplate, name;
	 
	@PostConstruct
	public void init() {
		try {
			Usuario usuario=(Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
			if(usuario.getRol().getName().equals("usuario")){
				selectedTemplate="/resources/templateUser/layout.xhtml";
			}else if (usuario.getRol().getName().equals("administrador")){
				selectedTemplate="/resources/template/layout.xhtml";
			}
			map = new DefaultMapModel();
			catastrofes = catastrofe.getAll();

			for (int i = 0; i < catastrofes.size(); i++) {
				LatLng coordenadas = new LatLng(catastrofes.get(i).getLatitud(), catastrofes.get(i).getLongitud());
				circle = new Circle(coordenadas, catastrofes.get(i).getRadio());
				circle.setStrokeColor(this.getColorCatastrophe(catastrofes.get(i).getTipoCatastrofe().name()));
				circle.setFillColor(this.getColorCatastrophe(catastrofes.get(i).getTipoCatastrofe().name()));
				circle.setData(catastrofes.get(i));
				circle.setFillOpacity(0.5);

				map.addOverlay(circle);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
		}

	}

	public void onCircleSelect(OverlaySelectEvent event) {

		catastrofeSelected = (Catastrofe) event.getOverlay().getData();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("catastrofe", catastrofeSelected);

		if (catastrofeSelected != null) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/catastrofe/view.xhtml?id="	+ catastrofeSelected.getId());
			} catch (IOException e) {
				e.printStackTrace();				
			}
		}
	}
	
	public void checkCatastrofe(){
		for (int i = 0; i < catastrofes.size(); i++) {
			if(!catastrofes.get(i).getNombre().contains(name)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"La catastrofe ingresa no existe","Por favor ingrese otra dirección"));
			}
		}
	}

	private String getColorCatastrophe(String tipoCatastrofe) {
		String retorno;

		switch (tipoCatastrofe) {
		case "Incendio":
			retorno = UserIndex.INCENDIO;
			break;
		case "Sudestada":
			retorno = UserIndex.SUDESTADA;
			break;
		case "Terremoto":
			retorno = UserIndex.TERREMOTO;
			break;
		case "Inundacion":
			retorno = UserIndex.INUNDACION;
			break;
		case "Tormenta":
			retorno = UserIndex.TORMENTA;

		default:
			retorno = UserIndex.INCENDIO;
			break;
		}

		return retorno;
	}
	
	public String getSelectedTemplate() {
		return selectedTemplate;
	}

	public void setSelectedTemplate(String selectedTemplate) {
		this.selectedTemplate = selectedTemplate;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
