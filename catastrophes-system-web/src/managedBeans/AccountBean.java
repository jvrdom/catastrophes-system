package managedBeans;

import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import com.ssacn.ejb.business.remote.AdminManagerRemote;
import com.ssacn.ejb.business.remote.UserManagerRemote;

@ManagedBean(name = "account")
public class AccountBean {
	
	@EJB
	private UserManagerRemote userM;
	//private AdminManagerRemote adminM;
	
	private String nombre, apellido, email, contraseña, sexo, telefono;
	private Date fechaNac;
	
	public void altaUsuario(){
		userM.createUser(nombre, apellido, email, contraseña, fechaNac, sexo,telefono);
		//adminM.create(nombre, apellido, email, contraseña, fechaNac, sexo);
	}
	
	/*Metodos de prueba*/
	public void bajaUsuario(){
		//userM.eliminarUsuario(1);
	}
	
	/*
	public void actualizarUsuario(){
		
	}*/

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}
