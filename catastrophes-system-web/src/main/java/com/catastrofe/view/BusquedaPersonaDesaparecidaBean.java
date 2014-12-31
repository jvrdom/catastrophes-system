package com.catastrofe.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.catastrofe.dao.PerosnaDesapDao;
import com.catastrofe.model.PerosnaDesap;

@ViewScoped
@ManagedBean
public class BusquedaPersonaDesaparecidaBean {
	
	@Inject
	private PerosnaDesapDao personDesDao;
	private String search;
	private List<PerosnaDesap> personasDesaparecidas;
	
	@PostConstruct
	public void init(){
		try{
		personasDesaparecidas = personDesDao.getPersonasByCatastrofe(4);
		}catch(Exception ex){
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",ex.getMessage()));
		   
		}
	}
	
	public void handleKeyEvent(){
		try{
		personasDesaparecidas = personDesDao.getDesaparecidosByName(4, search);
		}catch(Exception ex){
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",ex.getMessage()));
		}
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<PerosnaDesap> getPersonasDesaparecidas() {
		return personasDesaparecidas;
	}

	public void setPersonasDesaparecidas(List<PerosnaDesap> personasDesaparecidas) {
		this.personasDesaparecidas = personasDesaparecidas;
	}
	
}
