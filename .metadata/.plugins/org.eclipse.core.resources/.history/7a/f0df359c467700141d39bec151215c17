package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Donacion
 *
 */
@Entity
@Inheritance ( strategy=InheritanceType.SINGLE_TABLE )
public class Donacion implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer donacionId;
	
	@Temporal (TemporalType.DATE)
	private Date fecha;
	@OneToOne
	private Usuario usuario;
	@OneToOne
	private Ong ong;
	
	public Donacion() {
		super();
	}

	public Integer getDonacionId() {
		return donacionId;
	}

	public void setDonacionId(Integer donacionId) {
		this.donacionId = donacionId;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	
   
	
	
}
