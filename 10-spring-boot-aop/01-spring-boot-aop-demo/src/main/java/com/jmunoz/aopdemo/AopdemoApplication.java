package com.jmunoz.aopdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.jmunoz.aopdemo.dao.AccountDAO;
import com.jmunoz.aopdemo.dao.MembershipDAO;

// Se excluye la clase JmxAutoConfiguration.class porque da error mBeanExporter con el pointcut
// @Before("execution(* add*(..))") de MyDemoLoggingAspect.java
@SpringBootApplication(exclude = JmxAutoConfiguration.class) 
public class AopdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AopdemoApplication.class, args);
	}

	// Gracias a la anotación @Bean, Spring Boot inyectará automáticamente la dependencia.
	// No es necesario indicar @Autowired, de nuevo gracias a la anotación @Bean.
	// Si es necesario haber anotado AccoundDAOImpl y MembershipDaoImpl como componentes Spring.
	@Bean
	public CommandLineRunner commandLineRunner(AccountDAO theAccountDAO, MembershipDAO theMembershipDAO) {

		return runner -> {
			
			demoTheBeforeAdvice(theAccountDAO, theMembershipDAO);
		};
	}

	private void demoTheBeforeAdvice(AccountDAO theAccountDAO, MembershipDAO tMembershipDAO) {

		// call the business method
		theAccountDAO.addAccount();
		theAccountDAO.addSillyMember();

		Account myAccount = new Account();
		theAccountDAO.addAccount(myAccount);

		theAccountDAO.addAccount(myAccount, true);
		
		// call the membership business method
		tMembershipDAO.addAccount();
	}
}
