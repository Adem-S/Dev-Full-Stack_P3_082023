package com.openclassrooms.projet3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.projet3.model.Rental;
import com.openclassrooms.projet3.payload.MessageResponse;
import com.openclassrooms.projet3.payload.RentalRequest;
import com.openclassrooms.projet3.payload.RentalRequestWithoutPicture;
import com.openclassrooms.projet3.payload.RentalsResponse;
import com.openclassrooms.projet3.service.RentalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/rentals")
@CrossOrigin("*")
public class RentalController {

	@Autowired
	private RentalService rentalService;

	@PostMapping("")
	public ResponseEntity<MessageResponse> addRental(@Valid @ModelAttribute RentalRequest rentalRequest) {
		return new ResponseEntity<>(rentalService.addRental(rentalRequest.getName(), rentalRequest.getSurface(),
				rentalRequest.getPrice(), rentalRequest.getPicture(), rentalRequest.getDescription()), HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<RentalsResponse> getRentals() {
		return new ResponseEntity<>(rentalService.getRentals(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Rental> getRentalById(@PathVariable("id") final Long id) {

		return new ResponseEntity<>(rentalService.getRental(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<MessageResponse> updateRental(@PathVariable("id") final Long id,
			@Valid @ModelAttribute RentalRequestWithoutPicture rentalRequest) {
		return new ResponseEntity<>(rentalService.updateRental(id, rentalRequest.getName(), rentalRequest.getSurface(),
				rentalRequest.getPrice(), rentalRequest.getDescription()), HttpStatus.OK);
	}
}
