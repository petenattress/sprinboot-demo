package com.fivium.pon1.controller;

import com.fivium.pon1.model.PersonForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/form")
public class FormController {

  @GetMapping("/{id}")
  public String renderForm(@PathVariable("id") String id, PersonForm personForm) {
    //TODO pre-populate form
    //TODO ID must be not null
    //personForm.setAge(10);

    return "form";
  }

  @PostMapping("/{id}")
  public String handleSubmit(@PathVariable("id") String id, @Valid PersonForm personForm, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return "form";
    }

    return "redirect:/greeting";
  }

}
