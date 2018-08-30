package com.fivium.springboot.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.fivium.springboot.model.enums.ReleaseType;
import com.fivium.springboot.model.form.application.triage.ReleaseTypeForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

@Controller
@RequestMapping("/application/triage/{applicationId}")
public class NewApplicationTriageController {

  @GetMapping("/release-type")
  public ModelAndView renderReleaseType(@PathVariable long applicationId, @ModelAttribute("form") ReleaseTypeForm form) {
    //TODO restore value to form if exists
    return getModelAndView(applicationId);
  }

  @PostMapping("/release-type")
  public ModelAndView handleSubmitReleaseType(@PathVariable long applicationId,
                                              @Valid @ModelAttribute("form") ReleaseTypeForm form,
                                              BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return getModelAndView(applicationId);
    } else {
      //TODO - save to model
      return new ModelAndView("TODO");
    }
  }

  private ModelAndView getModelAndView(long applicationId) {
    Map<String, String> radioOptions = new LinkedHashMap<>();

    radioOptions.put(ReleaseType.IN_EXCESS_OF_PERMIT.toString(), "Discharge in excess of a permit");
    radioOptions.put(ReleaseType.UNRELATED_TO_PERMIT.toString(), "Release unrelated to a permit");

    String formAction = MvcUriComponentsBuilder.fromMethodCall(
        on(NewApplicationTriageController.class).handleSubmitReleaseType(applicationId, null, null)).build().toString();

    ModelAndView modelAndView = new ModelAndView("application/triage/releaseType");
    modelAndView.addObject("radioOptions", radioOptions);
    modelAndView.addObject("formAction", formAction);
    return modelAndView;
  }

}
