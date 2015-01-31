package com.catastrofe.view;

import java.util.Date;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.mindrot.jbcrypt.BCrypt;

import com.catastrofe.dao.Audit_LoginDao;
import com.catastrofe.dao.UsuarioDao;
import com.catastrofe.model.Audit_Login;
import com.catastrofe.model.Usuario;

@Named
@SessionScoped
@Stateful
public class LoginBean {
	
	/**
	 * 
	 */
	private Usuario usuario;
	private String user, password;
	
	@Inject
	private UsuarioDao usuarioDao;

	@Inject
	private Audit_LoginDao auditDao;
	
	public LoginBean() {
		
	}
	
	public String login(){
		try{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
			usuario = usuarioDao.findByUserAndPass(user, password);
			
			if(usuario != null){
				if(BCrypt.checkpw(password, usuario.getPassword())) {
					
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
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Password Incorrecto!","Intente de Nuevo!"));
					return "login?faces-redirect=false";
				}
				
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Login Invalido!","Intente de Nuevo!"));
				return "login?faces-redirect=false";
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al Iniciar Sesion",ex.getMessage()));
			return "login?faces-redirect=false";
		}
		
	}
	
	/*public String logout(){
		try{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
			return "login?faces-redirect=true";
		}catch(Exception ex){
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",ex.getMessage()));
			return "";
		}
		
	}*/
	public String doLogout() {
        try {
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
