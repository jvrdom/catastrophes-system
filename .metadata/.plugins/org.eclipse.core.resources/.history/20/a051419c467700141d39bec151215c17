package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: ImageCatastrophe
 *
 */
@Entity
public class ImagenCatastrofe implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public ImagenCatastrofe() {
		super();
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int imageId;
   
	private byte[] imagen;

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
	
}
