package com.fivium.pon1.security;

import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SamlProfileHandlerInterceptor implements HandlerInterceptor {
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                         ModelAndView modelAndView) {
    WebContext context = new J2EContext(request, response);
    ProfileManager manager = new ProfileManager(context);
    Optional<CommonProfile> profile = manager.get(true);

    if (modelAndView != null) {
      modelAndView.addObject("userEmail",
          profile.map(e -> ((List<String>) e.getAttribute("PRIMARY_EMAIL_ADDRESS")).get(0)).orElse(
              "Not logged in"));
    }
  }
}
