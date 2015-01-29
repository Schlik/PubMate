package com.schlik.pubmate;

import com.schlik.pubmate.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "pubmodelendpoint", namespace = @ApiNamespace(ownerDomain = "schlik.com", ownerName = "schlik.com", packagePath = "pubmate"))
public class PubModelEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listPubModel")
	public CollectionResponse<PubModel> listPubModel(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<PubModel> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from PubModel as PubModel");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<PubModel>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (PubModel obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<PubModel> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getPubModel")
	public PubModel getPubModel(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		PubModel pubmodel = null;
		try {
			pubmodel = mgr.find(PubModel.class, id);
		} finally {
			mgr.close();
		}
		return pubmodel;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param pubmodel the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertPubModel")
	public PubModel insertPubModel(PubModel pubmodel) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsPubModel(pubmodel)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(pubmodel);
		} finally {
			mgr.close();
		}
		return pubmodel;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param pubmodel the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updatePubModel")
	public PubModel updatePubModel(PubModel pubmodel) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsPubModel(pubmodel)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(pubmodel);
		} finally {
			mgr.close();
		}
		return pubmodel;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removePubModel")
	public void removePubModel(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			PubModel pubmodel = mgr.find(PubModel.class, id);
			mgr.remove(pubmodel);
		} finally {
			mgr.close();
		}
	}

	private boolean containsPubModel(PubModel pubmodel) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			PubModel item = mgr.find(PubModel.class, pubmodel.getId());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
