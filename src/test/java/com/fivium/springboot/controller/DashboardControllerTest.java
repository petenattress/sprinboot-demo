package com.fivium.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.fivium.springboot.model.security.SamlSsoUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DashboardControllerTest {

  @Autowired
  DashboardController dashboardController;

  @Test
  public void testRenderForm() {

    ModelAndView modelAndView = dashboardController.renderDashboard(mock(SamlSsoUser.class));

    assertThat(modelAndView.getViewName()).isEqualTo("dashboard");

  }
}