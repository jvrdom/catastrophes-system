package com.example.catastrophes_system_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DonacionActivity extends Activity {
	
	private final String USER_AGENT = "Mozilla/5.0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donacion);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		// Obtengo El Id Del Usuario Logeado
		Globals IdUsuarioLogin = Globals.getInstance();
		int IdUsuario = IdUsuarioLogin.getData();
		
		Button BtnDonacionEconomica = (Button) findViewById(R.id.DonacionEconomica);
		Button BtnDonacionBienes = (Button) findViewById(R.id.DonacionBienes);
		Button BtnDonacionServicios = (Button) findViewById(R.id.DonacionServicios);
		
		BtnDonacionEconomica.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {				
				Intent intent = new Intent(DonacionActivity.this, DonacionEconomicaActivity.class);
				startActivity(intent);
			}
		});
		
		BtnDonacionBienes.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {				
				Intent intent = new Intent(DonacionActivity.this, DonacionBienesActivity.class);
				startActivity(intent);
			}
		});
		
		BtnDonacionServicios.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {				
				Intent intent = new Intent(DonacionActivity.this, DonacionServiciosActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.donacion, menu);
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
