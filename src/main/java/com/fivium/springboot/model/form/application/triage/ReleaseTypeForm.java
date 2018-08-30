package com.fivium.springboot.model.form.application.triage;

import com.fivium.springboot.model.enums.ReleaseType;

import javax.validation.constraints.NotNull;

public class ReleaseTypeForm {

  @NotNull(message = "Select a release type")
  private ReleaseType releaseType;

  public ReleaseType getReleaseType() {
    return releaseType;
  }

  public void setReleaseType(ReleaseType releaseType) {
    this.releaseType = releaseType;
  }
}
