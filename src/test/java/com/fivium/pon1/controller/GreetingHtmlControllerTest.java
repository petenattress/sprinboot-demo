package com.fivium.pon1.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(GreetingHtmlController.class)
public class GreetingHtmlControllerTest {

  @Autowired
  GreetingHtmlController greetingHtmlController;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void handleSubmit() throws Exception {

    mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk());

  }
}