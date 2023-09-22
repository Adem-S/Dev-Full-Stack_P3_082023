package com.openclassrooms.projet3.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.projet3.exceptions.MessageException;
import com.openclassrooms.projet3.exceptions.RentalNotFoundException;
import com.openclassrooms.projet3.model.Rental;
import com.openclassrooms.projet3.payload.MessageResponse;
import com.openclassrooms.projet3.payload.RentalsResponse;
import com.openclassrooms.projet3.repository.RentalRepository;
import com.openclassrooms.projet3.security.services.UserDetailsImpl;

@Service
public class RentalService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private RentalRepository rentalRepository;

	public MessageResponse addRental(String name, String surface, String price, MultipartFile picture,
			String description) {

		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

			Long userId = userDetails.getId();

			String fileName = picture.getOriginalFilename();

			String fileExtension;

			if (fileName != null && fileName.lastIndexOf(".") != -1) {
				fileExtension = "." + fileName.substring(fileName.lastIndexOf(".") + 1);
			} else {
				fileExtension = "";
			}

			List<String> extensionsImages = Arrays.asList(".jpg", ".jpeg", ".png", ".bmp");

			if (fileExtension.length() == 0 || !extensionsImages.contains(fileExtension)) {
				throw new MessageException("Only image");
			}

			String uniqueFileName = System.currentTimeMillis() + "_" + userId + fileExtension;

			String pictureUrl = "http://localhost:3001/images/" + uniqueFileName;

			Path fileNameAndPath = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/images",
					uniqueFileName);

			Files.write(fileNameAndPath, picture.getBytes());

			Timestamp dateNow = new Timestamp(System.currentTimeMillis());

			Rental rental = new Rental(name, Integer.valueOf(surface), Integer.valueOf(price), pictureUrl, description,
					userId, dateNow, dateNow);

			rentalRepository.save(rental);

			return new MessageResponse("Rental created !");

		} catch (IOException e) {
			e.printStackTrace();
			throw new MessageException("Error");
		}

	}

	public RentalsResponse getRentals() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Long userId = userDetails.getId();

		List<Rental> rentals = rentalRepository.findByOwnerId(userId);

		return new RentalsResponse(rentals);

	}

	public Rental getRental(long id) {

		Optional<Rental> rental = rentalRepository.findById(id);

		return rental.orElseThrow(() -> new RentalNotFoundException("No Rental Found"));

	}

	public MessageResponse updateRental(long id, String name, String surface, String price, String description) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Long userId = userDetails.getId();

		Optional<Rental> optionalRental = rentalRepository.findById(id);

		if (optionalRental.isPresent()) {

			Rental rental = optionalRental.get();

			if (userId != rental.getOwner_id()) {
				throw new MessageException("Not Authorized");
			}

			Timestamp dateNow = new Timestamp(System.currentTimeMillis());
			rental.setName(name);
			rental.setUpdated_at(dateNow);
			rental.setPrice(Integer.valueOf(price));
			rental.setSurface(Integer.valueOf(price));
			rental.setDescription(description);

			rentalRepository.save(rental);

			return new MessageResponse("Rental updated !");

		} else {
			throw new MessageException("Not Authorized");
		}

	}
}
