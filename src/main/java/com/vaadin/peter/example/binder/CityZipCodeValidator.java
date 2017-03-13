package com.vaadin.peter.example.binder;

import java.util.Optional;
import java.util.function.Supplier;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.peter.example.CityZipCodeProvider;

/**
 * CityZipCodeValidator performs the crossfield validation of city and zipcode
 * through the database.
 * 
 * @author Peter / Vaadin
 */
public class CityZipCodeValidator extends AbstractValidator<String> {
	private CityZipCodeProvider cityZipCodeProvider;

	private Supplier<String> city;
	private Supplier<String> zipCode;

	public CityZipCodeValidator(CityZipCodeProvider cityZipCodeProvider, Supplier<String> city,
			Supplier<String> zipCode) {
		super("City and Zip code mismatch");

		this.cityZipCodeProvider = cityZipCodeProvider;
		this.city = city;
		this.zipCode = zipCode;
	}

	@Override
	public ValidationResult apply(String value, ValueContext context) {
		String cityValue = city.get();
		String zipCodeValue = zipCode.get();

		Optional<String> zipCodeFor = cityZipCodeProvider.getZipCodeForCity(cityValue);
		if (zipCodeFor.isPresent()) {
			if (!zipCodeFor.get().equals(zipCodeValue)) {
				return ValidationResult.error("Zip code doesn't match city");
			}
		}

		Optional<String> cityNameFor = cityZipCodeProvider.getCityForZipCode(zipCodeValue);
		if (cityNameFor.isPresent()) {
			if (!cityNameFor.get().equals(cityValue)) {
				return ValidationResult.error("City name doesn't match zip code");
			}
		}

		return ValidationResult.ok();
	}
}
