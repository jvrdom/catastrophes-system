package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Ong
 *
 */
@Entity

public class Ong implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ongId;

	private String nombre;

	private String descripcion;
	
	@OneToMany
	private List<Donacion> donaciones;
	
	@ManyToMany( mappedBy= "ongs" )
	private List <Catastrofe> catastrofes;
	
	public Ong() {
		super();
	}


	public Integer getOngId() {
		return ongId;
	}


	public void setOngId(Integer ongId) {
		this.ongId = ongId;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
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


	public List<Catastrofe> getCatastrofes() {
		return catastrofes;
	}


	public void setCatastrofes(List<Catastrofe> catastrofes) {
		this.catastrofes = catastrofes;
	}
	
	
   
}