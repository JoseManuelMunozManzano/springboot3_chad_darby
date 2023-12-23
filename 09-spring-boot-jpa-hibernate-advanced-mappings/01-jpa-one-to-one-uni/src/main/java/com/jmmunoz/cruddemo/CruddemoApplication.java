package com.jmmunoz.cruddemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.jmmunoz.cruddemo.dao.AppDAO;
import com.jmmunoz.cruddemo.entity.Instructor;
import com.jmmunoz.cruddemo.entity.InstructorDetail;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	// Definimos el bean para crear una app de línea de comandos.
	// CommandLineRunner viene de Spring Boot y este método se ejecuta tras haberse cargado los Spring Beans.
	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {
		
		return runner -> {
			// createInstructor(appDAO);

			findInstructor(appDAO);
		};
	}

	private void findInstructor(AppDAO appDAO) {

		int theId = 2;
		System.out.println("Finding instructor id: " + theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);

		// Obtenemos el instructor y el instructorDetail
		System.out.println("tempInstructor: " + tempInstructor);
		// Obtenemos solo el instructorDetail
		System.out.println("the associated instructorDetail only: " + tempInstructor.getInstructorDetail());
	}

	private void createInstructor(AppDAO appDAO) {

		/* Primer instructor guardado (y luego comentado)
		// create the instructor
		Instructor tempInstructor = new Instructor("José M.", "Muñoz", "jmmunoz@mail.com");

		// create the instructor detail
		InstructorDetail tempInstructorDetail = new InstructorDetail("http://www.luv2code.com/youtube", "Luv 2 Code!!!");

		// associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);
		*/

		// Segundo instructor guardado
		// create the instructor
		Instructor tempInstructor = new Instructor("Tania", "Muñoz", "tmunoz@mail.com");

		// create the instructor detail
		InstructorDetail tempInstructorDetail = new InstructorDetail("http://www.luv2code.com/youtube", "Her children");

		// associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);


		// save the instructor
		//
		// NOTE: this will ALSO save the details object
		// because of CascadeType.ALL
		//
		System.out.println("Saving instructor: " + tempInstructor);
		appDAO.save(tempInstructor);

		System.out.println("Done!");
	}
}
