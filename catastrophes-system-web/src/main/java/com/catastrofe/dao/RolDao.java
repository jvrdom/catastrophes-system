package com.catastrofe.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.catastrofe.model.Rol;

/**
 *  DAO for Rol
 */
@Stateless
public class RolDao
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   public void create(Rol entity)
   {
      em.persist(entity);
   }

   public void deleteById(Long id)
   {
      Rol entity = em.find(Rol.class, id);
      if (entity != null)
      {
         em.remove(entity);
      }
   }

   public Rol findById(Long id)
   {
      return em.find(Rol.class, id);
   }

   public Rol update(Rol entity)
   {
      return em.merge(entity);
   }

   public List<Rol> listAll(Integer startPosition, Integer maxResult)
   {
      TypedQuery<Rol> findAllQuery = em.createQuery("SELECT DISTINCT r FROM Rol r ORDER BY r.id", Rol.class);
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
}
