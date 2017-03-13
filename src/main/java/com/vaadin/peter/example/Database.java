package com.vaadin.peter.example;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

/**
 * Database resembles imaginary data storage that holds some cities and zip
 * codes.
 * 
 * @author Peter / Vaadin
 */
@Component
class Database implements CityZipCodeProvider {

	private static final Map<String, String> citiesToZipcodes;

	static {
		Map<String, String> citiesToZips = new LinkedHashMap<>();
		citiesToZips.put("turku", "20100");
		citiesToZips.put("salo", "24240");
		citiesToZips.put("espoo", "02770");
		citiesToZips.put("kauniainen", "02700");
		citiesToZips.put("pasila", "00520");
		citiesToZips.put("helsinki", "00100");

		citiesToZipcodes = Collections.unmodifiableMap(citiesToZips);
	}

	@Override
	public boolean hasCity(String city) {
		if (city == null) {
			return false;
		}

		return citiesToZipcodes.keySet().contains(city);
	}

	@Override
	public boolean hasZipCode(String zipCode) {
		if (zipCode == null) {
			return false;
		}

		return citiesToZipcodes.values().contains(zipCode);
	}

	@Override
	public Optional<String> getCityForZipCode(String zipCode) {
		if (zipCode == null) {
			return Optional.empty();
		}

		return citiesToZipcodes.entrySet().stream().filter(entry -> entry.getValue().equals(zipCode)).findFirst()
				.map(entry -> entry.getKey());
	}

	@Override
	public Optional<String> getZipCodeForCity(String city) {
		if (city == null) {
			return Optional.empty();
		}

		return Optional.ofNullable(citiesToZipcodes.get(city));
	}

	@Override
	public List<String> getCities() {
		return citiesToZipcodes.keySet().stream().collect(Collectors.toList());
	}

	@Override
	public List<String> getZipCodes() {
		return citiesToZipcodes.values().stream().collect(Collectors.toList());
	}
}
