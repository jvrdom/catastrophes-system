package com.ssacn.ejb.business.local;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssacn.ejb.business.remote.PlanEmManagerRemote;
import com.ssacn.ejb.persistence.entity.Catastrofe;
import com.ssacn.ejb.persistence.entity.Plan;
import com.ssacn.ejb.persistence.jpaController.JpaPlanEmController;

@LocalBean
@Stateless
public class PlanEmManager implements PlanEmManagerRemote {
private JpaPlanEmController planController;
	
	public PlanEmManager() {
		planController = new JpaPlanEmController(); 
	}
	
	@Override
	public void create(Catastrofe catastrofe, String descripcion, String tipo,String url) {
		Plan plan=new Plan();
		plan.setCatastrofe(catastrofe);
		plan.setDescripcion(descripcion);
		plan.setTipo(tipo);
		plan.setUrl(url);		
		planController.create(plan);
		
	}

	

	@Override
	public Plan findPlanByIdCatastrofe(int idCatastrofe) {
		return planController.findPlanByIdCatastrofe(idCatastrofe);
	}

}
