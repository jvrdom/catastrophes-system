package com.catastrofe.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.catastrofe.model.Ong;

/**
 *  DAO for Ong
 */
@Stateless
public class OngDao
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   public void create(Ong entity)
   {
      em.persist(entity);
   }

   public void deleteById(Long id)
   {
      Ong entity = em.find(Ong.class, id);
      if (entity != null)
      {
         em.remove(entity);
      }
   }

   public Ong findById(Long id)
   {
      return em.find(Ong.class, id);
   }

   public Ong update(Ong entity)
   {
      return em.merge(entity);
   }

   public List<Ong> listAll(Integer startPosition, Integer maxResult)
   {
      TypedQuery<Ong> findAllQuery = em.createQuery("SELECT DISTINCT o FROM Ong o LEFT JOIN FETCH o.donaciones LEFT JOIN FETCH o.catastrofes ORDER BY o.id", Ong.class);
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
   
   public List<Ong> findOngByCatastrofe(Long id) {
	TypedQuery<Ong> findAllQuery = em.createQuery("SELECT DISTINCT o FROM Ong o LEFT JOIN FETCH o.donaciones LEFT JOIN FETCH o.catastrofes WHERE catastrofes_id=:idCatastrofe ORDER BY o.id", Ong.class).setParameter("idCatastrofe", id);
	return findAllQuery.getResultList();
   }
   
}
