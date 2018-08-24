package com.fivium.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GreetingHtmlControllerTest2 {

  @Autowired
  GreetingHtmlController greetingHtmlController;

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void handleSubmit() throws Exception {

    ResponseEntity<String> result = restTemplate.getForEntity("http://localhost:" + port + "/greeting",
        String.class);
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}