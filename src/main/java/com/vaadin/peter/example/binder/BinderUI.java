package com.vaadin.peter.example.binder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.peter.example.CityZipCodeProvider;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * BinderUI is example UI for running the cross field validation example.
 * 
 * @author Peter / Vaadin
 */
@SpringUI
public class BinderUI extends UI {

	@Autowired
	private CityZipCodeProvider cityZipCodeProvider;

	private FormLayout layout;
	private TextField city;
	private TextField zipCode;

	private Label generalStatus;

	@Override
	protected void init(VaadinRequest request) {
		setSizeFull();

		generalStatus = new Label();

		layout = new FormLayout();
		layout.setMargin(true);
		layout.setSpacing(true);

		Binder<Customer> binder = new Binder<>();

		city = new TextField("City");
		city.setDescription(buildDescription(cityZipCodeProvider.getCities()), ContentMode.HTML);

		zipCode = new TextField("Zip code");
		zipCode.setDescription(buildDescription(cityZipCodeProvider.getZipCodes()), ContentMode.HTML);

		CityZipCodeValidator crossValidator = new CityZipCodeValidator(cityZipCodeProvider);

		binder.forField(city).asRequired("City is needed").withValidator(cityZipCodeProvider::hasCity, "No such city")
				.bind(Customer::getCity, Customer::setCity);

		binder.forField(zipCode).asRequired("Zip code is needed")
				.withValidator(cityZipCodeProvider::hasZipCode, "No such zip code")
				.bind(Customer::getZipCode, Customer::setZipCode);

		binder.withValidator(crossValidator);
		binder.setValidationStatusHandler(status -> handleValidationStatus(status));

		Customer customer = new Customer();
		binder.setBean(customer);

		generalStatus.setVisible(false);
		generalStatus.setIcon(VaadinIcons.CLOSE);

		layout.addComponents(city, zipCode, generalStatus);

		setContent(layout);
	}

	private void handleValidationStatus(BinderValidationStatus<Customer> status) {
		status.getFieldValidationStatuses().forEach(fieldStatus -> {
			AbstractComponent component = (AbstractComponent) fieldStatus.getField();
			component.setComponentError(
					fieldStatus.isError() ? new UserError(fieldStatus.getMessage().orElse(null)) : null);
		});

		generalStatus.setVisible(status.hasErrors());
		generalStatus.setValue(status.getValidationErrors().stream().map(error -> error.getErrorMessage())
				.collect(Collectors.joining(",")));
	}

	private String buildDescription(List<String> availableNames) {
		if (availableNames == null) {
			return null;
		}

		return availableNames.stream().collect(Collectors.joining("</br>"));
	}
}
