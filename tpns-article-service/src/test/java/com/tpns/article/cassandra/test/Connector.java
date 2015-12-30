package com.tpns.article.cassandra.test;

import java.util.List;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

//simple convenience class to wrap connections, just to reduce repeat code
public class Connector {

	Cluster cluster;
	Session session;

	public void connect(final String node) {
		cluster = Cluster.builder().addContactPoint(node).build();
		final Metadata metadata = cluster.getMetadata();
		System.out.printf("Cluster: %s\n", metadata.getClusterName());
		for (final Host host : metadata.getAllHosts()) {
			System.out.printf("Host: %s \n", host.getAddress());
		}
		session = cluster.connect("TPNS");

	}

	public void close() {
		session.close();
	}

	public void load() {

		final String query = "SELECT * FROM tpns.articles";
		final ResultSet result = session.execute(query);

		for (final Row row : result) {

			print(row);

			// ByteBuffer bytes = row.getBytes("value");
			// String value = new String(bytes.array());
			// String name = row.getObject("column1").toString();
			// System.out.println(name + ":\t" + value);
		}

	}

	private void print(final Row row) {
		final Long articleId = row.getLong("article_id");

		System.out.println(articleId);
		final String title = row.getString("title");
		System.out.println(title);
		final String content = row.getString("content");
		System.out.println(content);
	}

	public void insert() {

		final String query = "insert INTO articles (article_id , title , content ) VALUES ( ?,?,?)";

		final PreparedStatement prepare = session.prepare(query);
		final BoundStatement bind = prepare.bind(1L, "asdfa", "asdfasdf");

		final ResultSet execute = session.execute(bind);

		final List<Row> all = execute.all();
		if (null != all) {

			for (final Row row : all) {
				print(row);

			}
		}

	}

}