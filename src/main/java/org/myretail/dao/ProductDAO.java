/**
 * 
 */
package org.myretail.dao;

import org.myretail.model.Product;

/**
 * @author shsharma
 *
 */
public interface ProductDAO {
	public Product getProduct(String id, String currencyCode);
	
	public void updateProduct(Product product);

	public void addProduct(Product product);
}
