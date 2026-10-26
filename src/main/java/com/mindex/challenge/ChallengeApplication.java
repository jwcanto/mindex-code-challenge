package com.mindex.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot entry point for the Mindex challenge application.
 */
@SpringBootApplication
public class ChallengeApplication {

	/**
	 * Starts the Spring Boot application. No value is returned.
	 *
	 * @param args command-line arguments passed to Spring Boot
	 */
	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}
}
