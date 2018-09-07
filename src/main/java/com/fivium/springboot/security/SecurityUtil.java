package com.fivium.springboot.security;

import com.fivium.springboot.model.security.SamlSsoUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtil {

  public static Optional<SamlSsoUser> getCurrentSamlSsoUser() {
    Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (details instanceof SamlSsoUser) {
      return Optional.of((SamlSsoUser) details);
    } else {
      return Optional.empty();
    }
  }
}
