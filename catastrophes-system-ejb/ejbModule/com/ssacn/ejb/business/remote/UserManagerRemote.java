package com.ssacn.ejb.business.remote;

import java.util.Date;

import javax.ejb.Remote;

import com.ssacn.ejb.persistence.entity.Usuario;

@Remote
public interface UserManagerRemote {
	
    public void createUser(String nombre, String apellido, String email, String password, Date fecNac, String sexo);
    public Usuario findUserByLogin(String login);
    public boolean existeUsuario(String email, String password);
    //Se prueba solo con nombre, más adelante veremos con más campos.
    public void actualizarUsuario(String name);
    public void eliminarUsuario(int id_usuario);
    
}
