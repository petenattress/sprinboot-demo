package com.fivium.springboot.config;

import static org.springframework.security.extensions.saml2.config.SAMLConfigurer.saml;

import com.fivium.springboot.model.security.SamlSsoUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/webjars/**", "/resources/**")
          .permitAll()
        .antMatchers("/**")
          .authenticated()
        .and()
          .apply(saml())
            .serviceProvider()
            .keyStore()
              .storeFilePath("saml/keystore.jks")
              .password("keypass")
              .keyname("lite-ogel-registration")
              .keyPassword("keypass")
              .and()
            .protocol("http")
            .hostname("localhost:8080")
            .basePath("/")
            .and()
            .identityProvider()
              .metadataFilePath("classpath:saml/metadata.xml")
              .and()
            .userDetailsService(new MySAMLUserDetailsService());
  }

  public static class MySAMLUserDetailsService implements SAMLUserDetailsService {
    @Override
    public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
      return SamlSsoUser.fromCredential(credential);
    }
  }
}