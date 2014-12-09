package com.catastrofe.view;

import java.io.Serializable;
import java.util.ArrayList;
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

import com.catastrofe.model.Catastrofe;

/**
 * Backing bean for Catastrofe entities.
 * <p>
 * This class provides CRUD functionality for all Catastrofe entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class CatastrofeBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving Catastrofe entities
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

   private Catastrofe catastrofe;

   public Catastrofe getCatastrofe()
   {
      return this.catastrofe;
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
         this.catastrofe = this.example;
      }
      else
      {
         this.catastrofe = findById(getId());
      }
   }

   public Catastrofe findById(Long id)
   {

      return this.entityManager.find(Catastrofe.class, id);
   }

   /*
    * Support updating and deleting Catastrofe entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.catastrofe);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.catastrofe);
            return "view?faces-redirect=true&id=" + this.catastrofe.getId();
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
         Catastrofe deletableEntity = findById(getId());

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

   /*
    * Support searching Catastrofe entities with pagination
    */

   private int page;
   private long count;
   private List<Catastrofe> pageItems;

   private Catastrofe example = new Catastrofe();

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

   public Catastrofe getExample()
   {
      return this.example;
   }

   public void setExample(Catastrofe example)
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
      Root<Catastrofe> root = countCriteria.from(Catastrofe.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<Catastrofe> criteria = builder.createQuery(Catastrofe.class);
      root = criteria.from(Catastrofe.class);
      TypedQuery<Catastrofe> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<Catastrofe> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String nombre = this.example.getNombre();
      if (nombre != null && !"".equals(nombre))
      {
         predicatesList.add(builder.like(root.<String> get("nombre"), '%' + nombre + '%'));
      }
      String descripcion = this.example.getDescripcion();
      if (descripcion != null && !"".equals(descripcion))
      {
         predicatesList.add(builder.like(root.<String> get("descripcion"), '%' + descripcion + '%'));
      }
      String logo = this.example.getLogo();
      if (logo != null && !"".equals(logo))
      {
         predicatesList.add(builder.like(root.<String> get("logo"), '%' + logo + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<Catastrofe> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back Catastrofe entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<Catastrofe> getAll()
   {

      CriteriaQuery<Catastrofe> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(Catastrofe.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(Catastrofe.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final CatastrofeBean ejbProxy = this.sessionContext.getBusinessObject(CatastrofeBean.class);

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

            return String.valueOf(((Catastrofe) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private Catastrofe add = new Catastrofe();

   public Catastrofe getAdd()
   {
      return this.add;
   }

   public Catastrofe getAdded()
   {
      Catastrofe added = this.add;
      this.add = new Catastrofe();
      return added;
   }
}