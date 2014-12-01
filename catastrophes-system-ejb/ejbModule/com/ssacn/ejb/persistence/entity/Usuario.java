package com.ssacn.ejb.persistence.entity;

import com.ssacn.ejb.persistence.entity.Persona;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Usuario.findByLogin", query = "SELECT u FROM Usuario u WHERE u.email = :login")})
public class Usuario extends Persona implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	
	public Usuario() {
		super();
	}

	
   
}
