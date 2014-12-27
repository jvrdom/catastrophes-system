package com.catastrofe.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
		personasDesaparecidas = personDesDao.getPersonasByCatastrofe(4);
	}
	
	public void handleKeyEvent(){
		personasDesaparecidas = personDesDao.getDesaparecidosByName(4, search);
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
