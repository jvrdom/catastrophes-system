package com.example.catastrophes_system_android;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/*
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
*/
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MapaLocalidadesAfectadasActivity extends android.support.v4.app.FragmentActivity {

	private List<Catastrofe> ListaCatastrofe;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa_localidades_afectadas);
		
		// Obtengo El Id Del Usuario Logeado
		Globals IdUsuarioLogin = Globals.getInstance();
		int IdUsuario = IdUsuarioLogin.getData();
		
		// Obtengo Todas Las Catastrofes
		Catastrofe Catastrofe1 = new Catastrofe(1,"Incendio 1","Incendio","-34.8946978081253","-56.15354106270444"); 
		Catastrofe Catastrofe2 = new Catastrofe(2,"Incendio 2","Incendio","-34.89783049251236","-56.155922864309666"); 
		Catastrofe Catastrofe3 = new Catastrofe(3,"Incendio 3","Incendio","-34.91982631492827","-56.16977379166257"); 
		Catastrofe Catastrofe4 = new Catastrofe(4,"Incendio 4","Incendio","-34.90428871107998","-56.19888985"); 
		Catastrofe Catastrofe5 = new Catastrofe(5,"Inundacion 1","Inundacion","-34.89835476480354","-56.17839124764253"); 
		Catastrofe Catastrofe6 = new Catastrofe(6,"Inundacion 2","Inundacion","-34.896240810114165","-56.1738169963104"); 
		Catastrofe Catastrofe7 = new Catastrofe(7,"Inundacion 3","Inundacion","-34.88835080364929","-55.04187525653378"); 
		Catastrofe Catastrofe8 = new Catastrofe(8,"Inundacion 4","Inundacion","-34.59179457321143","-54.12526086473464"); 
		ListaCatastrofe = new ArrayList<Catastrofe>(); 
		ListaCatastrofe.add(Catastrofe1);		
		ListaCatastrofe.add(Catastrofe2);
		ListaCatastrofe.add(Catastrofe3);
		ListaCatastrofe.add(Catastrofe4);
		ListaCatastrofe.add(Catastrofe5);		
		ListaCatastrofe.add(Catastrofe6);
		ListaCatastrofe.add(Catastrofe7);
		ListaCatastrofe.add(Catastrofe8);
		/*
		// Obtengo El Mapa Y Agrego Las Marcas De Cada Catastrofe
		final GoogleMap Mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Mapa)).getMap();
		int Cant_Catastrofes= ListaCatastrofe.size();		
		for(int i=0; i<Cant_Catastrofes; i++){          
			Catastrofe Cat = ListaCatastrofe.get(i);
             String CatastrofeNombre = Cat.getNombre();   
             String CatastrofeTipo = Cat.getTipo();  
             String CatastrofeLatitud = Cat.getLatitud();
     		 String CatastrofeLongitud = Cat.getLongitud();  
     		 float CatastrofeColor = obtenerColor(CatastrofeTipo);	     		 
     		 LatLng LatLong = new LatLng(Double.parseDouble(CatastrofeLatitud),Double.parseDouble(CatastrofeLongitud));
     		 Mapa.addMarker(new MarkerOptions().position(LatLong).title(CatastrofeNombre).icon(BitmapDescriptorFactory.defaultMarker(CatastrofeColor)));	
     	}				
		Mapa.setMyLocationEnabled(true);		
		LatLng CentroMontevideo = new LatLng(-34.9055174447796,-56.18524793205254);
		CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(CentroMontevideo,13F);	
		Mapa.moveCamera(camera); 
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mapa_localidades_afectadas, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	/*
	public float obtenerColor(String Tipo){		
		float color = 0;
		if(Tipo.equals("Incendio")) color = BitmapDescriptorFactory.HUE_YELLOW;
		else if(Tipo.equals("Inundacion")) color = BitmapDescriptorFactory.HUE_BLUE;
		else color = BitmapDescriptorFactory.HUE_GREEN;
        return color;		
	}*/
}
