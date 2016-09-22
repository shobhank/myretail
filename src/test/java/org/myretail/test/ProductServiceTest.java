/**
 * 
 */
package org.myretail.test;

import org.myretail.exception.ProductNotFoundException;
import org.myretail.model.CurrentPrice;
import org.myretail.model.ProductDTO;
import org.myretail.service.ProductService;
import org.myretail.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 *
 * @author shsharma
 */

@Test
@ContextConfiguration(locations = { "classpath:spring-test-config.xml" })
public class ProductServiceTest extends AbstractTestNGSpringContextTests{
	
	
	@Autowired
	private ProductService productService;
	
    @Parameters({"id1","code_usd"})
	@Test(groups = {"SERVICE"})
	public void testValid(String id, String currencyCode) throws ProductNotFoundException{
		ProductDTO product = productService.getProduct(id, currencyCode);	
		Assert.assertNotNull(product);
	}
	
    @Parameters({"id_invalid","code_usd"})
	@Test(groups = {"SERVICE"}, expectedExceptions = ProductNotFoundException.class)
	public void testNotFoundProduct(String id, String currencyCode) throws ProductNotFoundException{
		productService.getProduct(id, currencyCode);	
	}
	
    @Test(groups = {"SERVICE"})
    @Parameters({"id1","name1","code_usd","value4"})
	public void testUpdateProductPrice(String id, String name, String currencyCode, double value) throws ProductNotFoundException{
		double newPriceUSD = value;
		ProductDTO newProduct = new ProductDTO();
		newProduct.setId(id);
		newProduct.setName(name);
		CurrentPrice newPrice = new CurrentPrice();
		newPrice.setCurrencyCode(currencyCode);
		newPrice.setValue(newPriceUSD);
		newProduct.setCurrentPrice(newPrice);
		productService.updateProductPrice(id, newProduct);
		
		ProductDTO response = productService.getProduct(id, currencyCode);
		Assert.assertEquals(response.getCurrentPrice().getValue(), newPriceUSD);
	}

    @Parameters({"id_invalid","name1","code_usd","value4"})
	@Test(groups = {"SERVICE"}, expectedExceptions = ProductNotFoundException.class)
	public void testUpdateInvalidProductPrice(String id, String name, String currencyCode, double value) throws ProductNotFoundException{
		double newPriceUSD = value;
		ProductDTO newProduct = new ProductDTO();
		newProduct.setId(id);
		newProduct.setName(name);
		CurrentPrice newPrice = new CurrentPrice();
		newPrice.setCurrencyCode(currencyCode);
		newPrice.setValue(newPriceUSD);
		newProduct.setCurrentPrice(newPrice);
		productService.updateProductPrice(id, newProduct);
	}

    @Parameters({"id2","name2"})
	@Test(groups = {"SERVICE"})
	public void getBookName(String id, String nameExpected){
		String nameActual = ((ProductServiceImpl)productService).getProductName(id);
		Assert.assertEquals(nameActual, nameExpected);
	}
}
