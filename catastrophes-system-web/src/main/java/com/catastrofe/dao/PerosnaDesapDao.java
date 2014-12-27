package com.catastrofe.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.catastrofe.model.PerosnaDesap;

/**
 *  DAO for PerosnaDesap
 */
@Stateless
public class PerosnaDesapDao
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   public void create(PerosnaDesap entity)
   {
      em.persist(entity);
   }

   public void deleteById(Long id)
   {
      PerosnaDesap entity = em.find(PerosnaDesap.class, id);
      if (entity != null)
      {
         em.remove(entity);
      }
   }

   public PerosnaDesap findById(Long id)
   {
      return em.find(PerosnaDesap.class, id);
   }

   public PerosnaDesap update(PerosnaDesap entity)
   {
      return em.merge(entity);
   }

   public List<PerosnaDesap> listAll(Integer startPosition, Integer maxResult)
   {
      TypedQuery<PerosnaDesap> findAllQuery = em.createQuery("SELECT DISTINCT p FROM PerosnaDesap p LEFT JOIN FETCH p.catastrofe LEFT JOIN FETCH p.reportado LEFT JOIN FETCH p.imagenes ORDER BY p.id", PerosnaDesap.class);
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
   
   public List<PerosnaDesap> getPersonasByCatastrofe(long idCatastrofe){
	   List<PerosnaDesap> lista = null;
       lista = em.createNamedQuery("PerosnaDesap.findByCatastrofe",PerosnaDesap.class).setParameter("idCatastrofe", idCatastrofe).getResultList();	        
       return lista;
   }
   
   public List<PerosnaDesap> getDesaparecidosByName(long idCatastrofe, String name){
       List<PerosnaDesap> lista = null;
       lista = em.createNamedQuery("PerosnaDesap.findByName",PerosnaDesap.class).setParameter("idCatastrofe", idCatastrofe).setParameter("nombre", name + "%").getResultList();
       return lista;
	}
  
}
