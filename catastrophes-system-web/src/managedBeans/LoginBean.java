package managedBeans;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.ssacn.ejb.business.remote.UserManagerRemote;

@ManagedBean(name="login")
@SessionScoped
public class LoginBean {
	
	@EJB
	private UserManagerRemote userM;
	
	private String email, password, message;
	
	public void login(){
		boolean result = userM.existeUsuario(email, password);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
		if(result){
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", email);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("admin/catastrofe/altaCatastrofe.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Invalid Login!","Please Try Again!"));
		}
	}
	
	public void logout(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
		
}
