package com.example.catastrophes_system_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
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
				if(LoginUsuario.isEmpty()){ mensaje = Toast.makeText(getApplicationContext(),"Ingresa Tu Nombre De Usuario",Toast.LENGTH_SHORT); }
				else if(LoginPassword.isEmpty()){ mensaje = Toast.makeText(getApplicationContext(),"Ingresa Tu Contraseña",Toast.LENGTH_SHORT); }
				else{ 
					// LLamo A La Operacion Que Chekea Si El Usuario Existe				
					mensaje = Toast.makeText(getApplicationContext(),"Login Correcto",Toast.LENGTH_SHORT); 
					Intent intent = new Intent(LoginActivity.this, MenuPrincipalActivity.class);
					// Si El Usuario Existe Obtengo Su Id	
					int IdUsuario = 1;
					Bundle Usuario = new Bundle();                                   
					Usuario.putInt("IdUsuario",IdUsuario);		
					intent.putExtras(Usuario); 
					startActivity(intent);
				}
				mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
				mensaje.show();							
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
