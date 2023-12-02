package com.jmmunoz.springdemo.mvc.validationdemo.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = CourseCodeConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME) // Retener la anotación en el fichero de clase Java y procesar en runtime
public @interface CourseCode {

  // definir un código de curso por defecto
  public String value() default "LUV";

  // definir mensaje de error por defecto
  public String message() default "must start with LUV";

  // definir grupos por defecto (se agrupan validation constraints relacionadas)
  public Class<?>[] groups() default {};

  // definir payloads por defecto (información adicional sobre el mensaje de error que acaba de ocurrir)
  public Class<? extends Payload>[] payload() default {};
}
