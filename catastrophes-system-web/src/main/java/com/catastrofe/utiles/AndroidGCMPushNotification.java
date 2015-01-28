package com.catastrofe.utiles;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.catastrofe.model.Catastrofe;
import com.catastrofe.model.PedidoAyuda;
import com.catastrofe.model.PerosnaDesap;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

public class AndroidGCMPushNotification {
	
	private static final String CATASTROFE_TITLE = "Hay una nueva catástrofe!";
	private static final String AYUDA_TITLE = "Hay un nuevo pedido de ayuda!";
	private static final String DESAPARECIDO_TITLE = "Hay una nueva persona desaparecida!";
	
	public boolean enviarNotificaciones(String collapseKey, List<String> androidTargets, Object object) throws JSONException
	{			
			//Instancia de com.android.gcm.server.Sender que realiza la transmision
			//de los mensajes a Google Cloud Messaging Service.
			//Se le pasa el API KEY obtenido de code.google.com
	        Sender sender = new Sender("API_KEY");
	        
	        // Mensaje que contiene los datos que seran transmitidos.
	        Message message1 = new Message.Builder()
		        .collapseKey(collapseKey)
		        .timeToLive(30)
		        .delayWhileIdle(true)
		        .addData("message", this.buildObject(object) )
		        .build();
	        try 
	        {
	        	MulticastResult result = sender.send(message1, androidTargets, 1);
	            
	        	if (result.getResults() != null) 
	            {
	                int canonicalRegId = result.getCanonicalIds();
	                if (canonicalRegId != 0) 
	                {
	                     
	                }
	            }
	            else 
	            {
	                int error = result.getFailure();
	                System.out.println("Broadcast failure: " + error);
	            }
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }	        
	        return true;
	}
	
	private String buildObject(Object object) {
		
		JSONObject jsonObj = new JSONObject();
		
		try {
			
			if(object instanceof PedidoAyuda) {
				
				jsonObj.put("title", AndroidGCMPushNotification.AYUDA_TITLE);
				jsonObj.put("description", ((PedidoAyuda) object).getDescripcion());
				jsonObj.put("dateAdded", ((PedidoAyuda) object).getFechaHora());
				jsonObj.put("lat", ((PedidoAyuda) object).getLatitud());
				jsonObj.put("long", ((PedidoAyuda) object).getLongitud());
				
			} else if (object instanceof Catastrofe) {
				
				jsonObj.put("title", AndroidGCMPushNotification.CATASTROFE_TITLE);
				jsonObj.put("name", ((Catastrofe) object).getNombre());
				jsonObj.put("description", ((Catastrofe) object).getDescripcion());
				jsonObj.put("address", ((Catastrofe) object).getDireccion());
				jsonObj.put("lat", ((Catastrofe) object).getLatitud());
				jsonObj.put("long", ((Catastrofe) object).getLongitud());
				jsonObj.put("logo", ((Catastrofe) object).getLogo());
				jsonObj.put("radio", ((Catastrofe) object).getRadio());
				jsonObj.put("plans", ((Catastrofe) object).getPlanes());
				jsonObj.put("type", ((Catastrofe) object).getTipoCatastrofe().name());
				
			} else if (object instanceof PerosnaDesap) {
				
				jsonObj.put("title", AndroidGCMPushNotification.DESAPARECIDO_TITLE);
				jsonObj.put("name", ((PerosnaDesap) object).getNombre());
				jsonObj.put("tel", ((PerosnaDesap) object).getTelDeContacto());
				jsonObj.put("description", ((PerosnaDesap) object).getDescripcion());
				jsonObj.put("lat", ((PerosnaDesap) object).getLatitud());
				jsonObj.put("long", ((PerosnaDesap) object).getLongitud());
				jsonObj.put("plans", ((PerosnaDesap) object).getImagenes());
				
			} else {
				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObj.toString();
	}
	
	
}