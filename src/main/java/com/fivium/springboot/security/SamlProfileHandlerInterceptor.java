package com.fivium.springboot.security;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SamlProfileHandlerInterceptor implements HandlerInterceptor {
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                         ModelAndView modelAndView) {

    //https://developingdeveloper.wordpress.com/2008/02/28/common-reference-data-in-spring-mvc/
    if (modelAndView != null) {
      boolean isRedirectView = modelAndView.getView() instanceof RedirectView;
      // if the view name is null then set a default value of true
      boolean viewNameStartsWithRedirect = (modelAndView.getViewName() != null &&
          modelAndView.getViewName().startsWith(UrlBasedViewResolver.REDIRECT_URL_PREFIX));

      if (!isRedirectView && !viewNameStartsWithRedirect) {
        modelAndView.addObject("userEmail", "TODO");
      }
    }
  }
}
