package com.ssacn.ejb.business.local;

import javax.ejb.LocalBean;

import javax.ejb.Stateless;

import com.ssacn.ejb.business.remote.DonacionManagerRemote;
import com.ssacn.ejb.persistence.entity.DeBienes;
import com.ssacn.ejb.persistence.entity.DeServicios;
import com.ssacn.ejb.persistence.entity.Economica;
import com.ssacn.ejb.persistence.jpaController.JpaDonacionController;

@LocalBean
@Stateless
public class DonacionManager implements DonacionManagerRemote {
	private JpaDonacionController donacionController;

	public DonacionManager() {
		donacionController = new JpaDonacionController(); 

	}
	@Override
	public void createEconmica(Economica donacion) {
		// TODO Auto-generated method stub
		donacionController.createEconomica(donacion);
	}

	@Override
	public void createDeBienes(DeBienes donacion) {
		// TODO Auto-generated method stub
		donacionController.createDeBienes(donacion);
	}

	@Override
	public void createDeservicios(DeServicios donacion) {
		// TODO Auto-generated method stub
		donacionController.createDeservicios(donacion);
	}

}
