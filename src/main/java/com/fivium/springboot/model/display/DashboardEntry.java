package com.fivium.springboot.model.display;

public class DashboardEntry {

  private Long applicationId;
  private String operatorReference;

  public DashboardEntry() {
  }

  public Long getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(Long applicationId) {
    this.applicationId = applicationId;
  }

  public String getOperatorReference() {
    return operatorReference;
  }

  public void setOperatorReference(String operatorReference) {
    this.operatorReference = operatorReference;
  }
}
