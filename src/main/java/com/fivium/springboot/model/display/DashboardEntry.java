package com.fivium.springboot.model.display;

public class DashboardEntry {

  private Long versionId;
  private String operatorReference;

  public DashboardEntry() {
  }

  public Long getVersionId() {
    return versionId;
  }

  public void setVersionId(Long versionId) {
    this.versionId = versionId;
  }

  public String getOperatorReference() {
    return operatorReference;
  }

  public void setOperatorReference(String operatorReference) {
    this.operatorReference = operatorReference;
  }
}
