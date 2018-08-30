package com.fivium.springboot.config;

import com.fivium.springboot.security.SamlProfileHandlerInterceptor;
import com.fivium.springboot.security.SamlSsoUserArgumentResolver;
import com.fivium.springboot.util.ApplicationHandlerInterceptor;
import com.fivium.springboot.util.Pon1ApplicationArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final ApplicationHandlerInterceptor applicationHandlerInterceptor;

  @Autowired
  public WebConfig(ApplicationHandlerInterceptor applicationHandlerInterceptor) {
    this.applicationHandlerInterceptor = applicationHandlerInterceptor;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/webjars/**")
        .addResourceLocations("/webjars/");
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new SamlProfileHandlerInterceptor());
    registry.addWebRequestInterceptor(applicationHandlerInterceptor).addPathPatterns("/application/**");
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new SamlSsoUserArgumentResolver());
    resolvers.add(new Pon1ApplicationArgumentResolver());
  }
}
