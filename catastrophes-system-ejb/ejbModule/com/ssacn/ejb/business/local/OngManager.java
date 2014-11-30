package com.ssacn.ejb.business.local;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssacn.ejb.business.remote.OngManagerRemote;
import com.ssacn.ejb.exceptions.IllegalOrphanException;
import com.ssacn.ejb.exceptions.NonexistentEntityException;
import com.ssacn.ejb.persistence.entity.Ong;
import com.ssacn.ejb.persistence.jpaController.JpaOngController;;

@LocalBean
@Stateless
public class OngManager implements OngManagerRemote {
	
private JpaOngController ongController;
	
	public OngManager() {
		ongController = new JpaOngController(); 
	}
	

	@Override
	public void create(Ong ong) {
		ongController.create(ong);
		
	}

	@Override
	public List<Ong> getOngs() {
		return ongController.findOngEntities();
	}

	@Override
	public List<Ong> findOngByCatastrofe(int idCatastrofe) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void edit(Ong ong) {
		try {
			ongController.edit(ong);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void delete(int id) {
		try {
			ongController.destroy(id);
		} catch (IllegalOrphanException | NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
