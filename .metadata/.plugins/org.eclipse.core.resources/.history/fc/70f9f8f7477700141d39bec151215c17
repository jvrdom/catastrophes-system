package com.ssacn.ejb.business.remote;

import java.util.Date;

import javax.ejb.Remote;

import com.ssacn.ejb.persistence.entity.Rescatista;

@Remote
public interface RescatistaManagerRemote {

	void create(String nombre, String ape, String email, String pass,
			Date parse, String sexo);
	public int login(String email, String pass);
	void actualizarRescatista(Rescatista user);

}
