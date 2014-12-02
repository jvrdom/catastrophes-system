package com.ssacn.ejb.business.remote;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import com.ssacn.ejb.exceptions.IllegalOrphanException;
import com.ssacn.ejb.exceptions.NonexistentEntityException;
import com.ssacn.ejb.persistence.entity.Rescatista;

@Remote
public interface RescatistaManagerRemote {

	public void create(String nombre, String ape, String email, String pass,
			Date parse, String sexo);
	public void create(Rescatista rescatista);
	public int login(String email, String pass);
	public void actualizarRescatista(Rescatista user);
	public Rescatista findById(int id);
	public void delete(Integer id) throws IllegalOrphanException, NonexistentEntityException ;
	public List<Rescatista> getRescatistas();
	
}
