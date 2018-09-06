package com.fivium.springboot.model.security;


import java.security.Principal;

public class SamlSsoUser {

  private final Principal profile;

  public static SamlSsoUser unauthenticatedUser() {
    return new SamlSsoUser(null);
  }

  public static SamlSsoUser fromCommonProfile(Principal commonProfile) {
    return new SamlSsoUser(commonProfile);
  }

  private SamlSsoUser(Principal profile) {
    this.profile = profile;
  }

  public boolean isAuthenticated() {
    return profile != null;
  }

  public String getUserId() {
    return profile != null ? profile.getName() : "";
  }

  public String getEmailAddress() {
    return profile != null ? profile.getName() : "";
  }
}
