package com.tpns.article.cassandra.test;

import java.nio.ByteBuffer;
import java.util.List;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftConnector {

	TTransport transport = new TSocket("localhost", 9160);

	Cassandra.Client client;

	public void connect() throws Exception {

		final TFramedTransport tFramedTransport = new TFramedTransport(transport);

		final TProtocol proto = new TBinaryProtocol(tFramedTransport);

		client = new Cassandra.Client(proto);

		transport.open();

	}

	public void close() {
		transport.close();
	}

	public void insertData(final String key, final String columnFamily, final String column, final String value) throws Exception {

		System.currentTimeMillis();

		final ByteBuffer keywrap = ByteBuffer.wrap(key.getBytes("UTF-8"));
		final ColumnParent columnFamilyWrap = new ColumnParent(columnFamily);
		final ByteBuffer columnWrap = ByteBuffer.wrap(column.getBytes("UTF-8"));
		client.insert(keywrap, columnFamilyWrap, new Column(columnWrap), ConsistencyLevel.ONE);

	}

	public void readColumn(final String columnFamily, final String rowKey, final String column) throws Exception {

		final ByteBuffer keyWrap = ByteBuffer.wrap(rowKey.getBytes("UTF-8"));
		// ByteBuffer columnFamilyWrap =
		// ByteBuffer.wrap(columnFamily.getBytes("UTF-8"));
		final ColumnPath path = new ColumnPath(column);
		System.out.println(client.get(keyWrap, path, ConsistencyLevel.ONE));

	}

	public void readRow(final String columnFamily, final String rowKey, final String column) throws Exception {

		final SlicePredicate predicate = new SlicePredicate();
		// (null, new SliceRange(new byte[0], new byte[0], false, 10)
		final ColumnParent parent = new ColumnParent(columnFamily);
		final ByteBuffer keyWrap = ByteBuffer.wrap(rowKey.getBytes("UTF-8"));
		final List<ColumnOrSuperColumn> results = client.get_slice(keyWrap, parent, predicate, ConsistencyLevel.ONE);
		for (final ColumnOrSuperColumn result : results) {
			final Column resultColumn = result.column;
			System.out.println(new String(resultColumn.getName(), "UTF-8") + " -> " + new String(resultColumn.getValue(), "UTF-8"));
		}

	}

}
