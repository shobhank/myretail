/**
 * 
 */
package org.myretail.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.myretail.model.CurrentPrice;
import org.myretail.model.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author shsharma
 */

@Test
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@EnableWebMvc
@ContextConfiguration(locations = { "classpath:spring-test-config.xml" })
public class ProductControllerTest extends AbstractTestNGSpringContextTests{
		
    @Autowired
    private WebApplicationContext webApplicationContext;
    
	@BeforeMethod(groups = {"CONTROLLER"})
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }
    
	private MockMvc mockMvc;

    @Test(groups = {"CONTROLLER"})
    @Parameters({"id1"})
    public void findProduct(String id) throws Exception {
        mockMvc.perform(get("/v1/products/"+id).accept("application/json"))
                .andExpect(status().isOk());
    }
    
    @Test(groups = {"CONTROLLER"})
    @Parameters({"id_invalid"})
    public void findInvalidProduct(String id) throws Exception {
        mockMvc.perform(get("/v1/products/"+id).accept("application/json"))
                .andExpect(status().is4xxClientError());
    }
    
    @Test(groups = {"CONTROLLER"})
    @Parameters({"id1","name1","code_usd","value2"})
    public void updateProduct(String id, String name, String currencyCode, double value) throws Exception {
		ProductDTO newProduct = new ProductDTO();
		newProduct.setId(id);
		newProduct.setName(name);
		CurrentPrice newPrice = new CurrentPrice();
		newPrice.setCurrencyCode(currencyCode);
		newPrice.setValue(value);
		newProduct.setCurrentPrice(newPrice);
		ObjectMapper objectMapper = new ObjectMapper();
		String putBody = objectMapper.writeValueAsString(newProduct);
    	
        mockMvc.perform(put("/v1/products/" + id).
        		accept("application/json").
        		contentType("application/json").
        		content(putBody)
        		)
                .andExpect(status().isOk());
    }
    
    @Test(groups = {"CONTROLLER"})
    @Parameters({"id1","name1","code_inr","value3"})
    public void addProduct(String id, String name, String currencyCode, double value) throws Exception {
		ProductDTO newProduct = new ProductDTO();
		newProduct.setId(id);
		newProduct.setName(name);
		CurrentPrice newPrice = new CurrentPrice();
		newPrice.setCurrencyCode(currencyCode);
		newPrice.setValue(value);
		newProduct.setCurrentPrice(newPrice);
		ObjectMapper objectMapper = new ObjectMapper();
		String putBody = objectMapper.writeValueAsString(newProduct);
    	
        mockMvc.perform(post("/v1/products").
        		accept("application/json").
        		contentType("application/json").
        		content(putBody)
        		)
                .andExpect(status().isCreated());
    }
}
