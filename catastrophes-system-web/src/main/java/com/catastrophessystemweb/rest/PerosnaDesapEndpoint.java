package com.catastrophessystemweb.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
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
   public List<PerosnaDesap> listAll()
   {
      final List<PerosnaDesap> results = em.createQuery("SELECT DISTINCT p FROM PerosnaDesap p LEFT JOIN FETCH p.catastrofe LEFT JOIN FETCH p.reportado LEFT JOIN FETCH p.imagenes ORDER BY p.id", PerosnaDesap.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(PerosnaDesap entity)
   {
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}