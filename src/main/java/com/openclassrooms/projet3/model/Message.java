package com.openclassrooms.projet3.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "messages")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	@Column(name="rental_id")
	private Long rentalId;

	@Column(name="user_id")
	private Long userId;
	
	
	@NotBlank(message = "A message is required.")
	private String message;

	private Timestamp created_at;

	private Timestamp updated_at;

	public Message() {
	}

	public Message(Long rentalId, Long userId, String message, Timestamp created_at, Timestamp updated_at) {
		this.rentalId = rentalId;
		this.userId = userId;
		this.message = message;
		this.created_at = created_at;
		this.updated_at = updated_at;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRental_id() {
		return rentalId;
	}

	public void setRental_id(Long rentalId) {
		this.rentalId = rentalId;
	}

	public Long getUser_id() {
		return userId;
	}

	public void setUser_id(Long userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

}