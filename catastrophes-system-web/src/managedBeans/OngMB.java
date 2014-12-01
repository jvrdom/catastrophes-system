package managedBeans;

	
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import com.ssacn.ejb.bean.TipoPlan;
import com.ssacn.ejb.business.local.CatastrofeManager;
import com.ssacn.ejb.business.local.PersDesapManager;
import com.ssacn.ejb.business.remote.OngManagerRemote;
import com.ssacn.ejb.business.remote.PersDesapManagerRemote;
import com.ssacn.ejb.persistence.entity.Catastrofe;
import com.ssacn.ejb.persistence.entity.Ong;


@ManagedBean(name="ongMB")
@ViewScoped
public class OngMB {

	@EJB
	private OngManagerRemote ongM;
	
	private List<Ong> ongs;
	private Ong selectedOng;
	private List<Ong> filteredOng;
	private List<Catastrofe> catastrofes;
	private List<Catastrofe> catsOng;
	private List<Catastrofe> selectedCatastrofes;
	private List<Catastrofe> selectedCat;
	private CatastrofeManager catastrofeM;
	private boolean readOnly;
	private boolean created;
	private String name;
	private String desc;
	 
	@PostConstruct
	public void init(){
		try{
			ongs=ongM.getOngs();
			catastrofeM=new CatastrofeManager();
			catsOng=new ArrayList<Catastrofe>();
			//crea catastrofes de prueba
			//catastrofeM.createCatastrofe("nom", TipoPlan.Emergencia, "/opt/url","urlicon" ,"desc", "(10,11)");
			 
		/*catastrofeM.createCatastrofe("nom2", "nplan2", "/opt/url2", "desc2", "(10,12)");
			catastrofeM.createCatastrofe("nom3", "nplan3", "/opt/url3", "desc3", "(10,13)");
			catastrofeM.createCatastrofe("nom4", "nplan4", "/opt/url4", "desc4", "(10,14)");*/
			PersDesapManagerRemote perM=new PersDesapManager();
			/*perM.createPerosnaDesap("nom", "apellido", "123", "descripcion", "status", 1, 51);
			perM.createPerosnaDesap("nom2", "apellido2", "123", "descripcion2", "status2", 1, 51);
			perM.createPerosnaDesap("nom3", "apellido3", "1233", "descripcion3", "status3", 1, 51);
			perM.createPerosnaDesap("nom4", "apellido4", "1234", "descripcion4", "status4", 1, 151);
			perM.createPerosnaDesap("nom5", "apellido5", "123", "descripcion5", "status5", 1, 151);*/
			catastrofes=catastrofeM.getCatastrofes();
			readOnly=true;
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
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
		catsOng=selectedOng.getCatastrofes();		
		name=selectedOng.getNombre();
		desc=selectedOng.getDescripcion();
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
		ong.setCatastrofes(catsOng);
		System.out.println("catAsignadas:"+ong.getCatastrofes().size()+created);
		
		if(created){			
			ongM.create(ong);
			String txt="Se ha creado correctamente.";
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, txt, txt);
	        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
		}else{
			ong.setOngId(selectedOng.getOngId());
			ongM.edit(ong);
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
	}
	
	public void deleteOng(){
		if(selectedOng!=null){
			ongM.delete(selectedOng.getOngId());
			ongs.remove(selectedOng);
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
