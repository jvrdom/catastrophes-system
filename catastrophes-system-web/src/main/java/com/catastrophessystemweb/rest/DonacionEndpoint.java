package com.catastrophessystemweb.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import com.catastrofe.dao.UsuarioDao;
import com.catastrofe.model.Donacion;
import com.catastrofe.model.Usuario;
import com.catastrofe.model.tipoDonacion;

/**
 * 
 */
@Stateless
@Path("/donacions")
public class DonacionEndpoint
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   @Inject private UsuarioDao usuDAO;
   @POST
   @Path("/{usuario_id:[0-9][0-9]*}/{tipo_donacion:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response create(Donacion entity,@PathParam("usuario_id") int usuario_id, @PathParam("tipo_donacion") int tipo_donacion)
   {
	  Long usuarioIdLong = Long.valueOf(usuario_id);	
	  Usuario usuario = usuDAO.findById(usuarioIdLong);
	  entity.setUsuario(usuario);
	  if(tipo_donacion==1) entity.setTipo(tipoDonacion.Económica);
	  else if(tipo_donacion==2) entity.setTipo(tipoDonacion.DeBienes);
	  else if(tipo_donacion==3) entity.setTipo(tipoDonacion.DeServicios);	 
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