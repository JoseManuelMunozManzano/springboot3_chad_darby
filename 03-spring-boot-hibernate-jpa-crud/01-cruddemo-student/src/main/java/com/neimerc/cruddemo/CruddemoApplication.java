package com.neimerc.cruddemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	// Creación de una app de línea de comandos.
	// CommandLineRunner es del Framework Spring Boot.
	// Se ejecuta tras haberse cargado los Beans de Spring, por lo que podemos usarlos aquí.
	@Bean
	public CommandLineRunner commandLineRunner(String[] args) {
		return runner -> {
			System.out.println("Hello World!");
		};
	}
}
