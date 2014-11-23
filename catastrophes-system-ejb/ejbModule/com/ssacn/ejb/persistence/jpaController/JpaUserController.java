package com.ssacn.ejb.persistence.jpaController;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import com.ssacn.ejb.persistence.entity.Administrador;
import com.ssacn.ejb.persistence.entity.Rescatista;
import com.ssacn.ejb.persistence.entity.Usuario;
import com.ssacn.ejb.exceptions.IllegalOrphanException;
import com.ssacn.ejb.exceptions.NonexistentEntityException;
import com.ssacn.ejb.util.PropertiesManager;

@Stateless
public class JpaUserController {
	@PersistenceContext(unitName = "SSCNjpaPU")
	private EntityManagerFactory emf;
	
	public JpaUserController() {
		System.out.println("jpaUserControler***");
        emf = Persistence.createEntityManagerFactory("SSCNjpaPU");
    }
	/*public JpaUserController(String dataBase){
		PropertiesManager config=new PropertiesManager();
		Map<String,String> prop=new HashMap<String,String>();
		String url=config.getPropiedad("url").trim();
		url+=dataBase.trim();

		prop.put("javax.persistence.jdbc.url",url);                
        prop.put("javax.persistence.jdbc.driver",  config.getPropiedad("driver").trim());                        
        prop.put("javax.persistence.jdbc.password", config.getPropiedad("password").trim());                    
        prop.put("javax.persistence.jdbc.user", config.getPropiedad("user").trim());
        
        emf = Persistence.createEntityManagerFactory("SSCNjpaPU",prop);
        
	}*/
    

    public EntityManager getEntityManager() {
    	System.out.println("getEntityManager***");
        return emf.createEntityManager();
    }

    public void create(Usuario user) {
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
    
    public void edit(Usuario user) throws IllegalOrphanException, NonexistentEntityException, Exception {
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
            Usuario user;
            try {
                user = em.getReference(Usuario.class, id);
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
    
    public Usuario findUserById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }
    
    public Usuario findUserByLogin(String login) {
        EntityManager em = getEntityManager();
        Usuario user = null;
        if (em.createNamedQuery("Usuario.findByLogin").setParameter("login", login).getResultList().size() > 0) {
            user = (Usuario) em.createNamedQuery("Usuario.findByLogin").setParameter("login", login).getSingleResult();
        }
        return user;
    }
    
    public Map<String, Boolean> existsUsuario(String login, String password){
    	EntityManager em = getEntityManager();
    	
    	Map<String, Boolean> map = new HashMap<String, Boolean>();

    	Object user = em.createNamedQuery("Persona.findByNamePass").setParameter("email", login).setParameter("password", password).getSingleResult();
        
    	if (user != null) {
    		map.put("Existe", true);
    		
        	if(user instanceof Usuario){
        		map.put("Usuario", true);
        	}
        	if(user instanceof Administrador){
        		map.put("Administrador", true);
        	}
        	if(user instanceof Rescatista){
        		map.put("Rescatista", true);
        	}
        } else{
        	map.put("Existe", false);
        }
        
        return map;
    }

}
