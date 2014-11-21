package com.ssacn.ejb.persistence.jpaController;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import com.ssacn.ejb.persistence.entity.PedidoAyuda;;

@Stateless
public class JpaAyudaController {
	
	@PersistenceContext(unitName = "SSCNjpaPU")
	private EntityManagerFactory emf;
	
	public JpaAyudaController() {		
        emf = Persistence.createEntityManagerFactory("SSCNjpaPU");
    }
	
	 public EntityManager getEntityManager() {
	        return emf.createEntityManager();
	    }

	    public void create(PedidoAyuda pedido) {
	        EntityManager em = null;
	        try {
	        	System.out.println("create***");
	            em = getEntityManager();
	            em.getTransaction().begin();
	            em.persist(pedido);
	            em.getTransaction().commit();
	        } finally {
	            if (em != null) {
	                em.close();
	            }
	           // Logger.getLogger(jpaUserController.class.getName()).log(Level.INFO," | Se creo el pedido " );
	        }
	    }

}
