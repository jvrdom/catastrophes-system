package com.catastrophessystemweb.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import com.catastrofe.model.PerosnaDesap;

/**
 * 
 */
@Stateless
@Path("/perosnadesaps")
public class PerosnaDesapEndpoint
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(PerosnaDesap entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(PerosnaDesapEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      PerosnaDesap entity = em.find(PerosnaDesap.class, id);
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      em.remove(entity);
      return Response.noContent().build();
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id)
   {
      TypedQuery<PerosnaDesap> findByIdQuery = em.createQuery("SELECT DISTINCT p FROM PerosnaDesap p LEFT JOIN FETCH p.catastrofe LEFT JOIN FETCH p.reportado LEFT JOIN FETCH p.imagenes WHERE p.id = :entityId ORDER BY p.id", PerosnaDesap.class);
      findByIdQuery.setParameter("entityId", id);
      PerosnaDesap entity;
      try
      {
         entity = findByIdQuery.getSingleResult();
      }
      catch (NoResultException nre)
      {
         entity = null;
      }
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   @Produces("application/json")
   public List<PerosnaDesap> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult)
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
      final List<PerosnaDesap> results = findAllQuery.getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(PerosnaDesap entity)
   {
      try
      {
         entity = em.merge(entity);
      }
      catch (OptimisticLockException e)
      {
         return Response.status(Response.Status.CONFLICT).entity(e.getEntity()).build();
      }

      return Response.noContent().build();
   }
}
