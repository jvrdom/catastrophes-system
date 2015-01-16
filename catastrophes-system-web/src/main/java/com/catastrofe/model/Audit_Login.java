package com.catastrofe.model;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Audit_Login
 *
 */
@Entity
public class Audit_Login implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column
	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Column
	private long usuario_id;
	
	@Column
	private String usuario_rol;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	

	public long getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(long usuario_id) {
		this.usuario_id = usuario_id;
	}

	public String getUsuario_rol() {
		return usuario_rol;
	}

	public void setUsuario_rol(String usuario_rol) {
		this.usuario_rol = usuario_rol;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Audit_Login)) {
			return false;
		}
		Audit_Login other = (Audit_Login) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
