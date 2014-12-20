package com.catastrofe.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import com.catastrofe.model.Catastrofe;

/**
 *  DAO for Catastrofe
 */
@Stateless
public class CatastrofeDao
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   public void create(Catastrofe entity)
   {
      em.persist(entity);
   }

   public void deleteById(Long id)
   {
      Catastrofe entity = em.find(Catastrofe.class, id);
      if (entity != null)
      {
         em.remove(entity);
      }
   }

   public Catastrofe findById(Long id)
   {
      return em.find(Catastrofe.class, id);
   }

   public Catastrofe update(Catastrofe entity)
   {
      return em.merge(entity);
   }

   public List<Catastrofe> listAll(Integer startPosition, Integer maxResult)
   {
      TypedQuery<Catastrofe> findAllQuery = em.createQuery("SELECT DISTINCT c FROM Catastrofe c LEFT JOIN FETCH c.imagenes LEFT JOIN FETCH c.novedades LEFT JOIN FETCH c.planes ORDER BY c.id", Catastrofe.class);
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
   
   public List<Catastrofe> getAll()
   {

      CriteriaQuery<Catastrofe> criteria = em.getCriteriaBuilder().createQuery(Catastrofe.class);
      return em.createQuery(criteria.select(criteria.from(Catastrofe.class))).getResultList();
   }
}
