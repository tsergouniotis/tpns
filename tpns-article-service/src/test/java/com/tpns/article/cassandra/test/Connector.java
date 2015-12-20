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

	public void connect(String node) {
		cluster = Cluster.builder().addContactPoint(node).build();
		Metadata metadata = cluster.getMetadata();
		System.out.printf("Cluster: %s\n", metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			System.out.printf("Host: %s \n", host.getAddress());
		}
		session = cluster.connect("TPNS");

	}

	public void close() {
		session.close();
	}

	public void load() {

		String query = "SELECT * FROM tpns.articles";
		ResultSet result = session.execute(query);

		for (Row row : result) {

			print(row);

			// ByteBuffer bytes = row.getBytes("value");
			// String value = new String(bytes.array());
			// String name = row.getObject("column1").toString();
			// System.out.println(name + ":\t" + value);
		}

	}

	private void print(Row row) {
		Long articleId = row.getLong("article_id");

		System.out.println(articleId);
		String title = row.getString("title");
		System.out.println(title);
		String content = row.getString("content");
		System.out.println(content);
	}

	public void insert() {

		String query = "insert INTO articles (article_id , title , content ) VALUES ( ?,?,?)";

		PreparedStatement prepare = session.prepare(query);
		BoundStatement bind = prepare.bind(1L, "asdfa", "asdfasdf");

		ResultSet execute = session.execute(bind);

		List<Row> all = execute.all();
		if (null != all) {

			for (Row row : all) {
				print(row);

			}
		}

	}

}