package com.catastrofe.view;


import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

import org.omnifaces.cdi.ViewScoped;

import com.catastrofe.dao.CatastrofeDao;
import com.catastrofe.dao.OngDao;
import com.catastrofe.model.Ong;
import com.catastrofe.model.Catastrofe;
import com.catastrofe.model.Usuario;


@Named
@ViewScoped
public class OngMB implements Serializable{

	@Inject
	private OngDao ongDao;
	@Inject
	private CatastrofeDao catastrofeDao;
	
	private List<Ong> ongs;
	private Ong selectedOng;
	private List<Ong> filteredOng;
	private List<Catastrofe> catastrofes;
	private List<Catastrofe> catsOng;
	private List<Catastrofe> selectedCatastrofes;
	private List<Catastrofe> selectedCat;

	private boolean readOnly;
	private boolean created;
	private String name;
	private String desc;
	private String mail;

	 
	@PostConstruct
	public void init(){
		try{
			
			
			ongs=ongDao.listAll(null,null);

			catsOng=new ArrayList<Catastrofe>();


			catastrofes=catastrofeDao.getAll();
			readOnly=true;


		}catch(Exception ex){
			ex.printStackTrace();
		}
	}



	


	public String getMail() {
		return mail;
	}






	public void setMail(String mail) {
		this.mail = mail;
	}






	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public boolean isCreated() {
		return created;
	}



	public void setCreated(boolean created) {
		this.created = created;
	}



	public String getDesc() {
		return desc;
	}



	public void setDesc(String desc) {
		this.desc = desc;
	}



	public boolean isReadOnly() {
		return readOnly;
	}



	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}



	public List<Catastrofe> getSelectedCat() {
		return selectedCat;
	}



	public void setSelectedCat(List<Catastrofe> selectedCat) {
		this.selectedCat = selectedCat;
	}



	public List<Catastrofe> getCatsOng() {
		return catsOng;
	}



	public void setCatsOng(List<Catastrofe> catsOng) {
		this.catsOng = catsOng;
	}



	public List<Catastrofe> getSelectedCatastrofes() {
		return selectedCatastrofes;
	}



	public void setSelectedCatastrofes(List<Catastrofe> selectedCatastrofes) {
		this.selectedCatastrofes = selectedCatastrofes;
	}



	public List<Catastrofe> getCatastrofes() {
		return catastrofes;
	}



	public void setCatastrofes(List<Catastrofe> catastrofes) {
		this.catastrofes = catastrofes;
	}



	public List<Ong> getOngs() {
		return ongs;
	}

	public void setOngs(List<Ong> ongs) {
		this.ongs = ongs;
	}

	public Ong getSelectedOng() {
		return selectedOng;
	}

	public void setSelectedOng(Ong selectedOng) {
		this.selectedOng = selectedOng;
	}

	public List<Ong> getFilteredOng() {
		return filteredOng;
	}

	public void setFilteredOng(List<Ong> filteredOng) {
		this.filteredOng = filteredOng;
	}
	
	public void onRowSelect(){
		System.out.println("cantidad:"+selectedOng.getCatastrofes().size());
		//catsOng=(List<Catastrofe>) selectedOng.getCatastrofes();	
		catsOng=new ArrayList<Catastrofe>(selectedOng.getCatastrofes());
		name=selectedOng.getNombre();
		desc=selectedOng.getDescripcion();
		mail=selectedOng.getPaypalMail();
	}
	
	
	public void addCatastrofes(){
		if(catsOng==null){
			catsOng=new ArrayList<>();
		}
		if(selectedCatastrofes!=null){
			for(Catastrofe f:selectedCatastrofes){
				catsOng.add(f);
			}
		}
		
	}
	
	public void cleanFields(){
		System.out.println("cleanFields********");
		selectedCat=new ArrayList<>();
		selectedCatastrofes=new ArrayList<>();
		catsOng=new ArrayList<>();
		selectedOng=null;
		readOnly=false;
		created=true;
		name="";
		desc="";
		mail="";
		
	}
	public void edit(){
		created=false;
	}
	public void createOng()
	{	
		System.out.println("en create ong");
		Ong ong=new Ong();
		ong.setNombre(name);
		ong.setDescripcion(desc);
		ong.setPaypalMail(mail);
		Set<Catastrofe> set = new HashSet<Catastrofe>(catsOng);
		ong.setCatastrofes(set);
		//ong.setCatastrofes((Set<Catastrofe>) catsOng);
		System.out.println("catAsignadas:"+ong.getCatastrofes().size()+created);
		
		if(created){			
			ongDao.create(ong);
			ongs.add(ong);
			String txt="Se ha creado correctamente.";
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, txt, txt);
	        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
		}else{
			ong.setId(selectedOng.getId());
			ongDao.update(ong);
			String txt="Se ha editado correctamente.";
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, txt, txt);
	        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
		}
		cleanFields();
		
	}
	
	public void cancelar(){
		selectedCat=new ArrayList<>();
		selectedCatastrofes=new ArrayList<>();
		if(catsOng!=null){
			catsOng.clear();
		}		
		selectedOng=null;
		readOnly=true;
		created=true;
		name="";
		desc="";
		mail="";
	}
	
	public void deleteOng(){
		if(selectedOng!=null){
			ongDao.deleteById(selectedOng.getId());
			ongs.remove(selectedOng);
			cleanFields();
			String txt="Se ha eliminado correctamente.";
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, txt, txt);
	        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
		}
	}
	
	public void removeCat(){
		for(Catastrofe cat:selectedCat){
			catsOng.remove(cat);
		}
	}
	
}
