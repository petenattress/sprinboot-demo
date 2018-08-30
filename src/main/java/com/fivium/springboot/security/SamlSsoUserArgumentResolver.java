package com.fivium.springboot.security;

import com.fivium.springboot.model.security.SamlSsoUser;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SamlSsoUserArgumentResolver implements HandlerMethodArgumentResolver {
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(SamlSsoUser.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    WebContext context = new J2EContext((HttpServletRequest) webRequest.getNativeRequest(),
        (HttpServletResponse) webRequest.getNativeResponse());
    ProfileManager<CommonProfile> manager = new ProfileManager<>(context);
    Optional<CommonProfile> optionalProfile = manager.get(true);
    return optionalProfile.map(SamlSsoUser::fromCommonProfile).orElse(SamlSsoUser.unauthenticatedUser());
  }
}
