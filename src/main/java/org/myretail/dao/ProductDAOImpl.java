/**
 * 
 */
package org.myretail.dao;

import org.myretail.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.apache.log4j.Logger;

/**
 *
 * @author shsharma
 */

@Repository
public class ProductDAOImpl implements ProductDAO{
	
	final static Logger logger = Logger.getLogger(ProductDAOImpl.class);
	
	@Autowired
	private CassandraOperations cassandraTemplate;
	
	@Override
	public Product getProduct(String id, String currencyCode){
		Select select = QueryBuilder.select().all().from("items").
				where(QueryBuilder.eq("id", id)).
				and(QueryBuilder.eq("currency_code", currencyCode)).orderBy(QueryBuilder.asc("currency_code"));
		logger.debug("[getProduct] Product to be found for id " + id);
		return cassandraTemplate.selectOne(select, Product.class);
	}

	@Override
	public void updateProduct(Product product) {
		logger.debug("[updateProduct] Product to be updated " + product);
		cassandraTemplate.insert(product);
	}


	@Override
	public void addProduct(Product product) {
		logger.debug("[addProduct] Product to be added " + product);
		cassandraTemplate.insert(product);
	}
	
	
}
