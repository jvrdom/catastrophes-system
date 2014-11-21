package com.ssacn.ejb.business.local;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssacn.ejb.business.remote.CatastrofeManagerRemote;
import com.ssacn.ejb.business.remote.PersDesapManagerRemote;
import com.ssacn.ejb.business.remote.UserManagerRemote;
import com.ssacn.ejb.persistence.entity.Catastrofe;
import com.ssacn.ejb.persistence.entity.PerosnaDesap;
import com.ssacn.ejb.persistence.entity.Usuario;
import com.ssacn.ejb.persistence.jpaController.JpaCatastrofeController;
import com.ssacn.ejb.persistence.jpaController.JpaPersDesapController;

@LocalBean
@Stateless
public class PersDesapManager implements PersDesapManagerRemote {

private JpaPersDesapController persDesapController;
	
	public PersDesapManager() {
		persDesapController = new JpaPersDesapController(); 
	}
	
	@Override
	public List<PerosnaDesap> getDesaparecidos(int idCatastrofe) {
		return persDesapController.findPersDesapEntities(idCatastrofe);
	}

	@Override
	public void createPerosnaDesap(String nombre, String apellido,
			String telefono, String descripcion, String status, int idUsuario, int idCatastrofe) {
		
		PerosnaDesap pers=new PerosnaDesap();
		pers.setApellido(apellido);
		pers.setDescripcion(descripcion);
		pers.setNombre(nombre);
		pers.setStatus(status);
		pers.setTelDeContacto(telefono);
		UserManagerRemote um=new UserManager();
		Usuario u=um.findUserById(idUsuario);		
		pers.setReportado(u);
		CatastrofeManagerRemote cm=new CatastrofeManager();
		Catastrofe c=cm.findCatastrofeById(idCatastrofe);
		pers.setCatastrofe(c);
		persDesapController.create(pers);
		
	}

}
