package com.fivium.springboot.model.security;

import static org.junit.Assert.*;

public class SamlSsoUserTestUtil {

  public static SamlSsoUser createUser(String userId, String email) {
    return new SamlSsoUser(userId, email);
  }

}