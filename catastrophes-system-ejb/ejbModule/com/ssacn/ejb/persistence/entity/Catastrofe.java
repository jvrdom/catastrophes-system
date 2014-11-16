package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Catastrophe
 *
 */
@Entity
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
	@Column(name="logo")
	private Byte[] logo;
	@Column(name="css")
	private String css;
	@Column(name="coordX")
	private double coordX;
	@Column(name="coordY")
	private double coordY;
	@OneToMany(cascade ={ CascadeType.PERSIST , CascadeType.REMOVE})
	private List<ImagenCatastrofe> images;
	@ManyToMany
	private List<Ong> ongs;
	@OneToMany
	private List<Novedades> novedades;
	@OneToMany
	private List<PerosnaDesap> personasDesap;
	@OneToOne(mappedBy="catastrofe",cascade= CascadeType.ALL)
	private Plan plan;
	

	public Catastrofe() {
		super();
	}

	public int getCatastrofeId() {
		return catastrofeId;
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

	public Byte[] getLogo() {
		return logo;
	}

	public void setLogo(Byte[] logo) {
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

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	
}
