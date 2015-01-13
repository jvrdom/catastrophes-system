package com.catastrofe.view;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.catastrofe.dao.PedidoAyudaDao;
import com.catastrofe.model.PedidoAyuda;
import com.catastrofe.model.Usuario;

/**
 * Backing bean for PedidoAyuda entities.
 * <p>
 * This class provides CRUD functionality for all PedidoAyuda entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class PedidoAyudaBean implements Serializable
{

   private static final long serialVersionUID = 1L;
   private String latLong;

   /*
    * Support creating and retrieving PedidoAyuda entities
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

   private PedidoAyuda pedidoAyuda;

   public PedidoAyuda getPedidoAyuda()
   {
      return this.pedidoAyuda;
   }

   @Inject
   private Conversation conversation;
   @Inject
   private PedidoAyudaDao pedidoAyudaDao;

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
         this.pedidoAyuda = this.example;
      }
      else
      {
         this.pedidoAyuda = findById(getId());
      }
   }

   public PedidoAyuda findById(Long id)
   {

      return this.entityManager.find(PedidoAyuda.class, id);
   }

   /*
    * Support updating and deleting PedidoAyuda entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
        	System.out.println("LatLong: " + latLong);
        	latLong = latLong.replace("(", "");
        	latLong = latLong.replace(")", "");

			String[] latlong = latLong.split(",");
			double lat = Double.parseDouble(latlong[0]);
			double lng = Double.parseDouble(latlong[1]);
			
			this.pedidoAyuda.setLatitud(lat);
			this.pedidoAyuda.setLongitud(lng);
			this.pedidoAyuda.setFechaHora(new Date());
			this.pedidoAyuda.setUsuario((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"));
			
			this.pedidoAyudaDao.create(this.pedidoAyuda);
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Su pedido de ayuda ha sido ingresado correctamente",""));
            
            return "search?faces-redirect=true";
         }
         else
         {
        	this.pedidoAyudaDao.update(this.pedidoAyuda);
            return "view?faces-redirect=true&id=" + this.pedidoAyuda.getId();
         }
      }
      catch (Exception e)
      {
    	 System.out.println("Entre al método");
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   public String delete()
   {
      this.conversation.end();

      try
      {
         PedidoAyuda deletableEntity = findById(getId());

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
   
   public String getLatLong() {
	   return latLong;
   }

   public void setLatLong(String latLong) {
	   this.latLong = latLong;
   }

   /*
    * Support searching PedidoAyuda entities with pagination
    */

   private int page;
   private long count;
   private List<PedidoAyuda> pageItems;

   private PedidoAyuda example = new PedidoAyuda();

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

   public PedidoAyuda getExample()
   {
      return this.example;
   }

   public void setExample(PedidoAyuda example)
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
      Root<PedidoAyuda> root = countCriteria.from(PedidoAyuda.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<PedidoAyuda> criteria = builder.createQuery(PedidoAyuda.class);
      root = criteria.from(PedidoAyuda.class);
      TypedQuery<PedidoAyuda> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<PedidoAyuda> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String descripcion = this.example.getDescripcion();
      if (descripcion != null && !"".equals(descripcion))
      {
         predicatesList.add(builder.like(root.<String> get("descripcion"), '%' + descripcion + '%'));
      }
      Usuario usuario = this.example.getUsuario();
      if (usuario != null)
      {
         predicatesList.add(builder.equal(root.get("usuario"), usuario));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<PedidoAyuda> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back PedidoAyuda entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<PedidoAyuda> getAll()
   {

      CriteriaQuery<PedidoAyuda> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(PedidoAyuda.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(PedidoAyuda.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final PedidoAyudaBean ejbProxy = this.sessionContext.getBusinessObject(PedidoAyudaBean.class);

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

            return String.valueOf(((PedidoAyuda) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private PedidoAyuda add = new PedidoAyuda();

   public PedidoAyuda getAdd()
   {
      return this.add;
   }

   public PedidoAyuda getAdded()
   {
      PedidoAyuda added = this.add;
      this.add = new PedidoAyuda();
      return added;
   }
}