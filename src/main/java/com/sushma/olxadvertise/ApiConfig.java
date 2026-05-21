package com.sushma.olxadvertise;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
		info = @Info(
				title = "OLX Advertise REST API Documentation",
				description = "OLX Advertise application",
				version = "1.0",
				license = @License(
						name = "LGPL",
						url = "http://lgpl.com"
				),
				contact = @Contact(
						name = "Sushma",
						email = "sushma.sarkar@zensar.com"
)))
public class ApiConfig {

}
