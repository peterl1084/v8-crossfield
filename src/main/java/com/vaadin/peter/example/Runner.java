package com.vaadin.peter.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Example application Spring Boot runner.
 * 
 * @author Peter / Vaadin
 */
@SpringBootApplication
public class Runner extends SpringBootServletInitializer {

	public static void main(final String... args) {
		SpringApplication.run(Runner.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(Runner.class);
	}
}
