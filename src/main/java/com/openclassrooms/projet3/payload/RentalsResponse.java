package com.openclassrooms.projet3.payload;

import java.util.List;

import com.openclassrooms.projet3.model.Rental;

public class RentalsResponse {

	private List<Rental> rentals;

	public RentalsResponse(List<Rental> rentals) {
		this.rentals = rentals;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}

}
