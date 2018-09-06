package com.fivium.springboot.model.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.saml.SAMLCredential;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

public class SamlSsoUser implements Serializable, UserDetails { //TODO is implements UserDetails required?

  private static final long serialVersionUID = 1L;

  private final String userId;
  private final String emailAddress;

  public static SamlSsoUser unauthenticatedUser() {
    return new SamlSsoUser("", "");
  }

  public static SamlSsoUser fromCredential(SAMLCredential credential) {
    return new SamlSsoUser(credential.getAttributeAsString("ID"), credential.getAttributeAsString("PRIMARY_EMAIL_ADDRESS"));
  }

  SamlSsoUser(String userId, String emailAddress) {
    this.userId = userId;
    this.emailAddress = emailAddress;
  }

  public boolean isAuthenticated() {
    return !"".equals(userId);
  }

  public String getUserId() {
    return userId;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptySet();
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return emailAddress;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
