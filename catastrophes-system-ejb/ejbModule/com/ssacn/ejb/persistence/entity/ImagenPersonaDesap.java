package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ImagenPersonaDesap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ImagenPersonaDesap() {

	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int imageId;
   
	private String imagen;

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
}
