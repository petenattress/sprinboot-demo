package com.fivium.springboot.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.fivium.springboot.model.security.SamlSsoUser;
import com.fivium.springboot.service.DashboardService;
import com.fivium.springboot.service.Pon1ApplicationService;
import com.fivium.springboot.service.UserPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.Set;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

  private final UserPrivilegeService userPrivilegeService;
  private final DashboardService dashboardService;
  private final Pon1ApplicationService pon1ApplicationService;

  @Autowired
  public DashboardController(UserPrivilegeService userPrivilegeService, DashboardService dashboardService,
                             Pon1ApplicationService pon1ApplicationService) {
    this.userPrivilegeService = userPrivilegeService;
    this.dashboardService = dashboardService;
    this.pon1ApplicationService = pon1ApplicationService;
  }

  @GetMapping
  public ModelAndView renderDashboard(SamlSsoUser samlSsoUser) {
    ModelAndView modelAndView = new ModelAndView("dashboard");
    modelAndView.addObject("dashboardEntries", dashboardService.createDashboardEntries(samlSsoUser));
    modelAndView.addObject("formAction",
        MvcUriComponentsBuilder.fromMethodCall(on(DashboardController.class).handleCreateApplicationSubmit(null)).build().toString());
    return modelAndView;
  }

  @PostMapping
  public ModelAndView handleCreateApplicationSubmit(SamlSsoUser samlSsoUser) {
    Set<String> organisationIds = userPrivilegeService.getOrganisationIdsForUser(samlSsoUser);
    pon1ApplicationService.createApplication(organisationIds.iterator().next());
    return new ModelAndView("redirect:/dashboard");
  }

}
