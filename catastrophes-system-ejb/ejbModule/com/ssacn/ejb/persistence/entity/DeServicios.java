package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: DeServicios
 *
 */
@Entity
public class DeServicios extends Donacion implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String tipoDeServicio;
	private int horasDedicadas;
	public DeServicios() {
		super();
	}
	public String getTipoDeServicio() {
		return tipoDeServicio;
	}
	public void setTipoDeServicio(String tipoDeServicio) {
		this.tipoDeServicio = tipoDeServicio;
	}
	public int getHorasDedicadas() {
		return horasDedicadas;
	}
	public void setHorasDedicadas(int horasDedicadas) {
		this.horasDedicadas = horasDedicadas;
	}
	
	
   
}
