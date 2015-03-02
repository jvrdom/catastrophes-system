package com.catastrophessystemweb.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.catastrofe.dao.CatastrofeDao;
import com.catastrofe.dao.UsuarioDao;
import com.catastrofe.model.Catastrofe;
import com.catastrofe.model.Imagen;
import com.catastrofe.model.PerosnaDesap;
import com.catastrofe.model.Usuario;

@Stateless
@Path("/perosnadesaps")
public class PerosnaDesapEndpoint
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;
   
   @Inject private CatastrofeDao catasDAO;
   @Inject private UsuarioDao usuDAO;
   @POST
   @Path("/create_new/{catastrofeId:[0-9][0-9]*}/{reportado_id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response create2(PerosnaDesap entity, @PathParam("catastrofeId") int catastrofeId, @PathParam("reportado_id") int reportado_id)
   {
	  Long catastrofeIdLong = Long.valueOf(catastrofeId);
	  Long usuarioIdLong = Long.valueOf(reportado_id);	  
	  Catastrofe catastrofe = catasDAO.findById(catastrofeIdLong);
	  Usuario usuario = usuDAO.findById(usuarioIdLong);
	  entity.setCatastrofe(catastrofe);
	  entity.setReportado(usuario);
	  
	// Agrego La Imagen Del Usuario
	  String urlImagen = entity.getStatus();
	  Imagen imagen = new Imagen();
	  imagen.setImagen(urlImagen);
	  Set<Imagen> imagenes = new HashSet<Imagen>();
	  imagenes.add(imagen);
	  entity.setImagenes(imagenes);
	  entity.setStatus("Desaparecida");

      em.persist(entity);      
      return Response.created(UriBuilder.fromResource(PerosnaDesapEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @POST
   @Path("/create")
   @Consumes("application/json")
   public Response create(PerosnaDesap entity)
   {
	  System.out.println("entidad " + entity);
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
   @Path("/list")   
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