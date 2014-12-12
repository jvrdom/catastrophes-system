package com.catastrofe.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;

@Entity
public class Novedades implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
   @Version
   @Column(name = "version")
   private int version;

   @Column
   private String descripcion;

   @Column
   private String origenDato;

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
      if (!(obj instanceof Novedades))
      {
         return false;
      }
      Novedades other = (Novedades) obj;
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

   public String getDescripcion()
   {
      return descripcion;
   }

   public void setDescripcion(String descripcion)
   {
      this.descripcion = descripcion;
   }

   public String getOrigenDato()
   {
      return origenDato;
   }

   public void setOrigenDato(String origenDato)
   {
      this.origenDato = origenDato;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (descripcion != null && !descripcion.trim().isEmpty())
         result += "descripcion: " + descripcion;
      if (origenDato != null && !origenDato.trim().isEmpty())
         result += ", origenDato: " + origenDato;
      return result;
   }
}