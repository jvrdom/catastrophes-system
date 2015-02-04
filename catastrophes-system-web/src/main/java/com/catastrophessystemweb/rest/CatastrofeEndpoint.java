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
import com.catastrofe.model.Catastrofe;

/**
 * 
 */
@Stateless
@Path("/catastrofes")
public class CatastrofeEndpoint
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(Catastrofe entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(CatastrofeEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Catastrofe entity = em.find(Catastrofe.class, id);
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
      TypedQuery<Catastrofe> findByIdQuery = em.createQuery("SELECT DISTINCT c FROM Catastrofe c LEFT JOIN FETCH c.imagenes LEFT JOIN FETCH c.novedades LEFT JOIN FETCH c.planes WHERE c.id = :entityId ORDER BY c.id", Catastrofe.class);
      findByIdQuery.setParameter("entityId", id);
      Catastrofe entity;
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
   @Path("/obtener")
   @Produces("application/json")
   public List<Catastrofe> listAll()
   {
      final List<Catastrofe> results = em.createQuery("SELECT DISTINCT c FROM Catastrofe c LEFT JOIN FETCH c.imagenes LEFT JOIN FETCH c.novedades LEFT JOIN FETCH c.planes ORDER BY c.id", Catastrofe.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(Catastrofe entity)
   {
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}