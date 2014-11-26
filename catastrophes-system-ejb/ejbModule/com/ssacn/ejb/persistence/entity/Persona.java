package com.ssacn.ejb.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.ssacn.ejb.bean.Sexo;

/**
 * Entity implementation class for Entity: Persona
 *
 */

@Entity
@Inheritance ( strategy=InheritanceType.SINGLE_TABLE )
@NamedQueries({
@NamedQuery(name = "Persona.findByNamePass", query = "SELECT p FROM Persona p WHERE p.email = :email AND p.password=:password")})
public class Persona implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Persona() {
		super();
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;
   
    @Column(nullable=false)
    protected String nombre;
        
    @Column(unique=true)
    protected String email;
    
    protected String apellido;
    
    @Column(nullable=false)
    protected String password;
    
    @Temporal (TemporalType.DATE)
    protected Date nacimiento;
    
    @Enumerated(EnumType.STRING)
    protected Sexo sexo;
    
    
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String lastname) {
		this.apellido = lastname;
	}
	
	public Date getNacimiento() {
		return nacimiento;
	}
	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer Id) {
		this.id = Id;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String name) {
		this.nombre = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
    public boolean equals(Object object) {
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
   
}
