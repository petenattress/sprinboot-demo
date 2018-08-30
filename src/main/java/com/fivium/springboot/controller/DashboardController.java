package com.fivium.springboot.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.fivium.springboot.model.display.DashboardEntry;
import com.fivium.springboot.model.security.SamlSsoUser;
import com.fivium.springboot.repository.Pon1ApplicationRepository;
import com.fivium.springboot.service.Pon1ApplicationService;
import com.fivium.springboot.service.UserPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

  private final UserPrivilegeService userPrivilegeService;
  private final Pon1ApplicationRepository pon1ApplicationRepository;
  private final Pon1ApplicationService pon1ApplicationService;

  @Autowired
  public DashboardController(UserPrivilegeService userPrivilegeService,
                             Pon1ApplicationRepository pon1ApplicationRepository,
                             Pon1ApplicationService pon1ApplicationService) {
    this.userPrivilegeService = userPrivilegeService;
    this.pon1ApplicationRepository = pon1ApplicationRepository;
    this.pon1ApplicationService = pon1ApplicationService;
  }

  @GetMapping
  public ModelAndView renderDashboard(SamlSsoUser samlSsoUser) {

    //TODO - should this logic be in a service? Might make testing easier
    Set<String> organisationIds = userPrivilegeService.getOrganisationIdsForUser(samlSsoUser);

    List<DashboardEntry> dashboardEntries = pon1ApplicationRepository
        .findByOrganisationIdIn(organisationIds)
        .stream()
        .map(e -> {
          DashboardEntry dashboardEntry = new DashboardEntry();
          dashboardEntry.setOperatorReference("TODO");
          dashboardEntry.setVersionId(1L);
          return dashboardEntry;
        }).collect(Collectors.toList());

    ModelAndView modelAndView = new ModelAndView("dashboard");
    modelAndView.addObject("dashboardEntries", dashboardEntries);
    modelAndView.addObject("formAction",
        MvcUriComponentsBuilder.fromMethodCall(on(DashboardController.class).handleCreateApplicationSubmit(null)).build().toString());
    return modelAndView;
  }

  @PostMapping
  public View handleCreateApplicationSubmit(SamlSsoUser samlSsoUser) {
    Set<String> organisationIds = userPrivilegeService.getOrganisationIdsForUser(samlSsoUser);
    pon1ApplicationService.createApplication(organisationIds.iterator().next());
    //return new ModelAndView("redirect:/dashboard");
    RedirectView redirectView = new RedirectView("/dashboard");
    redirectView.setExposeModelAttributes(false);
    return redirectView;
  }

}
