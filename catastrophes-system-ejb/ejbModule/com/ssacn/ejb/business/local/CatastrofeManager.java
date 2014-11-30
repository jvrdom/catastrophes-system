package com.ssacn.ejb.business.local;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssacn.ejb.bean.TipoPlan;
import com.ssacn.ejb.business.remote.CatastrofeManagerRemote;
import com.ssacn.ejb.persistence.entity.Catastrofe;
import com.ssacn.ejb.persistence.entity.Plan;
import com.ssacn.ejb.persistence.jpaController.JpaCatastrofeController;
import com.ssacn.ejb.persistence.jpaController.JpaPlanController;
import com.ssacn.ejb.util.AndroidGCMPushNotification;

@LocalBean
@Stateless
public class CatastrofeManager implements CatastrofeManagerRemote{
	
	private JpaCatastrofeController catastrofeController;
	private JpaPlanController planController;
	public CatastrofeManager() {
		catastrofeController = new JpaCatastrofeController(); 
		planController=new JpaPlanController();
	}
	
	@Override
	public void createCatastrofe(String nombre, TipoPlan tipoPlan, String urlPlan, String urlIcon,
			String descripcion,String latLng) {
		
		String regID = "APA91bErxDEhplg4-GT2RoY9N7tibbzJAifLrqpVhy0OYkwaNHhWWKsGAxzm31VpbBtixyssPC61jmbFNZfnq_lhfva55uE6Cb5ePauJlBygykDQV0Hje-Orjin0P94_em4nNBk8rYT-NHs96okhLmbfdDpApUAqbjAdGw21ZTnx1spu4Vtb8RM";
		
		//Convertir string to double
		latLng = latLng.replace("(", "");
		latLng = latLng.replace(")", "");
		
		String [] latlong = latLng.split(",");
		double lat = Double.parseDouble(latlong[0]);
		double lng = Double.parseDouble(latlong[1]);
		
		Catastrofe catastrofe = new Catastrofe();
		catastrofe.setNombre(nombre);
		catastrofe.setLogo(urlIcon);
		catastrofe.setDescription(descripcion);
		catastrofe.setCoordX(lat);
		catastrofe.setCoordY(lng);
				
		Plan plan = new Plan();
		plan.setUrl(urlPlan);
		plan.setDescripcion(descripcion);
		plan.setTipo(tipoPlan);
		plan.setCatastrofe(catastrofe);
		if(catastrofe.getPlanes()==null){
			List<Plan> planes=new ArrayList<Plan>();
			planes.add(plan);
			catastrofe.setPlanes(planes);
		}else{
			catastrofe.getPlanes().add(plan);
		}
		
		
		//
		//AndroidGCMPushNotification.enviarNotificaciones("prueba", regID);		
		catastrofeController.create(catastrofe);

	}

	@Override
	public List<Catastrofe> getCatastrofes() {
		return catastrofeController.findCatastrofeEntities();
	}

	@Override
	public Catastrofe findCatastrofeById(int idCatastrofe) {
		return catastrofeController.findCatastrofe(idCatastrofe);
	}

}
