package com.ssacn.ejb.persistence.jpaController;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import com.ssacn.ejb.exceptions.IllegalOrphanException;
import com.ssacn.ejb.exceptions.NonexistentEntityException;
import com.ssacn.ejb.persistence.entity.Catastrofe;
import com.ssacn.ejb.persistence.entity.PerosnaDesap;
import com.ssacn.ejb.persistence.entity.Plan;

@Stateless
public class JpaPlanController {
	@PersistenceContext(unitName = "SSCNjpaPU")
	private EntityManagerFactory emf;
	
	public JpaPlanController() {		
        emf = Persistence.createEntityManagerFactory("SSCNjpaPU");
    }
	
	 public EntityManager getEntityManager() {
	        return emf.createEntityManager();
	    }

	    public void create(Plan plan) {
	        EntityManager em = null;
	        try {
	        	System.out.println("create***");
	            em = getEntityManager();
	            em.getTransaction().begin();
	            em.persist(plan);
	            em.getTransaction().commit();
	        } finally {
	            if (em != null) {
	                em.close();
	            }
	           // Logger.getLogger(jpaUserController.class.getName()).log(Level.INFO," | Se creo la catastrofe " + catastrofe.getNombre());
	        }
	    }
	    
	    public void edit(Plan plan) throws IllegalOrphanException, NonexistentEntityException, Exception {
	        EntityManager em = null;
	        try {
	            em = getEntityManager();
	            em.getTransaction().begin();
	            
	            plan = em.merge(plan);
	            em.getTransaction().commit();
	        } catch (Exception ex) {
	            String msg = ex.getLocalizedMessage();
	            if (msg == null || msg.length() == 0) {
	                Integer id = plan.getPlanId();
	                if (findPlan(id) == null) {
	                    throw new NonexistentEntityException("The plan with id " + id + " no longer exists.");
	                }
	            }
	            throw ex;
	        } finally {
	            if (em != null) {
	                em.close();
	            }
	            //Logger.getLogger(LoginMB.class.getName()).log(Level.INFO, catastrofe + " | Se editó el proveedor " + catastrofe.getNombre());
	        }
	    }
	    
	    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
	        EntityManager em = null;
	        try {
	            em = getEntityManager();
	            em.getTransaction().begin();
	            Plan plan;
	            try {
	            	plan = em.getReference(Plan.class, id);
	            	plan.getPlanId();
	            } catch (EntityNotFoundException enfe) {
	                throw new NonexistentEntityException("The plan with id " + id + " no longer exists.", enfe);
	            }
	            em.remove(plan);
	            em.getTransaction().commit();
	        } finally {
	            if (em != null) {
	                em.close();
	            }
	        }
	    }
	    
	    public Plan findPlan(Integer id) {
	        EntityManager em = getEntityManager();
	        try {
	            return em.find(Plan.class, id);
	        } finally {
	            em.close();
	        }
	    }
	    
	    public List<Plan> findPlanEntities() {
	        return findPlanEntities(true, -1, -1);
	    }
	    
	    private List<Plan> findPlanEntities(boolean all, int maxResults, int firstResult) {
	        EntityManager em = getEntityManager();
	        try {
	            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	            cq.select(cq.from(Plan.class));
	            Query q = em.createQuery(cq);
	            if (!all) {
	                q.setMaxResults(maxResults);
	                q.setFirstResult(firstResult);
	            }
	            return q.getResultList();
	        } finally {
	            em.close();
	        }
	    }
	    
	    public Plan findPlanByIdCatastrofe(int idCatastrofe, String tipo) {	
	        EntityManager em = getEntityManager();
	        Plan plan = null;	        
	        plan = em.createNamedQuery("Plan.findByIdCatastofe",Plan.class).setParameter("idCatastrofe", idCatastrofe).setParameter("tipo", tipo).getSingleResult();	        
	        return plan;
	   
	    }
	    
}
