package com.catastrofe.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.catastrofe.model.Imagen;

/**
 *  DAO for Imagen
 */
@Stateless
public class ImagenDao
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   public void create(Imagen entity)
   {
      em.persist(entity);
   }

   public void deleteById(Long id)
   {
      Imagen entity = em.find(Imagen.class, id);
      if (entity != null)
      {
         em.remove(entity);
      }
   }

   public Imagen findById(Long id)
   {
      return em.find(Imagen.class, id);
   }

   public Imagen update(Imagen entity)
   {
      return em.merge(entity);
   }

   public List<Imagen> listAll(Integer startPosition, Integer maxResult)
   {
      TypedQuery<Imagen> findAllQuery = em.createQuery("SELECT DISTINCT i FROM Imagen i ORDER BY i.id", Imagen.class);
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
