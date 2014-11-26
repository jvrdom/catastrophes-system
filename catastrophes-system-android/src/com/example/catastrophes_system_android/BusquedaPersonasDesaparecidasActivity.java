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

public class BusquedaPersonasDesaparecidasActivity extends Activity {
	
	private final String USER_AGENT = "Mozilla/5.0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busqueda_personas_desaparecidas);
		
		Debug debugging = Debug.getInstance();	
		final String Host = debugging.getDebuggingHost();	
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		// Obtengo El Id Y Nombre De La Catastrofe Seleccionada
		Bundle bundle = this.getIntent().getExtras();
		final int IdCatastrofe = bundle.getInt("IdCatastrofe");
		final String NombreCatastrofe = bundle.getString("NombreCatastrofe");		
						
		// Obtengo El Id Del Usuario Logeado
		Globals IdUsuarioLogin = Globals.getInstance();
		final int IdUsuario = IdUsuarioLogin.getData();	
		
		final TextView NombrePersona = (TextView) findViewById(R.id.NombrePersona);
		final TextView ApellidoPersona = (TextView) findViewById(R.id.ApellidoPersona);
		final TextView TelefonoPersona = (TextView) findViewById(R.id.TelefonoPersona);
		final TextView DescripcionPersona = (TextView) findViewById(R.id.DescripcionPersona);
		final TextView ImagenPersona = (TextView) findViewById(R.id.ImagenPersona);		
		Button BtnCancelar = (Button) findViewById(R.id.Cancelar);
		Button BtnIngresar = (Button) findViewById(R.id.Ingresar);
		
		BtnCancelar.setOnClickListener(new OnClickListener() {				
			@Override public void onClick(View v) {				
				Intent intent = new Intent(BusquedaPersonasDesaparecidasActivity.this, MenuPrincipalActivity.class);
				startActivity(intent);
			}
		});
		
		BtnIngresar.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {								
				// Obtengo Los Datos
				Toast mensaje;
				String StrNombrePersona = NombrePersona.getText().toString();
				String StrApellidoPersona = ApellidoPersona.getText().toString();
				String StrTelefonoPersona = TelefonoPersona.getText().toString();
				String StrDescripcionPersona = DescripcionPersona.getText().toString();
				String StrImagenPersona = ImagenPersona.getText().toString();
				// Hago Las Validaciones 
				if(StrNombrePersona.isEmpty()){ 
					mensaje = Toast.makeText(getApplicationContext(),"Ingresa El Nombre De La Persona",Toast.LENGTH_SHORT); 
					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
					mensaje.show();
				}
				else if(StrApellidoPersona.isEmpty()){ 
					mensaje = Toast.makeText(getApplicationContext(),"Ingresa El Apellido De La Persona",Toast.LENGTH_SHORT); 
					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
					mensaje.show();
				}
				else if(StrTelefonoPersona.isEmpty()){ 
					mensaje = Toast.makeText(getApplicationContext(),"Ingresa El Teléfono De La Persona",Toast.LENGTH_SHORT); 
					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
					mensaje.show();
				}
				else if(StrDescripcionPersona.isEmpty()){ 
					mensaje = Toast.makeText(getApplicationContext(),"Ingresa Una Descripción De La Persona",Toast.LENGTH_SHORT);
					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
					mensaje.show();
				}
				else{ 
					// Ingreso A La Persona Desaparecida Mediante El Web Service					
					String URL = "http://"+Host+":8080/catastrophes-system-web/rest/ServicesPersDesap/ingreso";
					HttpClient httpClient = new DefaultHttpClient();		
					HttpPost post = new HttpPost(URL);
					post.setHeader("User-Agent", USER_AGENT);					
					try {
						List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();						
						urlParameters.add(new BasicNameValuePair("nombre", StrNombrePersona));						
						urlParameters.add(new BasicNameValuePair("apellido", StrApellidoPersona));
						urlParameters.add(new BasicNameValuePair("telefono", StrTelefonoPersona));
						urlParameters.add(new BasicNameValuePair("descripcion", StrDescripcionPersona));
						urlParameters.add(new BasicNameValuePair("status", "Desaparecida"));
						urlParameters.add(new BasicNameValuePair("idUsuario", Integer.toString(IdUsuario)));
						urlParameters.add(new BasicNameValuePair("idCatastrofe", Integer.toString(IdCatastrofe)));						
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
							// Ingreso Correcto
							mensaje = Toast.makeText(getApplicationContext(),"Persona Desaparecida Ingresada",Toast.LENGTH_SHORT); 
							mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 
							mensaje.show();
							Intent intent = new Intent(BusquedaPersonasDesaparecidasActivity.this, MenuPrincipalActivity.class);
							startActivity(intent);	 					
						}		 		
		 				else{
		 					// Ingreso Incorrecto
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.busqueda_personas_desaparecidas, menu);
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
