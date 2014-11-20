package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Plan
 *
 */
@Entity

public class Plan implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "planId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer planId;
	@Column(name="tipo")
	private String tipo;
	@Column(name="descripcion")
	private String descripcion;
	@Column(name="url")
	private String url;
	@OneToOne(fetch= FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Catastrofe catastrofe;
	@OneToMany
	private List<Donacion> donaciones;
	

	public Plan() {
		super();
	}


	public Integer getPlanId() {
		return planId;
	}


	public void setPlanId(Integer planId) {
		this.planId = planId;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public List<Donacion> getDonaciones() {
		return donaciones;
	}


	public void setDonaciones(List<Donacion> donaciones) {
		this.donaciones = donaciones;
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