package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: PerosnaDesap
 *
 */
@Entity
@NamedQueries({
@NamedQuery(name = "PerosnaDesap.findByCatastrofe", query = "SELECT p FROM PerosnaDesap p WHERE p.catastrofe.catastrofeId = :idCatastrofe"),
@NamedQuery(name = "PerosnaDesap.findByName", query = "SELECT p FROM PerosnaDesap p WHERE p.catastrofe.catastrofeId = :idCatastrofe AND p.nombre like :nombre")})
public class PerosnaDesap implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "perosnaDesapId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer perosnaDesapId;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellido")
	private String apellido;	
	@Column(name = "telDeContacto")
	private String telDeContacto;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "status")
	private String status;
	@ManyToOne
	private Usuario reportado;
	@ManyToOne
	private Catastrofe catastrofe;

	public PerosnaDesap() {
		super();
	}
	
	

	public Catastrofe getCatastrofe() {
		return catastrofe;
	}



	public void setCatastrofe(Catastrofe catastrofe) {
		this.catastrofe = catastrofe;
	}



	public Integer getPerosnaDesapId() {
		return perosnaDesapId;
	}

	public void setPerosnaDesapId(Integer perosnaDesapId) {
		this.perosnaDesapId = perosnaDesapId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelDeContacto() {
		return telDeContacto;
	}

	public void setTelDeContacto(String telDeContacto) {
		this.telDeContacto = telDeContacto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Usuario getReportado() {
		return reportado;
	}

	public void setReportado(Usuario reportado) {
		this.reportado = reportado;
	}
	
	
   
}
