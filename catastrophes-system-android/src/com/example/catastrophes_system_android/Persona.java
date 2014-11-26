package com.example.catastrophes_system_android;

public class Persona {

	private int IdPersona;
	private String NombrePersona;
	private String ImagenPersona;
	
	public Persona(int Id, String Nombre, String Imagen) {
		IdPersona = Id;
		NombrePersona = Nombre;		
		ImagenPersona = Imagen;
	}
	
	public int getId() { return IdPersona; }	
	public String getNombre() { return NombrePersona; }
	public String getImagen() { return ImagenPersona; }
	
	public void setId(int Id){ IdPersona = Id; }
	public void setNombre(String Nombre){ NombrePersona = Nombre; }
	public void setImagen(String Imagen){ ImagenPersona = Imagen; }	
	
}
