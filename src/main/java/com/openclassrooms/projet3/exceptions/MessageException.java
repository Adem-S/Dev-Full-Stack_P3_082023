package com.openclassrooms.projet3.exceptions;

public class MessageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MessageException(String s) {
		super(s);
	}
}
