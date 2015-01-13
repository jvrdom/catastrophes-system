package com.catastrofe.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

import java.lang.Override;

import com.catastrofe.model.Catastrofe;

import javax.persistence.ManyToOne;

import com.catastrofe.model.Usuario;
import com.catastrofe.model.Imagen;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "PerosnaDesap.findByCatastrofe", query = "SELECT p FROM PerosnaDesap p join p.catastrofe c WHERE c.id = :idCatastrofe"),
	@NamedQuery(name = "PerosnaDesap.findByName", query = "SELECT p FROM PerosnaDesap p WHERE p.catastrofe.id = :idCatastrofe AND p.nombre like :nombre")})
public class PerosnaDesap implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
   @Version
   @Column(name = "version")
   private int version;

   @Column
   private String nombre;

   @Column
   private String apellido;

   @Column
   private String telDeContacto;

   @Column
   private String descripcion;

   @Column
   private String status;
   
   @Column
   private double latitud;

   @Column
   private double longitud;

   @ManyToOne
   private Catastrofe catastrofe;

   @ManyToOne
   private Usuario reportado;

   @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
   private Set<Imagen> imagenes = new HashSet<Imagen>();

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (!(obj instanceof PerosnaDesap))
      {
         return false;
      }
      PerosnaDesap other = (PerosnaDesap) obj;
      if (id != null)
      {
         if (!id.equals(other.id))
         {
            return false;
         }
      }
      return true;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   public String getNombre()
   {
      return nombre;
   }

   public void setNombre(String nombre)
   {
      this.nombre = nombre;
   }

   public String getApellido()
   {
      return apellido;
   }

   public void setApellido(String apellido)
   {
      this.apellido = apellido;
   }

   public String getTelDeContacto()
   {
      return telDeContacto;
   }

   public void setTelDeContacto(String telDeContacto)
   {
      this.telDeContacto = telDeContacto;
   }

   public String getDescripcion()
   {
      return descripcion;
   }

   public void setDescripcion(String descripcion)
   {
      this.descripcion = descripcion;
   }

   public String getStatus()
   {
      return status;
   }

   public void setStatus(String status)
   {
      this.status = status;
   }
   
   public double getLatitud() {
	  return latitud;
   }	

   public void setLatitud(double latitud) {
	  this.latitud = latitud;
   }
	
   public double getLongitud() {
	  return longitud;
   }
	
   public void setLongitud(double longitud) {
	  this.longitud = longitud;
   }
	
   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (nombre != null && !nombre.trim().isEmpty())
         result += "nombre: " + nombre;
      if (apellido != null && !apellido.trim().isEmpty())
         result += ", apellido: " + apellido;
      if (telDeContacto != null && !telDeContacto.trim().isEmpty())
         result += ", telDeContacto: " + telDeContacto;
      if (descripcion != null && !descripcion.trim().isEmpty())
         result += ", descripcion: " + descripcion;
      if (status != null && !status.trim().isEmpty())
         result += ", status: " + status;
      return result;
   }

   public Catastrofe getCatastrofe()
   {
      return this.catastrofe;
   }

   public void setCatastrofe(final Catastrofe catastrofe)
   {
      this.catastrofe = catastrofe;
   }

   public Usuario getReportado()
   {
      return this.reportado;
   }

   public void setReportado(final Usuario reportado)
   {
      this.reportado = reportado;
   }

   public Set<Imagen> getImagenes()
   {
      return this.imagenes;
   }

   public void setImagenes(final Set<Imagen> imagenes)
   {
      this.imagenes = imagenes;
   }
}