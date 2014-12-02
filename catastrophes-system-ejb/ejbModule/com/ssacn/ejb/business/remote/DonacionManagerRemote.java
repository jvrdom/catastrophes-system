package com.ssacn.ejb.business.remote;

import javax.ejb.Remote;

import com.ssacn.ejb.persistence.entity.DeBienes;
import com.ssacn.ejb.persistence.entity.DeServicios;
import com.ssacn.ejb.persistence.entity.Economica;

@Remote
public interface DonacionManagerRemote {
	public void createEconmica(Economica donacion);
	public void createDeBienes(DeBienes donacion);
	public void createDeservicios(DeServicios donacion);
}
