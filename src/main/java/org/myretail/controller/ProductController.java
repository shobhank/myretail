package org.myretail.controller;

import org.myretail.exception.ProductNotFoundException;
import org.myretail.model.ProductDTO;
import org.myretail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.myretail.exception.ErrorResponse;

/**
 *
 * @author shsharma
 */

@Controller
@RequestMapping("/v1/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ProductDTO getProduct(@PathVariable("id") String id, @RequestHeader(value="currency-code" , defaultValue="USD") String currencyCode) throws ProductNotFoundException{
		return productService.getProduct(id,currencyCode); 
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public @ResponseBody void updateProductPrice(@PathVariable("id") String id, @RequestBody ProductDTO product) throws ProductNotFoundException{
		productService.updateProductPrice(id, product);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<?> addProduct(@RequestBody ProductDTO product) throws ProductNotFoundException{
		productService.addProduct(product);	
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(product.getId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> notFoundExceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
}
