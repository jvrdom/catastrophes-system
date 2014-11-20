package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Rescatista
 *
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Rescatista.findByLogin", query = "SELECT u FROM Rescatista u WHERE u.email = :login"),
@NamedQuery(name = "Rescatista.findByNamePass", query = "SELECT u FROM Rescatista u WHERE u.email = :email AND u.password=:password")})
public class Rescatista extends Persona implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Rescatista() {
		super();
	}
   
}
