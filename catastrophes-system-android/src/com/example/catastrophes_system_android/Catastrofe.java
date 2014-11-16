package com.example.catastrophes_system_android;

public class Catastrofe {

	private int IdCatastrofe;
	private String NombreEvento;
	private String Tipo;
	private String Latitud;
	private String Longitud;	
	
	public Catastrofe(int Id, String Nombre, String T, String Lat, String Long) {
		IdCatastrofe = Id;
		Tipo = T;
		NombreEvento = Nombre;		
		Latitud = Lat;
		Longitud = Long;
	}
	
	public int getId() { return IdCatastrofe; }	
	public String getNombre() { return NombreEvento; }
	public String getTipo() { return Tipo; }
	public String getLatitud() { return Latitud; }
	public String getLongitud() { return Longitud; }
	
	public void setId(int Id){ IdCatastrofe = Id; }
	public void setNombre(String Nombre){ NombreEvento = Nombre; }
	public void setTipo(String T){ Tipo = T; }
	public void setLatitud(String Lat){ Latitud = Lat; }	
	public void setLongitud(String Long){ Longitud = Long; }	
}
