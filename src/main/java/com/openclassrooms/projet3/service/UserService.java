package com.openclassrooms.projet3.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.projet3.exceptions.TokenException;
import com.openclassrooms.projet3.exceptions.UserNotFoundException;
import com.openclassrooms.projet3.model.User;
import com.openclassrooms.projet3.payload.LoginRequest;
import com.openclassrooms.projet3.payload.RegisterRequest;
import com.openclassrooms.projet3.payload.TokenResponse;
import com.openclassrooms.projet3.repository.UserRepository;
import com.openclassrooms.projet3.security.jwt.JwtUtils;
import com.openclassrooms.projet3.security.services.UserDetailsImpl;

@Service
public class UserService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	public User getMyInformation() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		String email = userDetails.getEmail();

		Optional<User> user = userRepository.findByEmail(email);

		return user.orElseThrow(() -> new UserNotFoundException("No User Found"));

	}

	public User getUser(final Long id) {

		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> new UserNotFoundException("No User Found"));

	}

	public TokenResponse saveUser(RegisterRequest registerRequest) {

		User user = new User();

		Timestamp dateNow = new Timestamp(System.currentTimeMillis());

		user.setName(registerRequest.getName());
		user.setEmail(registerRequest.getEmail());
		user.setCreated_at(dateNow);
		user.setUpdated_at(dateNow);
		user.setPassword(encoder.encode(registerRequest.getPassword()));

		userRepository.save(user);

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(registerRequest.getEmail());
		loginRequest.setPassword(registerRequest.getPassword());

		return loginUser(loginRequest);
	}

	public TokenResponse loginUser(LoginRequest loginRequest) {

		try {
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(auth);

			String jwt = jwtUtils.generateJwtToken(auth);

			return new TokenResponse(jwt);

		} catch (AuthenticationException e) {
			throw new TokenException("");
		}

	}

}