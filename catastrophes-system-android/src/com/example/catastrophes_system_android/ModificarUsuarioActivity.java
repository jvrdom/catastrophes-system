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
import android.app.Dialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.widget.DatePicker;

public class ModificarUsuarioActivity extends Activity implements OnClickListener {
		
	private int Day = 1;
	private int Month = 0;
	private int Year = 1980;
	private ImageButton BtnCalendario;
	private EditText FechaNacimiento;
	private final String USER_AGENT = "Mozilla/5.0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modificar_usuario);
		
		Debug debugging = Debug.getInstance();	
		final String Host = debugging.getDebuggingHost();	
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		// Obtengo El Id Del Usuario Logeado
		Globals IdUsuarioLogin = Globals.getInstance();
		final int IdUsuario = IdUsuarioLogin.getData();	
		
		FechaNacimiento = (EditText) findViewById(R.id.FechaNacimiento);	
		BtnCalendario = (ImageButton) findViewById(R.id.BtnCalendario);					
		BtnCalendario.setOnClickListener(this);		
		
		final TextView Nombre = (TextView) findViewById(R.id.Nombre);
		final TextView Apellido = (TextView) findViewById(R.id.Apellido);
		final TextView Email = (TextView) findViewById(R.id.Email);
		final TextView Telefono = (TextView) findViewById(R.id.Telefono);
		final TextView FechaNacimiento = (TextView) findViewById(R.id.FechaNacimiento);	
		final RadioGroup Sexo = (RadioGroup) findViewById(R.id.Sexo);
		final TextView Usuario = (TextView) findViewById(R.id.Usuario);
		final TextView Password = (TextView) findViewById(R.id.Password);
		Button BtnCancelar = (Button) findViewById(R.id.Cancelar);
		Button BtnModificar = (Button) findViewById(R.id.Modificar);	
		
		// Tengo Que Llamar A La Operacion Que Me Da Los Datos Del Usuario
		// Seteo Los Datos Del Usuario
		Nombre.setText("Mi Nombre");
		Apellido.setText("Mi Apellido");
		Email.setText("Mi Email");
		Telefono.setText("Mi Telefono");
		FechaNacimiento.setText("01 / 01 / 1980");
		Usuario.setText("Mi Usuario");
		Password.setText("Mi Password");		
		
		BtnCancelar.setOnClickListener(new OnClickListener() {				
			@Override public void onClick(View v) {				
				Intent intent = new Intent(ModificarUsuarioActivity.this, MenuPrincipalActivity.class);
				startActivity(intent);
			}
		});
		
		BtnModificar.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {								
				// Obtengo Los Datos
				Toast mensaje;
				String ModNombre = Nombre.getText().toString();
				String ModApellido = Apellido.getText().toString();
				String ModEmail = Email.getText().toString();
				String ModTelefono = Telefono.getText().toString();				
				RadioButton RadioSexo = (RadioButton) findViewById(Sexo.getCheckedRadioButtonId());
				String ModSexo = RadioSexo.getText().toString();	
				String ModFechaNacimiento = FechaNacimiento.getText().toString().replace(" / ","-"); 
				if(ModFechaNacimiento.isEmpty()) ModFechaNacimiento = "01-01-1980";				
				String ModUsuario = Usuario.getText().toString();
				String ModPassword = Password.getText().toString();
				// Hago Las Validaciones 
				if(ModNombre.isEmpty()){ 
					mensaje = Toast.makeText(getApplicationContext(),"Ingresa Tu Nombre",Toast.LENGTH_SHORT); 
					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
					mensaje.show();	
				}
				else if(ModApellido.isEmpty()){	
					mensaje = Toast.makeText(getApplicationContext(),"Ingresa Tu Apellido",Toast.LENGTH_SHORT);	
					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
					mensaje.show();	
				}
				else if(ModEmail.isEmpty()){ 
					mensaje = Toast.makeText(getApplicationContext(),"Ingresa Tu Email",Toast.LENGTH_SHORT);
					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
					mensaje.show();	
				}
				else if(ModTelefono.isEmpty()){ 
					mensaje = Toast.makeText(getApplicationContext(),"Ingresa Tu Telefono",Toast.LENGTH_SHORT);
					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
					mensaje.show();	
				}
				else if(ModUsuario.isEmpty()){ 
					mensaje = Toast.makeText(getApplicationContext(),"Ingresa Un Nombre De Usuario",Toast.LENGTH_SHORT); 
					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
					mensaje.show();	
				}
				else if(ModPassword.isEmpty()){ 
					mensaje = Toast.makeText(getApplicationContext(),"Ingresa Una Contraseña",Toast.LENGTH_SHORT);
					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
					mensaje.show();	
				}
				else{ 					
					// Modifico Al Usuario Mediante El Web Service										
					String URL = "http://"+Host+":8080/catastrophes-system-web/rest/ServicesUsuario/actualizar";
					HttpClient httpClient = new DefaultHttpClient();		
					HttpPost post = new HttpPost(URL);
					post.setHeader("User-Agent", USER_AGENT);
					try {						
						List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
						urlParameters.add(new BasicNameValuePair("idUsuario", Integer.toString(IdUsuario)));
						urlParameters.add(new BasicNameValuePair("nombre", ModNombre));
						urlParameters.add(new BasicNameValuePair("apellido", ModApellido));
						urlParameters.add(new BasicNameValuePair("email", ModEmail));
						urlParameters.add(new BasicNameValuePair("fechaNac", ModFechaNacimiento));
						urlParameters.add(new BasicNameValuePair("sexo", ModSexo));
						urlParameters.add(new BasicNameValuePair("tel", "123456"));
						urlParameters.add(new BasicNameValuePair("pass", ModPassword));
				 		post.setEntity(new UrlEncodedFormEntity(urlParameters));
						HttpResponse resp = httpClient.execute(post);
						
						System.out.println("Request To URL: " + URL);
						System.out.println("Response Code: " + resp.getStatusLine().getStatusCode());
													
						BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
		 				StringBuffer result = new StringBuffer();
		 				String line = "";
		 				while((line=rd.readLine())!=null){
		 					result.append(line);
		 				}
		 				System.out.println(result.toString());
		 				if(result.toString().equals("ok")){
		 					mensaje = Toast.makeText(getApplicationContext(),"Datos Actualizados Correctamente",Toast.LENGTH_SHORT); 
		 					mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 
							mensaje.show();		 					
		 					Intent intent = new Intent(ModificarUsuarioActivity.this, MenuPrincipalActivity.class);
							startActivity(intent);	 						 					
		 				}		 		
		 				else{
		 					mensaje = Toast.makeText(getApplicationContext(),"Hubo Un Problema Al Actualizar",Toast.LENGTH_SHORT); 
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
		getMenuInflater().inflate(R.menu.registro_usuario, menu);
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
	
	@Override public void onClick(View v) {
	  showDialog(0);
	}

	@Override
	@Deprecated  protected Dialog onCreateDialog(int id) {
	  return new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog_NoActionBar, datePickerListener, Year, Month, Day);
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
	    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
	       	String StrDay = Integer.toString(selectedDay);
	    	String StrMonth = Integer.toString(selectedMonth + 1);
	    	String StrYear = Integer.toString(selectedYear);	    	
	    	if(selectedDay<10) StrDay = "0"+StrDay;	    
	    	if(selectedMonth<9) StrMonth = "0"+StrMonth;		    	
	    	FechaNacimiento.setText(StrDay + " / " + StrMonth + " / " + StrYear);
	    }
	};		
}
