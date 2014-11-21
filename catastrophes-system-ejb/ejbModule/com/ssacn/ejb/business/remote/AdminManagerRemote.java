package com.ssacn.ejb.business.remote;

import java.util.Date;

import javax.ejb.Remote;

import com.ssacn.ejb.persistence.entity.Administrador;


@Remote
public interface AdminManagerRemote {
	public void create(String nombre, String apellido, String email, String password, Date fecNac, String sexo);
    public Administrador findUserByLogin(String login);
    public boolean existeUsuario(String email, String password);
    //Se prueba solo con nombre, más adelante veremos con más campos.
    public void actualizarUsuario(Administrador user);
    public void eliminarUsuario(int id_usuario);
    public Administrador findUserById(int id);
	public int login(String email, String pass);

}
