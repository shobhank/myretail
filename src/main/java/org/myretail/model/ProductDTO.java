package org.myretail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author shsharma
 *
 */

@JsonPropertyOrder({ "id", "name","currentPrice" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO{

	private String id;
	
	private String name;
	
	private CurrentPrice currentPrice;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public CurrentPrice getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(CurrentPrice currentPrice) {
		this.currentPrice = currentPrice;
	}
}
