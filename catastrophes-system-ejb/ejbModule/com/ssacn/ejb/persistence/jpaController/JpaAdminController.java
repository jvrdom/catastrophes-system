package com.ssacn.ejb.persistence.jpaController;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import com.ssacn.ejb.exceptions.IllegalOrphanException;
import com.ssacn.ejb.exceptions.NonexistentEntityException;
import com.ssacn.ejb.persistence.entity.Administrador;


@Stateless
public class JpaAdminController {
	@PersistenceContext(unitName = "SSCNjpaPU")
	private EntityManagerFactory emf;
	
	public JpaAdminController() {
		System.out.println("jpaUserControler***");
        emf = Persistence.createEntityManagerFactory("SSCNjpaPU");
    }
	

    public EntityManager getEntityManager() {
    	System.out.println("getEntityManager***");
        return emf.createEntityManager();
    }

    public void create(Administrador user) {
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
    
    public void edit(Administrador user) throws IllegalOrphanException, NonexistentEntityException, Exception {
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
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Administrador user;
            try {
                user = em.getReference(Administrador.class, id);
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
    
    public Administrador findUserById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Administrador.class, id);
        } finally {
            em.close();
        }
    }
    
    public Administrador findUserByLogin(String login) {
        EntityManager em = getEntityManager();
        Administrador user = null;
        if (em.createNamedQuery("Administrador.findByLogin").setParameter("login", login).getResultList().size() > 0) {
            user = (Administrador) em.createNamedQuery("Administrador.findByLogin").setParameter("login", login).getSingleResult();
        }
        return user;
    }
    
    public boolean existsUsuario(String login, String password){
    	EntityManager em = getEntityManager();
        if (em.createNamedQuery("Administrador.findByNamePass").setParameter("email", login).setParameter("password", password).getResultList().size() > 0) {
            return true;
        } else{
        	return false;
        }
    }

}
