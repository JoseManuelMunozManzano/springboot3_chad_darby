package com.jmunoz.aopdemo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.jmunoz.aopdemo.dao.AccountDAO;
import com.jmunoz.aopdemo.dao.MembershipDAO;
import com.jmunoz.aopdemo.service.TrafficFortuneService;

// Se excluye la clase JmxAutoConfiguration.class porque da error mBeanExporter con el pointcut
// @Before("execution(* add*(..))") de MyDemoLoggingAspect.java
@SpringBootApplication(exclude = JmxAutoConfiguration.class) 
public class AopdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AopdemoApplication.class, args);
	}

	// Gracias a la anotación @Bean, Spring Boot inyectará automáticamente la dependencia.
	// No es necesario indicar @Autowired, de nuevo gracias a la anotación @Bean.
	// Si es necesario haber anotado AccoundDAOImpl, MembershipDaoImpl y TrafficFortuneService como componentes Spring.
	@Bean
	public CommandLineRunner commandLineRunner(AccountDAO theAccountDAO, 
																						MembershipDAO theMembershipDAO, 
																						TrafficFortuneService theTrafficFortuneService) {

		return runner -> {
			
			// demoTheBeforeAdvice(theAccountDAO, theMembershipDAO);

			// demoTheAfterReturningAdvice(theAccountDAO);

			// demoTheAfterThrowingAdvice(theAccountDAO);

			// demoTheAfterAdvice(theAccountDAO);

			// demoTheAroundAdvice(theTrafficFortuneService);

			// demoTheAroundAdviceHandleException(theTrafficFortuneService);

			demoTheAroundAdviceRethrowException(theTrafficFortuneService);
		};
	}

	private void demoTheAroundAdviceRethrowException(TrafficFortuneService theTrafficFortuneService) {
	
		System.out.println("\nMain Program: demoTheAroundAdviceRethrowException");

		System.out.println("Calling getFortune()");

		boolean tripWire = true;

		// Si comentamos el manejo de la excepción y añadimos un throw exc, relanzamos la excepción y
		// entonces si que se devuelve la excepción y nos enteramos.
		String data = theTrafficFortuneService.getFortune(tripWire);

		System.out.println("\nMy fortune is: " + data);

		System.out.println("Finished");
	}

	private void demoTheAroundAdviceHandleException(TrafficFortuneService theTrafficFortuneService) {

		System.out.println("\nMain Program: demoTheAroundAdviceHandleException");

		System.out.println("Calling getFortune()");

		boolean tripWire = true;

		// Como hemos hecho que el advice @Around gestione la excepción, NUNCA sabremos que la hubo!!
		String data = theTrafficFortuneService.getFortune(tripWire);

		System.out.println("\nMy fortune is: " + data);

		System.out.println("Finished");
	}

	private void demoTheAroundAdvice(TrafficFortuneService theTrafficFortuneService) {

		System.out.println("\nMain Program: demoTheAroundAdvice");

		System.out.println("Calling getFortune()");

		String data = theTrafficFortuneService.getFortune();

		System.out.println("\nMy fortune is: " + data);

		System.out.println("Finished");
	}

	private void demoTheAfterAdvice(AccountDAO theAccountDAO) {

		// call method to find the accounts
		List<Account> theAccounts = null;

		try {
			// add a boolean flag to simulate exceptions
			// cambiar este valor a false (ejecución existosa) o true (ejecución lanza una excepción)
			boolean tripWire = false;
			theAccounts = theAccountDAO.findAccounts(tripWire);
		} catch(Exception exc) {
			System.out.println("\n\nMain Program: ... caught exception: " + exc);
		}

		// display the accounts
		System.out.println("\n\nMain Program: demoTheAfterThrowingAdvice");
		System.out.println("----");

		System.out.println(theAccounts);

		System.out.println("\n");
	}

	private void demoTheAfterThrowingAdvice(AccountDAO theAccountDAO) {

		// call method to find the accounts
		List<Account> theAccounts = null;

		try {
			// add a boolean flag to simulate exceptions
			boolean tripWire = true;
			theAccounts = theAccountDAO.findAccounts(tripWire);
		} catch(Exception exc) {
			System.out.println("\n\nMain Program: ... caught exception: " + exc);
		}

		// display the accounts
		System.out.println("\n\nMain Program: demoTheAfterThrowingAdvice");
		System.out.println("----");

		System.out.println(theAccounts);

		System.out.println("\n");
	}

	private void demoTheAfterReturningAdvice(AccountDAO theAccountDAO) {
		
		// call method to find the accounts
		List<Account> theAccounts = theAccountDAO.findAccounts();

		// display the accounts
		System.out.println("\n\nMain Program: demoTheAfterReturningAdvice");
		System.out.println("----");

		System.out.println(theAccounts);

		System.out.println("\n");
	}

	private void demoTheBeforeAdvice(AccountDAO theAccountDAO, MembershipDAO tMembershipDAO) {

		// call the business method
		theAccountDAO.addAccount();
		theAccountDAO.addSillyMember();

		Account myAccount = new Account();
		myAccount.setName("José M.");
		myAccount.setLevel("Platinum");

		theAccountDAO.addAccount(myAccount);

		theAccountDAO.addAccount(myAccount, true);

		theAccountDAO.doWork();

		// call the AccountDAO getter/setter methods
		theAccountDAO.setName("foobar");
		theAccountDAO.setServiceCode("silver");

		String name = theAccountDAO.getName();
		String code = theAccountDAO.getServiceCode();
		
		// call the membership business method
		tMembershipDAO.addAccount();

		tMembershipDAO.goToSleep();
	}
}
