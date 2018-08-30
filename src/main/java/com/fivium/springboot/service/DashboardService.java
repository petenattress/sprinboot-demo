package com.fivium.springboot.service;

import com.fivium.springboot.model.display.DashboardEntry;
import com.fivium.springboot.model.security.SamlSsoUser;
import com.fivium.springboot.repository.Pon1ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DashboardService {

  private final UserPrivilegeService userPrivilegeService;
  private final Pon1ApplicationRepository pon1ApplicationRepository;

  @Autowired
  public DashboardService(UserPrivilegeService userPrivilegeService,
                          Pon1ApplicationRepository pon1ApplicationRepository) {
    this.userPrivilegeService = userPrivilegeService;
    this.pon1ApplicationRepository = pon1ApplicationRepository;
  }

  public List<DashboardEntry> createDashboardEntries(SamlSsoUser samlSsoUser) {
    Set<String> organisationIds = userPrivilegeService.getOrganisationIdsForUser(samlSsoUser);

    return pon1ApplicationRepository.findByOrganisationIdIn(organisationIds).stream()
        .map(application -> {
          DashboardEntry dashboardEntry = new DashboardEntry();
          dashboardEntry.setOperatorReference("TODO");
          dashboardEntry.setApplicationId(application.getId());
          return dashboardEntry;
        })
        .collect(Collectors.toList());
  }

}
