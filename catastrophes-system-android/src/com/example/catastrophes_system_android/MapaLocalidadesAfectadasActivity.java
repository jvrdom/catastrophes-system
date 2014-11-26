package com.example.catastrophes_system_android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MapaLocalidadesAfectadasActivity extends android.support.v4.app.FragmentActivity {

	private final String USER_AGENT = "Mozilla/5.0";
	private List<Catastrofe> ListaCatastrofe;
	private Marker marker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa_localidades_afectadas);
		
		Debug debugging = Debug.getInstance();	
		final String Host = debugging.getDebuggingHost();								
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	
		// Obtengo El Id Del Usuario Logeado
		Globals IdUsuarioLogin = Globals.getInstance();
		int IdUsuario = IdUsuarioLogin.getData();
				
		ListaCatastrofe = new ArrayList<Catastrofe>(); 
		// Obtengo Las Catastrofes Mediante El Web Service
		String URL = "http://"+Host+":8080/catastrophes-system-web/rest/ServicesCatastrofe/list";
		HttpClient httpClient = new DefaultHttpClient();		
		HttpPost post = new HttpPost(URL);
		post.setHeader("User-Agent", USER_AGENT);					
		try {			
			HttpResponse resp = httpClient.execute(post);
			
			System.out.println("Request To URL: " + URL);
			System.out.println("Response Code: " + resp.getStatusLine().getStatusCode());
			
			BufferedReader rd = new BufferedReader(
            new InputStreamReader(resp.getEntity().getContent()));
 
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);				
			}			 			
			
			// Recorro Todo El Array Y Ingreso Cada Catastrofe En La Lista
			JSONArray ArrayCatastrofres = new JSONArray(result.toString());
			int QtyCatastrofes = ArrayCatastrofres.length();
			String CatastrofeTipo;
			for (int i=0; i<QtyCatastrofes; i++) {
                JSONObject jsonobject = ArrayCatastrofres.getJSONObject(i);
                int CatastrofeId = jsonobject.getInt("catastrofeId");
                String CatastrofeNombre = jsonobject.getString("nombre");
                String CatastrofeLatitud = jsonobject.getString("coordX");
                String CatastrofeLongitud = jsonobject.getString("coordY");
                String CatastrofeLogo = jsonobject.getString("logo");
                if(CatastrofeNombre.contains("Incendio")) CatastrofeTipo = "incendio";
                else if(CatastrofeNombre.contains("Inundacion")) CatastrofeTipo = "inundacion";
                else CatastrofeTipo = "sudestada";
                System.out.println("Catastrofe: " + CatastrofeId + " - " + CatastrofeNombre + " - " + CatastrofeLogo);
                Catastrofe Catas = new Catastrofe(CatastrofeId,CatastrofeNombre,CatastrofeTipo,CatastrofeLatitud,CatastrofeLongitud,CatastrofeLogo); 
                ListaCatastrofe.add(Catas);	
           }																        				        					        
		}
		catch(Exception ex)	{ Log.e("ServicioRest","Error!", ex); }	
				
		// Obtengo El Mapa Y Agrego Las Marcas De Cada Catastrofe
		final GoogleMap Mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Mapa)).getMap();
		int QtyCatastrofes= ListaCatastrofe.size();		
		for(int i=0; i<QtyCatastrofes; i++){          
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
	
	public float obtenerColor(String Tipo){		
		float color = 0;
		if(Tipo.equals("incendio")) color = BitmapDescriptorFactory.HUE_RED;
		else if(Tipo.equals("inundacion")) color = BitmapDescriptorFactory.HUE_BLUE;
		else color = BitmapDescriptorFactory.HUE_GREEN;
        return color;		
	}
}
