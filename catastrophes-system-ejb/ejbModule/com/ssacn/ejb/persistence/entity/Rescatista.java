package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Rescatista
 *
 */
@Entity

public class Rescatista extends Persona implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Rescatista() {
		super();
	}
   
}
