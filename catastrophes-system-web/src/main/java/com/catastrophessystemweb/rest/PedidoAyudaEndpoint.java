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
import com.catastrofe.model.PedidoAyuda;

/**
 * 
 */
@Stateless
@Path("/pedidoayudas")
public class PedidoAyudaEndpoint
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(PedidoAyuda entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(PedidoAyudaEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      PedidoAyuda entity = em.find(PedidoAyuda.class, id);
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
      TypedQuery<PedidoAyuda> findByIdQuery = em.createQuery("SELECT DISTINCT p FROM PedidoAyuda p LEFT JOIN FETCH p.usuario WHERE p.id = :entityId ORDER BY p.id", PedidoAyuda.class);
      findByIdQuery.setParameter("entityId", id);
      PedidoAyuda entity;
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
   public List<PedidoAyuda> listAll()
   {
      final List<PedidoAyuda> results = em.createQuery("SELECT DISTINCT p FROM PedidoAyuda p LEFT JOIN FETCH p.usuario ORDER BY p.id", PedidoAyuda.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(PedidoAyuda entity)
   {
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}