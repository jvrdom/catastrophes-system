package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.ssacn.ejb.bean.Tipo;

/**
 * Entity implementation class for Entity: Catastrophe
 *
 */
@Entity
@NamedQueries({
@NamedQuery(name="Catastrofe.findAll", query="SELECT c FROM Catastrofe c")})
public class Catastrofe implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="catastrofeId")
	private int catastrofeId;
	@Column(name="nombre", unique=true)
	private String nombre;
	@Column(name="description")
	private String description;
	@Enumerated(EnumType.STRING)
    protected Tipo tipo;
	@Column(name="logo")
	private String logo;
	@Column(name="css")
	private String css;
	@Column(name="coordX")
	private double coordX;
	@Column(name="coordY")
	private double coordY;
	@OneToMany(cascade ={ CascadeType.PERSIST , CascadeType.REMOVE})
	private List<ImagenCatastrofe> images;
	@OneToMany
	private List<Novedades> novedades;
	//@OneToMany
	//private List<PerosnaDesap> personasDesap;
	@OneToMany (cascade= CascadeType.ALL)
	private List<Plan> planes;


	public Catastrofe() {
		super();
	}

	
	public Tipo getTipo() {
		return tipo;
	}


	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}


	public int getCatastrofeId() {
		return catastrofeId;
	}

	public List<Novedades> getNovedades() {
		return novedades;
	}


	public void setNovedades(List<Novedades> novedades) {
		this.novedades = novedades;
	}



	public void setCatastrofeId(int catastropheID) {
		this.catastrofeId = catastropheID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String name) {
		this.nombre = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public double getCoordX() {
		return coordX;
	}

	public void setCoordX(double coordX) {
		this.coordX = coordX;
	}

	public double getCoordY() {
		return coordY;
	}

	public void setCoordY(double coordY) {
		this.coordY = coordY;
	}

	public List<ImagenCatastrofe> getImages() {
		return images;
	}

	public void setImages(List<ImagenCatastrofe> images) {
		this.images = images;
	}


	public List<Plan> getPlanes() {
		return planes;
	}


	public void setPlanes(List<Plan> planes) {
		this.planes = planes;
	}

	
	
}
