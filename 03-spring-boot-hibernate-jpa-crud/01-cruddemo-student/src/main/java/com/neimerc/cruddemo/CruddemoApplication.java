package com.neimerc.cruddemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.neimerc.cruddemo.dao.StudentDAO;
import com.neimerc.cruddemo.entity.Student;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	// Creación de una app de línea de comandos.
	// CommandLineRunner es del Framework Spring Boot.
	// Se ejecuta tras haberse cargado los Beans de Spring, por lo que podemos usarlos aquí.
	@Bean
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO) {
		return runner -> {
			createStudent(studentDAO);
		};
	}

	private void createStudent(StudentDAO studentDAO) {
		// crear un objeto student
		System.out.println("Creating new student object ...");
		Student tempStudent = new Student("José M.", "Muñoz", "jmmunoz@xxx.com");

		// guardar el objeto student
		System.out.println("Saving the student ...");
		studentDAO.save(tempStudent);

		// mostrar id del student guardado
		System.out.println("Saved student. Generated id: " + tempStudent.getId());
	}
}
