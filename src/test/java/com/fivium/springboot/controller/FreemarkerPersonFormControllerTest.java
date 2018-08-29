package com.fivium.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.fivium.springboot.model.PersonForm;
import com.fivium.springboot.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.NONE*/)
public class FreemarkerPersonFormControllerTest {

  @Autowired
  FreemarkerPersonFormController freemarkerPersonFormController;

  @MockBean
  PersonRepository personRepository;


  @Test
  public void testRenderForm() {

    ModelAndView modelAndView = freemarkerPersonFormController.renderForm(1L);

    assertThat(modelAndView.getModel().get("id")).isEqualTo(1L);
    assertThat(modelAndView.getModel().get("personForm")).isInstanceOf(PersonForm.class);
    assertThat(modelAndView.getModel().get("interestOptions")).isInstanceOfSatisfying(Map.class, map -> assertThat(map.size()).isEqualTo(4));

  }
}