/**
 * 
 */
package org.myretail.model;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * @author shsharma
 *
 */

@Table("items")
public class Product {
	
	@PrimaryKey
	@Column("id")
	private String id;
	
	public Product(String id, String currencyCode, double value) {
		super();
		this.id = id;
		this.currencyCode = currencyCode;
		this.value = value;
	}
	@Column("currency_code")
	private String currencyCode;
	
	@Column("value")
	private double value;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
}
