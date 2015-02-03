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
import javax.persistence.Enumerated;
import com.catastrofe.model.tipoDonacion;
import com.catastrofe.model.Usuario;
import javax.persistence.OneToOne;
import com.catastrofe.model.Ong;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Donacion implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
   @Version
   @Column(name = "version")
   private int version;

   @Column
   @Temporal(TemporalType.DATE)
   private Date fecha;

   @Enumerated
   private tipoDonacion tipo;

   @ManyToOne
   private Usuario usuario;

   @Column
   private String descripcion;

   @Column
   private String tipoDeServicio;

   @Column
   private int horasDedicadas;

   @Column
   private double monto;

   @Column
   private String tarjeta;

   @Column
   private String nroTarjeta;

   @ManyToOne
   private Ong ong;

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
      if (!(obj instanceof Donacion))
      {
         return false;
      }
      Donacion other = (Donacion) obj;
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

   public Date getFecha()
   {
      return fecha;
   }

   public void setFecha(Date fecha)
   {
      this.fecha = fecha;
   }

   public tipoDonacion getTipo()
   {
      return tipo;
   }

   public void setTipo(tipoDonacion tipo)
   {
      this.tipo = tipo;
   }

   public Usuario getUsuario()
   {
      return usuario;
   }

   public void setUsuario(Usuario usuario)
   {
      this.usuario = usuario;
   }

   public String getDescripcion()
   {
      return descripcion;
   }

   public void setDescripcion(String descripcion)
   {
      this.descripcion = descripcion;
   }

   public String getTipoDeServicio()
   {
      return tipoDeServicio;
   }

   public void setTipoDeServicio(String tipoDeServicio)
   {
      this.tipoDeServicio = tipoDeServicio;
   }

   public int getHorasDedicadas()
   {
      return horasDedicadas;
   }

   public void setHorasDedicadas(int horasDedicadas)
   {
      this.horasDedicadas = horasDedicadas;
   }

   public double getMonto()
   {
      return monto;
   }

   public void setMonto(double monto)
   {
      this.monto = monto;
   }

   public String getTarjeta()
   {
      return tarjeta;
   }

   public void setTarjeta(String tarjeta)
   {
      this.tarjeta = tarjeta;
   }

   public String getNroTarjeta()
   {
      return nroTarjeta;
   }

   public void setNroTarjeta(String nroTarjeta)
   {
      this.nroTarjeta = nroTarjeta;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (descripcion != null && !descripcion.trim().isEmpty())
         result += "descripcion: " + descripcion;
      if (tipoDeServicio != null && !tipoDeServicio.trim().isEmpty())
         result += ", tipoDeServicio: " + tipoDeServicio;
      result += ", horasDedicadas: " + horasDedicadas;
      result += ", monto: " + monto;
      if (tarjeta != null && !tarjeta.trim().isEmpty())
         result += ", tarjeta: " + tarjeta;
      if (nroTarjeta != null && !nroTarjeta.trim().isEmpty())
         result += ", nroTarjeta: " + nroTarjeta;
      return result;
   }

   public Ong getOng()
   {
      return this.ong;
   }

   public void setOng(final Ong ong)
   {
      this.ong = ong;
   }
}