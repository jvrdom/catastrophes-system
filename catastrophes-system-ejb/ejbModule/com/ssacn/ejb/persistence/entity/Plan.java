package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.ssacn.ejb.bean.Tipo;
import com.ssacn.ejb.bean.TipoPlan;

/**
 * Entity implementation class for Entity: Plan
 *
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Plan.findByIdCatastrofe", query = "SELECT p FROM Plan p join p.catastrofe c WHERE c.catastrofeId = :idCatastrofe AND p.tipo = :tipo")})
public class Plan implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "planId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer planId;
	@Enumerated(EnumType.STRING)
	private TipoPlan tipo;
	@Column(name="descripcion")
	private String descripcion;
	@Column(name="url")
	private String url;
	@OneToOne
	private Catastrofe catastrofe;

	

	public Plan() {
		super();
	}


	public Integer getPlanId() {
		return planId;
	}


	public void setPlanId(Integer planId) {
		this.planId = planId;
	}


	public TipoPlan getTipo() {
		return tipo;
	}


	public void setTipo(TipoPlan tipo) {
		this.tipo = tipo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}
	
	public Catastrofe getCatastrofe() {
		return catastrofe;
	}

	public void setCatastrofe(Catastrofe catastrofe) {
		this.catastrofe = catastrofe;
	}

	
   
}
