package com.fivium.springboot.service;

import com.fivium.springboot.model.security.SamlSsoUser;
import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class UserPrivilegeService {

  public Set<String> getOrganisationIdsForUser(SamlSsoUser samlSsoUser) {
    switch (samlSsoUser.getUserId()) {
      case "24492":
        return ImmutableSet.of("ORG1", "ORG2");
      case "24493":
        return ImmutableSet.of("ORG2", "ORG3");
      default:
        return Collections.emptySet();
    }
  }

}
