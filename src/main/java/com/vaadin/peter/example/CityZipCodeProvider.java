package com.vaadin.peter.example;

import java.util.List;
import java.util.Optional;

/**
 * CityZipCodeProvider allows accessing the city and zip code information
 * 
 * @author Peter / Vaadin
 */
public interface CityZipCodeProvider {

	/**
	 * @param city
	 * @return Optional of zipCode String for given city name.
	 */
	Optional<String> getZipCodeForCity(String city);

	/**
	 * @param zipCode
	 * @return Optional of city that matches the given zipCode.
	 */
	Optional<String> getCityForZipCode(String zipCode);

	/**
	 * @param zipCode
	 * @return true if this database holds given zipCode, false otherwise.
	 */
	boolean hasZipCode(String zipCode);

	/**
	 * @param city
	 * @return true if this database holds city with given name, false
	 *         otherwise.
	 */
	boolean hasCity(String city);

	/**
	 * @return List of all available cities.
	 */
	List<String> getCities();

	/**
	 * @return List of all available zip codes.
	 */
	List<String> getZipCodes();
}
