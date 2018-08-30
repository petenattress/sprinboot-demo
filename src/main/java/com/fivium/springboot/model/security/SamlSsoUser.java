package com.fivium.springboot.model.security;

import org.pac4j.core.profile.CommonProfile;

import java.util.List;

public class SamlSsoUser {

  private final CommonProfile profile;

  public static SamlSsoUser unauthenticatedUser() {
    return new SamlSsoUser(null);
  }

  public static SamlSsoUser fromCommonProfile(CommonProfile commonProfile) {
    return new SamlSsoUser(commonProfile);
  }

  private SamlSsoUser(CommonProfile profile) {
    this.profile = profile;
  }

  public boolean isAuthenticated() {
    return profile != null;
  }

  public String getUserId() {
    return profile != null ? ((List<String>) profile.getAttribute("ID")).get(0) : "";
  }

  public String getEmailAddress() {
    return profile != null ? ((List<String>) profile.getAttribute("PRIMARY_EMAIL_ADDRESS")).get(0) : "";
  }
}
