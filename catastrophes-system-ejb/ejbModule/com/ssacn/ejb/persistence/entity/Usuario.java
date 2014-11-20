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
@NamedQuery(name = "Usuario.findByLogin", query = "SELECT u FROM Usuario u WHERE u.email = :login"),
@NamedQuery(name = "Usuario.findByNamePass", query = "SELECT u FROM Usuario u WHERE u.email = :email AND u.password=:password")})
public class Usuario extends Persona implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String regId;
	
	public Usuario() {
		super();
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String idReg) {
		this.regId = idReg;
	}
   
}
