package com.fivium.springboot.model.form.application.triage;

import javax.validation.constraints.NotNull;

public class ExerciseForm {

  @NotNull
  private Boolean exercise;

  public Boolean getExercise() {
    return exercise;
  }

  public void setExercise(Boolean exercise) {
    this.exercise = exercise;
  }
}
