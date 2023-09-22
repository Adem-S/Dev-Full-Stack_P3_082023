package com.openclassrooms.projet3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.openclassrooms.projet3.model.Rental;
import com.openclassrooms.projet3.model.User;
import com.openclassrooms.projet3.payload.MessageResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		FieldError firstFieldError = result.getFieldErrors().get(0);
		String errorMessage = firstFieldError.getField() + ": " + firstFieldError.getDefaultMessage();

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setError("Form error");
		errorResponse.setMessage(errorMessage);
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<User> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {

		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RentalNotFoundException.class)
	public ResponseEntity<Rental> handleRentalNotFoundException(RentalNotFoundException ex, WebRequest request) {

		return new ResponseEntity<Rental>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MessageException.class)
	public ResponseEntity<MessageResponse> handleMessageException(MessageException ex, WebRequest request) {

		MessageResponse messageResponse = new MessageResponse(ex.getMessage());

		return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TokenException.class)
	public ResponseEntity<ErrorResponse> handleTokenException(TokenException ex, WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setError("Authentication Error");
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
	}

}
