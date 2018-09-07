package com.fivium.springboot.config;

import static org.springframework.security.extensions.saml2.config.SAMLConfigurer.saml;

import com.fivium.springboot.model.security.SamlSsoUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.extensions.saml2.config.SAMLConfigurer;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.security.saml.websso.WebSSOProfileOptions;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.lang.reflect.Field;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    SAMLConfigurer samlConfigurer = saml();

    //Hack to set the RelayState (required by SPIRE SSO) - better option would be to use XML config for SAML module which is more flexible
    //Or clone the DSL library and fix it
    Field profileOptions = samlConfigurer.getClass().getDeclaredField("webSSOProfileOptions");
    profileOptions.setAccessible(true);
    WebSSOProfileOptions webSSOProfileOptions = new WebSSOProfileOptions();
    webSSOProfileOptions.setRelayState("http://localhost:8080");
    webSSOProfileOptions.setIncludeScoping(false);
    profileOptions.set(samlConfigurer, webSSOProfileOptions);

    http
        .authorizeRequests()
        .antMatchers("/", "/logout", "/assets/**", "/webjars/**", "/resources/**")
          .permitAll()
        .antMatchers("/**")
          .authenticated()
        .and()
          .apply(samlConfigurer)
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
            .userDetailsService(new MySAMLUserDetailsService())
        .and()
          .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //NB documentation recommends this should be POST only
            .logoutSuccessUrl("/");
  }

  public static class MySAMLUserDetailsService implements SAMLUserDetailsService {
    @Override
    public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
      return SamlSsoUser.fromCredential(credential);
    }
  }
}