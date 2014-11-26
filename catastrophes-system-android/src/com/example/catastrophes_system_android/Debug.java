package com.example.catastrophes_system_android;

public class Debug {	
	   private static Debug instance;

	   private String type;
	   private String host_emulador;
	   private String host_phone;
	 
	   private Debug(){}
	   
	   public static synchronized Debug getInstance(){
		  if(instance==null){instance = new Debug(); }
		  return instance;
	   }
	 
	   public void setType(String t){ this.type=t; }
	   public void setHostPhone(String host){ this.host_phone=host; }
	   public void setHostEmulator(String host){ this.host_emulador=host; }
	   	   
	   public String getType(){ return this.type; }
	   public String getHostPhone(){ return this.host_phone; }
	   public String getHostEmulator(){ return this.host_emulador; }	   
	   public String getDebuggingHost(){
		   if(this.type.equals("Phone")) return this.host_phone;
		   else return this.host_emulador; 
	   }

}
