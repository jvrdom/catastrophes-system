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

public class DonacionServiciosActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donacion_servicios);
		
		final TextView TipoServicios = (TextView) findViewById(R.id.TipoServicios);
		final TextView HorasDisponibles = (TextView) findViewById(R.id.HorasDisponibles);
		Button BtnCancelar = (Button) findViewById(R.id.Cancelar);
		Button BtnRealizar = (Button) findViewById(R.id.Realizar);
		
		BtnCancelar.setOnClickListener(new OnClickListener() {				
			@Override public void onClick(View v) {				
				Intent intent = new Intent(DonacionServiciosActivity.this, DonacionActivity.class);
				startActivity(intent);
			}
		});
		
		BtnRealizar.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {								
				// Obtengo Los Datos
				Toast mensaje;
				String DonacionTipoServicios = TipoServicios.getText().toString();
				String DonacionHorasDisponibles = HorasDisponibles.getText().toString();
				// Hago Las Validaciones 
				if(DonacionTipoServicios.isEmpty()){ mensaje = Toast.makeText(getApplicationContext(),"Ingresa El Tipo De Servicio Que Realizas",Toast.LENGTH_SHORT); }
				else if(DonacionHorasDisponibles.isEmpty()){ mensaje = Toast.makeText(getApplicationContext(),"Ingresa La Cantidad De Horas Disponibles",Toast.LENGTH_SHORT); }
				else{ 
					// LLamo A La Operacion Que Ingresa La Donacion De Servicios
					mensaje = Toast.makeText(getApplicationContext(),"Donación De Servicios Realizada",Toast.LENGTH_SHORT); 
					Intent intent = new Intent(DonacionServiciosActivity.this, MenuPrincipalActivity.class);
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
		getMenuInflater().inflate(R.menu.donacion_servicios, menu);
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
