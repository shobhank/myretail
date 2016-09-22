/**
 * 
 */
package org.myretail.config;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import java.io.IOException;
/**
 * @author shsharma
 *
 */

public class PreContextLoadingTestExecutionListener extends DependencyInjectionTestExecutionListener
{
    protected void injectDependencies( final TestContext testContext ) throws Exception
    {
        doInitBeforeContextIsLoaded();
        doCreateKeySpace();
        doCreateTable();
        doCreateDataSet();
    }
 
    /**
	void * 
	 */
	private void doCreateDataSet() {
		Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").withPort(9142).build();
		Session session = cluster.connect("myretail");
		session.execute("INSERT INTO items (id, currency_code , value ) VALUES ( '0321356683','USD',39.99)");
		session.close();
	}

	/**
	void * 
	 */
	private void doCreateTable() {
		Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").withPort(9142).build();
		Session session = cluster.connect("myretail");
		session.execute("CREATE TABLE items (id text,currency_code text,value double,PRIMARY KEY (id, currency_code))");
		session.close();
	}

	/**
	void * 
	 */
	private void doCreateKeySpace() {
		Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").withPort(9142).build();
		Session session = cluster.connect();
		session.execute("CREATE KEYSPACE myretail WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };");
		session.close();
	}

	private void doInitBeforeContextIsLoaded()
    {
        try {
        	System.out.println("Starting embedded cassandra");
			EmbeddedCassandraServerHelper.startEmbeddedCassandra();
			System.out.println("Started embedded cassandra");
		} catch (ConfigurationException | TTransportException | IOException
				| InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}