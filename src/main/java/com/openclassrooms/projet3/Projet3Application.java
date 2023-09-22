package com.openclassrooms.projet3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Projet3Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Projet3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("start server");
	}

}
