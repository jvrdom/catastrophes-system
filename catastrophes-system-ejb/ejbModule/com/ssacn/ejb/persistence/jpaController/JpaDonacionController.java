package com.ssacn.ejb.persistence.jpaController;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import com.ssacn.ejb.persistence.entity.DeBienes;
import com.ssacn.ejb.persistence.entity.DeServicios;
import com.ssacn.ejb.persistence.entity.Economica;

@Stateless
public class JpaDonacionController {
	
	@PersistenceContext(unitName = "SSCNjpaPU")
	private EntityManagerFactory emf;
	
	public JpaDonacionController() {		
        emf = Persistence.createEntityManagerFactory("SSCNjpaPU");
    }
	
	 public EntityManager getEntityManager() {
	        return emf.createEntityManager();
	    }

	public void createEconomica(Economica donacion) {
		 EntityManager em = null;
	        try {
	        	System.out.println("create***");
	            em = getEntityManager();
	            em.getTransaction().begin();
	            em.persist(donacion);
	            em.getTransaction().commit();
	        } finally {
	            if (em != null) {
	                em.close();
	            }
	           // Logger.getLogger(jpaUserController.class.getName()).log(Level.INFO," | Se creo la catastrofe " + catastrofe.getNombre());
	        }
		
	}

	public void createDeBienes(DeBienes donacion) {
		 EntityManager em = null;
	        try {
	        	System.out.println("create***");
	            em = getEntityManager();
	            em.getTransaction().begin();
	            em.persist(donacion);
	            em.getTransaction().commit();
	        } finally {
	            if (em != null) {
	                em.close();
	            }
	           // Logger.getLogger(jpaUserController.class.getName()).log(Level.INFO," | Se creo la catastrofe " + catastrofe.getNombre());
	        }
		
	}

	public void createDeservicios(DeServicios donacion) {
		 EntityManager em = null;
	        try {
	        	System.out.println("create***");
	            em = getEntityManager();
	            em.getTransaction().begin();
	            em.persist(donacion);
	            em.getTransaction().commit();
	        } finally {
	            if (em != null) {
	                em.close();
	            }
	           // Logger.getLogger(jpaUserController.class.getName()).log(Level.INFO," | Se creo la catastrofe " + catastrofe.getNombre());
	        }
		
	}

}
