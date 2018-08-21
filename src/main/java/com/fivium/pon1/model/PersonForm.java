package com.fivium.pon1.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PersonForm {

  public enum EyeColour {
    BROWN, BLUE, GREEN
  }

  public enum Interest {
    PROGRAMMING, FISHING, DRIVING, MOPING;

    //Called dynamically by the template engine
    public String getFormPrompt() {
      return StringUtils.capitalize(toString().toLowerCase());
    }
  }

  @NotNull
  @Size(min=2, max=30)
  private String name;

  @NotNull
  @Min(18)
  private Integer age;

  @NotNull
  private EyeColour eyeColour = EyeColour.BROWN;

  @NotNull(message = "Please say if you have a driving licence")
  private Boolean drivingLicence;

  private List<Interest> interests = new ArrayList<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public EyeColour getEyeColour() {
    return eyeColour;
  }

  public void setEyeColour(EyeColour eyeColour) {
    this.eyeColour = eyeColour;
  }

  public Boolean getDrivingLicence() {
    return drivingLicence;
  }

  public void setDrivingLicence(Boolean drivingLicence) {
    this.drivingLicence = drivingLicence;
  }

  public List<Interest> getInterests() {
    return interests;
  }

  public void setInterests(List<Interest> interests) {
    this.interests = interests;
  }

  @Override
  public String toString() {
    return "PersonForm{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", eyeColour=" + eyeColour +
        ", drivingLicence=" + drivingLicence +
        ", interests=" + interests +
        '}';
  }
}
