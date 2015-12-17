package com.tpns.article.cassandra.test;

import org.junit.Test;

public class ConnectorTestCase {

	@Test
	public void testDataStax() {

		Connector connector = new Connector();

		connector.connect("localhost");

		connector.insert();

		connector.load();

		connector.close();
	}

	//	@Test
	public void testThrift() {

		try {
			ThriftConnector connector = new ThriftConnector();
			connector.connect();

			connector.insertData("thanserwood", "users", "fname", "thanasis");

			connector.readRow("users", "thanserwood", "fname");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
