package com.fivium.pon1.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.fivium.pon1.model.PersonForm;
import com.fivium.pon1.model.persistence.Person;
import com.fivium.pon1.repository.PersonRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

@Controller
@RequestMapping("/freemarker-form")
public class FreemarkerPersonFormController {

  private final PersonRepository personRepository;

  @Autowired
  public FreemarkerPersonFormController(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(new PersonFormValidator());
  }

  @GetMapping("/{id}")
  public ModelAndView renderForm(@PathVariable("id") Long id) {
    //TODO ID must be not null
    Optional<Person> personOptional = personRepository.findById(id);

    PersonForm personForm = new PersonForm();
    personForm.setName(personOptional.map(Person::getName).orElse(null));
    personForm.setAge(personOptional.map(Person::getAge).orElse(null));

    return createModelAndView(id, personForm);
  }

  @PostMapping("/{id}")
  public ModelAndView handleSubmit(@PathVariable("id") Long id, @Valid PersonForm personForm, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      System.out.println(personForm);
      return createModelAndView(id, personForm);
    } else {
      Person person = personRepository.findById(id).orElse(new Person());
      person.setName(personForm.getName());
      person.setAge(personForm.getAge());
      personRepository.save(person);

      return new ModelAndView("redirect:/greeting");
    }
  }

  private ModelAndView createModelAndView(Long id, PersonForm personForm) {
    ModelAndView modelAndView = new ModelAndView("personForm");
    modelAndView.addObject("personForm", personForm);
    modelAndView.addObject("formAction", MvcUriComponentsBuilder.fromMethodCall(on(FreemarkerPersonFormController.class).handleSubmit(id, null, null)).build().toString());
    modelAndView.addObject("interestOptions", Arrays.stream(PersonForm.Interest.values()).collect(Collectors.toMap(Enum::toString, PersonForm.Interest::getFormPrompt)));
    return modelAndView;
  }

  public static class PersonFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
      return PersonForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
      PersonForm personForm = (PersonForm) target;

      if (BooleanUtils.isFalse(personForm.getDrivingLicence()) &&
          personForm.getInterests().contains(PersonForm.Interest.DRIVING)) {
        errors.rejectValue("interests", "", "Driving cannot be an interest without a driving licence");
      }
    }
  }
}
