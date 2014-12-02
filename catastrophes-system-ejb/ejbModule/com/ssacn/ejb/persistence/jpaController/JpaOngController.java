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
import com.ssacn.ejb.persistence.entity.Administrador;
import com.ssacn.ejb.persistence.entity.Catastrofe;
import com.ssacn.ejb.persistence.entity.Ong;
import com.ssacn.ejb.persistence.entity.PerosnaDesap;

@Stateless
public class JpaOngController {
	
	@PersistenceContext(unitName = "SSCNjpaPU")
	private EntityManagerFactory emf;

	public JpaOngController() {
		emf = Persistence.createEntityManagerFactory("SSCNjpaPU");
	}

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Ong ong) {
		EntityManager em = null;
		try {
			System.out.println("create***");
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(ong);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
	
		}
	}
	
	public void edit(Ong ong) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            ong = em.merge(ong);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ong.getOngId();
                if (findOngById(id) == null) {
                    throw new NonexistentEntityException("The ong with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
            //Logger.getLogger(LoginMB.class.getName()).log(Level.INFO, usuario + " | Se editó el proveedor " + usuario.getNombre());
        }
    }
    
    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ong ong;
            try {
                ong = em.getReference(Ong.class, id);
                ong.getOngId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ong with id " + id + " no longer exists.", enfe);
            }
            em.remove(ong);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public Ong findOngById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ong.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Ong> findOngEntities(int idCatastrofe) {	
        EntityManager em = getEntityManager();
        List<Ong> lista = null;	        
        lista = em.createNamedQuery("Ong.findByCatastofe",Ong.class).setParameter("idCatastrofe", idCatastrofe).getResultList();	        
        return lista;
   
    }
    
    public List<Ong> findOngEntities() {
        //return findOngEntities(true, -1, -1);
    	EntityManager em = getEntityManager();
        List<Ong> lista = null;	        
        lista = em.createNamedQuery("Ong.findAll",Ong.class).getResultList();	        
        return lista;
    }
    
    /* private List<Ong> findOngEntities(boolean all, int maxResults, int firstResult) {
       EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Ong> cq = em.getCriteriaBuilder().createQuery(Ong.class);
            cq.select(cq.from(Ong.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    	
    	
    }*/
    
}
