package com.fivium.springboot.security;

import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

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

    //https://developingdeveloper.wordpress.com/2008/02/28/common-reference-data-in-spring-mvc/
    if (modelAndView != null) {
      boolean isRedirectView = modelAndView.getView() instanceof RedirectView;
      // if the view name is null then set a default value of true
      boolean viewNameStartsWithRedirect = (modelAndView.getViewName() != null &&
          modelAndView.getViewName().startsWith(UrlBasedViewResolver.REDIRECT_URL_PREFIX));

      if (!isRedirectView && !viewNameStartsWithRedirect) {
        modelAndView.addObject("userEmail",
            profile.map(e -> ((List<String>) e.getAttribute("PRIMARY_EMAIL_ADDRESS")).get(0))
                .orElse("Not logged in"));
      }
    }
  }
}
