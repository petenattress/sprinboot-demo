package com.fivium.springboot.util;

import com.fivium.springboot.exception.InvalidApplicationException;
import com.fivium.springboot.model.persistence.Pon1Application;
import com.fivium.springboot.repository.Pon1ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component
public class ApplicationHandlerInterceptor implements WebRequestInterceptor {

  public static final String PON1_APPLICATION_REQUEST_ATTRIBUTE = "ApplicationHandlerInterceptor.pon1Application";

  private final Pon1ApplicationRepository pon1ApplicationRepository;

  @Autowired
  public ApplicationHandlerInterceptor(Pon1ApplicationRepository pon1ApplicationRepository) {
    this.pon1ApplicationRepository = pon1ApplicationRepository;
  }

  @Override
  public void preHandle(WebRequest request) {
    Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
    Object applicationIdString = pathVariables.get("applicationId");
    if (applicationIdString != null) {
      long applicationId;
      try {
        applicationId = Long.parseLong(applicationIdString.toString());
      } catch (NumberFormatException e) {
        throw new InvalidApplicationException("applicationId in URL path is not a long");
      }

      Pon1Application pon1Application = pon1ApplicationRepository.findById(applicationId)
          .orElseThrow(() -> new InvalidApplicationException("Specified applicationId not found"));

      request.setAttribute(PON1_APPLICATION_REQUEST_ATTRIBUTE, pon1Application, RequestAttributes.SCOPE_REQUEST);
    } else {
      throw new InvalidApplicationException("Request path missing applicationId"); //TODO these should be mapped to 404
    }
  }

  @Override
  public void postHandle(WebRequest request, ModelMap model) {
  }

  @Override
  public void afterCompletion(WebRequest request, Exception ex) {
  }
}
