package com.ssacn.ejb.persistence.jpaController;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import com.ssacn.ejb.exceptions.IllegalOrphanException;
import com.ssacn.ejb.exceptions.NonexistentEntityException;
import com.ssacn.ejb.persistence.entity.Rescatista;
import com.ssacn.ejb.persistence.entity.Usuario;

@Stateless
public class JpaRescatistaController {
	@PersistenceContext(unitName = "SSCNjpaPU")
	private EntityManagerFactory emf;
	
	public JpaRescatistaController() {

        emf = Persistence.createEntityManagerFactory("SSCNjpaPU");
    }
	
    

    public EntityManager getEntityManager() {
    	System.out.println("getEntityManager***");
        return emf.createEntityManager();
    }

    public void create(Rescatista user) {
        EntityManager em = null;
        try {
        	System.out.println("create***");
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
           // Logger.getLogger(jpaUserController.class.getName()).log(Level.INFO," | Se creo el usuario " + user.getName());
        }
    }



	public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rescatista user;
            try {
                user = em.getReference(Rescatista.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
	
	public void edit(Rescatista user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            user = em.merge(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getId();
                if (findUserById(id) == null) {
                    throw new NonexistentEntityException("The rescatista with id " + id + " no longer exists.");
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

    
    public Rescatista findUserById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rescatista.class, id);
        } finally {
            em.close();
        }
    }
    
    public Rescatista findUserByLogin(String login) {
        EntityManager em = getEntityManager();
        Rescatista user = null;
        if (em.createNamedQuery("Rescatista.findByLogin").setParameter("login", login).getResultList().size() > 0) {
            user = (Rescatista) em.createNamedQuery("Rescatista.findByLogin").setParameter("login", login).getSingleResult();
        }
        return user;
    }
    
    public boolean existsUsuario(String login, String password){
    	EntityManager em = getEntityManager();
        if (em.createNamedQuery("Rescatista.findByNamePass").setParameter("email", login).setParameter("password", password).getResultList().size() > 0) {
            return true;
        } else{
        	return false;
        }
    }
}
