package com.ssacn.ejb.business.remote;

import javax.ejb.Remote;

import com.ssacn.ejb.persistence.entity.Catastrofe;
import com.ssacn.ejb.persistence.entity.Plan;

@Remote
public interface PlanEmManagerRemote {
	
	public void create(Catastrofe catastrofe, String descripcion, String tipo,String url);
	public Plan findPlanByIdCatastrofe(int idCatastrofe);
}
