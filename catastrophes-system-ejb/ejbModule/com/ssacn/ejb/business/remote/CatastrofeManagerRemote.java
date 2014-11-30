package com.ssacn.ejb.business.remote;

import java.util.List;

import javax.ejb.Remote;
import com.ssacn.ejb.persistence.entity.Catastrofe;

@Remote
public interface CatastrofeManagerRemote {
	public void createCatastrofe(String nombre, String nombrePlan, String urlPlan, String urlIcon, String descripcion, String latLng);
	public List<Catastrofe> getCatastrofes();
	public Catastrofe findCatastrofeById(int idCatastrofe);
}
