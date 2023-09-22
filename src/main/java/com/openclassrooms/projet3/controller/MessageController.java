package com.openclassrooms.projet3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.projet3.payload.MessageRequest;
import com.openclassrooms.projet3.payload.MessageResponse;
import com.openclassrooms.projet3.service.MessageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/messages")
@CrossOrigin("*")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@PostMapping("")
	public ResponseEntity<MessageResponse> sendMessage(@Valid @RequestBody MessageRequest messageRequest) {
		return new ResponseEntity<>(messageService.sendMessage(messageRequest), HttpStatus.OK);
	}
}