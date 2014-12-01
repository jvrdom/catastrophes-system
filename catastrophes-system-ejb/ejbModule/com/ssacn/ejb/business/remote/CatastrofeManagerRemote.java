package com.ssacn.ejb.business.remote;

import java.util.List;

import javax.ejb.Remote;

import com.ssacn.ejb.bean.Tipo;
import com.ssacn.ejb.bean.TipoPlan;
import com.ssacn.ejb.persistence.entity.Catastrofe;

@Remote
public interface CatastrofeManagerRemote {
	public void createCatastrofe(String nombre, TipoPlan tipo, String urlPlan, String urlIcon, String descripcion, String latLng, Tipo tipoCatastrofe);
	public List<Catastrofe> getCatastrofes();
	public Catastrofe findCatastrofeById(int idCatastrofe);
}
