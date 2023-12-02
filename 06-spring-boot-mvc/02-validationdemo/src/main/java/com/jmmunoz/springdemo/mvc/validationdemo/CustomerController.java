package com.jmmunoz.springdemo.mvc.validationdemo;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class CustomerController {

  // añadir un initbinder ... para hacer trim a los input strings
  // eliminar espacios por delante y por detrás
  // resuelve el problema de nuestra validación
  @InitBinder
  public void initBinder(WebDataBinder dataBinder) {

    // Esta clase es parte de Spring API
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

    // Registramos este editor personalizado en el DataBinder
    dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
  }
  
  @GetMapping("/")
  public String showForm(Model theModel) {

    theModel.addAttribute("customer", new Customer());

    return "customer-form";
  }

  @PostMapping("/processForm")
  public String processForm(
          @Valid @ModelAttribute("customer") Customer theCustomer, 
          BindingResult theBindingResult) {

    System.out.println("Last name: |" + theCustomer.getLastName() + "|");

    System.out.println("Binding results: " + theBindingResult.toString());

    System.out.println("\n\n\n\n");

    if (theBindingResult.hasErrors()) {
      return "customer-form";
    } 
    
    return "customer-confirmation";    
  }
}
