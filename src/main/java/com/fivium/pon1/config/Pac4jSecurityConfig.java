package com.fivium.pon1.config;

import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.saml.client.SAML2Client;
import org.pac4j.saml.client.SAML2ClientConfiguration;
import org.pac4j.springframework.web.SecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = "org.pac4j.springframework.web")
public class Pac4jSecurityConfig extends WebMvcConfigurerAdapter {

  @Bean
  public Config config() {
    SAML2ClientConfiguration samlConfig = new SAML2ClientConfiguration(new ClassPathResource("saml/keystore.jks"),
        "lite-ogel-registration",
        "jks",
        "keypass",
        "keypass",
        new ClassPathResource("saml/metadata.xml"));
    samlConfig.setMaximumAuthenticationLifetime(3600);
    samlConfig.setServiceProviderEntityId("http://localhost:8080");

    //Maximum permitted age of IdP response in seconds
    samlConfig.setMaximumAuthenticationLifetime(3600);

    SAML2Client saml2Client = new SAML2Client(samlConfig);

    Clients clients = new Clients("http://localhost:8080/callback", saml2Client);

    Config config = new Config(clients);
    //config.addAuthorizer("admin", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
    //config.addAuthorizer("custom", new CustomAuthorizer());
    return config;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new SecurityInterceptor(config(), "SAML2Client")).addPathPatterns("/form/*");
  }
}