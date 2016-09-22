/**
 *  This layer will be the thickest. It will have business and security logics.
 */
package org.myretail.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.myretail.dao.ProductDAO;
import org.myretail.model.Product;
import org.myretail.model.CurrentPrice;
import org.myretail.model.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.myretail.exception.ProductNotFoundException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author shsharma
 *
 */
@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	ProductDAO productDAO;

	@Override
	public ProductDTO getProduct(String id, String currencyCode) throws ProductNotFoundException {
		// Get Product from Cassandra
		Product cProduct = productDAO.getProduct(id, currencyCode);
		if(cProduct==null)
			throw new ProductNotFoundException("Product Not Found");
		ProductDTO product = new ProductDTO();
		product.setId(cProduct.getId());
		product.setName(getProductName(String.valueOf(id)));
		product.setCurrentPrice(new CurrentPrice(cProduct.getValue(), cProduct.getCurrencyCode()));
		return product;
	}

	@Override
	public void updateProductPrice(String id, ProductDTO product) throws ProductNotFoundException {
		Product cProduct = productDAO.getProduct(product.getId(), product.getCurrentPrice().getCurrencyCode());
		if(cProduct==null)
			throw new ProductNotFoundException("Product Not Found");
		cProduct.setValue(product.getCurrentPrice().getValue());
		productDAO.updateProduct(cProduct);
	}
	
	
	/**
	 * It should return name of the object from internal api.
	 * For the demo purpose, it returns book name when passed isbn number as id.
	 * It uses an available api (http://isbndb.com)
	 * This will be replaced in future when product api to get name based on id is 
	 * developed.
	 * 
	 * @param id
	 * @return String
	 */
	public String getProductName(String id){

		@SuppressWarnings({ "deprecation", "resource" })
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(
				"http://isbndb.com/api/v2/json/VH990HN4/book/" + id);
		HttpResponse response;
		String name = "";
		try {
			response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String responseStr = "";
			String line = "";
			while ((line = rd.readLine()) != null) {
				responseStr = responseStr + line;
			}
			name = getBookNameFromString(responseStr);
		} catch (IOException e1) {
			return "";
		} 
		return name;
	}
	
	public String getBookNameFromString(String response){
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readValue(response, JsonNode.class);
			JsonNode data = node.get("data").get(0);
			String title = data.get("title").asText();
			return title;
		} catch (JsonGenerationException e) {
			return "";
		} catch (JsonMappingException e) {
			return "";
		} catch (IOException e) {
			return "";
		} catch (NullPointerException npe){
			return "";
		}
	}


	@Override
	public void addProduct(ProductDTO product) {
		Product cProduct = new Product(product.getId(),product.getCurrentPrice().getCurrencyCode(),product.getCurrentPrice().getValue());
		productDAO.addProduct(cProduct);
	}
}
