package com.example.catastrophes_system_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DonacionEconomicaActivity extends Activity {
	
	private final String USER_AGENT = "Mozilla/5.0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donacion_economica);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		final TextView Monto = (TextView) findViewById(R.id.Monto);
		final TextView TipoTarjeta = (TextView) findViewById(R.id.TipoTarjeta);
		final TextView NumeroTarjeta = (TextView) findViewById(R.id.NumeroTarjeta);		
		Button BtnCancelar = (Button) findViewById(R.id.Cancelar);
		Button BtnRealizar = (Button) findViewById(R.id.Realizar);
		
		BtnCancelar.setOnClickListener(new OnClickListener() {				
			@Override public void onClick(View v) {				
				Intent intent = new Intent(DonacionEconomicaActivity.this, DonacionActivity.class);
				startActivity(intent);
			}
		});
		
		BtnRealizar.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {								
				// Obtengo Los Datos
				Toast mensaje;
				String DonacionMonto = Monto.getText().toString();
				String DonacionTipoTarjeta = TipoTarjeta.getText().toString();
				String DonacionNumeroTarjeta = NumeroTarjeta.getText().toString();
				// Hago Las Validaciones 
				if(DonacionMonto.isEmpty()){ mensaje = Toast.makeText(getApplicationContext(),"Ingresa Un Monto En $",Toast.LENGTH_SHORT); }
				else if(DonacionTipoTarjeta.isEmpty()){	mensaje = Toast.makeText(getApplicationContext(),"Ingresa Un Tipo De Tarjeta",Toast.LENGTH_SHORT);	}
				else if(DonacionNumeroTarjeta.isEmpty()){ mensaje = Toast.makeText(getApplicationContext(),"Ingresa Un Número De Tarjeta",Toast.LENGTH_SHORT); }
				else{ 
					// LLamo A La Operacion Que Ingresa La Donacion Economica
					mensaje = Toast.makeText(getApplicationContext(),"Donación Económica Realizada",Toast.LENGTH_SHORT); 
					Intent intent = new Intent(DonacionEconomicaActivity.this, MenuPrincipalActivity.class);
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
		getMenuInflater().inflate(R.menu.donacion_economica, menu);
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
