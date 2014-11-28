package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: PedidoAyuda
 *
 */
@Entity

public class PedidoAyuda implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "pedidoAyudaId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer pedidoAyudaId;
	@Column(name="descripcion")
	private String descripcion;
	@Column(name="fechaHora")
	@Temporal(TemporalType.DATE)
	private Date fechaHora;
	@ManyToOne
	private Usuario usuario;
	@Column(name="longitud")
	private float longitud;
	@Column(name="latitud")
	private float latitud;
	
	

	public PedidoAyuda() {
		super();
	}
	
	public float getLongitud() {
		return longitud;
	}
	
	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}
	
	public float getLatitud() {
		return latitud;
	}
	
	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}
	
	public Integer getPedidoAyudaId() {
		return pedidoAyudaId;
	}


	public void setPedidoAyudaId(Integer pedidoAyudaId) {
		this.pedidoAyudaId = pedidoAyudaId;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Date getFechaHora() {
		return fechaHora;
	}


	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
   
}
