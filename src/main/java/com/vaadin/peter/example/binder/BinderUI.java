package com.vaadin.peter.example.binder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.Binder.Binding;
import com.vaadin.peter.example.CityZipCodeProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * BinderUI is example UI for running the cross field validation example.
 * 
 * @author Peter / Vaadin
 */
@SpringUI
public class BinderUI extends UI {

	@Autowired
	private CityZipCodeProvider cityZipCodeProvider;

	private VerticalLayout layout;
	private TextField city;
	private TextField zipCode;

	@Override
	protected void init(VaadinRequest request) {
		setSizeFull();

		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);

		Binder<Customer> binder = new Binder<>();

		city = new TextField("City");
		city.setDescription(buildDescription(cityZipCodeProvider.getCities()), ContentMode.HTML);

		zipCode = new TextField("Zip code");
		zipCode.setDescription(buildDescription(cityZipCodeProvider.getZipCodes()), ContentMode.HTML);

		CityZipCodeValidator crossValidator = new CityZipCodeValidator(cityZipCodeProvider, city, zipCode);

		Binding<Customer, String> cityBinding = binder.forField(city).asRequired("City is needed")
				.withValidator(cityName -> cityZipCodeProvider.hasCity(cityName), "No such city")
				.withValidator(crossValidator).bind(Customer::getCity, Customer::setCity);

		Binding<Customer, String> zipCodeBinding = binder.forField(zipCode).asRequired("Zip code is needed")
				.withValidator(code -> cityZipCodeProvider.hasZipCode(code), "No such zip code")
				.withValidator(crossValidator).bind(Customer::getZipCode, Customer::setZipCode);

		city.addValueChangeListener(e -> zipCodeBinding.validate());
		zipCode.addValueChangeListener(e -> cityBinding.validate());

		layout.addComponents(city, zipCode);

		setContent(layout);
	}

	private String buildDescription(List<String> availableNames) {
		if (availableNames == null) {
			return null;
		}

		return availableNames.stream().collect(Collectors.joining("</br>"));
	}
}
