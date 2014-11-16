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

public class PedidoAyudaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pedido_ayuda);
		
		// Obtengo El Id Del Usuario Logeado
		Globals IdUsuarioLogin = Globals.getInstance();
		int IdUsuario = IdUsuarioLogin.getData();
		
		final TextView SituacionPersona = (TextView) findViewById(R.id.SituacionPersona);
		Button BtnCancelar = (Button) findViewById(R.id.Cancelar);
		Button BtnIngresar = (Button) findViewById(R.id.Ingresar);
		
		BtnCancelar.setOnClickListener(new OnClickListener() {				
			@Override public void onClick(View v) {				
				Intent intent = new Intent(PedidoAyudaActivity.this, MenuPrincipalActivity.class);
				startActivity(intent);
			}
		});
		
		BtnIngresar.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {								
				// Obtengo Los Datos
				Toast mensaje;
				String PedidoAyudaSituacion = SituacionPersona.getText().toString();
				// Hago Las Validaciones 
				if(PedidoAyudaSituacion.isEmpty()){ mensaje = Toast.makeText(getApplicationContext(),"Ingresa La Situación De La Persona",Toast.LENGTH_SHORT); }
				else{ 
					// LLamo A La Operacion Que Ingresa El Pedido De Ayuda
					mensaje = Toast.makeText(getApplicationContext(),"Pedido De Ayuda Ingresado",Toast.LENGTH_SHORT); 
					Intent intent = new Intent(PedidoAyudaActivity.this, MenuPrincipalActivity.class);
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
		getMenuInflater().inflate(R.menu.pedido_ayuda, menu);
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
