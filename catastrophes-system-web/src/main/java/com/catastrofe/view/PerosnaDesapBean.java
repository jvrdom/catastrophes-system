package com.catastrofe.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.json.JSONException;
import org.primefaces.event.FileUploadEvent;

import com.catastrofe.dao.PerosnaDesapDao;
import com.catastrofe.dao.UsuarioDao;
import com.catastrofe.model.Catastrofe;
import com.catastrofe.model.Imagen;
import com.catastrofe.model.PerosnaDesap;
import com.catastrofe.model.Rol;
import com.catastrofe.model.Usuario;
import com.catastrofe.utiles.AndroidGCMPushNotification;
import com.catastrofe.utiles.UtilesWeb;

/**
 * Backing bean for PerosnaDesap entities.
 * <p>
 * This class provides CRUD functionality for all PerosnaDesap entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class PerosnaDesapBean implements Serializable
{

   private static final long serialVersionUID = 1L;
   

   @Inject
   private PerosnaDesapDao personaDesDao;
   @Inject
   private UsuarioDao usuarioDao;
   private static final String DESAPARECIDO = "Desaparecido";
   private static final String RESCATISTA = "Rescatista";
   private UtilesWeb utiles;
   private Set<Imagen> imagenesPersonDes;
   private String coordenadas;
   private String estilo;
   private AndroidGCMPushNotification notifications;
   
   public PerosnaDesapBean() {
	   utiles = new UtilesWeb();
	   imagenesPersonDes = new HashSet<Imagen>();
	   Catastrofe catastrofe = (Catastrofe) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("catastrofe");
	   estilo = catastrofe.getCss();
	   notifications = new AndroidGCMPushNotification();
   }
   
   /*
    * Support creating and retrieving PerosnaDesap entities
    */

   private Long id;

   public Long getId()
   {
      return this.id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }
   
   public String getEstilo() {
	  return estilo;
   }

   public void setEstilo(String estilo) {
	  this.estilo = estilo;
   }

   private PerosnaDesap perosnaDesap;

   public PerosnaDesap getPerosnaDesap()
   {
      return this.perosnaDesap;
   }

   @Inject
   private Conversation conversation;
   
   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager entityManager;

   public String create()
   {

      this.conversation.begin();
      return "create?faces-redirect=true";
   }

   public void retrieve()
   {

      if (FacesContext.getCurrentInstance().isPostback())
      {
         return;
      }

      if (this.conversation.isTransient())
      {
         this.conversation.begin();
      }

      if (this.id == null)
      {
         this.perosnaDesap = this.example;
      }
      else
      {
         this.perosnaDesap = findById(getId());
      }
   }

   public PerosnaDesap findById(Long id)
   {
	  return this.personaDesDao.findById(id); 
   }

   /*
    * Support updating and deleting PerosnaDesap entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
        	coordenadas = coordenadas.replace("(", "").replace(")", "");
     		String[] latlong = coordenadas.split(",");
			double lat = Double.parseDouble(latlong[0]);
			double lng = Double.parseDouble(latlong[1]);

			this.perosnaDesap.setStatus(DESAPARECIDO);
        	this.perosnaDesap.setImagenes(this.imagenesPersonDes);
        	this.perosnaDesap.setLatitud(lat);
        	this.perosnaDesap.setLongitud(lng);
        	this.perosnaDesap.setCatastrofe((Catastrofe) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("catastrofe"));
        	this.perosnaDesap.setReportado((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"));
        	this.personaDesDao.create(this.perosnaDesap);
        	
        	//Envio la notificacion a los rescatistas
        	this.sendNotification(RESCATISTA, this.perosnaDesap);
        	
            FacesContext.getCurrentInstance().getExternalContext().redirect("search.xhtml");
            return null;
         }
         else
         {
        	this.personaDesDao.update(this.perosnaDesap);
            return "view?faces-redirect=true&id=" + this.perosnaDesap.getId();
         }
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   public String delete()
   {
      this.conversation.end();

      try
      {
         PerosnaDesap deletableEntity = findById(getId());

         this.entityManager.remove(deletableEntity);
         this.entityManager.flush();
         return "search?faces-redirect=true";
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }
   
   public void handleFileUpload(FileUploadEvent event) {
		try {
			
			Imagen imagen = new Imagen();
			imagen.setImagen(utiles.fileUpload(event.getFile().getFileName(), event.getFile().getInputstream()));
			
			imagenesPersonDes.add(imagen);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesful", event.getFile().getFileName() + " is uploaded.");
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        
		} catch (IOException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail!", "Failed to upload file: " + event.getFile().getFileName() + ", reason: " + e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
   }
   
   private void sendNotification(String rolUsuario, Object object){
	    try {
	    	notifications.enviarNotificaciones("10", usuarioDao.getRegIDs(rolUsuario), object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
   }
   
   public String getCoordenadas() {
	   return coordenadas;
   }

   public void setCoordenadas(String coordenadas) {
	   this.coordenadas = coordenadas;
   }
   
   
   /*
    * Support searching PerosnaDesap entities with pagination
    */

   private int page;
   private long count;
   private List<PerosnaDesap> pageItems;

   private PerosnaDesap example = new PerosnaDesap();

   public int getPage()
   {
      return this.page;
   }

   public void setPage(int page)
   {
      this.page = page;
   }

   public int getPageSize()
   {
      return 10;
   }

   public PerosnaDesap getExample()
   {
      return this.example;
   }

   public void setExample(PerosnaDesap example)
   {
      this.example = example;
   }

   public void search()
   {
      this.page = 0;
   }

   public void paginate()
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

      // Populate this.count

      CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
      Root<PerosnaDesap> root = countCriteria.from(PerosnaDesap.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<PerosnaDesap> criteria = builder.createQuery(PerosnaDesap.class);
      root = criteria.from(PerosnaDesap.class);
      TypedQuery<PerosnaDesap> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<PerosnaDesap> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String nombre = this.example.getNombre();
      if (nombre != null && !"".equals(nombre))
      {
         predicatesList.add(builder.like(root.<String> get("nombre"), '%' + nombre + '%'));
      }
      String apellido = this.example.getApellido();
      if (apellido != null && !"".equals(apellido))
      {
         predicatesList.add(builder.like(root.<String> get("apellido"), '%' + apellido + '%'));
      }
      String telDeContacto = this.example.getTelDeContacto();
      if (telDeContacto != null && !"".equals(telDeContacto))
      {
         predicatesList.add(builder.like(root.<String> get("telDeContacto"), '%' + telDeContacto + '%'));
      }
      String descripcion = this.example.getDescripcion();
      if (descripcion != null && !"".equals(descripcion))
      {
         predicatesList.add(builder.like(root.<String> get("descripcion"), '%' + descripcion + '%'));
      }
      String status = this.example.getStatus();
      if (status != null && !"".equals(status))
      {
         predicatesList.add(builder.like(root.<String> get("status"), '%' + status + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<PerosnaDesap> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back PerosnaDesap entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<PerosnaDesap> getAll()
   {
	   try{
		   CriteriaQuery<PerosnaDesap> criteria = this.entityManager
		            .getCriteriaBuilder().createQuery(PerosnaDesap.class);
		      return this.entityManager.createQuery(
		            criteria.select(criteria.from(PerosnaDesap.class))).getResultList();
	   }catch(Exception ex){
		   ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",ex.getMessage()));
			return null;
	   }
      
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final PerosnaDesapBean ejbProxy = this.sessionContext.getBusinessObject(PerosnaDesapBean.class);

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context,
               UIComponent component, String value)
         {

            return ejbProxy.findById(Long.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context,
               UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((PerosnaDesap) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private PerosnaDesap add = new PerosnaDesap();

   public PerosnaDesap getAdd()
   {
      return this.add;
   }

   public PerosnaDesap getAdded()
   {
      PerosnaDesap added = this.add;
      this.add = new PerosnaDesap();
      return added;
   }
}