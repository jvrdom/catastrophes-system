package com.catastrofe.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.catastrofe.model.PedidoAyuda;

/**
 *  DAO for PedidoAyuda
 */
@Stateless
public class PedidoAyudaDao
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   public void create(PedidoAyuda entity)
   {
      em.persist(entity);
   }

   public void deleteById(Long id)
   {
      PedidoAyuda entity = em.find(PedidoAyuda.class, id);
      if (entity != null)
      {
         em.remove(entity);
      }
   }

   public PedidoAyuda findById(Long id)
   {
      return em.find(PedidoAyuda.class, id);
   }

   public PedidoAyuda update(PedidoAyuda entity)
   {
      return em.merge(entity);
   }

   public List<PedidoAyuda> listAll(Integer startPosition, Integer maxResult)
   {
      TypedQuery<PedidoAyuda> findAllQuery = em.createQuery("SELECT DISTINCT p FROM PedidoAyuda p LEFT JOIN FETCH p.usuario ORDER BY p.id", PedidoAyuda.class);
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
