package com.catastrofe.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.catastrofe.model.Donacion;

/**
 *  DAO for Donacion
 */
@Stateless
public class DonacionDao
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   public void create(Donacion entity)
   {
      em.persist(entity);
   }

   public void deleteById(Long id)
   {
      Donacion entity = em.find(Donacion.class, id);
      if (entity != null)
      {
         em.remove(entity);
      }
   }

   public Donacion findById(Long id)
   {
      return em.find(Donacion.class, id);
   }

   public Donacion update(Donacion entity)
   {
      return em.merge(entity);
   }

   public List<Donacion> listAll(Integer startPosition, Integer maxResult)
   {
      TypedQuery<Donacion> findAllQuery = em.createQuery("SELECT DISTINCT d FROM Donacion d LEFT JOIN FETCH d.usuario LEFT JOIN FETCH d.ong ORDER BY d.id", Donacion.class);
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