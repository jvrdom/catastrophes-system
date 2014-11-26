package com.example.catastrophes_system_android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.StrictMode;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ListadoCatastrofesActivity extends Activity {
	
	private final String USER_AGENT = "Mozilla/5.0";
	private List<Catastrofe> ListaCatastrofe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listado_catastrofes);
		
		// Obtengo La Seccion Que Ingreso El Usuario
		Bundle bundle = this.getIntent().getExtras();
		final String Seccion = bundle.getString("Seccion");			
						
		Debug debugging = Debug.getInstance();	
		final String Host = debugging.getDebuggingHost();								
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);	
		
		if(Seccion.equals("BusquedaPersonasDesaparecidas")){
			final TextView SubTituloCatastrofes = (TextView) findViewById(R.id.SubTituloCatastrofes);
			SubTituloCatastrofes.setText("Para Buscar Una Persona Desaparecida");
		}
				
		ListaCatastrofe = new ArrayList<Catastrofe>(); 
		// Obtengo Las Catastrofes Mediante El Web Service
		String URL = "http://"+Host+":8080/catastrophes-system-web/rest/ServicesCatastrofe/list";
		HttpClient httpClient = new DefaultHttpClient();		
		HttpPost post = new HttpPost(URL);
		post.setHeader("User-Agent", USER_AGENT);					
		try {			
			HttpResponse resp = httpClient.execute(post);
			
			System.out.println("Request To URL: " + URL);
			System.out.println("Response Code: " + resp.getStatusLine().getStatusCode());
			
			BufferedReader rd = new BufferedReader(
            new InputStreamReader(resp.getEntity().getContent()));
 
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);				
			}			 			
			
			// Recorro Todo El Array Y Ingreso Cada Catastrofe En La Lista
			JSONArray ArrayCatastrofres = new JSONArray(result.toString());
			int QtyCatastrofes = ArrayCatastrofres.length();
			String CatastrofeTipo;
			for (int i=0; i<QtyCatastrofes; i++) {
                JSONObject jsonobject = ArrayCatastrofres.getJSONObject(i);
                int CatastrofeId = jsonobject.getInt("catastrofeId");
                String CatastrofeNombre = jsonobject.getString("nombre");
                String CatastrofeLatitud = jsonobject.getString("coordX");
                String CatastrofeLongitud = jsonobject.getString("coordY");
                String CatastrofeLogo = jsonobject.getString("logo");
                if(CatastrofeNombre.contains("Incendio")) CatastrofeTipo = "incendio";
                else if(CatastrofeNombre.contains("Inundacion")) CatastrofeTipo = "inundacion";
                else CatastrofeTipo = "sudestada";
                System.out.println("Catastrofe: " + CatastrofeId + " - " + CatastrofeNombre + " - " + CatastrofeLogo);
                Catastrofe Catas = new Catastrofe(CatastrofeId,CatastrofeNombre,CatastrofeTipo,CatastrofeLatitud,CatastrofeLongitud,CatastrofeLogo); 
                ListaCatastrofe.add(Catas);	
           }																        				        					        
		}
		catch(Exception ex)	{ Log.e("ServicioRest","Error!", ex); }	
					
		// Obtengo El Id Del Usuario Logeado
		Globals IdUsuarioLogin = Globals.getInstance();
		int IdUsuario = IdUsuarioLogin.getData();				
		
		ListView ListaCatastrofes = (ListView) findViewById(R.id.ListaCatastrofes);
		ListAdapter Adaptador = new AdaptadorCatastrofeList(this, ListaCatastrofe);
		ListaCatastrofes.setAdapter(Adaptador);
		ListaCatastrofes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
		
		ListaCatastrofes.setOnItemClickListener(new OnItemClickListener() {		    
			@Override public void onItemClick(AdapterView<?> a, View v, int position, long id) {		       
				Catastrofe CatastrofeSeleccionada = ((Catastrofe) a.getAdapter().getItem(position));
				int IdCatastrofe = CatastrofeSeleccionada.getId();
				String NombreCatastrofe = CatastrofeSeleccionada.getNombre();	
				Bundle Catas = new Bundle();                                   
				Catas.putInt("IdCatastrofe",IdCatastrofe);	
				Catas.putString("NombreCatastrofe",NombreCatastrofe);	
				if(Seccion.equals("BusquedaPersonasDesaparecidas")){
					Intent intent = new Intent(ListadoCatastrofesActivity.this, BusquedaPersonasDesaparecidasActivity.class);	
					intent.putExtras(Catas); 
					startActivity(intent);
				} 		
				else if(Seccion.equals("ReportePersonasDesaparecidas")){
					Intent intent = new Intent(ListadoCatastrofesActivity.this, ReportePersonasDesaparecidasActivity.class);	
					intent.putExtras(Catas); 
					startActivity(intent);
				}
		    }
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.listado_catastrofes, menu);
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
