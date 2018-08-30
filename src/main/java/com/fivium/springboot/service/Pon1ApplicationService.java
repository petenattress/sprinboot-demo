package com.fivium.springboot.service;

import com.fivium.springboot.model.persistence.Pon1Application;
import com.fivium.springboot.model.persistence.Pon1ApplicationVersion;
import com.fivium.springboot.repository.Pon1ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Pon1ApplicationService {

  private final Pon1ApplicationRepository pon1ApplicationRepository;

  @Autowired
  public Pon1ApplicationService(Pon1ApplicationRepository pon1ApplicationRepository) {
    this.pon1ApplicationRepository = pon1ApplicationRepository;
  }

  @Transactional
  public Pon1Application createApplication(String organisationId) {
    Pon1Application application = new Pon1Application();
    application.setOrganisationId(organisationId);
    Pon1ApplicationVersion applicationVersion = new Pon1ApplicationVersion();
    applicationVersion.setPon1Application(application);
    application.getPon1ApplicationVersions().add(applicationVersion);

    pon1ApplicationRepository.save(application);
    return application;
  }

}
