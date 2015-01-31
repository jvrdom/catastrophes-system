package com.catastrofe.view;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.context.RequestContext;

import com.catastrofe.dao.Audit_LoginDao;
import com.catastrofe.dao.RolDao;
import com.catastrofe.dao.UsuarioDao;
import com.catastrofe.model.Audit_Login;
import com.catastrofe.model.Rol;
import com.catastrofe.model.Usuario;
import com.catastrofe.model.sexo;

@Named
@SessionScoped
@Stateful
public class LoginBean {
	
	/**
	 * 
	 */
	private Usuario usuario;
	private String user, password;
	private String userFace;
	
	@Inject
	private UsuarioDao usuarioDao;

	@Inject
	private Audit_LoginDao auditDao;
	
	@Inject
	private RolDao rolDao;
	
	public LoginBean() {
		
	}
	
	public String login(){
		try{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
			usuario = usuarioDao.findByUser(user);
			
			if(usuario != null){
				if(BCrypt.checkpw(password, usuario.getPassword())) {
					if(validarDatos()){
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
						if(usuario != null){
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
							Audit_Login audit=new Audit_Login();
							audit.setFecha(new Date());
							audit.setUsuario_id(usuario.getId());
							audit.setUsuario_rol(usuario.getRol().getName());
							auditDao.create(audit);
		
							if(usuario.getRol().getName().toLowerCase().equals("administrador")){							
								return "catastrofe/create?faces-redirect=true";
							} else if (usuario.getRol().getName().toLowerCase().equals("usuario")) {
								return "usuario/index?faces-redirect=true";
							} else {
								return "usuario/view?faces-redirect=true";
							}
						} else {
							FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Login Invalido!","Intente de Nuevo!"));
							return "login?faces-redirect=false";
						}
					} else {
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Login Invalido!","Intente de Nuevo!"));
						return "login?faces-redirect=false";
					}
				} else{
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Password Incorrecto!","Intente de Nuevo!"));
					return "login?faces-redirect=false";
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"El usuario ingresado no esta registrado","Intente de Nuevo!"));
				return "login?faces-redirect=false";
			}

		} catch(Exception ex){
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al Iniciar Sesion",ex.getMessage()));
			return "login?faces-redirect=false";
		}
		
	}
	
	private boolean validarDatos() {
		if(user.isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Login Invalido!","Debe ingresar un usuario"));
			return false;
		}else if(password.isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Login Invalido!","Debe ingresar un password"));
			return false;
		}else{
			return true;
		}
		
	}

	public String getUserFace() {
		return userFace;
	}

	public void setUserFace(String userFace) {
		this.userFace = userFace;
	}

	public String faceLogin(){
		String[] datos=userFace.split(",");
		String nombre=datos[0].trim();
		String apellido=datos[1].trim();
		String mail=datos[2].trim();
		String sex=datos[3].trim();
		usuario=null;
		usuario=usuarioDao.findBySocialUser(mail);
		if(usuario==null){
			usuario=new Usuario();
			List<Rol> roles = rolDao.listAll(null, null);
        	
        	for(int i = 0; i < roles.size(); i++){
        		if (roles.get(i).getName().equals("usuario")){
        			usuario.setRol(roles.get(i));
        		} 
        	}		
        	if(sex.trim().equalsIgnoreCase("male")){
        		usuario.setSexo(sexo.Masculino);
        	}else{
        		usuario.setSexo(sexo.Femenino);
        	}
        	usuario.setUser(mail);
        	usuario.setEmail(mail);
        	usuario.setNombre(nombre);
        	usuario.setApellido(apellido);
        	usuario.setSocialAuth(true);
			usuarioDao.create(usuario);
			usuario=usuarioDao.findBySocialUser(mail);
			
		}
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
		Audit_Login audit=new Audit_Login();
		audit.setFecha(new Date());
		audit.setUsuario_id(usuario.getId());
		audit.setUsuario_rol(usuario.getRol().getName());
		auditDao.create(audit);
		System.out.println("Valorrrrrrrr de usuarioF:"+userFace.trim());		
		return "usuario/index.xhtml";
	}
	
	public String doLogout() {
        try {
        	Usuario u=(Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        	if(u.getSocialAuth()!=null && u.getSocialAuth())
        	{
        		RequestContext requestContext = RequestContext.getCurrentInstance();  
        		requestContext.execute("faceLogout();");
        	}
            limpiarUsuario();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            System.out.println("en logout***********");
        } catch (Exception ex) {
        	ex.printStackTrace();
        	FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",ex.getMessage()));
            return null;
        }
        return "/login?faces-redirect=true";
    }

	private void limpiarUsuario() {
		usuario=null;
		user="";
		password="";
		
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
