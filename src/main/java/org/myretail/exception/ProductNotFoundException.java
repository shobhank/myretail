/**
 * 
 */
package org.myretail.exception;

/**
 *
 * @author shsharma
 */
/**
 * @author shsharma
 *
 */

public class ProductNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorMessage;
 
	public String getErrorMessage() {
		return errorMessage;
	}
	public ProductNotFoundException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	public ProductNotFoundException() {
		super();
	}
}
