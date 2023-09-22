package com.openclassrooms.projet3.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.openclassrooms.projet3.exceptions.MessageException;
import com.openclassrooms.projet3.model.Message;
import com.openclassrooms.projet3.payload.MessageRequest;
import com.openclassrooms.projet3.payload.MessageResponse;
import com.openclassrooms.projet3.repository.MessageRepository;
import com.openclassrooms.projet3.security.services.UserDetailsImpl;

@Service
public class MessageService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private MessageRepository messageRepository;

	public MessageResponse sendMessage(MessageRequest messageRequest) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		if (userDetails.getId() != messageRequest.getUser_id()) {
			throw new MessageException("Unauthorized");
		}

		Timestamp dateNow = new Timestamp(System.currentTimeMillis());

		Message message = new Message(messageRequest.getRental_id(), messageRequest.getUser_id(),
				messageRequest.getMessage(), dateNow, dateNow);

		messageRepository.save(message);

		return new MessageResponse("Message send with success");

	}

}