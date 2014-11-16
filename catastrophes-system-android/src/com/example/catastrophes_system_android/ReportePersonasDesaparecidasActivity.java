package com.example.catastrophes_system_android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ReportePersonasDesaparecidasActivity extends Activity {
	
	private List<Persona> ListaPersonas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reporte_personas_desaparecidas);
		
		// Obtengo El Id Del Usuario Logeado
		Globals IdUsuarioLogin = Globals.getInstance();
		int IdUsuario = IdUsuarioLogin.getData();
		
		Persona persona1 = new Persona(1,"Persona Desaparecida 1","persona"); 
		Persona persona2 = new Persona(2,"Persona Desaparecida 2","persona"); 
		Persona persona3 = new Persona(3,"Persona Desaparecida 3","persona"); 
		Persona persona4 = new Persona(4,"Persona Desaparecida 4","persona"); 
		Persona persona5 = new Persona(5,"Persona Desaparecida 5","persona"); 
		Persona persona6 = new Persona(6,"Persona Desaparecida 6","persona"); 
		Persona persona7 = new Persona(5,"Persona Desaparecida 7","persona"); 
		Persona persona8 = new Persona(6,"Persona Desaparecida 8","persona"); 
		Persona persona9 = new Persona(6,"Persona Desaparecida 9","persona"); 
		Persona persona10 = new Persona(6,"Persona Desaparecida 10","persona"); 
		ListaPersonas = new ArrayList<Persona>(); 
		ListaPersonas.add(persona1);		
		ListaPersonas.add(persona2);
		ListaPersonas.add(persona3);
		ListaPersonas.add(persona4);
		ListaPersonas.add(persona5);
		ListaPersonas.add(persona6);	
		ListaPersonas.add(persona7);
		ListaPersonas.add(persona8);
		ListaPersonas.add(persona9);
		ListaPersonas.add(persona10);
		ListView ListaPersonasDesaparecidas = (ListView) findViewById(R.id.ListaPersonasDesaparecidas);
		ListAdapter Adaptador = new AdaptadorPersonaList(this, ListaPersonas);
		ListaPersonasDesaparecidas.setAdapter(Adaptador);
		ListaPersonasDesaparecidas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				
		ListaPersonasDesaparecidas.setOnItemClickListener(new OnItemClickListener() {
		    
			@Override public void onItemClick(AdapterView<?> a, View v, int position, long id) {		       
				Persona PersonaSeleccionada = ((Persona) a.getAdapter().getItem(position));
				int IdPersona = PersonaSeleccionada.getId();					
				Toast mensaje = Toast.makeText(getApplicationContext(),"Persona Desaparecida Seleccionada: "+IdPersona,Toast.LENGTH_SHORT);
				mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
				mensaje.show();					
				Bundle Persona = new Bundle();                                   
				Persona.putInt("IdPersona",IdPersona);		   
				Intent intent = new Intent(ReportePersonasDesaparecidasActivity.this, DetallePersonaDesaparecidaActivity.class);	
				intent.putExtras(Persona); 
			    startActivity(intent); 			    
		    }
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reporte_personas_desaparecidas, menu);
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
