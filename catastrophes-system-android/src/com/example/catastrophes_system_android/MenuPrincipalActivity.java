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
import android.widget.Toast;

public class MenuPrincipalActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_principal);
		
		// Obtengo El Id Del Usuario Logeado Si Vengo Del Login
		Intent intent = this.getIntent();		
		if (intent.hasExtra("Usuario")){
			Bundle bundle = this.getIntent().getExtras();
			final int IdUsuario = bundle.getInt("IdUsuario");
			// Seteo El Id En Una Variable Global
			Globals IdUsuarioLogin = Globals.getInstance();
			IdUsuarioLogin.setData(IdUsuario);
			Toast mensaje;		
			mensaje = Toast.makeText(getApplicationContext(),"Id Usuario: "+IdUsuario,Toast.LENGTH_SHORT); 				
			mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
			mensaje.show();		
		}
		
		Button BtnMenu01 = (Button) findViewById(R.id.Menu01);
		Button BtnMenu02 = (Button) findViewById(R.id.Menu02);
		Button BtnMenu03 = (Button) findViewById(R.id.Menu03);
		Button BtnMenu04 = (Button) findViewById(R.id.Menu04);
		Button BtnMenu05 = (Button) findViewById(R.id.Menu05);
		Button BtnMenu06 = (Button) findViewById(R.id.Menu06);
		Button BtnMenu07 = (Button) findViewById(R.id.Menu07);
		Button BtnMenu08 = (Button) findViewById(R.id.Menu08);
		
		BtnMenu01.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {
				Intent intent = new Intent(MenuPrincipalActivity.this, MapaLocalidadesAfectadasActivity.class);
				startActivity(intent);			
			}
		});
		
		BtnMenu02.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {				
				Intent intent = new Intent(MenuPrincipalActivity.this, BusquedaPersonasDesaparecidasActivity.class);
				startActivity(intent);
			}
		});
		
		BtnMenu03.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {				
				Intent intent = new Intent(MenuPrincipalActivity.this, ReportePersonasDesaparecidasActivity.class);
				startActivity(intent);
			}
		});
		
		BtnMenu04.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {				
				Intent intent = new Intent(MenuPrincipalActivity.this, PedidoAyudaActivity.class);
				startActivity(intent);
			}
		});
		
		BtnMenu05.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {				
				Intent intent = new Intent(MenuPrincipalActivity.this, DonacionActivity.class);
				startActivity(intent);
			}
		});
		
		BtnMenu06.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {				
				Intent intent = new Intent(MenuPrincipalActivity.this, NovedadesActivity.class);
				startActivity(intent);
			}
		});
		
		BtnMenu07.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {				
				Intent intent = new Intent(MenuPrincipalActivity.this, ModificarUsuarioActivity.class);
				startActivity(intent);
			}
		});
		
		BtnMenu08.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {				
				Intent intent = new Intent(MenuPrincipalActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_principal, menu);
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
