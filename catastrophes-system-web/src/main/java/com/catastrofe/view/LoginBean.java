package com.catastrofe.view;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

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
	private UsuarioBean usuarioBean;

	public LoginBean() {
		
	}
	
	public String login(){
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
		usuario = usuarioBean.findByUserAndPass(user, password);
		
		if(usuario != null){
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
			if(usuario.getRol().getName().equals("administrador")){
				return "usuario/create?faces-redirect=true";
			} else if (usuario.getRol().getName().equals("usuario")) {
				return "usuario/search?faces-redirect=true";
			} else {
				return "usuario/view?faces-redirect=true";
			}
			
		} else {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Invalid Login!","Please Try Again!"));
			return "login?faces-redirect=false";
		}
		
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
