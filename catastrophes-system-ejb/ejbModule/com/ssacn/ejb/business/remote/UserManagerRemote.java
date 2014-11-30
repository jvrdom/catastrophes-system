package com.ssacn.ejb.business.remote;

import java.util.Date;
import java.util.Map;

import javax.ejb.Remote;

import com.ssacn.ejb.persistence.entity.Usuario;

@Remote
public interface UserManagerRemote {
	
    public void createUser(String nombre, String apellido, String email, String password, Date fecNac, String sexo);
    public void createUser(String nombre, String apellido, String email,String password, Date fecNac, String sexo,String tel); 
    public Usuario findUserByLogin(String login);
    public Object findUserByLoginPass(String login, String password);
    public Map<String, Boolean> existeUsuario(String email, String password);
    public void actualizarUsuario(Usuario user);
    public void eliminarUsuario(int id_usuario);
    public Usuario findUserById(int id);
	public int login(String email, String pass);
    
}
