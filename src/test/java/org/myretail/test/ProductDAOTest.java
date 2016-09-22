/**
 * 
 */
package org.myretail.test;

import org.testng.*;
import org.myretail.dao.ProductDAO;
import org.myretail.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


/**
 *
 * @author shsharma
 */

@Test
@ContextConfiguration(locations = { "classpath:spring-test-config.xml" })
public class ProductDAOTest  extends AbstractTestNGSpringContextTests{
	
	@Autowired
	private ProductDAO productDAO;
		
    @Test(groups = {"DAO"})
    @Parameters({"id1", "code_usd"})
	public void testDAO(String id, String currencyCode){
		Product cProduct = productDAO.getProduct(id, currencyCode);		
		Assert.assertNotNull(cProduct);
	}
}
