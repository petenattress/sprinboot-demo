package com.fivium.springboot.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fivium.springboot.Application;
import com.fivium.springboot.config.WebConfig;
import com.fivium.springboot.config.WebSecurityConfig;
import com.fivium.springboot.model.enums.ReleaseType;
import com.fivium.springboot.model.persistence.Pon1Application;
import com.fivium.springboot.model.persistence.Pon1ApplicationVersion;
import com.fivium.springboot.repository.Pon1ApplicationRepository;
import com.fivium.springboot.service.NewApplicationTriageService;
import com.fivium.springboot.util.ApplicationHandlerInterceptor;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(NewApplicationTriageController.class)
@Import({WebConfig.class, WebSecurityConfig.class}) //Workaround for https://github.com/spring-projects/spring-boot/issues/6514
public class NewApplicationTriageControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private NewApplicationTriageService newApplicationTriageService;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  private static Pon1ApplicationRepository setupMockRepo(Pon1ApplicationRepository pon1ApplicationRepository) {

    Pon1Application pon1Application = new Pon1Application();
    pon1Application.getPon1ApplicationVersions().add(new Pon1ApplicationVersion());

    when(pon1ApplicationRepository.findById(1L)).thenReturn(Optional.of(pon1Application));
    return pon1ApplicationRepository;
  }

  @Test
  public void testGetReleaseType() throws Exception {

    mockMvc.perform(get("/application/{id}/triage/release-type", 1).session(createMockHttpSession()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attribute("formAction", Matchers.endsWith("/application/1/triage/release-type")))
        .andExpect(model().attribute("radioOptions", Matchers.isA(Map.class)))
        .andExpect(view().name("application/triage/releaseType"));
  }

  @Test
  public void testPostReleaseType_valid() throws Exception {

    mockMvc.perform(post("/application/{id}/triage/release-type", 1)
            .session(createMockHttpSession())
            .with(csrf())
            .param("releaseType", ReleaseType.IN_EXCESS_OF_PERMIT.toString())
        )
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("exercise"));
  }

  @Test
  public void testPostReleaseType_invalid() throws Exception {

    mockMvc.perform(post("/application/{id}/triage/release-type", 1)
            .session(createMockHttpSession())
            .with(csrf())
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attribute("formAction", Matchers.endsWith("/application/1/triage/release-type")))
        .andExpect(model().attribute("radioOptions", Matchers.isA(Map.class)))
        .andExpect(model().attributeHasFieldErrors("form", "releaseType"))
        .andExpect(view().name("application/triage/releaseType"));
  }

  //Needed due to the main Application class having JPA specific annotations on it
  //https://stackoverflow.com/questions/41250177/getting-at-least-one-jpa-metamodel-must-be-present-with-webmvctest
  @SpringBootApplication
  public static class TestApplication {
    public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
    }
  }

  @Configuration
  public static class TestConfiguration {
    //Dependency of WebConfig class needs a mock injected into it - might be a good reason not to use this pattern
    @Bean
    ApplicationHandlerInterceptor applicationHandlerInterceptor() {
      return new ApplicationHandlerInterceptor(setupMockRepo(mock(Pon1ApplicationRepository.class)));
    }
  }

  private MockHttpSession createMockHttpSession() {
    MockHttpSession mockHttpSession = new MockHttpSession();
    return mockHttpSession;
  }

}