package com.catastrofe.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.catastrofe.model.Usuario;
import javax.persistence.ManyToOne;

@Entity
public class PedidoAyuda implements Serializable
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
   @Temporal(TemporalType.DATE)
   private Date fechaHora;

   @ManyToOne
   private Usuario usuario;

   @Column
   private float longitud;

   @Column
   private float latitud;

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
      if (!(obj instanceof PedidoAyuda))
      {
         return false;
      }
      PedidoAyuda other = (PedidoAyuda) obj;
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

   public Date getFechaHora()
   {
      return fechaHora;
   }

   public void setFechaHora(Date fechaHora)
   {
      this.fechaHora = fechaHora;
   }

   public Usuario getUsuario()
   {
      return this.usuario;
   }

   public void setUsuario(final Usuario usuario)
   {
      this.usuario = usuario;
   }

   public float getLongitud()
   {
      return longitud;
   }

   public void setLongitud(float longitud)
   {
      this.longitud = longitud;
   }

   public float getLatitud()
   {
      return latitud;
   }

   public void setLatitud(float latitud)
   {
      this.latitud = latitud;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (descripcion != null && !descripcion.trim().isEmpty())
         result += "descripcion: " + descripcion;
      result += ", longitud: " + longitud;
      result += ", latitud: " + latitud;
      return result;
   }
}