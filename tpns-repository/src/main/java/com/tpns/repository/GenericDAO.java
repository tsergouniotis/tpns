package com.tpns.repository;

import java.util.List;

/**
 * The Interface GenericDAO.
 *
 * @param <T> the generic type
 * @param <K> the key type
 */
public interface GenericDAO<T, K> {

	/**
	 * Save.
	 *
	 * @param entity the entity
	 * @return the t
	 */
	T save(T entity);

	/**
	 * Find.
	 *
	 * @param key the key
	 * @return the t
	 */
	T find(K key);

	/**
	 * Find all.
	 *
	 * @return the collection
	 */
	List<T> findAll();

	/**
	 * Delete.
	 *
	 * @param entity the entity
	 */
	void delete(T entity);

}
