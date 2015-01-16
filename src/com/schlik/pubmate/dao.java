package com.schlik.pubmate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import com.schlik.pubmate.PubModel;

public enum dao {
	INSTANCE;
	
	/** A simple endpoint method that takes a name and says Hi back */
	public List<PubModel> listPubs() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from PubModel m");
		List<PubModel> todos = q.getResultList();
		return todos;
	}

	public void add( String name) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			PubModel pub = new PubModel(name );
			em.persist(pub);
			em.close();
		}
	}

	public List<PubModel> getPubs(String userId) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from PubModel t where t.author = :userId");
		q.setParameter("userId", userId);
		List<PubModel> pubs = q.getResultList();
		return pubs;
	}

	public void remove(String name) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			PubModel pub = em.find(PubModel.class, name);
			em.remove(pub);
		} finally {
			em.close();
		}
	}
} 
