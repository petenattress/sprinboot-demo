package com.fivium.springboot.service;

import com.fivium.springboot.model.form.application.triage.ExerciseForm;
import com.fivium.springboot.model.form.application.triage.ReleaseTypeForm;
import com.fivium.springboot.model.persistence.Pon1Application;
import com.fivium.springboot.repository.Pon1ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewApplicationTriageService {

  private final Pon1ApplicationRepository pon1ApplicationRepository;

  @Autowired
  public NewApplicationTriageService(Pon1ApplicationRepository pon1ApplicationRepository) {
    this.pon1ApplicationRepository = pon1ApplicationRepository;
  }

  public void applyReleaseTypeModelToForm(Pon1Application pon1Application, ReleaseTypeForm form) {
    form.setReleaseType(pon1Application.getCurrentVersion().getReleaseType());
  }

  public void applyReleaseTypeFormToModel(ReleaseTypeForm form, Pon1Application pon1Application) {
    pon1Application.getCurrentVersion().setReleaseType(form.getReleaseType());
    pon1ApplicationRepository.save(pon1Application);
  }

  public void applyExerciseModelToForm(Pon1Application pon1Application, ExerciseForm form) {
    form.setExercise(pon1Application.getCurrentVersion().isExercise());
  }

  public void applyExerciseFormToModel(ExerciseForm form, Pon1Application pon1Application) {
    pon1Application.getCurrentVersion().setExercise(form.getExercise());
    pon1ApplicationRepository.save(pon1Application);
  }

}
