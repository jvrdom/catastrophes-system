package com.ssacn.ejb.business.local;

import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssacn.ejb.business.remote.RescatistaManagerRemote;
import com.ssacn.ejb.exceptions.IllegalOrphanException;
import com.ssacn.ejb.exceptions.NonexistentEntityException;
import com.ssacn.ejb.persistence.entity.Rescatista;
import com.ssacn.ejb.persistence.entity.Usuario;
import com.ssacn.ejb.persistence.jpaController.JpaRescatistaController;
import com.ssacn.ejb.persistence.jpaController.JpaUserController;
import com.ssacn.ejb.util.UserUtiles;

@LocalBean
@Stateless
public class RescatistaManager implements RescatistaManagerRemote{
	private JpaRescatistaController rescatistaController;
	private UserUtiles utilesUsuario;
	
    /**
     * Default constructor. 
     */
    public RescatistaManager() {
    	rescatistaController = new JpaRescatistaController();
    	utilesUsuario=new UserUtiles();
    }
	@Override
	public void create(String nombre, String ape, String email, String pass,
			Date fecha, String sexo) {
		Rescatista u = new Rescatista();
		u.setNombre(nombre);
		u.setApellido(ape);
		u.setEmail(email);
		u.setPassword(pass);
		u.setNacimiento(fecha);
		u.setSexo(utilesUsuario.getSexo(sexo));
		rescatistaController.create(u);
		
	}

	@Override
	public int login(String email, String pass) {
		if(!existeUsuario(email, pass)){
			return 0;
		}else{
			return findUserByLogin(email).getId();
		}
	}


	public boolean existeUsuario(String email, String password) {
		return rescatistaController.existsUsuario(email, password);
	}
	
	   public Rescatista findUserByLogin(String login){
	    	return rescatistaController.findUserByLogin(login);
	    }
	   
	   @Override
		public Rescatista findById(int id){
			return rescatistaController.findUserById(id);
		}
		  
		@Override
		public void actualizarRescatista(Rescatista user) {
			try {
				rescatistaController.edit(user);
			} catch (IllegalOrphanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NonexistentEntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
}
