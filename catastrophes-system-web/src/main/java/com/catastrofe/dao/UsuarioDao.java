package com.catastrofe.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
}
