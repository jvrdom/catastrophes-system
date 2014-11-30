package com.ssacn.ejb.business.local;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssacn.ejb.bean.TipoPlan;
import com.ssacn.ejb.business.remote.PlanManagerRemote;
import com.ssacn.ejb.persistence.entity.Catastrofe;
import com.ssacn.ejb.persistence.entity.Plan;
import com.ssacn.ejb.persistence.jpaController.JpaPlanController;

@LocalBean
@Stateless
public class PlanManager implements PlanManagerRemote {
private JpaPlanController planController;
	
	public PlanManager() {
		planController = new JpaPlanController(); 
	}
	
	@Override
	public void create(Catastrofe catastrofe, String descripcion, TipoPlan tipo,String url) {
		Plan plan=new Plan();
		//plan.setCatastrofe(catastrofe);
		plan.setDescripcion(descripcion);
		plan.setTipo(tipo);
		plan.setUrl(url);		
		planController.create(plan);
		
	}

	

	@Override
	public Plan findPlanByIdCatastrofe(int idCatastrofe, String tipo) {
		return planController.findPlanByIdCatastrofe(idCatastrofe, tipo);
	}

}
