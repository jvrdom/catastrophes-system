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

public class DonacionBienesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donacion_bienes);
		
		final TextView Descripcion = (TextView) findViewById(R.id.Descripcion);
		Button BtnCancelar = (Button) findViewById(R.id.Cancelar);
		Button BtnRealizar = (Button) findViewById(R.id.Realizar);
		
		BtnCancelar.setOnClickListener(new OnClickListener() {				
			@Override public void onClick(View v) {				
				Intent intent = new Intent(DonacionBienesActivity.this, DonacionActivity.class);
				startActivity(intent);
			}
		});
		
		BtnRealizar.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {								
				// Obtengo Los Datos
				Toast mensaje;
				String DonacionDescripcion = Descripcion.getText().toString();
				// Hago Las Validaciones 
				if(DonacionDescripcion.isEmpty()){ mensaje = Toast.makeText(getApplicationContext(),"Ingresa Una Descripción Del Bien A Donar",Toast.LENGTH_SHORT); }
				else{ 
					// LLamo A La Operacion Que Ingresa La Donacion De Bienes
					mensaje = Toast.makeText(getApplicationContext(),"Donación De Bienes Realizada",Toast.LENGTH_SHORT); 
					Intent intent = new Intent(DonacionBienesActivity.this, MenuPrincipalActivity.class);
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
		getMenuInflater().inflate(R.menu.donacion_bienes, menu);
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
