package com.example.catastrophes_system_android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.StrictMode;

public class LoginActivity extends Activity {
	
	private final String USER_AGENT = "Mozilla/5.0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Debug debugging = Debug.getInstance();	
		final String Host = debugging.getDebuggingHost();								
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
				
		final TextView Usuario = (TextView) findViewById(R.id.Usuario);
		final TextView Password = (TextView) findViewById(R.id.Password);		
		Button BtnCancelar = (Button) findViewById(R.id.Cancelar);
		Button BtnIngresar = (Button) findViewById(R.id.Ingresar);			
			
		BtnCancelar.setOnClickListener(new OnClickListener() {				
			@Override public void onClick(View v) {				
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
		
		BtnIngresar.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {	
				// Obtengo Los Datos
				Toast mensaje;
				String LoginUsuario = Usuario.getText().toString();
				String LoginPassword = Password.getText().toString();
				// Hago Las Validaciones
				if(LoginUsuario.isEmpty()){ 
					mensaje = Toast.makeText(getApplicationContext(),"Ingresa Tu Nombre De Usuario",Toast.LENGTH_SHORT); 
					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 
					mensaje.show();
				}
				else if(LoginPassword.isEmpty()){ 
					mensaje = Toast.makeText(getApplicationContext(),"Ingresa Tu Contraseña",Toast.LENGTH_SHORT); 
					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 
					mensaje.show();
				}
				else{ 
					// Chekeo Si El Usuario Existe Mediante El Web Service
					String URL = "http://"+Host+":8080/catastrophes-system-web/rest/ServicesUsuario/login";
					HttpClient httpClient = new DefaultHttpClient();		
					HttpPost post = new HttpPost(URL);
					post.setHeader("User-Agent", USER_AGENT);					
					try {
						List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();						
						urlParameters.add(new BasicNameValuePair("email", LoginUsuario));						
						urlParameters.add(new BasicNameValuePair("pass", LoginPassword));
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
						if(result.toString().equals("0")){
							// Login Incorrecto
		 					mensaje = Toast.makeText(getApplicationContext(),"Email O Password Incorrectos",Toast.LENGTH_SHORT); 
		 					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 
							mensaje.show();
						}		 		
		 				else{
		 					// Login Correcto
		 					mensaje = Toast.makeText(getApplicationContext(),"Login Correcto",Toast.LENGTH_SHORT); 
							mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
							mensaje.show();
		 					int IdUsuario = Integer.parseInt(result.toString());
		 					Intent intent = new Intent(LoginActivity.this, MenuPrincipalActivity.class);					
							Bundle Usuario = new Bundle();                                   
							Usuario.putInt("IdUsuario",IdUsuario);		
							intent.putExtras(Usuario); 
							startActivity(intent);		 					
		 				}											        				        					        
					}
					catch(Exception ex)	{ Log.e("ServicioRest","Error!", ex); }															
				}											
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
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
