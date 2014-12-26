package com.catastrofe.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import com.catastrofe.model.Donacion;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import com.catastrofe.model.Catastrofe;

@Entity
public class Ong implements Serializable
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
   private String descripcion;

   @OneToMany(mappedBy = "ong", cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<Donacion> donaciones = new HashSet<Donacion>();

   @OneToMany
   private Set<Catastrofe> catastrofes = new HashSet<Catastrofe>();

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
      if (!(obj instanceof Ong))
      {
         return false;
      }
      Ong other = (Ong) obj;
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

   public String getDescripcion()
   {
      return descripcion;
   }

   public void setDescripcion(String descripcion)
   {
      this.descripcion = descripcion;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (nombre != null && !nombre.trim().isEmpty())
         result += "nombre: " + nombre;
      if (descripcion != null && !descripcion.trim().isEmpty())
         result += ", descripcion: " + descripcion;
      return result;
   }

   public Set<Donacion> getDonaciones()
   {
      return this.donaciones;
   }

   public void setDonaciones(final Set<Donacion> donaciones)
   {
      this.donaciones = donaciones;
   }

   public Set<Catastrofe> getCatastrofes()
   {
      return this.catastrofes;
   }

   public void setCatastrofes(final Set<Catastrofe> catastrofes)
   {
      this.catastrofes = catastrofes;
   }
}