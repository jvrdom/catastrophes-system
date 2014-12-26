package com.catastrofe.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import javax.persistence.Enumerated;
import com.catastrofe.model.tipoPlan;
import com.catastrofe.model.Catastrofe;
import javax.persistence.ManyToOne;

@Entity
public class Plan implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
   @Version
   @Column(name = "version")
   private int version;

   @Enumerated
   private tipoPlan tipo;

   @Column
   private String descripcion;

   @Column
   private String url;

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
      if (!(obj instanceof Plan))
      {
         return false;
      }
      Plan other = (Plan) obj;
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

   public tipoPlan getTipo()
   {
      return tipo;
   }

   public void setTipo(tipoPlan tipo)
   {
      this.tipo = tipo;
   }

   public String getDescripcion()
   {
      return descripcion;
   }

   public void setDescripcion(String descripcion)
   {
      this.descripcion = descripcion;
   }

   public String getUrl()
   {
      return url;
   }

   public void setUrl(String url)
   {
      this.url = url;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (descripcion != null && !descripcion.trim().isEmpty())
         result += "descripcion: " + descripcion;
      if (url != null && !url.trim().isEmpty())
         result += ", url: " + url;
      return result;
   }

}