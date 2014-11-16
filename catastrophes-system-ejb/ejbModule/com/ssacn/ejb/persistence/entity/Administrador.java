package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Administrador
 *
 */
@Entity

public class Administrador extends Persona implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Administrador() {
		super();
	}
   
}
