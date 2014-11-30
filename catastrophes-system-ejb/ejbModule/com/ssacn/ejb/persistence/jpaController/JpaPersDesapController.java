package com.ssacn.ejb.persistence.jpaController;

import java.util.List;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import com.ssacn.ejb.persistence.entity.PerosnaDesap;


@Stateless
public class JpaPersDesapController {
	@PersistenceContext(unitName = "SSCNjpaPU")
	private EntityManagerFactory emf;

	public JpaPersDesapController() {
		emf = Persistence.createEntityManagerFactory("SSCNjpaPU");
	}

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(PerosnaDesap pers) {
		EntityManager em = null;
		try {
			System.out.println("create***");
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(pers);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
			// Logger.getLogger(jpaUserController.class.getName()).log(Level.INFO," | Se creo la PerosnaDesap "
			// + catastrofe.getNombre());
		}
	}
	
	public List<PerosnaDesap> findPersDesapEntities() {
        return findPersDesapEntities(true, -1, -1);
    }
	
	public List<PerosnaDesap> findPersDesapEntities(int idCatastrofe) {	
	        EntityManager em = getEntityManager();
	        List<PerosnaDesap> lista = null;	        
	        lista = em.createNamedQuery("PerosnaDesap.findByCatastrofe",PerosnaDesap.class).setParameter("idCatastrofe", idCatastrofe).getResultList();	        
	        return lista;
	   
    }
	
	public List<PerosnaDesap> findPersonDesapByName(int idCatastrofe, String name){
		EntityManager em = getEntityManager();
        List<PerosnaDesap> lista = null;
        lista = em.createNamedQuery("PerosnaDesap.findByName",PerosnaDesap.class).setParameter("idCatastrofe", idCatastrofe).setParameter("nombre", name + "%").getResultList();
        return lista;
	}
	
    private List<PerosnaDesap> findPersDesapEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PerosnaDesap.class));
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
