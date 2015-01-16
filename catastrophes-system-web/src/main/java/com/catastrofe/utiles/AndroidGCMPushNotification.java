package com.catastrofe.utiles;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;

public class AndroidGCMPushNotification {
	
	private static Result result;

	public static boolean enviarNotificaciones(String collapseKey, List<String> androidTargets) throws JSONException
	{			
			//Instancia de com.android.gcm.server.Sender que realiza la transmision
			//de los mensajes a Google Cloud Messaging Service.
			//Se le pasa el API KEY obtenido de code.google.com
	        Sender sender = new Sender("AIzaSyCzvR53GGYrs_hGElCQewAcNSAaxOyo5JA");
	        
	        JSONObject obj = new JSONObject();

	        obj.put("name", "foo");
	        obj.put("num", new Integer(100));
	        obj.put("balance", new Double(1000.21));
	        obj.put("is_vip", new Boolean(true));
	        
	        Gson gson = new Gson();
	        String mensaje = gson.toJson(obj);
	        	       
	        //String message = "Mensaje de prueba";
	        
	        // Mensaje que contiene los datos que seran transmitidos.
	        Message message1 = new Message.Builder()

		        .collapseKey(collapseKey)
		        .timeToLive(30)
		        .delayWhileIdle(true)
		        .addData("title", "Nueva notificación")
		        .addData("message", mensaje )
		        .build();
	        
	        try 
	        {
	        	for (String regId : androidTargets) {
	        		
	        		result = sender.send(message1, regId, 1);
		             
		            if (result != null) 
		            {
		                String canonicalRegId = result.getMessageId();
		                if (canonicalRegId != null) 
		                {
		                     
		                }
		            } 
		            else 
		            {
		                String error = result.getErrorCodeName();
		                System.out.println("Broadcast failure: " + error);
		            }
				}
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }	        
	        return true;
	}
}