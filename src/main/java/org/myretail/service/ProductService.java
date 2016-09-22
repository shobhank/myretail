/**
 * 
 */
package org.myretail.service;

import org.myretail.exception.ProductNotFoundException;
import org.myretail.model.ProductDTO;

/**
 * @author shsharma
 *
 */
public interface ProductService {
	public ProductDTO getProduct(String id, String currencyCode) throws ProductNotFoundException;

	public void updateProductPrice(String id, ProductDTO product) throws ProductNotFoundException;

	public void addProduct(ProductDTO product);
}
