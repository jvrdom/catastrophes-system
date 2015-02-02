package com.catastrofe.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import com.catastrofe.model.Catastrofe;
import com.catastrofe.model.Rol;
import com.catastrofe.model.Usuario;

/**
 *  DAO for Usuario
 */
@Stateless
public class UsuarioDao
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   public void create(Usuario entity)
   {
      em.persist(entity);
   }

   public void deleteById(Long id)
   {
      Usuario entity = em.find(Usuario.class, id);
      if (entity != null)
      {
         em.remove(entity);
      }
   }

   public Usuario findById(Long id)
   {
      return em.find(Usuario.class, id);
   }

   public Usuario update(Usuario entity)
   {
      return em.merge(entity);
   }

   public List<Usuario> listAll(Integer startPosition, Integer maxResult)
   {
      TypedQuery<Usuario> findAllQuery = em.createQuery("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.rol ORDER BY u.id", Usuario.class);
      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      return findAllQuery.getResultList();
   }
   
   public Usuario findByUserAndPass(String user, String password){
	   if (em.createNamedQuery("Usuario.findByLoginPass").setParameter("usuario", user).setParameter("password", password).getResultList().size() > 0) {
           return (Usuario) em.createNamedQuery("Usuario.findByLoginPass").setParameter("usuario", user).setParameter("password", password).getSingleResult();
       } else {
    	   return null;
       }
   }
   
   public boolean existeUsuario(String user){
	   if (em.createNamedQuery("Usuario.existeUsuario").setParameter("usuario", user).getResultList().size() > 0) {
           return true;
       } else {
    	   return false;
       }
   }
   
   public List<String> getRegIDs(String rolUsuario){
	   List<String> resultList = em.createNamedQuery("Usuario.getRegIdsRescatista").setParameter("nombre", rolUsuario).getResultList();
	   return resultList;
   }

public Usuario findByUser(String mail) {
	if (em.createNamedQuery("Usuario.findByUser").setParameter("user", mail).getResultList().size() > 0) {
        return (Usuario) em.createNamedQuery("Usuario.findByUser").setParameter("user", mail).getSingleResult();
    } else {
 	   return null;
    }
}
}
