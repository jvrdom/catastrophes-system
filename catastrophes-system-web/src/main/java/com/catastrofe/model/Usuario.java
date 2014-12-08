package com.catastrofe.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

import java.lang.Override;

import com.catastrofe.model.Rol;
import com.catastrofe.model.sexo;

import javax.persistence.ManyToOne;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQueries({
      @NamedQuery(name = "Usuario.findByLoginPass", query = "SELECT u FROM Usuario u WHERE u.user = :usuario AND u.password=:password"),
      @NamedQuery(name= "Usuario.findAll", query="SELECT u FROM Usuario u"),
})
@XmlRootElement
public class Usuario implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   private String user;

   @Column
   private String password;

   @ManyToOne
   private Rol rol;

   @Column
   private String nombre;

   @Column
   private String apellido;

   @Temporal(TemporalType.DATE)
   private Date nacimiento;

   @Column
   private String telefono;

   @Column
   private sexo Sexo;

   @Column
   private String email;

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
         return id.equals(((Usuario) that).id);
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

   public String getUser()
   {
      return this.user;
   }

   public void setUser(final String user)
   {
      this.user = user;
   }

   public String getPassword()
   {
      return this.password;
   }

   public void setPassword(final String password)
   {
      this.password = password;
   }

   public Rol getRol()
   {
      return this.rol;
   }

   public void setRol(final Rol rol)
   {
      this.rol = rol;
   }

   public String getNombre()
   {
      return this.nombre;
   }

   public void setNombre(final String nombre)
   {
      this.nombre = nombre;
   }

   public String getApellido()
   {
      return this.apellido;
   }

   public void setApellido(final String apellido)
   {
      this.apellido = apellido;
   }

   public Date getNacimiento()
   {
      return this.nacimiento;
   }

   public void setNacimiento(final Date nacimiento)
   {
      this.nacimiento = nacimiento;
   }

   public String getTelefono()
   {
      return this.telefono;
   }

   public void setTelefono(final String telefono)
   {
      this.telefono = telefono;
   }

   public sexo getSexo()
   {
      return this.Sexo;
   }

   public void setSexo(final sexo Sexo)
   {
      this.Sexo = Sexo;
   }

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      result += "serialVersionUID: " + serialVersionUID;
      if (user != null && !user.trim().isEmpty())
         result += ", user: " + user;
      if (password != null && !password.trim().isEmpty())
         result += ", password: " + password;
      if (nombre != null && !nombre.trim().isEmpty())
         result += ", nombre: " + nombre;
      if (apellido != null && !apellido.trim().isEmpty())
         result += ", apellido: " + apellido;
      if (telefono != null && !telefono.trim().isEmpty())
         result += ", telefono: " + telefono;
      if (email != null && !email.trim().isEmpty())
         result += ", email: " + email;
      return result;
   }
}