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
import com.catastrofe.model.Usuario;

/**
 * 
 */
@Stateless
@Path("/usuarios")
public class UsuarioEndpoint
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(Usuario entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(UsuarioEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Usuario entity = em.find(Usuario.class, id);
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
      TypedQuery<Usuario> findByIdQuery = em.createQuery("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.rol WHERE u.id = :entityId ORDER BY u.id", Usuario.class);
      findByIdQuery.setParameter("entityId", id);
      Usuario entity;
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
   public List<Usuario> listAll()
   {
      final List<Usuario> results = em.createQuery("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.rol ORDER BY u.id", Usuario.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(Usuario entity)
   {
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}