
package com.openclassrooms.projet3.exceptions;

public class RentalNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RentalNotFoundException(String s) {
		super(s);
	}
}
