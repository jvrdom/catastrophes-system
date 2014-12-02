package managedBeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ssacn.ejb.business.remote.RescatistaManagerRemote;
import com.ssacn.ejb.exceptions.IllegalOrphanException;
import com.ssacn.ejb.exceptions.NonexistentEntityException;
import com.ssacn.ejb.persistence.entity.Rescatista;
import com.ssacn.ejb.util.UserUtiles;

@ManagedBean(name="rescatistaMB")
@ViewScoped
public class RescatistaMB {
	@EJB
	private RescatistaManagerRemote rescatistaM;
	
	private List<Rescatista> rescatistas;
	private Rescatista selectedRescatista;
	//private List<Ong> filteredOng;


	private boolean readOnly;
	private boolean created;
	private String nombre;
	private String apellido;
	private String email;
	private String password;
	private Date fechaNac;
	private String telefono;
	private String sexo;
	
	@PostConstruct
	public void init(){
		try{
			rescatistas=rescatistaM.getRescatistas();
			readOnly=true;			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	



	public List<Rescatista> getRescatistas() {
		return rescatistas;
	}






	public void setRescatistas(List<Rescatista> rescatistas) {
		this.rescatistas = rescatistas;
	}






	public Rescatista getSelectedRescatista() {
		return selectedRescatista;
	}






	public void setSelectedRescatista(Rescatista selectedRescatista) {
		this.selectedRescatista = selectedRescatista;
	}






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






	public String getPassword() {
		return password;
	}






	public void setPassword(String password) {
		this.password = password;
	}






	public Date getFechaNac() {
		return fechaNac;
	}






	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}






	public String getTelefono() {
		return telefono;
	}






	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}






	public String getSexo() {
		return sexo;
	}






	public void setSexo(String sexo) {
		this.sexo = sexo;
	}






	public boolean isCreated() {
		return created;
	}



	public void setCreated(boolean created) {
		this.created = created;
	}




	public boolean isReadOnly() {
		return readOnly;
	}



	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}



	

	



	

	
	
	public void onRowSelect(){
	
		nombre=selectedRescatista.getNombre();		
		apellido=selectedRescatista.getApellido();
		email=selectedRescatista.getEmail();
		fechaNac=selectedRescatista.getNacimiento();
		telefono=selectedRescatista.getTelefono();
		password=selectedRescatista.getPassword();
		sexo=selectedRescatista.getSexo().toString();
	}
	
	
	
	public void cleanFields(){
		System.out.println("cleanFields********");

		selectedRescatista=null;
		readOnly=false;
		created=true;
		nombre="";		
		apellido="";
		email="";
		fechaNac=null;
		telefono="";
		password="";
		sexo="";
		
	}
	public void edit(){
		created=false;
	}
	public void create()
	{	
		UserUtiles utilesUsuario = new UserUtiles();
		System.out.println("en create ong");
		Rescatista res=new Rescatista();
		res.setNombre(nombre);
		res.setApellido(apellido);
		res.setEmail(email);
		res.setNacimiento(fechaNac);
		res.setPassword(password);
		res.setTelefono(telefono);
		res.setSexo(utilesUsuario.getSexo(sexo));
		
		if(created){			
			rescatistaM.create(res);
			rescatistas.add(res);
			String txt="Se ha creado correctamente.";
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, txt, txt);
	        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
		}else{
			res.setId(selectedRescatista.getId());
			rescatistaM.actualizarRescatista(res);
			String txt="Se ha editado correctamente.";
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, txt, txt);
	        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
		}
		cleanFields();
		
	}
	
	public void cancelar(){
		
		selectedRescatista=null;
		readOnly=true;
		created=true;
		nombre="";		
		apellido="";
		email="";
		fechaNac=null;
		telefono="";
		password="";
		sexo="";
	}
	
	public void deleteRescatista(){
		if(selectedRescatista!=null){
			try {
				rescatistaM.delete(selectedRescatista.getId());
			} catch (IllegalOrphanException | NonexistentEntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				String txt=e.getMessage();
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, txt, txt);
		        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
			}
			rescatistas.remove(selectedRescatista);
			selectedRescatista=null;
			String txt="Se ha eliminado correctamente.";
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, txt, txt);
	        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
		}
	}
	
	
}
