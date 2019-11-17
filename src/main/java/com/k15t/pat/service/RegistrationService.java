package com.k15t.pat.service;

import com.k15t.pat.domain.Registration;

/**
 * This is a service interface with all the necessary methods to interact with
 * the @Repository component RegistrationRepository
 * 
 * @author Sujata
 *
 */
public interface RegistrationService {

	/**
	 * This method returns the list of all registrations
	 * 
	 * @return list of Registration
	 */
	Iterable<Registration> list();

	/**
	 * This method creates a new registration
	 * 
	 * @param registration registration row
	 * @return instance of Registration
	 */

	Registration create(Registration registration);

	/**
	 * This method takes an id and returns the matching instance of Registration
	 * 
	 * @param id is
	 * @return instance of Registration
	 */
	Registration get(long id);

	/**
	 * This method takes an id and updates the matching instance in the database
	 * @param id id
	 * @param registration registration row
	 * @return instance of Registration
	 */
	Registration update(long id, Registration registration);

	/**
	 * This method takes an id and deletes the matching instance from the database
	 * 
	 * @param id id
	 * @return instance of Registration
	 */
	Registration delete(long id);
}