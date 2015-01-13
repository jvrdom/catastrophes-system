package com.catastrofe.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
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
   
   @Column
   private double radio;

   @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
   private Set<Imagen> imagenes = new HashSet<Imagen>();

   @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
   private Set<Novedades> novedades = new HashSet<Novedades>();

   @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
   private Set<Plan> planes = new HashSet<Plan>();

   @Column
   private String direccion;

   @Column
   private tipoCatastrofe tipoCatastrofe;

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

   public Set<Imagen> getImagenes()
   {
      return this.imagenes;
   }

   public void setImagenes(final Set<Imagen> imagenes)
   {
      this.imagenes = imagenes;
   }

   public Set<Novedades> getNovedades()
   {
      return this.novedades;
   }

   public void setNovedades(final Set<Novedades> novedades)
   {
      this.novedades = novedades;
   }

   public Set<Plan> getPlanes()
   {
      return this.planes;
   }

   public void setPlanes(final Set<Plan> planes)
   {
      this.planes = planes;
   }

   public String getDireccion()
   {
      return this.direccion;
   }

   public void setDireccion(final String direccion)
   {
      this.direccion = direccion;
   }

   public tipoCatastrofe getTipoCatastrofe()
   {
      return this.tipoCatastrofe;
   }

   public void setTipoCatastrofe(final tipoCatastrofe tipoCatastrofe)
   {
      this.tipoCatastrofe = tipoCatastrofe;
   }
   
   public double getRadio() {
	   return radio;
   }

   public void setRadio(double radio) {
	   this.radio = radio;
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
      if (direccion != null && !direccion.trim().isEmpty())
         result += ", direccion: " + direccion;
      return result;
   }
}