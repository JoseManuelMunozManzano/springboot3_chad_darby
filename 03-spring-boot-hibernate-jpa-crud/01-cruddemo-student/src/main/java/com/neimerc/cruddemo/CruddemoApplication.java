package com.neimerc.cruddemo;

import java.util.List;

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
			// createStudent(studentDAO);

			// createMultipleStudents(studentDAO);

			// readStudent(studentDAO);

			// queryForStudents(studentDAO);

			queryForStudentsByLastName(studentDAO);
		};
	}

	private void queryForStudentsByLastName(StudentDAO studentDAO) {
		// obtener una lista de students
		List<Student> theStudents = studentDAO.findByLastName("Duck");

		// mostrar la lista de students
		for (Student tempStudent : theStudents) {
			System.out.println(tempStudent);
		}
	}

	private void queryForStudents(StudentDAO studentDAO) {
		// obtener una lista de students
		List<Student> theStudents = studentDAO.findAll();

		// mostrar la lista de students
		for (Student tempStudent : theStudents) {
			System.out.println(tempStudent);
		}
	}

	private void readStudent(StudentDAO studentDAO) {
		// crear un objeto student
		System.out.println("Creating new student object ...");
		Student tempStudent = new Student("Daffy", "Duck", "daffy@xxx.com");

		// guardar el objeto student
		System.out.println("Saving the student...");
		studentDAO.save(tempStudent);

		// mostrar id del student guardado
		int theId = tempStudent.getId();
		System.out.println("Saved student. Generate id: " + theId);

		// recuperar student basado en el id: primary key
		System.out.println("Retrieving student with id: " + theId);
		Student myStudent = studentDAO.findById(theId);

		// mostrar student
		System.out.println("Found the student: " + myStudent);
	}

	// El objetivo de este método es probar la generación automática, por parte de MariaDB, de la columna id (que es la primary key)
	private void createMultipleStudents(StudentDAO studentDAO) {
		// crear muchos students
		System.out.println("Creating 3 student objects ...");
		Student tempStudent1 = new Student("Adriana", "López", "alopez@xxx.com");
		Student tempStudent2 = new Student("Tania", "Gómez", "tgomez@xxx.com");
		Student tempStudent3 = new Student("Ferney", "Pérez", "fperez@xxx.com");

		// guardar los objetos student
		System.out.println("Saving the students ...");
		studentDAO.save(tempStudent1);
		studentDAO.save(tempStudent2);
		studentDAO.save(tempStudent3);
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
