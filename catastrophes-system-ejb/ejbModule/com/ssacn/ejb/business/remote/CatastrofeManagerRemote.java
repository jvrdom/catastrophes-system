package com.ssacn.ejb.business.remote;

import javax.ejb.Remote;

@Remote
public interface CatastrofeManagerRemote {
	public void createCatastrofe(String nombre, String nombrePlan, String url, String descripcion, String latLng);
}
