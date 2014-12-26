package com.catastrofe.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.catastrofe.model.Novedades;

/**
 *  DAO for Novedades
 */
@Stateless
public class NovedadesDao
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   public void create(Novedades entity)
   {
      em.persist(entity);
   }

   public void deleteById(Long id)
   {
      Novedades entity = em.find(Novedades.class, id);
      if (entity != null)
      {
         em.remove(entity);
      }
   }

   public Novedades findById(Long id)
   {
      return em.find(Novedades.class, id);
   }

   public Novedades update(Novedades entity)
   {
      return em.merge(entity);
   }

   public List<Novedades> listAll(Integer startPosition, Integer maxResult)
   {
      TypedQuery<Novedades> findAllQuery = em.createQuery("SELECT DISTINCT n FROM Novedades n ORDER BY n.id", Novedades.class);
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
