package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Rescatista
 *
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Rescatista.findByLogin", query = "SELECT u FROM Rescatista u WHERE u.email = :login")})
public class Rescatista extends Persona implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String regId;


	public String getRegId() {
		return regId;
	}

	public void setRegId(String idReg) {
		this.regId = idReg;
	}
   
	public Rescatista() {
		super();
	}
   
}
