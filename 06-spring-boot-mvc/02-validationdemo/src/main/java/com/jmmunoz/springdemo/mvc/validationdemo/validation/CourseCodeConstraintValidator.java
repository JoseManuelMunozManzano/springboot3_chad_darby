package com.jmmunoz.springdemo.mvc.validationdemo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CourseCodeConstraintValidator implements ConstraintValidator<CourseCode, String> {

  private String coursePrefix;

  @Override
  public void initialize(CourseCode theCourseCode) {
    // El value que se indica en la anotación al usarla en un campo o método
    coursePrefix = theCourseCode.value(); 
  }

  // En ConstraintValidatorContext podemos indicar mensajes de error adicionales
  // Podemos hacer lo que queramos en este método, como llamar a BD, un web service, REST API...
  @Override
  public boolean isValid(String theCode, ConstraintValidatorContext theConstraintValidatorContext) {
    boolean result = true;

    if (theCode != null) {
      result = theCode.startsWith(coursePrefix);
    }

    return result;
  }

}
