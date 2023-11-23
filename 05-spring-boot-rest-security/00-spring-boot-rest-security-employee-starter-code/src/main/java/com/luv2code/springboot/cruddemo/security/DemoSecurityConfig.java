package com.luv2code.springboot.cruddemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {
  
  // The InMemoryUserDetailsManager class is a Spring bean, which means that it is automatically 
  // created by Spring Boot when the application starts up.
  // The @Bean annotation tells Spring Boot to create an instance of the InMemoryUserDetailsManager 
  // class and register it in the Spring application context.
  //
  // The Spring Security configuration then uses the InMemoryUserDetailsManager to load the user details from memory.
  // The InMemoryUserDetailsManager constructor takes a map of user details as its argument.
  // This map is created by the Spring Security configuration and contains the username, password, and role for each user.
  //
  // When a user tries to authenticate to the application, the Spring Security framework will 
  // call the loadUserByUsername() method on the InMemoryUserDetailsManager to retrieve the user 
  // details for that user. If the user details are found, the user will be authenticated and 
  // allowed to access the application.
  //
  // So, Spring Security does not explicitly inject the InMemoryUserDetailsManager anywhere in the project.
  // However, it does scan for all beans in the application context and create an 
  // instance of the InMemoryUserDetailsManager if it finds it.
  // The InMemoryUserDetailsManager constructor is then invoked to load the user details from memory.
  @Bean
  public InMemoryUserDetailsManager userDetailsManager() {
    
    UserDetails john = User.builder()
              .username("john")
              .password("{noop}test123")
              .roles("EMPLOYEE")
              .build();

    UserDetails mary = User.builder()
              .username("mary")
              .password("{noop}test123")
              .roles("EMPLOYEE", "MANAGER")
              .build();

    UserDetails susan = User.builder()
              .username("susan")
              .password("{noop}test123")
              .roles("EMPLOYEE", "MANAGER", "ADMIN")
              .build();

    return new InMemoryUserDetailsManager(john, mary, susan);
  }

  // Restringir acceso basado en roles
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    
    http.authorizeHttpRequests(configurer -> 
            configurer
                    .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
                    .requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE")
                    .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.PUT, "/api/employees").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
    );

    // Usamos Autenticación Básica HTTP
    http.httpBasic(Customizer.withDefaults());

    // Deshabilitamos Cross-Site Request Forgery (CSRF)
    // En general, no es requerida para REST APIs sin estado que usen POST, PUST, DELETE y/o PATCH
    http.csrf(csrf -> csrf.disable());

    return http.build();
  }
}
