package com.k15t.pat.domain;

import com.k15t.pat.repository.RegistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * This component runs the CommandLineRunner and loads the initial values into
 * the database
 * 
 * @author Sujata
 *
 */

@Component
public class RegistrationCommandLineRunner implements CommandLineRunner {

	@Autowired
	private RegistrationRepository registrationRepository;

	@Override
	public void run(String... args) throws Exception {

		registrationRepository.save(
				new Registration("Sujata", "sujata", "Dubai", "sujata.khochede@gmail.com", new BigDecimal("0567882265")));
		registrationRepository
				.save(new Registration("Ironman", "ironman", "LA", "ironman@gmail.com", new BigDecimal("9657791539")));
		registrationRepository.save(new Registration("Nandlal", "nandu", "Pune", "nandlaldhande@gmail.com",
				new BigDecimal("544726315")));
		registrationRepository
				.save(new Registration("Advait", "advait", "New york", "advait@gmail.com", new BigDecimal("9657791539")));
		registrationRepository.save(
				new Registration("Batman", "batman", "Manchester", "batman@gmail.com", new BigDecimal("9657791539")));

	}

}