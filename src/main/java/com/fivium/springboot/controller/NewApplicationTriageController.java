package com.fivium.springboot.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.fivium.springboot.model.enums.ReleaseType;
import com.fivium.springboot.model.form.application.triage.ExerciseForm;
import com.fivium.springboot.model.form.application.triage.ReleaseTypeForm;
import com.fivium.springboot.model.persistence.Pon1Application;
import com.fivium.springboot.service.NewApplicationTriageService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/application/{applicationId}/triage")
public class NewApplicationTriageController {

  public static final String RELEASE_TYPE_PATH = "release-type";
  public static final String EXERCISE_PATH = "exercise";

  private final NewApplicationTriageService newApplicationTriageService;

  @Autowired
  public NewApplicationTriageController(NewApplicationTriageService newApplicationTriageService) {
    this.newApplicationTriageService = newApplicationTriageService;
  }

  @GetMapping(RELEASE_TYPE_PATH)
  public ModelAndView renderReleaseType(@PathVariable long applicationId, Pon1Application pon1Application,
                                        @ModelAttribute("form") ReleaseTypeForm form) {
    newApplicationTriageService.populateReleaseTypeForm(pon1Application, form);
    return getReleaseTypeModelAndView(applicationId);
  }

  @PostMapping(RELEASE_TYPE_PATH)
  public ModelAndView handleSubmitReleaseType(@PathVariable long applicationId, Pon1Application pon1Application,
                                              @Valid @ModelAttribute("form") ReleaseTypeForm form,
                                              BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return getReleaseTypeModelAndView(applicationId);
    } else {
      newApplicationTriageService.saveReleaseTypeForm(form, pon1Application);
      return new ModelAndView("redirect:" + EXERCISE_PATH);
    }
  }

  @GetMapping(EXERCISE_PATH)
  public ModelAndView renderExercise(@PathVariable long applicationId, Pon1Application pon1Application,
                                     @ModelAttribute("form") ExerciseForm form) {
    newApplicationTriageService.populateExerciseForm(pon1Application, form);
    return getExerciseModelAndView(applicationId);
  }

  @PostMapping(EXERCISE_PATH)
  public ModelAndView handleSubmitExercise(@PathVariable long applicationId, Pon1Application pon1Application,
                                           @Valid @ModelAttribute("form") ExerciseForm form,
                                           BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return getExerciseModelAndView(applicationId);
    } else {
      newApplicationTriageService.saveExerciseForm(form, pon1Application);
      return new ModelAndView("redirect:/dashboard");
    }
  }

  private ModelAndView getReleaseTypeModelAndView(long applicationId) {
    Map<String, String> radioOptions = new LinkedHashMap<>();

    radioOptions.put(ReleaseType.IN_EXCESS_OF_PERMIT.toString(), "Discharge in excess of a permit");
    radioOptions.put(ReleaseType.UNRELATED_TO_PERMIT.toString(), "Release unrelated to a permit");

    String formAction = MvcUriComponentsBuilder.fromMethodCall(
        on(NewApplicationTriageController.class).handleSubmitReleaseType(applicationId, null, null, null)).build().toString();

    ModelAndView modelAndView = new ModelAndView("application/triage/releaseType");
    modelAndView.addObject("radioOptions", radioOptions);
    modelAndView.addObject("formAction", formAction);
    return modelAndView;
  }

  private ModelAndView getExerciseModelAndView(long applicationId) {
    String formAction = MvcUriComponentsBuilder.fromMethodCall(
        on(NewApplicationTriageController.class).handleSubmitExercise(applicationId, null, null, null)).build().toString();

    ModelAndView modelAndView = new ModelAndView("application/triage/exercise");
    modelAndView.addObject("formAction", formAction);
    return modelAndView;
  }
}
