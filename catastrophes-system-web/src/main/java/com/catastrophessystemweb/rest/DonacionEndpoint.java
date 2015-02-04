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
import com.catastrofe.model.Donacion;

/**
 * 
 */
@Stateless
@Path("/donacions")
public class DonacionEndpoint
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(Donacion entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(DonacionEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Donacion entity = em.find(Donacion.class, id);
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
      TypedQuery<Donacion> findByIdQuery = em.createQuery("SELECT DISTINCT d FROM Donacion d LEFT JOIN FETCH d.usuario LEFT JOIN FETCH d.ong WHERE d.id = :entityId ORDER BY d.id", Donacion.class);
      findByIdQuery.setParameter("entityId", id);
      Donacion entity;
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
   public List<Donacion> listAll()
   {
      final List<Donacion> results = em.createQuery("SELECT DISTINCT d FROM Donacion d LEFT JOIN FETCH d.usuario LEFT JOIN FETCH d.ong ORDER BY d.id", Donacion.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(Donacion entity)
   {
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}