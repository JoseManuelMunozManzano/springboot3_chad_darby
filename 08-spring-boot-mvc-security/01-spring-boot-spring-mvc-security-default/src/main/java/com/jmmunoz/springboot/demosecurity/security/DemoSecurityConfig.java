package com.jmmunoz.springboot.demosecurity.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {

  // añadir soporte para JDBC ... no más usuarios hardcode !!
  // Inyectamos un DataSource que es auto-configurado por Spring Boot
  // Tablas personalizadas
  @Bean
  public UserDetailsManager userDetailsManager(DataSource dataSource) {

    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

    // definir consulta para recuperar un usuario por username
    // El signo ? será el nombre de usuario indicado en el login
    jdbcUserDetailsManager.setUsersByUsernameQuery(
      "select user_id, pw, active from members where user_id = ?");

    // definir consulta para recuperar los authorities / roles por username
    jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
      "select user_id, role from roles where user_id = ?");

    return jdbcUserDetailsManager;
  }

  // Configuración para que Spring Security referencia a nuestro formulario de login personalizado.
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(configurer ->
            configurer
                .requestMatchers("/").hasRole("EMPLOYEE")
                .requestMatchers("/leaders/**").hasRole("MANAGER")
                .requestMatchers("/systems/**").hasRole("ADMIN")
                .anyRequest().authenticated()   // cualquier petición a la app debe estar autenticada (logged in)
    )
    .formLogin(form -> 
            form
                  .loginPage("/showMyLoginPage")
                  .loginProcessingUrl("/authenticateTheUser")
                  .permitAll()
    )
    .logout(logout -> logout.permitAll())
    .exceptionHandling(configurer -> configurer.accessDeniedPage("/access-denied"));

    return http.build();
  }

/*
  // añadir soporte para JDBC ... no más usuarios hardcode !!
  // Inyectamos un DataSource que es auto-configurado por Spring Boot
  @Bean
  public UserDetailsManager userDetailsManager(DataSource dataSource) {
    // Indica a Spring Security que use autenticación JDBC con nuestro data source.
    // Spring Security buscará las tablas con nombre por defecto users y authorities
    return new JdbcUserDetailsManager(dataSource);
  }
*/

/*
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
*/
}
