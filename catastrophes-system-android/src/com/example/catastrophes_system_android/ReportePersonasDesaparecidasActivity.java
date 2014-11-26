package com.example.catastrophes_system_android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
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
	
	private final String USER_AGENT = "Mozilla/5.0";
	private List<Persona> ListaPersonas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reporte_personas_desaparecidas);
		
		Debug debugging = Debug.getInstance();	
		final String Host = debugging.getDebuggingHost();	
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		// Obtengo El Id Y Nombre De La Catastrofe Seleccionada
		Bundle bundle = this.getIntent().getExtras();
		final int IdCatastrofe = bundle.getInt("IdCatastrofe");
		final String NombreCatastrofe = bundle.getString("NombreCatastrofe");
		
		// Obtengo El Id Del Usuario Logeado
		Globals IdUsuarioLogin = Globals.getInstance();
		int IdUsuario = IdUsuarioLogin.getData();
		
		ListaPersonas = new ArrayList<Persona>();
		// Obtengo Las Personas Desaparecidas Mediante El Web Service
		String URL = "http://"+Host+":8080/catastrophes-system-web/rest/ServicesPersDesap/list";
		URL += "/"+IdCatastrofe;
		HttpClient httpClient = new DefaultHttpClient();		
		HttpGet get = new HttpGet(URL);
		get.setHeader("User-Agent", USER_AGENT);					
		try {			
			HttpResponse resp = httpClient.execute(get);
			
			System.out.println("Request To URL: " + URL);
			System.out.println("Response Code: " + resp.getStatusLine().getStatusCode());
			
			BufferedReader rd = new BufferedReader(
            new InputStreamReader(resp.getEntity().getContent()));
 
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);				
			}			 			
			System.out.println(result.toString());
			
			// Recorro Todo El Array Y Ingreso Cada Persona Desaparecida En La Lista
			JSONArray ArrayPersonasDesaparecidas = new JSONArray(result.toString());
			int QtyPersonas = ArrayPersonasDesaparecidas.length();	
			System.out.println(QtyPersonas);
			for (int i=0; i<QtyPersonas; i++) {
                //JSONObject jsonobject = ArrayPersonasDesaparecidas.getJSONObject(i);
               /* int CatastrofeId = jsonobject.getInt("catastrofeId");
                String CatastrofeNombre = jsonobject.getString("nombre");
                String CatastrofeLatitud = jsonobject.getString("coordX");
                String CatastrofeLongitud = jsonobject.getString("coordY");
                String CatastrofeLogo = jsonobject.getString("logo");
               
                System.out.println("Catastrofe: " + CatastrofeId + " - " + CatastrofeNombre + " - " + CatastrofeLogo);
                Catastrofe Catas = new Catastrofe(CatastrofeId,CatastrofeNombre,CatastrofeTipo,CatastrofeLatitud,CatastrofeLongitud,CatastrofeLogo); 
                ListaPersonas.add(Catas);	*/
           }																        				        					        
		}
		catch(Exception ex)	{ Log.e("ServicioRest","Error!", ex); }	
			
		
		Persona persona1 = new Persona(1,"Persona Desaparecida 1","persona"); 
		Persona persona2 = new Persona(2,"Persona Desaparecida 2","persona"); 
		Persona persona3 = new Persona(3,"Persona Desaparecida 3","persona"); 
			
		ListaPersonas.add(persona1);		
		ListaPersonas.add(persona2);
		ListaPersonas.add(persona3);
		
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
