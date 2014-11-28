package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Novedades
 *
 */
@Entity

public class Novedades implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "novedadesId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer novedadesId;
	@Column(name="descripcion")
	private String descripcion;
	
	@OneToMany
	private List<OrigenDato> origenDatos;
	
	public Novedades() {
		super();
	}
   
}
