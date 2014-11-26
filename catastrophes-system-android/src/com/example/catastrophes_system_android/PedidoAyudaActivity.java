package com.example.catastrophes_system_android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PedidoAyudaActivity extends android.support.v4.app.FragmentActivity {
	
	private final String USER_AGENT = "Mozilla/5.0";
	private Marker marker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pedido_ayuda);
		
		Debug debugging = Debug.getInstance();	
		final String Host = debugging.getDebuggingHost();								
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		// Obtengo El Id Del Usuario Logeado
		Globals IdUsuarioLogin = Globals.getInstance();
		final int IdUsuario = IdUsuarioLogin.getData();
		
		final GoogleMap Mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Mapa)).getMap();
		final TextView MarcaLatitud = (TextView) findViewById(R.id.MarcaLatitud);
		final TextView MarcaLongitud = (TextView) findViewById(R.id.MarcaLongitud);
		final TextView SituacionPersona = (TextView) findViewById(R.id.SituacionPersona);
		Button BtnCancelar = (Button) findViewById(R.id.Cancelar);
		Button BtnIngresar = (Button) findViewById(R.id.Ingresar);
		
		Mapa.setMyLocationEnabled(true);		
		LatLng CentroMontevideo = new LatLng(-34.9055174447796,-56.18524793205254);
		CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(CentroMontevideo,15F);	
		Mapa.moveCamera(camera); 
		
		// Agrego La Marca De La Ubicacion Del Pedido De Ayuda
		Mapa.setOnMapClickListener(new OnMapClickListener() {
	        @Override
	        public void onMapClick(LatLng point) {	        	
	        	if (marker!=null) { marker.remove(); }	        	
	            double Latitud = point.latitude;
	            double Longitud = point.longitude;	            
	            Latitud = (double)Math.round(Latitud * 1000000000) / 1000000000;
	            Longitud = (double)Math.round(Longitud * 1000000000) / 1000000000;
	            String StrLatitud = Double.toString(Latitud);
	            String StrLongitud = Double.toString(Longitud);
	            MarcaLatitud.setText(StrLatitud);
	            MarcaLongitud.setText(StrLongitud);	                     
	            marker = Mapa.addMarker(new MarkerOptions().position(point).title("Ubicacion").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));         
	        }
		});
		
		BtnCancelar.setOnClickListener(new OnClickListener() {				
			@Override public void onClick(View v) {				
				Intent intent = new Intent(PedidoAyudaActivity.this, MenuPrincipalActivity.class);
				startActivity(intent);
			}
		});
		
		BtnIngresar.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {								
				// Obtengo Los Datos
				Toast mensaje;			
				String StrMarcaLatitud = MarcaLatitud.getText().toString();
				String StrMarcaLongitud = MarcaLongitud.getText().toString();
				String PedidoAyudaSituacion = SituacionPersona.getText().toString();
				// Hago Las Validaciones 
				if(StrMarcaLatitud.isEmpty()){ 
					mensaje = Toast.makeText(getApplicationContext(),"Marca Un Punto Del Mapa",Toast.LENGTH_SHORT); 
					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
					mensaje.show();		
				}
				else if(PedidoAyudaSituacion.isEmpty()){ 
					mensaje = Toast.makeText(getApplicationContext(),"Ingresa La Situación De La Persona",Toast.LENGTH_SHORT); 
					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
					mensaje.show();
				}
				else{ 
					// Ingreso El Pedido De Ayuda Mediante El Web Service
					String URL = "http://"+Host+":8080/catastrophes-system-web/rest/ServicePedidoDeAyuda/alta";
					HttpClient httpClient = new DefaultHttpClient();		
					HttpPost post = new HttpPost(URL);
					post.setHeader("User-Agent", USER_AGENT);					
					try {						
						List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();						
						urlParameters.add(new BasicNameValuePair("latitud", StrMarcaLatitud));						
						urlParameters.add(new BasicNameValuePair("longitud", StrMarcaLongitud));
						urlParameters.add(new BasicNameValuePair("descrpcion", PedidoAyudaSituacion));
						urlParameters.add(new BasicNameValuePair("idUsuario", Integer.toString(IdUsuario)));
						post.setEntity(new UrlEncodedFormEntity(urlParameters));
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
						System.out.println(result.toString());
						if(result.toString().equals("ok")){
							mensaje = Toast.makeText(getApplicationContext(),"Pedido De Ayuda Ingresado",Toast.LENGTH_SHORT); 
							mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
							mensaje.show();
							Intent intent = new Intent(PedidoAyudaActivity.this, MenuPrincipalActivity.class);
							startActivity(intent);
						}		 		
		 				else{		 				
		 					mensaje = Toast.makeText(getApplicationContext(),"Hubo Un Error Al Ingresar",Toast.LENGTH_SHORT); 
							mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
							mensaje.show();		 							 					
		 				}											        				        					        
					}
					catch(Exception ex)	{ Log.e("ServicioRest","Error!", ex); }					
				}							
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pedido_ayuda, menu);
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
}
