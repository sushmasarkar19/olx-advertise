package com.sushma.olxadvertise;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class OlxAdvertiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlxAdvertiseApplication.class, args);
	}
	
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
	 @Bean
	    public OpenAPI customOpenAPI() {
	        final String securitySchemeName = "bearerAuth";

	        return new OpenAPI()	        		
	        		.servers(List.of(new Server().url("/gateway/advertise")))
	                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
	                .components(new Components()
	                        .addSecuritySchemes(securitySchemeName,
	                                new SecurityScheme()
	                                        .name(securitySchemeName)
	                                        .type(SecurityScheme.Type.HTTP)
	                                        .scheme("bearer")
	                                        .bearerFormat("JWT")
	                        )
	                );
	    }


}
