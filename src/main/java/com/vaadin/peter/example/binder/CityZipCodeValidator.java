package com.vaadin.peter.example.binder;

import java.util.Optional;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.peter.example.CityZipCodeProvider;
import com.vaadin.ui.TextField;

/**
 * CityZipCodeValidator performs the crossfield validation of city and zipcode
 * through the database.
 * 
 * @author Peter / Vaadin
 */
public class CityZipCodeValidator extends AbstractValidator<String> {
	private TextField cityField;
	private TextField zipCodeField;
	private CityZipCodeProvider cityZipCodeProvider;

	public CityZipCodeValidator(CityZipCodeProvider cityZipCodeProvider, TextField cityField, TextField zipCodeField) {
		super("City and Zip code mismatch");

		this.cityZipCodeProvider = cityZipCodeProvider;
		this.cityField = cityField;
		this.zipCodeField = zipCodeField;
	}

	@Override
	public ValidationResult apply(String value, ValueContext context) {
		String cityName = cityField.getValue();
		String zipCode = zipCodeField.getValue();

		Optional<String> zipCodeFor = cityZipCodeProvider.getZipCodeForCity(cityName);
		if (zipCodeFor.isPresent()) {
			if (!zipCodeFor.get().equals(zipCode)) {
				return ValidationResult.error("Zip code doesn't match city");
			}
		}

		Optional<String> cityNameFor = cityZipCodeProvider.getCityForZipCode(zipCode);
		if (cityNameFor.isPresent()) {
			if (!cityNameFor.get().equals(cityName)) {
				return ValidationResult.error("City name doesn't match zip code");
			}
		}

		return ValidationResult.ok();
	}
}
