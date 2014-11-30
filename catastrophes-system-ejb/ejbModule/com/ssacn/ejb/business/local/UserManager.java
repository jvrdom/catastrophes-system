package com.ssacn.ejb.business.local;

import java.util.Date;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssacn.ejb.business.remote.UserManagerRemote;
import com.ssacn.ejb.persistence.entity.Usuario;
import com.ssacn.ejb.persistence.jpaController.JpaUserController;
import com.ssacn.ejb.util.UserUtiles;

/**
 * Session Bean implementation class UserManager
 */
@LocalBean
@Stateless
public class UserManager implements UserManagerRemote {

	private JpaUserController userController;
	private UserUtiles utilesUsuario;
	
    /**
     * Default constructor. 
     */
    public UserManager() {
    	userController = new JpaUserController();
    	utilesUsuario = new UserUtiles();
    }
    
    public Usuario findUserByLogin(String login){
    	return userController.findUserByLogin(login);
    }
    
    @Override
    public Usuario findUserById(int id){
    	return userController.findUserById(id);
    }

	@Override
	public void createUser(String nombre, String apellido, String email,
			String password, Date fecNac, String sexo) {
		
		Usuario u = new Usuario();
		u.setNombre(nombre);
		u.setApellido(apellido);
		u.setEmail(email);
		u.setPassword(password);
		u.setNacimiento(fecNac);
		u.setSexo(utilesUsuario.getSexo(sexo));
		
		userController.create(u);
		
	}
	
	@Override
	public void createUser(String nombre, String apellido, String email,
			String password, Date fecNac, String sexo,String tel) {
		
		Usuario u = new Usuario();
		u.setNombre(nombre);
		u.setApellido(apellido);
		u.setEmail(email);
		u.setPassword(password);
		u.setNacimiento(fecNac);
		u.setSexo(utilesUsuario.getSexo(sexo));
		u.setTelefono(tel);
		
		userController.create(u);
		
	}
	
	@Override
	public int login (String email, String pass) {
		if(!existeUsuario(email, pass).get("Existe")){
			return 0;
		}else{
			return findUserByLogin(email).getId();
		}
	}

	@Override
	public Map<String, Boolean> existeUsuario(String email, String password) {
		return userController.existsUsuario(email, password);
	}

	@Override
	public void actualizarUsuario(Usuario user){
		try {
			
		userController.edit(user);
			
		} catch (Exception e) {
	
			e.printStackTrace();
		} 
		
	}

	@Override
	public void eliminarUsuario(int id_usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object findUserByLoginPass(String login, String password) {
		return userController.findUserByLoginPass(login, password);
	}

	
}
