package com.fivium.pon1.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.fivium.pon1.model.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class GreetingRestController {

  @RequestMapping(value = "/greeting", method = GET)
  public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    return new Greeting(1, String.format("Hello, %s", name));
  }

}
