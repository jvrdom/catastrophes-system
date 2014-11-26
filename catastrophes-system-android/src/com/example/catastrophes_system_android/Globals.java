package com.example.catastrophes_system_android;

public class Globals{
	   private static Globals instance;

	   private int data;
	 
	   private Globals(){}
	   
	   public static synchronized Globals getInstance(){
		  if(instance==null){instance = new Globals(); }
		  return instance;
	   }
	 
	   public void setData(int d){ this.data=d; }
	   
	   public int getData(){ return this.data; }	 

}