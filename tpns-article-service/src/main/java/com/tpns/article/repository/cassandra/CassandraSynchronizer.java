package com.tpns.article.repository.cassandra;

import java.util.UUID;
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

	@Schedule(second = "*", minute = "*", hour = "*")
	private void checkOnTheDaughters() {

		String query = "SELECT article_id FROM tpns.articles";
		ResultSet result = session.execute(query);

		for (Row row : result) {

			System.out.println(row.getLong("article_id"));

		}
		checks.incrementAndGet();
	}

}
