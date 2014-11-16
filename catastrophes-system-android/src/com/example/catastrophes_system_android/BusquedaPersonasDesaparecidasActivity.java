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

public class BusquedaPersonasDesaparecidasActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busqueda_personas_desaparecidas);
		
		// Obtengo El Id Del Usuario Logeado
		Globals IdUsuarioLogin = Globals.getInstance();
		int IdUsuario = IdUsuarioLogin.getData();	
		
		final TextView NombrePersona = (TextView) findViewById(R.id.NombrePersona);
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
				String StrDescripcionPersona = DescripcionPersona.getText().toString();
				String StrImagenPersona = ImagenPersona.getText().toString();
				// Hago Las Validaciones 
				if(StrNombrePersona.isEmpty()){ mensaje = Toast.makeText(getApplicationContext(),"Ingresa El Nombre De La Persona",Toast.LENGTH_SHORT); }
				else if(StrDescripcionPersona.isEmpty()){ mensaje = Toast.makeText(getApplicationContext(),"Ingresa Una Descripción De La Persona",Toast.LENGTH_SHORT);	}
				else{ 
					// LLamo A La Operacion Que Ingresa La Persona Desaparecida
					mensaje = Toast.makeText(getApplicationContext(),"Persona Desaparecida Ingresada",Toast.LENGTH_SHORT); 
					Intent intent = new Intent(BusquedaPersonasDesaparecidasActivity.this, MenuPrincipalActivity.class);
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
