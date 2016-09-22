/**
 * 
 */
package org.myretail.config;

import org.testng.annotations.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * @author shsharma
 *
 */

@Test(groups = {"CONFIG"})
@TestExecutionListeners(inheritListeners = false, value = {
		DirtiesContextTestExecutionListener.class,
		PreContextLoadingTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath:spring-test-config.xml" })
public class CassandraMessageRepositoryTest extends AbstractTestNGSpringContextTests  {
	
	@Test
	public void test(){
	}
	
	@Configuration
	static class ContextConfiguration {

		@Bean
		public CassandraClusterFactoryBean cluster() {
			CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
			cluster.setContactPoints("127.0.0.1");
			cluster.setPort(9142);
			
			return cluster;
		}

		@Bean
		public CassandraSessionFactoryBean session() throws Exception {
			CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
			session.setCluster(cluster().getObject());
			session.setConverter(converter());
			session.setSchemaAction(SchemaAction.NONE);
			session.setKeyspaceName("myretail");
			return session;
		}
		
		
		
		@Bean
		public CassandraOperations cassandraTemplate() throws Exception {
			return new CassandraTemplate(session().getObject());
		}

		@Bean
		public CassandraMappingContext mappingContext() {
			return new BasicCassandraMappingContext();
		}

		@Bean
		public CassandraConverter converter() {
			return new MappingCassandraConverter(mappingContext());
		}

	}

}