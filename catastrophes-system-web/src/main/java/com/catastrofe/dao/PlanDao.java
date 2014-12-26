package com.catastrofe.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.catastrofe.model.Plan;

/**
 *  DAO for Plan
 */
@Stateless
public class PlanDao
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   public void create(Plan entity)
   {
      em.persist(entity);
   }

   public void deleteById(Long id)
   {
      Plan entity = em.find(Plan.class, id);
      if (entity != null)
      {
         em.remove(entity);
      }
   }

   public Plan findById(Long id)
   {
      return em.find(Plan.class, id);
   }

   public Plan update(Plan entity)
   {
      return em.merge(entity);
   }

   public List<Plan> listAll(Integer startPosition, Integer maxResult)
   {
      TypedQuery<Plan> findAllQuery = em.createQuery("SELECT DISTINCT p FROM Plan p LEFT JOIN FETCH p.catastrofe ORDER BY p.id", Plan.class);
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
