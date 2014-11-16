package com.ssacn.ejb.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class PropertiesManager {
	// Clase que nos permite manipular un archivo properties.
    Properties properties;
	public PropertiesManager() {

        try {
            // create and load default properties
            properties = new Properties();
            FileInputStream in = new FileInputStream("./config/catastrophes.properties");
            //properties.load( getClass().getResourceAsStream("config/catastrophes.properties") );

            properties.load( in);
            
        } catch (IOException ex) {
            Logger.getLogger(PropertiesManager.class.getName()).log(Level.ERROR, "Error al cargar archivo de propiedades", ex);
            Logger.getLogger(PropertiesManager.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
        } 
    }
    

    public String getPropiedad(String name) {
        return properties.getProperty(name);
    }

}
