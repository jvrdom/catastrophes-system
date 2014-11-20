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

@Stateless
public class JpaCatastrofeController {
	@PersistenceContext(unitName = "SSCNjpaPU")
	private EntityManagerFactory emf;
	
	public JpaCatastrofeController() {		
        emf = Persistence.createEntityManagerFactory("SSCNjpaPU");
    }
	
	 public EntityManager getEntityManager() {
	        return emf.createEntityManager();
	    }

	    public void create(Catastrofe catastrofe) {
	        EntityManager em = null;
	        try {
	        	System.out.println("create***");
	            em = getEntityManager();
	            em.getTransaction().begin();
	            em.persist(catastrofe);
	            em.getTransaction().commit();
	        } finally {
	            if (em != null) {
	                em.close();
	            }
	           // Logger.getLogger(jpaUserController.class.getName()).log(Level.INFO," | Se creo la catastrofe " + catastrofe.getNombre());
	        }
	    }
	    
	    public void edit(Catastrofe catastrofe) throws IllegalOrphanException, NonexistentEntityException, Exception {
	        EntityManager em = null;
	        try {
	            em = getEntityManager();
	            em.getTransaction().begin();
	            
	            catastrofe = em.merge(catastrofe);
	            em.getTransaction().commit();
	        } catch (Exception ex) {
	            String msg = ex.getLocalizedMessage();
	            if (msg == null || msg.length() == 0) {
	                Integer id = catastrofe.getCatastrofeId();
	                if (findCatastrofe(id) == null) {
	                    throw new NonexistentEntityException("The catastrofe with id " + id + " no longer exists.");
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
	            Catastrofe catastrofe;
	            try {
	            	catastrofe = em.getReference(Catastrofe.class, id);
	            	catastrofe.getCatastrofeId();
	            } catch (EntityNotFoundException enfe) {
	                throw new NonexistentEntityException("The catastrofe with id " + id + " no longer exists.", enfe);
	            }
	            em.remove(catastrofe);
	            em.getTransaction().commit();
	        } finally {
	            if (em != null) {
	                em.close();
	            }
	        }
	    }
	    
	    public Catastrofe findCatastrofe(Integer id) {
	        EntityManager em = getEntityManager();
	        try {
	            return em.find(Catastrofe.class, id);
	        } finally {
	            em.close();
	        }
	    }
	    
	    public List<Catastrofe> findCatastrofeEntities() {
	        return findCatastrofeEntities(true, -1, -1);
	    }
	    
	    private List<Catastrofe> findCatastrofeEntities(boolean all, int maxResults, int firstResult) {
	        EntityManager em = getEntityManager();
	        try {
	            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	            cq.select(cq.from(Catastrofe.class));
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

}
