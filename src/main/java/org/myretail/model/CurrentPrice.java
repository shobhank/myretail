package org.myretail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author shsharma
 *
 */
@JsonPropertyOrder({ "value", "currencyCode" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentPrice {

	public CurrentPrice(){		
	}
	
	public CurrentPrice(double value, String currencyCode) {
		super();
		this.value = value;
		this.currencyCode = currencyCode;
	}
	private double value;
		
	private String currencyCode;
		
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
