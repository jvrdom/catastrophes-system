package com.ssacn.ejb.business.remote;

import javax.ejb.Remote;

import com.ssacn.ejb.bean.TipoPlan;
import com.ssacn.ejb.persistence.entity.Catastrofe;
import com.ssacn.ejb.persistence.entity.Plan;

@Remote
public interface PlanManagerRemote {
	
	public void create(Catastrofe catastrofe, String descripcion, TipoPlan tipo,String url);
	public Plan findPlanByIdCatastrofe(int idCatastrofe, String tipo);
}
