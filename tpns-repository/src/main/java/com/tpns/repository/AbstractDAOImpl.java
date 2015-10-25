package com.tpns.repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public abstract class AbstractDAOImpl<T, K> implements GenericDAO<T, K> {

	protected static final int MAX_RESULTS = 500;

	private Class<T> type;

	protected abstract EntityManager entityManager();

	@SuppressWarnings("unchecked")
	public AbstractDAOImpl() {
		Type type = getClass().getGenericSuperclass();

		while (!(type instanceof ParameterizedType) || (ParameterizedType.class.cast(type)).getRawType() != AbstractDAOImpl.class) {
			if (type instanceof ParameterizedType) {
				type = ((Class<?>) ((ParameterizedType.class.cast(type))).getRawType()).getGenericSuperclass();
			} else {
				type = ((Class<?>) type).getGenericSuperclass();
			}
		}

		this.type = (Class<T>) (ParameterizedType.class.cast(type)).getActualTypeArguments()[0];
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<T> findAll() {
		EntityManager em = entityManager();
		TypedQuery<List> query = em.createQuery("SELECT T FROM " + type.getSimpleName() + " T", List.class);
		return (Collection<T>) query.setMaxResults(MAX_RESULTS).getResultList();
	}

	@Override
	public T save(T entity) {
		entityManager().persist(entity);
		return entity;
	}

	@Override
	public T find(K key) {
		return entityManager().find(type, key);
	}

	@Override
	public void delete(T entity) {
		entityManager().remove(entity);

	}

}
