package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: OrigenDato
 *
 */
@Entity

public class OrigenDato implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name = "origenDatoId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer origenDatoId;
	
	@Column(name="descripcion")
	private String descripcion;
	
	public OrigenDato() {
		super();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getOrigenDatoId() {
		return origenDatoId;
	}

	public void setOrigenDatoId(Integer origenDatoId) {
		this.origenDatoId = origenDatoId;
	}
	
   
}
