package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Administrador
 *
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Administrador.findByLogin", query = "SELECT u FROM Administrador u WHERE u.email = :login"),
@NamedQuery(name = "Administrador.findByNamePass", query = "SELECT u FROM Administrador u WHERE u.email = :email AND u.password=:password")})
public class Administrador extends Persona implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Administrador() {
		super();
	}
   
}
