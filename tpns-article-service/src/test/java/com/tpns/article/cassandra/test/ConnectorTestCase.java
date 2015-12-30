package com.tpns.article.cassandra.test;

public class ConnectorTestCase {

	// @Test
	public void testDataStax() {

		final Connector connector = new Connector();

		connector.connect("localhost");

		connector.insert();

		connector.load();

		connector.close();
	}

	// @Test
	public void testThrift() {

		try {
			final ThriftConnector connector = new ThriftConnector();
			connector.connect();

			connector.insertData("thanserwood", "users", "fname", "thanasis");

			connector.readRow("users", "thanserwood", "fname");

		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
