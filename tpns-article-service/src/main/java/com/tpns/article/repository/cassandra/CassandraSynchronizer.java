package com.tpns.article.repository.cassandra;

import java.util.concurrent.atomic.AtomicInteger;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

@Singleton
@Lock(LockType.READ)
public class CassandraSynchronizer {

	private final AtomicInteger checks = new AtomicInteger();

	@Inject
	private Session session;

	@Schedule(second = "0", minute = "0", hour = "*")
	private void checkOnTheDaughters() {

		final String query = "SELECT article_id FROM tpns.articles";
		final ResultSet result = session.execute(query);

		for (final Row row : result) {

			System.out.println(row.getLong("article_id"));

		}
		checks.incrementAndGet();
	}

}
