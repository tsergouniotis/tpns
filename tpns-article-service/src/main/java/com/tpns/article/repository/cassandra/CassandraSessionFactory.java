package com.tpns.article.repository.cassandra;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.datastax.driver.core.Session;

@Named
@ApplicationScoped
public class CassandraSessionFactory {

	@Inject
	private CassandraConnectionPool pool;

	@Produces
	@RequestScoped
	public Session createSession() {
		return pool.getCluster().connect();
	}

	public void close(@Disposes Session session) {
		session.close();
	}

}
