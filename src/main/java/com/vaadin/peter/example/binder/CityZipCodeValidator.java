package com.vaadin.peter.example.binder;

import java.util.Optional;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.peter.example.CityZipCodeProvider;

/**
 * CityZipCodeValidator performs the crossfield validation of city and zipcode
 * through the bean.
 * 
 * @author Peter / Vaadin
 */
public class CityZipCodeValidator extends AbstractValidator<Customer> {
	private CityZipCodeProvider cityZipCodeProvider;

	public CityZipCodeValidator(CityZipCodeProvider cityZipCodeProvider) {
		super("City and Zip code mismatch");
		this.cityZipCodeProvider = cityZipCodeProvider;
	}

	@Override
	public ValidationResult apply(Customer value, ValueContext context) {

		Optional<String> zipCodeFor = cityZipCodeProvider.getZipCodeForCity(value.getCity());
		if (zipCodeFor.isPresent()) {
			if (!zipCodeFor.get().equals(value.getZipCode())) {
				return ValidationResult.error("Zip code doesn't match city");
			}
		}

		Optional<String> cityNameFor = cityZipCodeProvider.getCityForZipCode(value.getZipCode());
		if (cityNameFor.isPresent()) {
			if (!cityNameFor.get().equals(value.getCity())) {
				return ValidationResult.error("City name doesn't match zip code");
			}
		}

		return ValidationResult.ok();
	}

}
