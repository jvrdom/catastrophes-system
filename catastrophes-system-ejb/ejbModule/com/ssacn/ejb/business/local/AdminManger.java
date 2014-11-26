package com.ssacn.ejb.business.local;


import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssacn.ejb.business.remote.AdminManagerRemote;
import com.ssacn.ejb.persistence.entity.Administrador;
import com.ssacn.ejb.persistence.entity.Usuario;
import com.ssacn.ejb.persistence.jpaController.JpaAdminController;
import com.ssacn.ejb.persistence.jpaController.JpaUserController;
import com.ssacn.ejb.util.UserUtiles;

@LocalBean
@Stateless
public class AdminManger implements AdminManagerRemote {
	private JpaAdminController adminController;
	private UserUtiles utilesUsuario;
	
    /**
     * Default constructor. 
     */
    public AdminManger() {
    	adminController = new JpaAdminController();
    	utilesUsuario = new UserUtiles();
    }
    
    public Administrador findUserByLogin(String login){
    	return adminController.findUserByLogin(login);
    }
    @Override
    public Administrador findUserById(int id){
    	return adminController.findUserById(id);
    }

	@Override
	public void create(String nombre, String apellido, String email,
			String password, Date fecNac, String sexo) {
		
		Administrador u = new Administrador();
		u.setNombre(nombre);
		u.setApellido(apellido);
		u.setEmail(email);
		u.setPassword(password);
		u.setNacimiento(fecNac);
		u.setSexo(utilesUsuario.getSexo(sexo));
	//	u.setIdReg("");
		
		adminController.create(u);
		
	}
	
	@Override
	public int login (String email, String pass) {
		if(!existeUsuario(email, pass)){
			return 0;
		}else{
			return findUserByLogin(email).getId();
		}
	}

	@Override
	public boolean existeUsuario(String email, String password) {
		return adminController.existsUsuario(email, password);
	}

	@Override
	public void actualizarUsuario(Administrador user){
		try {
			
				adminController.edit(user);
			
		} catch (Exception e) {
	
			e.printStackTrace();
		} 
		
	}

	@Override
	public void eliminarUsuario(int id_usuario) {
		// TODO Auto-generated method stub
		
	}
}
