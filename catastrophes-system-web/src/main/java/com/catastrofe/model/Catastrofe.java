package com.catastrofe.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Catastrofe implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @NotNull
   private String nombre;

   @Column
   private String descripcion;

   @Column
   private String logo;

   @Column
   private double latitud;

   @Column
   private double longitud;

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
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((Catastrofe) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getNombre()
   {
      return this.nombre;
   }

   public void setNombre(final String nombre)
   {
      this.nombre = nombre;
   }

   public String getDescripcion()
   {
      return this.descripcion;
   }

   public void setDescripcion(final String descripcion)
   {
      this.descripcion = descripcion;
   }

   public String getLogo()
   {
      return this.logo;
   }

   public void setLogo(final String logo)
   {
      this.logo = logo;
   }

   public double getLatitud()
   {
      return this.latitud;
   }

   public void setLatitud(final double latitud)
   {
      this.latitud = latitud;
   }

   public double getLongitud()
   {
      return this.longitud;
   }

   public void setLongitud(final double longitud)
   {
      this.longitud = longitud;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (nombre != null && !nombre.trim().isEmpty())
         result += "nombre: " + nombre;
      if (descripcion != null && !descripcion.trim().isEmpty())
         result += ", descripcion: " + descripcion;
      if (logo != null && !logo.trim().isEmpty())
         result += ", logo: " + logo;
      result += ", latitud: " + latitud;
      result += ", longitud: " + longitud;
      return result;
   }
}