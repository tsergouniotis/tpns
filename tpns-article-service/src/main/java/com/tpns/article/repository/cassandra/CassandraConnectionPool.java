package com.tpns.article.repository.cassandra;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.tpns.article.conf.ApplicationParameter;

@Singleton
public class CassandraConnectionPool {

	@Inject
	@ApplicationParameter("cassandra.contact.point.address")
	private String address;

	@Inject
	@ApplicationParameter("cassandra.core.connections.per.host")
	private String coreConnectionsPerHost;

	@Inject
	@ApplicationParameter("cassandra.max.connections.per.host")
	private String maxConnectionsPerHost;

	private Cluster cluster;

	public Cluster getCluster() {
		return cluster;
	}

	@PostConstruct
	public void postConstruct() {

		final PoolingOptions poolingOptions = new PoolingOptions();
		poolingOptions.setCoreConnectionsPerHost(HostDistance.LOCAL, Integer.valueOf(coreConnectionsPerHost))
				.setMaxConnectionsPerHost(HostDistance.LOCAL, Integer.valueOf(maxConnectionsPerHost))
				.setCoreConnectionsPerHost(HostDistance.REMOTE, Integer.valueOf(coreConnectionsPerHost))
				.setMaxConnectionsPerHost(HostDistance.REMOTE, Integer.valueOf(maxConnectionsPerHost));

		cluster = Cluster.builder().addContactPoint(address).withPoolingOptions(poolingOptions).build();

	}

}
