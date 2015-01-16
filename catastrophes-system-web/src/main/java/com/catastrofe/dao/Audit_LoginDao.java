package com.catastrofe.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.catastrofe.model.Audit_Login;

@Stateless
public class Audit_LoginDao {

	@PersistenceContext(unitName = "forge-default")
	private EntityManager em;

	public void create(Audit_Login entity) {
		em.persist(entity);
	}

	public void deleteById(Long id) {
		Audit_Login entity = em.find(Audit_Login.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Audit_Login findById(Long id) {
		return em.find(Audit_Login.class, id);
	}

	public Audit_Login update(Audit_Login entity) {
		return em.merge(entity);
	}

	public List<Audit_Login> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Audit_Login> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT d FROM Audit_Login d ORDER BY d.id",
						Audit_Login.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
	
	public List<Audit_Login> listAllByRol(String rol) {
		TypedQuery<Audit_Login> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT d FROM Audit_Login d WHERE d.usuario_rol=:rol ORDER BY d.id",
						Audit_Login.class).setParameter("rol", rol);
		
		return findAllQuery.getResultList();
	}
	
}
